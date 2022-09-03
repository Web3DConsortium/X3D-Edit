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

import java.util.Iterator;
import javax.vecmath.Vector3d;

/**
 * Representation of BVH/HAnim Joint, Segment and Site triplet
 * @author Don Brutzman
 */

public class Joint extends BvhSkeletonParameters {
    /**
     * parent Hierarchy or Joint node
     */
    private BvhSkeletonParameters parent;
    /**
     * ancestor Hierarchy node containing BVH tree with this node
     */
    private Hierarchy ancestorHierarchy;
    /**
     * three angles in radians, order X-Y-Z
     */
    private double[] eulerRotations = {0.0, 0.0, 0.0};
    /**
     * whether or not this Joint contains an end-effector Site
     */
    private boolean siteIncluded = false;
    /**
     * X, Y, Z values
     */
    private Vector3d siteOffset = new Vector3d();
    
    /**
     * Constructor creates new Joint object
     */
    public Joint()
    {
        // initialization
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

        outputBVH.append(indentSpacing(indentLevel));
        outputBVH.append("JOINT ").append(getBvhName()).append("\n");
        outputBVH.append(indentSpacing(indentLevel));
        outputBVH.append("{");
        outputBVH.append("\n");
        outputBVH.append(indentSpacing(indentLevel+1));
        outputBVH.append("OFFSET ").append(getOffsetString()).append("\n");
        outputBVH.append(indentSpacing(indentLevel+1));
        outputBVH.append("CHANNELS ").append(getChannelSize()).append(" ").append(getChannelNamesString()).append("\n");
        
        if (siteIncluded)
        {
            outputBVH.append(indentSpacing(indentLevel+1));
            outputBVH.append("End Site").append("\n");
            outputBVH.append(indentSpacing(indentLevel+1));
            outputBVH.append("{").append("\n");
            outputBVH.append(indentSpacing(indentLevel+2));
            outputBVH.append("OFFSET ").append(getSiteOffsetString()).append("\n");
            outputBVH.append(indentSpacing(indentLevel+1));
            outputBVH.append("}").append("\n");
        }
        else // recursive exposure of each JOINT (containing OFFSET CHANNELS JOINT)
        {
            Iterator<Joint> jointsIterator = joints.iterator();
            while   (jointsIterator.hasNext())
            {
                Joint nextJoint = jointsIterator.next();
                outputBVH.append(nextJoint.getHierarchyOutputBVH(indentLevel + 1));
            }
        }
        outputBVH.append(indentSpacing(indentLevel));
        outputBVH.append("}");
//      outputBVH.append(" // ").append(getName()); // trace
        outputBVH.append("\n");
        return outputBVH.toString();
    }
//
//    /**
//     * Produce BVH content in plain-text BVH format
//     * @return the serialized object in plain-text BVH format
//     */
//    public String getHierarchyOutputX3D(String modelName, String parentJointName) {
//        return getHierarchyOutputX3D(0, modelName, parentJointName);
//    }

    /**
     * Produce X3D model in XML-based .x3d encoding
     * @param indentLevel tree level of this node, used to support indent spacing
     * @param modelName bvhName of this BVH model
     * @param parentJointName bvhName of parent Joint node
     * @return the serialized object
     */
    public String getHierarchyOutputX3D(int indentLevel, String modelName, String parentJointName) {
		return getHierarchyOutputX3D (indentLevel, modelName, parentJointName, "0 0 0"); // initialize priorOffset
    }

