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
 * Description: Create points along circumference of unit rounded rectangle
 * Filename:    NsidedPolygon.java
 * @author      Don Brutzman
 * Identifier:  NsidedPolygon.java
 * Created:     1 January 2002
 * Revised:     14 January 2011
 * Identifier:  http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09-EventUtilitiesScripting/CircleLines.java
 * Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09-EventUtilitiesScripting/CircleLinesExample.x3d
 * License:     ../license.html
 */

package org.web3d.x3d;

import java.text.DecimalFormat;
import org.web3d.x3d.palette.items.SHAPE;

/**
 * Create a string array of points that draws a 2D rounded rectangle
 * @author brutzman
 */
public class RoundedRectangle {

    DecimalFormat   formatPrecision3 = new DecimalFormat ("#0.000");
    DecimalFormat   formatPrecision4 = new DecimalFormat ("#0.0000");
    DecimalFormat   formatPrecision  = formatPrecision3;
//  private boolean closed = true; // always closed
    
    /** number of sectors subdividing each quadrant */
    private int   sectors = 9; // 10 degrees each
    
    /** percentage of each side for each rounded corner */
    private float   cornerPercentage = 0.1f; // thus default is 10% of each side is rounded
    
    /** radius of each corner, relative to default side length of 1 */
    private float   radius = getCornerPercentage();
    
    /** scale multiplication factor */
    private float   scale = 1.0f;
    
