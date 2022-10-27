package org.web3d.x3d.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openide.modules.ModuleInstall;
import org.openide.modules.OnStart;

import org.web3d.x3d.config.util.FindPath;

/**
 * Set stuff up so that X3D-Edit can do it's thing
 * <p>
 * Installer.java
 * Created on Dec 21, 2007
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * </p>
 * @author Mike Bailey <jmbailey@nps.edu>
 * @version $Id$
 */
@OnStart
public class Installer extends ModuleInstall implements Runnable
{
  /** Name of our keystore file */
  private static final String JKS_NAME = "java-dod-stat-cacerts.jks";
  private static final String JKS_PWORD = "changeit";
  
  /** Default constructor */
  public void Installer() {
  }
  
  @Override
  public void run()
  {   
      Path p = Path.of(JKS_NAME);
      String msg = "WARN: Can not find Path for: " + p.toString();
       
      // Assuming not null here
      if (!p.toFile().exists()) {
          FindPath finder = new FindPath(p.toString());
          String usrDir = System.getProperty("user.dir"); // might be x3dedit33/bin
          try {
              Files.walkFileTree(Path.of(usrDir).getParent(), finder);

              // Big assumption here
              p = finder.get();
          } catch (IOException ex) {
              System.err.println(msg);
              return;
          }
          
          if (!p.toFile().exists()) {
              System.err.println(msg);
              return;
          }
      }
      
      // For module update access via https
      System.setProperty("javax.net.ssl.trustStore", p.toString());
      System.setProperty("javax.net.ssl.trustStorePassword", JKS_PWORD);
  }

}
