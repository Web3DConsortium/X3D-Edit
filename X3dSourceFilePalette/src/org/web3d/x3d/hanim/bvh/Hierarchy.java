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

//import java.util.ArrayList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

//import javax.vecmath.AxisAngle4d;
//import javax.vecmath.Matrix4f;
//import javax.vecmath.Quat4d;
//import javax.vecmath.Vector3d;

/**
 *
 * @author Don Brutzman
 */


public class Hierarchy extends BvhSkeletonParameters
{

	/**
	 * @return the skeletonJointName
	 */
	public String getSkeletonJointName() {
		return skeletonJointName;
	}

	/**
	 * @param newSkeletonJointName the skeletonJointName to set
	 */
	public void setSkeletonJointName(String newSkeletonJointName) {
		skeletonJointName = newSkeletonJointName;
	}
    /**
     * unique DEF bvhName for this HAnimHumanoid node, matching X3D H-Anim naming conventions; note that corresponding Joint has a different DEF bvhName
     */
    private String              DEF         = new String();
	
    /**
     * unique DEF-bvhName prefix for this HAnimHumanoid node, matching X3D H-Anim naming conventions; note that corresponding Joint has a different DEF bvhName
     */
    private String              modelName         = new String();
	
    /**
     * HIERARCHY ROOT bvhName from this BVH file
     */
    private String              hierarchyRootName         = new String();
	
    /**
     * HIERARCHY ROOT bvhName from this BVH file
     */
    private String              skeletonJointName         = new String();
	
    /**
     * number of values expected in each frame, based on cumulative CHANNELS definitions contained in HIERARCHY ROOT
     */
    private int                 expectedChannelsPerFrame = 0;
    
    /**
     * All contained Joint nodes, in same order encountered in BVH file, for <code>joints</code> field.
     */
    protected ArrayList<Joint>  hierarchyJointList       = new ArrayList<>();
    
    /**
     * DEF names for all contained Segment nodes, in same order encountered in BVH file, for <code>HAnimHumanoid segments</code> field.
     */
    protected ArrayList<String>  hierarchySegmentList = new ArrayList<>();
    
    /**
     * DEF names for all contained Site nodes, in same order encountered in BVH file, for <code>HAnimHumanoid sites</code> field.
     */
    protected ArrayList<String>  hierarchySiteList    = new ArrayList<>();
    
    /**
     * DEF names for all contained Viewpoint nodes, in same order encountered in BVH file, for <code>HAnimHumanoid viewpoints</code> field.
     */
    protected ArrayList<String>  hierarchySiteViewpointList    = new ArrayList<>();
    
    /**
     * contained Motion nodes.  Typically a single Motion node, can support multiple if allowed by BVH
     */
    protected ArrayList<Motion>  motions                 = new ArrayList<>();
    
    /**
     * X3D version
	 * @see <a href="https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Validation">X3D Scene Authoring Hints: Validation of X3D Scenes using DTD and XML Schema</a>
     */
    private String  version       = "3.3";
	
	private double minX, minY, minZ = Double.MAX_VALUE;
	private double maxX, maxY, maxZ = Double.MIN_VALUE;
	
	/** Ensure feet are on the ground */
	private double heightOffset = 0.0;
    
    /**
     * Estimated scale factor based on model height
     */
	private double scaleFactor = 1.0;
    
    /**
     * Visualization Segment name (if used)
     */
	private String rootSegmentName = new String();
	
    /**
     * Constructor creates new Hierarchy object
     */
    public Hierarchy()
    {
        // initialization code goes here
    }

    /**
     * @return the serialized object in plain-text BVH format
     */
    public String getHierarchyOutputBVH() {
        StringBuilder outputBVH = new StringBuilder();
        
        outputBVH.append("HIERARCHY").append("\n");
        outputBVH.append("ROOT").append(" ").append(getHierarchyRootName()).append("\n");
        outputBVH.append("{").append("\n");
        outputBVH.append(indentSpacing(1));
        outputBVH.append("OFFSET ").append(getOffsetString()).append("\n");
        outputBVH.append(indentSpacing(1));
        outputBVH.append("CHANNELS ").append(getChannelSize()).append(" ").append(getChannelNamesString()).append("\n");

        // recursive exposure of each JOINT (containing OFFSET CHANNELS JOINT)
        Iterator<Joint> jointsIterator = joints.iterator();
        while   (jointsIterator.hasNext())
        {
            Joint joint = jointsIterator.next();
            outputBVH.append(joint.getHierarchyOutputBVH(1));
        }
        outputBVH.append("}").append("\n"); // end ROOT
        
        // output each Motion which contain Frames data
        Iterator<Motion> motionsIterator = motions.iterator();
        while   (motionsIterator.hasNext())
        {
            Motion motion = motionsIterator.next();
            outputBVH.append(motion.getHierarchyOutputBVH());
        }
        return outputBVH.toString();
    }

