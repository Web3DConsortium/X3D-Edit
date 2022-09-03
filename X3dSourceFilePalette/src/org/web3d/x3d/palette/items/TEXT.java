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

import java.util.Arrays;
import java.util.Vector;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DGeometryNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

// TODO add .jar to path, bug #1742 https://www.movesinstitute.org/bugzilla/show_bug.cgi?id=1742
// import com.tecnick.htmlutils.htmlentities.HTMLEntities;

/**
 * TEXT.java
 * Created on August 16, 2007, 10:49 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class TEXT extends X3DGeometryNode
{
  private String[]  stringArray, stringArrayDefault;

  private SFFloat[] length, lengthDefault;
  private SFFloat maxExtent, maxExtentDefault;
  private boolean solid;
  private boolean insertCommas, insertLineBreaks = false;

  public TEXT()
  {
  }

  @Override
  public String getElementName()
  {
    return TEXT_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTCustomizer.class;
  }

  @Override
  public void initialize()
  {
    stringArray = stringArrayDefault = parseMFStringIntoStringArray(TEXT_ATTR_STRING_DFLT, true);

    String[] sa;
    if(TEXT_ATTR_LENGTH_DFLT == null || TEXT_ATTR_LENGTH_DFLT.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(TEXT_ATTR_LENGTH_DFLT);
    length = lengthDefault = parseToSFFloatArray(sa);

    maxExtent = maxExtentDefault = new SFFloat(TEXT_ATTR_MAXEXTENT_DFLT, 0.0f, null);
    solid = Boolean.parseBoolean(TEXT_ATTR_SOLID_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(TEXT_ATTR_STRING_NAME);
    if (attr != null)
      stringArray = parseMFStringIntoStringArray(attr.getValue(), false); // false = do not strip outer quotes
      // results get fully escaped already, so do not strip trailing \" which is part of intended string
	  // example scene: https://www.web3d.org/x3d/content/examples/Basic/development/TextExamples.x3d

    attr = root.getAttribute(TEXT_ATTR_LENGTH_NAME);
    if(attr != null) {
      String[] sa = parseX(attr.getValue());
      length = parseToSFFloatArray(sa);
    }
    attr = root.getAttribute(TEXT_ATTR_MAXEXTENT_NAME);
    if(attr != null)
      maxExtent = new SFFloat(attr.getValue(), 0.0f, null);

    attr = root.getAttribute(TEXT_ATTR_SOLID_NAME);
    if(attr != null)
     solid = Boolean.parseBoolean(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if ((length != null) && !lengthFormatted().equals("") && 
        (TEXT_ATTR_LENGTH_REQD || !arraysIdenticalOrNull(length,lengthDefault))) {
      sb.append(" ");
      sb.append(TEXT_ATTR_LENGTH_NAME);
      sb.append("='");
      sb.append(lengthFormatted());
      sb.append("'");
    }
    if (TEXT_ATTR_MAXEXTENT_REQD || !maxExtent.equals(maxExtentDefault)) {
      sb.append(" ");
      sb.append(TEXT_ATTR_MAXEXTENT_NAME);
      sb.append("='");
      sb.append(maxExtent);
      sb.append("'");
    }
    if (TEXT_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(TEXT_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(TEXT_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    if (TEXT_ATTR_STRING_REQD || (!Arrays.equals(stringArray, stringArrayDefault) && stringArray.length>0)) {
      sb.append(" ");
      sb.append(TEXT_ATTR_STRING_NAME);
      sb.append("='");
      sb.append(formatStringArray(stringArray, insertCommas, insertLineBreaks)); // includes XML character escaping
      sb.append("'");
    }
    return sb.toString();
  }

  private Vector<String> parseString(String s) // parse input
  {
    Vector<String> v = new Vector<>();
    s = s.trim();
    if(s.length()>0) {
      if(!s.startsWith("\""))
        v.add(s);
      else {  // surrounded by quotes
        s = s.substring(1); // chop first
        if(s.endsWith("\""))
          s = s.substring(0,s.length()-1);  // chop last

        String[] sa = s.replace(',', ' ').trim().split("\"\\s*\"");  // quote front and back, white between
        v.addAll(Arrays.asList(sa));
      }
    }
    return v;
  }

  /**
   *
   * @return length field which is an MFFloat array, formatted
   */

  private String lengthFormatted()
  {
    if ((length==null) || (length.length == 0) || ((length.length == 1) && (length[0].toString().equals("0"))))
        return "";
    StringBuilder sb = new StringBuilder();
    for(SFFloat f : length) {
      sb.append(f.toString());
      sb.append(' ');
    }
    if(sb.length()>0)
      sb.setLength(sb.length()-1);  // trim last space character
    return sb.toString();
  }

  private Vector<SFFloat> parseLength(String s)
  {
    Vector<SFFloat> v = new Vector<>();
    s = s.trim();
    if(s.length()>0) {
      String[] sa = s.replace(',', ' ').trim().split("\\s");
      for(String st : sa)
        {
            v.add(new SFFloat(st, 0.0f, null));
        }
    }
    return v;
  }

  public String[] getStringArray()
  {
    return this.stringArray;
  }

  public void setStringArray(String[] newStringArray)
  {
    this.stringArray = newStringArray;
  }

  public String getLength()
  {
    return lengthFormatted();
  }

  public void setLength(String newLength)
  {
    if ((newLength == null) || (newLength.length() == 0))
    {
        this.length = null;
    }
    else
    {
        String[] sa = parseX(newLength);
        this.length = parseToSFFloatArray(sa);
    }
  }

  public String getMaxExtent()
  {
    return maxExtent.toString();
  }

  public void setMaxExtent(String maxExtent)
  {
    this.maxExtent = new SFFloat(maxExtent,0.0f,null);
  }

  public boolean isSolid()
  {
    return solid;
  }

  public void setSolid(boolean solid)
  {
    this.solid = solid;
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
     * @param insertLineBreaks the insertLineBreak values to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }
}
