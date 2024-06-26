/*
 * Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer
 *       in the documentation and/or other materials provided with the
 *       distribution.
 *  Neither the names of the Naval Postgraduate School (NPS)
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
package org.web3d.x3d.xj3d.viewer;

import java.awt.BorderLayout;
import java.io.Serializable;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.ErrorManager;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
    dtd = "-//org.web3d.x3d.palette//Xj3dNew//EN", // TODO huh?  what is this?
    autostore = false
)
@TopComponent.Description(
    preferredID = "Xj3dTopComponent",
    //iconBase="SET/PATH/TO/ICON/HERE",
    persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "xj3dview", openAtStartup = false)
@ActionID(category = "Window", id = "org.web3d.x3d.xj3d.viewer.Xj3dTopComponent")
/* This window is opened by other actions...this not needed
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/&View Saved X3D Model", position = 110),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&View Saved X3D Model", position = 110)})
*/
@TopComponent.OpenActionRegistration(
    displayName = "#CTL_Xj3dNewAction",
    preferredID = "Xj3dTopComponent"
)
@Messages({
  "CTL_Xj3dNewAction=Xj3D Model View renders current scene",
  "CTL_Xj3dTopComponent=Xj3D Model View",
  "HINT_Xj3dTopComponent=Xj3D Model View is dockable"
})
public final class Xj3dTopComponent extends TopComponent
{

  public Xj3dTopComponent()
  {
    initComponents();
    setName(Bundle.CTL_Xj3dTopComponent());
    setToolTipText(Bundle.HINT_Xj3dTopComponent());
    putClientProperty(TopComponent.PROP_SLIDING_DISABLED, Boolean.TRUE);

    xj3dPanel = new Xj3dViewerPanel();
    xj3dPanel.initialize("Templates/Other/newScene.x3d"); // opens a blank scene
    add(xj3dPanel, BorderLayout.CENTER);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

  // These two required by @ConvertAsProperties
  void writeProperties(java.util.Properties p)
  {
    // better to version settings since initial version as advocated at
    // https://wiki.apidesign.org/wiki/PropertyFiles
    p.setProperty("version", "1.0");
    // TODO store your settings
  }

  void readProperties(java.util.Properties p)
  {
    p.getProperty("version");
    // TODO read your settings according to their version
  }
    
  private static final String              PREFERRED_ID = "Xj3dTopComponent";
  private static       Xj3dTopComponent instance;
  private        final Xj3dViewerPanel     xj3dPanel;
  private static final java.awt.Dimension  XJ3D_PANEL_MINIMUM_DIMENSIONS = new java.awt.Dimension(80, 60); // width, height in pixels

  /**
   * Gets or creates the Xj3dTopComponent.
   * @return an Xj3dTopComponent
   */
  public static synchronized Xj3dTopComponent findInstance()
  {
    TopComponent window = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
    if (window == null)
    {
      return getDefault(); // instance not found via PREFERRED_ID, so create it
    }
    if (window instanceof Xj3dTopComponent xj3dTopComponent) 
    {
      window.setMinimumSize(XJ3D_PANEL_MINIMUM_DIMENSIONS); // pane for Xj3D Model View
      return xj3dTopComponent;
    }
    ErrorManager.getDefault().log(ErrorManager.WARNING, "There seem to be multiple components with the \'" + PREFERRED_ID + "\' ID. That is a potential source of errors and unexpected behavior.");
    return getDefault();
  }

  /**
   * Gets default instance. Do not use externally/directly: reserved for *.settings files only,
   * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
   * To obtain the singleton instance, use {@link findInstance} method.
   * @return the default Xj3dTopComponent
   */
  private static synchronized Xj3dTopComponent getDefault()
  {
    if (instance == null)
    {
        instance = new Xj3dTopComponent();
        instance.setMinimumSize(XJ3D_PANEL_MINIMUM_DIMENSIONS); // pane for Xj3D Model View
    }
    return instance;
  }

  public Xj3dViewerPanel getXj3DViewerPanel()
  {
    return xj3dPanel;
  }

  /** replaces this in object stream
   * @return a resolver helper
   */
  @Override
  public Object writeReplace()
  {
    return new ResolvableHelper();
  }

  static final class ResolvableHelper implements Serializable
  {
    private static final long serialVersionUID = 1L;

    public Object readResolve()
    {
      return Xj3dTopComponent.getDefault();
    }
  }

}
