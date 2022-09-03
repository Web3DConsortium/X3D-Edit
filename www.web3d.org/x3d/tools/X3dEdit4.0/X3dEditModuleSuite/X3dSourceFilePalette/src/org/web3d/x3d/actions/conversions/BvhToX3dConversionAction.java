/*
* Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
*  
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*  
*  * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*  * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer
*       in the documentation and/or other materials provided with the
*       distribution.
*  * Neither the names of the Naval Postgraduate School (NPS)
*       Modeling Virtual Environments and Simulation (MOVES) Institute
*       (http://www.nps.edu and https://MovesInstitute.nps.edu)
*       nor the names of its contributors may be used to endorse or
*       promote products derived from this software without specific
*       prior written permission.
*  
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
* FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
* COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/

package org.web3d.x3d.actions.conversions;

import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.vecmath.Vector3d;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.web3d.x3d.InputOutputReporter;
import org.web3d.x3d.hanim.bvh.BvhSkeletonParameters;
import org.web3d.x3d.hanim.bvh.Hierarchy;
import org.web3d.x3d.hanim.bvh.Joint;
import org.web3d.x3d.hanim.bvh.Motion;

@ActionID(id = "org.web3d.x3d.actions.conversions.BvhToX3dConversionAction", category = "File")

@ActionRegistration(   iconBase = "org/web3d/x3d/palette/items/resources/BvhMocap.png",
                    displayName = "#CTL_BvhToX3dAction",
                           lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Import Model from File", position = 50),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Import Model from File", position = 50)
})

// TODO refactor to place BVH parser in a separate class in org.web3d.x3d.hanim.bvh package

public final class BvhToX3dConversionAction extends CallableSystemAction
{
//  private static JFileChooser fChooser;
    
    BvhToX3dConversionPanel bvhToX3dConversionPanel = new BvhToX3dConversionPanel(this);
    private boolean bvhLocalFile  = true; // set by BvhToX3dConversionPanel
    private String  bvhLocalPath  = "";   // set by BvhToX3dConversionPanel
    private String  bvhUrlAddress = "";   // set by BvhToX3dConversionPanel

    private String  conversionStage = ""; // diagnostic label for each stage of conversion
    private String  bvhName;
    private String  newBvhFileName;
    private String  newX3dFileName;
    private File    bvhFile;
    private File    x3dFile;
    private boolean userSaysCancel = false;
	private boolean includeVisualizationShapes = true;

    private Hierarchy             hierarchy   = new Hierarchy();
    private BvhSkeletonParameters currentNode = hierarchy;
    private Motion                motion      = new Motion();

  @Override
  public void performAction()
  {
    try
	{
		hierarchy.setX3dBallAndStickVisualizationIncluded(isIncludeVisualizationShapes());
        conversionStage = "file bvhToX3dConversionPanel chooser";
        if  (bvhLocalFile)
        {
            // prior value of bvhLocalPath or bvhUrlAddress may get updated:
            bvhToX3dConversionPanel.showEditDialog(bvhLocalPath); 
            
            if      (bvhLocalPath.startsWith("file:///"))
                     bvhLocalPath = bvhLocalPath.substring(8);
            else if (bvhLocalPath.startsWith("file://"))
                     bvhLocalPath = bvhLocalPath.substring(7);
        }
        else 
        {
            bvhToX3dConversionPanel.showEditDialog(bvhUrlAddress); // prior value and bvhLocalFile may get updated
        }

        conversionStage = "file name manipulation";
        if  (bvhLocalFile)
        {
            bvhName = bvhLocalPath;
            if (bvhName.contains("\\"))
                bvhName = bvhName.substring(bvhName.lastIndexOf("\\")+1);
            if (bvhName.contains("/"))
                bvhName = bvhName.substring(bvhName.lastIndexOf("/")+1);
            if (bvhName.contains("\\"))
                bvhName = bvhName.substring(bvhName.lastIndexOf("\\")+1); // just in case mixed separators are present
            if (bvhName.contains("."))
                bvhName = bvhName.substring(0,bvhName.lastIndexOf('.'));
        }
        else 
        {
            bvhName = bvhUrlAddress;
            if (bvhName.contains(File.separator))
                bvhName = bvhName.substring(bvhName.lastIndexOf("/")+1);
            if (bvhName.contains("."))
                bvhName = bvhName.substring(0,bvhName.lastIndexOf('.'));
        }
        newBvhFileName = bvhName + ".bvh.txt";
        newX3dFileName = bvhName + ".x3d";

        conversionStage = "file retrieval";
        if  (bvhLocalFile)
        {
            bvhFile = new File(bvhLocalPath);
        }
        else 
        {
            // TODO retrieve http address
        }
        String        line;
        StringBuilder bvhSource = new StringBuilder();
        
        conversionStage = "bvh file reading"; // diagnostic label for each stage of conversion
        
        try (FileReader fileReader = new FileReader(bvhFile))
        {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null)
            {
                bvhSource.append(line).append('\n');
            }
        }
        catch(Throwable t) {
          if(!t.getClass().getName().endsWith("ExitSecurityException")) {
            System.err.print("Exception from translator: "+t.getLocalizedMessage());
            return;
          }
        }
        
        bvhToX3dConversionPanel.setBvhSource(bvhSource.toString());
        File temporaryOutputFile;
        conversionStage = "creating temporary .x3d file"; // diagnostic label for each stage of conversion
    
        temporaryOutputFile = File.createTempFile("X3D-EditTemporaryOutputFile", ".x3d");
        temporaryOutputFile.deleteOnExit();

        conversionStage = "processing BVH file "; // diagnostic label for each stage of conversion

        // (re)initialize
		hierarchy   = new Hierarchy();
		hierarchy.setBvhName(bvhName);
		currentNode = hierarchy;
        String   value, currentName;
        String[] lines = bvhSource.toString().split("\\n");
        boolean     hierarchyFound = false;
        boolean       motionParsed = false;
        boolean insufficientValues;
		userSaysCancel = false;
                  
        for (int i = 0; i < lines.length; i++)
        {
            if (userSaysCancel)
                return;
            line = lines[i].trim();
            if (!line.isEmpty())
            {
                if (motionParsed && isNumeric(line.split("[,\\s]+")[0]))
                {
                    // skip excess numeric lines after MOTION parsing is complete, see if anything else is there
//                  continue;
                }
                else if   ((!hierarchyFound) && lines[i].startsWith("HIERARCHY"))
                {
                    hierarchyFound = true;
                      motionParsed = false;
                    if  (line.length() > ("HIERARCHY").length())
                    {
                        bvhParseProblem (i, line, "Illegal content \"" + line.substring(("HIERARCHY").length()).trim() + "\" found at end of line");
                    }
// create root Joint so that HAnimHumanoid can be positioned, oriented independently of BVH motion animation
//                    Joint newJoint = new Joint();
//                    newJoint.setParent(currentNode);
//                    currentNode.addJoint(newJoint);
//                    currentNode = newJoint;
//					hierarchy.incrementHierarchyJointList(newJoint);
                }
                else if (!hierarchyFound)
                {
                        bvhParseProblem (i, line, "Illegal file content: HIERARCHY not found at start of file, BVH parsing terminated");
                        return; // nothing left to do
                }
                else if (line.startsWith("ROOT"))
                {
                    value = line.substring(4).trim(); // read remainder of line
                    if (value.contains("{"))
                    {
                        value = value.substring(0,value.indexOf("{")).trim();
                        hierarchy.setHierarchyRootName(value);
                        currentName     = value;
                    }
                    else
                    {
                        hierarchy.setHierarchyRootName(value);
                        currentName     = value;
                        // now check for follow-on { character
                        if (lines[i+1] == null)
                        {
                            bvhParseProblem (i, line, "Illegal content: premature eof after ROOT, BVH parsing terminated");
                            return; // nothing left to do
                        }
                        else if (lines[i+1].trim().startsWith("{"))
                        {
                            i++; // skip line
                        }
                        else 
                        {
                            bvhParseProblem (i, line, "Illegal content: no { bracket character provided after ROOT, BVH parsing terminated");
                            return; // nothing left to do
                        }
                    }
                    if (hierarchy.getBvhName().isEmpty())
                    {
                        bvhParseProblem (i, line, "Illegal content: no name provided for ROOT");
                    }
                }
                else if (line.startsWith("JOINT"))
                {
                    Joint newJoint = new Joint();
                    newJoint.setParent(currentNode);
                    newJoint.setAncestorHierarchy(hierarchy);
                    currentNode.addJoint(newJoint);
                    currentNode = newJoint;
					hierarchy.incrementHierarchyJointList(newJoint);
                    
                    value = line.substring(5).trim(); // read remainder of line
                    if (value.contains("{"))
                    {
                        value = value.substring(0,value.indexOf("{")).trim();
                        newJoint.setBvhName(value);
                        newJoint.assignHAnimNamesFromBvhModelName(value);
                        currentName     = value;
                    }
                    else
                    {
                        newJoint.setBvhName(value);
                        newJoint.assignHAnimNamesFromBvhModelName(value);
                        currentName     = value;
						// DEF names are set during serialization by Joint.getHierarchyOutputX3D()
						
                        // now check for follow-on { character
                        if (lines[i+1] == null)
                        {
                            bvhParseProblem (i, line, "Illegal content: premature eof after JOINT, BVH parsing terminated");
                            return; // nothing left to do
                        }
                        else if (lines[i+1].trim().startsWith("{"))
                        {
                            i++; // skip line
                        }
                        else 
                        {
                            bvhParseProblem (i, line, "Illegal content: no { bracket character provided after JOINT, BVH parsing terminated");
                            return; // nothing left to do
                        }
                    }
                    if (newJoint.getBvhName().isEmpty())
                    {
                        bvhParseProblem (i, line, "Illegal content: no name provided for JOINT");
                    }
                }
                else if (line.startsWith("OFFSET")) // similar block below
                {
                    String   offset  = line.substring(6).trim(); // read remainder of line
                    String[] offsets = offset.split("[,\\s]+");
                    if (offset.contains(","))
                    {
                        bvhParseProblem (i, line, "Illegal content: no commas allowed in OFFSET value, ignored");
                    }
					else if (offset.isEmpty())
                    {
                        bvhParseProblem (i, line, "Illegal content: no OFFSET data values found, ignored");
                        currentNode.setOffset (new Vector3d (0.0, 0.0, 0.0)); // ensure initialized
                    }
                    else if (offsets.length != 3)
                    {
                        bvhParseProblem (i, line, "Illegal content: incorrect number of OFFSET values found, expected 3");
                        currentNode.setOffset (new Vector3d (0.0, 0.0, 0.0));
                    }
                    else // OFFSET is OK
                    {
                        currentNode.setOffsetX (Double.parseDouble(offsets[0]));
                        currentNode.setOffsetY (Double.parseDouble(offsets[1]));
                        currentNode.setOffsetZ (Double.parseDouble(offsets[2]));
						
						if  (currentNode instanceof Joint)
							 currentNode.setCumulativeOffset(new Vector3d(
								currentNode.getOffset().x + ((Joint)currentNode).getParent().getCumulativeOffset().x,
								currentNode.getOffset().y + ((Joint)currentNode).getParent().getCumulativeOffset().y,
								currentNode.getOffset().z + ((Joint)currentNode).getParent().getCumulativeOffset().z
							 ));
						else currentNode.setCumulativeOffset(currentNode.getOffset());
                    }
                }
                else if (line.startsWith("CHANNELS"))
                {
                    String   channel  = line.substring(8).trim(); // read remainder of line
                    String[] channels = channel.split("[,\\s]+");
                    if (channel.contains(","))
                    {
                        bvhParseProblem (i, line, "Illegal content: no commas allowed in CHANNELS value, ignored");
                    }
					else if (channel.isEmpty())
                    {
                        bvhParseProblem (i, line, "Illegal content: no CHANNELS data values found, ignored");
                        // TODO probably not safe to assume and set a default, typically 6-tuple or 3-tuple
                    }
					else // CHANNELS is OK
                    {
                        int channelCount = Integer.parseInt(channels[0]);
                        hierarchy.augmentExpectedChannelsPerFrame(channelCount);
                        if (channels.length != channelCount + 1)
                        {
                            bvhParseProblem (i, line, "Illegal content: incorrect length for channels");
                            // handle this problem as best possible by passing along whatever channel data is present anyway
                        }
                        if (channel.contains(" "))
                        {
                            currentNode.setChannelNames(channel.substring(channel.indexOf(" ") + 1).split("[,\\s]+")); // skip channel count
                        }
                    }
                }
                else if (line.startsWith("{")) // begin definition for child Joint
                {
//                  continue; // skip line
                }
                else if (line.startsWith("}")) // completed definition for this child Joint
                {
                    if (currentNode instanceof Joint) // don't go above hierarchy node, which is root
                    {
                        currentNode = ((Joint)currentNode).getParent();
                    }
                }
                else if (line.startsWith("End Site")) // no further child Joint elements, OFFSET expected next
                {
                    if (lines[i+1].trim().startsWith("{"))
                    {
                        i++; // skip line
                        line = lines[i+1].trim();
                    }
                    if (line.startsWith("OFFSET")) // similar block above
                    {
                        String   offset  = line.substring(6).trim(); // read remainder of line
                        String[] offsets = offset.split("[,\\s]+");
                        if (offset.contains(","))
                        {
                            bvhParseProblem (i, line, "Illegal content: no commas allowed in OFFSET value, ignored");
                        }
						else if (offset.isEmpty())
                        {
                            bvhParseProblem (i, line, "Illegal content: no OFFSET data values found, ignored");
                            ((Joint)currentNode).setSiteOffset (new Vector3d (0.0, 0.0, 0.0));
                        }
                        else if (offsets.length != 3)
                        {
                            bvhParseProblem (i, line, "Illegal content: incorrect number of OFFSET values found, expected 3");
                            ((Joint)currentNode).setSiteOffset (new Vector3d (0.0, 0.0, 0.0));
                        }
                        else // OFFSET is OK
                        {
                            ((Joint)currentNode).setSiteIncluded(true);
							double x = Double.parseDouble(offsets[0]);
							double y = Double.parseDouble(offsets[1]);
							double z = Double.parseDouble(offsets[2]);
                            ((Joint)currentNode).setSiteOffsetX (x);
                            ((Joint)currentNode).setSiteOffsetY (y);
                            ((Joint)currentNode).setSiteOffsetZ (z);
							if (x < hierarchy.getMinX()) hierarchy.setMinX(x);
							if (y < hierarchy.getMinY()) hierarchy.setMinY(y);
							if (z < hierarchy.getMinZ()) hierarchy.setMinZ(z);
							if (x > hierarchy.getMaxX()) hierarchy.setMaxZ(x);
							if (y > hierarchy.getMaxY()) hierarchy.setMaxY(y);
							if (z > hierarchy.getMaxZ()) hierarchy.setMaxZ(z);
                        }
                        // now skip trailing } so that it does not pop us up the node hierarchy
                        if (lines[i+2].trim().startsWith("}"))
                        {
                            i++; // skip line
                            i++; // skip line
//                            line = lines[i+1].trim();
                        }
                        else
                        {
                            bvhParseProblem (i, line, "Illegal content: no close } found after End Site OFFSET, ignored");
                            return; // nothing left to do
                        }
                    }
                }
                else if (line.startsWith("MOTION")) // begin definition for child Joint
                {
                    motion = new Motion();
					motion.setAncestorHierarchy(hierarchy);
                    if (lines[i+1] == null)
                    {
                        bvhParseProblem (i, line, "Illegal content: premature eof after MOTION, BVH parsing terminated");
                        return; // nothing left to do
                    }
                    if (lines[i+1].trim().startsWith("Frames:"))
                    {
                        line = lines[i+1].trim();
                        i++; // skip line
                        
                        int frameCount = Integer.parseInt(line.substring(7).trim());
                        motion.setExpectedFrameCount(frameCount);
                    }
                    if (lines[i+1] == null)
                    {
                        bvhParseProblem (i, line, "Illegal content: premature eof after Frames:, BVH parsing terminated");
                        return; // nothing left to do
                    }
                    if (lines[i+1].trim().startsWith("Frame Time:"))
                    {
                        line = lines[i+1].trim();
                        i++; // skip line
                        
                        double frameTime = Double.parseDouble(line.substring(11).trim());
                        motion.setFrameDuration(frameTime);
                    }
                    if (lines[i+1] == null)
                    {
                        bvhParseProblem (i, line, "Illegal content: premature eof after MOTION: Frames: Frame Time:, BVH parsing terminated");
                        return; // nothing left to do
                    }
                    line = lines[i+1].trim();
                    i++; // skip line, now at start of values array
                    
                    String lastChannel = "";
                    
                    // process all frames of motion data (i.e. all remaining lines)
                    for (int indexFrame = 0; indexFrame < motion.getExpectedFrameCount(); indexFrame++)
                    {
                        insufficientValues = false;
                        int     expectedValueCount = hierarchy.getExpectedChannelsPerFrame();
                        double[] frameValueArray    = new double[expectedValueCount];
                        
                        while (line.isEmpty()) // skip any expected blank lines
                        {
                            line = lines[i+1].trim();
                            i++; // skip line
                            if (i == lines.length) // reached end of file
                                break;
                        }
                        if (line.contains(","))
                        {
                            bvhParseProblem (i, line, "Illegal content: no commas allowed in MOTION Frames array of values, ignored");
                        }
                        
                        // each frame is on a separate line in the BVH file
                        String[]frameStrings = line.split("[,\\s]+");
                        if (frameStrings.length != expectedValueCount)
                        {
                            String valueMismatch;
                            int differenceArrayLengths = frameStrings.length - expectedValueCount;
                            if  (differenceArrayLengths < 0)
                                 valueMismatch = String.valueOf(differenceArrayLengths) + " missing values";
                            else valueMismatch = String.valueOf(differenceArrayLengths) +  " excess values";

                            bvhParseProblem (i, line, "<html><p>Motion data problem: expected (<b>channelsPerFrame=" + hierarchy.getExpectedChannelsPerFrame() + 
                                                      "</b>) data values,</p>" +
                                                      "<p>but actually found <b>" + String.valueOf(frameStrings.length) + " values</b>," + 
                                                      "with a mismatch of <b>" + valueMismatch + "</b> in the frame</b>." + 
                                                      "</p><p>Continuing to parse...");
                        }
                        // ensure all extra data is saved, even if not expected
                        int arraySize = Math.max (hierarchy.getExpectedChannelsPerFrame(), frameStrings.length);
                        for (int indexChannel = 0; indexChannel < arraySize; indexChannel++)
                        {
                            if  (frameStrings[indexChannel] != null)
                                 frameValueArray[indexChannel] = Double.parseDouble(frameStrings[indexChannel]);
                            else
                            {
                                frameValueArray[indexChannel] = 0.0f; // buffer underflow
                                if (insufficientValues == false)
                                {
                                    lastChannel = String.valueOf(indexChannel);
                                }
                                insufficientValues = true;
                            }
                        }
                        if (insufficientValues)
                        {
                            bvhParseProblem (i, line, "Illegal content: insufficient data values found at frame " + indexFrame + ", channel " + lastChannel + ", set remainder to 0");
                        }
                        motion.addFrame(frameValueArray);
                        motionParsed = true;
                        if (i < lines.length - 1)
                        {
                            line = lines[i+1].trim();
                            i++; // skip line
                        }
                    }
                    hierarchy.addMotion(motion);
                } // continue with next line
            } // BVH file parsing complete
        }

        conversionStage = "copying file "; // diagnostic label for each stage of conversion

        BufferedInputStream  bis = new BufferedInputStream (new FileInputStream(bvhFile));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(temporaryOutputFile));
        int b;
        while((b = bis.read()) != -1) {
          bos.write(b);
        }
        bis.close();
        bos.close();

        FileObject fo   = FileUtil.toFileObject(bvhFile);
        DataObject dobj = DataObject.find(fo);
        OpenCookie op   = dobj.getLookup().lookup(OpenCookie.class);
        op.open(); // source .bvh
        
        File newBvhFile = new File (bvhFile.getParent(), newBvhFileName);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(newBvhFile));
            writer.write(hierarchy.getHierarchyOutputBVH()); // serialize BVH output
        } 
        catch (IOException e) {
        } 
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
        FileObject fo2  = FileUtil.toFileObject(newBvhFile);
        DataObject dobj2= DataObject.find(fo2);
        OpenCookie op2  = dobj2.getLookup().lookup(OpenCookie.class);
        op2.open(); // round-trip .bvh
        
        // newX3dFileName
        
        File newX3dFile = new File (bvhFile.getParent(), newX3dFileName);
        try {
            writer = new BufferedWriter(new FileWriter(newX3dFile));
            writer.write(hierarchy.getHierarchyOutputX3D(newX3dFileName)); // serialize X3D output
        } 
        catch (IOException e) {
        } 
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
            }
        }
        FileObject fo3  = FileUtil.toFileObject(newX3dFile);
        DataObject dobj3= DataObject.find(fo3);
        OpenCookie op3  = dobj3.getLookup().lookup(OpenCookie.class);
        op3.open(); // converted .x3d
        
//        System.out.println(hierarchy.getHierarchyOutputBVH());
//        PrintWriter newBvhFile = new PrintWriter(newBvhFileName);
//        System.out.println ("Output BVH file: " + newBvhFile.newBvhFileName);
    }
    catch(IOException | HeadlessException ex) 
    {
        NotifyDescriptor d = new NotifyDescriptor.Message(
                "<html><p> Error during conversion: " + conversionStage + "</p>" + 
                "<p>"+ex.getLocalizedMessage()+"</p>",
                NotifyDescriptor.ERROR_MESSAGE);
        DialogDisplayer.getDefault().notify(d);
    }
    
    //String fsep = System.getProperty("file.separator");
    //String pathToJava = System.getProperty("java.home") + fsep + "bin" + fsep + "java";
    //String classToRun = "xj3d.filter.CDFFilter";
    //String cp = System.getProperty("java.class.path");
//    ProcessBuilder pb = new ProcessBuilder(pathToJava, "-classpath",cp,
//        classToRun,
//        filter,
//        fChooser.getSelectedFile().getAbsolutePath(),
//        tmpOutFile.getAbsolutePath());
  }
  /** 
   * Report a parse problem to console and user 
   * @param lineNumber of BVH file
   * @param line being read
   * @param message to user
   */
  private void bvhParseProblem (int lineNumber, String line, String message)
  {
        System.err.println("BVH parse problem: " + message);
        System.err.println("   [line " + lineNumber + "] " + line);

//        NotifyDescriptor d = new NotifyDescriptor.Message(
//                "<html><h3>BVH parse problem:</h3>" +
//                      "<p>" + message + "</p>" +
//                      "<p>    [line " + lineNumber + "] " + line + "</p>",
//                NotifyDescriptor.OK_CANCEL_OPTION);
//        d.setTitle("BVH parse problem");
//        DialogDisplayer.getDefault().notify(d);
        
        int returnValue = JOptionPane.showConfirmDialog(
                bvhToX3dConversionPanel, 
                "<html><h3>BVH parse problem:</h3>" +
                "<p>" + message + "</p>" +
                "<p>    [line " + lineNumber + "] " + line + "</p>",
                "BVH parse problem",
                JOptionPane.OK_CANCEL_OPTION);

        if (returnValue != JOptionPane.OK_OPTION)
            userSaysCancel = true;
  }
  protected void showOut(String msg)
  {
    InputOutputReporter console = new InputOutputReporter("BVH translation");
    java.util.ResourceBundle bun = NbBundle.getBundle(getClass());

    console.message(msg);
    console.moveToFront(true);    
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(BvhToX3dConversionAction.class, "CTL_BvhToX3dAction");
  }

  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }
 
  /**
   * Test if string is numeric
   * @see http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
   */
  public static boolean isNumeric(String value)  
{  
  try  
  {  
    double d = Double.parseDouble(value);  
  }  
  catch(NumberFormatException nfe)  
  {  
    return false;  
  }  
  return true;  
}

    /**
     * @return the bvhLocalFile
     */
    public boolean isBvhLocalFile() {
        return bvhLocalFile;
    }

    /**
     * @param localFile the bvhLocalFile value to set
     */
    public void setBvhLocalFile(boolean localFile) {
       bvhLocalFile = localFile;
    }

    /**
     * @return the bvhLocalPath
     */
    public String getBvhLocalPath() {
        return bvhLocalPath;
    }

    /**
     * @param localPath the bvhLocalPath value to set
     */
    public void setBvhLocalPath(String localPath) {
        bvhLocalPath = localPath;
    }

    /**
     * @return the bvhUrlAddress
     */
    public String getBvhUrlAddress() {
        return bvhUrlAddress;
    }

    /**
     * @param urlAddress the bvhUrlAddress value to set
     */
    public void setBvhUrlAddress(String urlAddress) {
        this.bvhUrlAddress = urlAddress;
    }

	/**
	 * @return whether to includeVisualizationShapes
	 */
	public boolean isIncludeVisualizationShapes() {
		return includeVisualizationShapes;
	}

	/**
	 * @param newIncludeVisualizationShapes the includeVisualizationShapes to set
	 */
	public void setIncludeVisualizationShapes(boolean newIncludeVisualizationShapes) {
		this.includeVisualizationShapes = newIncludeVisualizationShapes;
	}
  
}