    /**
     * Produce X3D model in XML-based .x3d encoding
     * @param indentLevel tree level of this node, used to support indent spacing
     * @param modelName bvhName of this BVH model
     * @param parentJointName bvhName of parent Joint node
     * @param priorOffset bvhName of parent Joint node
     * @return the serialized object
     */
    public String getHierarchyOutputX3D(int indentLevel, String modelName, String parentJointName, String priorOffset) {
        StringBuilder outputX3D = new StringBuilder();
		setJointDEF(modelName + "_" + getHAnimJointName());
        setSegmentName(parentJointName + "_to_" + getHAnimJointName()); // ParentJointName1_to_ChildJointName2
		if (!getHAnimSegmentName().isEmpty())
			setSegmentName(getHAnimSegmentName());
		
        // ignore initial orientation from MOTIONS array, provide default pose

        // JOINT, OFFSET
        outputX3D.append(indentSpacing(indentLevel));
        outputX3D.append("<HAnimJoint DEF='").append(getJointDEF());
////		if (getParent() instanceof Hierarchy) // HAnimHumanoid
////			outputX3D.append("' containerField='skeleton");

        outputX3D.append("' center='").append(getCumulativeOffsetString()).append("' name='").append(getHAnimJointName()).append("'>\n");
//        outputX3D.append("<HAnimJoint DEF='hanim_sacroiliac' center='0.0 0.9149 0.0016' bvhName='sacroiliac'> ").append(getName()).append("\n");
//        outputX3D.append(indentSpacing(indentLevel));
//        outputX3D.append("{");
//      outputX3D.append(" // ").append(getName()); // trace
//        outputX3D.append("\n");
//        outputX3D.append(indentSpacing(indentLevel+1));
//        outputX3D.append("OFFSET ").append(getCumulativeOffsetString()).append("\n");

        // CHANNELS
		if (isBvhChannelX3dCommentInsertion())
		{
			outputX3D.append(indentSpacing(indentLevel+1));
			outputX3D.append("<!-- BVH JOINT ").append(getBvhName()).append(", OFFSET ").append(getOffsetString()).append(", CHANNELS ").append(getChannelSize()).append(" ").append(getChannelNamesString()).append(" -->\n");
		}
		setSegmentDEF(modelName + "_" + getSegmentName());
		getAncestorHierarchy().incrementHierarchySegmentList(getSegmentDEF()); // add to list
        outputX3D.append(indentSpacing(indentLevel+1));
        outputX3D.append("<HAnimSegment DEF='").append(getSegmentDEF()).append("' name='").append(getSegmentName()).append("'");
        outputX3D.append(">\n"); // finish open tag
        
		outputX3D.append(indentSpacing(indentLevel+2)).append("<!-- Transform to establish local-origin reference frame at center of current Joint -->\n");
        outputX3D.append(indentSpacing(indentLevel+2)).append("<Transform translation='").append(getCumulativeOffsetString()).append("'>\n");
        
		if (isSiteIncluded() || isX3dBallAndStickVisualizationIncluded())
        {
            // <HAnimSite DEF='hanim_l_knee_crease' bvhName='l_knee_crease' translation='0.0993 0.4881 -0.0309'/>
            
            // End Site, OFFSET
            setSiteName(getHAnimSegmentName() + "_tip");
			setSiteDEF(modelName + "_" + getSiteName());
			if (isSiteIncluded())
			{
				getAncestorHierarchy().incrementHierarchySiteList(getSiteDEF()); // add to list

				outputX3D.append(indentSpacing(indentLevel+3));
				outputX3D.append("<HAnimSite DEF='").append(getSiteDEF()).append("' name='").append(getSiteName())
						 .append("' translation='").append(getSiteOffsetString()).append("'");
				outputX3D.append(">\n");
				outputX3D.append(indentSpacing(indentLevel+4));
				outputX3D.append("<!-- BVH End Site OFFSET ").append(siteOffset).append(" -->\n");
				if (isX3dBallAndStickVisualizationIncluded()) // BVH Site visualization
				{
					outputX3D.append(indentSpacing(indentLevel+4));
					outputX3D.append("<TouchSensor description='HAnimSite ").append(getSiteName()).append("'/>\n");
					outputX3D.append(indentSpacing(indentLevel+4));
					outputX3D.append("<Shape USE='HAnimSiteShape'/>\n");
					outputX3D.append(indentSpacing(indentLevel+4)).append("<!-- HAnimSite visualization line for current OFFSET from grandparent <HAnimJoint name='").append(getHAnimJointName()).append("'/> to current <HAnimSite name='").append(getSiteName()).append("'/> -->\n"); // <HAnimJoint bvhName='TODO'/> 
					outputX3D.append(indentSpacing(indentLevel+4)).append("<Shape>\n");
					outputX3D.append(indentSpacing(indentLevel+5)).append("<LineSet vertexCount='2'>\n"); // containerField='geometry'
					outputX3D.append(indentSpacing(indentLevel+6)).append("<Coordinate point='").append(getReverseSiteOffsetString()).append(" 0 0 0'/>\n"); // TODO confirm both points
					outputX3D.append(indentSpacing(indentLevel+6)).append("<ColorRGBA USE='HAnimSiteLineColorRGBA'/>\n");
					outputX3D.append(indentSpacing(indentLevel+5)).append("</LineSet>\n");
					outputX3D.append(indentSpacing(indentLevel+4)).append("</Shape>\n");
				}
				outputX3D.append(indentSpacing(indentLevel+3));
				outputX3D.append("</HAnimSite>\n"); // finish closing tag HAnimSite
			}
			
			if (isX3dBallAndStickVisualizationIncluded()) // BVH Joint visualization
			{
				outputX3D.append(indentSpacing(indentLevel+3)).append("<!-- Visualization sphere for <HAnimJoint name='").append(getHAnimJointName()).append("'> is placed within <HAnimSegment name='").append(modelName).append("_").append(getSegmentName()).append("'> -->\n");
				outputX3D.append(indentSpacing(indentLevel+3)).append("<TouchSensor description='HAnimJoint ").append(getBvhName()).append(" ").append(getHAnimJointName()).append(", HAnimSegment ").append(getSegmentName()).append("'/>\n");
				outputX3D.append(indentSpacing(indentLevel+3)).append("<Shape USE='HAnimJointShape'/>\n");
				
				// loop to draw line geometry from current Joint to its child Joints, if any
				Iterator<Joint> jointsIterator = getJoints().iterator();
				while   (jointsIterator.hasNext())
				{
					Joint nextChildJoint = jointsIterator.next();
					outputX3D.append(indentSpacing(indentLevel+3)).append("<!-- HAnimSegment OFFSET visualization line from current <HAnimJoint name='").append(getHAnimJointName()).append("'/> to child <HAnimJoint name='").append(nextChildJoint.getHAnimJointName()).append("'/> -->\n");
					outputX3D.append(indentSpacing(indentLevel+3)).append("<Shape>\n");
					outputX3D.append(indentSpacing(indentLevel+4)).append("<LineSet vertexCount='2'>\n"); // containerField='geometry'
					outputX3D.append(indentSpacing(indentLevel+5)).append("<Coordinate point='0 0 0 ").append(nextChildJoint.getOffsetString()).append("'/>\n"); // TODO confirm both points
					outputX3D.append(indentSpacing(indentLevel+5)).append("<ColorRGBA USE='HAnimSegmentLineColorRGBA'/>\n");
					outputX3D.append(indentSpacing(indentLevel+4)).append("</LineSet>\n");
					outputX3D.append(indentSpacing(indentLevel+3)).append("</Shape>\n");
				}
			}
        }
        else // no siteIncluded, no visualization
        {
			// ensure contained geometry goes into correct place!
            outputX3D.append(indentSpacing(indentLevel+3)).append("<!-- insert Shape geometry here -->\n");
        }
		outputX3D.append(indentSpacing(indentLevel+2)).append("</Transform>\n");
		outputX3D.append(indentSpacing(indentLevel+1));
		outputX3D.append("</HAnimSegment>\n"); // close tag

        // recursive exposure of each child JOINT (containing OFFSET CHANNELS JOINT)
		Iterator<Joint> jointsIterator = joints.iterator();
		while   (jointsIterator.hasNext())
		{
			Joint nextJoint = jointsIterator.next();
			outputX3D.append(nextJoint.getHierarchyOutputX3D(indentLevel + 1, modelName, getHAnimJointName(), getCumulativeOffsetString()));
		}
		
        outputX3D.append(indentSpacing(indentLevel));
        outputX3D.append("</HAnimJoint>");
//      outputX3D.append(" // ").append(getHAnimJointName()); // trace
        outputX3D.append("\n");
        return outputX3D.toString();
    }
    
//    /** 
//     * Compute local AxisAngle4d SFRotation from Euler-angle rotations
//	 * @see BvhSkeletonParameters#getAxisAngleRotation(double,double,double)
//     */
//    public AxisAngle4d getAxisAngleRotation ()
//    {
//            // javax.vecmath conversions (boy is this a convoluted API or what!)
//            AxisAngle4d axisAngleX = new AxisAngle4d(1.0, 0.0, 0.0, getEulerRotationX());
//            AxisAngle4d axisAngleY = new AxisAngle4d(0.0, 1.0, 0.0, getEulerRotationY());
//            AxisAngle4d axisAngleZ = new AxisAngle4d(0.0, 0.0, 1.0, getEulerRotationZ());
//            
//            AxisAngle4d axisAngleIdentity  = new AxisAngle4d(0.0, 0.0, 1.0, 0.0);
//            Quat4d      quaternionIdentity = new Quat4d ();    quaternionIdentity.set(axisAngleIdentity);
//            
//            Quat4d      quaternionX      = new Quat4d ();      quaternionX.set(axisAngleX);
//            Quat4d      quaternionY      = new Quat4d ();      quaternionY.set(axisAngleY);
//            Quat4d      quaternionZ      = new Quat4d ();      quaternionZ.set(axisAngleZ);
//            Matrix4d    matrixCurrent    = new Matrix4d();    
//            Matrix4d    matrixRotated    = new Matrix4d();    matrixRotated.set(quaternionIdentity);
//            
//            matrixCurrent.set(quaternionZ);
//            
//            matrixRotated.set(quaternionIdentity);
//            matrixCurrent.set(quaternionY);         // TODO check order, currently Y X Z
//            matrixRotated.mul(matrixCurrent);
//            matrixCurrent.set(quaternionX);
//            matrixRotated.mul(matrixCurrent);
//            matrixCurrent.set(quaternionZ);
//            matrixRotated.mul(matrixCurrent);
//            
//            Quat4d      quaternionRotated   = new Quat4d ();    quaternionRotated.set(matrixRotated);
//            AxisAngle4d axisAngleRotated    = new AxisAngle4d(); axisAngleRotated.set(quaternionRotated);
//            return axisAngleRotated;
//    }