    /**
     * @return the serialized object in XML-based .x3d encoding
     */
    public String getHierarchyOutputX3D(String filename)
	{
        StringBuilder outputX3DHead = new StringBuilder();
        StringBuilder outputX3DInfo = new StringBuilder();
        StringBuilder outputX3DBody = new StringBuilder();
        boolean verbose = false;
		
        setModelName("Bvh" + filename.trim()); // avoids trouble when file bvhName begins with a number, also indicates genesis
        if (getModelName().endsWith(".x3d"))
            setModelName(getModelName().substring(0, getModelName().indexOf(".x3d")));
        
        outputX3DHead.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
        outputX3DHead.append("<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D ").append(getVersion()).append("//EN\" \"https://www.web3d.org/specifications/x3d-").append(getVersion()).append(".dtd\">").append("\n");
        outputX3DHead.append("<X3D profile='Immersive' version='").append(getVersion()).append("' xmlns:xsd='http://www.w3.org/2001/XMLSchema-instance' xsd:noNamespaceSchemaLocation='https://www.web3d.org/specifications/x3d-").append(getVersion()).append(".xsd'>").append("\n");
        outputX3DHead.append("  <head>").append("\n");
        outputX3DHead.append("    <component level='1' name='H-Anim'/>").append("\n"); // TODO level 2 when mocap specification available
        outputX3DHead.append("    <meta content='").append(getModelName()).append(".x3d' name='title'/>").append("\n");
        outputX3DHead.append("    <meta content='BVH file conversion: *enter description here, short-sentence summaries preferred*' name='description'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter name of original author here*' name='creator'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter date of initial version here*' name='created'/>").append("\n");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        outputX3DHead.append("    <meta content='").append(dateFormat.format(date)).append("' name='translated'/>").append("\n");
        outputX3DHead.append("    <meta content='").append(dateFormat.format(date)).append("' name='modified'/>").append("\n");
        if (verbose)
        {
        outputX3DHead.append("    <meta content='*enter version here, if any*' name='version'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter reference citation or relative/online url here*' name='reference'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter additional url/bibliographic reference information here*' name='reference'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter reference resource here if required to support function, delivery, or coherence of content*' name='requires'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter copyright information here* Example: Copyright (c) Web3D Consortium Inc. 2016 to present' name='rights'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter drawing filename/url here*' name='drawing'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter image filename/url here*' name='Image'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter movie filename/url here*' name='MovingImage'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter photo filename/url here*' name='photo'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter subject keywords here*' name='subject'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter permission statements or url here*' name='accessRights'/>").append("\n");
        outputX3DHead.append("    <meta content='*insert any known warnings, bugs or errors here*' name='warning'/>").append("\n");
        outputX3DHead.append("    <!-- Additional authoring resources for meta-tags: http://www.dublincore.org/documents/dcmi-terms http://www.dublincore.org/documents/dces http://www.w3.org/TR/html4/struct/global.html#h-7.4.4 http://vancouver-webpages.com/META http://vancouver-webpages.com/META/about-mk-metas2.html Additional authoring resources for language codes: http://www.rfc-editor.org/rfc/bcp/bcp47.txt http://www.loc.gov/standards/iso639-2/langhome.html http://www.iana.org/numbers.html#L -->").append("\n");
        }
        outputX3DHead.append("    <meta content='https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#MOCAP' name='reference'/>").append("\n");
        outputX3DHead.append("    <meta content='Java BVH to X3D Converter, org.web3d.x3d.hanim.bvh package' name='generator'/>").append("\n");
        outputX3DHead.append("    <meta content='X3D-Edit 4.0, https://savage.nps.edu/X3D-Edit' name='generator'/>").append("\n");
        outputX3DHead.append("    <meta content='*enter online Uniform Resource Identifier (URI) or Uniform Resource Locator (URL) address for this file here*' name='identifier'/>").append("\n");
        outputX3DHead.append("    <meta content='../license.html' name='license'/>").append("\n");
        outputX3DHead.append("  </head>").append("\n");
        outputX3DHead.append("  <Scene>").append("\n");
//		outputX3DHead.append(indentSpacing(2))append("<Background skyColor='1 1 1'/>").append("\n"); // TODO color default
		outputX3DHead.append(indentSpacing(2));
		outputX3DHead.append("<NavigationInfo type='\"EXAMINE\" \"ANY\"'/>\n"); // default value but needed by some players
        
        outputX3DInfo.append(indentSpacing(2));
        outputX3DInfo.append("<Group DEF='").append(getModelName()).append("_").append("BvhToX3dConversionImportInformation'>\n");
        outputX3DInfo.append(indentSpacing(3));
        outputX3DInfo.append("<!-- ").append(hierarchyJointList.size()).append(" BVH JOINT definitions found, following a single HIERARCHY ROOT -->\n");
        outputX3DInfo.append(indentSpacing(3));
        outputX3DInfo.append("<!-- BVH HIERARCHY model size computations:");
        outputX3DInfo.append(" minX=").append(getMinX()).append(", maxX=").append(getMaxX()).append(", width=" ).append(getWidth() ).append(";");
        outputX3DInfo.append(" minY=").append(getMinY()).append(", maxY=").append(getMaxY()).append(", height=").append(getHeight()).append(";");
        outputX3DInfo.append(" minZ=").append(getMinZ()).append(", maxZ=").append(getMaxZ()).append(", depth=" ).append(getDepth() ).append(" -->\n");
		
		if      (getHeight() > 250.0) // mm
			 setScaleFactor(0.001);   // mm to m
		else if (getHeight() > 100.0) // cm
			 setScaleFactor(0.01);    // cm to m
		else if (getHeight() > 24.0)  // Inches
			 setScaleFactor(ONE_INCH_TO_METERS);  // Inches to Meters
		else if (getHeight() > 2.5)   // perhaps a tall person? expected average height 1.8m, BVH file might only include upper or lower body
			 setScaleFactor(0.1);
		else if (getHeight() < 0.002)
			 setScaleFactor(1000.0);
		else if (getHeight() < 0.02)
			 setScaleFactor(100.0);
		else if (getHeight() < 0.2)
			 setScaleFactor(10.0);
        if (getScaleFactor() != 1.0)
		{
			outputX3DInfo.append(indentSpacing(3));
			outputX3DInfo.append("<!-- Estimated rescaling to meters based on height: scaleFactor=").append(getScaleFactor())
					 .append(" for modified height of ").append(threeDigitFormat.format(getHeight() * getScaleFactor())).append("m -->\n");
		}
		if (Math.abs(getMinY() * getScaleFactor()) > 0.1)
		{
			setHeightOffset(-getMinY() * getScaleFactor());
			outputX3DInfo.append(indentSpacing(3));
			outputX3DInfo.append("<!-- Vertical offset to move bottom of BVH figure to ground plane (adjusted in HAnimJoint containerField='skeleton'): heightOffset=").append(getHeightOffset()).append("m -->\n");
		}	
		// set initial translation to match initial position, if MOTIONS data provided
		String initialPositionOffset, initialPositionScaled;
		String viewpointOffsetZ = "8";
		
		initialPositionOffset = threeDigitFormat.format(                 (maxX - minX)/2.0) + " " + 
								threeDigitFormat.format(                ((maxY - minY)/2.0) + getHeightOffset()) + " " + 
								threeDigitFormat.format(                 (maxZ - minZ)/2.0);
		
		initialPositionScaled = threeDigitFormat.format(getScaleFactor()* (maxX - minX)/2.0) + " " + 
								threeDigitFormat.format(getScaleFactor()*((maxY - minY)/2.0) + getHeightOffset()) + " " + 
								threeDigitFormat.format(getScaleFactor()* (maxZ - minZ)/2.0);
		
		String initialMotionViewPosition = "";
		String   finalMotionViewPosition = "";
		if ((getMotions().size() > 0) && (getMotions().get(0).getFrame(0).length > 0))
		{
			double[] frameValues = getMotions().get(0).getFrame(0);
			if (frameValues.length >= 3)
			    initialMotionViewPosition = threeDigitFormat.format(frameValues[0]*getScaleFactor()) + " " + 
										    threeDigitFormat.format(frameValues[1]*getScaleFactor()) + " " + 
										    threeDigitFormat.format(frameValues[2]*getScaleFactor()  + Double.parseDouble(viewpointOffsetZ));
			frameValues = getMotions().get(0).getFrame(getMotions().get(0).getFrameCount()-1);
			if (frameValues.length >= 3)
			    finalMotionViewPosition   = threeDigitFormat.format(frameValues[0]*getScaleFactor()) + " " + 
										    threeDigitFormat.format(frameValues[1]*getScaleFactor()) + " " + 
										    threeDigitFormat.format(frameValues[2]*getScaleFactor()  + Double.parseDouble(viewpointOffsetZ));
		}
		int viewpointIndent = 2;
		if (initialPositionScaled.length() > 0) // wrap scaling Transform if needed
		{
			outputX3DBody.append(indentSpacing(viewpointIndent));
			outputX3DBody.append("<!-- initialPositionOffset computation: ").append(initialPositionOffset).append(", initialPositionScaled computation: ").append(initialPositionScaled).append(" -->\n");
			outputX3DBody.append(indentSpacing(viewpointIndent));
			outputX3DBody.append("<Transform DEF='InitialPositionScaled' translation='").append(initialPositionScaled).append("'>\n");
			viewpointIndent++;
		}
		// these Viewpoint nodes are outside HAnimHumanoid and thus do not need USE entries
        outputX3DBody.append(indentSpacing(viewpointIndent));
        outputX3DBody.append("<Viewpoint description='").append(getModelName()).append(" model BVH to X3D conversion, from ").append(viewpointOffsetZ).append("m' position='0 0 8'/>\n");
		outputX3DBody.append(indentSpacing(viewpointIndent));
        outputX3DBody.append("<Viewpoint description='").append(getModelName()).append(" initial motion position' position='").append(initialMotionViewPosition).append("'/>\n");
		outputX3DBody.append(indentSpacing(viewpointIndent));
        outputX3DBody.append("<Viewpoint description='").append(getModelName()).append(  " final motion position' position='").append(  finalMotionViewPosition).append("'/>\n");
		if (initialPositionScaled.length() > 0) // finish wrap scaling Transform if needed
		{
	        outputX3DBody.append(indentSpacing(viewpointIndent-1));
		    outputX3DBody.append("</Transform>\n");
		}

		// HIERARCHY
	
		// top-level naming
		setBvhName (getHierarchyRootName());
		setDEF     (getModelName() + "_" + getHierarchyRootName());
//		setSkeletonJointName(              "HIERARCHY_" + getHierarchyRootName());   // pseudo joint to match HIERARCHY
//		setJointDEF(getModelName() + "_" + getSkeletonJointName());                  // pseudo joint to match HIERARCHY
//		String skeletonSegmentName       = "HIERARCHY_SEGMENT_" + getHierarchyRootName();
//		String skeletonSegmentDEF        = getModelName() + "_" + 
//										   "HIERARCHY_SEGMENT_" + getHierarchyRootName();
//		setSegmentName(skeletonSegmentName);
		assignHAnimNamesFromBvhModelName(getBvhName()); // potential to override if near-match is recognized
		setJointDEF(modelName + "_" + getHAnimJointName());
		String skeletonJointDEF = getJointDEF();
		setSkeletonJointName(getHAnimJointName()); // do not incrementHierarchyJointList since not part of BVH
		if (!getHAnimSegmentName().isEmpty())
			setSegmentName(getHAnimSegmentName());
		setSegmentDEF(modelName + "_" + getSegmentName());

		// Begin HAnimHumanoid.  Note that precise naming conventions occur throughout.
		outputX3DBody.append(indentSpacing(2));
        outputX3DBody.append("<HAnimHumanoid DEF='").append(getDEF()).append("'"); 
		// h-anim metadata
        outputX3DBody.append(" info='\"authorEmail=*TODO*\" \"authorName=*TODO*\" \"copyright=Copyright 2017\" \"humanoidVersion=*TODO*\" \"usageRestrictions=*TODO*\"'");
        outputX3DBody.append(" name='").append(getBvhName()).append("' version='2.0'");
		outputX3DBody.append(">\n"); // finish opening HAnimHumanoid element
		
		// humanoidroot Site Viewpoint attached to top-level HAnimHumanoid
		String initialSiteViewpointName = getHAnimJointName() + "_view";
		String initialSiteViewpointDEF  = modelName + "_" + initialSiteViewpointName;
		incrementHierarchySiteList         (initialSiteViewpointDEF); // add to list
		incrementHierarchySiteViewpointList(initialSiteViewpointDEF); // add to list
		outputX3DBody.append(indentSpacing(3)).append("<!-- Top-level HAnimSite/Viewpoint attached to HAnimHumanoid is unaffected by motion animation -->\n");
		outputX3DBody.append(indentSpacing(3)).append("<HAnimSite DEF='").append(initialSiteViewpointDEF).append("' containerField='viewpoints' name='").append(initialSiteViewpointName).append("'>\n");
		outputX3DBody.append(indentSpacing(4)).append("<Viewpoint DEF='").append(initialSiteViewpointDEF).append("point' description='").append(getModelName()).append(" front view towards HAnimHumanoid center' position='0 0 ").append(8.0 / getScaleFactor()).append("'/>\n");
		outputX3DBody.append(indentSpacing(3)).append("</HAnimSite>\n");
		
        // ROOT, OFFSET
		
        outputX3DBody.append(indentSpacing(3));
        outputX3DBody.append("<!-- insert pseudo Joint for humanoidroot skeleton (matching root HIERARCHY in original BVH mocap model, but appearing as skeleton field in X3D HAnimHumanoid) -->\n");		
        outputX3DBody.append(indentSpacing(3));
        outputX3DBody.append("<HAnimJoint DEF='").append(skeletonJointDEF).append("'");
		if (!getOffsetString().equals("0.0 0.0 0.0") && !getOffsetString().equals("0 0 0"))
			outputX3DBody.append(" center='").append(getOffsetString()).append("'");
		outputX3DBody.append(" containerField='skeleton'").append(" name='").append(getSkeletonJointName()).append("'");
        // place inside the HAnimHumanoid
		if (getScaleFactor() != 1.0)
		{
			outputX3DBody.append(" scale='").append(getScaleFactor()).append(" ").append(getScaleFactor()).append(" ").append(getScaleFactor()).append("'"); 
		}
		if (initialPositionScaled.length() > 0)
		{
			outputX3DBody.append(" translation='").append(initialPositionScaled).append("'"); // note HAnimJoint uses center for initial position
		}
		outputX3DBody.append(">\n");
			
        // CHANNELS
		if (isBvhChannelX3dCommentInsertion())
		{
			outputX3DBody.append(indentSpacing(4));
        	outputX3DBody.append("<!-- BVH ROOT ").append(getHierarchyRootName()).append(", OFFSET ").append(getHierarchyRootJoint().getOffsetString()).append(", CHANNELS ").append(getChannelSize()).append(" ").append(getChannelNamesString()).append(" -->\n");
		}
		rootSegmentName = getSegmentDEF(); // do not incrementHierarchySegmentList since not part of BVH
		outputX3DBody.append(indentSpacing(4)).append("<HAnimSegment DEF='").append(getSegmentDEF()).append("' name='").append(getHAnimSegmentName()).append("'>\n"); // TODO check bvhName
		if (isX3dBallAndStickVisualizationIncluded())
		{
			outputX3DBody.append(indentSpacing(5));
			outputX3DBody.append("<!-- humanoidroot child HAnimSegment with visualization root shape plus hidden DEF geometry for later use (radius 1 inch) -->\n");
			outputX3DBody.append(indentSpacing(5)).append("<Switch whichChoice='0'>\n");
			outputX3DBody.append(indentSpacing(6)).append("<Group>\n");
			outputX3DBody.append(indentSpacing(7)).append("<TouchSensor description='HAnimHumanoid ROOT ").append(getBvhName()).append(", HAnimSegment ").append(getSegmentName()).append("'/>\n");
			outputX3DBody.append(indentSpacing(7)).append("<Shape DEF='HAnimRootShape'>\n");
			outputX3DBody.append(indentSpacing(8)).append("<Sphere DEF='HAnimJointSphere' radius='").append(ONE_INCH_TO_METERS / getScaleFactor()).append("'/>\n"); // 1 inch
			outputX3DBody.append(indentSpacing(8)).append("<Appearance>\n");
			outputX3DBody.append(indentSpacing(9)).append("<Material DEF='HAnimRootMaterial' diffuseColor='0.8 0 0' transparency='0.3'/>\n");
			outputX3DBody.append(indentSpacing(8)).append("</Appearance>\n");
			outputX3DBody.append(indentSpacing(7)).append("</Shape>\n");
			outputX3DBody.append(indentSpacing(6)).append("</Group>\n");
			outputX3DBody.append(indentSpacing(6)).append("<Shape DEF='HAnimJointShape'>\n");
			outputX3DBody.append(indentSpacing(7)).append("<Sphere USE='HAnimJointSphere'/>\n");
			outputX3DBody.append(indentSpacing(7)).append("<Appearance>\n");
			outputX3DBody.append(indentSpacing(8)).append("<Material DEF='HAnimJointMaterial' diffuseColor='0 0 0.8' transparency='0.3'/>\n");
			outputX3DBody.append(indentSpacing(7)).append("</Appearance>\n");
			outputX3DBody.append(indentSpacing(6)).append("</Shape>\n");
			outputX3DBody.append(indentSpacing(6)).append("<Shape>\n");
			outputX3DBody.append(indentSpacing(7)).append("<LineSet vertexCount='2'>\n"); // containerField='geometry'
			outputX3DBody.append(indentSpacing(8)).append("<Coordinate point='0 0 0 0 0 0'/>\n");
			outputX3DBody.append(indentSpacing(8)).append("<ColorRGBA DEF='HAnimSegmentLineColorRGBA' color='1 1 0 1 1 1 0 0.1'/>\n");
			outputX3DBody.append(indentSpacing(7)).append("</LineSet>\n");
			outputX3DBody.append(indentSpacing(6)).append("</Shape>\n");
			outputX3DBody.append(indentSpacing(6)).append("<Shape DEF='HAnimSiteShape'>\n");
			outputX3DBody.append(indentSpacing(7)).append("<IndexedFaceSet DEF='DiamondIFS' coordIndex='0 1 2 -1 0 2 3 -1 0 3 4 -1 0 4 1 -1 5 2 1 -1 5 3 2 -1 5 4 3 -1 5 1 4 -1' creaseAngle='0.5' solid='false'>\n");
			outputX3DBody.append(indentSpacing(8)).append("<Coordinate point='0 ").append(ONE_INCH_TO_METERS / getScaleFactor()).append(" 0 -").append(ONE_INCH_TO_METERS / getScaleFactor()).append(" 0 0 0 0 ").append(ONE_INCH_TO_METERS / getScaleFactor()).append(" ").append(ONE_INCH_TO_METERS / getScaleFactor()).append(" 0 0 0 0 -").append(ONE_INCH_TO_METERS / getScaleFactor()).append(" 0 -").append(ONE_INCH_TO_METERS / getScaleFactor()).append(" 0'/>\n");
			outputX3DBody.append(indentSpacing(7)).append("</IndexedFaceSet>\n");
			outputX3DBody.append(indentSpacing(7)).append("<Appearance>\n");
			outputX3DBody.append(indentSpacing(8)).append("<Material diffuseColor='1 0.5 0' transparency='0.3'/>\n");
			outputX3DBody.append(indentSpacing(7)).append("</Appearance>\n");
			outputX3DBody.append(indentSpacing(6)).append("</Shape>\n");
			outputX3DBody.append(indentSpacing(6)).append("<Shape>\n");
			outputX3DBody.append(indentSpacing(7)).append("<LineSet vertexCount='2'>\n"); // containerField='geometry'
			outputX3DBody.append(indentSpacing(8)).append("<Coordinate point='0 0 0 0 0 0'/>\n");
			outputX3DBody.append(indentSpacing(8)).append("<ColorRGBA DEF='HAnimSiteLineColorRGBA' color='1 0.5 0 1 1 0.5 0 0.1'/>\n");
			outputX3DBody.append(indentSpacing(7)).append("</LineSet>\n");
			outputX3DBody.append(indentSpacing(6)).append("</Shape>\n");
			outputX3DBody.append(indentSpacing(5)).append("</Switch>\n");
				
			// loop to draw line geometry from current Joint to its child Joints, if any
			Iterator<Joint> jointsIterator = getJoints().iterator();
			while   (jointsIterator.hasNext())
			{
				Joint nextChildJoint = jointsIterator.next();
				outputX3DBody.append(indentSpacing(5)).append("<!-- HAnimSegment OFFSET visualization line from current <HAnimJoint name='").append(getHAnimJointName()).append("'/> to child <HAnimJoint name='").append(nextChildJoint.getHAnimJointName()).append("'/> -->\n");
				outputX3DBody.append(indentSpacing(5)).append("<Shape>\n");
				outputX3DBody.append(indentSpacing(6)).append("<LineSet vertexCount='2'>\n"); // containerField='geometry'
				outputX3DBody.append(indentSpacing(7)).append("<Coordinate point='0 0 0 ").append(nextChildJoint.getOffsetString()).append("'/>\n"); // TODO confirm both points
				outputX3DBody.append(indentSpacing(7)).append("<ColorRGBA USE='HAnimSegmentLineColorRGBA'/>\n");
				outputX3DBody.append(indentSpacing(6)).append("</LineSet>\n");
				outputX3DBody.append(indentSpacing(5)).append("</Shape>\n");
			}
		}
		else
		{
			outputX3DBody.append(indentSpacing(5));
			outputX3DBody.append("<!-- humanoidroot child HAnimSegment with no visualization root shape -->\n");
		}
		outputX3DBody.append(indentSpacing(4)).append("</HAnimSegment>\n");
		
		// recursive exposure of each top-level JOINT (containing OFFSET CHANNELS JOINT)
        Iterator<Joint> jointsIterator = joints.iterator();
        while   (jointsIterator.hasNext())
        {
            Joint joint = jointsIterator.next();
            outputX3DBody.append(joint.getHierarchyOutputX3D(4, getModelName(), getSkeletonJointName()));
        }
		// pseudo joint for ROOT
        outputX3DBody.append(indentSpacing(3));
        outputX3DBody.append("</HAnimJoint>").append("\n");

        outputX3DBody.append(indentSpacing(3)).append("<!-- top-level USE nodes follow DEF declarations and can be employed by inverse-kinematics (IK) engines or other HAnim tools -->\n");

		// first entry in joints list is pseudo joint for HAnimHumanoid skeleton root
        outputX3DBody.append(indentSpacing(3)).append("<HAnimJoint USE='").append(skeletonJointDEF).append("' containerField='joints'/>\n"); // TODO check bvhName
        // now output remainder of joints list
        Iterator<Joint> jointsDefIterator = getHierarchyJointList().iterator();
        while   (jointsDefIterator.hasNext())
        {
            String jointDEF = jointsDefIterator.next().getJointDEF();
            outputX3DBody.append(indentSpacing(3)).append("<HAnimJoint USE='").append(jointDEF).append("' containerField='joints'/>\n"); // TODO check bvhName
        }
		// first entry in segments list is visualizationSegmentName, if any
		if (!rootSegmentName.isEmpty())
            outputX3DBody.append(indentSpacing(3)).append("<HAnimSegment USE='").append(rootSegmentName).append("' containerField='segments'/>\n"); // TODO check bvhName
		// output segments list
        Iterator<String> segmentDefIterator = getHierarchySegmentList().iterator();
        while   (segmentDefIterator.hasNext())
        {
            String segmentDEF = segmentDefIterator.next();
            outputX3DBody.append(indentSpacing(3)).append("<HAnimSegment USE='").append(segmentDEF).append("' containerField='segments'/>\n"); // TODO check bvhName
        }
        // output sites list
        Iterator<String> siteDefIterator = getHierarchySiteList().iterator();
        while   (siteDefIterator.hasNext())
        {
            String segmentDEF = siteDefIterator.next();
            outputX3DBody.append(indentSpacing(3)).append("<HAnimSite USE='").append(segmentDEF).append("' containerField='sites'/>\n"); // TODO check bvhName
        }
        // no viewpoints list to output, they are top-level HAnimSite/Viewpoint instances (not USE nodes)
		
        outputX3DBody.append(indentSpacing(2));
        outputX3DBody.append("</HAnimHumanoid>").append("\n"); // end ROOT
		
		// comments showing conversion test results
		outputX3DBody.append(testAxisAngleRotation());
        
        // output each Motion which contain Frames data
        Iterator<Motion> motionsIterator = motions.iterator();
        while   (motionsIterator.hasNext())
        {
            Motion motion = motionsIterator.next();
            outputX3DBody.append(motion.getHierarchyOutputX3D(2));
        }
		
        outputX3DInfo.append(indentSpacing(3));
        outputX3DInfo.append("<MetadataSet name='BvhToHAnimConversionNameTable'>\n");
        outputX3DInfo.append(indentSpacing(4));
        outputX3DInfo.append("<!-- <MetadataString name='bvhName' reference='bvhType' value='\"name\" \"segmentName\"'/> -->\n");
		// initial pseudo HAnimJoint with containerField='skeleton' is not on BVH-based getHierarchyJointList()
		outputX3DInfo.append(indentSpacing(4));
		outputX3DInfo.append("<MetadataString containerField='value' name='").append(getHierarchyRootName()).append("' reference='ROOT' value='\"")
					 .append(getHAnimJointName()).append("\" \"").append(getHAnimSegmentName()).append("\"'/>\n");
		// expose each JOINT
		int jointIndex = 1;
        jointsIterator = getHierarchyJointList().iterator();
        while   (jointsIterator.hasNext())
        {
            Joint joint = jointsIterator.next();
			outputX3DInfo.append(indentSpacing(4));
			// value attribute has type MFString and must be "quoted" while other attributes are SFString
            outputX3DInfo.append("<MetadataString containerField='value' name='").append(joint.getBvhName()).append("' reference='JOINT' value='\"")
						 .append(joint.getHAnimJointName()).append("\" \"").append(joint.getSegmentName()).append("\"'/>\n");
			if (joint.isSiteIncluded())
			{
				outputX3DInfo.append(indentSpacing(4));
				// value is MFString and must be "quoted" while other attributes are SFString
				outputX3DInfo.append("<MetadataString containerField='value' name='").append(joint.getBvhName()).append("Site")
							 .append("' reference='Site' value='\"").append(joint.getHAnimJointName()).append("_tip").append("\"'/>\n");	
			}
			jointIndex++;
        }
        outputX3DInfo.append(indentSpacing(3));
        outputX3DInfo.append("</MetadataSet>\n");
        outputX3DInfo.append(indentSpacing(2));
        outputX3DInfo.append("</Group>\n");
        
        outputX3DBody.append("  </Scene>").append("\n");
        outputX3DBody.append("</X3D>").append("\n");
        
		StringBuilder outputX3D = new StringBuilder();
		outputX3D.append(outputX3DHead);
		outputX3D.append(outputX3DInfo);
		outputX3D.append(outputX3DBody);
		
        return outputX3D.toString();
    }

