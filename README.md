# README for X3D-Edit 4.0 Development

This is the developers' documentation page for creation of X3D-Edit 4.0 suite.

* [Netbeans website source in github](https://github.com/apache/netbeans-website)

 
## [NetBeans Platform Learning Trail](https://netbeans.apache.org/kb/docs/platform.html)

* [NetBeans Issues]( https://github.com/apache/netbeans/issues)


### [NetBeans Platform Plugin Quick Start](https://netbeans.apache.org/tutorials/nbm-google.html)

Important starter tutorial.  Initial problems: missing images links, at first appears old but tests OK.

* [Tutorial source](https://github.com/apache/netbeans-website/blob/master/netbeans.apache.org/src/content/tutorials/nbm-google.asciidoc)

* Issue #4491: [Website tutorial on modules missing images](https://github.com/apache/netbeans/issues/4491)


### XML Editor Extension Module

* [XML Editor Extension Module Tutorial](https://netbeans.apache.org/tutorials/nbm-xmleditor.html)

Tutorial section *Specifying the Module’s Dependencies*

** Issue #4492: [XML Editor Extension Module Tutorial erratum](https://github.com/apache/netbeans/issues/4492) 
   I/O APIs not found, (I/O APIs - Swing) worked, other is (I/O API and SPI)

** Note that [multiple XML-related module dependencies](X3dEditModuleSuite/snapshots/XmlModuleDependencies.png) are provided by NetBeans 


## Creating standalone installers

* Tutorial [Native Packaging in NetBeans IDE](https://netbeans.apache.org/kb/docs/java/native_pkg.html)
** [WiX Toolset](https://wixtoolset.org) to create Windows installation packages
** [Inno Setup](https://jrsoftware.org)  free installer for Windows programs
