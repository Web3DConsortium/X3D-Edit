/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.

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
package org.web3d.x3d.xj3d.viewer;

// External imports
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import org.j3d.util.I18nManager;

import org.openide.ErrorManager;

import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import org.web3d.vrml.scripting.external.sai.SAIBrowser;

import org.web3d.x3d.X3DDataObject;

import org.web3d.x3d.sai.BrowserEvent;
import org.web3d.x3d.sai.BrowserFactory;
import org.web3d.x3d.sai.BrowserListener;
import org.web3d.x3d.sai.InvalidBrowserException;
import org.web3d.x3d.sai.InvalidX3DException;
import org.web3d.x3d.sai.X3DComponent;
import org.web3d.x3d.sai.X3DScene;

import org.xj3d.sai.Xj3DBrowser;

// Local imports
// None

/**
 * Xj3dViewerPanel.java
 * Created on Sep 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id: Xj3dViewerPanel.java 32240 2021-06-17 16:47:44Z brutzman $
 */
public class Xj3dViewerPanel extends JPanel implements BrowserListener
{
    /** Property describing positional dead reckoning */
    protected static final String DEADRECKON_POSITION_PROPERTY = "org.web3d.vrml.renderer.common.dis.input.deadreckonPosition";

    /** Property describing rotational dead reckoning */
    protected static final String DEADRECKON_ROTATION_PROPERTY = "org.web3d.vrml.renderer.common.dis.input.deadreckonRotation";
    
  /** Name of the application that we'll default to */
  private static final String APP_NAME = Xj3dViewerPanel.class.getSimpleName();

  /** The location of the resource property bundle in the classpath */
  private static final String CONFIG_FILE = "config.i18n.xj3dResources";

  private Xj3DBrowser xj3dBrowser;
  private X3DDataObject currentX3dDataObject;
  private X3DComponent x3dComponent;
  private X3DScene currentX3dScene;
  String currentFileName;
  private File currentFile;
  private final VisibilityListener listener;
  private final Map<String, Object> requestedParameters;

  /** Default constructor */
  public Xj3dViewerPanel()
  {
    /* Enable messages from the I18nManager.  NOTE: By default, it is first
     * set in org.xj3d.ui.awt.browser.ogl.X3DOGLBrowserFactoryImpl.
     * We are re-setting here to override which properties we want
     * available from the I18nManager.
     */
    I18nManager i18nManager = I18nManager.getManager();
    i18nManager.setApplication(APP_NAME, CONFIG_FILE);
    listener = new VisibilityListener();
    
    System.setProperty("org.xj3d.core.eventmodel.OriginManager.enabled", "true");
    System.setProperty("org.xj3d.core.eventmodel.DefaultOriginManager.elevation_threshold", "50000"); // redundant, = default
    System.setProperty("org.xj3d.core.eventmodel.DefaultOriginManager.distance_threshold",  "50000"); // ditto
    
    // Turn off rotation dead reckoning
    System.setProperty(DEADRECKON_ROTATION_PROPERTY, "false");

    // Turn off position dead reckoning.  This helps with recorded packet
    // single stepping
    System.setProperty(DEADRECKON_POSITION_PROPERTY, "false");

    // Beef up the # of content thread loaders (for GeoSpatial LODs)
    System.setProperty("org.xj3d.core.loading.threads", "4");

    // Setup browser parameters
    requestedParameters=new HashMap<>();

    // Also see X3DLoader.java in the Browser examples of NPS Xj3D Git repo
    // n.b. these parameters are also created (with some differences) in the 
    // Xj3D external pane to render saved modeler xj3d.browser.Xj3DBrowser

    // Xj3D initialization parameters, set for highest rendering quality
    // http://www.xj3d.org/tutorials/xj3d_application.html
    // https://www.web3d.org/files/specifications/19775-1/V3.2/Part01/components/networking.html#t-BrowserProperties

    // Using JOGL's NEWT (Native Windowing Toolkit) is the best for performance.
    // However, the NEWT is not allowing mouse and keyboard events to get
    // through on Windows, so, next best performer is the AWT GLCanvas parented
    // by a JPanel (swing). newt | swing | swing-lightweight
    requestedParameters.put("Xj3D_InterfaceType", "swing");
    requestedParameters.put("Xj3D_FPSShown", Boolean.TRUE);
    requestedParameters.put("Xj3D_LocationShown", Boolean.FALSE);
    requestedParameters.put("Xj3D_LocationPosition", "top");
    requestedParameters.put("Xj3D_LocationReadOnly", Boolean.FALSE);
    requestedParameters.put("Xj3D_OpenButtonShown", Boolean.FALSE);
    requestedParameters.put("Xj3D_ReloadButtonShown", Boolean.FALSE);
    requestedParameters.put("Xj3D_ShowConsole", Boolean.FALSE);
    requestedParameters.put("Xj3D_StatusBarShown", Boolean.TRUE);
    requestedParameters.put("Xj3D_AntialiasingQuality", "high");
    requestedParameters.put("Antialiased", Boolean.TRUE);
    requestedParameters.put("PrimitiveQuality", "high");
    requestedParameters.put("TextureQuality", "high");
    requestedParameters.put("Shading", Boolean.TRUE);
    // Xj3D anisotropicDegree 16 derived from TextureQuality high in BrowserComposite.java

    Xj3dViewerPanel.this.setLayout(new BorderLayout());
  }