    /**
     * @return the expectedChannelsPerFrame
     */
    public int getExpectedChannelsPerFrame() {
        return expectedChannelsPerFrame;
    }

    /**
     * @param value the expectedChannelsPerFrame to set
     */
    public void setExpectedChannelsPerFrame(int value) {
            expectedChannelsPerFrame = value;
    }

    /**
     * Increment expectedChannelsPerFrame by provided value when additional CHANNELS are encountered
     * @param value the increment to add expectedChannelsPerFrame
     */
    public void augmentExpectedChannelsPerFrame(int value) {
        expectedChannelsPerFrame += value;
    }

    /**
     * Reduce expectedChannelsPerFrame when additional CHANNELS are removed.  Non-negative result.
     * @param value the decrement to reduce from expectedChannelsPerFrame
     */
    public void reduceExpectedChannelsPerFrame(int value) {
        expectedChannelsPerFrame -= value;
        
        if (expectedChannelsPerFrame < 0)
            expectedChannelsPerFrame = 0; // TODO unexpected correction, consider warning message
    }

    /**
     * @return the motions
     */
    public ArrayList<Motion> getMotions() {
        return motions;
    }

    /**
     * @param newMotions the motions to set
     */
    public void setMotions(ArrayList<Motion> newMotions) {
        motions = newMotions;
    }

