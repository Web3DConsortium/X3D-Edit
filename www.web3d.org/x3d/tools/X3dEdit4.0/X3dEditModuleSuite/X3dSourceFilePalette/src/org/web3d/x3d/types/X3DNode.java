/*
Copyright (c) 2007-2021 held by the author(s) .  All rights reserved.

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
package org.web3d.x3d.types;

import java.util.HashMap;
import org.web3d.x3d.palette.items.BaseX3DElement;
import org.web3d.x3d.sai.*;

/**
 * X3DNode.java
 * Created on August 2, 2007, 10:05 AM
 *
 * Root marker interface for
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public abstract class X3DNode extends BaseX3DElement
{
  abstract public String getDefaultContainerField();
  
  @Override
  abstract public String getElementName();

  /*
  Immersive components:   Supported components:
                          CADGeometry
  Core                    Core
                          DIS
  EnvironmentalEffects    EnvironmentalEffects
  EnvironmentalSensor     EnvironmentalSensor
  EventUtilities          EventUtilities
  Geometry2D              Geometry2D
  Geometry3D              Geometry3D
                          Geospatial
  Grouping                Grouping
                          H-Anim
  Interpolation           Interpolation
  KeyDeviceSensor         KeyDeviceSensor
                          Layering
  Lighting                Lighting
  Navigation              Navigation
  Networking              Networking
                          ParticleSystems
                          PickingSensor
  PointingDeviceSensor    PointingDeviceSensor
  Rendering               Rendering
                          RigidBodyPhysics
  Scripting               Scripting
  Shape                   Shape
  Sound                   Sound
  Text                    Text
  Texturing               Texturing
  Time                    Time
  */
private static X3DScene scene;
private static ExternalBrowser browser;
private static X3DComponent component;

  /** Utility method to aid in return of field definitions */
  private static void createEmptyScene() {
    component = BrowserFactory.createX3DComponent(new HashMap<String, Object>());
    browser = component.getBrowser();
    scene = browser.createScene(null, browser.getSupportedComponents());
  }

  private static String tmpName = "__tempNode_";                 //noi18n

  public static X3DFieldDefinition[] getNodeFields(String x3dNodeName)
  {
    createEmptyScene();
 
    X3DFieldDefinition[] defs = new X3DFieldDefinition[0];  // empty by default
    synchronized (scene) {
      try {
        org.web3d.x3d.sai.X3DNode n = scene.createNode(x3dNodeName);
        scene.updateNamedNode(tmpName, n);
        defs = n.getFieldDefinitions();
        scene.removeNamedNode(tmpName);
      }
      catch (InvalidNodeException ex) {
          System.err.println(x3dNodeName + " not found by Xj3D");
      } finally {
        browser.dispose();
        component.shutdown();
 
        // Cleanup
        browser = null;
        component = null;
        scene = null;
      }
    }
    return defs;
  }
}
