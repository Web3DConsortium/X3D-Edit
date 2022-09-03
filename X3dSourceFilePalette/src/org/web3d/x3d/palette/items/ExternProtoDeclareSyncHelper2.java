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

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.filter.ContentFilter;
import org.jdom.input.SAXBuilder;
import org.openide.filesystems.FileObject;

/**
 * ExternProtoDeclareSyncHelper.java
 * Created on Feb 10, 2009, 10:55 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class ExternProtoDeclareSyncHelper2
{
  public static interface SyncStatusListener
  {
    public void statusIn(String s);
    public void checkDone(HashMap<String,Object> remoteHashMap, String info);
  };

  public static String PROTODECL_APPINFO_KEY       = "____appinfo";
  public static String PROTODECL_DOCUMENTATION_KEY = "____documentation";

  private HashMap <String,Object> protoDeclareHashMap; // appinfo, documentation also saved but as String types and not as DOM Elements
  private final SyncStatusListener synchronizeStatusListener;
  public Thread checkThread;

  public ExternProtoDeclareSyncHelper2(FileObject masterDocLocation, String url, SyncStatusListener syncStatusListener)
  {
    synchronizeStatusListener = syncStatusListener;
    checkThread = new Thread(new Checker(masterDocLocation,url),"ExternProtoDeclareSyncHelper2Thread");
    checkThread.setPriority(Thread.NORM_PRIORITY);
    checkThread.start();
  }

  class Checker implements Runnable
  {
    private final FileObject masterDocLocation;
    private final String urlString;
    Checker(FileObject masterDocLocation, String s)
    {
      urlString = s;
      this.masterDocLocation = masterDocLocation;
    }
    @Override
    public void run()
    {
      String errorString;
      try {
        setStatus("Loading from selected url...");
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(getUrlContents(urlString));
        setStatus("File found, reading xml...");
        processPrototypeDeclaration(doc,urlString);
        checkThread = null;
        return;
      }
      catch(MalformedURLException ex) {
        errorString = "MalformedURLException reading URL" + "\n" + ex.getLocalizedMessage();
      }
      catch(JDOMException jex) {
        errorString = "JDOMException reading URL" + "\n" + jex.getLocalizedMessage();
      }
      catch(IOException iex) {
        errorString = "IOException reading URL" + "\n" + iex.getLocalizedMessage();
      }
      catch(Exception ex) {
        System.out.println("bp");
        errorString = "Exception reading URL" + "\n" + ex.getLocalizedMessage();
      }
      setDone (null, errorString); // no hash map, nothing found
      checkThread = null;
    }


  /**
   * From UrlStatus.java
   * @param urlStr
   * @return
   */
  private Reader getUrlContents(String urlStr) throws Exception
  {
    URL urlObj = UrlExpandableList2.buildUrl(masterDocLocation,urlStr);

    URLConnection uConn = urlObj.openConnection();
    InputStream inStr = new BufferedInputStream(uConn.getInputStream());
    return new InputStreamReader(inStr);
  }
}
  private void processPrototypeDeclaration(Document domDocument, String urlPrototypeDeclaration)
  {
    String  urlAnchorName = ""; // expected name of the prototype
    Element protoInterface = null;

    if (urlPrototypeDeclaration.indexOf('#') != -1)
      urlAnchorName = urlPrototypeDeclaration.substring(urlPrototypeDeclaration.lastIndexOf('#') + 1, urlPrototypeDeclaration.length());

    Element protoDeclaration = findProtoDeclare(domDocument, urlAnchorName);
    if (protoDeclaration != null) {
      protoDeclareHashMap = new HashMap<>();

      Attribute appInfoAttr = protoDeclaration.getAttribute("appinfo");
      if(appInfoAttr != null)
        protoDeclareHashMap.put(PROTODECL_APPINFO_KEY, appInfoAttr.getValue());
      Attribute docAttr = protoDeclaration.getAttribute("documentation");
      if(docAttr != null)
        protoDeclareHashMap.put(PROTODECL_DOCUMENTATION_KEY,docAttr.getValue());

      protoInterface = protoDeclaration.getChild("ProtoInterface");
      if (protoInterface != null) {
        List fieldList = protoInterface.getChildren("field");
        for (Object o : fieldList) {
          Element el = (Element) o;
          Attribute name = el.getAttribute("name");
          protoDeclareHashMap.put(name.getValue(), el);
        }
      }
    }
    if (protoDeclareHashMap != null)
    {
        if       (protoDeclareHashMap.isEmpty())
          setDone(protoDeclareHashMap, "Retrieval and comparison complete, no ProtoDeclare appinfo/documentation or ProtoInterface field definitions found in external file");
        else if (protoInterface == null)
          setDone(protoDeclareHashMap, "Retrieval and comparison complete, no ProtoInterface field definitions found in external file");
        else
          setDone(protoDeclareHashMap, "Retrieval and comparison complete");
    }
    else
      setDone(null, "Error: ProtoDeclare not found in external file");
  }

  private Element findProtoDeclare(Document doc, String anchor)
  {
    ContentFilter filter = new ContentFilter(ContentFilter.ELEMENT);
    Element root = doc.getRootElement();
    return findProtoElement("ProtoDeclare",anchor,root,filter);
  }

  private Element findProtoElement(String elementType, String nameAttr, Element element, ContentFilter fil)
  {
    if (element.getName().equals(elementType)) {
      if (nameAttr == null)
        return element;
      if (element.getAttribute("name").getValue().equals(nameAttr))
        return element;
    }

    List lis = element.getContent(fil);
    for (Object o : lis) {
      Element elm = findProtoElement(elementType, nameAttr, (Element) o, fil);
      if (elm != null)
        return elm;
    }
    return null;
  }

  private void setDone(HashMap<String,Object> remoteHashMap, String s)
  {
    if(synchronizeStatusListener != null)
       synchronizeStatusListener.checkDone(remoteHashMap, s);
  }
  private void setStatus(final String s)
  {
    if(synchronizeStatusListener != null)
       synchronizeStatusListener.statusIn(s);
  }
}
