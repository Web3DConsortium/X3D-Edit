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
package org.web3d.x3d.actions.qualityassurance;

//import javax.swing.JMenuItem;
import javax.swing.JMenuItem;
import org.openide.util.NbBundle;

public class SchematronValidationWithAntAction extends XmlValidationAction
{

    public void performAction()
    {
        // TODO perform XSLT invocation of X3dSchematron.xslt stylesheet instead of ant
        // to hopefully overcome run-time memory/exception issues

        performValidationAntTask("validateSceneX3dSchematron" + stylesheetExtension);
        return;
    }

    public String getName()
    {
        return NbBundle.getMessage(SchematronValidationWithAntAction.class, "CTL_SchematronValidationAction");
    }

    /**
     * Do this because this call in the super creates a new one every time, losing any 
     * previous tooltip.
     * @return what goes into the menu
     */
    @Override
    public JMenuItem getMenuPresenter()
    {
        JMenuItem mi = super.getMenuPresenter();
        mi.setToolTipText(NbBundle.getMessage(SchematronValidationWithAntAction.class, "CTL_SchematronValidationAction_tt"));
        return mi;
    }
}
