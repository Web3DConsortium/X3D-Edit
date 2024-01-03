# README for X3D-Edit 4.0 Development

<!-- https://github.com/Web3DConsortium/X3D-Edit/blob/master/README.md -->

Got X3D-Edit?

- Application details are found on the [X3D-Edit home page](https://savage.nps.edu/X3D-Edit).

- Primary distribution is via [Apache NetBeans X3D-Edit Plugin](https://plugins.netbeans.apache.org/catalogue/?id=90).

- Executable installers are at [SourceForge X3D-Edit Distribution](https://sourceforge.net/projects/x3d/files).

This file is the developers' documentation page for creation of X3D-Edit 4.0 suite.

----

## Work in Progress

X3D-Edit is now stable and successfully transitioned as part of the NetBeans plugin architecture.

The coming year will see most work focused on implementing addition nodes for [X3D version 4](https://www.web3D.org/x3d4).

We are now tracking work items at [GitHub Issues for X3D-Edit](https://github.com/Web3DConsortium/X3D-Edit/issues).

----

## Resources

* [Tutorial Contribution Guidelines](https://netbeans.apache.org/kb/docs/contributing.html)
* [AsciiDoc](https://asciidoc.org) is a plain text markup language for writing technical content

* Online Javadoc for Netbeans IDE: [Apache NetBeans Development Version Documentation | APIs Overview](https://bits.netbeans.org/dev/javadoc/index.html)
* TODO attaching Javadoc for Netbeans IDE
* [Apache NetBeans API Development](https://netbeans.apache.org/wiki/APIDevelopment.asciidoc)
* [Apache NetBeans DevFaq wiki index](https://netbeans.apache.org/wiki/DevFaqIndex.asciidoc)

* [List of NetBeans-based software](https://en.wikipedia.org/wiki/List_of_NetBeans-based_software) outdated?
* StackOverflow: [How to debug multiple threads/runnables at the same time in netbeans](https://stackoverflow.com/questions/26952986/how-to-debug-multiple-threads-runnables-at-the-same-time-in-netbeans/26960526)

----

## NetBeans Resources


### [NetBeans Platform Learning Trail](https://netbeans.apache.org/kb/docs/platform.html)

"Comprehensive tutorials that highlight a wide range of Apache NetBeans APIs for a variety of application types."

* [NetBeans Issues](https://github.com/apache/netbeans/issues)

* [Netbeans API References](https://netbeans.apache.org/kb/docs/platform.html#API)

* [Top 10 NetBeans APIs](https://www.youtube.com/watch?v=FF5fvHbZxpk) dated but some nuggets found


### [XML Editor Extension Module Tutorial](https://netbeans.apache.org/tutorials/nbm-xmleditor.html)

Tutorial section *Specifying the Module’s Dependencies*

* Issue 4492 [XML Editor Extension Module Tutorial erratum](https://github.com/apache/netbeans/issues/4492)
I/O APIs not found, (I/O APIs - Swing) worked, other is (I/O API and SPI)

* Note that [multiple XML-related module dependencies](X3dEditModuleSuite/snapshots/XmlModuleDependencies.png) are provided by NetBeans

Don used this to create initial code block for X3D-Edit Module Suite.


### [NetBeans Code Snippet Module Tutorial](https://netbeans.apache.org/tutorials/nbm-palette-api1.html)

Has better excerpt of `layer.xml` file.
* Inspect build/classes/META-INF/generated-layer.xml to see what is actually generated using layer.xml and annotations

Creating `layer.xml` file is optional if using annotations instead:

* StackOverflow [Learning NetBeans platform: annotations vs layer.xml file](https://stackoverflow.com/questions/5840648/learning-netbeans-platform-annotations-vs-layer-xml-file)

* StackOverflow [Where to find the main layer.xml file in Netbeans RCP maven project?](https://stackoverflow.com/questions/34825549/where-to-find-the-main-layer-xml-file-in-netbeans-rcp-maven-project)



### [NetBeans Editor Component Palette Module Tutorial](https://netbeans.apache.org/tutorials/nbm-palette-api2.html)

"This tutorial demonstrates how to create a component palette that provides drag-and-drop code snippets for a new file type."

* Issue 4519 [NetBeans Editor Component Palette Module Tutorial missing source code](https://github.com/apache/netbeans/issues/4519)

* Issue 4520 [NetBeans Editor Component Palette Module Tutorial, garbled source](https://github.com/apache/netbeans/issues/4520)

* Issue 4521 [org.openide.util.Exceptions not found by import in tutorial](https://github.com/apache/netbeans/issues/4521) fixed, closed.

Implemented module as `X3dSourceFilePalette`.  Not all names aligned, not all source applied, work ongoing.


### Netbeans Splash Screen

* [How to make Splash Screen in Java using Netbeans with Source Code](https://www.youtube.com/watch?v=tR7nZ2gSNB4)

Note problem failing when turned on by Project setting for X3D-Edit module suite. <- FIXED (TDN)


### [NetBeans Ant-Based Project Type Module Tutorial](https://netbeans.apache.org/tutorials/nbm-projecttypeant.html)

TODO.  Consider if this will help create projects of interest - might be superfluous.

### Using the NetBeans IDE created installer for Windows

Desktop shortcut for Windows. Assumes JAVA_HOME areadly defined in Environment Variables
Right click on shortcut properties and set:
* Target: x3deditmodulesuite64 --jdkhome "%JAVA_HOME%" --console new
* Start in: "C:\Program Files\x3deditmodulesuite\bin"

### Creating standalone installers

Once the X3D-Edit 4.0 module suite is working as a Netbeans plugin, this tutorial shows how to create installer executables.

Tutorial [Native Packaging in NetBeans IDE](https://netbeans.apache.org/kb/docs/java/native_pkg.html)
* [WiX Toolset](https://wixtoolset.org) to create Windows installation packages

* [Inno Setup](https://jrsoftware.org)  free installer for Windows programs