    /**
     * @param newMotion the new Motion to append
     */
    public void addMotion(Motion newMotion) {
        motions.add(newMotion);
    }

    /**
     * @param newMotion the new Motion to insert
     */
    public void removeMotion(int index) {
        motions.remove(index);
    }

    /**
     * @param newMotion the new Motion to insert
     */
    public void insertMotion(int index, Motion newMotion) {
        motions.add(index, newMotion);
    }

    /**
     * clear all contained motions
     */
    public void clearMotions() {
        motions = new ArrayList<>();
    }

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param newVersion the version to set
	 */
	public void setVersion(String newVersion) {
		version = newVersion;
	}

	/**
	 * @return the DEF
	 */
	public String getDEF() {
		return DEF;
	}

	/**
	 * @param newDEF the DEF to set
	 */
	public void setDEF(String newDEF) {
		DEF = newDEF;
	}
	
	/**
	 * Provide hierarchyJointList of all joints defined in BVH file, in order encountered
	 * @return hierarchyJointList
	 */
	public ArrayList<Joint> getHierarchyJointList ()
	{
		return hierarchyJointList;
	}
	
	/**
	 * Add a joint to hierarchyJointList, following order encountered in BVH file
	 */
	public void incrementHierarchyJointList (Joint newJoint)
	{
		hierarchyJointList.add(newJoint);
	}
	
