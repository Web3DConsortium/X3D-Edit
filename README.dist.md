# X3D-Edit 4.0 Beta Distribution Testing

X3D-Edit 4.0 Beta Distribution files are available at

* [https://sourceforge.net/projects/x3d/files](https://sourceforge.net/projects/x3d/files)

Prerequisite for operation: Java JDK 17 LTS (or later).  Recommended: OpenJDK jdk-19

* [https://openjdk.org](https://openjdk.org)

The following files are available for testing:

File size | Date         | File | Description
--------- | ------------ | ---- | -----------
189065217 | Oct 10 11:30 | [x3deditmodulesuite.zip](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite.zip)                 | Zip archive
188984320 | Oct 10 11:30 | [x3deditmodulesuite-linux.sh](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-linux.sh)       | Linux installer
188906342 | Oct 10 11:30 | [x3deditmodulesuite-macosx.tgz](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-macosx.tgz)   | Mac installer
189380973 | Oct 10 11:30 | [x3deditmodulesuite-windows.exe](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-windows.exe) | Windows installer
189380973 | Oct 10 11:30 | [org-web3d-x3d-palette.nbm](https://sourceforge.net/projects/x3d/files/org-web3d-x3d-palette.nbm)           | [Netbeans](https://netbeans.org) plugin module

Following installation, a modification is needed to point to your locally installed Java JDK.
For example, in file

*  <code>Desktop/temp/x3deditmodulesuite/x3deditmodulesuite.conf</code>

... look for <code>jdkhome="/path/to/jdk"</code> in that file and add the following, using your actual local path:

* <code>jdkhome="C:\Program Files\Java\openjdk\jdk-19"</code>

Test reports and feedback welcome.  Please send email to
[Don Brutzman, brutzman@nps.edu](mailto:brutzman at nps.edu%20(Don%20Brutzman)?subject=X3D-Edit%204.0%20Beta%20Testing%20feedback).

Have fun with [X3D-Edit](https://savage.nps.edu/X3D-Edit)!

