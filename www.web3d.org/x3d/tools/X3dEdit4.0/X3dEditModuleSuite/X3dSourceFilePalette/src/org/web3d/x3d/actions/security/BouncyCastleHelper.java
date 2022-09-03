/*
* Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
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
package org.web3d.x3d.actions.security;

import java.awt.event.ActionEvent;

import java.io.IOException;
import java.io.InputStream;

import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.apache.xml.security.exceptions.Base64DecodingException;
import org.apache.xml.security.utils.XMLUtils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import org.openide.util.NbBundle;

import org.web3d.x3d.options.X3dOptions;

/**
 * BouncyCastleHelper.java
 * Created on Jun 25, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
public class BouncyCastleHelper
{
  public static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
  public static final String END_CERT = "-----END CERTIFICATE-----";
  
  private static boolean installed = false;
  
  public static void setup()
  {
    if(!installed) {
      Security.addProvider(new BouncyCastleProvider());
      installed = true;
    }
  }
  
  //////////////////////
  // password eraser code
  // idea:  want to keep the user from having to enter the pw repeated, but not keep it around forever
  // here, we clear it if it's not used for x 
  private static int NULLINATOR_DELAY_IN_SECONDS = 60;
  
  private static char[] baby; // disguise name since we're holding onto it for a few seconds
  
  private static AtomicBoolean babyLock = new AtomicBoolean(false);
  private static boolean LOCKED = true;
  private static boolean FREE = false;
  
  public static void flushPassword()
  {
    if(babyLock.compareAndSet(FREE,LOCKED)) {  // if not already locked  
      baby = null;
      babyLock.set(FREE); // release
    }
  }
  
  /**
   * @param msg to show in dialog
   * @param flush true = disregard saved password
   * @return
   */
  public static char[] getAPassword(String msg)
  {
    while(!babyLock.compareAndSet(FREE, LOCKED)) {
      Thread.yield();
    }
    char[] retPW = null;
    if(baby != null) {
      retPW = baby;
      bumpTimer();
    }
    else {
      if (msg == null)
        msg = org.openide.util.NbBundle.getMessage(BouncyCastleHelper.class, "MSG_EnterPassword"); //"Enter password:"
      final JPasswordField pwF = new JPasswordField(10);
      
      //problem:  I only wanted OK and CANCEL and I want the pwField to have focus when it comes up;
      // first requires confirm dialog, 2nd requires the timer.
      // this is clumsy but works:
      javax.swing.Timer timer = new javax.swing.Timer(250, (ActionEvent ae) -> {
          pwF.requestFocusInWindow();    
      });
      timer.setRepeats(false);
      timer.start();
      
      int ret = JOptionPane.showConfirmDialog(null, pwF, msg, JOptionPane.OK_CANCEL_OPTION);
      if (ret != JOptionPane.CANCEL_OPTION) {
        retPW = pwF.getPassword();
        bumpTimer();
        baby = retPW;
      }
    }   
    babyLock.set(FREE);
    return retPW;
  }
   
  private static Timer nullinator = new Timer("nullinator", true); // daemon
  private static TimerTask nuller;
  
  static class nullinatorTask extends TimerTask
  {
    @Override
    public void run()
    {
      flushPassword();
    }   
  }
  
  private static void bumpTimer()
  {
    if(nuller != null)
      nuller.cancel();
    nullinator.schedule(nuller = new nullinatorTask(),NULLINATOR_DELAY_IN_SECONDS*1000);
  }
  
  /////////////////////
  
  public static KeyStore getKeyStore() throws KeyStoreException,NoSuchProviderException
  {
    return KeyStore.getInstance("BouncyCastle","BC");
  }
  
  public static String getKeyStoreNameExtension()
  {
    return "ks"; // "uber" would be more accurate;
  }
  
  public static SecretKey createSecretKey() throws NoSuchAlgorithmException,NoSuchProviderException    
  {
    KeyGenerator keygen = KeyGenerator.getInstance("DESede","BC");
    keygen.init(192);  // 192 bits for TripleDES, 24 bytes
    return keygen.generateKey();
  }
  
  public static SecretKey readSecretKey(InputStream is) throws IOException,
          InvalidKeyException, NoSuchAlgorithmException, 
          NoSuchProviderException, InvalidKeySpecException,
          Base64DecodingException
  {
    byte[] byteArr = is.readAllBytes();
    XMLUtils.decode(byteArr);
    DESedeKeySpec keySpec = new DESedeKeySpec(byteArr);

    SecretKeyFactory kFact = SecretKeyFactory.getInstance("DESede", "BC");  // look for tripledes
    return kFact.generateSecret(keySpec);
  }
  
  public static String getKeyPairGenerationAlgorithm()
  {
    return "RSA";
  }
 
  public static String getKeyPairSigningAlgorithm()
  {
    return "SHA1WithRSA";
  }
  
  public static long getCertificateValidityInDays()
  {
    return 5L * 52 * 7; // 5 years
  }
  
  public static String getCerficateDistinguishedName()
  {
    return
      "CN=X3D-Edit user, " +
      "OU=Savage Research Group / MOVES Institute, " +
      "O=Naval Postgraduate School, " +
      "L=Monterey, "+
      "S=California, "+
      "C=US";
  }
  
  public static int getKeyPairKeySize()
  {
    return 1024;
  }
  
  public static long getNextCertificateSerialNumber()
  {
    long last = X3dOptions.getLastCertificateSerialNum();
    last++;
    X3dOptions.setLastCertificateSerialNum(last);
    return last;   
  }
  
  public static class KeystorePasswordException extends Exception
  {
    public KeystorePasswordException(String msg)
    {
      super(msg);
    }
  }
  public static ManageKeyStorePanel buildManageKeyPanel(char[] pw) throws Exception
  {
    do {
      try {
        ManageKeyStorePanel keyPan = new ManageKeyStorePanel(pw);
        return keyPan;
      }
      catch (KeystorePasswordException pwEx) {  // other exceptions are caught up the stack
        flushPassword();
        pw=null;
        if(!tryAgain())
          return null;
      }
    }
    while (true);
    
  }
  
  public static SelectKeyPanel buildSelectKeyPanel(int type) throws Exception
  {
    do {
      try {
        SelectKeyPanel keyPan = new SelectKeyPanel(type);
        return keyPan;
      }
      catch (KeystorePasswordException pwEx) {  // other exceptions are caught up the stack
        flushPassword();
        if(!tryAgain())
          return null;
      }
    }
    while (true);
  }
  
    private static boolean tryAgain()
  {
    return JOptionPane.showConfirmDialog(
            null, // parent
            NbBundle.getMessage(BouncyCastleHelper.class, "MSG_PasswordTryAgain"), // msg
            NbBundle.getMessage(BouncyCastleHelper.class, "MSG_KeystoreInitError"), // title
            JOptionPane.YES_NO_OPTION, // which buttons
            JOptionPane.ERROR_MESSAGE)
    == JOptionPane.YES_OPTION;   
  }
}
