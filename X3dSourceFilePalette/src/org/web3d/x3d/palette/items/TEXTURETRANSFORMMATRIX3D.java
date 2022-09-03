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

import java.util.Arrays;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DTextureCoordinateNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * TEXTURETRANSFORMMATRIX3D.java
 * Created on 20 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TEXTURETRANSFORMMATRIX3D extends X3DTextureCoordinateNode
{
  private SFFloat[][] matrix, matrixDefault;
  private boolean     insertCommas, insertLineBreaks = false;

  public TEXTURETRANSFORMMATRIX3D()
  {
  }

  @Override
  public String getElementName()
  {
    return TEXTURETRANSFORMMATRIX3D_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] sa = parseX(TEXTURETRANSFORMMATRIX3D_ATTR_MATRIX_DFLT);
        
    matrix        = new SFFloat[4][4];
    matrixDefault = new SFFloat[4][4]; // must instantiate separately
    
    int index = 0;
    for(int row=0; row < 4; row++)
      for(int col=0; col < 4; col++)
      {
          matrix[row][col] = matrixDefault[row][col] = buildSFFloat(sa[index]);
          index++;
      }
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(TEXTURETRANSFORMMATRIX3D_ATTR_MATRIX_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      if (sa.length != 16)
      {
          System.out.println ("Illegal number of matrix values for TextureTransformMatrix3D:  found " + sa.length + ", need 16");
          System.out.println (attr.getValue().trim());
      }
      matrix = makeSFFloatArray(sa,4);
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
//    else
//      matrix = matrixDefault; // default array
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTURETRANSFORMMATRIX3DCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (TEXTURETRANSFORMMATRIX3D_ATTR_MATRIX_REQD || !Arrays.equals(makeStringArray(matrix),makeStringArray(matrixDefault)))
    {
      sb.append(" ");
      sb.append(TEXTURETRANSFORMMATRIX3D_ATTR_MATRIX_NAME);
      sb.append("='");
      sb.append(formatFloatArray(matrix, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }

  public String[][] getMatrix()
  {
    if(matrix != null && matrix.length > 0)
         return makeStringArray(matrix);
    else return new String[][]{
        {"1","0","0","0"},
        {"0","1","0","0"},
        {"0","0","1","0"},
        {"0","0","0","1"},}; // something to start with
  }

  @SuppressWarnings("ManualArrayToCollectionCopy")
  public void setMatrix(String[][] saa)
  {
      // ensure matrix size wasn't reset while reading partial array from DOM
      matrix        = new SFFloat[4][4];
      
      if ((saa == null) || (saa.length == 0)) // empty matrix (i.e. XML default) is identity matrix
      {
        for(int row=0; row < 4; row++)
          for(int col=0; col < 4; col++)
          {
              matrix[row][col] = matrixDefault[row][col];
          }
        return;
      }
      
      if ((saa.length != 4) || (saa[0].length != 4))
      {
          System.err.println("Illegal matrix size for TextureTransformMatrix3D (need 4x4 = 16 values), performing partial save...");
          for (int row = 0; row < saa.length; row++)
          {
              System.err.print("   ");
              for (int col = 0; col < saa[0].length; col++)
              {
                  System.err.print(saa[row][col] + " ");
              }
              System.err.println();
          }
      }
      
      for (int row = 0; row < 4; row++)
      {
          for (int col = 0; col < 4; col++)
          {
              if ((row > saa.length - 1) || (saa[0].length == 0) || (col > saa[0].length - 1))
                   matrix[row][col] = new SFFloat("0.0"); // no value provided, use 0
              else matrix[row][col] = buildSFFloat(saa[row][col]);
          }
      }
  }

  public boolean isNil()
  {
    return !(matrix != null && matrix.length>0);
  }

    /**
     * @return the insertCommas value
     */
    public boolean isInsertCommas() {
        return insertCommas;
    }

    /**
     * @param insertCommas the insertCommas value to set
     */
    public void setInsertCommas(boolean insertCommas) {
        this.insertCommas = insertCommas;
    }

    /**
     * @return the insertLineBreaks value
     */
    public boolean isInsertLineBreaks() {
        return insertLineBreaks;
    }

    /**
     * @param insertLineBreaks the insertLineBreaks value to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }

}
