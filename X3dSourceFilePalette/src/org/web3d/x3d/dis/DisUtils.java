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

package org.web3d.x3d.dis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * DisUtils.java
 * Created on 9 Jan 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class DisUtils
{
  /**
   * Based on the idxStr, typically stored in the XML, return the appropriate
   * enum to go into the combobox.  Else, return an approprate string.
   * @param en enum--any instance will do
   * @param idxStr normally "1", "2", "47", etc.
   * @return enum or descriptive string
   */
  public static Object getDisEnum( Enum en, String idxStr)
  {
    int idx = 0;
    if(idxStr == null)
      idxStr = "0";
    try {
      idx = (new SFInt32(idxStr)).getValue();
      Field lkup = en.getClass().getDeclaredField("lookup");
      Object obj = lkup.get(null); //static instance
      if(obj.getClass().isArray())
        return ((Object[])obj)[idx];
    }
    catch(Exception ex) {
    }
    return ""+idx;
  }

  /**
   * Reverse of getDisEnum()...this is the object from the combo, convert
   * it so the int string that goes into the xml
   * @param fromCombo
   * @return integer as string
   */
  public static String intStringFromDisEnum(Object fromCombo)
  {
    if(fromCombo instanceof Enum) {
      try {
        Class eCls = fromCombo.getClass();
        @SuppressWarnings("unchecked")
        Method getValue = eCls.getMethod("getValue", (Class[])null);
        return ""+getValue.invoke(fromCombo,(Object[])null);
      }
      catch(Exception ex) {
      }
    }
    return fromCombo.toString();
 }
}