    /** number of points computed */
    private static int numberOfPoints; // verified by computation of points

public String roundedRectanglePointsArray2D  ()
{
    return roundedRectanglePointsArray (1.0f, 1.0f, 0.0f, SHAPE.ARRAY_2D, false, -1);
}

public String roundedRectanglePointsArray2D  (float width, float height)
{
    return roundedRectanglePointsArray (width, height, 0.0f, SHAPE.ARRAY_2D, false, -1);
}

public String roundedRectanglePointsArrayXY  (float width, float height, float depth)
{
    return roundedRectanglePointsArray (width, height, depth, SHAPE.ARRAY_3D_XY_PLANE, false, -1);
}

public String roundedRectanglePointsArrayXZ  (float width, float height, float depth)
{
    return roundedRectanglePointsArray (width, height, depth, SHAPE.ARRAY_3D_XZ_PLANE, false, -1);
}

public String roundedRectanglePointsArrayYZ  (float width, float height, float depth)
{
    return roundedRectanglePointsArray (width, height, depth, SHAPE.ARRAY_3D_YZ_PLANE, false, -1);
}

public String roundedRectanglePointsArray  (float width, float height, float depth, int dimensionality)
{
    return roundedRectanglePointsArray (width, height, depth, dimensionality, false, -1);
}

/** Compute Rounded Rectangle as IndexedFaceSet coordinate points and indices, centered about origin. 
 *  @param width  is positive X dimension (in default dimensionality direction)
 *  @param height is positive Y dimension (in default dimensionality direction)
 *  @param depth  is positiveZ dimension (in default dimensionality direction), or 0 if flat
 *  @param dimensionality is enumeration value indicating 2D/3D and orientation of result
 *  @param appendCommas whether to insert commas between coordIndex values for each polygon and individual points
 *  @param lineBreakInterval if > 0, break lines after each counted set of data points to aid readability
 *  @return source X3D for text insertion
 */
public String roundedRectanglePointsArray  (float width, float height, float depth, int dimensionality, boolean appendCommas, int lineBreakInterval)
{
    StringBuilder pointsBuilder = new StringBuilder();
    numberOfPoints = 0; // not counting inner rectangle, only one level
    
    int circlePoints = 4 * (sectors + 1);
    if (circlePoints <=120)
         formatPrecision  = formatPrecision3;
    else formatPrecision  = formatPrecision4;
    
    if      (width <= 0.0f)
    {
        System.out.println("[RoundedRectangle.roundedRectanglePointsArray] illegal value, width=" + width + ", computation ignored");
        return "";
    }
    else if  (height <= 0.0f)
    {
        System.out.println("[RoundedRectangle.roundedRectanglePointsArray] illegal value, height=" + height + ", computation ignored");
        return "";
    }
    else if  (depth < 0.0f) // may equal zero to achieve 2D effect
    {
        System.out.println("[RoundedRectangle.roundedRectanglePointsArray] illegal value, depth=" + depth + ", computation ignored");
        return "";
    }
    
    int numberOfLlevels = 2;
    if ((dimensionality == SHAPE.ARRAY_2D) || (depth == 0.0f))
        numberOfLlevels = 1;
    
    for (int level = 0; level < numberOfLlevels; level++) // range [0..0] for 2D, or [0..1] for 3D
    {
        double offsetWidth  = ( width / 2.0f) * (1.0f - cornerPercentage);
        double offsetHeight = (height / 2.0f) * (1.0f - cornerPercentage);
        
        double offsetDepth  = 0.0;
        if      (numberOfLlevels == 1)
        {
            offsetDepth  =   0.0; // center
        }
        else if ((numberOfLlevels == 2) && (level == 0))
        {
            offsetDepth  =   depth / 2.0; // above
        }
        else if ((numberOfLlevels == 2) && (level == 1))
        {
            offsetDepth  = - depth / 2.0; // below
        }
        // inner rectangle, see diagram RoundedRectangleDesign.svg
        if ((dimensionality == SHAPE.ARRAY_2D) || (depth == 0.0f))
        {
            pointsBuilder.append(formatPrecision.format((+offsetWidth)  * getScale())).append(" "); // coordIndex 0
            pointsBuilder.append(formatPrecision.format((+offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(0.0f)).append(" ");
            pointsBuilder.append(formatPrecision.format((+offsetWidth)  * getScale())).append(" "); // coordIndex 1
            pointsBuilder.append(formatPrecision.format((-offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(0.0f)).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetWidth)  * getScale())).append(" "); // coordIndex 2
            pointsBuilder.append(formatPrecision.format((-offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(0.0f)).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetWidth)  * getScale())).append(" "); // coordIndex 3
            pointsBuilder.append(formatPrecision.format((+offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(0.0f)).append(" ");
        }
        else if (dimensionality == SHAPE.ARRAY_3D_XY_PLANE)
        {
            pointsBuilder.append(formatPrecision.format((+offsetWidth)  * getScale())).append(" "); // coordIndex 0
            pointsBuilder.append(formatPrecision.format((+offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((+offsetWidth)  * getScale())).append(" "); // coordIndex 1
            pointsBuilder.append(formatPrecision.format((-offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetWidth)  * getScale())).append(" "); // coordIndex 2
            pointsBuilder.append(formatPrecision.format((-offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetWidth)  * getScale())).append(" "); // coordIndex 3
            pointsBuilder.append(formatPrecision.format((+offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" ");
        }
        else if (dimensionality == SHAPE.ARRAY_3D_XZ_PLANE)
        {
            pointsBuilder.append(formatPrecision.format((+offsetWidth)  * getScale())).append(" "); // coordIndex 0
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((+offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((+offsetWidth)  * getScale())).append(" "); // coordIndex 1
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetWidth)  * getScale())).append(" "); // coordIndex 2
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetWidth)  * getScale())).append(" "); // coordIndex 3
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((+offsetHeight) * getScale())).append(" ");
        }
        else if (dimensionality == SHAPE.ARRAY_3D_YZ_PLANE)
        {
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" "); // coordIndex 0
            pointsBuilder.append(formatPrecision.format((+offsetWidth)  * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((+offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" "); // coordIndex 1
            pointsBuilder.append(formatPrecision.format((+offsetWidth)  * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" "); // coordIndex 2
            pointsBuilder.append(formatPrecision.format((-offsetWidth)  * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((-offsetHeight) * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format(  offsetDepth   * getScale())).append(" "); // coordIndex 3
            pointsBuilder.append(formatPrecision.format((-offsetWidth)  * getScale())).append(" ");
            pointsBuilder.append(formatPrecision.format((+offsetHeight) * getScale())).append(" ");
        }
        else
        {
            System.out.println("[RoundedRectangle.roundedRectanglePointsArray] illegal dimensionality enumeration, computation ignored");
            return "";
        }

        if (level > 0) pointsBuilder.append(" ");

        for (int side = 0; side < 4; side++)
        {
            switch (side)
            {
                case 0: // top side, upper-right corner
                    offsetWidth  =   ( width / 2.0f) * (1.0f - cornerPercentage);
                    offsetHeight =   (height / 2.0f) * (1.0f - cornerPercentage);
                    break;
                case 1: // right side, bottom-right corner
                    offsetWidth  =   ( width / 2.0f) * (1.0f - cornerPercentage);
                    offsetHeight = - (height / 2.0f) * (1.0f - cornerPercentage);
                    break;
                case 2: // bottom side, bottom-left corner
                    offsetWidth  = - ( width / 2.0f) * (1.0f - cornerPercentage);
                    offsetHeight = - (height / 2.0f) * (1.0f - cornerPercentage);
                    break;
                case 3: // left side, upper-left corner
                    offsetWidth  = - ( width / 2.0f) * (1.0f - cornerPercentage);
                    offsetHeight =   (height / 2.0f) * (1.0f - cornerPercentage);
                    break;
            }
            for ( int index = 0; index <= (sectors + 1); index++) // calculate current quadrant
            {
                double angle = ((float)(side * (sectors + 1) + index) / (float)circlePoints) * (2.0 * Math.PI);
                
                if ((dimensionality == SHAPE.ARRAY_2D) || (depth == 0.0f))
                {
                    pointsBuilder.append(formatPrecision.format(((Math.sin(angle) * radius) + offsetWidth)  * getScale())).append(" ");
                    pointsBuilder.append(formatPrecision.format(((Math.cos(angle) * radius) + offsetHeight) * getScale())).append(" ");
                    pointsBuilder.append(formatPrecision.format(0.0f));
                }
                else if (dimensionality == SHAPE.ARRAY_3D_XY_PLANE)
                {
                    pointsBuilder.append(formatPrecision.format(((Math.sin(angle) * radius) + offsetWidth)  * getScale())).append(" ");
                    pointsBuilder.append(formatPrecision.format(((Math.cos(angle) * radius) + offsetHeight) * getScale())).append(" ");
                    pointsBuilder.append(formatPrecision.format(                              offsetDepth   * getScale()));
                }
                else if (dimensionality == SHAPE.ARRAY_3D_XZ_PLANE)
                {
                    pointsBuilder.append(formatPrecision.format(((Math.sin(angle) * radius) + offsetWidth)  * getScale())).append(" ");
                    pointsBuilder.append(formatPrecision.format(                              offsetDepth   * getScale())).append(" ");
                    pointsBuilder.append(formatPrecision.format(((Math.cos(angle) * radius) + offsetHeight) * getScale()));
                }
                else if (dimensionality == SHAPE.ARRAY_3D_YZ_PLANE)
                {
                    pointsBuilder.append(formatPrecision.format(                              offsetDepth   * getScale())).append(" ");
                    pointsBuilder.append(formatPrecision.format(((Math.sin(angle) * radius) + offsetWidth)  * getScale())).append(" ");
                    pointsBuilder.append(formatPrecision.format(((Math.cos(angle) * radius) + offsetHeight) * getScale()));
                }
                numberOfPoints++;
                
                if (appendCommas)
                     pointsBuilder.append(",\t");
                else if ((side != 3) || (index != sectors + 1) || ((numberOfLlevels == 2) && (level == 0)))
                     pointsBuilder.append(" ");
                // five pairs of coordinate triples per printed line, for readability:
                if ((lineBreakInterval > 0) && ((index + 1) % lineBreakInterval) == 0) 
                    pointsBuilder.append ("\n"); 
            }
            // no need to add point at end of each side segment, rounded corners are contiguous
        }
    }
    if (numberOfLlevels == 2)
    {
        numberOfPoints = numberOfPoints / 2; // only count points on one level
    }
    return pointsBuilder.toString();
}
public String roundedRectangleIndexArray2D  (float width, float height)
{
    return roundedRectangleIndexArray (width, height, 0.0f, SHAPE.ARRAY_2D);
}
public String roundedRectangleIndexArray  (float width, float height, float depth, int dimensionality)
{
    StringBuilder indexBuilder = new StringBuilder();
    
   // upper level
    // inner box
    indexBuilder.append("0 1 2 3 0 -1 ");
    // right box
    indexBuilder.append("0 ").append(4 +    (sectors+1)     ).append(" ").append(4 +    (sectors+1)  + 1).append(" 1 0 -1 ");
    // lower box
    indexBuilder.append("1 ").append(4 + 2*((sectors+1)) + 1).append(" ").append(4 + 2*((sectors+1)) + 2).append(" 2 1 -1 ");
    // left box
    indexBuilder.append("2 ").append(4 + 3*((sectors+1)) + 2).append(" ").append(4 + 3*((sectors+1)) + 3).append(" 3 2 -1 ");
    // upper box
    indexBuilder.append("3 ").append(4 + 4*((sectors+1)) + 3).append(" ").append(4                      ).append(" 0 3 -1");
    // rounded corners
    for (int side = 0; side < 4; side++)
    {
        indexBuilder.append(" ").append(side).append(" ");
        for (int i=0; i <= sectors + 1; i++)
        {
            indexBuilder.append(4 + side * (sectors + 2) + i).append(" ");
            if (i == sectors + 1)
                indexBuilder.append(side).append(" -1"); // close loop
        }
    }
    // lower level
    if ((dimensionality != SHAPE.ARRAY_2D) && (depth != 0.0f))
    {
        int indexOffset = numberOfPoints + 4; // points per side + initial inner rectangle, points at second inner rectangle
        // bottom side
        indexBuilder.append(" ");
        // inner box
        indexBuilder.append(indexOffset+0).append(" ")
                    .append(indexOffset+1).append(" ")
                    .append(indexOffset+2).append(" ")
                    .append(indexOffset+3).append(" ")
                    .append(indexOffset+0).append(" -1 ");
        // right box
        indexBuilder.append(indexOffset+0).append(" ").append(indexOffset+4 +    (sectors+1)     ).append(" ").append(indexOffset+4 +    (sectors+1)  + 1).append(" ")
                    .append(indexOffset+1).append(" ").append(indexOffset+0).append(" -1 ");
        // lower box
        indexBuilder.append(indexOffset+1).append(" ").append(indexOffset+4 + 2*((sectors+1)) + 1).append(" ").append(indexOffset+4 + 2*((sectors+1)) + 2).append(" ")
                    .append(indexOffset+2).append(" ").append(indexOffset+1).append(" -1 ");
        // left box
        indexBuilder.append(indexOffset+2).append(" ").append(indexOffset+4 + 3*((sectors+1)) + 2).append(" ").append(indexOffset+4 + 3*((sectors+1)) + 3).append(" ")
                    .append(indexOffset+3).append(" ").append(indexOffset+2).append(" -1 ");
        // upper box
        indexBuilder.append(indexOffset+3).append(" ").append(indexOffset+4 + 4*((sectors+1)) + 3).append(" ").append(indexOffset+4                      ).append(" ")
                    .append(indexOffset+0).append(" ").append(indexOffset+3).append(" -1 ");
        // rounded corners
        for (int side = 0; side < 4; side++)
        {
            indexBuilder.append(" ").append(indexOffset + side).append(" ");
            for (int i=0; i <= sectors + 1; i++)
            {
                indexBuilder.append(indexOffset + 4 + side * (sectors + 2) + i).append(" ");
                if (i == sectors + 1)
                    indexBuilder.append(indexOffset + side).append(" -1"); // close loop
            }
        }
        // sides
        indexBuilder.append(" ");
        for (int i=0; i < (numberOfPoints - 1); i++)
        {
            // define outward-facing quadrilateral
            indexBuilder.append(4 + i + 1).append(" ").append(4 + i).append(" ").append(indexOffset + 4 + i).append(" ").append(indexOffset + 4 + i + 1); 
            indexBuilder.append(" -1 "); // done with this quad
        }
        // final quad to stitch side seam
        //    top [4                  .. 4 +   numberOfPoints - 1]
        // bottom [4 + numberOfPoints .. 4 + 2*numberOfPoints - 1]
        indexBuilder.append(              4 + numberOfPoints - 1).append(" ") //    top last
                    .append(indexOffset + 4 + numberOfPoints - 1).append(" ") // bottom last
                    .append(indexOffset + 4                     ).append(" ") // bottom first
                    .append(              4 +    0);                              //    top first
        indexBuilder.append(" -1"); // done
    }
    return indexBuilder.toString();
}

    /**
     * @return the numberOfPoints computed, not counting inner rectangle
     */
    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    /**
     * @return the multiplication-factor scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * @param scale the multiplication-factor scale to set
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * @return the number of sectors subdividing each quadrant
     */
    public int getSectors() {
        return sectors;
    }

    /**
     * @param sectors the number of sectors subdividing each quadrant to set
     */
    public void setSectors(int sectors) {
        this.sectors = sectors;
    }

    /**
     * @return the cornerPercentage
     */
    public float getCornerPercentage() {
        return cornerPercentage;
    }

    /**
     * @param cornerPercentage the cornerPercentage to set
     */
    public void setCornerPercentage(float cornerPercentage) {
        this.cornerPercentage = cornerPercentage;
        this.radius           = cornerPercentage;
    }
}
