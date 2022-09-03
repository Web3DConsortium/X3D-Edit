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
package org.web3d.x3d.hanim.bvh;

import java.util.ArrayList;
import java.util.Iterator;
import javax.vecmath.AxisAngle4d;

/**
 *
 * @author Don Brutzman
 */

public class Motion extends BvhSkeletonParameters {
    
    /**
     * BVH file frameTime equals duration interval between each frame, units are typically in seconds
     */
    private double              frameDuration = 0.0;
    /**
     * total duration for all frames, units are typically in seconds
     */
    private double              animationTotalDuration = 0.0;
    /**
     * number of expected frames with each containing an array of values, note that actual contained frame count may be higher.
     */
    private int                expectedFrameCount = 0;
	
    /**
     * ancestor Hierarchy node containing BVH tree with this node
     */
	private Hierarchy ancestorHierarchy;
	
    /**
     * contained frames, each a double-precision array of pose values
     */
    private ArrayList<double[]> frames    = new ArrayList<>();
	
	private StringBuilder keyArray = new StringBuilder();
    
    /**
     * Constructor creates new Motion object
     */
    public Motion() 
    {
        // TODO initialization code goes here
    }

    /**
     * Produce BVH content in plain-text BVH format
     * @return the serialized object in plain-text BVH format
     */
    public String getHierarchyOutputBVH() {
        return getHierarchyOutputBVH(0);
    }

    /**
     * Produce BVH content in plain-text BVH format
     * @param indentLevel tree level of this node, used to support indent spacing
     * @return the serialized object
     */
    public String getHierarchyOutputBVH(int indentLevel) {
        StringBuilder outputBVH = new StringBuilder();
        
        outputBVH.append("MOTION").append("\n");
        outputBVH.append("Frames: ").append(getFrameCount()).append("\n");
        outputBVH.append("Frame Time: ").append(getFrameDuration()).append("\n");
        
		// output full array of BVH MOTION values
        Iterator<double[]> framesIterator = frames.iterator();
        double[] values;
        while (framesIterator.hasNext())
        {
            values = framesIterator.next();
            for (int k = 0; k < values.length; k++)
            {
//                // uniform column spacing
//                if (values[k] >= 0.0)
//                {
//                    if      (values[k] <  10.0)
//                        outputBVH.append("   ");
//                    else if (values[k] < 100.0)
//                        outputBVH.append("  ");
//                }
//                else // negative
//                {
//                    if      (values[k] >  -10.0)
//                        outputBVH.append("  ");
//                    else if (values[k] > -100.0)
//                        outputBVH.append(" ");
//                }
                outputBVH.append(sixDigitFormat.format(values[k]));
                
                if (k == values.length - 1)   // last channel column in frame
                     outputBVH.append("\n");
                else if (((k + 1) % 3) == 0)  // slight whitespace separation between 3-tuples
                     outputBVH.append("\t");  // tab
                else outputBVH.append(" ");   // space
            }
        }
        return outputBVH.toString();
    }

    /**
     * Produce BVH content in plain-text BVH format
     * @return the serialized object in plain-text BVH format
     */
    public String getHierarchyOutputX3D() {
        return getHierarchyOutputX3D(0);
    }

