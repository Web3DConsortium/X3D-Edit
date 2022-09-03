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

/**
 * LocalExamplesFinder.java
 * Created on Feb 7, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey <jmbailey@nps.edu>
 * @author Don Brutzman <jmbailey@nps.edu>
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
  public static String DEFAULT_CONFORM_PATH;
  public static String DEFAULT_HANIM_PATH;
  public static String DEFAULT_VRMLSB_PATH;
  public static String DEFAULT_X3D4WA_PATH;
  public static String DEFAULT_X3D4AM_PATH;
  public static String DEFAULT_SAVAGE_PATH;
  public static String DEFAULT_SAVAGEDEFENSE_PATH;
  
  private dirHandler basic;
  private dirHandler conform;
  private dirHandler hanim;
  private dirHandler vrmlsb;
  private dirHandler x3d4wa;
  private dirHandler x3d4am;
  private dirHandler savage;
  private dirHandler savagedefense;
  private boolean    savageDefenseFound = false;
  
  private LocalExamplesFinder()
  {
      initialize ();
  }
  private void initialize ()
  {
    // TODO make successful path persistent and exposed through X3D Options panel
    DEFAULT_ROOT_DIR  = new File(DEFAULT_DIR_TREE);
    DEFAULT_ROOT_PATH = DEFAULT_ROOT_DIR.getAbsolutePath();
    if ((DEFAULT_ROOT_PATH == null) || DEFAULT_ROOT_PATH.isEmpty() || !DEFAULT_ROOT_DIR.isDirectory())
    {
        DEFAULT_ROOT_DIR  = new File(DEFAULT_SOURCEFORGE + DEFAULT_DIR_TREE);
        DEFAULT_ROOT_PATH = DEFAULT_ROOT_DIR.getAbsolutePath();
    } // noi18n
    DEFAULT_BASIC_PATH   = DEFAULT_ROOT_PATH+File.separator+"Basic";
    DEFAULT_CONFORM_PATH = DEFAULT_ROOT_PATH+File.separator+"ConformanceNist";
    DEFAULT_HANIM_PATH   = DEFAULT_ROOT_PATH+File.separator+"HumanoidAnimation";
    DEFAULT_VRMLSB_PATH  = DEFAULT_ROOT_PATH+File.separator+"Vrml2.0Sourcebook";
    DEFAULT_X3D4WA_PATH  = DEFAULT_ROOT_PATH+File.separator+"X3dForWebAuthors";  
    DEFAULT_X3D4AM_PATH  = DEFAULT_ROOT_PATH+File.separator+"X3dForAdvancedModeling";  
    DEFAULT_SAVAGE_PATH  = DEFAULT_ROOT_PATH+File.separator+"Savage";
    DEFAULT_SAVAGEDEFENSE_PATH  = DEFAULT_ROOT_PATH+File.separator+"SavageDefense";
    
    basic   = new dirHandler("BasicExamples"      ,DEFAULT_BASIC_PATH); // noi18n
    conform = new dirHandler("ConformanceExamples",DEFAULT_CONFORM_PATH);
    hanim   = new dirHandler("HumanoidExamples"   ,DEFAULT_HANIM_PATH);
    vrmlsb  = new dirHandler("VrmlExamples"       ,DEFAULT_VRMLSB_PATH);
    x3d4wa  = new dirHandler("X3d4waExamples"     ,DEFAULT_X3D4WA_PATH);
    x3d4am  = new dirHandler("X3d4amExamples"     ,DEFAULT_X3D4AM_PATH);
    savage  = new dirHandler("SavageExamples"     ,DEFAULT_SAVAGE_PATH);
    
    if ((new File(DEFAULT_SAVAGEDEFENSE_PATH)).isDirectory())
    {
        savageDefenseFound = true;
        savagedefense = new dirHandler("SavageDefenseExamples", DEFAULT_SAVAGEDEFENSE_PATH);
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
        
  class dirHandler
  {
    dirHandler(String key, String dflt)
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
    return findExamplesDir(basic,menuItem);
  }
  public String findConformExamplesDirectory(Action menuItem)
  {
    return findExamplesDir(conform,menuItem);
  }
  public String findHumanoidAnimationExamplesDirectory(Action menuItem)
  {
    return findExamplesDir(hanim,menuItem);
  }
  public String findVrmlExamplesDirectory(Action menuItem)
  {
    return findExamplesDir(vrmlsb,menuItem);
  }
  public String findX3d4waExamplesDirectory(Action menuItem)
  {
    return findExamplesDir(x3d4wa,menuItem);
  }
  public String findX3d4amExamplesDirectory(Action menuItem)
  {
    return findExamplesDir(x3d4am,menuItem);
  }
  public String findSavageExamplesDirectory(Action menuItem)
  {
    return findExamplesDir(savage,menuItem);
  }
  public String findSavageDefenseExamplesDirectory(Action menuItem)
  {
      if  (isSavageDefenseFound())
           return findExamplesDir(savagedefense,menuItem);
      else return null;
  }
  
    
  public void setBasicExamplesDirectory(String s)
  {
    setExamplesDir(basic,s);
  }
  public void setConformExamplesDirectory(String s)
  {
    setExamplesDir(conform,s);
  }
  public void setHumanoidAnimationExamplesDirectory(String s)
  {
    setExamplesDir(hanim,s);
  }
  public void setVrmlExamplesDirectory(String s)
  {
    setExamplesDir(vrmlsb,s);
  }
  public void setX3d4waExamplesDirectory(String s)
  { 
    setExamplesDir(x3d4wa,s);
  }
  public void setX3d4amExamplesDirectory(String s)
  { 
    setExamplesDir(x3d4am,s);
  }
  public void setSavageExamplesDirectory(String s)
  {
    setExamplesDir(savage,s);
  }
  public void setSavageDefenseExamplesDirectory(String s)
  {
      if  (isSavageDefenseFound())
           setExamplesDir(savagedefense,s);
  }
  
  
  private String findExamplesDir(dirHandler dh, Action enabler)
  {
    dh.listeners.add(enabler);
    String ret = prefsGet(dh.prefsKey);
    if(ret != null)
      if(!isThere(ret)) {         // erased?
        prefsRemove(dh.prefsKey);
        ret = null;
      }
    if(ret == null)
      ret = findDefaultDir(dh);
    if(ret != null)
      setExamplesDir(dh,ret);
    else
      doActions(dh,false);
    return ret;    
  }
  
  private String findDefaultDir(dirHandler dh)
  {
    if(isThere(dh.defaultLoc))
      return dh.defaultLoc;
    return null;
  }
  
  private void doActions(dirHandler dh, boolean enabled)
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
  
  private void setExamplesDir(dirHandler dh,String s)
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
