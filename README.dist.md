File size | Date         | # X3D-Edit 4.0 Beta Distribution Testing

<!-- posted at https://sourceforge.net/projects/x3d/files -->

X3D-Edit 4.0 Beta Distribution files are available at

* [https://sourceforge.net/projects/x3d/files](https://sourceforge.net/projects/x3d/files)

*User expectations.* Please note that this beta release is initially focused on restoring original X3D-Edit 3.3 functionality.
Once stability is demonstrated on multiple platforms, we expect that authoring support for 
[X3D4](https://www.web3d.org/x3d4) nodes and features will be added steadily.

Prerequisite for operation: Java JDK 17 LTS (or later).  Recommended: OpenJDK jdk-19, latest versions available at [https://openjdk.org](https://openjdk.org)

The following files are available for X3D-Edit authoring:

X3D-Edit Install File                                                                                       | Description               | Status                                                        | File size | Date        
----------------------------------------------------------------------------------------------------------- | ------------------------- | ------------------------------------------------------------- | --------- | ------------  
[org-web3d-x3d-palette.nbm](https://sourceforge.net/projects/x3d/files/org-web3d-x3d-palette.nbm)           | [NetBeans](https://netbeans.org) plugin module    | Tested sat, operating system agnostic |  67732688 | Oct 10 14:31  
[x3deditmodulesuite.zip](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite.zip)                 | Zip archive for [Java](https://openjdk.java.net)  | Tested sat, operating system agnostic | 156827561 | Oct 10 14:31  
[x3deditmodulesuite-linux.sh](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-linux.sh)       | Linux installer           | Tested unsat, troubleshooting                                 | 156775424 | Oct 10 14:32 
[x3deditmodulesuite-macosx.tgz](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-macosx.tgz)   | Mac installer             | Tested sat                                                    | 156688279 | Oct 10 14:33   
[x3deditmodulesuite-windows.exe](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-windows.exe) | Windows installer         | Tested sat                                                    | 157172202 | Oct 10 14:32  

----

Important: following installation, a modification is needed to point to your locally installed Java JDK.
For example, under Windows the following file requires modification.

* Windows installer: <br /><b><code>C:\Program Files\x3deditmodules\etc\x3deditmodulesuite.conf</code></b> (for example)
* Zip installation:  <br /><b><code>C:\downloads\x3deditmodulesuite\etc\x3deditmodulesuite.conf</code></b> (for example)

... then look for <code>jdkhome="/path/to/jdk"</code> in that file and add the following, using your actual local path:

* <code>jdkhome="C:\Program Files\Java\openjdk\jdk-19"</code> (for example)

Note: if you use default installation directory, then you may need administrator permissions to perform any such modifications.

----

*TODO*. A better solution is needed from NetBeans so that the OS installers arrive with either a bundled Java or else a properly configured JDK CLASSPATH.

Test reports and feedback welcome.  Please send email to
[Don Brutzman, brutzman@nps.edu](mailto:brutzman at nps.edu%20(Don%20Brutzman)?subject=X3D-Edit%204.0%20Beta%20Testing%20feedback).

Have fun with [X3D-Edit 4.0](https://savage.nps.edu/X3D-Edit)!
