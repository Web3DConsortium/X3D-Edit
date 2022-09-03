/*
Copyright (c) 2008-2021 held by the author(s) .  All rights reserved.

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

/**
 * Description: Create points along circumference of unit circle to create 3D capsule
 * Filename:    NsidedCapsule.java
 * @author      Don Brutzman
 * Identifier:  NsidedCapsule.java
 * Created:     1 November 2014
 * Reference:   http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/CapsuleGenerator.x3d 
 * Reference:   http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/CapsuleGenerator.js
 */

package org.web3d.x3d;

import java.text.DecimalFormat;

/**
 * Create a string array of points to draw an n-sided 3D capsule
 * @author brutzman
 */
public class NsidedCapsule {

    final int defaultSegmentCount = 24;
    DecimalFormat   formatPrecision3 = new DecimalFormat ("#0.000");
    DecimalFormat   formatPrecision4 = new DecimalFormat ("#0.0000");
    DecimalFormat   formatPrecision  = formatPrecision3;
    
    private String element1, element2;

public String capsuleGeometry  (int numberOfPoints, float radius, float height, int numberOfLevels, String geometryType, 
                                boolean top, boolean side, boolean bottom)
{
    return capsuleGeometry (numberOfPoints, radius, height, numberOfLevels, geometryType, top, side, bottom, false, 0);
}
public String capsuleGeometry  (int numberOfPoints, float radius, float height, int numberOfLevels, String geometryType,
                                boolean top, boolean side, boolean bottom, 
                                boolean appendCommas, int lineBreakInterval)
{    
    // reset, defer assigning/sending any of the output events until done constructing
    StringBuilder newPointArray = new StringBuilder();
    StringBuilder newIndexArray = new StringBuilder();
    StringBuilder shapeBuilder  = new StringBuilder();
    
    int indexEndCapOffset;
    int        pointCount = 0;
        
    // top endcap ========================================
    
    for (int vIndex = 0; vIndex <= numberOfLevels; vIndex++) //   vertical index
    {
              double vAngle = (Math.PI / 2.0) * vIndex / numberOfLevels; //  [0..90] degrees total
        double radiusFactor = Math.cos(vAngle); // decreases with increasing vAngle, [1..0]
   double levelHeightFactor = Math.sin(vAngle); // increases with increasing vAngle, [0..1]

        for (int hIndex = 0; hIndex < numberOfPoints; hIndex++) // horizontal index
        {
            double hAngle = 2.0 * Math.PI * hIndex / numberOfPoints;  // horizontal angle, [0..360) degrees total
            
            if ((vIndex == 0) || (top == true)) // top points for drawing the side are handled by initial level of vIndex
            {
                // (dimensionality == SHAPE.ARRAY_3D_XZ_PLANE)
                // height for each endcap is 1/4 of total height, height for sides is 1/2 of total height
                // https://www.web3d.org/documents/specifications/19777-1/V3.3/Part1/functions.html#MFVec3f
                // append new value to end of array:
                newPointArray.append( Math.sin(hAngle)  * radius * radiusFactor).append(" ")
                             .append((levelHeightFactor * height / 4.0) + (height / 4.0)).append(" ")
                             .append( Math.cos(hAngle)  * radius * radiusFactor).append(" ");
                pointCount++;
            }
            
            if ((hIndex > 0) && (vIndex > 0)) // wait for second/second loop before starting to define polygon indices
            {
                if (top == true) // polygons only get indexed when top is drawn
                {
                    // https://www.web3d.org/documents/specifications/19777-1/V3.3/Part1/functions.html#MFInt32
                    // append new values to end of array, follow right-hand rule for outward-facing normals:
                    newIndexArray.append(( vIndex      * numberOfPoints) + (hIndex    )).append(" ")
                                 .append(( vIndex      * numberOfPoints) + (hIndex - 1)).append(" ")
                                 .append(((vIndex - 1) * numberOfPoints) + (hIndex - 1)).append(" ")
                                 .append(((vIndex - 1) * numberOfPoints) + (hIndex    )).append(" ")
                                 .append(-1).append(" "); // end of polygon
                }

                if ((hIndex == (numberOfPoints - 1)) && (top == true)) // add another polygon at end to stitch seam
                {
                    newIndexArray.append(( vIndex      * numberOfPoints) + (0)).append(" ")
                                 .append(( vIndex      * numberOfPoints) + (hIndex)).append(" ")
                                 .append(((vIndex - 1) * numberOfPoints) + (hIndex)).append(" ")
                                 .append(((vIndex - 1) * numberOfPoints) + (0)).append(" ")
                                 .append(-1).append(" "); // end of polygon
                }
            }
        }
    }
    // bottom endcap ========================================
    
    indexEndCapOffset = pointCount;
    
    for (int vIndex = 0; vIndex <= numberOfLevels; vIndex++) //   vertical index
    {
              double vAngle = (Math.PI / 2.0) * vIndex / numberOfLevels; //  [0..90] degrees total
        double radiusFactor = Math.cos(vAngle); // decreases with increasing vAngle, [1..0]
   double levelHeightFactor = Math.sin(vAngle); // increases with increasing vAngle, [0..1]
     
        for (int hIndex = 0; hIndex < numberOfPoints; hIndex++) // horizontal index
        {
            double hAngle = 2.0 * Math.PI * hIndex / numberOfPoints;  // horizontal angle, [0..360) degrees total
            
            if ((vIndex == 0) || (bottom == true)) // bottom points for drawing the side are handled by initial level of vIndex
            {
                // (dimensionality == SHAPE.ARRAY_3D_XZ_PLANE)
                // height for each endcap is 1/4 of total height, height for sides is 1/2 of total height
                // https://www.web3d.org/documents/specifications/19777-1/V3.3/Part1/functions.html#MFVec3f
                // append new value to end of array:
                 newPointArray.append( Math.sin(hAngle)   * radius * radiusFactor).append(" ")
                              .append(-(levelHeightFactor * height / 4.0) - (height / 4.0)).append(" ")
                              .append( Math.cos(hAngle)   * radius * radiusFactor).append(" ");
                 pointCount++;
            }
            
            if ((hIndex > 0) && (vIndex > 0)) // wait for second/second loop before starting to define polygon indices
            {
                if (bottom == true) // polygons only get indexed when bottom is drawn
                {
                    // https://www.web3d.org/documents/specifications/19777-1/V3.3/Part1/functions.html#MFInt32
                    // append new values to end of array, follow right-hand rule for outward-facing normals:
                    newIndexArray.append(( vIndex      * numberOfPoints) + (hIndex - 1) + indexEndCapOffset).append(" ")
                                 .append(( vIndex      * numberOfPoints) + (hIndex    ) + indexEndCapOffset).append(" ")
                                 .append(((vIndex - 1) * numberOfPoints) + (hIndex    ) + indexEndCapOffset).append(" ")
                                 .append(((vIndex - 1) * numberOfPoints) + (hIndex - 1) + indexEndCapOffset).append(" ")
                                 .append(-1).append(" "); // end of polygon
                }

                if ((hIndex == (numberOfPoints - 1)) && (bottom == true)) // add another polygon at end to stitch seam
                {
                    newIndexArray.append(( vIndex      * numberOfPoints) + (hIndex) + indexEndCapOffset).append(" ")
                                 .append(( vIndex      * numberOfPoints) + (0)      + indexEndCapOffset).append(" ")
                                 .append(((vIndex - 1) * numberOfPoints) + (0)      + indexEndCapOffset).append(" ")
                                 .append(((vIndex - 1) * numberOfPoints) + (hIndex) + indexEndCapOffset).append(" ")
                                 .append(-1).append(" "); // end of polygon
                }
            }
        }
    }
    
    // sides ========================================
    
    if (side == true)
    {
        for (int hIndex = 1; hIndex < numberOfPoints; hIndex++) // horizontal index
        {
                    // append new values to end of array, follow right-hand rule for outward-facing normals:
                    newIndexArray.append((hIndex    )).append(" ")
                                 .append((hIndex - 1)).append(" ")
                                 .append((hIndex - 1) + indexEndCapOffset).append(" ")
                                 .append((hIndex    ) + indexEndCapOffset).append(" ")
                                 .append(-1).append(" "); // end of polygon
        }
        // add another polygon at end to stitch seam
                    newIndexArray.append( 0 ).append(" ")
                                 .append((numberOfPoints - 1)).append(" ")
                                 .append((numberOfPoints - 1) + indexEndCapOffset).append(" ")
                                 .append( 0                   + indexEndCapOffset).append(" ")
                                 .append(-1).append(" "); // end of polygon
    }
    
        switch (geometryType) {
            case "points":
                element1 = "<PointSet ";
                element2 = "</PointSet>";
                break;
            case "lines":
                element1 = "<IndexedLineSet ";
                element2 = "</IndexedLineSet>";
                break;
            default: // (geometryType.equals("polygons")
                element1 = "<IndexedFaceSet creaseAngle='0.785398' solid='true' ";
                element2 = "</IndexedFaceSet>";
                break;
        }

    String capsuleDescription = geometryType + " Capsule consisting of ";
    if (side)   capsuleDescription += (numberOfPoints) + " sides, with ";
    else        capsuleDescription += "no sides, with ";
    if (top)    capsuleDescription += "[" + (numberOfPoints) + " circumference points * (" + (numberOfLevels) + "+1) vertical levels = " + ((numberOfLevels +1) * numberOfPoints) + "] top-cap and ";
    else        capsuleDescription += "no top-cap and ";
    if (!top && bottom)
                capsuleDescription += "[" + (numberOfPoints) + " circumference points * (" + (numberOfLevels) + "+1) vertical levels = " + ((numberOfLevels +1) * numberOfPoints) + "] ";
    if (bottom) capsuleDescription += ((numberOfLevels +1) * numberOfPoints) + " bottom-cap ";
    else        capsuleDescription += "no bottom-cap ";
    if (geometryType.equals("points"))
         capsuleDescription += "points ";
    else capsuleDescription += "quadrilaterals ";
    capsuleDescription += "together making a total of " + pointCount + " Coordinate point values";
    
    shapeBuilder.append("    <!-- ").append(capsuleDescription).append(" -->");
    if  (geometryType.equals("points"))
         shapeBuilder.append("\n    ").append(element1).append(">");
    else shapeBuilder.append("\n    ").append(element1).append("coordIndex='").append(newIndexArray).append("'>");
    shapeBuilder.append("\n      <Coordinate point='").append(newPointArray).append("'/>");
    shapeBuilder.append("\n    ").append(element2);
    
    return shapeBuilder.toString();
}

}
