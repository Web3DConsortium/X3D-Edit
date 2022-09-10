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
/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 */
/*
 * RecentColors.java
 *
 * Created on 30. listopad 2003, 21:55
 */

package net.java.dev.colorchooser;

import java.awt.Color;
import java.awt.Dimension;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;

/** Palette implementation that can have recent colors added to it.
 *
 * @author  Tim Boudreau
 */
class RecentColors extends Palette {
    private Palette palette; 
    private boolean changed=true;
    /** Creates a new instance of RecentColors */
    private RecentColors() {
    }
    
    private Palette getWrapped() {
        if (changed || palette == null) {
            palette = createPalette();
            changed = false;
        }
        return palette;
    }
    
    @Override
    public java.awt.Color getColorAt(int x, int y) {
        return getWrapped().getColorAt(x,y);
    }
    
    @Override
    public String getDisplayName() {
        try {
            return ResourceBundle.getBundle(
                "org.netbeans.swing.colorchooser.Bundle").getString("recent"); //NOI18N
        } catch (MissingResourceException mre) {
//            mre.printStackTrace(System.err);
            return "Recent colors";
        }
    }
    
    @Override
    public Dimension getSize() {
        Dimension result = ((PredefinedPalette)getWrapped()).calcSize();
        return result;
    }
    
    @Override
    public void paintTo(java.awt.Graphics g) {
        getWrapped().paintTo(g);
    }
    
    @Override
    public String getNameAt(int x, int y) {
        return getWrapped().getNameAt(x,y);
    }
    
   Stack stack = new Stack();
   @SuppressWarnings("unchecked")
   void add(Color c) {
        if (c instanceof RecentColor) {
            return;
        }
        if (stack.indexOf(c) == -1) {
            String name = c instanceof PredefinedPalette.BasicNamedColor ? 
                ((NamedColor) c).getDisplayName() : null;
            String toString = c instanceof PredefinedPalette.BasicNamedColor ?
                c.toString() : null;
            Color col = new RecentColor(name, c.getRed(), c.getGreen(), c.getBlue(), toString);
            stack.push(col);
            changed = true;
            palette = null;
            if (c instanceof NamedColor) {
                addToNameCache((NamedColor)c);
            }
            saveToPrefs();
        }
    }
    public static final String INNER_DELIMITER="^$";
    public static final String OUTER_DELIMITER="!*";
    @SuppressWarnings("unchecked")
    public void saveToPrefs() {
        Preferences prefs = getPreferences();
        if (prefs == null) return;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        Stack tempStack = new Stack();
        tempStack.addAll(this.stack);
        while (!tempStack.isEmpty() && count < 64) {
            count++;
            Color c = (Color) tempStack.pop();
            if (c instanceof DummyColor) {
                break;
            }
            String name="null";
            if (c instanceof PredefinedPalette.BasicNamedColor) {
                PredefinedPalette.BasicNamedColor nc = (PredefinedPalette.BasicNamedColor) c;
                name = nc.getDisplayName();
            }
            if ((name != null) && name.equals("null")) { //NOI18N
                name = null;
            }
            sb.append (name);
            sb.append(INNER_DELIMITER);
            sb.append (c.getRed());
            sb.append (INNER_DELIMITER);
            sb.append(c.getGreen());
            sb.append (INNER_DELIMITER);
            sb.append(c.getBlue());
            sb.append (INNER_DELIMITER);
            if (c instanceof PredefinedPalette.BasicNamedColor) {
                sb.append(c.toString());
            } else {
                sb.append('x');
            }
            sb.append (OUTER_DELIMITER); //NOI18N
        }
        prefs.put("recentColors", sb.toString()); //NOI18N
    }
    
    static Map namedMap = null;
    static NamedColor findNamedColor (Color color) {
        if (namedMap == null) {
            return null;
        }
        NamedColor result = (NamedColor)namedMap.get(color.getRGB());
        return result;
    }
    
  @SuppressWarnings("unchecked")
  static void addToNameCache (NamedColor color) {
        if (namedMap == null) {
            namedMap = new HashMap(40);
        }
        namedMap.put (color.getRGB(), color);
    }
    
