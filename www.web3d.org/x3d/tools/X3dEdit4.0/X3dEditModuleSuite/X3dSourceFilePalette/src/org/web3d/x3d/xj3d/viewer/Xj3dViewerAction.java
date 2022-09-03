/*
 * Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
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
 *       (http://www.nps.edu and https://MovesInstitute.nps.edu)
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

import org.openide.util.NbBundle;

/**
 * Action which shows Xj3dTopComponent.
 */
@ActionID(id = "org.web3d.x3d.xj3d.viewer.Xj3dViewerAction", category = "Window")

@ActionRegistration(iconBase = "org/web3d/x3d/resources/xj3d.png",
        displayName = "#CTL_Xj3dViewerAction",
        lazy = true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
    @ActionReference(path = "Menu/X3D-Edit/Author Workflow", position = 20),
    @ActionReference(path = "Toolbars/Author Workflow", position = 20),
    @ActionReference(path = "Editors/model/x3d+xml/Popup/Author Workflow", position = 20),
    @ActionReference(path = "Menu/X3D-Edit/View Saved Scene", position = 20),
    @ActionReference(path = "Editors/model/x3d+xml/Popup/View Saved Scene", position = 20),
    @ActionReference(path = "Shortcuts", name = "CS-X")
})
@NbBundle.Messages("CTL_Xj3dViewerAction=Xj3D internal pane to render saved model")

/**
 * Sets the Xj3D JFrame visible to view X3D scenes
 */
public final class Xj3dViewerAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent evt) {
        
        Xj3dTopComponent xj3dTopComponent = Xj3dTopComponent.findInstance();

        // Makes the Xj3d component visible
        if (!xj3dTopComponent.isOpened()) {
             xj3dTopComponent.open();
             xj3dTopComponent.requestActive();
        }
    }
}