	/**
	 * Clear hierarchyJointList to reset
	 */
	public void clearHierarchyJointList ()
	{
		hierarchyJointList = new ArrayList<>();
	}
	
	/**
	 * Return hierarchy root Joint, which is first Joint in hierarchyJointList
	 * @return root joint
	 */
	public Joint getHierarchyRootJoint()
	{
		return hierarchyJointList.get(0);
	}
	
	/**
	 * Provide hierarchySegmentList of all segments encountered in BVH file, in order encountered
	 * @return hierarchySegmentList
	 */
	public ArrayList<String> getHierarchySegmentList ()
	{
		return hierarchySegmentList;
	}
	
	/**
	 * Add a segment to hierarchySegmentList, following order encountered in BVH file
	 */
	public void incrementHierarchySegmentList (String newSegment)
	{
		hierarchySegmentList.add(newSegment);
	}
	
	/**
	 * Clear hierarchySegmentList to reset
	 */
	public void clearHierarchySegmentList ()
	{
		hierarchySegmentList = new ArrayList<>();
	}
	
	/**
	 * Return hierarchy root Site DEF, which is first Site in hierarchySegmentList
	 * @return root site
	 */
	public String getHierarchyRootSegment()
	{
		return hierarchySegmentList.get(0);
	}
	
