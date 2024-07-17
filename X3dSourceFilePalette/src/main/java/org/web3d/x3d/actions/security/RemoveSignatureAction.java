/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
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
/**
 * RemoveSignatureAction.java
 * Created July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
package org.web3d.x3d.actions.security;

import java.awt.HeadlessException;
import java.io.IOException;
import java.util.MissingResourceException;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.w3c.dom.Document;
import org.web3d.x3d.BaseX3DEditAction;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom.ElementLocation;

@ActionID(id = "org.web3d.x3d.actions.security.RemoveSignatureAction", category = "X3D-Edit")
// https://commons.wikimedia.org/wiki/File:Eraser-307519.svg
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/235px-Eraser-307519.svg32x27.png",
                    displayName = "#CTL_RemoveSignatureAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/XML &Security", position = 1100),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/XML &Security", position = 1100)})

public final class RemoveSignatureAction extends BaseX3DEditAction
{
  @Override
  protected void doWork(Node[] activatedNodes)
  {
      try {
          Document w3cDoc = getW3cDocument();
          if (w3cDoc == null) {
            String msg = NbBundle.getMessage(getClass(), "MSG_EncryptedSignatureRemoval"); //"Can not remove encrypted signature"
            NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
            DialogDisplayer.getDefault().notify(nd);
            return; // can happen when attempting to remove sig from an encrypted document
          } 
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException ex) {
          Exceptions.printStackTrace(ex);
      }
    try {     
     ElementLocation signatureLoc = X3DPaletteUtilitiesJdom.findNamedElement(
         documentEditorPane,
         org.apache.xml.security.utils.Constants._TAG_SIGNATURE,
         org.apache.xml.security.utils.Constants.SignatureSpecNS
     );
     
     if(signatureLoc == null) {
       JOptionPane.showMessageDialog(null, NbBundle.getMessage(getClass(), "MSG_NoSignatureInFile"));//"No signature element found in file");
       return;
     }
     if(JOptionPane.showConfirmDialog(
         null,
         NbBundle.getMessage(getClass(),"MSG_ConfirmRemoveSignature"),//"Are you sure you want to delete the signature element?",
         NbBundle.getMessage(getClass(),"ConfirmRemoveSignatureTitle"),//"Signature Deletion", 
         JOptionPane.YES_NO_OPTION)
           != JOptionPane.YES_OPTION)
       return;
           
     deleteLocatedElement(signatureLoc);
     
     // remove ds namespace
     X3DPaletteUtilitiesJdom.buildJdom(documentEditorPane);
     ElementLocation rootLoc = X3DPaletteUtilitiesJdom.findNamedElement(documentEditorPane, "X3D", "");
     if(rootLoc != null) {
       org.jdom.Element elm    = rootLoc.element;
       org.jdom.Namespace nmsp = elm.getNamespace("ds");
       if (nmsp == null)
           if (elm.getAdditionalNamespaces().contains("ds")) {
               int ix = elm.getAdditionalNamespaces().indexOf("ds");
               nmsp = (Namespace) elm.getAdditionalNamespaces().get(ix);
           }
       
       elm.removeNamespaceDeclaration(nmsp);
       
       String newElem =  new XMLOutputter().outputString(elm);
       replaceSelectedElement(rootLoc,newElem);
     }
    }
    catch(HeadlessException | IOException | MissingResourceException | BadLocationException | JDOMException ex) {  //todo, specific msgs for spec exceptions
      throw new RuntimeException(ex); // let baseclass handle
    }            
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_RemoveSignatureAction");
  }

  @Override
  protected Class<?>[] cookieClasses()
  {
    return new Class<?>[]{DataObject.class};
  }

  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
    putValue("noIconInMenu", Boolean.TRUE);
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }
}

