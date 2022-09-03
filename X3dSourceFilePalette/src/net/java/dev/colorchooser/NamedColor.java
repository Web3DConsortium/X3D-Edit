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
 * NamedColor.java
 *
 * Created on 1. prosinec 2003, 16:49
 */

package net.java.dev.colorchooser;

import java.awt.Color;

/** An abstract class representing a color which has a name and may provide
 * custom code for instantiation. Implements comparable in order to appear
 * in an ordered way in palettes.  Note that this class is internal to the color
 * chooser.  It is not acceptable
 * for the color chooser to provide instances of NamedColor from its getColor
 * method, since they may be serialized and will not be deserializable if their
 * implementation is not on the classpath.
 *
 * @author  Tim Boudreau
 */
abstract class NamedColor extends Color implements Comparable {
    /**
     * Creates a new instance of NamedColor
     * @param name
     * @param r red
     * @param g green
     * @param b blue
     */
    protected NamedColor(String name, int r, int g, int b) {
        super (r, g, b);
    }
    /**
     * Get a localized display name for this color if possible.  For
     * some colors, such as named system colors, a localized variant is not
     * a reasonable option.
     * @return the localized (or not) display name
     */
    public abstract String getDisplayName();
    /** Get the programmatic name, if any, for this color, such as a 
     * Swing UIDefaults key or an SVG constant name.*/
    public abstract String getName();
    /**
     * Fetch a java code snippet for instantiating this color.  For cases such
     * as named defaults from the Swing UIManager, this method might return
     * something such as <code>UIManager.getColor(&quot;control&quot;)</code>.
     * Useful when implementing a property editor.
     * @return a string that could be pasted into Java code to instantiate a
     * color with these rgb values
     */
    public String getInstantiationCode() {
        return toString();
    }
    
    static NamedColor create (Color c, String name) {
        return new DefaultNamedColor(c, name);
    }
    
    private static final class DefaultNamedColor extends NamedColor {
        private String name;
        public DefaultNamedColor(Color c, String name) {
            super (name, c.getRed(), c.getGreen(), c.getBlue());
            this.name = name;
        }

        public String getDisplayName() {
            return name;
        }

        public String getName() {
            return name;
        }

        public int compareTo(Object o) {
            if (o instanceof NamedColor) {
                NamedColor nc = (NamedColor) o;
                String nm = nc.getDisplayName();
                if (nm == null && getDisplayName() == null) {
                    return 0;
                } else {
                    return nm != null && getDisplayName() != null ?
                        getDisplayName().compareTo(nm) : -1;
                }
            } else {
                return -1;
            }
        }
    }
}