  //----------------------------------------------------------
  // Method defined by BrowserListener
  //----------------------------------------------------------

  @Override
  public void browserChanged(BrowserEvent ev)
  {
    switch(ev.getID()) {
      case BrowserEvent.INITIALIZED:
        // If we don't get this after a replace scene, xj3d broken
        //System.out.println("World Initialized");
        break;
      case BrowserEvent.URL_ERROR:
        //System.out.println("Error loading world");
        break;
      default:
    }
    System.out.flush();

  }

  /** Creates a new Xj3DBrowser instance */
  private void initialize()
  {
      // Create an SAI component
      x3dComponent = BrowserFactory.createX3DComponent(requestedParameters);
      add((Component) x3dComponent, BorderLayout.CENTER);

      // Get an external browser
      xj3dBrowser = (Xj3DBrowser) x3dComponent.getBrowser();
      xj3dBrowser.addBrowserListener(Xj3dViewerPanel.this);
      xj3dBrowser.setMinimumFrameInterval(1000 / 30);  // 30+ fps

      addComponentListener(listener);
  }

  /** Entered from MaterialCustomizer in a separate instance from the main editor
   *
   * @param jarredScene a default scene stored in a classpath jar
   */
  public void initialize(String jarredScene)
  {
    if (x3dComponent == null)
        initialize();
    
    try {

      // This path is setup to be resolved in the X3D layer.xml file.
      FileObject jarredFile = FileUtil.getConfigRoot().getFileSystem().findResource(jarredScene);
      currentFileName = jarredFile.getNameExt();
      
      String path = jarredFile.getParent().getPath();
      
      // TODO: The path is incorrect and will fail on relative paths for texture 
      // image loading b/c it points to a virtual directory. Will work for any
      // other non-texture mapping scene, i.e. Templates/MaterialExample.x3d
      openXj3dScene(path, jarredFile.getInputStream());
    } catch (IOException ex) {
      ErrorManager.getDefault().log(ErrorManager.ERROR, ex.getMessage());
    }
  }

  /**
   * @return the x3dComponent
   */
  public X3DComponent getX3dComponent() {
      return x3dComponent;
  }

  /**
   * @return an X3DScene instance used by the MaterialCustomizer
   */
  public X3DScene getScene()
  {
    return currentX3dScene;
  }
  
  /** Loads and X3D scene from given path
   * 
   * @param path the canonical path to scene on the local file system
   */
  public void openXj3dScene(String path) {
      currentFile = new File(path);
      currentFileName = currentFile.getName();
      currentX3dScene = xj3dBrowser.createX3DFromURL(new String[]{currentFile.getPath()});
      loadScene(false);
  }
  
