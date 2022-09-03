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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public abstract class BvhSkeletonParameters
{	
	/** whether to insert visualization geometry for X3D skeleton 
	    TODO integration with panel */
	private boolean x3dBallAndStickVisualizationIncluded = true;

    /**
     * unique bvhName of this node, matching BVH naming conventions
     */
    protected String            bvhName         = new String();

    /**
     * unique Joint name of this node, matching X3D H-Anim naming conventions
     */
    private String            hanimJointName         = new String();

    /**
     * unique Segment name of this node, matching X3D H-Anim naming conventions
     */
    private String            hAnimSegmentName         = new String();

    /**
     * unique jointDEF bvhName for this node set, matching X3D H-Anim naming conventions
     */
    private String              jointDEF         = new String();

    /**
     * unique segmentDEF bvhName for this node set, matching X3D H-Anim naming conventions
     */
    private String              segmentDEF         = new String();
    /**
     * unique segmentName for this node set, matching X3D H-Anim naming conventions if found or an arbitrary name otherwise
     */
    private String              segmentName         = new String();

    /**
     * unique siteDEF bvhName for this node set, matching X3D H-Anim naming conventions
     */
    private String              siteDEF         = new String();

    /**
     * unique site name for this node set, matching X3D H-Anim naming conventions by adding suffix to parent HAnimSegment name
     */
    private String              siteName         = new String();
	
    /**
     * X, Y, Z values
     */
    private Vector3d          offset       = new Vector3d();
	
    /**
     * summed X, Y, Z values from root
     */
    private Vector3d          cumulativeOffset       = new Vector3d();
	
    /**
     * names for measured channels, matching BVH naming conventions
     */
    protected ArrayList<String> channelNames = new ArrayList<>();
	
    /**
     * contained Joint nodes
     */
    protected ArrayList<Joint>  joints       = new ArrayList<>();
    
    // avoid internalization (I18N) localization (L10N) of decimal separator (decimal point)
    Locale currentLocale = Locale.getDefault();
//  System.out.println ("Current locale: " + currentLocale.getLanguage() + " " + currentLocale.getLanguage());
    Locale usLocale = new Locale ("en","US"); // avoid settings of current locale
    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(usLocale);
    DecimalFormat   twoDigitFormat = new DecimalFormat("0.00",     decimalFormatSymbols); // 0 means trailing zero required
    DecimalFormat threeDigitFormat = new DecimalFormat("0.000",    decimalFormatSymbols); // 0 means trailing zero required
    DecimalFormat  fourDigitFormat = new DecimalFormat("0.0000",   decimalFormatSymbols); // 0 means trailing zero required
    DecimalFormat  fiveDigitFormat = new DecimalFormat("0.00000",  decimalFormatSymbols); // 0 means trailing zero required
    DecimalFormat   sixDigitFormat = new DecimalFormat("0.000000", decimalFormatSymbols); // 0 means trailing zero required
    
    protected final String XPOSITION = "Xposition";
    protected final String YPOSITION = "Yposition";
    protected final String ZPOSITION = "Zposition";
    protected final String XROTATION = "Xrotation";
    protected final String YROTATION = "Yrotation";
    protected final String ZROTATION = "Zrotation";
    protected final String XSCALE    = "Xscale";
    protected final String YSCALE    = "Yscale";
    protected final String ZSCALE    = "Zscale";
	
	protected final double ONE_INCH_TO_METERS = 0.0254;
	
	private boolean bvhChannelX3dCommentInsertion = true;
    
    /**
     * Constructor initializes new object implemented by subclass
     * 
     */
    public BvhSkeletonParameters()
    {
//      setDefaultChannelNames(); // see TODO issues
    }

    /**
     * Indent spacing
     * @param level tree level of this node, used to support indent spacing
     * @return appropriate number of space characters for current node level
     */
    public String indentSpacing(int level) {
        String   result;
        String[] spacings = {"", 
                             " ", 
                             "  ", 
                             "   ", 
                             "    ", 
                             "     ", 
                             "      ", 
                             "       ", 
                             "        ", 
                             "         ", 
                             "          ", 
                             "           ", 
                             "            ", 
                             "             ", 
                             "              ", 
                             "               " };
        if  (level < spacings.length)
            result = spacings[level] + spacings[level]; // doule spacing
        else
        {
            result = ""; // reset, efficiently
            for (int i = 0; i == level; i++)
            {
                result += "  "; // double spacing
            }
        }
        return result;
    }
    /** 
     * Compute local AxisAngle4d SFRotation from Euler-angle rotations
	 * @see Joint#getAxisAngleRotation
     */
    public AxisAngle4d getAxisAngleRotation (double phi, double theta, double psi)
    {
            // javax.vecmath conversions (boy is this a convoluted API or what!)
            AxisAngle4d axisAngleX = new AxisAngle4d(1.0, 0.0, 0.0, phi);
            AxisAngle4d axisAngleY = new AxisAngle4d(0.0, 1.0, 0.0, theta);
            AxisAngle4d axisAngleZ = new AxisAngle4d(0.0, 0.0, 1.0, psi);

            AxisAngle4d axisAngleIdentity  = new AxisAngle4d(0.0, 0.0, 1.0, 0.0);
            Quat4d      quaternionIdentity = new Quat4d ();    quaternionIdentity.set(axisAngleIdentity);

            Quat4d      quaternionX      = new Quat4d ();      quaternionX.set(axisAngleX);
            Quat4d      quaternionY      = new Quat4d ();      quaternionY.set(axisAngleY);
            Quat4d      quaternionZ      = new Quat4d ();      quaternionZ.set(axisAngleZ);
            Matrix4d    matrixCurrent    = new Matrix4d();    
            Matrix4d    matrixRotated    = new Matrix4d();    matrixRotated.set(quaternionIdentity);

            matrixCurrent.set(quaternionZ);

            matrixRotated.set(quaternionIdentity);         // TODO check order!
            matrixCurrent.set(quaternionZ);	matrixRotated.mul(matrixCurrent);
            matrixCurrent.set(quaternionX);	matrixRotated.mul(matrixCurrent);
            matrixCurrent.set(quaternionY);	matrixRotated.mul(matrixCurrent);

            Quat4d      quaternionRotated   = new Quat4d ();    quaternionRotated.set(matrixRotated);
            AxisAngle4d axisAngleRotated    = new AxisAngle4d(); axisAngleRotated.set(quaternionRotated);
            return axisAngleRotated;
    }
	

    /** Whether HAnim bvhName was found for this BVH node */
	private boolean hAnimNameFound = false;
	
    /**
	 * Convert
     * @param newBvhName the BVH model bvhName to decipher
     * @return the corresponding HAnim node bvhName (if found), otherwise original bvhName
     */
    public void assignHAnimNamesFromBvhModelName(String newBvhName) 
	{
		String bvhNameLowerCase = newBvhName.toLowerCase();
		StringBuilder   hAnimJointNameSB = new StringBuilder();
		StringBuilder hAnimSegmentNameSB = new StringBuilder();
		
		// https://www.web3d.org/x3d/content/examples/Basic/HumanoidAnimation/tables/tables.html
		// https://www.web3d.org/x3d/content/examples/Basic/HumanoidAnimation/tables/HAnimJointLoa1Names19774V1.0.txt
		// https://www.web3d.org/x3d/content/examples/Basic/HumanoidAnimation/tables/HAnimJointLoa2Names19774V1.0.txt
		// https://www.web3d.org/x3d/content/examples/Basic/HumanoidAnimation/tables/HAnimJointLoa3Names19774V1.0.txt
		
		// https://www.web3d.org/documents/specifications/19774-1/V2.0/HAnim/concepts.html#Hierarchy
		
		/** first entry is hAnim name, remaining entries are aliases */
		String[][] LOA1JointSegmentAliasTable = {
			// Joint, segment, alias(es)
			{"humanoidroot","sacrum","root","Hips"},
			{"sacroiliac","pelvis", "Waist"},
			{"hip", 	"thigh",	"Hip"},
			{"knee",	"calf",		"Knee"},
			{"midtarsal","middistal","AnkleEnd"}, // watch order for bvhName collision
			{"ankle",	"hindfoot",	"Ankle"},
			{"Chest2",	"", 		"Chest2"}, // ignore, watch order for bvhName collision
			{"vl5",		"l5",		"Chest"},
			{"shoulder","upperarm",	"Shoulder"},
			{"elbow",	"forearm",	"Elbow"},
			{"wrist",	"hand", 	"Wrist"},
			{"skullbase","skull",	"Head","Noggin"},
//			{"",	""},
		};
		
		if (newBvhName.equals("ROOT"))
		{
			  hAnimJointNameSB.append("humanoidroot");
			hAnimSegmentNameSB.append("sacrum");
			hAnimNameFound = true;
		}
		else
		{
			if      (bvhNameLowerCase.contains("left"))
			{
				  hAnimJointNameSB.append("l_");
				hAnimSegmentNameSB.append("l_");
			}
			else if (bvhNameLowerCase.contains("right"))
			{
				  hAnimJointNameSB.append("r_");
				hAnimSegmentNameSB.append("r_");
			}
			
			for (int i = 0; i < LOA1JointSegmentAliasTable.length; i++)
			{
				for (int j = 2; j < LOA1JointSegmentAliasTable[i].length; j++)
				{
					if ((LOA1JointSegmentAliasTable[i]    != null) && 
						(LOA1JointSegmentAliasTable[i].length > j) && 
						(LOA1JointSegmentAliasTable[i][j] != null) && 
						 bvhNameLowerCase.contains(LOA1JointSegmentAliasTable[i][j].toLowerCase()))
					{
						  hAnimJointNameSB.append(LOA1JointSegmentAliasTable[i][0]);
						hAnimSegmentNameSB.append(LOA1JointSegmentAliasTable[i][1]);
						hAnimNameFound = true;
						  setHAnimJointName(hAnimJointNameSB.toString());
						setHAnimSegmentName(hAnimSegmentNameSB.toString());
						return; // hAnim bvhName found and set
					}
				}
			}
		}
		setHAnimJointName(newBvhName); // hAnim bvhName not found, not supported in LOA-1 mappings.  keep original BVH bvhName unchanged.
    }

    /**
     * @return the node bvhName
     */
    public String getBvhName() {
        return bvhName;
    }

    /**
     * @param newBvhName the node bvhName to set
     */
    public void setBvhName(String newBvhName) {
        bvhName = newBvhName;
    }

    /**
     * @return the 3-tuple offset
     */
    public Vector3d getOffset()
	{
        return offset;
    }
    
    /**
     * @return the offset 3-tuple as a space-separated String
     */
    public String getOffsetString()
	{
        String offsetString = offset.x + " " + offset.y + " " + offset.z;
        return offsetString;
    }
    
    /**
     * @return the reverse (i.e. negated) offset 3-tuple as a space-separated String
     */
    public String getReverseOffsetString()
	{
        String reverseOffsetString = new String();
		if  (offset.x == 0.0)
			 reverseOffsetString += "0 ";
		else reverseOffsetString += -offset.x + " ";
		if  (offset.y == 0.0)
			 reverseOffsetString += "0 ";
		else reverseOffsetString += -offset.y + " ";
		if  (offset.z == 0.0)
			 reverseOffsetString += "0";
		else reverseOffsetString += -offset.z;
		
        return reverseOffsetString;
    }

    /**
     * @param newOffset the new offset value to set
     */
    public void setOffset(Vector3d newOffset) {
        offset = newOffset;
    }

    /**
     * @param newOffsetX the new offset.x value to set
     */
    public void setOffsetX (double newOffsetX) {
        offset.x = newOffsetX;
    }

    /**
     * @param newOffsetY the new offset.y value to set
     */
    public void setOffsetY (double newOffsetY) {
        offset.y = newOffsetY;
    }

    /**
     * @param newOffsetZ the new offset.z value to set
     */
    public void setOffsetZ (double newOffsetZ) {
        offset.z = newOffsetZ;
    }

	/**
	 * @return the cumulativeOffset
	 */
	public String getCumulativeOffsetString() {
        String offsetString = 
				cumulativeOffset.x + " " + 
				cumulativeOffset.y + " " + 
				cumulativeOffset.z;
        return offsetString;
	}

	/**
	 * @return the cumulativeOffset
	 */
	public Vector3d getCumulativeOffset() {
		return cumulativeOffset;
	}

	/**
	 * @param cumulativeOffset the cumulativeOffset to set
	 */
	public void setCumulativeOffset(Vector3d cumulativeOffset) {
		this.cumulativeOffset = cumulativeOffset;
	}

    /**
     * @return the channel size
     */
    public int getChannelSize() {
        return channelNames.size();
    }

    /**
     * @return the channelNames
     */
    public ArrayList<String> getChannelNamesArrayList() {
        return channelNames;
    }

    /**
     * @return the channelNames as a space-separated String
     */
    public String getChannelNamesString() {
        String channelNamesString = new String();
        
        Iterator<String> channelNamesIterator = channelNames.iterator();
        while   (channelNamesIterator.hasNext())
        {
            String channelName = channelNamesIterator.next();
            channelNamesString += channelName + " ";
        }
        return channelNamesString.trim();
    }

    /**
     * @param newChannelNames the new channelNames value to set
     */
    public void setChannelNames(ArrayList<String> newChannelNames) {
        // TODO warn if unusual sequence
        channelNames = newChannelNames;
    }

    /**
     * @param newChannelNames the new channelNames value to set
     */
    public void setChannelNames(String[] newChannelNames) {
        // TODO warn if unusual sequence
        clearChannels();
		channelNames.addAll(Arrays.asList(newChannelNames));
    }

    /**
     * clear all contained channels
     */
    public void clearChannels() {
        channelNames = new ArrayList<>();
    }

    /**
     * Initialize typical values; 
     * TODO leave values unset to avoid unintended defaults?
     * TODO are these even correct, is there a default?
     */
    public void setDefaultChannelNames() {
        channelNames.add(XROTATION); 
        channelNames.add(ZROTATION);
        channelNames.add(YROTATION);
    }

    /**
     * @return the joints
     */
    public ArrayList<Joint> getJoints() {
        return joints;
    }

    /**
     * @param newJoints the joints value to set
     */
    public void setJoints(ArrayList<Joint> newJoints) {
        joints = newJoints;
    }

    /**
     * @param newJoint the new Joint to append
     */
    public void addJoint(Joint newJoint) {
        joints.add(newJoint);
    }

    /**
     * @param index number of the Joint to remove from list
     */
    public void removeJoint(int index) {
        joints.remove(index);
    }

    /**
     * @param newJoint the new Joint to insert
     */
    public void insertJoint(int index, Joint newJoint) {
        joints.add(index, newJoint);
    }

    /**
     * clear all contained joints
     */
    public void clearJoints() {
        joints = new ArrayList<>();
    }

	/**
	 * @return the bvhChannelCommentInsertion
	 */
	public boolean isBvhChannelX3dCommentInsertion() {
		return bvhChannelX3dCommentInsertion;
	}

	/**
	 * @param bvhChannelX3dCommentInsertion the bvhChannelCommentInsertion to set
	 */
	public void setBvhChannelX3dCommentInsertion(boolean newBvhChannelX3dCommentInsertionStatus) {
		this.bvhChannelX3dCommentInsertion = newBvhChannelX3dCommentInsertionStatus;
	}

	/**
	 * whether to insert visualization geometry for X3D skeleton
	 * @return the x3dBallAndStickVisualizationIncluded
	 */
	public boolean isX3dBallAndStickVisualizationIncluded() {
		return x3dBallAndStickVisualizationIncluded;
	}

	/**
	 * whether to insert visualization geometry for X3D skeleton
	 * @param newX3dBallAndStickVisualizationIncluded the x3dBallAndStickVisualizationIncluded to set
	 */
	public void setX3dBallAndStickVisualizationIncluded(boolean newX3dBallAndStickVisualizationIncluded) {
		this.x3dBallAndStickVisualizationIncluded = newX3dBallAndStickVisualizationIncluded;
	}

	/**
	 * @return the jointDEF for this node
	 */
	public String getJointDEF() {
		return jointDEF;
	}

	/**
	 * @param newJointDEF the jointDEF to set for this node
	 */
	public void setJointDEF(String newJointDEF) {
		jointDEF = newJointDEF;
	}

	/**
	 * @return the segmentDEF for this node
	 */
	public String getSegmentDEF() {
		return segmentDEF;
	}

	/**
	 * @param newSegmentDEF the segmentDEF to set for this node
	 */
	public void setSegmentDEF(String newSegmentDEF) {
		segmentDEF = newSegmentDEF;
	}

	/**
	 * @return the siteDEF for this node
	 */
	public String getSiteDEF() {
		return siteDEF;
	}

	/**
	 * @param newSiteDEF the siteDEF to set for this node
	 */
	public void setSiteDEF(String newSiteDEF) {
		siteDEF = newSiteDEF;
	}

	/**
	 * @return the hAnimNameFound
	 */
	public boolean isHAnimNameFound() {
		return hAnimNameFound;
	}

	/**
	 * @return the hanimJointName
	 */
	public String getHAnimJointName() {
		return hanimJointName;
	}

	/**
	 * @param newHanimJointName the hanimName to set
	 */
	public void setHAnimJointName(String newHanimJointName) {
		hanimJointName = newHanimJointName;
	}

	/**
	 * @return the hAnimSegmentName
	 */
	public String getHAnimSegmentName() {
		return hAnimSegmentName;
	}

	/**
	 * @param newHAnimSegmentName the hAnimSegmentName to set
	 */
	public void setHAnimSegmentName(String newHAnimSegmentName) {
		hAnimSegmentName = newHAnimSegmentName;
	}

	/**
	 * @return the segmentName
	 */
	public String getSegmentName() {
		return segmentName;
	}

	/**
	 * @param newSegmentName the segmentName to set
	 */
	public void setSegmentName(String newSegmentName) {
		segmentName = newSegmentName;
	}

	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * @param newSiteName the siteName to set
	 */
	public void setSiteName(String newSiteName) {
		siteName = newSiteName;
	}
}
