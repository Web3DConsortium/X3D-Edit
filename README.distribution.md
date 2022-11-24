<img align='right' width='200' src="https://www.web3d.org/x3d/content/examples/images//oss-rising-star-white.svg"/>
<!--
https://sourceforge.net/p/x3d/admin/files/badges/
-->

# X3D-Edit 4.0 Beta Distribution, 24 November 2022

<!-- posted at https://sourceforge.net/projects/x3d/files -->

X3D-Edit is a free, open-source Extensible 3D (X3D) Graphics authoring tool for simple high-quality authoring, editing, import/export, validation and viewing of X3D scenes.

Now testing: [X3D-Edit Update Center](https://savage.nps.edu/X3D-Edit/#Downloads) to simplify module updates once installed.

*User expectations.* Please note that this beta release is initially focused on restoring original X3D-Edit 3.3 functionality.
Once baseline stability is demonstrated on multiple platforms, we expect that further authoring support for 
[X3D4](https://www.web3d.org/x3d4) nodes and features will be added steadily.

Prerequisite for operation: Java JDK 17 LTS (or later).  Suggested:

* Oracle  jdk-19.0.1, latest versions available at [https://www.oracle.com/java/technologies/downloads](https://www.oracle.com/java/technologies/downloads)
* OpenJDK jdk-19.0.1, latest versions available at [https://openjdk.org](https://openjdk.org)

The following files are available for X3D-Edit authoring:

X3D-Edit Install File                                                                                       | Description                                       | Status                                                        | File size | Date        
----------------------------------------------------------------------------------------------------------- | ------------------------------------------------- | ------------------------------------------------------------- | --------- | ------------  
[x3deditmodulesuite.zip](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite.zip)                 | Zip archive for [Java](https://openjdk.java.net)  | Tested satisfactorily, any operating system, recommended      | 187.2 MB | NOV 24  
[org-web3d-x3d-palette.nbm](https://sourceforge.net/projects/x3d/files/org-web3d-x3d-palette.nbm)           | [NetBeans](https://netbeans.org) plugin module    | Tested satisfactorily, any operating system                   |  65.9 MB | NOV 24  
[x3deditmodulesuite-macosx.tgz](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-macosx.tgz)   | Mac installer                                     | Tested satisfactorily                                         | 187.1 MB | NOV 24  
[x3deditmodulesuite-linux.sh](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-linux.sh)       | Linux installer                                   | Tested unsatisfactorily, troubleshooting                      | 187.1 MB | NOV 24 
[x3deditmodulesuite-windows.exe](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-windows.exe) | Windows installer                                 | Tested sometimes fails, Java configuration seems difficult    | 187.5 MB | NOV 24  

SourceForge [download statistics](https://sourceforge.net/projects/x3d/files/stats/timeline) are interesting.

----

The Zip installation seems most reliable and popular, typically working without modifications.

## Troubleshooting

Following installation, a modification might be needed to point to your locally installed Java JDK.
For example, under Windows the following file requires modification.

* Zip installation:  <br /><b><code>C:\downloads\x3deditmodulesuite\etc\x3deditmodulesuite.conf</code></b> (for example)

* Windows installer: <br /><b><code>C:\Program Files\x3deditmodules\etc\x3deditmodulesuite.conf</code></b> (for example)

... then look for <code><b>#</b>jdkhome="/path/to/jdk"</code> in that file and add the following, using your actual local path:

* <code>jdkhome="C:\Program Files\Java\openjdk\jdk-19.0.1"</code> (for example)

Note: if you use default installation directory, then you may need administrator permissions to perform any such modifications.

----

## TODO

* NetBeans-produced Windoes installers need a better solution that needs to bundle Java or else include a properly configured JDK CLASSPATH.
* We plan to setup a NetBeans plugin update capability that might work for all versions. Step by step...

[Changelog](https://sourceforge.net/p/x3d/code/HEAD/log/?path=/www.web3d.org/x3d/tools/X3dEdit4.0/X3dEditModuleSuite/README.distribution.md) provides progress details.
Test reports and feedback welcome.  Please send email to
[Don Brutzman, brutzman@nps.edu](mailto:brutzman at nps.edu%20(Don%20Brutzman)?subject=X3D-Edit%204.0%20Beta%20Testing%20feedback).

Have fun with [X3D-Edit 4.0](https://savage.nps.edu/X3D-Edit)!

[![Download x3d](https://img.shields.io/sourceforge/dt/x3d.svg)](https://sourceforge.net/projects/x3d/files/stats/timeline)
<!--
[![Download x3d](https://img.shields.io/sourceforge/dt/x3d.svg)](https://sourceforge.net/projects/x3d/files/latest/download)
-->
