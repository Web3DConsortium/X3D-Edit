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
package org.web3d.x3d.dis;

import edu.nps.moves.dis7.examples.*;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

import org.web3d.x3d.options.X3dOptions;

/**
 * Action which sends out examples of all PDUs.
 */
@ActionID(id = "org.web3d.x3d.dis.DisPduSenderTestAction", category = "Window")
@ActionRegistration(displayName = "#CTL_DisPduSenderTestAction", lazy = false)
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/DIS Networking", name = "DisPduSenderTestAction", position = 300),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/DIS Networking", position = 300)
})
public class DisPduSenderTestAction extends AbstractAction
{
  public DisPduSenderTestAction()
  {
    super(NbBundle.getMessage(DisPduSenderTestAction.class, "CTL_DisPduSenderTestAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(DisPlayerRecorderTopComponent.ICON_PATH, true)));
  }

  @Override
  public void actionPerformed(ActionEvent evt)
  {
    String address = X3dOptions.getDISaddress("239.1.2.3");
    String port    = X3dOptions.getDISport("3000");
    
    AlphabeticalPduSender.main(new String[]{address, port});
  }
}

//     <!-- Runs a generator that sends many or most of the PDU types, and saves an example XML file -->
//     <target name="runGenerator" depends="jar" description="run DIS PDU generator program">
//      <java classname="edu.nps.moves.examples.PduSender">
//        <classpath>
//          <fileset dir="dist">
//            <include name="**/*.jar"/>
//          </fileset>
//          <fileset dir="lib">
//            <include name="**/*.jar"/>
//          </fileset>
//          <pathelement location="{$build}"/>
//        </classpath>
//    </java>
//    </target>