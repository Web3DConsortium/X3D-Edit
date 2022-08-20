# README for X3D-Edit 4.0 Development

This is the developers' documentation page for creation of X3D-Edit 4.0 suite.

* [README.md](https://sourceforge.net/p/x3d/code/HEAD/tree/www.web3d.org/x3d/tools/X3dEdit4.0/X3dEditModuleSuite/README.md)
* [README.md source](https://svn.code.sf.net/p/x3d/code/www.web3d.org/x3d/tools/X3dEdit4.0/X3dEditModuleSuite/README.md)

## [NetBeans Platform Plugin Quick Start](https://netbeans.apache.org/tutorials/nbm-google.html)

"Welcome to Apache NetBeans plugin development!"
Important starter tutorial.  Initial problems: missing images links, at first appears old but tests OK.

* [Netbeans website source in github](https://github.com/apache/netbeans-website)

* [Tutorial source](https://github.com/apache/netbeans-website/blob/master/netbeans.apache.org/src/content/tutorials/nbm-google.asciidoc)

* Issue #4491: [Website tutorial on modules missing images](https://github.com/apache/netbeans/issues/4491)

 TODO: retest tutorial once images are restored, provide feedback.


## [NetBeans Platform Learning Trail](https://netbeans.apache.org/kb/docs/platform.html)

"Comprehensive tutorials that highlight a wide range of Apache NetBeans APIs for a variety of application types."

* [NetBeans Issues](https://github.com/apache/netbeans/issues)



### XML Editor Extension Module

* [XML Editor Extension Module Tutorial](https://netbeans.apache.org/tutorials/nbm-xmleditor.html)

Tutorial section *Specifying the Module’s Dependencies*

** Issue #4492: [XML Editor Extension Module Tutorial erratum](https://github.com/apache/netbeans/issues/4492) 
   I/O APIs not found, (I/O APIs - Swing) worked, other is (I/O API and SPI)

** Note that [multiple XML-related module dependencies](X3dEditModuleSuite/snapshots/XmlModuleDependencies.png) are provided by NetBeans 


## Creating standalone installers

Once the X3D-Edit 4.0 module suite is working as a Netbeans plugin, this tutorial shows how to create installer executables.

* Tutorial [Native Packaging in NetBeans IDE](https://netbeans.apache.org/kb/docs/java/native_pkg.html)
** [WiX Toolset](https://wixtoolset.org) to create Windows installation packages
** [Inno Setup](https://jrsoftware.org)  free installer for Windows programs