	/**
	 * Provide hierarchySiteList of all sites defined in BVH file, in order encountered
	 * @return hierarchySiteList
	 */
	public ArrayList<String> getHierarchySiteList ()
	{
		return hierarchySiteList;
	}
	
	/**
	 * Add a site to hierarchySiteList, following order encountered in BVH file
	 */
	public void incrementHierarchySiteList (String newSite)
	{
		hierarchySiteList.add(newSite);
	}
	
	/**
	 * Clear hierarchySiteList to reset
	 */
	public void clearHierarchySiteList ()
	{
		hierarchySiteList = new ArrayList<>();
	}
	
	/**
	 * Return hierarchy root Site DEF, which is first Site in hierarchySiteList
	 * @return root site
	 */
	public String getHierarchyRootSite()
	{
		return hierarchySiteList.get(0);
	}
	
	/**
	 * Provide hierarchySiteViewpointList of all sites defined in BVH file, in order encountered
	 * @return hierarchySiteViewpointList
	 */
	public ArrayList<String> getHierarchySiteViewpointList ()
	{
		return hierarchySiteViewpointList;
	}
	
	/**
	 * Add a site to hierarchySiteViewpointList, following order encountered in BVH file
	 */
	public void incrementHierarchySiteViewpointList (String newSiteViewpoint)
	{
		hierarchySiteViewpointList.add(newSiteViewpoint);
	}
	
