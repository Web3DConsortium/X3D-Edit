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
 * Description: Create points along circumference of unit circle
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
 * Create a string array of points that draws a 2D circle outline 
 * @author brutzman
 */
public class NsidedPolygon {

    final int defaultSegmentCount = 24;
    DecimalFormat   formatPrecision3 = new DecimalFormat ("#0.000");
    DecimalFormat   formatPrecision4 = new DecimalFormat ("#0.0000");
    DecimalFormat   formatPrecision  = formatPrecision3;
    private boolean closed = true;
    
    private float height = 0.0f;
    
    private float  scale = 1.0f;

public String circlePointsArray2D  ()
{
    return circlePointsArray (defaultSegmentCount, SHAPE.ARRAY_2D, true, false, -1);
}

public String circlePointsArray2D  (int numberOfPoints, boolean closed)
{
    return circlePointsArray (numberOfPoints, SHAPE.ARRAY_2D, closed, false, -1);
}

public String circlePointsArrayXY  (int numberOfPoints, boolean closed)
{
    return circlePointsArray (numberOfPoints, SHAPE.ARRAY_3D_XY_PLANE, closed, false, -1);
}

public String circlePointsArrayXZ  (int numberOfPoints, boolean closed)
{
    return circlePointsArray (numberOfPoints, SHAPE.ARRAY_3D_XZ_PLANE, closed, false, -1);
}

public String circlePointsArrayXZ  (int numberOfPoints, float radius, boolean closed)
{
    return circlePointsArray (numberOfPoints, radius, SHAPE.ARRAY_3D_XZ_PLANE, closed, false, -1);
}

public String circlePointsArrayYZ  (int numberOfPoints, boolean closed)
{
    return circlePointsArray (numberOfPoints, SHAPE.ARRAY_3D_YZ_PLANE, closed, false, -1);
}

public String circlePointsArray  (int numberOfPoints, int dimensionality, boolean closed)
{
    return circlePointsArray (numberOfPoints, dimensionality, closed, false, -1);
}

public String circlePointsArray  (int numberOfPoints, int dimensionality, boolean closed, boolean appendCommas, int lineBreakInterval)
{
    // default radius
    return circlePointsArray  (numberOfPoints, 1.0f, dimensionality, closed, appendCommas, lineBreakInterval);
}

public String circlePointsArray  (int numberOfPoints, float radius, int dimensionality, boolean closed, boolean appendCommas, int lineBreakInterval)
{
    int index;
    this.closed = closed;
    StringBuilder pointsBuilder = new StringBuilder();
    if (numberOfPoints <=120)
         formatPrecision  = formatPrecision3;
    else formatPrecision  = formatPrecision4;
    
    if      (numberOfPoints < 0)
         numberOfPoints = defaultSegmentCount;
    else if (numberOfPoints == 0)
         return "";
    
    int totalIterations = numberOfPoints; // do not override passed parameter
    
    for (index = 0; index < totalIterations; index++)
    {
        double angle = 2.0 * Math.PI * index / totalIterations;
        if (dimensionality == SHAPE.ARRAY_2D)
        {
            pointsBuilder.append(formatPrecision.format(Math.sin(angle) * radius * scale));
            pointsBuilder.append(" ");
            pointsBuilder.append(formatPrecision.format(Math.cos(angle) * radius * scale));
        }
        else if (dimensionality == SHAPE.ARRAY_3D_XY_PLANE)
        {
            pointsBuilder.append(formatPrecision.format(Math.sin(angle) * radius * scale));
            pointsBuilder.append(" ");
            pointsBuilder.append(formatPrecision.format(Math.cos(angle) * radius * scale));
            pointsBuilder.append(" 0.0");
        }
        else if (dimensionality == SHAPE.ARRAY_3D_XZ_PLANE)
        {
            pointsBuilder.append(formatPrecision.format(Math.sin(angle) * radius * scale));
            pointsBuilder.append(" ").append(height).append(" ");
            pointsBuilder.append(formatPrecision.format(Math.cos(angle) * radius * scale));
        }
        else if (dimensionality == SHAPE.ARRAY_3D_YZ_PLANE)
        {
            pointsBuilder.append("0.0 ");
            pointsBuilder.append(formatPrecision.format(Math.sin(angle) * radius * scale));
            pointsBuilder.append(" ");
            pointsBuilder.append(formatPrecision.format(Math.cos(angle) * radius * scale));
        }
        if (appendCommas)
             pointsBuilder.append(",\t");
        else pointsBuilder.append(" ");
        // five pairs of coordinate triples per printed line, for readability:
        if ((lineBreakInterval > 0) && ((index + 1) % lineBreakInterval) == 0) 
            pointsBuilder.append ("\n"); 
    }
    if (closed) // add initial point at end
    {
        if (dimensionality == SHAPE.ARRAY_2D)
        {
            pointsBuilder.append(formatPrecision.format(Math.sin(0.0) * radius * scale));
            pointsBuilder.append(" ");
            pointsBuilder.append(formatPrecision.format(Math.cos(0.0) * radius * scale));
        }
        else if (dimensionality == SHAPE.ARRAY_3D_XY_PLANE)
        {
            pointsBuilder.append(formatPrecision.format(Math.sin(0.0) * radius * scale));
            pointsBuilder.append(" ");
            pointsBuilder.append(formatPrecision.format(Math.cos(0.0) * radius * scale));
            pointsBuilder.append(" 0.0");
        }
        else if (dimensionality == SHAPE.ARRAY_3D_XZ_PLANE)
        {
            pointsBuilder.append(formatPrecision.format(Math.sin(0.0) * radius * scale));
            pointsBuilder.append(" ").append(height).append(" ");
            pointsBuilder.append(formatPrecision.format(Math.cos(0.0) * radius * scale));
        }
        else if (dimensionality == SHAPE.ARRAY_3D_YZ_PLANE)
        {
            pointsBuilder.append("0.0 ");
            pointsBuilder.append(formatPrecision.format(Math.sin(0.0) * radius * scale));
            pointsBuilder.append(" ");
            pointsBuilder.append(formatPrecision.format(Math.cos(0.0) * radius * scale));
        }
    }    
    return pointsBuilder.toString();
}

    /**
     * @return whether closed
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * @param closed the closed value to set
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /**
     * @param value the height to set
     */
    public void setHeight(float value) {
        this.height = value;
    }

    /**
     * @return the scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(float scale) {
        this.scale = scale;
    }
}
