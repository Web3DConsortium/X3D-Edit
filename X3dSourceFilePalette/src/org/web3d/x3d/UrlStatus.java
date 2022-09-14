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

import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import java.util.Enumeration;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;

import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.protocol.Protocol;

/**
 * UrlStatus.java
 * Created on Aug 22, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class UrlStatus
{
  public static final int MISMATCHED_CASE = -2;
  public static final int INDETERMINATE   = -1;
  public static final int SUCCESS         = HttpStatus.SC_OK;
  public static final int FAILURE         = HttpStatus.SC_NOT_FOUND;
  
  private static Protocol myhttpsProtocolHandler=null;
  
  public static Object checkStatus(String url, File baseDir, UrlStatusListener listener)
  {
    Runner r = new Runner();
    Thread thr = new Thread(r,"org.web3d.x3d.UrlStatus");
    thr.setPriority(Thread.NORM_PRIORITY);
    ActionPacket packet = new ActionPacket(url, baseDir, listener, thr);
    r.setData(packet);
    thr.start();
    return packet;
  }

  static class Runner implements Runnable
  {
    ActionPacket packet;

    public void setData(ActionPacket packet)
    {
      this.packet = packet;
    }

    @Override
    public void run()
    {
      String protocol;
      URL url=null;
      try {
        url = new URL(packet.url);
        protocol = url.getProtocol();
      }
      catch(MalformedURLException murl) {
        protocol = "file";  // try this
      }
      
      try {        
        if (protocol.equalsIgnoreCase("http"))
          finish(handleHttp());
        else if (protocol.equalsIgnoreCase("https"))
          finish(handleHttps());
        else if (protocol.equalsIgnoreCase("ftp"))
          finish(handleFtp());
        else if (protocol.equalsIgnoreCase("file"))
          finish(handleFile(url, packet.url)); // also pass along original filename to check case sensitivity
        else {
          finish(INDETERMINATE);
        }
      }
      catch (UnknownHostException uhe) {
        finish(isNetworkUp()?FAILURE:INDETERMINATE);
//        return;
      }
      catch (LinkageError le) {
        System.err.println("Linkage error in UrlStatus, upgrade httpclient and sslsocketfactory");
        System.err.println(le.getLocalizedMessage());
        finish(INDETERMINATE);
//        return;
      }
      catch(Exception ex) {
        System.err.println(ex.getClass().getSimpleName()+" in UrlStatus");
        System.err.println(ex.getLocalizedMessage());
        finish(INDETERMINATE);
//        return;
      }
//      catch (Throwable ex) {
//        if(ex instanceof UnknownHostException) {
//          if(!isNetworkUp()) {
//            finish(INDETERMINATE);
//            return;
//          }
//        }
//        if(ex instanceof LinkageError) {  // catching here temporarily
//          System.err.println("Linkage error in UrlStatus, upgrade httpclient and sslsocketfactory");
//          finish(INDETERMINATE);
//          return;
//        }
//        if(ex instanceof Error) {  // indicate serious error, best practice: do not catch
//          throw (Error)ex;
//        }
//        finish(FAILURE);
//      }
//      return;
    }
    
    private int handleHttp() throws Exception
    {
      HttpClient client = new HttpClient();
      HeadMethod head = new HeadMethod(packet.url);
      //head.getParams().setParameter(arg0, head);

      return client.executeMethod(head);
    }
    
    /**
     * This is only necessary to allow self-signed certs to pass.
     * @throws java.lang.Exception
     */
    @SuppressWarnings("deprecation")
    private int handleHttps() throws Exception
    {
      if (myhttpsProtocolHandler == null) {
        myhttpsProtocolHandler = new Protocol("https", new EasySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", myhttpsProtocolHandler);
      }
      return handleHttp();
    }
    
    /**
     * Can try to support, but don't see any uses in scene archives
     * @throws java.lang.Exception
     */
    private int handleFtp() throws Exception
    {
      return UrlStatus.INDETERMINATE;
    }
    
    /**
     * 
     * @param url might be null
     * @throws java.lang.Exception
     */
    private int handleFile(URL url, String originalFilePath) throws Exception
    {
      String filePath;
      String originalFileName = originalFilePath;
      if      (originalFilePath.contains("/"))
               originalFileName = originalFilePath.substring (originalFilePath.lastIndexOf("/")+1);
      else if (originalFilePath.contains("\\"))
               originalFileName = originalFilePath.substring (originalFilePath.lastIndexOf("\\")+1);
     
      File f, parentDirectory;
      if (url != null) {
        // legit file url
        filePath = url.getPath();
        if (filePath.startsWith("/") || filePath.contains(":"))
        {
            f          = new File(filePath);
        }
        else
        {
            f          = new File(packet.baseDir, filePath);
        }
      }
      else {
        // Can't handle the "fragment/ref" here...remove it
        filePath = packet.url;
        int idx = filePath.lastIndexOf('#');
        if (idx > 0)
        {
            filePath = filePath.substring(0, idx); // strip #ViewpointName or #PrototypeName or #Bookmark
        }
        f = new File(packet.baseDir, filePath);
      }
      parentDirectory = new File (f.getParent());
      if (!parentDirectory.isDirectory())
      {
          return UrlStatus.FAILURE; // illegal directory, perhaps from illegal relative path
      }
//      String fileName = f.getName();
//      if (!f.isDirectory())
//           fileName = f.getName();
//      else fileName = "";

        for (File listFile : parentDirectory.listFiles()) {
            String newFileName = listFile.getName();
            if (listFile.isDirectory()) {
                continue;
            }
            if (originalFileName.equalsIgnoreCase(newFileName))
            {
                if (!originalFileName.equals(newFileName))
                    return UrlStatus.MISMATCHED_CASE; // for file
                // check that proper case of directory was provided
                if (!parentDirectory.getCanonicalPath().replace('\\', '/').contains(filePath.substring(0, filePath.lastIndexOf("/" + newFileName)).replace("../", "")))
                    return UrlStatus.MISMATCHED_CASE; // for directory
                
            }
        }
      if (f.exists())
        return UrlStatus.SUCCESS;
      else
        return UrlStatus.FAILURE;
    }
    
    private void finish(int status)
    {
      synchronized(packet) {
        packet.lis.statusIn(packet, status);
      }
      nullOut(packet);
      packet = null;
    }

    private boolean isNetworkUp()
    {
      // is this java 6? if so I can use a couple of new methods
      try {
        Method isUpMethod = NetworkInterface.class.getDeclaredMethod("isUp", (Class[])null);
        Method isLoopback = NetworkInterface.class.getDeclaredMethod("isLoopback", (Class[])null);

        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while(en.hasMoreElements()) {
          NetworkInterface ni = en.nextElement();
          if(isUpMethod.invoke(ni,(Object[])null)==Boolean.TRUE &&     // if at least one is up and not a loopback
             isLoopback.invoke(ni,(Object[])null)==Boolean.FALSE)
            return true;          
        }
        return false;   // found no up interfaces
      }
      catch(NoSuchMethodException | SecurityException | SocketException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        return true;    //  typ, if jdk 1.5, assume up
      }
    }

  }

  private static void nullOut(ActionPacket pkt)
  {
    pkt.thread = null;
    pkt.lis = null;
    pkt.url = null;
  }
  
  public static void stopListener(Object key) // TODO unused?  also overwriting method parameter key?
  {
    synchronized (key) {
      try {
        ((ActionPacket)key).thread.interrupt();
        nullOut((ActionPacket)key);
      }
      catch (Throwable t) {
        System.err.println("UrlStatus.stopListener called with invalid key: " +
                t.getClass().getSimpleName() + ": " + t.getLocalizedMessage());
      }
    }
    key = null;
  }

  public static interface UrlStatusListener
  {
    /**
     * Status receiver.  After this is called, the held listener object will be discarded.
     * @param keyPack
     * @param status One of SUCCESS, FAILURE or BADURL
     */
    public void statusIn(Object keyPack, int status);
  }

  public static class ActionPacket
  {
    String url;
    UrlStatusListener lis;
    Thread thread;
    File baseDir;
    
    ActionPacket(String url, File baseDir, UrlStatusListener lis, Thread t)
    {
      this.url = url;
      this.baseDir = baseDir;
      this.lis = lis;
      this.thread = t;
    }
  }
}