	/**
	 * Clear hierarchySiteViewpointList to reset
	 */
	public void clearHierarchySiteViewpointList ()
	{
		hierarchySiteViewpointList = new ArrayList<>();
	}
	
	/**
	 * Return hierarchy root Viewpoint DEF, which is first Viewpoint in hierarchySiteViewpointList
	 * @return root site
	 */
	public String getHierarchyRootSiteViewpoint()
	{
		return hierarchySiteViewpointList.get(0);
	}

	/**
	 * @return the minX
	 */
	public double getMinX() {
		return minX;
	}

	/**
	 * @return the minY
	 */
	public double getMinY() {
		return minY;
	}

	/**
	 * @return the minZ
	 */
	public double getMinZ() {
		return minZ;
	}

	/**
	 * @return the maxX
	 */
	public double getMaxX() {
		return maxX;
	}

	/**
	 * @return the maxY
	 */
	public double getMaxY() {
		return maxY;
	}

	/**
	 * @return the maxZ
	 */
	public double getMaxZ() {
		return maxZ;
	}

	/**
	 * @param newMinX the minX to set
	 */
	public void setMinX(double newMinX) {
		this.minX = newMinX;
	}

	/**
	 * @param newMinY the minY to set
	 */
	public void setMinY(double newMinY) {
		minY = newMinY;
	}

