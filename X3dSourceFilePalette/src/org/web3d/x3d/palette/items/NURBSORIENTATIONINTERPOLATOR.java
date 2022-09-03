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

import org.web3d.x3d.types.X3DChildNode;
import java.util.Iterator;
import javax.swing.text.JTextComponent;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * NURBSORIENTATIONINTERPOLATOR.java
 * Created on 18 December 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class NURBSORIENTATIONINTERPOLATOR extends X3DChildNode 
{
  private SFInt32              order,        orderDefault;
  
  private SFDouble[]           knot,         knotDefault; // MFDouble
  private SFDouble[]         weight,       weightDefault; // MFDouble
  
  private SFDouble[][] controlPoint, controlPointDefault; // MFVec3f/MFVec3d --- TODO from Coordinate node
  
  private String  coordinateNodeDEF = new String();
  private String  coordinateNodeUSE = new String();
  
  private boolean coordinateNodeFound, coordinateDoubleNodeFound = false;
  private boolean coordinateValueArrayEditable = true;
  private boolean hasChangedValues = false;
  
  private boolean     insertCommas, insertLineBreaks = false;

  public NURBSORIENTATIONINTERPOLATOR()
  {
  }

  @Override
  public String getElementName()
  {
    return NURBSORIENTATIONINTERPOLATOR_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return NURBSORIENTATIONINTERPOLATORCustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
           order =        orderDefault = new SFInt32(NURBSORIENTATIONINTERPOLATOR_ATTR_ORDER_DFLT,          2, null, false); // don't skipTest
    
    String[] sa;
    if(NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_DFLT == null || NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_DFLT);
    knot = knotDefault = parseToSFDoubleArray(sa);
    
    if(NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_DFLT == null || NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_DFLT);
    weight = weightDefault = parseToSFDoubleArray(sa);
    
    controlPoint = controlPointDefault = parseToSFDoubleTable(sa,3); // MFVec3f/MFVec3d --- TODO from Coordinate node
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(NURBSORIENTATIONINTERPOLATOR_ATTR_ORDER_NAME);
    if(attr != null)
      order = new SFInt32(attr.getValue());

    String[] sa;
    attr = root.getAttribute(NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_NAME);
    if(attr != null) {
      sa = parseX(attr.getValue());
      knot = parseToSFDoubleArray(sa);
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    attr = root.getAttribute(NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_NAME);
    if(attr != null) {
      sa = parseX(attr.getValue());
      weight = parseToSFDoubleArray(sa);
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    // now read contained Coordinate or CoordinateDouble node for controlPoint array, include attributes and comments
    
    org.jdom.Element coordinateNode = root.getChild("Coordinate");
    if(coordinateNode != null)
    {
        coordinateNodeFound = true;
        attr = coordinateNode.getAttribute("DEF");
        if(attr != null) {
            coordinateNodeDEF = attr.getValue();
            setCoordinateValueArrayEditable(true);
        }
        attr = coordinateNode.getAttribute("USE");
        if(attr != null) {
            coordinateNodeUSE = attr.getValue();
            setCoordinateValueArrayEditable(false);
        }
        attr = coordinateNode.getAttribute("point"); // only works with DEF
        if(attr != null) {
            controlPoint = parseToSFDoubleTable(parseX(attr.getValue()),3); // MFVec3f/MFVec3d
        }
    }
    org.jdom.Element coordinateDoubleNode = root.getChild("CoordinateDouble");
    if(coordinateDoubleNode != null)
    {
        coordinateDoubleNodeFound = true;
        attr = coordinateDoubleNode.getAttribute("DEF");
        if(attr != null) {
            coordinateNodeDEF = attr.getValue();
            setCoordinateValueArrayEditable(true);
        }
        attr = coordinateDoubleNode.getAttribute("USE");
        if(attr != null) {
            coordinateNodeUSE = attr.getValue();
            setCoordinateValueArrayEditable(false);
        }
        attr = coordinateDoubleNode.getAttribute("point"); // only works with DEF
        if(attr != null) {
            controlPoint = parseToSFDoubleTable(parseX(attr.getValue()),3); // MFVec3f/MFVec3d
        }
    }
  }
  
  private org.jdom.Document documentRoot;
  protected void setDocumentRootRecheckUSEpointValues (org.jdom.Document targetDocumentRoot)
  {
    org.jdom.Element coordinateDoubleNode = new org.jdom.Element("Coordinate");
    this.documentRoot = targetDocumentRoot;
    
    // recompute controlPoint array based on USE node
    if (getCoordinateNodeUSE().isEmpty()) return;
    
    org.jdom.filter.ElementFilter elementFilter = new org.jdom.filter.ElementFilter("Coordinate");
    
    if      (coordinateNodeFound)
        elementFilter = new org.jdom.filter.ElementFilter("Coordinate");
    else if (coordinateDoubleNodeFound)
        elementFilter = new org.jdom.filter.ElementFilter("CoordinateDouble");
    
    if (documentRoot != null) 
    {
        org.jdom.Element documentRootNode = documentRoot.getRootElement();
        Iterator nodeList = documentRootNode.getDescendants(elementFilter);
        while (nodeList.hasNext())
        {
            org.jdom.Element thisNode = (org.jdom.Element)nodeList.next();
            org.jdom.Attribute attr = thisNode.getAttribute("DEF");
            if ((attr != null) && attr.getValue().equals(getCoordinateNodeUSE()))
            {
                coordinateDoubleNode = thisNode;
                break;
            }
        }
        org.jdom.Attribute attr = coordinateDoubleNode.getAttribute("point");
        if(attr != null) {
            controlPoint = parseToSFDoubleTable(parseX(attr.getValue()),3); // MFVec3f/MFVec3d
        }
    }
  }

  @Override
  public String createAttributes()
  {
    // first handle contained content prior to attributes
    String improperOrderWarning = "";
    if      ((order.getValue() < 2) && !getContent().contains(NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING))
    {
        improperOrderWarning =    "\n\t\t<!--" + NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING + "-->\n";
    }
    else if ((order.getValue() > 6) && !getContent().contains(NURBSCURVECustomizer.ORDER_TOO_LARGE_WARNING))
    {
        improperOrderWarning    = "\n\t\t<!--" + NURBSCURVECustomizer.ORDER_TOO_LARGE_WARNING + "-->\n";
    }
    setContent(getContent().replace("<!--" + NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING + "-->", "")); // clear old warning, if any
    setContent(getContent().replace("<!--" + NURBSCURVECustomizer.ORDER_TOO_LARGE_WARNING + "-->", "")); // clear old warning, if any
    setContent(improperOrderWarning + getContent());
    
    StringBuilder coordinateNodeBuilder = new StringBuilder();
    if ((controlPoint.length > 0) && coordinateValueArrayEditable)
    {
        if (coordinateNodeFound)
        {
            coordinateNodeBuilder.append("\n\t\t<Coordinate ");
        }    
        else // CoordinateDouble is default
        {
            coordinateNodeBuilder.append("\n\t\t<CoordinateDouble ");
        }
        if (getCoordinateNodeDEF().length() > 0)
            coordinateNodeBuilder.append("DEF='").append(getCoordinateNodeDEF()).append("' ");
        coordinateNodeBuilder.append("point='");
        coordinateNodeBuilder.append(formatDoubleArray(controlPoint, insertCommas, insertLineBreaks));
        coordinateNodeBuilder.append("'/>\n");
        
        // get rid of any prior <Coordinate> definition to avoid duplication
        int start = getContent().indexOf("<Coordinate");
        if  (start > 0)
        {
            int end = getContent().substring(start).indexOf(">");
            String priorCoordinateString = getContent().substring(start,start+end+1);
            setContent(getContent().replace(priorCoordinateString,""));
        }
    }
    else if (!coordinateValueArrayEditable && (getCoordinateNodeUSE().length() > 0))
    {
        // No change needed since prior Coordinate element and USE name is already in getContent()
//        if (coordinateNodeFound)
//        {
//            coordinateBuilder.append("\n\t\t<Coordinate ");
//        }    
//        else // CoordinateDouble is default
//        {
//            coordinateBuilder.append("\n\t\t<CoordinateDouble ");
//        }
//        coordinateBuilder.append(" USE='").append(coordinateNodeUSE).append("'/>\n");
    }
    setContent(getContent() + coordinateNodeBuilder.toString());
    setContent(getContent().replaceAll("\n(\\s*)\n", "\n")); // reduce whitespace line breaks
    
    // prior Coordinate/CoordinateDouble contained comments, metadata (if any) are preserved
    
    StringBuilder sb = new StringBuilder();

    if (NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_REQD || !arraysIdenticalOrNull(knot,knotDefault)) {
      sb.append(" ");
      sb.append(NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_NAME);
      sb.append("='");
      sb.append(formatDoubleArray(knot, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if (NURBSORIENTATIONINTERPOLATOR_ATTR_ORDER_REQD || !order.equals(orderDefault)) {
      sb.append(" ");
      sb.append(NURBSORIENTATIONINTERPOLATOR_ATTR_ORDER_NAME);
      sb.append("='");
      sb.append(order);
      sb.append("'");
    }
    if (NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_REQD || !arraysIdenticalOrNull(weight,weightDefault)) {
      sb.append(" ");
      sb.append(NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_NAME);
      sb.append("='");
      sb.append(formatDoubleArray(weight, insertCommas, insertLineBreaks));
      sb.append("'");     
    }

    return sb.toString();
  }


  public int getOrder()
  {
    return order.getValue();
  }
  public void setOrder(String value)
  {
    order = new SFInt32(value);
  }
  
  public void setKnot(String[] saa)
  {
    if(saa.length == 0) {
      knot = new SFDouble[]{};
      return;
    }
    knot = new SFDouble[saa.length];
    for (int r=0;r<saa.length; r++) {
      knot[r] = buildSFDouble(saa[r]);
    }
  }
  
  public void setKnot(String text)
  {
    if(text.length() == 0) {
      knot = new SFDouble[]{};
      return;
    }
    setKnot(text.replace(',', ' ').trim().split("\\s"));
  }
  public String getKnot()
  {
    if (knot.length == 0)
        return "";
    
    StringBuilder knotBuilder = new StringBuilder();
    
    for(int r=0; r < knot.length; r++)
    {
      knotBuilder.append(knot[r].toString()).append(" ");
    }
    return knotBuilder.toString().trim();
  }
  public String[] getKnotArray()
  {
    if (knot.length == 0)
        return new String[]{ }; // default weight, controlPoint pair is empty
    
    String[] sa = new String[knot.length];
    
    for(int r=0; r < knot.length; r++)
    {
      sa[r] = knot[r].toString();
    }
    return sa;
  }
  
  public void setWeightsAndControlPoints(String[][] saa)
  {
    if(saa.length == 0)
    {
            weight = new SFDouble[]  {};
      controlPoint = new SFDouble[][]{};
      return;
    }
    
          weight = new SFDouble[saa.length];
    controlPoint = new SFDouble[saa.length][3];
    
    for (int r=0;r<saa.length; r++)
    {
       weight[r] = buildSFDouble(saa[r][0]); // column 0
       for (int c=0;c<3; c++) {
           controlPoint[r][c] = buildSFDouble(saa[r][c+1]); // columns 1..3
       }
    }
  }
  public String[][] getWeightsAndControlPoints()
  {
    if ((weight.length == 0) && (controlPoint.length == 0))
        return new String[][]{{ }}; // default weight, controlPoint pair is empty
    
    int pointLength = Math.max(weight.length, controlPoint.length); // append 0 data if one array is shorter
    
    String[][] saa = new String[pointLength][4];
    
    for(int row=0; row < pointLength; row++)
    {
      if (row < weight.length)
          saa[row][0] = weight[row].toString();
      else 
      {
          saa[row][0] = "1"; // fill-in value for weight
          hasChangedValues = true;
      }
      
      for(int col=0; col < 3; col++)
      {
          if (row < controlPoint.length)
              saa[row][col+1] = controlPoint[row][col].toString();
          else 
          {
              saa[row][col+1] = "0"; // fill-in value
              hasChangedValues = true;
          }
      }
    }
    return saa;
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

    /**
     * @return the coordinateValueArrayEditable
     */
    public boolean isCoordinateValueArrayEditable() {
        return coordinateValueArrayEditable;
    }

    /**
     * @param coordinateValueArrayEditable the coordinateValueArrayEditable to set
     */
    public void setCoordinateValueArrayEditable(boolean coordinateValueArrayEditable) {
        this.coordinateValueArrayEditable = coordinateValueArrayEditable;
    }

    /**
     * @return whether child Coordinate node was found
     */
    public boolean isCoordinateNodeFound() {
        return coordinateNodeFound;
    }

    /**
     * @return whether child CoordinateDouble node was found
     */
    public boolean isCoordinateDoubleNodeFound() {
        return coordinateDoubleNodeFound;
    }
    /**
     * @return whether child Coordinate DEF node was found
     */
    public boolean isCoordinateDefNodeFound() {
        return coordinateNodeFound && !coordinateNodeDEF.isEmpty();
    }
    /**
     * @return whether child CoordinateDouble DEF node was found
     */
    public boolean isCoordinateDoubleDefNodeFound() {
        return coordinateDoubleNodeFound && !coordinateNodeDEF.isEmpty();
    }
    /**
     * @return whether child Coordinate USE node was found
     */
    public boolean isCoordinateUseNodeFound() {
        return coordinateNodeFound && !coordinateNodeUSE.isEmpty();
    }
    /**
     * @return whether child CoordinateDouble USE node was found
     */
    public boolean isCoordinateDoubleUseNodeFound() {
        return coordinateDoubleNodeFound && !coordinateNodeUSE.isEmpty();
    }

    /**
     * @return the coordinateNodeDEF
     */
    public String getCoordinateNodeDEF() {
        return coordinateNodeDEF;
    }

    /**
     * @return the coordinateNodeUSE
     */
    public String getCoordinateNodeUSE() {
        return coordinateNodeUSE;
    }

    /**
     * @return hasChangedValues
     */
    public boolean hasChangedValues() {
        return hasChangedValues;
    }
}
