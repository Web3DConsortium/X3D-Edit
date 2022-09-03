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
package org.web3d.x3d;

import java.io.FileNotFoundException;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.netbeans.modules.xml.catalog.spi.*;

import org.openide.ErrorManager;

import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileStateInvalidException;
import org.openide.filesystems.FileUtil;

import org.openide.util.NbBundle;

import org.xml.sax.InputSource;

/** Catalog for X3d DTDs and Schemas...based on:
 *  org.netbeans.modules.j2ee.ddloaders.web.DDCatalog.java
 *  Catalog for web app DTDs that enables completion support in editor.
 *
 * @author Milan Kuchtiak
 * 
 * X3DCatalog.java
 * Created on Oct 3, 2007, 1:39:37 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class X3DCatalog implements CatalogReader, CatalogDescriptor2, org.xml.sax.EntityResolver, URIResolver
{
  private static final String LOCAL_DIR = "nbres:/org/web3d/x3d/specifications/";

  private static final String DTD_30_PUBID = "ISO//Web3D//DTD X3D 3.0//EN"; // noI18N
  private static final String DTD_31_PUBID = "ISO//Web3D//DTD X3D 3.1//EN"; // noI18N
  private static final String DTD_32_PUBID = "ISO//Web3D//DTD X3D 3.2//EN"; // noI18N
  private static final String DTD_33_PUBID = "ISO//Web3D//DTD X3D 3.3//EN"; // noI18N
  private static final String DTD_40_PUBID = "ISO//Web3D//DTD X3D 4.0//EN"; // noI18N
  private static final String DTD_41_PUBID = "ISO//Web3D//DTD X3D 4.1//EN"; // noI18N

  private static final String DTD_30_LOCAL = LOCAL_DIR + "x3d-3.0.dtd"; // noI18N
  private static final String DTD_31_LOCAL = LOCAL_DIR + "x3d-3.1.dtd"; // noI18N
  private static final String DTD_32_LOCAL = LOCAL_DIR + "x3d-3.2.dtd"; // noI18N
  private static final String DTD_33_LOCAL = LOCAL_DIR + "x3d-3.3.dtd"; // noI18N
  private static final String DTD_40_LOCAL = LOCAL_DIR + "x3d-4.0.dtd"; // noI18N
  private static final String DTD_41_LOCAL = LOCAL_DIR + "x3d-4.1.dtd"; // noI18N

  private static final String DTD_30_EXT_IO_FN   ="x3d-3.0-InputOutputFields.dtd";
  private static final String DTD_30_EXT_PUB_FN  ="x3d-3.0-Web3dExtensionsPublic.dtd";
  private static final String DTD_30_EXT_PRIV_FN ="x3d-3.0-Web3dExtensionsPrivate.dtd";
  private static final String DTD_31_EXT_IO_FN   ="x3d-3.1-InputOutputFields.dtd";
  private static final String DTD_31_EXT_PUB_FN  ="x3d-3.1-Web3dExtensionsPublic.dtd";
  private static final String DTD_31_EXT_PRIV_FN ="x3d-3.1-Web3dExtensionsPrivate.dtd";
  private static final String DTD_32_EXT_IO_FN   ="x3d-3.2-InputOutputFields.dtd";
  private static final String DTD_32_EXT_PUB_FN  ="x3d-3.2-Web3dExtensionsPublic.dtd";
  private static final String DTD_32_EXT_PRIV_FN ="x3d-3.2-Web3dExtensionsPrivate.dtd";
  private static final String DTD_33_EXT_IO_FN   ="x3d-3.3-InputOutputFields.dtd";
  private static final String DTD_33_EXT_PUB_FN  ="x3d-3.3-Web3dExtensionsPublic.dtd";
  private static final String DTD_33_EXT_PRIV_FN ="x3d-3.3-Web3dExtensionsPrivate.dtd";
  private static final String DTD_40_EXT_IO_FN   ="x3d-4.0-InputOutputFields.dtd";
  private static final String DTD_40_EXT_PUB_FN  ="x3d-4.0-Web3dExtensionsPublic.dtd";
  private static final String DTD_40_EXT_PRIV_FN ="x3d-4.0-Web3dExtensionsPrivate.dtd";
  private static final String DTD_41_EXT_IO_FN   ="x3d-4.1-InputOutputFields.dtd";
  private static final String DTD_41_EXT_PUB_FN  ="x3d-4.1-Web3dExtensionsPublic.dtd";
  private static final String DTD_41_EXT_PRIV_FN ="x3d-4.1-Web3dExtensionsPrivate.dtd";

  private static final String DTD_30_EXT_IO_LOCAL   = LOCAL_DIR + DTD_30_EXT_IO_FN;
  private static final String DTD_30_EXT_PUB_LOCAL  = LOCAL_DIR + DTD_30_EXT_PUB_FN;
  private static final String DTD_30_EXT_PRIV_LOCAL = LOCAL_DIR + DTD_30_EXT_PRIV_FN;
  private static final String DTD_31_EXT_IO_LOCAL   = LOCAL_DIR + DTD_31_EXT_IO_FN;
  private static final String DTD_31_EXT_PUB_LOCAL  = LOCAL_DIR + DTD_31_EXT_PUB_FN;
  private static final String DTD_31_EXT_PRIV_LOCAL = LOCAL_DIR + DTD_31_EXT_PRIV_FN;
  private static final String DTD_32_EXT_IO_LOCAL   = LOCAL_DIR + DTD_32_EXT_IO_FN;
  private static final String DTD_32_EXT_PUB_LOCAL  = LOCAL_DIR + DTD_32_EXT_PUB_FN;
  private static final String DTD_32_EXT_PRIV_LOCAL = LOCAL_DIR + DTD_32_EXT_PRIV_FN;
  private static final String DTD_33_EXT_IO_LOCAL   = LOCAL_DIR + DTD_33_EXT_IO_FN;
  private static final String DTD_33_EXT_PUB_LOCAL  = LOCAL_DIR + DTD_33_EXT_PUB_FN;
  private static final String DTD_33_EXT_PRIV_LOCAL = LOCAL_DIR + DTD_33_EXT_PRIV_FN;
  private static final String DTD_40_EXT_IO_LOCAL   = LOCAL_DIR + DTD_40_EXT_IO_FN;
  private static final String DTD_40_EXT_PUB_LOCAL  = LOCAL_DIR + DTD_40_EXT_PUB_FN;
  private static final String DTD_40_EXT_PRIV_LOCAL = LOCAL_DIR + DTD_40_EXT_PRIV_FN;
  private static final String DTD_41_EXT_IO_LOCAL   = LOCAL_DIR + DTD_41_EXT_IO_FN;
  private static final String DTD_41_EXT_PUB_LOCAL  = LOCAL_DIR + DTD_41_EXT_PUB_FN;
  private static final String DTD_41_EXT_PRIV_LOCAL = LOCAL_DIR + DTD_41_EXT_PRIV_FN;

  private static final String SCHEMA_30_FN  = "x3d-3.0.xsd";
  private static final String SCHEMA_31_FN  = "x3d-3.1.xsd";
  private static final String SCHEMA_32_FN  = "x3d-3.2.xsd";
  private static final String SCHEMA_33_FN  = "x3d-3.3.xsd";
  private static final String SCHEMA_40_FN  = "x3d-4.0.xsd";
  private static final String SCHEMA_41_FN  = "x3d-4.1.xsd";
  private static final String SCHEMA_30_LOC = "https://www.web3d.org/specifications/"+SCHEMA_30_FN;
  private static final String SCHEMA_31_LOC = "https://www.web3d.org/specifications/"+SCHEMA_31_FN;
  private static final String SCHEMA_32_LOC = "https://www.web3d.org/specifications/"+SCHEMA_32_FN;
  private static final String SCHEMA_33_LOC = "https://www.web3d.org/specifications/"+SCHEMA_33_FN;
  private static final String SCHEMA_40_LOC = "https://www.web3d.org/specifications/"+SCHEMA_40_FN;
  private static final String SCHEMA_41_LOC = "https://www.web3d.org/specifications/"+SCHEMA_41_FN;
  private static final String SCHEMA_30_PUBID = "SCHEMA:"+SCHEMA_30_LOC;
  private static final String SCHEMA_31_PUBID = "SCHEMA:"+SCHEMA_31_LOC;
  private static final String SCHEMA_32_PUBID = "SCHEMA:"+SCHEMA_32_LOC;
  private static final String SCHEMA_33_PUBID = "SCHEMA:"+SCHEMA_33_LOC;
  private static final String SCHEMA_40_PUBID = "SCHEMA:"+SCHEMA_40_LOC;
  private static final String SCHEMA_41_PUBID = "SCHEMA:"+SCHEMA_41_LOC;

  private static final String SCHEMA_30_LOCAL = LOCAL_DIR+SCHEMA_30_FN; // noI18N
  private static final String SCHEMA_31_LOCAL = LOCAL_DIR+SCHEMA_31_FN; // noI18N
  private static final String SCHEMA_32_LOCAL = LOCAL_DIR+SCHEMA_32_FN; // noI18N
  private static final String SCHEMA_33_LOCAL = LOCAL_DIR+SCHEMA_33_FN; // noI18N
  private static final String SCHEMA_40_LOCAL = LOCAL_DIR+SCHEMA_40_FN; // noI18N
  private static final String SCHEMA_41_LOCAL = LOCAL_DIR+SCHEMA_41_FN; // noI18N

  private static final String SCHEMA_30_EXT_PUB_FN  ="x3d-3.0-Web3dExtensionsPublic.xsd";
  private static final String SCHEMA_30_EXT_PRIV_FN ="x3d-3.0-Web3dExtensionsPrivate.xsd";
  private static final String SCHEMA_31_EXT_PUB_FN  ="x3d-3.1-Web3dExtensionsPublic.xsd";
  private static final String SCHEMA_31_EXT_PRIV_FN ="x3d-3.1-Web3dExtensionsPrivate.xsd";
  private static final String SCHEMA_32_EXT_PUB_FN  ="x3d-3.2-Web3dExtensionsPublic.xsd";
  private static final String SCHEMA_32_EXT_PRIV_FN ="x3d-3.2-Web3dExtensionsPrivate.xsd";
  private static final String SCHEMA_33_EXT_PUB_FN  ="x3d-3.3-Web3dExtensionsPublic.xsd";
  private static final String SCHEMA_33_EXT_PRIV_FN ="x3d-3.3-Web3dExtensionsPrivate.xsd";
  private static final String SCHEMA_40_EXT_PUB_FN  ="x3d-4.0-Web3dExtensionsPublic.xsd";
  private static final String SCHEMA_40_EXT_PRIV_FN ="x3d-4.0-Web3dExtensionsPrivate.xsd";
  private static final String SCHEMA_41_EXT_PUB_FN  ="x3d-4.1-Web3dExtensionsPublic.xsd";
  private static final String SCHEMA_41_EXT_PRIV_FN ="x3d-4.1-Web3dExtensionsPrivate.xsd";
  
  private static final String SCHEMA_30_EXT_PUB_LOCAL  =LOCAL_DIR+SCHEMA_30_EXT_PUB_FN;
  private static final String SCHEMA_30_EXT_PRIV_LOCAL =LOCAL_DIR+SCHEMA_30_EXT_PRIV_FN;
  private static final String SCHEMA_31_EXT_PUB_LOCAL  =LOCAL_DIR+SCHEMA_31_EXT_PUB_FN;
  private static final String SCHEMA_31_EXT_PRIV_LOCAL =LOCAL_DIR+SCHEMA_31_EXT_PRIV_FN;
  private static final String SCHEMA_32_EXT_PUB_LOCAL  =LOCAL_DIR+SCHEMA_32_EXT_PUB_FN;
  private static final String SCHEMA_32_EXT_PRIV_LOCAL =LOCAL_DIR+SCHEMA_32_EXT_PRIV_FN;
  private static final String SCHEMA_33_EXT_PUB_LOCAL  =LOCAL_DIR+SCHEMA_33_EXT_PUB_FN;
  private static final String SCHEMA_33_EXT_PRIV_LOCAL =LOCAL_DIR+SCHEMA_33_EXT_PRIV_FN;
  private static final String SCHEMA_40_EXT_PUB_LOCAL  =LOCAL_DIR+SCHEMA_40_EXT_PUB_FN;
  private static final String SCHEMA_40_EXT_PRIV_LOCAL =LOCAL_DIR+SCHEMA_40_EXT_PRIV_FN;
  private static final String SCHEMA_41_EXT_PUB_LOCAL  =LOCAL_DIR+SCHEMA_41_EXT_PUB_FN;
  private static final String SCHEMA_41_EXT_PRIV_LOCAL =LOCAL_DIR+SCHEMA_41_EXT_PRIV_FN;

  private static X3DCatalog instance;
  public static X3DCatalog getInstance()
  {
    if(instance == null)
      instance = new X3DCatalog();
    return instance;
  }
  // Cant enforce the singleton, stay public
  public X3DCatalog()
  {
    instance = X3DCatalog.this;
  }

  /**
   * Get String iterator representing all public IDs registered in catalog.
   * @return null if cannot proceed, try later.
   */
  @Override
  public java.util.Iterator getPublicIDs()
  {
    //System.out.println("X3DCatalog.getPublicIDs().............");
    java.util.List<String> list = new java.util.ArrayList<>();
    list.add(DTD_30_PUBID);
    list.add(DTD_31_PUBID);
    list.add(DTD_32_PUBID);
    list.add(DTD_33_PUBID);
    list.add(DTD_40_PUBID);
    list.add(DTD_41_PUBID);
    list.add(SCHEMA_30_PUBID);
    list.add(SCHEMA_31_PUBID);
    list.add(SCHEMA_32_PUBID);
    list.add(SCHEMA_33_PUBID);
    list.add(SCHEMA_40_PUBID);
    list.add(SCHEMA_41_PUBID);
    return list.listIterator();
  }

  /**
   * Get registered systemid for given public Id or null if not registered.
   * @param publicId
   * @return null if not registered
   */
  @Override
  public String getSystemID(String publicId)
  {
    if      (null != publicId)
      //System.out.println("X3DCatalog.getSystemIDs().............");
      switch (publicId) {
          case DTD_30_PUBID:
              return DTD_30_LOCAL;
          case DTD_31_PUBID:
              return DTD_31_LOCAL;
          case DTD_32_PUBID:
              return DTD_32_LOCAL;
          case DTD_33_PUBID:
              return DTD_33_LOCAL;
          case DTD_40_PUBID:
              return DTD_40_LOCAL;
          case DTD_41_PUBID:
              return DTD_41_LOCAL;
      }

    if      (null != publicId)
      switch (publicId) {
          case SCHEMA_30_PUBID:
              return SCHEMA_30_LOCAL;
          case SCHEMA_31_PUBID:
              return SCHEMA_31_LOCAL;
          case SCHEMA_32_PUBID:
              return SCHEMA_32_LOCAL;
          case SCHEMA_33_PUBID:
              return SCHEMA_33_LOCAL;
          case SCHEMA_40_PUBID:
              return SCHEMA_40_LOCAL;
          case SCHEMA_41_PUBID:
              return SCHEMA_41_LOCAL;
      }

    //else
    return null;
  }

  /**
   * Refresh content according to content of mounted catalog.
   */
  @Override
  public void refresh()
  {
  }

  /**
   * Optional operation allowing to listen at catalog for changes.
   * @param l
   */
  @Override
  public void addCatalogListener(CatalogListener l)
  {
  }

  /**
   * Optional operation coupled with addCatalogListener.
   * @param l
   */
  @Override
  public void removeCatalogListener(CatalogListener l)
  {
  }

  /** Registers new listener.
   * @param l 
   */
  @Override
  public void addPropertyChangeListener(java.beans.PropertyChangeListener l)
  {
  }

  /**
   * @return I18N display name
   */
  @Override
  public String getDisplayName()
  {
    return NbBundle.getMessage(X3DCatalog.class, "LBL_DDCatalog");
  }

  /**
   * Return visualized state of given catalog.
   * @param type of icon defined by JavaBeans specs
   * @return icon representing current state or null
   */
  @Override
  public String getIconResource(int type)
  {
    return "org/netbeans/modules/j2ee/ddloaders/web/resources/DDCatalog.gif"; // NOI18N
  }

  /**
   * @return I18N short description
   */
  @Override
  public String getShortDescription()
  {
    return NbBundle.getMessage(X3DCatalog.class, "DESC_DDCatalog");
  }

  /** Unregister the listener.
   * @param l 
   */
  @Override
  public void removePropertyChangeListener(java.beans.PropertyChangeListener l)
  {
  }

  /**
   * Resolves schema definition file for deployment descriptor (spec.2_4 and spec.2_5)
   * @param publicId publicId for resolved entity (null in our case)
   * @param systemId systemId for resolved entity
   * @return InputSource for
   * @throws org.xml.sax.SAXException
   * @throws java.io.IOException
   */
  @Override
  public org.xml.sax.InputSource resolveEntity(String publicId, String systemId) throws org.xml.sax.SAXException, java.io.IOException
  {
    //System.out.println("X3DCatalog.resolveEntity(): "+ publicId + " " + systemId);
    if (systemId == null)
      return null;

  switch (systemId) {
      case SCHEMA_30_LOC:
          return new InputSource(SCHEMA_30_LOCAL);
      case SCHEMA_31_LOC:
          return new InputSource(SCHEMA_31_LOCAL);
      case SCHEMA_32_LOC:
          return new InputSource(SCHEMA_32_LOCAL);
      case SCHEMA_33_LOC:
          return new InputSource(SCHEMA_33_LOCAL);
      case SCHEMA_40_LOC:
          return new InputSource(SCHEMA_40_LOCAL);
      case SCHEMA_41_LOC:
          return new InputSource(SCHEMA_41_LOCAL);
  }

    if (systemId.endsWith     (SCHEMA_30_FN))
      return new InputSource  (SCHEMA_30_LOCAL);
    else if (systemId.endsWith(SCHEMA_31_FN))
      return new InputSource  (SCHEMA_31_LOCAL);
    else if (systemId.endsWith(SCHEMA_32_FN))
      return new InputSource  (SCHEMA_32_LOCAL);
    else if (systemId.endsWith(SCHEMA_33_FN))
      return new InputSource  (SCHEMA_33_LOCAL);
    else if (systemId.endsWith(SCHEMA_40_FN))
      return new InputSource  (SCHEMA_40_LOCAL);
    else if (systemId.endsWith(SCHEMA_41_FN))
      return new InputSource  (SCHEMA_41_LOCAL);

    if (systemId.endsWith     (SCHEMA_30_EXT_PUB_FN))
      return new InputSource  (SCHEMA_30_EXT_PUB_LOCAL);
    else if (systemId.endsWith(SCHEMA_30_EXT_PRIV_FN))
      return new InputSource  (SCHEMA_30_EXT_PRIV_LOCAL);
	
    else if (systemId.endsWith(SCHEMA_31_EXT_PUB_FN))
      return new InputSource  (SCHEMA_31_EXT_PUB_LOCAL);
    else if (systemId.endsWith(SCHEMA_31_EXT_PRIV_FN))
      return new InputSource  (SCHEMA_31_EXT_PRIV_LOCAL);
	
    else if (systemId.endsWith(SCHEMA_32_EXT_PUB_FN))
      return new InputSource  (SCHEMA_32_EXT_PUB_LOCAL);
    else if (systemId.endsWith(SCHEMA_32_EXT_PRIV_FN))
      return new InputSource  (SCHEMA_32_EXT_PRIV_LOCAL);
	
    else if (systemId.endsWith(SCHEMA_33_EXT_PUB_FN))
      return new InputSource  (SCHEMA_33_EXT_PUB_LOCAL);
    else if (systemId.endsWith(SCHEMA_33_EXT_PRIV_FN))
      return new InputSource  (SCHEMA_33_EXT_PRIV_LOCAL);
	
    else if (systemId.endsWith(SCHEMA_40_EXT_PUB_FN))
      return new InputSource  (SCHEMA_40_EXT_PUB_LOCAL);
    else if (systemId.endsWith(SCHEMA_40_EXT_PRIV_FN))
      return new InputSource  (SCHEMA_40_EXT_PRIV_LOCAL);
	
    else if (systemId.endsWith(SCHEMA_41_EXT_PUB_FN))
      return new InputSource  (SCHEMA_41_EXT_PUB_LOCAL);
    else if (systemId.endsWith(SCHEMA_41_EXT_PRIV_FN))
      return new InputSource  (SCHEMA_41_EXT_PRIV_LOCAL);

    // I think this should go here:
    if      (systemId.endsWith(DTD_30_EXT_IO_FN))
        return new InputSource(DTD_30_EXT_IO_LOCAL);
    else if (systemId.endsWith(DTD_30_EXT_PUB_FN))
        return new InputSource(DTD_30_EXT_PUB_LOCAL);
    else if (systemId.endsWith(DTD_30_EXT_PRIV_FN))
        return new InputSource(DTD_30_EXT_PRIV_LOCAL);
    else if (systemId.endsWith(DTD_31_EXT_IO_FN))
        return new InputSource(DTD_31_EXT_IO_LOCAL);
    else if (systemId.endsWith(DTD_31_EXT_PUB_FN))
        return new InputSource(DTD_31_EXT_PUB_LOCAL);
    else if (systemId.endsWith(DTD_31_EXT_PRIV_FN))
        return new InputSource(DTD_31_EXT_PRIV_LOCAL);
    else if (systemId.endsWith(DTD_32_EXT_IO_FN))
        return new InputSource(DTD_32_EXT_IO_LOCAL);
    else if (systemId.endsWith(DTD_32_EXT_PUB_FN))
        return new InputSource(DTD_32_EXT_PUB_LOCAL);
    else if (systemId.endsWith(DTD_32_EXT_PRIV_FN))
        return new InputSource(DTD_32_EXT_PRIV_LOCAL);
    else if (systemId.endsWith(DTD_33_EXT_IO_FN))
        return new InputSource(DTD_33_EXT_IO_LOCAL);
    else if (systemId.endsWith(DTD_33_EXT_PUB_FN))
        return new InputSource(DTD_33_EXT_PUB_LOCAL);
    else if (systemId.endsWith(DTD_33_EXT_PRIV_FN))
        return new InputSource(DTD_33_EXT_PRIV_LOCAL);
    else if (systemId.endsWith(DTD_40_EXT_IO_FN))
        return new InputSource(DTD_40_EXT_IO_LOCAL);
    else if (systemId.endsWith(DTD_40_EXT_PUB_FN))
        return new InputSource(DTD_40_EXT_PUB_LOCAL);
    else if (systemId.endsWith(DTD_40_EXT_PRIV_FN))
        return new InputSource(DTD_40_EXT_PRIV_LOCAL);
    else if (systemId.endsWith(DTD_41_EXT_IO_FN))
        return new InputSource(DTD_41_EXT_IO_LOCAL);
    else if (systemId.endsWith(DTD_41_EXT_PUB_FN))
        return new InputSource(DTD_41_EXT_PUB_LOCAL);
    else if (systemId.endsWith(DTD_41_EXT_PRIV_FN))
        return new InputSource(DTD_41_EXT_PRIV_LOCAL);

    //else
    return null;
  }

  /**
   * Get registered URI for the given name or null if not registered.
   * @param name
   * @return null if not registered
   */
  @Override
  public String resolveURI(String name)
  {
    //System.err.println("resolveURI......"+name);
    if (name.endsWith("xslt"))
      return "nbres:/org/web3d/x3d/stylesheets/" + name;
    //System.err.println("......returning null");
    return null;
  }

  /**
   * Get registered URI for the given publicId or null if not registered.
   * @param publicId
   * @return null if not registered
   */
  @Override
  public String resolvePublic(String publicId)
  {
    return null;
  }

  @Override
  public Source resolve(String href, String base) throws TransformerException
  {
    try {
      FileObject stylesheets = FileUtil.getConfigRoot().getFileSystem().findResource("X3dTransforms"); //Repository.getDefault().getDefaultFileSystem().findResource("X3dTransforms");

      FileObject[] children = stylesheets.getChildren();
      for (FileObject fo : children) {
        if (href.endsWith(fo.getNameExt())) {
          return new StreamSource(fo.getInputStream());
        }
      }
    }
    catch (FileStateInvalidException | FileNotFoundException ex) {
      ErrorManager.getDefault().log(ErrorManager.ERROR, "Can't read transform from netbeans filesystem: " + ex.getLocalizedMessage());
    }
    return null;
  }
}