  /** Called at initial startup when a scene is loaded into the editor, or when
   * a call to reload the scene is fired via action item
   * 
   * @param xObj the X3DDataObject to extract a scene file from
   */
  public void openXj3dScene(X3DDataObject xObj)
  {
      // This has been happening for some reason
      if (xObj == null) {return;}

      //System.out.println("XJ3D:.....openXj3dScene(xObj)");
      currentX3dDataObject = xObj;
      try {
          currentFile = FileUtil.toFile(xObj.getPrimaryFile());
          currentFileName = currentFile.getName();
          String path = currentFile.getParentFile().toURI().toURL().toExternalForm();
          openXj3dScene(path, xObj.getPrimaryFile().getInputStream());
      } catch (IOException ex) {
          ErrorManager.getDefault().log(ErrorManager.EXCEPTION, ex.getMessage());
      }
  }
  
  /** Return the current X3DDataObject
   * @return the current X3DDataObject
   */
  public X3DDataObject getCurrentX3DDataObject() {
      return currentX3dDataObject;
  }
  
  /** Loads and X3D scene from an InputStream and resolves content loading info
   * from the parentPath
   * @param parentPath the path to resolve content loading from
   * @param is the InputStream to the content resource
   */
  public void openXj3dScene(String parentPath, InputStream is)
  {
      try {
        currentX3dScene = xj3dBrowser.createX3DFromStream(parentPath, is);
      }
      catch(InvalidBrowserException | InvalidX3DException | IOException ex) {
       ErrorManager.getDefault().log(ErrorManager.EXCEPTION, ex.getMessage());
      }
      loadScene(true);
  }
  
  /** Called when a scene has been modified/saved. Will reload and render scene
   * modifications.
   */
  public void refreshXj3dScene()
  {
    openXj3dScene(currentX3dDataObject);
  }

  /**
   * When you close one of multiple open tabs, the next tab opens before
   * the old one gets closed.  In this case, just return.  The last tab to
   * close will load a blank scene into Xj3D
   * 
   * @param passedX3DDataObject the x3d scene to render
   */
  public void closeXj3dScene(X3DDataObject passedX3DDataObject)
  { 
    //System.out.println("getClass().getName() + ".closeXj3dScene()");
    if(currentX3dDataObject == null || currentX3dDataObject != passedX3DDataObject)
      return;

    // Put up the empty one
    initialize("Templates/Other/newScene.x3d");
    
    currentX3dDataObject = null;
    currentFile = null;
    currentFileName = null;
    currentX3dScene = null;  
  }

  /** Properly dispose of Xj3D OpenGL (JOGL) resources. Should only be called
   *  by Xj3DShutdown via @OnStop hook.
   */
  public void shutDownXj3D() {
      removeComponentListener(listener);
      xj3dBrowser.removeBrowserListener(Xj3dViewerPanel.this);
      x3dComponent.shutdown();
      remove((Component) x3dComponent);
      x3dComponent = null;
  }

  private void loadScene(boolean stream) {
    String msg;
    xj3dBrowser.beginUpdate();
    xj3dBrowser.replaceWorld(currentX3dScene);
    xj3dBrowser.endUpdate();
    if (stream) {
      msg = "openxj3dScene loaded (stream): "  + currentFileName;
    } else {
      msg = "openxj3dScene loaded (file): "  + currentFileName;
    }
    System.out.println(msg);
  }

  class VisibilityListener extends ComponentAdapter
  {
    @Override
    public void componentShown(ComponentEvent e)
    {
      if(Xj3dViewerPanel.this.xj3dBrowser instanceof SAIBrowser)
        xj3dBrowser.setMinimumFrameInterval(40);
    }

    @Override
    public void componentHidden(ComponentEvent e)
    {
      if(Xj3dViewerPanel.this.xj3dBrowser instanceof SAIBrowser)
        xj3dBrowser.setMinimumFrameInterval(1000);
    }
  }

}