    @SuppressWarnings("removal") // for AccessControlException
    private Preferences getPreferences() {
        try {
            Preferences base = Preferences.userNodeForPackage(getClass());
            return base.node("1.5"); //NOI18N
        } catch (AccessControlException ace) {
            return null;
        }
    }
    
  @SuppressWarnings("unchecked")
  public void loadFromPrefs() {
        Preferences prefs = getPreferences();
        if (prefs == null) return;
        String s = prefs.get("recentColors", null); //NOI18N
        stack = new Stack();
        Color[] col = new Color[64];
        Arrays.fill(col, new DummyColor());
        int count = 63;
        try {
            if (s != null) {
                //a weird but highly unlikely delimiter
                StringTokenizer tok = new StringTokenizer(s,OUTER_DELIMITER); //NOI18N
                while (tok.hasMoreTokens() && count >= 0) {
                    String curr = tok.nextToken();
                    //another weird but highly unlikely delimiter
                    StringTokenizer tk2 = new StringTokenizer(curr, INNER_DELIMITER); //NOI18N
                    while (tk2.hasMoreTokens()) {
                        String name = tk2.nextToken();
                        if ("null".equals(name)) {
                            name = null;
                        }
                        int r = Integer.parseInt(tk2.nextToken());
                        int g = Integer.parseInt(tk2.nextToken());
                        int b = Integer.parseInt(tk2.nextToken());
                        String toString = tk2.nextToken();
                        if ("x".equals(toString)) { //NOI18N
                            col[count] = new RecentColor(name,r,g,b);
                        } else {
                            col[count] = new RecentColor(name,r,g,b,toString);
                        }
                        addToNameCache((NamedColor)col[count]);
                    }
                    count--;
                }
            }
            stack.addAll(Arrays.asList(col));
        } catch (NumberFormatException e) {
            System.err.println("Error loading color preferences"); //NOI18N
            e.printStackTrace(System.err);
        }
    }
    
  @SuppressWarnings("unchecked")
  private Palette createPalette() {
        PredefinedPalette.BasicNamedColor[] nc = (PredefinedPalette.BasicNamedColor[]) stack.toArray(new PredefinedPalette.BasicNamedColor[0]);
        return new PredefinedPalette("", nc); //NOI18N
    }
    
    private class RecentColor extends PredefinedPalette.BasicNamedColor {
        String displayName;
        String toString = null;
        public RecentColor (String name, int r, int g, int b) {
            super(name, r, g, b);
            displayName = name;
        }

        public RecentColor(String name, int r, int g, int b, String toString) {
            this(name, r, g, b);
            displayName = name;
            this.toString = toString;
        }
        
        @Override
        public int compareTo(Object o) {
            return stack.indexOf(o) - stack.indexOf(this);
        }
        
        @Override
        public String getDisplayName() {
            return displayName;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o instanceof Color) {
                Color c = (Color) o;
                return c.getRGB() == getRGB();
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return getRGB();
        }
        
        @Override
        public String toString() {
            if (toString != null) {
                return toString;
            } else {
                return "new java.awt.Color(" + getRed() + "," + getGreen() 
                    + "," + getBlue()+ ")"; //NOI18N
            }
        }
    }
    
    private static RecentColors defaultInstance = null;
    public static final RecentColors getDefault() {
        if (defaultInstance == null) {
            defaultInstance = new RecentColors();
            defaultInstance.loadFromPrefs();
        }
        return defaultInstance;
    }
    
    /** A stand in for colors to fill up the array of recent colors until
     * we really have something to put there. */
    private class DummyColor extends RecentColor {
        public DummyColor() {
            super(null, 0,0,0);
        }
        @Override
        public String getDisplayName() {
            return null;
        }
        //Ensure that no color will match this, so black swing colors can
        //be put into the recent colors array
        @Override
        public boolean equals(Object o) {
            return o == this;
        }
        @Override
        public int hashCode() {
            return System.identityHashCode(this);
        }
    }
    
}