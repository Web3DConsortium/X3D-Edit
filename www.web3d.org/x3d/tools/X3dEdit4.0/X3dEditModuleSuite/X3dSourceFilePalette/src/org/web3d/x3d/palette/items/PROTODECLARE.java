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

package org.web3d.x3d.palette.items;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import org.jdom.Content;
import org.jdom.Element;
import org.jdom.Attribute;
import javax.swing.text.JTextComponent;
import javax.xml.transform.Result;
import org.jdom.Document;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;
import org.jdom.output.XMLOutputter;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.palette.X3DXMLOutputter;
import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * PROTODECLARE.java
 * Created on Mar 9, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class PROTODECLARE extends X3DChildNode
{
  private PROTOINTERFACE protoInterface = new PROTOINTERFACE();
  private PROTOBODY protoBody    = new PROTOBODY();
  private String name            = "";
  private String appinfo         = PROTODECLARE_ATTR_APPINFO_DFLT;
  private String documentation   = PROTODECLARE_ATTR_DOC_DFLT;
  private String appendedContent;
  
  public PROTODECLARE()
  {
  }

  @Override
  public void initialize()
  {
    protoInterface.setFieldsComment( // TODO:  not getting thru  :(   (try tracing in debug mode)
            "<!-- field definitions (if any) go here -->");
    protoBody.setContent(
            linesep + "    <!-- First node determines node type of this prototype -->" + 
            linesep + "    <Group DEF='PrototypeRootNode'/>" + 
            linesep + "    <!-- Subsequent nodes do not render, but still must be a valid X3D subgraph -->" + linesep);
    
    name          = PROTODECLARE_ATTR_NAME_DFLT;
    appinfo       = PROTODECLARE_ATTR_APPINFO_DFLT;
    documentation = PROTODECLARE_ATTR_DOC_DFLT;
  }

  private List<Content> contentLis;
  private Element root;
  
  @Override
  @SuppressWarnings("unchecked")
  public void initializeFromJdom(org.jdom.Element docRoot, JTextComponent comp)
  {
    super.initializeFromJdom(docRoot, comp);
    root = docRoot; 
    Attribute attr = root.getAttribute(PROTODECLARE_ATTR_NAME_NAME);
    if(attr != null)
      name = attr.getValue();
    attr = root.getAttribute(PROTODECLARE_ATTR_APPINFO_NAME);
    if(attr != null)
      appinfo = attr.getValue();
    attr = root.getAttribute(PROTODECLARE_ATTR_DOC_NAME);
    if(attr != null)
      documentation = attr.getValue();
    
    contentLis = (List<Content>) root.getContent();   // suppress cast warning
    Element pInterface = null;
    Element pBody = null;
    int pIFidx = -1;
    int pBidx = -1;
    
    if (contentLis.size() > 0) {
      int i=0;
      for (Content cnt : contentLis) {
        if (cnt instanceof Element) {
          Element el = (Element) cnt;
          if (el.getName().equals("ProtoInterface")) {
            pInterface = el; // todo remove
            pIFidx = i;
          }
          else if (el.getName().equals("ProtoBody")) {
            pBody = el; // todo remove
            pBidx = i;
          }
        }
        i++;
      }
    }
    if (pInterface != null) {
      root.removeContent(pInterface);
      pIFText = new Text("");
      root.addContent(pIFidx, pIFText);
      protoInterface = new PROTOINTERFACE();
      protoInterface.initializeFromJdom(new Document(pInterface).getRootElement(), comp);
    }
    if (pBody != null) {
      root.removeContent(pBody);
      pBText = new Text("");
      root.addContent(pBidx, pBText);
      protoBody = new PROTOBODY();
      protoBody.initializeFromJdom(new Document(pBody).getRootElement(), comp);
    }

  }
  Text pIFText;
  Text pBText;
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (PROTODECLARE_ATTR_NAME_REQD || !name.equals(PROTODECLARE_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(PROTODECLARE_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (PROTODECLARE_ATTR_APPINFO_REQD || !appinfo.equals(PROTODECLARE_ATTR_APPINFO_DFLT)) {
      sb.append(" ");
      sb.append(PROTODECLARE_ATTR_APPINFO_NAME);
      sb.append("='");
      sb.append(appinfo);
      sb.append("'");
    }
    if (PROTODECLARE_ATTR_DOC_REQD || !documentation.equals(PROTODECLARE_ATTR_DOC_DFLT)) {
      sb.append(" ");
      sb.append(PROTODECLARE_ATTR_DOC_NAME);
      sb.append("='");
      sb.append(documentation);
      sb.append("'");
    }
    return sb.toString();
  }

  @Override
  public String getElementName()
  {
    return PROTODECLARE_ELNAME;
  }
  
  public PROTOINTERFACE getProtoInterface()
  {
    return protoInterface;
  }
  
  public PROTOBODY getProtoBody()
  {
    return protoBody;
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String nm)
  {
    name=nm;
  }
  
  @Override
  public String getContent()
  {
    if(pIFText == null)
      pIFText = new Text("");
    pIFText.setText(protoInterface.createBody().trim());

    if(pBText == null)
      pBText = new Text("");
    pBText.setText(protoBody.createBody().trim());
    
    if(root == null)
      return linesep + pIFText.getTextTrim() + linesep + pBText.getTextTrim() +linesep;
    
    // This is the important one:
    root.addContent(0,new ProcessingInstruction(Result.PI_DISABLE_OUTPUT_ESCAPING,""));
    try {
      StringWriter sw = new StringWriter();
      XMLOutputter xout = new X3DXMLOutputter();
      xout.outputElementContent(root, sw);
      return sw.toString();
    }
    catch (IOException ex) {
      System.err.println("error PROTODECLARE.getContent(): "+ex.getLocalizedMessage());
      return "";
    }
  }
  
  public String getAppinfo()
  {
    return appinfo;
  }
  
  public void setAppinfo(String appinfo)
  {
    this.appinfo = appinfo;
  }
  
  // TODO add button to launch link in browser
  public String getDocumentation()
  {
    return documentation;
  }
  
  public void setDocumentation(String documentation)
  {
    this.documentation = documentation;
  }

    /**
     * @return the appendedContent for serialization output following the ProtoDeclare
     */
    public String getAppendedContent() {
        if (appendedContent == null) return "";
        else                         return appendedContent;
    }

    /**
     * @param appendedContent the appendedContent to set for serialization output following the ProtoDeclare
     */
    public void setAppendedContent(String appendedContent) {
        this.appendedContent = appendedContent;
    }
  
}