	/**
	 * @param newMinZ the minZ to set
	 */
	public void setMinZ(double newMinZ) {
		minZ = newMinZ;
	}

	/**
	 * @param newMaxX the maxX to set
	 */
	public void setMaxX(double newMaxX) {
		maxX = newMaxX;
	}

	/**
	 * @param newMaxY the maxY to set
	 */
	public void setMaxY(double newMaxY) {
		maxY = newMaxY;
	}

	/**
	 * @param newMaxZ the maxZ to set
	 */
	public void setMaxZ(double newMaxZ) {
		maxZ = newMaxZ;
	}

	/**
	 * @return the unscaled humanoid width
	 */
	public double getWidth() {
		return maxX - minX;
	}

	/**
	 * @return the unscaled humanoid width
	 */
	public double getHeight() {
		return maxY - minY;
	}

	/**
	 * @return the unscaled humanoid depth
	 */
	public double getDepth() {
		return maxZ - minZ;
	}

	/**
	 * @return the scaleFactor
	 */
	public double getScaleFactor() {
		return scaleFactor;
	}

	/**
	 * @param scaleFactor the scaleFactor to set
	 */
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param newModelName the modelName to set
	 */
	public void setModelName(String newModelName) {
		modelName = newModelName;
	}
	/**
	 * Test getAxisAngleRotation (phi, theta, psi) with test values from RotationTests.x3d
	 * @see http://X3dGraphics.com/examples/X3dForAdvancedModeling/HumanoidAnimation/RotationTestsIndex.html
	 * @return String result suitable for output
	 */
	private String testAxisAngleRotation()
	{
		StringBuilder result = new StringBuilder();
		result.append("    <!-- ============================================================= -->\n");
		result.append("    <!-- testAxisAngleRotation() results compared to RotationTests.x3d -->\n");
		
		double   phi = -4.400301; // X-axis rotation
		double theta = -0.381611; // Y-axis rotation
		double   psi = -1.829527; // Z-axis rotation
		result.append("    <!-- getAxisAngleRotation(")
			  .append(fiveDigitFormat.format(phi)).append(",")
			  .append(fiveDigitFormat.format(theta)).append(",")
			  .append(fiveDigitFormat.format(psi)).append(") = ")
			  .append(getAxisAngleRotation(phi,theta,psi))
			  .append(", expected rotation: 0.40671 -0.71645 -0.56683 2.6753").append(" -->\n");

		  phi =  5.801149; // X-axis rotation
		theta =  2.55377; // Y-axis rotation
		  psi =  2.832229; // Z-axis rotation
		result.append("    <!-- getAxisAngleRotation(")
			  .append(fiveDigitFormat.format(phi)).append(",")
			  .append(fiveDigitFormat.format(theta)).append(",")
			  .append(fiveDigitFormat.format(psi)).append(") = ")
			  .append(getAxisAngleRotation(phi,theta,psi))
			  .append(", expected rotation: -0.96458 0.07774 0.25206 2.59674").append(" -->\n");
	
		  phi = -3.766201; // X-axis rotation
		theta = -3.474078; // Y-axis rotation
		  psi = -3.939975; // Z-axis rotation
		result.append("    <!-- getAxisAngleRotation(")
			  .append(fiveDigitFormat.format(phi)).append(",")
			  .append(fiveDigitFormat.format(theta)).append(",")
			  .append(fiveDigitFormat.format(psi)).append(") = ")
			  .append(getAxisAngleRotation(phi,theta,psi))
			  .append(", expected rotation: 0.40758 -0.49149 -0.76962 1.12862").append(" -->\n");
		result.append("    <!-- ============================================================= -->\n");
		
		return result.toString();
	}

	/**
	 * @return the hierarchyRootName
	 */
	public String getHierarchyRootName() {
		return hierarchyRootName;
	}

	/**
	 * @param hierarchyRootName the hierarchyRootName to set
	 */
	public void setHierarchyRootName(String hierarchyRootName) {
		this.hierarchyRootName = hierarchyRootName;
	}

	/**
	 * @return the heightOffset
	 */
	public double getHeightOffset() {
		return heightOffset;
	}

	/**
	 * @param heightOffset the heightOffset to set
	 */
	public void setHeightOffset(double heightOffset) {
		this.heightOffset = heightOffset;
	}
}
