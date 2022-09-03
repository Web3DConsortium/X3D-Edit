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

/**
 * CommandExecutionScripts.java
 * Created on Apr 17, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey <jmbailey@nps.edu>
 * @version $Id$
 */
public class CommandExecutionScripts
{
  // Public interface
  /**
   * @return String array to use with ProcessBuilder to launch a command
   */
  public static String[] getCommandExecutionScript()
  {
    String[] sa = new String[commandExecutionStringArray.length];
    System.arraycopy(commandExecutionStringArray,0,sa,0,sa.length);
    return sa;
  }
  
  /** 
   * @return index into array returned by {@link #getCommandExecutionScript() getCommandExecutionScript} method
   * at which to stuff the command name/path.
   */
  public static int getExecutablePathIndex()
  {
    return PUTEXECUTABLEPATHHERE;
  }
  
  /** 
   * @return index into array returned by {@link #getCommandExecutionScript() getCommandExecutionScript} method
   * at which to stuff the argument name/path.
   */
  public static int getArgumentPathIndex()
  {
    return PUTTARGETFILEHERE;
  }
  
  // Implementation
  private static final String[] winxpExecutionStringArray = {
    "cmd",
    "/c",
    "path to executable from Preferences",
    "content filepath"
  };
  private static final String[] macosXExecutionStringArray = {
    "open",
    "-a",
    "path to executable from Preferences",
    "content filepath"
  };
  
  private static final String[] defaultExecutionStringArray = {
    "path to executable from Preferences",
    "content filepath"
  };

  private static final String[]   commandExecutionStringArray;
  private static final int        PUTEXECUTABLEPATHHERE;
  private static final int        PUTTARGETFILEHERE;
  
  static {
    String os = System.getProperty("os.name");
    if(os.equals("Windows XP") || os.equals("Windows 7") || os.contains("Windows")) {
      commandExecutionStringArray = winxpExecutionStringArray;
      PUTEXECUTABLEPATHHERE = 2;
      PUTTARGETFILEHERE     = 3;
    }
    else if(os.equals("Mac OS X") || os.contains("Mac")) {     
      commandExecutionStringArray = macosXExecutionStringArray;
      PUTEXECUTABLEPATHHERE = 2;
      PUTTARGETFILEHERE     = 3;
    }
    else {
      commandExecutionStringArray = defaultExecutionStringArray;
      PUTEXECUTABLEPATHHERE = 0;
      PUTTARGETFILEHERE     = 1;
    }
  }
  
}
