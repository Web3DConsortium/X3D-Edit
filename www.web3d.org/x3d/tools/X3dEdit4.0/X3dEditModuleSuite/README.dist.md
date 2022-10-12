# X3D-Edit 4.0 Beta Distribution Testing

X3D-Edit 4.0 Beta Distribution files are available at

* [https://sourceforge.net/projects/x3d/files](https://sourceforge.net/projects/x3d/files)

Prerequisite for operation: Java JDK 17 LTS (or later).  Recommended: OpenJDK jdk-19

* [https://openjdk.org](https://openjdk.org)

The following files are available for testing:

File size | Date         | File                                                                                                        | Description       | Status
--------- | ------------ | ----------------------------------------------------------------------------------------------------------- | ----------------- | ------
 67732688 | Oct 10 14:31 | [org-web3d-x3d-palette.nbm](https://sourceforge.net/projects/x3d/files/org-web3d-x3d-palette.nbm)           | [NetBeans](https://netbeans.org) plugin module | Tested sat, operating system agnostic
156827561 | Oct 10 14:31 | [x3deditmodulesuite.zip](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite.zip)                 | Zip archive       | Tested sat, operating system agnostic
156775424 | Oct 10 14:32 | [x3deditmodulesuite-linux.sh](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-linux.sh)       | Linux installer   | Tested unsat, troubleshooting
156688279 | Oct 10 14:33 | [x3deditmodulesuite-macosx.tgz](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-macosx.tgz)   | Mac installer     | Tested sat
157172202 | Oct 10 14:32 | [x3deditmodulesuite-windows.exe](https://sourceforge.net/projects/x3d/files/x3deditmodulesuite-windows.exe) | Windows installer | Tested sat

Important: following installation, a modification is needed to point to your locally installed Java JDK.
For example, under Windows the following file requires modification.  (If you use default installation directory, 
then you may need administrator permissions to perform any such modifications.)

* Windows installer: <br /><b><code>C:\Program Files\x3deditmodules\etc\x3deditmodulesuite.conf</code></b>
* Zip installation:  <br /><b><code>C:\downloads\x3deditmodulesuite\etc\x3deditmodulesuite.conf</code></b>

... then look for <code>jdkhome="/path/to/jdk"</code> in that file and add the following, using your actual local path:

* <code>jdkhome="C:\Program Files\Java\openjdk\jdk-19"</code>

User expectations: note that this beta release is primarily focused on restoring lost X3D-Edit functionality.
Once stability is demonstrated on multiple platforms, we expect that authoring support for 
X3D4 nodes and features will be added steadily.

Test reports and feedback welcome.  Please send email to
[Don Brutzman, brutzman@nps.edu](mailto:brutzman at nps.edu%20(Don%20Brutzman)?subject=X3D-Edit%204.0%20Beta%20Testing%20feedback).

Have fun with [X3D-Edit 4.0](https://savage.nps.edu/X3D-Edit)!
