/*
 * 
 * DocumentSigner.java
 * 
 * Example from "Professional XML Development with Apache Tools"
 *
 */
package com.sauria.apachexml.ch10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.Manifest;
import org.apache.xml.security.signature.ObjectContainer;
import org.apache.xml.security.signature.SignatureProperties;
import org.apache.xml.security.signature.SignatureProperty;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.apache.xml.security.utils.SignatureElementProxy;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DocumentSigner
{
  protected Document document;
  protected PrivateKey privateKey;
  protected X509Certificate certificate;
  protected PublicKey publicKey;
  protected String baseURI;
  protected String signatureMethod;
  protected String digestMethod;
  protected String transformArray[] = null;
  protected List   objectList = null;
  
  static
  {
    org.apache.xml.security.Init.init();
  }

  public DocumentSigner()
  {
  }

  public DocumentSigner(Document doc,
                        PrivateKey privateKey, X509Certificate cert,
                        PublicKey publicKey, String baseURI,
                        String signatureMethod, String digestMethod)
  {
    this.document = doc;
    this.privateKey = privateKey;
    this.certificate = cert;
    this.publicKey = publicKey;
    this.baseURI = baseURI;
    this.signatureMethod = signatureMethod;
    this.digestMethod = digestMethod;
  }

  public Document sign()
  {
    XMLSignature sig = null;
    try {
      sig = new XMLSignature(document, baseURI, signatureMethod);
    }
    catch (XMLSecurityException xse) {
      xse.printStackTrace(System.err);
    }

    Node root = document.getDocumentElement();

    root.appendChild(sig.getElement());

    try {
      addReferences(sig);
    }
    catch (XMLSignatureException xse) {
      xse.printStackTrace(System.err);
    }

    try {
      if (certificate != null) {
        sig.addKeyInfo(certificate);
        sig.addKeyInfo(certificate.getPublicKey());
      }

      if (publicKey != null && certificate == null) {
        sig.addKeyInfo(publicKey);
      }

      if (objectList != null) {
        for (Iterator i = objectList.iterator(); i.hasNext(); ) {
          SignatureElementProxy sep = (SignatureElementProxy) i.next();
          ObjectContainer oc = new ObjectContainer(document);
          oc.appendChild(sep.getElement());
          sig.appendObject(oc);
        }
      }
    }
    catch (XMLSecurityException xse) {
      xse.printStackTrace(System.err);
    }

    try {
      sig.sign(privateKey);
    }
    catch (XMLSignatureException xse) {
      xse.printStackTrace(System.err);
    }

    return document;
  }

  protected void addReferences(XMLSignature sig) throws XMLSignatureException
  {
    Transforms transforms = new Transforms(document);

    try {
      transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
      transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);
    }
    catch (TransformationException te) {
      te.printStackTrace(System.err);
    }

    sig.addDocument("#xpointer(/)", transforms,digestMethod);
  }

  public void writeSignature(OutputStream outputStream)
  {
    XMLUtils.outputDOMc14nWithComments(document, outputStream);
  }

  @SuppressWarnings("unchecked")
  public Manifest addManifest()
  {
    if (objectList == null) {
      objectList = new ArrayList();
    }
    Manifest manifest = new Manifest(document);
    objectList.add(manifest);
    return manifest;
  }

  @SuppressWarnings("unchecked")
  public SignatureProperties addSignatureProperties()
  {
    if (objectList == null) {
      objectList = new ArrayList();
    }
    SignatureProperties signatureProperties = new SignatureProperties(document);
    objectList.add(signatureProperties);
    return signatureProperties;
  }

  public static void main(String[] args)
  {
    DocumentSigner signer = new DocumentSigner();
    testSigning(signer, args[0], args[1]);
  }

  public static boolean testSigning(DocumentSigner signer,
                                    String uri, String outputFile)
  {
    Document doc = getInputDocument(uri);

    KeyStore ks = getKeyStore("keystore.jks", "password");

    PrivateKey privateKey = getPrivateKey(ks, "johndoe", "password");

    X509Certificate certificate = null;
    try {
      certificate = (X509Certificate) ks.getCertificate("johndoe");
    }
    catch (KeyStoreException kse) {
      kse.printStackTrace(System.err);
    }

    File signatureFile = new File(outputFile);

    String baseURI = getBaseURI(signatureFile);

    signer.setDocument(doc);
    signer.setPrivateKey(privateKey);
    signer.setCertificate(certificate);
    signer.setBaseURI(baseURI);
    signer.setSignatureMethod( XMLSignature.ALGO_ID_SIGNATURE_DSA);
    signer.setDigestMethod(Constants.ALGO_ID_DIGEST_SHA1);

    Manifest m = signer.addManifest();

    SignatureProperties sps = signer.addSignatureProperties();
    SignatureProperty sp = new SignatureProperty(sps.getDocument(),
          "http://www.sauria.com",
          "hardware");
    sp.addText("smartcard");
    sps.addSignatureProperty(sp);
    signer.sign();

    try (FileOutputStream signatureStream = new FileOutputStream(signatureFile)) {
        signer.writeSignature(signatureStream);
    }
    catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace(System.err);
    }
    catch (IOException ioe) {
      ioe.printStackTrace(System.err);
    }
    return true;
  }

  public static String getBaseURI(File signatureFile)
  {
    String BaseURI = null;
    try {
      BaseURI = signatureFile.toURI().toURL().toString();
    }
    catch (MalformedURLException mue) {
      mue.printStackTrace(System.err);
    }
    return BaseURI;
  }

  public static Document getInputDocument(String uri)
  {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);

    Document doc = null;
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.parse(uri);
    }
    catch (ParserConfigurationException | SAXException | IOException pce) {
      pce.printStackTrace(System.err);
    }
    return doc;
  }

  public static PrivateKey getPrivateKey(KeyStore ks,  String privateKeyAlias, String privateKeyPassword)
  {
    PrivateKey privateKey = null;
    try {
      privateKey = (PrivateKey) ks.getKey(privateKeyAlias, privateKeyPassword.toCharArray());
    }
    catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException kse) {
      kse.printStackTrace(System.err);
    }
    return privateKey;
  }

  public static KeyStore getKeyStore(String filename, String keystorePassword)
  {
    KeyStore ks = null;
    try {
      ks = KeyStore.getInstance("JKS");
    }
    catch (KeyStoreException kse) {
      kse.printStackTrace(System.err);
    }

    FileInputStream fis = null;
    try {
      fis = new FileInputStream(filename);
    }
    catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace(System.err);
    }

    try {
      ks.load(fis, keystorePassword.toCharArray());
    }
    catch (NoSuchAlgorithmException | CertificateException | IOException nsae) {
      nsae.printStackTrace(System.err);
    }
    return ks;
  }

  public String getBaseURI()
  {
    return baseURI;
  }

  public X509Certificate getCertificate()
  {
    return certificate;
  }

  public String getDigestMethod()
  {
    return digestMethod;
  }

  public PrivateKey getPrivateKey()
  {
    return privateKey;
  }

  public String getSignatureMethod()
  {
    return signatureMethod;
  }

  public String[] getTransformArray()
  {
    return transformArray;
  }

  public Document getDocument()
  {
    return document;
  }

  public void setBaseURI(String string)
  {
    baseURI = string;
  }

  public void setCertificate(X509Certificate certificate)
  {
    this.certificate = certificate;
  }

  public void setDigestMethod(String string)
  {
    digestMethod = string;
  }

  public void setPrivateKey(PrivateKey key)
  {
    privateKey = key;
  }

  public void setSignatureMethod(String string)
  {
    signatureMethod = string;
  }

  public void setTransformArray(String[] strings)
  {
    transformArray = strings;
  }

  public void setDocument(Document d)
  {
    document = d;
  }

  public List getObjectList()
  {
    return objectList;
  }

  public PublicKey getPublicKey()
  {
    return publicKey;
  }

  public void setObjectList(List list)
  {
    objectList = list;
  }

  public void setPublicKey(PublicKey key)
  {
    publicKey = key;
  }
}