/*
Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
 
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

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Action;
import org.openide.util.NbPreferences;
import org.web3d.x3d.options.X3dEditUserPreferences;

/**
 * LocalExamplesFinder.java
 * Created on Feb 7, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey jmbailey@nps.edu
 * @author Don Brutzman jmbailey@nps.edu
 * @version $Id$
 */
public class LocalExamplesFinder
{
  // The following root must resolve to a string which when appended to "file://", can be correctly displayed in the
  // default browser on each platform.
  public static File   DEFAULT_ROOT_DIR;
  public static String DEFAULT_DIR_TREE    = "/www.web3d.org/x3d/content/examples/";
  public static String DEFAULT_SOURCEFORGE = "/x3d-code"; // default sourceforge checkout parent directory
  public static String DEFAULT_ROOT_PATH;
  public static String DEFAULT_BASIC_PATH;
  public static String DEFAULT_CONFORMANCENIST_PATH;
  public static String DEFAULT_HANIM_PATH;
  public static String DEFAULT_VRMLSOURCEBOOK_PATH;
  public static String DEFAULT_X3D4WA_PATH;
  public static String DEFAULT_X3D4AM_PATH;
  public static String DEFAULT_SAVAGE_PATH;
  public static String DEFAULT_SAVAGEDEFENSE_PATH;
  
  private DirectoryHandler basicDirectoryHandler;
  private DirectoryHandler conformanceNistDirectoryHandler;
  private DirectoryHandler hanimDirectoryHandler;
  private DirectoryHandler vrmlsourcebookDirectoryHandler;
  private DirectoryHandler x3d4waDirectoryHandler;
  private DirectoryHandler x3d4amDirectoryHandler;
  private DirectoryHandler savageDirectoryHandler;
  private DirectoryHandler savagedefenseDirectoryHandler;
  private boolean          savageDefenseFound = false;
  
  private LocalExamplesFinder()
  {
      initialize ();
  }
  private void initialize ()
  {
    // TODO make successful path persistent and exposed through X3D Options panel
    if  (!X3dEditUserPreferences.getExampleArchivesRootDirectory().isBlank())
         DEFAULT_ROOT_DIR  = new File(X3dEditUserPreferences.getExampleArchivesRootDirectory());
    else DEFAULT_ROOT_DIR  = new File(DEFAULT_DIR_TREE);
    DEFAULT_ROOT_PATH = DEFAULT_ROOT_DIR.getAbsolutePath();
    if ((DEFAULT_ROOT_PATH == null) || DEFAULT_ROOT_PATH.isEmpty() || !DEFAULT_ROOT_DIR.isDirectory())
    {
        DEFAULT_ROOT_DIR  = new File(DEFAULT_SOURCEFORGE + DEFAULT_DIR_TREE);
        DEFAULT_ROOT_PATH = DEFAULT_ROOT_DIR.getAbsolutePath();
    } // noi18n
    DEFAULT_BASIC_PATH           = DEFAULT_ROOT_PATH+File.separator+"Basic";
    DEFAULT_CONFORMANCENIST_PATH = DEFAULT_ROOT_PATH+File.separator+"ConformanceNist";
    DEFAULT_HANIM_PATH           = DEFAULT_ROOT_PATH+File.separator+"HumanoidAnimation";
    DEFAULT_VRMLSOURCEBOOK_PATH  = DEFAULT_ROOT_PATH+File.separator+"Vrml2Sourcebook";
    DEFAULT_X3D4WA_PATH          = DEFAULT_ROOT_PATH+File.separator+"X3dForWebAuthors";  
    DEFAULT_X3D4AM_PATH          = DEFAULT_ROOT_PATH+File.separator+"X3dForAdvancedModeling";  
    DEFAULT_SAVAGE_PATH          = DEFAULT_ROOT_PATH+File.separator+"Savage";
    DEFAULT_SAVAGEDEFENSE_PATH   = DEFAULT_ROOT_PATH+File.separator+"SavageDefense";
    
    basicDirectoryHandler   = new DirectoryHandler("BasicExamples"      ,DEFAULT_BASIC_PATH); // noi18n
    conformanceNistDirectoryHandler = new DirectoryHandler("ConformanceExamples",DEFAULT_CONFORMANCENIST_PATH);
    hanimDirectoryHandler   = new DirectoryHandler("HumanoidExamples"   ,DEFAULT_HANIM_PATH);
    vrmlsourcebookDirectoryHandler  = new DirectoryHandler("VrmlExamples"       ,DEFAULT_VRMLSOURCEBOOK_PATH);
    x3d4waDirectoryHandler  = new DirectoryHandler("X3d4waExamples"     ,DEFAULT_X3D4WA_PATH);
    x3d4amDirectoryHandler  = new DirectoryHandler("X3d4amExamples"     ,DEFAULT_X3D4AM_PATH);
    savageDirectoryHandler  = new DirectoryHandler("SavageExamples"     ,DEFAULT_SAVAGE_PATH);
    
    if ((new File(DEFAULT_SAVAGEDEFENSE_PATH)).isDirectory())
    {
        savageDefenseFound = true;
        savagedefenseDirectoryHandler = new DirectoryHandler("SavageDefenseExamples", DEFAULT_SAVAGEDEFENSE_PATH);
    }
  }

