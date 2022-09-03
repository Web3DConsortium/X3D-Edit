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

import javax.swing.text.JTextComponent;

import org.web3d.x3d.types.SceneGraphStructureNodeType;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * DOCTYPE.java
 * Created on April 12, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class DOCTYPE extends SceneGraphStructureNodeType
{
  private String version;
  
  public DOCTYPE()
  {
  }

  @Override
  public String getElementName()
  {
    return DOCTYPE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return DOCTYPECustomizer.class;
  }

  @Override
  public void initialize()
  {
    version   = DOCTYPE_ATTR_VERSION_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(DOCTYPE_ATTR_VERSION_NAME);
    if (attr != null)
      version = attr.getValue();
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D ");
    sb.append(version);
    sb.append("//EN\" \"https://www.web3d.org/specifications/x3d-");
    sb.append(version);
    sb.append(".dtd\">");
    return sb.toString();
  }

  private final String xmlHdrRegEx         = "<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>";
  private final String xmlHdr              = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  private final String dtdHdr              = "<!DOCTYPE X3D ";
  private final String dtdHdr1             = "PUBLIC ";
  private final String dtdFtr              = ">";
  
  private final String dtdNameElement0     = "\"ISO//Web3D//DTD X3D "; //3.1
  private final String dtdNameElement1     = "//EN\"";
  private final String dtdLocationElement0 = "\"https://www.web3d.org/specifications/x3d-"; // version is inserted here
  private final String dtdLocationElement1 = ".dtd\"";
  
  private final String regexPattern =
      xmlHdrRegEx+
      "\\s*" + // whitespace including cr/lf, zero or more
      dtdHdr+
      ".*"   + // any chars, 0 or more, PUBLIC/SYSTEM fits here
      "\""   + // quote
      ".*"   + // any chars, 0 or more
      "\""   + // quote
      "\\s*" + // whitespace including cr/lf, zero or more
      "\""   + // quote
      ".*"   + // any chars, 0 or more
      "\""   + // quote
      "\\s*" + // whitespace including cr/lf, zero or more
      dtdFtr;
  
  /**
   * This gets called after a good insert when things have settled down.  Here we try to make sure the DTD spec is the same as our chosen version.
   * The schema reference gets changed above, so try for the xml and dtd headers here.
   * @param jTextComponent 
   */
  @Override
  public void postInsert(JTextComponent jTextComponent)
  {
    String completeText = jTextComponent.getText();
	
	// special handling to remove duplicate DOCTYPE
	if (completeText.indexOf("<!DOCTYPE") !=     completeText.lastIndexOf("<!DOCTYPE"))
		completeText = completeText.substring(0, completeText.indexOf    ("<!DOCTYPE")) + // snip
				       completeText.substring(   completeText.lastIndexOf("<!DOCTYPE"));

    String s = completeText;
    int size = completeText.length();
    
    if(size > 255) {
      // operate on a portion of the whole thing
      s = s.substring(0, 256);
      size = 255;
    }
    
    String newHeader = xmlHdr+linesep+dtdHdr+dtdHdr1;  // first part of new header
    
    version = getVersion();
    if (version.equals("3.0") || version.equals("3.1") || version.equals("3.2") || version.equals("3.3") ||
	    version.equals("4.0") || version.equals("4.1"))
      ; // nop
    else {
      System.err.println("Bad X3D version='" + version + "' found by DOCTYPE.java: must be 3.0, 3.1, 3.2, 3.3, 4.0 or 4.1");
      return;
    }

    newHeader = newHeader+dtdNameElement0+version+dtdNameElement1 + " " + dtdLocationElement0+version+dtdLocationElement1+dtdFtr;
    
    // Quickly find out if we matched; if not, oddball existing header maybe...leave it alone
    String[] sa = s.split(regexPattern);
    if(sa == null || sa.length<2) {
      System.err.println("No DTD header match found by DOCTYPE.java");
      return;
    }
    
    // Do the replace
    jTextComponent.setText(completeText.replaceFirst(regexPattern, newHeader));
  }

  public String getVersion()
  {
    return version;
  }

  public void setVersion(String newVersion)
  {
    version = newVersion;
  }
}
