/*
 * 
 * EncryptionMain.java
 * 
 * Example from "Professional XML Development with Apache Tools"
 *
 */
package com.sauria.apachexml.ch10;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.Key;

//import java.security.Provider;
//import java.security.Security;
//import java.util.Map.Entry;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.security.encryption.XMLCipher; 

//import org.apache.xml.serialize.DOMSerializer;
//import org.apache.xml.serialize.Method;
//import org.apache.xml.serialize.OutputFormat;
//import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class EncryptionMain
{
  static
  {
    org.apache.xml.security.Init.init();
  }

  public static Document encrypt(Key key, String cipherName, Document contextDocument,
                                 Element elementToEncrypt) throws Exception
  {
    XMLCipher cipher = XMLCipher.getInstance(cipherName);
    cipher.init(XMLCipher.ENCRYPT_MODE, key);
    return cipher.doFinal(contextDocument, elementToEncrypt);
  }

  public static Document decrypt(Key key, String cipherName, Document documentToDecrypt) throws Exception
  {
    XMLCipher cipher = XMLCipher.getInstance(cipherName);
    cipher.init(XMLCipher.DECRYPT_MODE, key);

    Element encryptedDataElement = (Element) documentToDecrypt.getElementsByTagNameNS(
        "http://www.w3.org/2001/04/xmlenc#",
        "EncryptedData").item(0);

    return cipher.doFinal(documentToDecrypt, encryptedDataElement);
  }

  public static SecretKey get3DESKey(String phrase) throws Exception
  {
    byte[] passPhrase = phrase.getBytes();

    DESedeKeySpec keySpec = new DESedeKeySpec(passPhrase);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
    return keyFactory.generateSecret(keySpec);
  }

  public static Key getAESKey(int bits) throws Exception
  {
    KeyGenerator kgen = KeyGenerator.getInstance("AES");
    kgen.init(bits);
    SecretKey skey = kgen.generateKey();
    byte[] raw = skey.getEncoded();
    return new SecretKeySpec(raw, "AES");
  }

  public static Document loadDocument(String uri) throws Exception
  {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    dbf.setNamespaceAware(true);
    DocumentBuilder db = dbf.newDocumentBuilder();
    File f = new File(uri);
    return db.parse(f);
  }

  @SuppressWarnings("deprecation")
  private static String toString(Document document) throws Exception
  {
    org.apache.xml.serialize.OutputFormat of = new org.apache.xml.serialize.OutputFormat();
    of.setIndenting(true);
    of.setMethod(org.apache.xml.serialize.Method.XML);
    
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    org.apache.xml.serialize.DOMSerializer serializer = new org.apache.xml.serialize.XMLSerializer(baos, of);
    serializer.serialize(document);
    return (baos.toString());
  }
  
  public static boolean test() throws Exception
  {
    String xmlFile = "book.xml";
    String bookNS = "http://sauria.com/schemas/apache-xml-book/book";

    Document contextDoc = loadDocument(xmlFile);
    Element elementToEncrypt = (Element) contextDoc.getElementsByTagNameNS(bookNS, "author").item(0);

    Key key = get3DESKey("3DES or DES-EDE secret key");
    String cipher = XMLCipher.TRIPLEDES;
    Document encryptedDoc = encrypt(key, cipher, contextDoc, elementToEncrypt);
    Document decryptedDoc = decrypt(key, cipher, encryptedDoc);

    contextDoc = loadDocument(xmlFile);
    boolean resultDES = toString(contextDoc).equals(toString(decryptedDoc));
    System.out.println(resultDES);

    key = getAESKey(256);
    cipher = XMLCipher.AES_256;
    elementToEncrypt = (Element) contextDoc.getElementsByTagNameNS(bookNS, "author").item(0);
    encryptedDoc = encrypt(key, cipher, contextDoc, elementToEncrypt);
    decryptedDoc = decrypt(key, cipher, encryptedDoc);

    contextDoc = loadDocument(xmlFile);
    boolean resultAES = toString(contextDoc).equals(toString(decryptedDoc));
    System.out.println(resultAES);

    return resultDES && resultAES;
  }

  public static void main(String[] args)
  {
    try {
      test();
    } catch(Exception ex) {
      ex.printStackTrace(System.err);
    }
  }
}