  private static LocalExamplesFinder me;

  public static LocalExamplesFinder instance()
  {
    final Object syncher = new Object();
    synchronized (syncher) {
      if (me == null)
      {
          me = new LocalExamplesFinder();
          me.initialize ();
      }
    }
    return me;
  }

    /**
     * @return whether savageDefenseFound
     */
    public boolean isSavageDefenseFound()
    {
        return savageDefenseFound;
    }
        
  class DirectoryHandler
  {
    DirectoryHandler(String key, String dflt)
    {
      defaultLoc = dflt;
      prefsKey = key;
    }
    public Set<Action> listeners = new HashSet<>();
    public String defaultLoc;
    public String prefsKey;
  }
  
  public String findBasicExamplesDirectory(Action menuItem)
  {
    return findExamplesDirectory(basicDirectoryHandler,menuItem);
  }
  public String findConformExamplesDirectory(Action menuItem)
  {
    return findExamplesDirectory(conformanceNistDirectoryHandler,menuItem);
  }
  public String findHumanoidAnimationExamplesDirectory(Action menuItem)
  {
    return findExamplesDirectory(hanimDirectoryHandler,menuItem);
  }
  public String findVrmlExamplesDirectory(Action menuItem)
  {
    return findExamplesDirectory(vrmlsourcebookDirectoryHandler,menuItem);
  }
  public String findX3d4waExamplesDirectory(Action menuItem)
  {
    return findExamplesDirectory(x3d4waDirectoryHandler,menuItem);
  }
  public String findX3d4amExamplesDirectory(Action menuItem)
  {
    return findExamplesDirectory(x3d4amDirectoryHandler,menuItem);
  }
  public String findSavageExamplesDirectory(Action menuItem)
  {
    return findExamplesDirectory(savageDirectoryHandler,menuItem);
  }
  public String findSavageDefenseExamplesDirectory(Action menuItem)
  {
      if  (isSavageDefenseFound())
           return findExamplesDirectory(savagedefenseDirectoryHandler,menuItem);
      else return null;
  }
  
    
  public void setBasicExamplesDirectory(String s)
  {
    setExamplesDir(basicDirectoryHandler,s);
  }
  public void setConformExamplesDirectory(String s)
  {
    setExamplesDir(conformanceNistDirectoryHandler,s);
  }
  public void setHumanoidAnimationExamplesDirectory(String s)
  {
    setExamplesDir(hanimDirectoryHandler,s);
  }
  public void setVrmlExamplesDirectory(String s)
  {
    setExamplesDir(vrmlsourcebookDirectoryHandler,s);
  }
  public void setX3d4waExamplesDirectory(String s)
  { 
    setExamplesDir(x3d4waDirectoryHandler,s);
  }
  public void setX3d4amExamplesDirectory(String s)
  { 
    setExamplesDir(x3d4amDirectoryHandler,s);
  }
  public void setSavageExamplesDirectory(String s)
  {
    setExamplesDir(savageDirectoryHandler,s);
  }
  public void setSavageDefenseExamplesDirectory(String s)
  {
      if  (isSavageDefenseFound())
           setExamplesDir(savagedefenseDirectoryHandler,s);
  }
  
  
  private String findExamplesDirectory(DirectoryHandler directoryHandler, Action enabler)
  {
    directoryHandler.listeners.add(enabler);
    String ret = prefsGet(directoryHandler.prefsKey);
    if(ret != null)
      if(!isThere(ret)) {         // erased?
        prefsRemove(directoryHandler.prefsKey);
        ret = null;
      }
    if(ret == null)
      ret = findDefaultDirecctory(directoryHandler);
    if(ret != null)
      setExamplesDir(directoryHandler,ret);
    else
      doActions(directoryHandler,false);
    return ret;    
  }
  
  private String findDefaultDirecctory(DirectoryHandler directoryHandler)
  {
    if(isThere(directoryHandler.defaultLoc))
      return directoryHandler.defaultLoc;
    return null;
  }
  
  private void doActions(DirectoryHandler dh, boolean enabled)
  {
    for(Action a: dh.listeners)
      a.setEnabled(enabled);
  }
  
  private boolean isThere(String dir)
  {
    File f = new File(dir);
    if(f.exists())
      if(f.isDirectory())
        return true;
    return false;    
  }
  
  private String prefsGet(String key)
  {
    return NbPreferences.forModule(getClass()).get(key, null);
  }
  
  private void setExamplesDir(DirectoryHandler dh,String s)
  {
    prefsSet(dh.prefsKey,s);
    doActions(dh,true);
  }
  
  private void prefsSet(String key, String val)
  {
    NbPreferences.forModule(getClass()).put(key, val);
  }
  
  private void prefsRemove(String key)
  {
    NbPreferences.forModule(getClass()).remove(key);
  }
}
