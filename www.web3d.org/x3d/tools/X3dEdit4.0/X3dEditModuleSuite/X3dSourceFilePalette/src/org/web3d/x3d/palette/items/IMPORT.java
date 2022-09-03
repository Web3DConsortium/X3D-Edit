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
 * IMPORT.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class IMPORT extends SceneGraphStructureNodeType
{
  private String   inlineDEF;
  private String importedDEF;
  private String AS;
  

  public IMPORT()
  {
  }

  @Override
  public String getElementName()
  {
    return IMPORT_ELNAME;
  }

  @Override
  public void initialize()
  {
      inlineDEF = IMPORT_ATTR_INLINEDEF_DFLT;
    importedDEF = IMPORT_ATTR_IMPORTEDDEF_DFLT;
             AS = IMPORT_ATTR_AS_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(IMPORT_ATTR_INLINEDEF_NAME);
    if (attr != null)
      inlineDEF = attr.getValue();
    attr = root.getAttribute(IMPORT_ATTR_IMPORTEDDEF_NAME);
    if (attr != null)
      importedDEF = attr.getValue();
    attr = root.getAttribute(IMPORT_ATTR_AS_NAME);
    if (attr != null)
      AS = attr.getValue();
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return IMPORTCustomizer.class;
  }
  
  @Override
  public String buildDEF()
  {
    return "";
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (IMPORT_ATTR_IMPORTEDDEF_REQD || !importedDEF.equals(IMPORT_ATTR_IMPORTEDDEF_DFLT)) {
      sb.append(" ");
      sb.append(IMPORT_ATTR_IMPORTEDDEF_NAME);
      sb.append("='");
      sb.append(importedDEF);
      sb.append("'");
    }
    if (IMPORT_ATTR_INLINEDEF_REQD || !inlineDEF.equals(IMPORT_ATTR_INLINEDEF_DFLT)) {
      sb.append(" ");
      sb.append(IMPORT_ATTR_INLINEDEF_NAME);
      sb.append("='");
      sb.append(inlineDEF);
      sb.append("'");
    }
    if (IMPORT_ATTR_AS_REQD || !AS.equals(IMPORT_ATTR_AS_DFLT)) {
      sb.append(" ");
      sb.append(IMPORT_ATTR_AS_NAME);
      sb.append("='");
      sb.append(AS);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getImportedDEF()
  {
    return importedDEF;
  }
  
  public void setImportedDEF(String newImportedDEF)
  {
    importedDEF = newImportedDEF;
  }

  public String getInlineDEF()
  {
    return inlineDEF;
  }
  
  public void setInlineDEF(String newInlineDEF)
  {
    inlineDEF = newInlineDEF;
  }
  
  public String getAS()
  {
    return AS;
  }

  public void setAS(String newAS)
  {
    this.AS = newAS;
  }

  @Override
  public String getContent()
  {
    return ""; // no contained content for this element
  }
  
  @Override
  public void setContent(String newContainedContent)
  {
    // no contained content for this element
  }

}
