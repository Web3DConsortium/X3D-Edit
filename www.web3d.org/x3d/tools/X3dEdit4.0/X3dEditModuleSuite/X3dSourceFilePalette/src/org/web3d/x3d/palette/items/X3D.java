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
 * X3D.java
 * Created on March 10, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class X3D extends SceneGraphStructureNodeType
{
  private String profile;
  private String version;
  private String xmlns_xsd;
  private String xsd_noNamespaceSchemaLocation;

  private final String defaultContent =
      linesep   +
      "<head>"  +
      linesep   +
      "<!-- meta nodes are added here -->"+
      linesep   +
     "</head>"  +
      linesep   +
      "<Scene>" +
      linesep   +
      "<!-- Scene graph nodes are added here -->"+
      linesep   +
      "</Scene>"+
      linesep;
  
  public X3D()
  {
  }

  @Override
  public String getElementName()
  {
    return X3D_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return X3DCustomizer.class;
  }

  @Override
  public void initialize()
  {
    profile   = X3D_ATTR_PROFILE_DFLT;
    version   = X3D_ATTR_VERSION_DFLT;
    xmlns_xsd = X3D_ATTR_XMLNSXSD_DFLT;
    xsd_noNamespaceSchemaLocation = X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_DFLT;

    setContent(defaultContent);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(X3D_ATTR_PROFILE_NAME);
    if (attr != null)
      profile = attr.getValue();
    attr = root.getAttribute(X3D_ATTR_VERSION_NAME);
    if (attr != null)
      version = attr.getValue();
    attr = root.getAttribute(X3D_ATTR_XMLNSXSD_NAME);
    if (attr != null)
      xmlns_xsd = attr.getValue();
    attr = root.getAttribute(X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_NAME);
    if (attr != null)
      xsd_noNamespaceSchemaLocation = attr.getValue();
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (X3D_ATTR_PROFILE_REQD || (!profile.equals(X3D_ATTR_PROFILE_DFLT))) {
      sb.append(" ");
      sb.append(X3D_ATTR_PROFILE_NAME);
      sb.append("='");
      sb.append(profile);
      sb.append("'");
    }
    if (X3D_ATTR_VERSION_REQD || (!version.equals(X3D_ATTR_VERSION_DFLT))) {
      sb.append(" ");
      sb.append(X3D_ATTR_VERSION_NAME);
      sb.append("='");
      sb.append(version);
      sb.append("'");
    }
    if (X3D_ATTR_XMLNSXSD_REQD || (!xmlns_xsd.equals(X3D_ATTR_XMLNSXSD_DFLT))) {
      sb.append(" ");
      sb.append(X3D_ATTR_XMLNSXSD_NAME);
      sb.append("='");
      sb.append(xmlns_xsd);
      sb.append("'");
    }
    if (X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_REQD || (!xsd_noNamespaceSchemaLocation.equals(X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_DFLT))) {
      sb.append(" ");
      sb.append(X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_NAME);
      sb.append("='");
      sb.append(xsd_noNamespaceSchemaLocation);
      sb.append("'");
    }
    return sb.toString();
  }

  private final String xmlHdrRegEx         = "<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>";
  private final String xmlHdr              = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  private final String dtdHeader              = "<!DOCTYPE X3D ";
  private final String dtdHdr1             = "PUBLIC ";
  private final String dtdFooter              = ">";
  
  private final String dtdNameElement0     = "\"ISO//Web3D//DTD X3D "; //3.1
  private final String dtdNameElement1     = "//EN\"";
  private final String dtdLocationElement0 = "\"https://www.web3d.org/specifications/x3d-"; //"3.1
  private final String dtdLocationElement1 = ".dtd\"";
  
  private final String regexPattern =
      xmlHdrRegEx+
      "\\s*" + // whitespace including cr/lf, zero or more
      dtdHeader+
      ".*"   + // any chars, 0 or more, PUBLIC/SYSTEM fits here
      "\""   + // quote
      ".*"   + // any chars, 0 or more
      "\""   + // quote
      "\\s*" + // whitespace including cr/lf, zero or more
      "\""   + // quote
      ".*"   + // any chars, 0 or more
      "\""   + // quote
      "\\s*" + // whitespace including cr/lf, zero or more
      dtdFooter;
  
  /**
   * This gets called after a good insert when things have settled down.  Here we try to make sure the DTD spec is the same as our chosen version.
   * The schema reference gets changed above, so try for the xml and dtd headers here.
   * @param jTextComponent 
   */
  @Override
  public void postInsert(JTextComponent jTextComponent)
  {
    String completeText = jTextComponent.getText();
    String s = completeText;
    int size = completeText.length();
    
    if(size > 255) {
      // operate on a portion of the whole thing
      s = s.substring(0, 256);
//      size = 255;
    }
    
    String newHeader = xmlHdr+linesep+dtdHeader+dtdHdr1;  // first part of new header
    
    version = getVersion();
    if(version.equals("3.0") || version.equals("3.1") || version.equals("3.2") || version.equals("3.3") || version.equals("4.0") || version.equals("4.1"))
      ;
    else {
      System.err.println("Bad X3D version='" + version + "' found by X3D.java: must be 3.0, 3.1, 3.2, 3.3, 4.0 or 4.1");
      return;
    }

    newHeader = newHeader+dtdNameElement0+version+dtdNameElement1 + " " + dtdLocationElement0+version+dtdLocationElement1+dtdFooter;
    
    // Quickly find out if we matched; if not, oddball existing header maybe...leave it alone
    String[] sa = s.split(regexPattern);
    if(sa == null || sa.length<2) {
      System.err.println("No DTD header match in X3D.java");
      return;
    }
     
    // Do the replace
    jTextComponent.setText(completeText.replaceFirst(regexPattern, newHeader));
  }

  public String getProfile()
  {
    return profile;
  }

  public void setProfile(String newProfile)
  {
    profile = newProfile;
  }

  public String getVersion()
  {
    return version;
  }

  public void setVersion(String newVersion)
  {
    version = newVersion;
  }

  public String getXmlns_xsd()
  {
    return xmlns_xsd;
  }

  public void setXmlns_xsd(String newXmlns_xsd)
  {
    xmlns_xsd = newXmlns_xsd;
  }

  public String getXsd_noNamespaceSchemaLocation()
  {
    return xsd_noNamespaceSchemaLocation;
  }

  public void setXsd_noNamespaceSchemaLocation(String newXsd_noNamespaceSchemaLocation)
  {
    xsd_noNamespaceSchemaLocation = newXsd_noNamespaceSchemaLocation;
  }

}
