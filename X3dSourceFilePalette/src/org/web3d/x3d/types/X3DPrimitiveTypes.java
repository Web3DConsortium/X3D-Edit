/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.
 
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

package org.web3d.x3d.types;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

/**
 * X3DPrimitiveTypes.java
 * Created on July 12, 2007, 9:30 AM
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * 
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class X3DPrimitiveTypes
{
  public static DecimalFormat floatFormat, doubleFormat, singleDigitFormat, twoDigitFormat, threeDigitFormat, fourDigitFormat, fiveDigitFormat, radiansFormat, degreesFormat, colorFormat;
  
  public X3DPrimitiveTypes()
  {
  }
  
  static 
  {
    // avoid internalization (I18N) localization (L10N) of decimal separator (decimal point)
    Locale currentLocale = Locale.getDefault();
    System.out.println ("Current locale: " + currentLocale.getLanguage() + " " + currentLocale.getLanguage());
    
    Locale usLocale = new Locale ("en","US"); // avoid settings of current locale
    
    DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(usLocale);
    decimalFormatSymbols.setDecimalSeparator('.');    
    
          floatFormat = new DecimalFormat("0.######",     decimalFormatSymbols);
         doubleFormat = new DecimalFormat("0.###########",decimalFormatSymbols);
    singleDigitFormat = new DecimalFormat("0.#",          decimalFormatSymbols);
       twoDigitFormat = new DecimalFormat("0.##",         decimalFormatSymbols);
     threeDigitFormat = new DecimalFormat("0.###",        decimalFormatSymbols);
     fourDigitFormat  = new DecimalFormat("0.####",       decimalFormatSymbols);
    // 5 digits avoids rounding up CylinderSensor diskAngle above 90 degrees = 1.5708 because max allowed is 1.57079
     fiveDigitFormat  = new DecimalFormat("0.#####",      decimalFormatSymbols);
    
      colorFormat     = new DecimalFormat("0.####",       decimalFormatSymbols);
    radiansFormat     = new DecimalFormat("0.######",     decimalFormatSymbols);
    degreesFormat     = new DecimalFormat("###.#",        decimalFormatSymbols);
    
//    maximumDigitsFormat = new DecimalFormat();
//    maximumDigitsFormat.setMaximumFractionDigits(1);
  }

  public static interface X3Dtype
  {
    // abstract base interface
  }
  // the following normalization methods are interesting and included here for possible future use
  // but might not appear to be immediately useful if Float.parseFloat and Double.parseDouble can
  // be assumed to provide normalized values
  
  // TODO aren't these provided somewhere in Java?
  
  /**
   * Ensure floating-point value avoids denormalization problems for very small values, as described in
   * https://wiki.sei.cmu.edu/confluence/display/java/NUM54-J.+Do+not+use+denormalized+numbers
   * Note that strictfp can reduce performance on different platforms
   * https://stackoverflow.com/questions/517915/when-should-i-use-the-strictfp-keyword-in-java
   * @param value single-precision floating-point value
   * @return same value or else 0 as appropriate
   */
  
  /* strictfp */ public static float normalize (float value)
  {
      if   (value == 0)
           return value;
      else if ((value > -Float.MIN_NORMAL) && (value < Float.MIN_NORMAL))
           return 0.0f; // fix unsuspected problem
      else return value;
  }
  
  /**
   * Ensure floating-point value avoids denormalization problems for very small values, as described in
   * https://wiki.sei.cmu.edu/confluence/display/java/NUM54-J.+Do+not+use+denormalized+numbers
   * Note that strictfp can reduce performance on different platforms
   * https://stackoverflow.com/questions/517915/when-should-i-use-the-strictfp-keyword-in-java
   * @param value double-precision floating-point value
   * @return same value or else 0 as appropriate
   */
  
  /* strictfp */ public static double normalize (double value)
  {
      if   (value == 0)
           return value;
      else if ((value > -Double.MIN_NORMAL) && (value < Double.MIN_NORMAL))
           return 0.0; // fix unsuspected problem
      else return value;
  }
  
  /**
   * Detect denormalization problems for very small values, as described in
   * https://wiki.sei.cmu.edu/confluence/display/java/NUM54-J.+Do+not+use+denormalized+numbers
   * Note that strictfp can reduce performance on different platforms
   * https://stackoverflow.com/questions/517915/when-should-i-use-the-strictfp-keyword-in-java
   * @param value floating-point value
   * @return whether denormalized or not
   */
    /* strictfp */ public static boolean isDenormalized(float value) {
        if (value == 0) {
            return false;
        }
        return (value > -Float.MIN_NORMAL) && (value < Float.MIN_NORMAL);
    }     
  
  /**
   * Detect denormalization problems for very small values, as described in
   * https://wiki.sei.cmu.edu/confluence/display/java/NUM54-J.+Do+not+use+denormalized+numbers
   * Note that strictfp can reduce performance on different platforms
   * https://stackoverflow.com/questions/517915/when-should-i-use-the-strictfp-keyword-in-java
   * @param value double-precision value
   * @return whether denormalized or not
   */
    /* strictfp */ public static boolean isDenormalized(double value) {
        if (value == 0) {
            return false;
        }
        return (value > -Double.MIN_NORMAL) && (value < Double.MIN_NORMAL);
    }
    
    private static boolean foundLocalizedValue = false; // only output diagnostic once
    /**
     * Delocalize numeric values to English (decimal separator point)
     * @param pValue numeric string to be parsed
     * @return numeric string localized to English usage
     */
    protected static String delocalizeNumericValue (String pValue)
    {
        String value = pValue;
        
        Locale locale = Locale.getDefault();
        
        // if Locale is used like this, author's case sensitivity is lost.  alternate approach using Locale is not clear...
//       return value.toUpperCase(locale);
        
        if (value.contains(","))
        {
            if (!foundLocalizedValue)
            {
                System.err.println ("Encountered comma in numeric value, adjusting localization " + locale.getLanguage() + " to English");
                foundLocalizedValue = true;
            }
            while (value.endsWith(","))
                value = value.substring(0,value.length()-1);
            
            if  (!value.contains(","))
                 return value;
            else if  (value.contains(",") && !value.contains(".") && !locale.getLanguage().equalsIgnoreCase("EN"))
                 return value.replace(",",".");
            else return value; // punt
        }
        else return value;
    }
  /**
   * SFColor is X3D type Single Field Color, which includes r g b values
   */
  public static class SFColor implements X3Dtype
  {
    private Color color;
    public SFColor(Color c)
    {
      color = c;
    }
    public SFColor(String r, String g, String b) throws IllegalArgumentException
    {
      float fr, fg, fb;
      
      try
      {
        if  ((r == null) || r.trim().isEmpty())
             fr = 0.0f;
        else fr = normalize(Float.parseFloat(delocalizeNumericValue(r.trim())));

        if  ((g == null) || g.trim().isEmpty())
             fg = 0.0f;
        else fg = normalize(Float.parseFloat(delocalizeNumericValue(g.trim())));

        if  ((b == null) || b.trim().isEmpty())
             fb = 0.0f;
        else fb = normalize(Float.parseFloat(delocalizeNumericValue(b.trim())));
      }
      catch (NumberFormatException ex)
      {
        System.out.println ("SFColor constructor error: " + ex.getMessage());
        System.out.println ("   avoiding delocalization of value \"" + r + " " + g + " " + b + "\"");
        fr = normalize(Float.parseFloat(r));
        fg = normalize(Float.parseFloat(g));
        fb = normalize(Float.parseFloat(b));
      }
      color = new Color(fr,fg,fb);
    }
    public Color getColor ()
    {
        return color;
    }
    public String getHex ()
    {
        StringBuilder hexValue = new StringBuilder();
        String   redHex = Integer.toHexString(color.getRed());
        String greenHex = Integer.toHexString(color.getGreen());
        String  blueHex = Integer.toHexString(color.getBlue());
        
        if (  redHex.length() == 1)    redHex = "0" + redHex;
        if (greenHex.length() == 1)  greenHex = "0" + greenHex;
        if ( blueHex.length() == 1)   blueHex = "0" + blueHex;
        
        hexValue.append("#").append(  redHex)
                            .append(greenHex)
                            .append( blueHex);
        return hexValue.toString();
    }
    public String getHtmlColor ()
    {
        StringBuilder htmlValue = new StringBuilder();
        String   redHtmlColor = Integer.toString(color.getRed());
        String greenHtmlColor = Integer.toString(color.getGreen());
        String  blueHtmlColor = Integer.toString(color.getBlue());
        
        htmlValue.append(  redHtmlColor).append(" ")
                 .append(greenHtmlColor).append(" ")
                 .append( blueHtmlColor);
        return htmlValue.toString();
    }
  }
  
  /**
   * SFInt32 is X3D type Single Field Integer
   */
  public static class SFInt32 implements X3Dtype
  {
    Integer i, min, max;
    public SFInt32(int value)
    {
      this(Integer.toString(value),null,null);
    }
    public SFInt32(String s)
    {
      this(s,null,null);
    }
    public SFInt32(String s, Integer min, Integer max)
    {
      this(s,min,max,false);
    }
    public SFInt32(String s, Integer min, Integer max, boolean skipTest)
    {
      this.min = min;
      this.max = max;
      Integer _i;
      if((s == null) || s.trim().isEmpty())  // default value for empty string is 0
        _i = 0;
      else
      {
          try
          {
              _i = Integer.parseInt(delocalizeNumericValue(s.trim()));
          }
          catch (NumberFormatException ex)
          {
              System.out.println ("SFInt32 constructor error: " + ex.getMessage());
              System.out.println ("   avoiding delocalization of value \"" + s.trim() + "\"");
              _i = Integer.parseInt(s.trim());
          }
      }
      if(!skipTest)
        testLimits(_i);
      this.i = _i;
    }

    @Override
    public String toString()
    {  
          try
          {
              return delocalizeNumericValue(Integer.toString(i));
          }
          catch (NumberFormatException ex)
          {
              System.out.println ("SFInt32.toString() error: " + ex.getMessage());
              System.out.println ("   avoiding delocalization of value \"" + i + ")");
              return Integer.toString(i);
          }
    }
    @Override
    public boolean equals(Object obj)
    {
      return (obj instanceof SFInt32) &&
             (((SFInt32)obj).getValue() == this.getValue());
    }

    public int getValue()
    {
      return i;
    }
    
    public void setValue(int value)
    {
      i = value;
    }

    private void testLimits(int intValue) throws IllegalArgumentException
    {
        // TODO can we report offending class to help better identify what failed?
      if(min != null && intValue<min)
        throw new IllegalArgumentException("Attribute validation error:  entered value " + intValue + " is less than minimum of "+min);
      if(max != null && intValue>max)
        throw new IllegalArgumentException("Attribute validation error:  entered value " + intValue + " exceeds maximum of "+max);
    }
  }

  /**
   * SFFloat is X3D type Single Field Float
   */
  public static class SFFloat implements X3Dtype
  {
    Float f, min, max;
    public SFFloat(float value)
    {
      this(Float.toString(value),null,null);
    }
    public SFFloat(String s)
    {
      this(s,null,null);
    }
    public SFFloat(String s, Float min, Float max)
    {
      this(s,min,max,false);
    }
    public SFFloat(String s, Float min, Float max, boolean skipTest) // for -1,-1,-1 todo
    {
      this.min = min;
      this.max = max;
      Float _f;
      if((s == null) || s.trim().isEmpty())  // default value for empty string is 0.0f
        _f = 0.0f;
      else
      {
          try
          {
              _f = normalize(Float.parseFloat(delocalizeNumericValue(s.trim())));
          }
          catch (NumberFormatException ex)
          {
              System.out.println ("SFFloat constructor error: " + ex.getMessage());
              System.out.println ("   avoiding delocalization of value \"" + s.trim() + "\")");
              _f = normalize(Float.parseFloat(s.trim()));
          }
      }
      if(!skipTest)
        testLimits(_f);
      this.f = _f;
    }

    private void testLimits(float value) throws IllegalArgumentException
    {
        String message;
        // TODO can we report offending class to help better identify what failed?
      if(min != null && value<min)
      {
          message = "Attribute value error:  entered value " + value + " is less than minimum of " + min;
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "Float value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
      }
      else if(max != null && value>max)
      {
          message = "Attribute value error:  entered value " + value + " exceeds maximum of " + max;
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "Float value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
      }
    }

    @Override
    public String toString()
    {
          try
          {
              return f.isNaN() ? "" : delocalizeNumericValue(floatFormat.format(f)); //""+f;
          }
          catch (NumberFormatException ex)
          {
              System.out.println ("SFFloat.toString() error: " + ex.getMessage());
              System.out.println ("   avoiding delocalization of value \"" + f + "\"");
              return floatFormat.format(f);
          }
    }

    @Override
    public boolean equals(Object obj)
    {
      return (obj instanceof SFFloat) &&
             (Float.compare(((SFFloat)obj).getValue(),this.getValue()) == 0);
    }

    public float getValue()
    {
      return f;
    }
    
    public void setValue(float value)
    {
      f = value;
    }

  }

  /**
   * SFDouble is X3D type Single Field Double
   */
  public static class SFDouble implements X3Dtype
  {
    Double d, min, max;
    public SFDouble(double value)
    {
      this(Double.toString(value),null,null);
    }
    public SFDouble(String s)
    {
      this(s,null,null);
    }
    public SFDouble(String s, Double min, Double max)
    {
      this(s,min,max,false);
    }
    public SFDouble(String s, Double min, Double max, boolean skipTest) // for -1,-1,-1 todo
    {
      this.min = min;
      this.max = max;
      Double _d;
      if((s == null) || s.trim().isEmpty())  // default value for empty string is 0.0f
        _d = 0.0;
      else
      {
          try
          {
              _d = Double.parseDouble(delocalizeNumericValue(s.trim()));
          }
          catch (NumberFormatException ex)
          {
              System.out.println ("SFDouble constructor error: " + ex.getMessage());
              System.out.println ("   avoiding delocalization of value \"" + s.trim() + "\"");
              _d = Double.parseDouble(s.trim());
          }
      }
      if(!skipTest)
        testLimits(_d);
      this.d = _d;
    }

    private void testLimits(double value) throws IllegalArgumentException
    {
        // TODO can we report offending class to help better identify what failed?
      if(min != null && value<min)
        throw new IllegalArgumentException("Attribute validation error:  entered value " + value + " is less than minimum of "+min);
      if(max != null && value>max)
        throw new IllegalArgumentException("Attribute validation error:  entered value " + value + " exceeds maximum of "+max);
    }

    @Override
    public String toString()
    {
          try
          {
              return d.isNaN() ? "" : delocalizeNumericValue(doubleFormat.format(d)); //""+d;
          }
          catch (NumberFormatException ex)
          {
              System.out.println ("SFDouble.toString() error: " + ex.getMessage());
              System.out.println ("   avoiding delocalization of value \"" + d + "\"");
              return doubleFormat.format(d);
          }
    }

    @Override
    public boolean equals(Object obj)
    {
      return (obj instanceof SFDouble) &&
             (Double.compare(((SFDouble)obj).getValue(),this.getValue()) == 0);
    }

    public double getValue()
    {
      return d;
    }
    
    public void setValue(double value)
    {
      d = value;
    }

  }

  public static class SFFloat_orig implements X3Dtype
  {
    protected float f;
    private Float min,max;
    
    protected SFFloat_orig()
    {
      
    }
    public SFFloat_orig(float f)
    {
      this(f,null,null);
    }
    
    public SFFloat_orig(float f, Float min, Float max)
    {
      this(f,min,max,false);
    }
    
    public SFFloat_orig(float f, Float min, Float max, boolean skipTest) // for -1,-1,-1  todo
    {
      this.min = min;
      this.max = max;
      if(!skipTest)
        testLimits(f);
      this.f = f;
      
    }
    public SFFloat_orig(String sf) throws IllegalArgumentException
    {
      this(Float.parseFloat(sf));
//      float f;
//      try {
//        f = Float.parseFloat(sf);
//      }
//      catch (NumberFormatException ex) {
//        throw new IllegalArgumentException("Invalid float value");
//      }
//      testLimits(f);
//      this.f = f;
    }
    public SFFloat_orig(String sf, Float min, Float max)
    {
      this(Float.parseFloat(sf),min,max);
    }
    
    public void setValue(float f) throws IllegalArgumentException
    {
      testLimits(f);
      this.f = f;
    }
    
    public float getValue()
    {
      return f;
    }
    
    private void testLimits(float value) throws IllegalArgumentException
    {
      if(min != null && value<min)
        throw new IllegalArgumentException("Attribute validation error:  entered value " + value + "is less than minimum of "+min);
      if(max != null && value>max)
        throw new IllegalArgumentException("Attribute validation error:  entered value " + value + "exceeds maximum of "+max);
    }
  }
  
//  public static class SFVec3f implements X3Dtype
//  {
//    private float f0,f1,f2;
//    private Float min,max;
//    
//    public SFVec3f(SFFloat f0, SFFloat f1, SFFloat f2) throws IllegalArgumentException
//    {
//      this(f0.getValue(),f1.getValue(),f2.getValue());
//    }
//    
//    public SFVec3f(float f0, float f1, float f2) throws IllegalArgumentException
//    {
//      this.f0 = f0;
//      this.f1 = f1;
//      this.f2 = f2;
//      if(min != null && (f0<min||f1<min||f2<min))
//        throw new IllegalArgumentException("Lower than minimum");
//      if(max != null && (f0>max||f1>max||f2>max))
//        throw new IllegalArgumentException("Exceeds maximum");
//    }
//    
//    public SFVec3f (String[] fa) throws IllegalArgumentException
//    {
//      this(Float.parseFloat(fa[0]),Float.parseFloat(fa[1]),Float.parseFloat(fa[2]));
//    }
//    
//    public SFVec3f(String s) throws IllegalArgumentException
//    {
//      this(s.split("\\s",3));     
//    }
//    
//    public void setValue(SFVec3f vec)
//    {
//      //todo
//    }
//  }
}
