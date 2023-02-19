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

package org.web3d.x3d.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.openide.modules.ModuleInstall;
import org.openide.modules.OnStart;
import org.web3d.x3d.config.util.FindPath;

/**
 * Find local keystore that we have previously created for Java and X3D-Edit so that X3D-Edit can 
 * trust plugins to X3D-Edit Trust Center
 * <p>
 KeystoreLocator.java
 Created on Dec 21, 2007

 MOVES Institute
 Naval Postgraduate School, Monterey, CA, USA
 www.nps.edu
 </p>
 * @author Mike Bailey <jmbailey@nps.edu>
 * @version $Id$
 */
@OnStart
public class KeystoreLocator extends ModuleInstall implements Runnable
{
  /** Name of our keystore file */
  private static final String JAVA_X3DEDIT_KEYSTORE_NAME     = "server-cert-keystore.jks";
  private static final String JAVA_X3DEDIT_KEYSTORE_PASSWORD = "changeit";
  
  /** Default constructor */
  public void Installer() {
  }
  
    @Override
    public void run()
    {   
        Path javaKeyStorePath = Path.of(JAVA_X3DEDIT_KEYSTORE_NAME);
        String errorMessage = "WARNING: cannot find Path for: " + javaKeyStorePath.toString();

        // Assuming not null here
        if (!javaKeyStorePath.toFile().exists())
        {
            FindPath finder = new FindPath(javaKeyStorePath.toString());
            String userDirectoryName = System.getProperty("user.dir"); // might instead be x3dedit33/bin
            try {
                Path pathOfUserDirParent = Path.of(userDirectoryName).getParent();
                if (pathOfUserDirParent == null)
                    return; // getting NPE duirng IDE plugin install
                Files.walkFileTree(pathOfUserDirParent, finder);

                // Big assumption here
                javaKeyStorePath = finder.get();
            }
            catch (IOException ex) {
                System.err.println(errorMessage);
                return;
            }
            if ((javaKeyStorePath == null) || !javaKeyStorePath.toFile().exists()) {
                System.err.println(errorMessage);
                return;
            }
        }
        if (javaKeyStorePath.toFile().exists())
        {
            // For module update access via https
            System.setProperty("javax.net.ssl.trustStore", javaKeyStorePath.toString());
            System.setProperty("javax.net.ssl.trustStorePassword", JAVA_X3DEDIT_KEYSTORE_PASSWORD);
        }
  }
}
