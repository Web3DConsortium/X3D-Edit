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
  static final JCheckBox openInEditorCheckBox;
  static final JCheckBox openInBrowserCheckBox;
  static final OpenResultInEditorChooserAccessory openResultInEditorChooserAccessory;
  private static boolean openInBrowserSetting = true; // default viewable
  private static boolean openInEditorSetting  = true; // default verbose
  private static String saveChooserDialogTitle = "Save X3D conversion";
  
  static {
    saveChooser =  new JFileChooser(); 
    openResultInEditorChooserAccessory = new OpenResultInEditorChooserAccessory();
    openInEditorCheckBox = openResultInEditorChooserAccessory.getOpenInEditorCheckBox();
    openInEditorCheckBox.setSelected(true); // default
    openInBrowserCheckBox = openResultInEditorChooserAccessory.getOpenInBrowserCheckBox();
    openInBrowserCheckBox.setSelected(true); // default
    saveChooser.setAccessory(openResultInEditorChooserAccessory);
    ConversionsHelper.setOpenInBrowserSetting(true);
    ConversionsHelper.setOpenInEditorSetting(true);
    ConversionsHelper.getOpenInEditorSetting(); // debug
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
     * @param newOpenInBrowserSetting the openInBrowserSetting to set
     */
    public static void setOpenInBrowserSetting(boolean newOpenInBrowserSetting) {
        openInBrowserSetting = newOpenInBrowserSetting;
        openInBrowserCheckBox.setSelected(openInEditorSetting);
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
     * @param newOpenInEditorSetting the openInEditorSetting to set
     */
    public static void setOpenInEditorSetting(boolean newOpenInEditorSetting) {
        openInEditorSetting = newOpenInEditorSetting;
        openInEditorCheckBox.setSelected(openInEditorSetting);
    }
  
  public static class saveFilePack
  {
    public File file;
    public boolean openInEditor  = true; // viewable
    public boolean openInBrowser = true; // verbose
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
    saveChooser.setAccessory(wantOpenInEditorShown?openResultInEditorChooserAccessory:null);

//    openInEditorCheckBox.setEnabled(wantOpenInEditorShown);
//    openInBrowserCheckBox.setEnabled(wantOpenInEditorShown);
    
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
    retData.openInEditor = openInEditorCheckBox.isSelected();
    retData.openInBrowser= openInBrowserCheckBox.isSelected();
    if (selFile != null)
        saveDirs.put(sourceFile.getAbsolutePath(),selFile.getParent());
    
    return retData;   
  }
  
  static public void openInEditor(String filePathName)
  {
    try {
        if (openInEditorCheckBox.isSelected())
            openInEditor(new File(filePathName));
     }
    catch (Exception e) {
      TransformListener.getInstance().message(NbBundle.getMessage(BaseConversionsAction.class,"Error_opening_") + filePathName +
                                              NbBundle.getMessage(BaseConversionsAction.class,"_in_editor:_") + e.getClass().getName());
    }
  }

  static public void openInEditor(File f) throws Exception
  {
    System.out.println("*** ConversionsHelper.openInInEditor File " + f.getPath());
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
  
  /** Open file in system browser, ensuring prefix such as http://localhost:8001 has been included already.
   * @param resultFileAddress file or Web address to open
   */
  static public void openInBrowser(String resultFileAddress)
  {
      // TODO copy logic from LaunchDownload panel View button
    try {
        resultFileAddress = resultFileAddress.replaceAll("\\\\","/");

        System.out.println("*** ConversionsHelper.openInBrowser String " + resultFileAddress);
        if  (resultFileAddress.startsWith("http"))
             openInBrowser(new URL(resultFileAddress));
        else openInBrowser(new URL("file://"+resultFileAddress));
    }
    catch (MalformedURLException e) {
      IOProvider.getDefault().getIO("Output",false).getOut().append(
        NbBundle.getMessage(BaseConversionsAction.class,"Trying_to_display_") + resultFileAddress +
        NbBundle.getMessage(BaseConversionsAction.class,"_in_HtmlBrowser:_") + e.getLocalizedMessage());   
    }
  }
  
  /** Open file in system browser, ensuring prefix such as http://localhost:8001 has been included already.
   * @param url file or Web address to open
   */
  static public void openInBrowser(URL url)
  {
    // HtmlBrowser.URLDisplayer.getDefault().showURL(url);
      
    if ((url == null) || url.toString().isBlank())
    {
        System.err.println("*** openInBrowser(" + url + ") is an incorrect url address");
        return;
    }
    String urlString = url.toString();
    int    portValue = url.getPort();
    if (urlString.startsWith("http://localhost") && !X3dToXhtmlDomConversionFrame.isPortBound(portValue))
    {
        // notify user if localhost port is not bound
        String message = "openInBrowser(" + urlString + ") port=" + portValue + " is not bound, may need to start localhost http server";
        System.out.println("*** " + message);
        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
        DialogDisplayer.getDefault().notify(notifyDescriptor);
        // continue anyway
    }
    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) && openInBrowserCheckBox.isSelected())
        try {
            System.out.println("*** ConversionsHelper.openInBrowser URL    " + urlString);
            Desktop.getDesktop().browse(new URI(urlString.replaceAll("\\\\","/")));
    } 
    catch (IOException | URISyntaxException ex) {
        System.err.println("*** ConversionsHelper failure to openInBrowser " + urlString);
        Exceptions.printStackTrace(ex);
    }
  }
  
  /** Open file in system browser, ensuring prefix such as http://localhost:8001 has been included already.
   * @param uri file or Web address to open
   */
  static public void openInBrowser(URI uri)
  {
    // HtmlBrowser.URLDisplayer.getDefault().showURL(uri);
      
    if ((uri == null) || uri.toString().isBlank())
    {
        System.err.println("*** openInBrowser(" + uri + ") is an incorrect uri address");
        return;
    }
    String uriString = uri.toString();
    int    portValue = uri.getPort();
    if (uriString.startsWith("http://localhost") && !X3dToXhtmlDomConversionFrame.isPortBound(portValue))
    {
        // notify user if localhost port is not bound
        String message = "openInBrowser(" + uriString + ") port=" + portValue + " is not bound, may need to start localhost http server";
        System.out.println("*** " + message);
        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
        DialogDisplayer.getDefault().notify(notifyDescriptor);
        // continue anyway
    }
    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        try {
            System.out.println("*** ConversionsHelper.openInBrowser URI    " + uri.toString());
            Desktop.getDesktop().browse(uri);
    } 
    catch (IOException ex) {
        Exceptions.printStackTrace(ex);
    }
  }
  
//  static public void openInBrowserCheckBox(String urlString)
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
