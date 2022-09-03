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

import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * COORDINATECHASER.java
 * Created on 6 February 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public class COORDINATECHASER extends X3DChildNode
{
  private SFFloat duration,            durationDefault;
  
  private SFFloat[][] initialDestination, initialDestinationDefault;
  private SFFloat[][] initialValue,       initialValueDefault;
  private boolean     insertCommas, insertLineBreaks = false;

  public COORDINATECHASER()
  {
  }

  @Override
  public String getElementName()
  {
    return COORDINATECHASER_ELNAME;
  }
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return COORDINATECHASERCustomizer.class;
  }

  @Override
  public void initialize()
  {
    duration       = durationDefault       = new SFFloat(ORIENTATIONCHASER_ATTR_DURATION_DFLT,        0.0f, null); // no upper limit

    String [] sa;
    if(COORDINATECHASER_ATTR_INITIALVALUE_DFLT == null || COORDINATECHASER_ATTR_INITIALVALUE_DFLT.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(COORDINATECHASER_ATTR_INITIALVALUE_DFLT);
    initialValue = initialValueDefault = parseToSFFloatTable(sa,3); // 3-tuple

    if(COORDINATECHASER_ATTR_INITIALDESTINATION_DFLT == null || COORDINATECHASER_ATTR_INITIALDESTINATION_DFLT.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(COORDINATECHASER_ATTR_INITIALDESTINATION_DFLT);
    initialDestination = initialDestinationDefault = parseToSFFloatTable(sa,3); // 3-tuple
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(ORIENTATIONCHASER_ATTR_DURATION_NAME);
    if (attr != null)
        duration = new SFFloat(attr.getValue(), 0.0f, null);

    attr = root.getAttribute(COORDINATECHASER_ATTR_INITIALVALUE_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      initialValue = parseToSFFloatTable(sa,3); // 3-tuple
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }

    attr = root.getAttribute(COORDINATECHASER_ATTR_INITIALDESTINATION_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      initialDestination = parseToSFFloatTable(sa,3); // 3-tuple
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }
 
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (ORIENTATIONCHASER_ATTR_DURATION_REQD || (!duration.equals (durationDefault))) {
      sb.append(" ");
      sb.append(ORIENTATIONCHASER_ATTR_DURATION_NAME);
      sb.append("='");
      sb.append(duration);
      sb.append("'");
    }
    if(COORDINATECHASER_ATTR_INITIALVALUE_REQD || !arraysIdenticalOrNull(initialValue,initialValueDefault)) {
      sb.append(" ");
      sb.append(COORDINATECHASER_ATTR_INITIALVALUE_NAME);
      sb.append("='");
      sb.append(formatFloatArray (initialValue, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if(COORDINATECHASER_ATTR_INITIALDESTINATION_REQD || !arraysIdenticalOrNull(initialDestination,initialDestinationDefault)) {
      sb.append(" ");
      sb.append(COORDINATECHASER_ATTR_INITIALDESTINATION_NAME);
      sb.append("='");
      sb.append(formatFloatArray (initialDestination, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }
    /**
     * @return the duration value
     */
    public String getDuration ()
    {
        return duration.toString();
    }
    /**
     * @param newDuration the duration value to set
     */
    public void setDuration (SFFloat newDuration)
    {
        this.duration = newDuration;
    }
    /**
     * @return the initialDestination array
     */
  public String[][] getInitialDestination()
  {
    if(initialDestination.length == 0)
      return new String[][]{{}}; // something (actually nothing) to start with

    int kLen = initialDestination.length;

    String[][] saa = new String[kLen][3]; // 3-tuple

    for(int r=0; r<kLen; r++) {
      saa[r][0] = initialDestination[r].toString();
      for(int c=0; c<3; c++) // 3-tuple
      {
          if (initialDestination[r][c] != null)
              saa[r][c] = initialDestination[r][c].toString ();
          else
              saa[r][c] = "0.0"; // fill in too-short array
      }
    }
    return saa;
  }

    /**
     * @param saa the initialDestination array to set
     */
  public void setInitialDestination(String[][] saa)
  {
    if(saa.length == 0) {
      initialDestination = new SFFloat[][]{};
      return;
    }

    initialDestination = new SFFloat[saa.length][3]; // 3-tuple
    for (int r=0;r<saa.length; r++) {
      for(int c=0;c<3;c++) { // 3-tuple
          if (saa[r][c] != null)
              initialDestination[r][c] = buildSFFloat(saa[r][c]);
          else
              initialDestination[r][c] = buildSFFloat("0.0");
      }
    }
  }
    /**
     * @return the initialValue array
     */
  public String[][] getInitialValue()
  {
    if(initialValue.length == 0)
      return new String[][]{{}}; // something (actually nothing) to start with

    int kLen = initialValue.length;

    String[][] saa = new String[kLen][3]; // 3-tuple

    for(int r=0; r<kLen; r++) {
      saa[r][0] = initialValue[r].toString();
      for(int c=0; c<3; c++) // 3-tuple
      {
          if (initialValue[r][c] != null)
              saa[r][c] = initialValue[r][c].toString ();
          else
              saa[r][c] = "0.0"; // fill in too-short array
      }
    }
    return saa;
  }
    /**
     * @param saa the initialValue array to set
     */
  public void setInitialValue(String[][] saa)
  {
    if(saa.length == 0) {
      initialValue = new SFFloat[][]{};
      return;
    }

    initialValue = new SFFloat[saa.length][3]; // 3-tuple
    for (int r=0;r<saa.length; r++) {
      for(int c=0;c<3;c++) { // 3-tuple
          if (saa[r][c] != null)
              initialValue[r][c] = buildSFFloat(saa[r][c]);
          else
              initialValue[r][c] = buildSFFloat("0.0");
      }
    }
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
