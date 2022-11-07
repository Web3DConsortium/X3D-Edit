# X3D-Edit 4.0 Beta Distribution

<!-- posted at https://sourceforge.net/projects/x3d/files 

X3D-Edit 4.0 Beta Distribution files are available at

* [https://sourceforge.net/projects/x3d/files](https://sourceforge.net/projects/x3d/files)
-->

X3D-Edit is a free, open-source Extensible 3D (X3D) Graphics authoring tool for simple high-quality authoring, editing, import/export, validation and viewing of X3D scenes.

*User expectations.* Please note that this beta release is initially focused on restoring original X3D-Edit 3.3 functionality.
Once baseline stability is demonstrated on multiple platforms, we expect that further authoring support for 
[X3D4](https://www.web3d.org/x3d4) nodes and features will be added steadily.

Prerequisite for operation: Java JDK 17 LTS (or later).  Suggested:

* OpenJDK jdk-19.0.1, latest versions available at [https://openjdk.org](https://openjdk.org)
* Oracle  jdk-19.0.1, latest versions available at [https://www.oracle.com/java/technologies/downloads](https://www.oracle.com/java/technologies/downloads)

The following files are available for X3D-Edit authoring:

X3D-Edit Install File                                                                                       | Description               | Status                                                                     | File size | Date        
----------------------------------------------------------------------------------------------------------- | ------------------------- | -------------------------------------------------------------------------- | --------- | ------------  
[x3deditmodulesuite.zip](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite.zip)                 | Zip archive for [Java](https://openjdk.java.net)  | Tested satisfactorily, operating system agnostic, recommended | 156827561 | Oct 10 14:31  
[org-web3d-x3d-palette.nbm](https://sourceforge.net/projects/x3d/files/org-web3d-x3d-palette.nbm)           | [NetBeans](https://netbeans.org) plugin module    | Tested satisfactorily, operating system agnostic              |  67732688 | Oct 10 14:31  
[x3deditmodulesuite-macosx.tgz](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-macosx.tgz)   | Mac installer             | Tested satisfactorily                                                                 | 156688279 | Oct 10 14:33   
[x3deditmodulesuite-linux.sh](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-linux.sh)       | Linux installer           | Tested unsatisfactorily, troubleshooting                                              | 156775424 | Oct 10 14:32 
[x3deditmodulesuite-windows.exe](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-windows.exe) | Windows installer         | Tested satisfactorily, but Java configuration seems difficult                         | 157172202 | Oct 10 14:32  

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

* A better solution is needed from NetBeans so that the OS installers arrive with either a bundled Java or else a properly configured JDK CLASSPATH.
* We plan to setup a NetBeans plugin update capability that might work for all versions. Step by step...

Test reports and feedback welcome.  Please send email to
[Don Brutzman, brutzman@nps.edu](mailto:brutzman at nps.edu%20(Don%20Brutzman)?subject=X3D-Edit%204.0%20Beta%20Testing%20feedback).

Have fun with [X3D-Edit 4.0](https://savage.nps.edu/X3D-Edit)!
