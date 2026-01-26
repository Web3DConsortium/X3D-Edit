<img align='right' width='200' src="https://www.web3d.org/x3d/content/examples/images//oss-rising-star-white.svg"/>
<!--
https://sourceforge.net/p/x3d/admin/files/badges/
-->
<!--
* [SourceForge Markdown Syntax Guide](https://sourceforge.net/nf/markdown_syntax)
-->

# X3D-Edit Authoring Tool for Extensible 3D (X3D) Graphics

[X3D-Edit](https://www.web3d.org/x3d/tools/X3D-Edit/X3D-Edit.html) is a free, open-source Extensible 3D (X3D) Graphics authoring tool for simple high-quality authoring, editing, import/export, validation and viewing of X3D scenes.


## X3D-Edit 4.0 Distribution, 25 January 2026, interim release

<!-- posted at https://sourceforge.net/projects/x3d/files -->


This directory shares **official release files** as part of official X3D-Edit applications release.

Primary module deployment is found in [NetBeans X3D-Edit plugin portal](https://plugins.netbeans.apache.org/catalogue/?id=90) autoupdate support.    

Backup module deployment is found in  [Maven Central Repository](https://central.sonatype.com/artifact/org.web3d.x3d.tools/x3dedit/versions) distribution support.

X3D-Edit Install File                                                                                                                        | Description                                       | Status                                                         | File size | Date        
-------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------- | -------------------------------------------------------------- | --------- | -----------  
[org-web3d-x3d-palette.nbm](https://sourceforge.net/projects/x3d/files/org-web3d-x3d-palette.nbm)           | [NetBeans](https://netbeans.org) plugin module    | Build using NetBeans&nbsp;28. Tested satisfactorily, any operating system |   137.2 MB | 25 January 2026
[x3deditmodulesuite.zip](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite.zip/download)        | Complete zip archive for [Java JDK](https://openjdk.java.net) execution | Must set jdkhome in netbeans.conf       |  260.7 MB | 25 February 2025

<!---
[x3deditmodulesuite-windows.exe](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-windows.exe) | Windows installer                                 | See installation workaround below                              |  206.2 MB | 7 July 2024, pending
[x3deditmodulesuite-macosx.tgz](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-macosx.tgz)   | Mac installer                                     | Tested unsatisfactorily, TODO&nbsp;future troubleshooting      |  205.7 MB | 7 July 2024, pending
[x3deditmodulesuite-linux.sh](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-linux.sh)       | Linux installer                                   | Tested unsatisfactorily, TODO&nbsp;future troubleshooting      |  205.8 MB | 7 July 2024, pending
-->
SourceForge [update-version statistics](https://sourceforge.net/projects/x3d/files/stats/timeline) are interesting (over 3,000), as are
NetBeans Plugin Portal [deployment statistics](https://plugins.netbeans.apache.org/catalogue/?id=90) (over 29,000).

## How To Do It

The easiest way to use X3D-Edit is to install [NetBeans](https://netbeans.apache.org), then select _NetBeans > Tools > Plugins > Available Plugins_, and then select the X3D-Edit plugin.

For full details on updates, please see our 
[X3D-Edit Installation Video (22:56)](https://www.youtube.com/watch?v=ThToh2YLZeY)
[<img align='right' width='400' src="https://www.web3d.org/x3d/tools/X3D-Edit/images/X3D-EditHowToInstallVideoWelcome800x398.png"/>](https://www.youtube.com/watch?v=ThToh2YLZeY)

## Source Code available on GitHub

X3D-Edit software development is now maintained at [Web3D Consortium GitHub](https://github.com/Web3DConsortium/X3D-Edit).

Note: SourceForge is excellent!  Nevertheless, in order to conform with trusted-plugin requirements on the 
[Apache NetBeans Plugin Portal](https://plugins.netbeans.apache.org/catalogue/?id=90).
we have moved source code development to [GitHub](https://github.com/Web3dConsortium/X3D-Edit) and 
[Maven central repository](https://central.sonatype.com/artifact/org.web3d.x3d.tools/x3dedit).

## Standalone Application Currently Not Available

For Windows users: there is an issue in both the zip and [X3D-Edit Windows installer](https://github.com/Web3DConsortium/X3D-Edit/issues/3) and our current testing shows this does not work.  We will track ongoing NetBeans progress to restore this capability.

If you want to try the standalone application on another system, please note that you will manually need to edit the configuration files to point to your local JDK installation.  For example:

- Using _localadmin_ permissions, set `jdkhome` permissions in configuration file:
- `C:\\Program Files\\x3deditmodulesuite\\etc\\x3deditmodulesuite.conf`
- `jdkhome="C:\Program Files\Java\openjdk\jdk-25.0.2"`

## Troubleshooting NetBeans

If things don't work "out of the box" for you, please let us know.

[Changelog](https://sourceforge.net/p/x3d/code/HEAD/log/?path=/www.web3d.org/x3d/tools/X3dEdit4.0/X3dEditModuleSuite/README.prerelease.md) provides progress details.
Test reports and feedback welcome.  Please send email to
[Don Brutzman, don.brutzman@gmail.com](mailto:don.brutzman at gmail.com%20(Don%20Brutzman)?subject=X3D-Edit%204.0%20SourceForge%20release%20feedback).

Have fun with [X3D-Edit 4.0](https://www.web3d.org/x3d/tools/X3D-Edit)!

[![Download x3d](https://img.shields.io/sourceforge/dm/x3d.svg)](https://sourceforge.net/projects/x3d/files/stats/timeline)
<!--
[![Download x3d](https://img.shields.io/sourceforge/dm/x3d.svg)](https://sourceforge.net/projects/x3d/files/latest/download)
-->