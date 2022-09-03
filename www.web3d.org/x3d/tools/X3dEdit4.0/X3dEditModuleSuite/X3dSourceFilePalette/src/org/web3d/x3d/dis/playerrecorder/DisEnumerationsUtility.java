package org.web3d.x3d.dis.playerrecorder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * DisEnumerationsUtility.java
 * Created on Jul 20, 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey / jmbailey@nps.edu
 * @version $Id$
 */
public class DisEnumerationsUtility
{
  private static Class[] intParam = new Class[]{int.class};

  // Unnecessary performance tweeks
  private static boolean lastReturnGood = false;
  private static Class   lastClass = null;
  private static int     lastValue = -1;
  private static String  lastDescription = null;

  @SuppressWarnings("unchecked")
  public static String getEnumerationDescription(Class<?> enumerationClass, int value)
  {
    lastClass = enumerationClass;
    lastValue = value;
    try {
      Object[] oa = new Object[]{value};
      Method m = (Method) enumerationClass.getDeclaredMethod("enumerationForValueExists", intParam);
      Boolean yn = (Boolean)m.invoke(null, oa);
      if(yn) {
        m = (Method) enumerationClass.getDeclaredMethod("getDescriptionForValue", intParam);
        lastDescription = (String)m.invoke(null, oa);
        lastReturnGood = true;
        return lastDescription;
      }
    }
    catch(IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException t) {
      System.out.println("No DIS enumeration definition found to match value = "+value+" in class "+enumerationClass.getSimpleName());
    }
    lastReturnGood = false;
    return ""+value;
  }

  public static String getEnumerationToolTip(Class<?> enumerationClass, int value)
  {
    if((lastClass != enumerationClass) || (lastValue != value))
      getEnumerationDescription(enumerationClass,value);

    if(lastReturnGood)
      return "" + value + " " +  lastDescription;
    else
      return ("No DIS enumeration definition found to match value = "+value+" in class "+enumerationClass.getSimpleName());
  }
}