    /**
     * @return the eulerRotation about X axis
     */
    public double getEulerRotationX() {
        return eulerRotations[0];
    }

    /**
     * @return the eulerRotation about Y axis
     */
    public double getEulerRotationY() {
        return eulerRotations[1];
    }

    /**
     * @return the eulerRotation about Z axis
     */
    public double getEulerRotationZ() {
        return eulerRotations[2];
    }

    /**
     * @return the eulerRotations
     */
    public double[] getEulerRotations() {
        return eulerRotations;
    }

    /**
     * @param newEulerRotations the new eulerRotations value to set
     */
    public void setEulerRotations(double[] newEulerRotations) {
        if ((newEulerRotations != null) && newEulerRotations.length == 3)
             eulerRotations = newEulerRotations;
		else System.err.println("Illegal value for newEulerRotations");
    }
    
    /**
     * @return the siteIncluded
     */
    public boolean isSiteIncluded() {
        return siteIncluded;
    }

    /**
     * @param sitePresent whether the site is present for this Joint
     */
    public void setSiteIncluded(boolean sitePresent) {
        siteIncluded = sitePresent;
    }

    /**
     * @return the siteOffset
     */
    public Vector3d getSiteOffset() {
        return siteOffset;
    }
    /**
     * @return the siteOffset as a space-separated String
     */
    public String getSiteOffsetString() {
        String siteOffsetString = siteOffset.x + " " + siteOffset.y + " " + siteOffset.z;
        return siteOffsetString;
    }
    /**
     * @return the reverse (i.e. negated) siteOffset 3-tuple as a space-separated String
     */
    public String getReverseSiteOffsetString() 
	{
        String reverseSiteOffsetString = new String();
		if  (siteOffset.x == 0.0)
			 reverseSiteOffsetString += "0 ";
		else reverseSiteOffsetString += -siteOffset.x + " ";
		if  (siteOffset.y == 0.0)
			 reverseSiteOffsetString += "0 ";
		else reverseSiteOffsetString += -siteOffset.y + " ";
		if  (siteOffset.z == 0.0)
			 reverseSiteOffsetString += "0";
		else reverseSiteOffsetString += -siteOffset.z;
		
        return reverseSiteOffsetString;
    }


    /**
        * @param newSiteOffset the siteOffset value to set
     */
    public void setSiteOffset(Vector3d newSiteOffset) {
        siteOffset = newSiteOffset;
    }

    /**
     * @param newSiteOffsetX the new siteOffset.x value to set
     */
    public void setSiteOffsetX (double newSiteOffsetX) {
        siteOffset.x = newSiteOffsetX;
    }

    /**
     * @param newSiteOffsetY the new offset.y value to set
     */
    public void setSiteOffsetY (double newSiteOffsetY) {
        siteOffset.y = newSiteOffsetY;
    }

    /**
     * @param newSiteOffsetZ the new offset.z value to set
     */
    public void setSiteOffsetZ (double newSiteOffsetZ) {
        siteOffset.z = newSiteOffsetZ;
    }

    /**
     * @return the parent node
     */
    public BvhSkeletonParameters getParent() {
        return parent;
    }

    /**
     * @param parent the parent node value to set
     */
    public void setParent(BvhSkeletonParameters parent) {
        this.parent = parent;
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
