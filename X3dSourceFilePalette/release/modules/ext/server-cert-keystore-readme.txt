$Id: readme.txt 6073 2010-07-08 19:56:00Z tnorbraten $

The server-cert-keystore.jks file contains certificates. Nominally it has the 
certificates of certificate authorities, which are used to sign the server 
certificates of https servers. But we can also place self-signed https server 
certificates into it. This allows us to do https downloads by establishing an 
SSL connection to a host. Normally this would be disallowed if the server 
certificate wasn't signed by a well-known certificate authority. 

Java needs two properties to make this work: javax.net.ssl.trustStore and
javax.net.ssl.trustStorePassword, plus a store in which the certificates
are kept. 

1. The certificate store. This is server-cert-keystore.jks. This contains the 
certificates, and is typically password protected. The default password for 
access is "changeit".

To import a https server certificate, you need to obtain the certificate. 
Typically this is saved in the https server's conf directory, e.g.
/var/www/html/conf/dodCert/savage.nps.edu.cer. You can also obtain the server 
certificate from a web browser (various browser have their ways of doing this).  
The latter is the most expedient way of saving the cert to local file.

Once you have the web server certificate, save it to this directory:
release/modules/ext

It looks something like this:

-----BEGIN CERTIFICATE-----
MIID7jCCA1egAwIBAgIBADANBgkqhkiG9w0BAQQFADCBsTELMAkGA1UEBhMCVVMx
EzARBgNVBAgTCkNhbGlmb3JuaWExETAPBgNVBAcTCE1vbnRlcmV5MSIwIAYDVQQK
....
U/x9wcbNB1E+GaCmOUeAKbrhnSa7Z8zJTjx9nOIqB0dmkj6OXdvp1mkw8Esi7fKr
LRyMdVIftU8tSTM2WpIlf65q5jVZrpqqysL7Heni8kMeQ571gHrdWumObL2JKHs7
Hrg=
-----END CERTIFICATE-----

The following will need to be accomplished for both savage.nps.edu and 
savagedefense.nps.navy.mil servers anytime there is a certificate change, or 
change in server configuration, etc.

Note: For the time being, we are intentionally not placing the SavageDefense 
certificate into the key store.

2. Import into the key store. Java can use either the default keystore, 
typically in jre/lib/security/cacerts, or another certificate file that you 
specify. In X3D-Edit this will be 
www.web3d.org/x3d/tools/X3dEdit4.0/X3dEditModuleSuite/X3dSourceFilePalette/release/modules/ext

Note: If a new certificate has been issued, first delete the old information 
with this command:

keytool -delete -alias savage.nps.edu -storepass changeit -keystore server-cert-keystore.jks

You'll probably be prompted for a password. "changeit" is the default. Then, use 
this command to import the new cert:

keytool -import -alias savage.nps.edu -file savage.nps.edu.cer -storepass changeit -keystore server-cert-keystore.jks

3. Will also have to import the JDK's keystore as server-cert-keystore.jks does 
not yet contain these certs:

keytool -importkeystore -v -srckeystore ${java.home)/lib/security/cacerts -storepass changeit -destkeystore server-cert-keystore.jks

This import is so the regular NetBeans Platform can find it's own plugin updates

4. Once the keystore has the server certificate added, you need to tell the JVM 
to use the other-than-default keystore. You can do this by setting a system 
property, or by passing an argument on the command line. The command line 
argument would look like this:

java -Djavax.net.ssl.trustStore=modules/ext/server-cert-keystore.jks -Djavax.net.ssl.trustStorePassword=changeit

In Ant, you can specify this by using the sysproperty element inside of a java 
element:

<property name="keyStoreLocation" location="modules/ext/server-cert-keystore.jks"/>
<property name="keyStorePassword" value="changeit"/>

<property name="runarg1" value="-Djavax.net.ssl.trustStorePassword=${keyStorePassword}"/>
<property name="runarg2" value="-Djavax.net.ssl.trustStore=${keyStoreLocation}"/>

<java classname="${entrypoint}" fork="true">
      <jvmarg value="${runarg1}"/>
      <jvmarg value="${runarg2}"/>
      <classpath refid="run.classpath"/>
</java>

Note: Property setting has already been accomplished in 
org.web3d.x3d.config.Installer.java

5. Since the X3D-Edit modules/plugins are kept behind an https URL, this process 
will allow the X3D-Edit Platform to appropriately "handshake" with the server
hosting updates of these files.