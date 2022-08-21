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
LOSS OF USE, DATA, OR PROFITS;OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/
package org.web3d.x3d.types;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;

/**
 * X3DSchemaData4.java
 * Created on April, 3, 2021 @ 1142 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Terry Norbraten
 * @version $Id$
 */
public class X3DSchemaData4
{
  // X3D v4.0 ===================================================================================

  /**
   * parse2 parses 2 values at a time into String[] array
   * @param values input string holding token values
   * @return String array containing each value
   */
  static public String[] parse2(String values)
  {
    return parse(values,2);
  }

  /**
   * parse3 parses 3 values at a time into String[] array
   * @param values input string holding token values
   * @return String array containing each value
   */
  static public String[] parse3(String values)
  {
    return parse(values,3);
  }

  /**
   * parse4 parses 4 values at a time into String[] array
   * @param values input string holding token values
   * @return String array containing each value
   */
  static public String[] parse4(String values)
  {
    return parse(values,4);
  }

  /**
   * parse9 parses 9 values at a time (i.e. a 3x3 matrix) into String[] array
   * @param values input string holding token values
   * @return String array containing each value
   */
  static public String[] parse9(String values)
  {
    return parse(values,9);
  }

  /**
   * parseX allows whitespace and/or punctuation to separate delimiters
   * @param values input string holding token values
   * @return String array containing each value
   */
  static public String[] parseX(String values)
  {
    if ((values == null) || (values.trim().length() == 0 ))
        return new String[] {""};
    return values.replace(',', ' ').trim().split("(\\s+[,;:/]*\\s*)|(\\s*[,;:/]+\\s*)");
  }

  /**
   * parse  parses count values into a String[] array
   * @param values input string holding values
   * @param count number of token values expected
   * @return String array containing each value
   */
  static public String[] parse(String values, int count)
  {
     String[] sa = parseX(values);
     if(sa.length != count)
      throw new NumberFormatException("Wrong number of tokens");
    return sa;
  }

  /**
   * DegreesDecimalToDegreesMinutesSeconds utility converts position values from DegreesDecimal to DegreesMinutesSeconds
   * @param positionValue is a latitude or longitude value expressed in decimal degrees
   * @return DegreesMinutesSeconds as a string
   */
  static public String DegreesDecimalToDegreesMinutesSeconds (String positionValue)
  {
      String sign;
      double value, seconds;
      int    degrees, minutes;
      DecimalFormat format4DigitFractionalPart      = new DecimalFormat("##.####");

      // avoid internalization (I18N) localization (L10N) of decimal separator (decimal point)
      DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
      decimalFormatSymbols.setDecimalSeparator('.');

      value   = Double.parseDouble(positionValue);
      if (value < 0.0d)
           sign = "-";
      else sign = "";
      value = Math.abs(value);
      degrees = (int) Math.floor(value);
      minutes = (int) ((value - Math.floor(value)) * 60.0d);
      seconds = (value - degrees - (minutes)/60.0d) * 3600.0d;
      return sign + degrees + "° " + minutes + "' " + format4DigitFractionalPart.format(seconds) + "''";
  }

  /**
   * DegreesMinutesSecondsToDegreesDecimal utility converts position values from DegreesMinutesSeconds to DegreesDecimal
   * @param positionValue is a latitude or longitude value expressed in decimal degrees
   * @return DegreesDecimal string
   */
  static public String DegreesMinutesSecondsToDegreesDecimal (String positionValue) // TODO filter input values
  {
      boolean negative;
      int    degrees = 0;
      int    minutes = 0;
      double seconds = 0.0d;

      String sa[] = positionValue.split("[+-°'\"\\s]"); // split on + - ° ' " or whitespace
      negative = positionValue.trim().startsWith("-");
      // TODO consider warning of errors
      // TODO handle missing degree or minute values
      if (sa[0].length() > 0) degrees = (new SFInt32  (sa[0])).getValue();
      if (sa[1].length() > 0) minutes = (new SFInt32  (sa[1])).getValue();
      if (sa[2].length() > 0) seconds = (new SFDouble (sa[2])).getValue();

      double value   = degrees + minutes/60.d + seconds/3600.0d;
      if (negative) value = -value;
      return "" + value;
  }
}
