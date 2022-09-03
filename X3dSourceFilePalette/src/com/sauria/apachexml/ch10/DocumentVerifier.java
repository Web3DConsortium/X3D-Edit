/*
 * 
 * DocumentVerifier.java
 * 
 * Example from "Professional XML Development with Apache Tools"
 *
 */
package com.sauria.apachexml.ch10;

import java.io.File;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.TransformerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.SignedInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.Constants;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DocumentVerifier
{
  static
  {
    org.apache.xml.security.Init.init();
  }

  public X509Certificate getCertificate(Document doc, String baseURI) throws Exception
  {
    KeyInfo keyInfo = getSignature(doc, baseURI).getKeyInfo();

    if (keyInfo == null)
      throw new Exception("No key include in signature ");
    if (!keyInfo.containsX509Data())
      throw new Exception("No X.509 certificate data in signature");
      
    return keyInfo.getX509Certificate();
  }
  
  // Removed in 1.4.2, but needed
  public static Element createDSctx(Document doc, String prefix, String namespace)
  {
    if ((prefix == null) || (prefix.trim().length() == 0))
      throw new IllegalArgumentException("You must supply a prefix");

    Element ctx = doc.createElementNS(null, "namespaceContext");
    ctx.setAttributeNS(Constants.NamespaceSpecNS, "xmlns:" + prefix.trim(), namespace);
    return ctx;
  }

  public static boolean hasSignature(Document doc, String baseURI)
  {
    Element namespaceContext = /*XMLUtils.*/createDSctx(doc, "ds", Constants.SignatureSpecNS);
    Element signatureElement = null;
    try {
     signatureElement = (Element) XPathAPI.selectSingleNode(doc, "//ds:Signature[1]", namespaceContext);
    }
    catch(TransformerException tex) {}
    
    return signatureElement != null;
  }

  public static XMLSignature getSignature(Document doc, String baseURI) throws Exception
  {
    Element namespaceContext = /*XMLUtils.*/createDSctx(doc, "ds", Constants.SignatureSpecNS);
    Element signatureElement = (Element) XPathAPI.selectSingleNode(doc, "//ds:Signature[1]", namespaceContext);
    if (signatureElement == null)
      throw new Exception("No signature in document, integrity verification and authentication not possible");

    return new XMLSignature(signatureElement, baseURI);
   }
  
  @SuppressWarnings("unchecked")
  public static List verifySignedDocument(Document doc, PublicKey pKey, String baseURI) throws Exception
  {
    XMLSignature signature = getSignature(doc,baseURI);
    List result = new ArrayList();
    boolean coarseResult = false;
//    Element namespaceContext = XMLUtils.createDSctx(doc, "ds", Constants.SignatureSpecNS);
//    Element signatureElement = null;
//    signatureElement = (Element) XPathAPI.selectSingleNode(doc, "//ds:Signature[1]", namespaceContext);
//    if (signatureElement == null)
//      throw new Exception("No signature in document");
//
//    myXMLSignature signature = new myXMLSignature(signatureElement, baseURI);
    KeyInfo ki = signature.getKeyInfo();

    if (ki != null) {
      if (pKey != null)
        coarseResult = signature.checkSignatureValue(pKey);        // validate against passed in key
      else {
        if (!ki.containsX509Data())
          throw new Exception("No X.509 certificate data in signature");

        X509Certificate cert = ki.getX509Certificate();

        if (cert != null)
          coarseResult = signature.checkSignatureValue(cert);
        else {
          PublicKey pk = signature.getKeyInfo().getPublicKey();
          coarseResult = signature.checkSignatureValue(pk);
        }
      }
    }
    else {
      throw new Exception("No key included in signature ");
    }

    if (!coarseResult) {
      SignedInfo si = signature.getSignedInfo();
      getFailedReferences(result, si);
      if(result.size() <= 0)
        result.add("Unspecified verification error");
    }
    return result;
  }
  
  @SuppressWarnings("unchecked")
  public static void getFailedReferences(List result, SignedInfo si) throws XMLSecurityException
  {
    int length = si.getLength();
    for (int i = 0; i < length; i++) {
      if (!si.getVerificationResult(i))
        result.add(si.getReferencedContentBeforeTransformsItem(i));
    }
  }

  public static void main(String[] args)
  {
    try {
      verify(args[0],null);
    }
    catch (Exception ex) {
      ex.printStackTrace(System.err);
    }
  }

  public static boolean verify(String uri, PublicKey pkey) throws Exception
  {
    File signatureFile = new File(uri);
    String baseURI = getBaseURI(signatureFile);
    Document doc = getInputDocument(signatureFile);
    List result = verifySignedDocument(doc, pkey, baseURI);

    if (result.size() > 0) {
      for (int i = 0; i < result.size(); i++) {
        XMLSignatureInput xsi = (XMLSignatureInput) result.get(i);
        System.out.println("Reference URI " + xsi.getSourceURI() + " did not verify.");
      }
    }
    else {
      System.out.println("Signature verified");
    }
    return result.isEmpty();
  }

  public static String getBaseURI(File signatureFile) throws Exception
  {
    return signatureFile.toURI().toURL().toString();
  }

  public static Document getInputDocument(File signatureFile) throws Exception
  {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(signatureFile);

    return doc;
  }
}
