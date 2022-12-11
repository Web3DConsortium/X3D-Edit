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
      if (!javaKeyStorePath.toFile().exists()) {
          FindPath finder = new FindPath(javaKeyStorePath.toString());
          String userDirectoryName = System.getProperty("user.dir"); // might instead be x3dedit33/bin
          try {
              Files.walkFileTree(Path.of(userDirectoryName).getParent(), finder);

              // Big assumption here
              javaKeyStorePath = finder.get();
          } catch (IOException ex) {
              System.err.println(errorMessage);
              return;
          }
          
          if ((javaKeyStorePath != null) && !javaKeyStorePath.toFile().exists()) {
              System.err.println(errorMessage);
              return;
          }
      }
      
      // For module update access via https
      System.setProperty("javax.net.ssl.trustStore", javaKeyStorePath.toString());
      System.setProperty("javax.net.ssl.trustStorePassword", JAVA_X3DEDIT_KEYSTORE_PASSWORD);
  }

}
