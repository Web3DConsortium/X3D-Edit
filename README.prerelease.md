<img align='right' width='200' src="https://www.web3d.org/x3d/content/examples/images//oss-rising-star-white.svg"/>
<!--
https://sourceforge.net/p/x3d/admin/files/badges/
-->
<!--
* [SourceForge Markdown Syntax Guide](https://sourceforge.net/nf/markdown_syntax)
-->

# X3D-Edit 4.0 Prerelease, 19 December 2024

<!-- posted at https://sourceforge.net/projects/x3d/files/X3D-Edit%20Pre-Release%20Testing/ -->

This directory shares **pre-release test files** prior to [official X3D-Edit applications release](https://sourceforge.net/projects/x3d/files).

Primary software deployment is found in [NetBeans X3D-Edit plugin](https://plugins.netbeans.apache.org/catalogue/?id=90) autoupdate support.

Backup software deployment is found in  [Maven Central Repository](https://central.sonatype.com/artifact/org.web3d.x3d.tools/x3dedit) distribution support.

These SourceForge pages are used for distributing X3D-Edit application installers.

X3D-Edit Install File                                                                                                                        | Description                                       | Status                                                         | File size | Date        
-------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------- | -------------------------------------------------------------- | --------- | -----------  
[x3deditmodulesuite.zip](https://sourceforge.net/projects/x3d/files/X3D-Edit%20Pre-Release%20Testing/x3deditmodulesuite.zip/download)        | Complete zip archive for [Java JDK](https://openjdk.java.net) execution | Recommended, tested satisfactorily, any operating system       |  202.7 MB | 16 December 2024
[org-web3d-x3d-palette.nbm](https://sourceforge.net/projects/x3d/files/X3D-Edit%20Pre-Release%20Testing/org-web3d-x3d-palette.nbm)           | [NetBeans](https://netbeans.org) plugin module    | NetbBeans&nbsp;24. tested satisfactorily, any operating system |   72.9 MB | 16 December 2024

<!---
[x3deditmodulesuite-windows.exe](https://sourceforge.net/projects/x3d/files/X3D-Edit%20Pre-Release%20Testing/x3deditmodulesuite-windows.exe) | Windows installer                                 | See installation workaround below                              |  206.2 MB | 7 July 2024, pending
[x3deditmodulesuite-macosx.tgz](https://sourceforge.net/projects/x3d/files/X3D-Edit%20Pre-Release%20Testing/x3deditmodulesuite-macosx.tgz)   | Mac installer                                     | Tested unsatisfactorily, TODO&nbsp;future troubleshooting      |  205.7 MB | 7 July 2024, pending
[x3deditmodulesuite-linux.sh](https://sourceforge.net/projects/x3d/files/X3D-Edit%20Pre-Release%20Testing/x3deditmodulesuite-linux.sh)       | Linux installer                                   | Tested unsatisfactorily, TODO&nbsp;future troubleshooting      |  205.8 MB | 7 July 2024, pending
-->

SourceForge [download statistics](https://sourceforge.net/projects/x3d/files/stats/timeline) are interesting.

## How To Do It

Please see our
[Installation video (22:56)](https://www.youtube.com/watch?v=ThToh2YLZeY)
[<img align='right' width='400' src="https://savage.nps.edu/X3D-Edit/images/X3D-EditHowToInstallVideoWelcome800x398.png"/>](https://www.youtube.com/watch?v=ThToh2YLZeY)

**Windows users**: there is an issue in both the zip and [X3D-Edit Windows installer](https://github.com/Web3DConsortium/X3D-Edit/issues/3).
Please note that you will manually need to edit the configuration files to point to your local JDK installation.  For example:

- Using _localadmin_ permissions, set `jdkhome` permissions in configuration file:
- `C:\\Program Files\\x3deditmodulesuite\\etc\\x3deditmodulesuite.conf`
- `jdkhome="C:\Program Files\Java\openjdk\jdk-23.0.1"`

## Source Code is Public on GitHub

X3D-Edit software development has moved to [GitHub](https://github.com/Web3DConsortium/X3D-Edit).

Note: SourceForge is excellent!  Nevertheless, in order to conform with trusted-plugin requirements on the 
[Apache NetBeans Plugin Portal](https://plugins.netbeans.apache.org/catalogue/?id=90).
we have moved source code development to [GitHub](https://github.com/Web3dConsortium/X3D-Edit) and 
[Maven central repository](https://central.sonatype.com/artifact/org.web3d.x3d.tools/x3dedit).

## Troubleshoot Netbeans

If things don't work "out of the box" for you, please let us know.

<!--
here are some further tips.

If you have an earlier X3D-Edit build installed earlier, please reinstall your zip.

NetBeans users troubleshooting certificate permissions can also download and install the following NetBeans plugin which provides end-user more positive control 
when certificate problems occur (such as automated quarterly updates).

* [ssl_certificate_exception-0.17.nbm](SSL Certificate Exception plugin) ([documentation](https://plugins.netbeans.apache.org/catalogue/?id=20)) while we currently use self-signed certificates.  This helps with automaticupdate capabilities.
-->

[Changelog](https://sourceforge.net/p/x3d/code/HEAD/log/?path=/www.web3d.org/x3d/tools/X3dEdit4.0/X3dEditModuleSuite/README.prerelease.md) provides progress details.
Test reports and feedback welcome.  Please send email to
[Don Brutzman, brutzman@nps.edu](mailto:brutzman at nps.edu%20(Don%20Brutzman)?subject=X3D-Edit%204.0%20pre-release%20testing%20feedback).

Have fun with [X3D-Edit 4.0](https://savage.nps.edu/X3D-Edit)!

[![Download x3d](https://img.shields.io/sourceforge/dm/x3d.svg)](https://sourceforge.net/projects/x3d/files/stats/timeline)
<!--
[![Download x3d](https://img.shields.io/sourceforge/dm/x3d.svg)](https://sourceforge.net/projects/x3d/files/latest/download)
-->
