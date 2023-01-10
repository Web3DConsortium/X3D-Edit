/*
 Copyright (c) 1995-2023 held by the author(s).  All rights reserved.

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
      (https://www.nps.edu and https://my.nps.edu/web/moves)
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

package org.web3d.x3d.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import org.netbeans.spi.autoupdate.KeyStoreProvider;
import org.openide.util.Exceptions;
import org.openide.util.lookup.ServiceProvider;
import org.web3d.x3d.actions.security.BouncyCastleHelper;

/** KeyStore provider service for trusting our NBM self-signed certificate 
 *
 * @author <a href="mailto:tdnorbra@nps.edu?subject=org.web3d.x3d.config.X3DEditKeyStoreProvider">Terry D. Norbraten</a>
 */
@ServiceProvider(service=KeyStoreProvider.class)
public class X3DEditKeyStoreProvider implements KeyStoreProvider {
    
    private KeyStore keystore;;

    @Override
    public KeyStore getKeyStore() {
        try {
            keystore = BouncyCastleHelper.getKeyStore();
        } catch (KeyStoreException | NoSuchProviderException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        // These were set at X3D-Edit startup by the KeyStoreLocator
        String certPath = System.getProperty("javax.net.ssl.trustStore");
        String certPw   = System.getProperty("javax.net.ssl.trustStorePassword");
        
        InputStream str;
        try {
            str = new FileInputStream(certPath);
            keystore.load(str, certPw.toCharArray());
        } catch (IOException | NoSuchAlgorithmException | CertificateException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        return keystore;
    }
    
    @Override
    public KeyStoreProvider.TrustLevel getTrustLevel() {
        return KeyStoreProvider.TrustLevel.TRUST; // we sign this module, therefore you can trust us ... ;)
    }

} // end class file X3DEditKeyStoreProvider
