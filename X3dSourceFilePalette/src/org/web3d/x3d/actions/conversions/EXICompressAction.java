/*
 * Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
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
package org.web3d.x3d.actions.conversions;

//import com.siemens.ct.exi.CodingMode;
//import com.siemens.ct.exi.EXIFactory;
//import com.siemens.ct.exi.GrammarFactory;
//import com.siemens.ct.exi.api.sax.EXIResult;
//import com.siemens.ct.exi.exceptions.EXIException;
//import com.siemens.ct.exi.grammar.Grammar;
//import com.siemens.ct.exi.helpers.DefaultEXIFactory;
import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DEditorSupport;
import org.xml.sax.InputSource;

@ActionID(id = "org.web3d.x3d.actions.conversions.EXICompressAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_ExiAction", 
                           popupText="TODO update, currently disabled", 
                            lazy=false) // don't do lazy=false since iconBase no longer gets registered)
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Compression", position = 900),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Compression", position = 900)
})

public class EXICompressAction extends BaseConversionsAction
{
  public EXICompressAction()
  {
    setEnabled(false); // TODO
  }
  @Override
  public String transformSingleFile(X3DEditorSupport.X3dEditor xed)
  {
//    Node[] node = xed.getActivatedNodes();
//
//    X3DDataObject dob = (X3DDataObject) xed.getX3dEditorSupport().getDataObject();
//    FileObject mySrc = dob.getPrimaryFile();
//
//    File mySrcF = FileUtil.toFile(mySrc);
//    File myOutF = new File(mySrcF.getParentFile(), mySrc.getName() + ".x3d.exi");
//
//    TransformListener co = TransformListener.getInstance();
//    co.message(NbBundle.getMessage(getClass(), "EXI_compression_starting"));
//    co.message(NbBundle.getMessage(getClass(), "Saving_as_") + myOutF.getAbsolutePath());
//    co.moveToFront();
//    co.setNode(node[0]);
//
//    try {
//      JEditorPane pane = xed.getEditorPane();
//      X3DPaletteUtilities.buildJdom(pane);  // rebuild jdom tree
//
//      FileInputStream fis = new FileInputStream(mySrcF);
//      encodeWithSiemens(new InputSource(fis),myOutF.getAbsolutePath(),whichSchema(xed));
//    }
//    catch (Exception ex) {
//      co.message(NbBundle.getMessage(getClass(), "Exception:__") + ex.getLocalizedMessage());
//      return null;
//    }
//    co.message(NbBundle.getMessage(getClass(), "EXI_compression_complete"));
//    return myOutF.getAbsolutePath();
    return "";
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_ExiAction");
  }

  private void encodeWithSiemens(InputSource inputXML, String outputFile, String schemaFileName) //throws EXIException, FileNotFoundException, IOException, SAXException
  {
//    System.out.println("\n****ENCODING WITH SEIMENS TO EXI (" + inputXML + ")****");
//
//    EXIFactory exiFactory = DefaultEXIFactory.newInstance();
//    exiFactory.setCodingMode(CodingMode.COMPRESSION);
//    //Create Grammar processor
//    GrammarFactory grammarFactory = GrammarFactory.newInstance();
//    File schemaDir = XmlValidationAction.getSchemaTempDirectory();
//
//
//    File schemaPath = new File(schemaDir,schemaFileName); //"x3d-3.1.xsd");
//
//    Grammar g = grammarFactory.createGrammar(schemaPath.toURI().toASCIIString());
//    exiFactory.setGrammar(g);
//
//    FileOutputStream exiOut = new FileOutputStream(outputFile);
//    EXIResult saxResult = new EXIResult(exiOut, exiFactory);
//
//    XMLReader parser = XMLReaderFactory.createXMLReader();
//    parser.setContentHandler(saxResult.getHandler());
//    parser.parse(inputXML);
//    exiOut.flush();
//    exiOut.close();
//
//    System.out.println("\tDone Encode with Seimens!!!");
  }

  private String whichX3dSchema(X3DEditorSupport.X3dEditor xed)
  {
    org.jdom.Document doc = xed.getJdomDoc();
    org.jdom.Element x3dRootEl = doc.getRootElement();
    String version = x3dRootEl.getAttributeValue("version");
    if(version.equalsIgnoreCase("3.0"))
      return "x3d-3.0.xsd";
    else if(version.equalsIgnoreCase("3.1"))
      return "x3d-3.1.xsd";
    else if(version.equalsIgnoreCase("3.2"))
      return "x3d-3.2.xsd";
    else if(version.equalsIgnoreCase("3.3"))
      return "x3d-3.3.xsd";
    else if(version.equalsIgnoreCase("4.0"))
      return "x3d-4.0.xsd";
    //default
    else return "x3d-3.3.xsd"; // presumably unreachable; default recommended
  }

  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
    putValue("noIconInMenu", Boolean.TRUE);
  }

  /**
   * Do this because this call in the super creates a new one every time, losing any
   * previous tt.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_ExiAction_tt"));
    return mi;
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