    /**
     * Produce BVH content in plain-text BVH format
     * @param indentLevel tree level of this node, used to support indent spacing
     * @return the serialized object
     */
    public String getHierarchyOutputX3D(int indentLevel)
	{
        StringBuilder outputX3D = new StringBuilder();
        Iterator<double[]> framesIterator;
        double[] values;
        
		outputX3D.append(indentSpacing(indentLevel)).append("<Group DEF='").append(ancestorHierarchy.getModelName()).append("_MotionGroup'>\n");
        outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- BVH MOTION").append(" -->\n");
        outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- BVH Frames: ").append(getFrameCount()).append(" -->\n");
        outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- BVH Frame Time: ").append(getFrameDuration()).append(" seconds (")
				 .append(twoDigitFormat.format(getFrameRate())).append(" frames per second)").append(" -->\n");
		
		animationTotalDuration = getFrameCount() * getFrameDuration();
        outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Expected frame count: ").append(getExpectedFrameCount());
		outputX3D.append(                                        ", actual frame count: ").append(getFrameCount()        );
		outputX3D.append(                                        ", animation total duration: ").append(threeDigitFormat.format(getTotalDuration())).append(" seconds -->\n");
        outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Frame width: ").append(getFrameWidth()/3).append(" triplet values").append(" -->\n");
		outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Total count: ").append(getFrameWidth()).append(" * ").append(getFrameCount()).append(" = ").append(getFrameWidth()*getFrameCount()).append(" recorded motion values").append(" -->\n");
		
		// error checks
		if (getExpectedFrameCount() != getFrameCount()) // match check
		{
			outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Error: Expected frame count != actual frame count").append(" -->\n");
		}
		if (3*(ancestorHierarchy.getHierarchyJointList().size() + 2) != getFrameWidth()) // match check
		{
			outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Error: 3 * (size of joints list + HIERARCHY ROOT positions/rotations) != actual frame width").append(" -->\n");
		}
		if ((getFrameWidth() % 3) != 0) // modulus check
		{
			outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Error: frame width = ").append(getFrameWidth()).append(" is not a multiple of 3").append(" -->\n");
		}
		
		computeKeyArray();
		
		outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Animation playback: enable RealTimer for continuous motion at ")
				 .append(threeDigitFormat.format(getFrameRate())).append(" frames/second (fps) -->\n");
		outputX3D.append(indentSpacing(indentLevel+1)).append("<TimeSensor DEF='RealTimer' cycleInterval='").append(threeDigitFormat.format(animationTotalDuration)).append("' enabled='true' loop='true'/>\n");
			
		outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Alternative replay: enable StepTimer for discrete time-step motion at 1 fps -->\n");
		outputX3D.append(indentSpacing(indentLevel+1)).append("<TimeSensor DEF='StepTimer' cycleInterval='").append(getFrameCount()).append("' enabled='false' loop='true'/>\n");
		outputX3D.append(indentSpacing(indentLevel+1)).append("<ScalarInterpolator DEF='FrameStepper'\n");
		String      computeKeyArrayDoubled = computeKeyArrayDoubled();
		String shiftedKeyArrayDoubled      = computeKeyArrayDoubled.substring(computeKeyArrayDoubled.indexOf(" ") + 1);
		String shiftedKeyValueArrayDoubled = computeKeyArrayDoubled.substring(0,computeKeyArrayDoubled.lastIndexOf(" "));
		outputX3D.append(indentSpacing(indentLevel+2)).append("      key='").append(shiftedKeyArrayDoubled     ).append("'\n");   // [0..1] stepwise
		outputX3D.append(indentSpacing(indentLevel+2)).append(" keyValue='").append(shiftedKeyValueArrayDoubled).append("'/>\n"); // [0..1] stepwise
		outputX3D.append(indentSpacing(indentLevel+1)).append("<ROUTE fromNode='StepTimer' fromField='fraction_changed' toNode='FrameStepper' toField='set_fraction'/>\n");

		// output interpolators, note that initial PositionInterpolator and OrientationInterpolator both go to root Joint
		double phi, theta, psi;
		double  minAngle = Double.MAX_VALUE;
		double  maxAngle = Double.MIN_VALUE;
		for (int channelIndex = 0; channelIndex < (getFrameWidth() / 3); channelIndex++)
		{
			// TODO handle scale channel if present
			
			String jointName, jointDEF, jointHAnimName, channelNames;
			if  (channelIndex < 2) // HIERARCHY ROOT
			{
				  jointName = getAncestorHierarchy().getBvhName();
				  jointDEF  = getAncestorHierarchy().getDEF();
		     jointHAnimName = getAncestorHierarchy().getSkeletonJointName();
			   channelNames = getAncestorHierarchy().getChannelNamesString();
			}
			else 
			{
				  jointName = getAncestorHierarchy().getHierarchyJointList().get(channelIndex - 2).getBvhName();
				  jointDEF  = getAncestorHierarchy().getHierarchyJointList().get(channelIndex - 2).getJointDEF();
		     jointHAnimName = getAncestorHierarchy().getHierarchyJointList().get(channelIndex - 2).getHAnimJointName();
			   channelNames = getAncestorHierarchy().getHierarchyJointList().get(channelIndex - 2).getChannelNamesString();
			}
			String interpolatorDEF = "Interpolator" + channelIndex;
			if (!jointHAnimName.isEmpty())
				 interpolatorDEF += "_" + jointHAnimName;
			outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- ").append(interpolatorDEF)
					 .append(" channels [").append(3*channelIndex).append("..").append(3*channelIndex+2)
					 .append("] sends animation values to BVH JOINT ").append(jointName).append(", DEF ").append(jointDEF)
					 .append(", <HAnimJoint name=").append(jointHAnimName).append("/> -->\n");
			outputX3D.append(indentSpacing(indentLevel+1)).append("<");
			if  (channelIndex == 0)
				 outputX3D.append("PositionInterpolator");
			else outputX3D.append("OrientationInterpolator");
			outputX3D.append(" DEF='").append(interpolatorDEF).append("'\n");
			outputX3D.append(indentSpacing(indentLevel+2)).append("     key='").append(getKeyArray()).append("'\n");
			outputX3D.append(indentSpacing(indentLevel+2)).append("keyValue='");
			
			boolean angleValuesUnchanged   = true;  // initialize
			boolean angleValuesJittery     = true;  // initialize
			Double  angleJitterThreshold   = 0.000005;
			boolean priorValuesInitialized = false; // initialize
			double  prior0 = 0.0;
			double  prior1 = 0.0;
			double  prior2 = 0.0;
			double  minAngle0 = Double.MAX_VALUE;
			double  minAngle1 = Double.MAX_VALUE;
			double  minAngle2 = Double.MAX_VALUE;
			double  maxAngle0 = Double.MIN_VALUE;
			double  maxAngle1 = Double.MIN_VALUE;
			double  maxAngle2 = Double.MIN_VALUE;
	
			framesIterator = frames.iterator(); // restart at beginning
			while (framesIterator.hasNext())
			{
				values = framesIterator.next();
				if (channelIndex == 0) // Position value is at ROOT Joint
				{
					// position values are scaled to match previous scaling
					outputX3D.append(fourDigitFormat.format(values[3*channelIndex  ] * getAncestorHierarchy().getScaleFactor())).append(" ")
							 .append(fourDigitFormat.format(values[3*channelIndex+1] * getAncestorHierarchy().getScaleFactor())).append(" ")
							 .append(fourDigitFormat.format(values[3*channelIndex+2] * getAncestorHierarchy().getScaleFactor()));
				}
				else // Orientation value, now convert from Euler-angles triplet to SFRotation 4-tuple
				{
					String channelNamesString;
					if (channelIndex == 1) // First rotation value (second channel, index == 1) is also at ROOT Joint
					{
						channelNamesString = ancestorHierarchy.getChannelNamesString();
					}
					else
					{
						Joint thisJoint = ancestorHierarchy.getHierarchyJointList().get(channelIndex - 2); // skip first two channels
						channelNamesString = thisJoint.getChannelNamesString();
					}
					if (channelNamesString.equalsIgnoreCase("Zrotation Xrotation Yrotation") || // JOINT
						channelNamesString.endsWith        ("Zrotation Xrotation Yrotation"))   // ROOT channels 0, 1
//					           Xposition Yposition Zposition Zrotation Xrotation Yrotation      // ROOT channels 0, 1
					{
						phi = values[3*channelIndex+1]; // Euler angle about X axis,  second channel 
					  theta = values[3*channelIndex+2]; // Euler angle about Y axis,   third channel
						psi = values[3*channelIndex+0]; // Euler angle about Z axis, initial channel
					}
					else  // TODO: different order for theta, psi if different CHANNEL order found
					{
						phi = values[3*channelIndex+0]; // Euler angle about X axis
					  theta = values[3*channelIndex+2]; // Euler angle about Y axis
						psi = values[3*channelIndex+1]; // Euler angle about Z axis
					}
					AxisAngle4d axisAngle = getAxisAngleRotation( // computation is also order dependent
							  phi * Math.PI / 180.0, // degrees to radians
							theta * Math.PI / 180.0, 
							  psi * Math.PI / 180.0);
					outputX3D.append(fourDigitFormat.format(axisAngle.x)).append(" ")
							 .append(fourDigitFormat.format(axisAngle.y)).append(" ")
							 .append(fourDigitFormat.format(axisAngle.z)).append(" ")
							 .append(fourDigitFormat.format(axisAngle.angle));
				}
				Double value0 = values[3*channelIndex  ];
				Double value1 = values[3*channelIndex+1];
				Double value2 = values[3*channelIndex+2];
				if (priorValuesInitialized)
				{
					if (angleValuesUnchanged &&
						 ((prior0 != value0) ||
					      (prior1 != value1) ||
						  (prior2 != value2)))
						angleValuesUnchanged = false;
					if (angleValuesJittery &&
						 ((Math.abs(prior0 - value0) > angleJitterThreshold) ||
					      (Math.abs(prior1 - value1) > angleJitterThreshold) ||
						  (Math.abs(prior2 - value2) > angleJitterThreshold)))
						angleValuesJittery = false;
				}
				else
				{
					priorValuesInitialized = true; // by follow-on statements
				}
				prior0 = values[3*channelIndex  ];
				prior1 = values[3*channelIndex+1];
				prior2 = values[3*channelIndex+2];
				if (channelIndex > 0)
				{
					if (minAngle0 > value0) minAngle0 = value0;
					if (minAngle1 > value1) minAngle1 = value1;
					if (minAngle2 > value2) minAngle2 = value2;
					if (maxAngle0 < value0) maxAngle0 = value0;
					if (maxAngle1 < value1) maxAngle1 = value1;
					if (maxAngle2 < value2) maxAngle2 = value2;
				}
				
				if (framesIterator.hasNext())
					outputX3D.append(", ");
			}
			outputX3D.append("'/>\n");
			
			/* Diagnosis support: document these single-channel triplet values within an X3D comment, 
			   sampling vertically down the corresponding channel columns in the motions array */
			outputX3D.append(indentSpacing(indentLevel+2)).append("<!-- ");
			if      (angleValuesUnchanged)
				     outputX3D.append("Unchanging ");
			else if (angleValuesJittery)
				     outputX3D.append("Jittery ");
			if  (channelIndex == 0) // Position value is at ROOT Joint
				 outputX3D.append("Position");
			else outputX3D.append("Euler angle");
			outputX3D.append(" triplet values, CHANNELS ").append(channelNames).append(", with min/max ranges [")
					 .append(minAngle0).append(",").append(maxAngle0).append("], [")
					 .append(minAngle1).append(",").append(maxAngle1).append("], [")
					 .append(minAngle2).append(",").append(maxAngle2).append("] degrees");
			outputX3D.append(" -->\n");
			outputX3D.append(indentSpacing(indentLevel+2)).append("<!-- ");
			framesIterator = frames.iterator(); // restart at beginning
			while (framesIterator.hasNext())
			{
				values = framesIterator.next();
				outputX3D.append(sixDigitFormat.format(values[3*channelIndex+0])).append(" ")
						 .append(sixDigitFormat.format(values[3*channelIndex+1])).append(" ")
						 .append(sixDigitFormat.format(values[3*channelIndex+2]));
				if (framesIterator.hasNext())
					outputX3D.append(", ");
			}
			outputX3D.append(" -->\n");
			if (minAngle > minAngle0) minAngle = minAngle0;
			if (minAngle > minAngle1) minAngle = minAngle1;
			if (minAngle > minAngle2) minAngle = minAngle2;
			if (maxAngle < maxAngle0) maxAngle = maxAngle0;
			if (maxAngle < maxAngle1) maxAngle = maxAngle1;
			if (maxAngle < maxAngle2) maxAngle = maxAngle2;
		}
		outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Overall angle min/max range [")
				 .append(minAngle).append(",").append(maxAngle).append("] degrees -->\n");
		if ((Math.abs(minAngle) <= 2.0 * Math.PI) &&
			(Math.abs(maxAngle) <= 2.0 * Math.PI))
		{
			outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Warning: angles are likely radian values, not degrees -->\n");
		}
		if (Math.abs(minAngle) > 360.0)
		{
			outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Warning: some angle values are greater than 360 degrees -->\n");
		}
		// now output ROUTE statements, note that initial PositionInterpolator and OrientationInterpolator both go to root Joint
        outputX3D.append(indentSpacing(indentLevel+1)).append("<!-- Corresponding ROUTE statements to send animation values -->\n");
		for (int index = 0; index < (getFrameWidth() / 3); index++)
		{
			Joint currentJoint;
			String sourceDEF = "Interpolator" + index;
			String targetDEF, targetField;
			if  ((index == 0) || (index == 1)) // target the same initial root Joint for both first and second interpolators 
			{
				currentJoint = getAncestorHierarchy().getHierarchyJointList().get(0);
				sourceDEF   += "_" + getAncestorHierarchy().getSkeletonJointName();
				targetDEF = ancestorHierarchy.getJointDEF();
			}
			else 
			{
				// skip first 2 index values; pseudo hierarchy ROOT Joint is not on list:
				currentJoint = getAncestorHierarchy().getHierarchyJointList().get(index - 2); 
				sourceDEF   += "_" + currentJoint.getHAnimJointName();
				targetDEF    = currentJoint.getJointDEF(); // loop to match ancestorHierarchy
			}
			if  (index == 0)
				 targetField = "set_translation";
			else targetField = "set_rotation";
			outputX3D.append(indentSpacing(indentLevel+1)).append("<ROUTE fromField='fraction_changed' fromNode='RealTimer' toField='set_fraction' toNode='").append(sourceDEF).append("'/>\n");
			outputX3D.append(indentSpacing(indentLevel+1)).append("<ROUTE fromField='value_changed' fromNode='FrameStepper' toField='set_fraction' toNode='").append(sourceDEF).append("'/>\n");
			outputX3D.append(indentSpacing(indentLevel+1)).append("<ROUTE fromField='value_changed' fromNode='").append(sourceDEF).append("' toField='").append(targetField).append("' toNode='").append(targetDEF).append("'/>\n");
		}
		outputX3D.append(indentSpacing(indentLevel)).append("</Group>\n");
		
		/* debug: output full array of original BVH MOTION values */
        outputX3D.append("<!-- All frame data:\n");
		framesIterator = frames.iterator(); // restart at beginning
        while (framesIterator.hasNext())
        {
            values = framesIterator.next();
            for (int k = 0; k < values.length; k++)
            {
                outputX3D.append(threeDigitFormat.format(values[k]));
                
                if (k == values.length - 1)   // last channel column in frame
                     outputX3D.append("\n");
                else if (((k + 1) % 3) == 0)  // slight whitespace separation between 3-tuples
                     outputX3D.append("\t");  // tab
                else outputX3D.append(" ");
            }
        }
        outputX3D.append("-->\n");

        return outputX3D.toString(); // all Motion output complete
    }

    /**
     * @return the total duration, units are typically in seconds
     */
    public double getFrameRate() {
		double frameRate = (1.0 / frameDuration);
		frameRate = (Math.round((float)(100.0 * frameRate + 0.5)) / 100.0); // round to nearest hundredth
        return frameRate;
    }

    /**
     * @return the total duration for this animation, units are typically in seconds
     */
    public double getTotalDuration() {
        return animationTotalDuration;
    }

    /**
     * @return the actual frame count
     */
    public int getFrameCount() {
        return frames.size();
    }

    /**
     * @return the expectedFrameCount
     */
    public int getExpectedFrameCount() {
        return expectedFrameCount;
    }

    /**
     * @param expectedFrames the expectedFrameCount to set
     */
    public void setExpectedFrameCount(int expectedFrames) {
        this.expectedFrameCount = expectedFrames;
    }

    /**
     * @return the frame time from the BVH file, which equals time duration of each frame
     */
    public double getFrameDuration() {
        return frameDuration;
    }

    /**
     * @param newFrameDuration the frameTime to set
     */
    public void setFrameDuration(double newFrameDuration) {
        frameDuration = newFrameDuration;
    }

    /**
     * @return the frames
     */
    public ArrayList<double[]> getFrames() {
        return frames;
    }

    /**
     * @param frames the frames to set
     */
    public void setFrames(ArrayList<double[]> frames) {
        this.frames = frames;
    }

    /**
     * @param newFrame the new frame to append
     */
    public void addFrame(double[] newFrame) {
        frames.add(newFrame);
    }

    /**
     * @param newFrame the new frame to insert
     */
    public void removeFrame(int index) {
        frames.remove(index);
    }

    /**
     * reinitialize frames array
     */
    public void clearFrames() {
        frames = new ArrayList<>();
    }

    /**
     * @param newFrame the new frame to insert
     */
    public void insertFrame(int index, double[] newFrame) 
	{
        frames.add(index, newFrame);
    }

    /**
     * @return values for a single frame
     */
    public double[] getFrame(int index) {
        return frames.get(index);
    }

    /**
     * @return values for a single frame
     */
    public int getFrameWidth() {
		if  (frames.size() == 0)
			 return 0; // not set yet
		else return frames.get(0).length;
    }

    /**
     * set values for a single frame
     */
    public void setFrame(int index, double[] arrayValue) {
        frames.set(index, arrayValue);
    }

	/**
	 * compute the keyArray from actual frame count, ranging [0..1] with one key for each frame
	 * @see computeKeyArrayDoubled()
	 */
	public void computeKeyArray()
	{
		keyArray = new StringBuilder(); // clear prior array, if any
		for (int i = 0; i < getFrameCount() - 1; i++)
		{
			double fraction = (double) i / (getFrameCount() - 1);
			keyArray.append(fourDigitFormat.format(fraction));
			if (i < getFrameCount() - 2)
				keyArray.append(" ");
		}
		keyArray.append(" 1.0");
	}

	/**
	 * compute a modified keyArray from actual frame count, ranging [0..1] with TWO keys for each frame
	 * @see computeKeyArray()
	 * @return doubled key array for discrete steps
	 */
	public String computeKeyArrayDoubled()
	{
		StringBuilder doubleKeyArray = new StringBuilder(); // clear prior array, if any
		for (int i = 0; i < getFrameCount() - 1; i++)
		{
			double fraction = (double) i / (getFrameCount() - 1);
			doubleKeyArray.append(fourDigitFormat.format(fraction));
			doubleKeyArray.append(" ");
			doubleKeyArray.append(fourDigitFormat.format(fraction));
			if (i < getFrameCount() - 2)
				doubleKeyArray.append(" ");
		}
		doubleKeyArray.append(" 1.0 1.0");
		return doubleKeyArray.toString();
	}

	/**
	 * @return the keyArray
	 */
	public String getKeyArray() {
		return keyArray.toString();
	}

	/**
	 * @return the ancestorHierarchy
	 */
	public Hierarchy getAncestorHierarchy() {
		return ancestorHierarchy;
	}

	/**
	 * @param newAncestorHierarchy the ancestorHierarchy to set
	 */
	public void setAncestorHierarchy(Hierarchy newAncestorHierarchy) {
		ancestorHierarchy = newAncestorHierarchy;
	}
	
}
