/*
Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

 * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
 * Neither the names of the Naval Postgraduate School (NPS)
Modeling Virtual Environments and Simulation (MOVES) Institute
(http://www.nps.edu and https://MovesInstitute.nps.edu)
nor the names of its contributors may be used to endorse or
promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 */
package org.web3d.x3d.palette.items;

import java.util.Comparator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.AbstractListModel;
import javax.swing.JComboBox;
import javax.swing.ListModel;
import org.web3d.x3d.types.X3DSchemaData;
import static org.web3d.x3d.types.X3DSchemaData.COMMENT_ELNAME;
import static org.web3d.x3d.types.X3DSchemaData.COMPONENT_ELNAME;
import static org.web3d.x3d.types.X3DSchemaData.CONNECT_ELNAME;
import static org.web3d.x3d.types.X3DSchemaData.DOCTYPE_ELNAME;
import static org.web3d.x3d.types.X3DSchemaData.META_ELNAME;
import static org.web3d.x3d.types.X3DSchemaData.ROUTE_ELNAME;

//import java.util.Comparator;
//import javax.swing.DefaultComboBoxModel;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Collections;
//import java.util.Vector;
//import javax.swing.AbstractListModel;
//import javax.swing.JComboBox;

/**
 * NodeListHelper.java
 * Created on Jul 3, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
//@SuppressWarnings("empty-statement")
public class NodeListHelper
{

/*
  
  static final Vector<String> nodeNamesVector = new Vector<String>();
  
  static {
    try {
      FileSystem fileSystem = FileUtil.getConfigRoot().getFileSystem(); //Repository.getDefault().getDefaultFileSystem();
      FileObject paletteFO = fileSystem.findResource("X3DPalette");
      String classHelper = new String();
      String className   = new String();
      String nodeName    = new String();

//    earlier Enumeration approach did not work, probably due to previously uncaught exception
//    later palette-based approach did not work, probably due to stricter reflection handling
      FileObject[] folderObjects = paletteFO.getChildren();
        for (FileObject folderFO : folderObjects) {
            if (!folderFO.getName().contains("Archive")) // ignore specialty folders
            {
                FileObject[] fileObjects = folderFO.getChildren();
                FileObject fileFO;
                for (FileObject fileObject : fileObjects) {
                    try {
                        fileFO = fileObject;
                        if (fileFO != null) // some palette entries are for special cases
                        {
                            classHelper = fileFO.getNameExt();
                            if (classHelper.endsWith(".xml"))
                            {
                                className = classHelper.substring(0, classHelper.length() - 4).toUpperCase(); // remove .xml
                                Class<?> c = Class.forName("org.web3d.x3d.palette.items." + className);
                                Object obj;
                                Method meth;
                                if (c != null)
                                {
                                    obj  = c.getDeclaredConstructor(c).newInstance(); // TODO failing here
                                    meth = c.getMethod("getElementName", null);  // new Class<?>[]{});
                                    nodeName = (String) meth.invoke(obj, new Object[]{});

                                    if (!excludeFromNodeList(nodeName))
                                    {
                                        if (!nodeNamesVector.contains(nodeName)) nodeNamesVector.add(nodeName);
                                    }
                                }
                            }
                        }
                    }
                    catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e)
                    {
                        // continue building list
                        System.err.println ("Exception building NodeListHelper element list for " + className + ":\n" + e);
                    }
                }
            }
        }
    }
    catch (FileStateInvalidException ex) {
      System.err.println("Program error in NodeListHelper.static block");
    }
    Collections.sort(nodeNamesVector, new AlphabetizationComparator());
    
    nodeListComboBox = new JComboBox<>(nodeNamesVector);
  }
*/
    
  
  private static class AlphabetizationComparator implements Comparator<String>
  {
    @Override
    public int compare(String s1, String s2)
    {
      return s1.compareToIgnoreCase(s2);
    }    
  }
  private final static JComboBox<String> nodeListComboBox = new JComboBox<>(X3DSchemaData.ALL_X3D_NODE_NAMES);
  
  public static JComboBox<String> getNodeListCombo()
  {
    return nodeListComboBox;
  }
  
  public void setSelectedName(String value)
  {
    nodeListComboBox.setSelectedItem(value);
  }
  
  public static DefaultComboBoxModel<String> getNodeListComboModel()
  {
    return new DefaultComboBoxModel<>(X3DSchemaData.ALL_X3D_NODE_NAMES); // nodeNamesVector);
  }
  
  public static ListModel<String> getNodeListModel()
  {
    return new AbstractListModel<String>()
    {
      @Override
      public int getSize() { return X3DSchemaData.ALL_X3D_NODE_NAMES.length; } // nodeNamesVector.size(); }
      @Override
      public String getElementAt(int i) { return X3DSchemaData.ALL_X3D_NODE_NAMES[i]; } // nodeNamesVector.get(i); }
    };
  }
  
  // The following method nops the 2 after that;  don't yet have a handle on editting
  // XML comments with the current code.
  public static boolean excludeFromNodeList(String s)
  {
      boolean exclude = false;
      // no need to offer choices that cannot be valid X3D
      if (s.equals(COMMENT_ELNAME)   ||
          s.equals(DOCTYPE_ELNAME)   ||
//          s.equals(COMPONENT_ELNAME) ||
//          s.equals(META_ELNAME)      ||
//          s.equals(ROUTE_ELNAME)     ||
//          s.equals(CONNECT_ELNAME)   ||
          s.startsWith("SMAL"))
          exclude = true;
      return exclude;
  }
}
