# X3D-Edit 4.0 Distribution Beta Testing

Prerequisite for operation: Java JDK 17 (or later).  Recommended: OpenJDK jdk-19
* [https://openjdk.org](https://openjdk.org)

The following files are available for testing:

* 189065217 Oct 10 11:30 [x3deditmodulesuite.zip](x3deditmodulesuite.zip) Zip archive
* 188984320 Oct 10 11:30 [x3deditmodulesuite-linux.sh](x3deditmodulesuite-linux.sh) Linux installer
* 188906342 Oct 10 11:30 [x3deditmodulesuite-macosx.tgz](x3deditmodulesuite-macosx.tgz) Mac installer
* 189380973 Oct 10 11:30 [x3deditmodulesuite-windows.exe](x3deditmodulesuite-windows.exe) Windows installer

Following installation, a modification is needed to point to your locally installed Java JDK.
For example, in file
*  <code>Desktop/temp/x3deditmodulesuite/x3deditmodulesuite.conf</code>

... after <code>jdkhome="/path/to/jdk"</code> add the following, using your actual local path:
* <code>jdkhome="C:\Program Files\Java\openjdk\jdk-19"</code>
