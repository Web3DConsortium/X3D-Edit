/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.

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
package org.web3d.x3d;

import java.io.File;
import java.io.IOException;
import javax.xml.transform.sax.SAXSource;
import org.netbeans.spi.xml.cookies.CheckXMLSupport;
import org.netbeans.spi.xml.cookies.DataObjectAdapters;
import org.netbeans.spi.xml.cookies.TransformableSupport;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.cookies.ViewCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.*;

import org.openide.nodes.Children;
import org.openide.nodes.CookieSet;
import org.openide.nodes.Node;
import org.openide.nodes.Node.Cookie;
import org.openide.util.Lookup;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

@MIMEResolver.ExtensionRegistration(
    displayName = "#Services/MIMEResolver/x3d.xml",
    mimeType = "model/x3d+xml",
    extension = {"x3d"},
    position = 25
)
@DataObject.Registration(
    mimeType = "model/x3d+xml",
    iconBase = "org/web3d/x3d/resources/x3dObject.png",
    displayName = "#Services/MIMEResolver/x3d.xml",
    position = 1610
)
@ActionReferences({
  @ActionReference(
      path = "Loaders/model/x3d+xml/Actions",
      id = @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
      position = 100,
      separatorAfter = 200
  )})

/**
 * Object that represents one x3d file.
 * 
 * 
 * X3dDataObject.java
 * Created on March 8, 2007, 3:20 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class X3DDataObject extends MultiDataObject implements CookieSet.Factory {

  private X3DEditorSupport editorSupport;
  private final MultiFileLoader loader;
  private final ValidateXMLSupport validateCookie;
  private final CheckXMLSupport checkXmlCookie;
  private final TransformableSupport transformXmlCookie;
  private final ValidateXMLSupport dtdValidator;
  private final ValidateXMLSupport schemaValidator;
  private /*final*/ String x3dDataObjectDirectory="";

  /** New instance.
   * @param pf primary file object for this data object
   * @param loader the data loader creating it
   * @throws DataObjectExistsException if there was already a data object for it
   */
 public X3DDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException
  {
    super(pf, loader);
    this.loader = loader;
    CookieSet set = getCookieSet();
    set.add(X3DEditorSupport.class, X3DDataObject.this);
    set.add(ViewSupport.class, X3DDataObject.this);
    File fil = FileUtil.toFile(pf);
    if(fil != null)
      x3dDataObjectDirectory = fil.getParent();

    // Enable SaveAs support, per FAQ
    set.assign(SaveAsCapable.class, (SaveAsCapable) (FileObject folder, String fileName) -> {
        editorSupport.saveAs(folder, fileName);
    });

    //This enables the validate and check and transform (xslt) xml buttons
    InputSource in = DataObjectAdapters.inputSource(X3DDataObject.this);
    validateCookie = new ValidateXMLSupport(in);
    set.add(validateCookie);
    checkXmlCookie = new CheckXMLSupport(in);
    set.add(checkXmlCookie);
    transformXmlCookie = new TransformableSupport(new SAXSource(in));
    set.add(transformXmlCookie);

    dtdValidator = new DTDValidator(in);
    schemaValidator = new SchemaValidator(in);

  }

  public SAXSource getFreshSaxSource()
  {
    return new SAXSource(DataObjectAdapters.inputSource(this));
  }

  @Override
  protected Node createNodeDelegate()
  {
    DataNode n = new X3DDataNode(this, Children.LEAF);
    n.setIconBaseWithExtension("org/web3d/x3d/resources/x3dObject.png"); // NOI18N
    return n;
  }

  /**
   * Create a new X3d file from template
   * @param directoryDF a DataFolder
   * @param newName for file
   * @return a DataObject of our new file
   * @throws java.io.IOException
   */
  @Override
  protected DataObject handleCreateFromTemplate(DataFolder directoryDF, String newName)
      throws IOException
  {
    // Make a FileObject for the template file
    String path = "Templates/Other/newScene.x3d";
    FileObject x3dTmplFo = FileUtil.getConfigRoot().getFileSystem().findResource(path);
    FileObject directoryFo = directoryDF.getPrimaryFile();  // FO referring to target dir
    // write the new file to disk by copying the template
    FileObject newFo = FileUtil.copyFile(x3dTmplFo,directoryFo,newName);

    return new X3DDataObject(newFo,loader);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T extends Cookie> T createCookie(Class<T> klass)
  {
     if (((Class<?>)klass).isAssignableFrom((Class<?>)X3DEditorSupport.class))
    {
      editorSupport = new X3DEditorSupport(this);
      return (T) editorSupport;
    }
    else if (((Class<?>)klass).isAssignableFrom((Class<?>)ViewSupport.class))
    {
      return (T) new ViewSupport(getPrimaryEntry());
    }
    else
    {
      return null;
    }
  }

  public ValidateXMLSupport getValidateHelper()
  {
    return validateCookie;
  }
  public CheckXMLSupport getCheckXmlHelper()
  {
    return checkXmlCookie;
  }
  public TransformableSupport getTransformXmlHelper()
  {
    return transformXmlCookie;
  }
  public ValidateXMLSupport getDtdValidator()
  {
    return dtdValidator;
  }
  public ValidateXMLSupport getSchemaValidator()
  {
    return schemaValidator;
  }

  /** Creates new Cookie
   * @param klass
   * @return
   */ // todo delete
  public Node.Cookie xcreateCookie(Class<?> klass)
  {
    if (((Class<?>)klass).isAssignableFrom((Class<?>)X3DEditorSupport.class))
    {
      editorSupport = new X3DEditorSupport(this);
      return editorSupport;
    }
    else if (((Class<?>)klass).isAssignableFrom((Class<?>)ViewSupport.class))
    {
      return new ViewSupport(getPrimaryEntry());
    }
    else
    {
      return null;
    }
  }

  /** to eliminate runtime warnings
    * @return
    */
  @Override
  public Lookup getLookup()
  {
    return getCookieSet().getLookup();
  }

  // Package accessibility for X3dEditorSupport:
  CookieSet getCookieSet0()
  {
    return getCookieSet();
  }

  /**
   * @return the x3dDataObjectPath
   */
  public String getX3dDataObjectDirectory() {
    return x3dDataObjectDirectory;
  }

  static final class ViewSupport implements ViewCookie
  {
    /** entry */
    private final MultiDataObject.Entry primary;

    /** Constructs new ViewSupport */
    public ViewSupport(MultiDataObject.Entry primary)
    {
      this.primary = primary;
    }

    @Override
    public void view()
    {
      /* TODO
      try
      {
        X3dBrowser.URLDisplayer.getDefault().showURL(primary.getFile().getURL());
      }
      catch (FileStateInvalidException e)
      {
      }
       */
    }
  }

  class DTDValidator extends ValidateXMLSupport
  {
    public DTDValidator(InputSource in)
    {
      super(in);
    }

    @Override
    protected XMLReader createParser(boolean arg0)
    {
      XMLReader rdr =  super.createParser(arg0);
      try {
        rdr.setFeature("http://xml.org/sax/features/validation", true);
        rdr.setFeature("http://apache.org/xml/features/validation/schema",false);
      }
      catch ( SAXNotRecognizedException | SAXNotSupportedException ex) {
        System.err.println("Can't customize DTD parser in X3DDataObject: "+ex.getClass().getSimpleName()+" "+ex.getLocalizedMessage());
      }
      return rdr;

    }

  }
  class SchemaValidator extends ValidateXMLSupport
  {
    public SchemaValidator(InputSource in)
    {
      super(in);
    }
    @Override
    protected XMLReader createParser(boolean arg0)
    {
      XMLReader rdr =  super.createParser(arg0);
      try {
        rdr.setFeature("http://xml.org/sax/features/validation", true);
        rdr.setFeature("http://apache.org/xml/features/validation/schema",true);
      }
      catch ( SAXNotRecognizedException | SAXNotSupportedException ex) {
        System.err.println(new StringBuilder().append("Can't customize Schema parser in X3DDataObject: ").append(ex.getClass().getSimpleName()).append(" ").append(ex.getLocalizedMessage()).toString());
      }
     return rdr;
    }


  }

}
