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
package net.java.dev.colorchooser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;

/**
 * Parent class of UI delegates for color choosers.  This class handles popping
 * up palettes and selection/setting transient color and firing events.
 * Generally, subclasses will simply want to override the painting logic.
 * <p>
 * To completely override all behavior, override <code>installListeners()</code>
 * and <code>uninstallListeners()</code> and do not have them call super.
 *
 * @author Tim Boudreau
 */
public abstract class ColorChooserUI extends ComponentUI {
    /** Creates a new instance of ColorChooserUI */
    protected ColorChooserUI() {
    }
    
    public final void installUI(JComponent jc) {
        installListeners((ColorChooser) jc);
        init((ColorChooser) jc);
    }
    
    public final void uninstallUI(JComponent jc) {
        uninstallListeners((ColorChooser) jc);
        uninit((ColorChooser) jc);
    }
    
    /** Optional initialization method called from <code>installUI()</code> */
    protected void init (ColorChooser c) {
        
    }
    
    /** Optional initialization method called from <code>uninstallUI()</code>*/
    protected void uninit (ColorChooser c) {
        
    }
    
    /** Begin listening for mouse events on the passed component */
    protected void installListeners(ColorChooser c) {
        L l = new L();
        c.addMouseListener(l);
        c.addFocusListener (l);
        c.addKeyListener (l);
        c.putClientProperty ("uiListener", l); //NOI18N
    }
    
    /** Stop listening for mouse events on the passed component */
    protected void uninstallListeners(ColorChooser c) {
        Object o = c.getClientProperty("uiListener"); //NOI18N
        if (o instanceof L) {
            L l = (L) o;
            c.removeMouseListener(l);
            c.removeFocusListener(l);
            c.removeKeyListener(l);
        }
    }
    
    /** Running on macintosh? */
    static final boolean MAC = System.getProperty ("mrj.version") //NOI18N
        != null;
    /**
     * 
     * Map a key event to an integer used to index into the array of available
     * palettes, used to change which palette is displayed on the fly.  Note
     * this method reads the key code, not the modifiers, of the key event.
     * <p>
     * If you override this method, also override <code>paletteIndexFromModifiers</code>.
     * <p>
     * The palette actually used is as follows:
     *  <ul>
     *  <li>No keys held: 0</li>
     *  <li>Shift: 1</li>
     *  <li>Ctrl (Command on macintosh): 2</li>
     *  <li>Shift-Ctrl(Command): 3</li>
     *  <li>Alt: 4</li>
     *  <li>Alt-Shift: 5</li>
     *  <li>Alt-Ctrl(Command): 6</li>
     *  <li>Alt-Ctrl(Command)-Shift: 7</li>
     *  </ul>
     */
    protected int paletteIndexFromKeyCode(final KeyEvent ke) {
        int keyCode = ke.getKeyCode();
        int result = (keyCode == KeyEvent.VK_SHIFT) ? 1 : 0;
        if (MAC) {
            result += (keyCode == KeyEvent.VK_META) ? 2 : 0;
        } else {
            result += (keyCode == KeyEvent.VK_CONTROL) ? 2 : 0;
        }
        result += (keyCode == KeyEvent.VK_ALT) ? 4 : 0;
        return result;
    }
    
    /**
     * 
     * Map the modifiers on an input event 
     * to an integer used to index into the array of available
     * palettes, used to change which palette is displayed on the fly.  Note
     * this method uses the value of from the event's <code>getModifiersEx()</code>
     * method.
     * <p>
     * If you override this method, also override 
     * <code>paletteIndexFromKeyCode</code>.
     * <p>
     * The palette actually used is as follows:
     *  <ul>
     *  <li>No keys held: 0</li>
     *  <li>Shift: 1</li>
     *  <li>Ctrl (Command on macintosh): 2</li>
     *  <li>Shift-Ctrl(Command): 3</li>
     *  <li>Alt: 4</li>
     *  <li>Alt-Shift: 5</li>
     *  <li>Alt-Ctrl(Command): 6</li>
     *  <li>Alt-Ctrl(Command)-Shift: 7</li>
     *  </ul>
     */
    protected int paletteIndexFromModifiers (InputEvent me) {
        int mods = me.getModifiersEx();
        int result = ((mods & me.SHIFT_DOWN_MASK) != 0) ? 1 : 0;
        result += ((mods & me.CTRL_DOWN_MASK) != 0) ? 2 : 0;
        result += ((mods & me.ALT_DOWN_MASK) != 0) ? 4 : 0;
        return result;
    }

    private JColorChooser jchooser = null;
    /**
     * Called when the color chooser is invoked from the keyboard (user pressed
     * space or enter).
     */
    protected void keyboardInvoke(final ColorChooser colorChooser) {
        if (!colorChooser.isEnabled()) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        if (jchooser == null) {
            jchooser = new JColorChooser();
        } else {
	    return;
	}
        jchooser.setColor(colorChooser.getColor());
        Color nue = jchooser.showDialog(
                colorChooser, 
                ColorChooser.getString("chooseColor"), //NOI18N
                colorChooser.getColor());
        
        if (nue != null) {
            colorChooser.setColor(nue);
            fireColorChanged(colorChooser);
        }
	jchooser = null;
    }
    
    /**
     * Cause the passed color chooser to fire an action event to its listeners
     * notifying them that the color has changed.
     */
    protected void fireColorChanged(ColorChooser chooser) {
            chooser.fireActionPerformed(new ActionEvent(chooser, 
                ActionEvent.ACTION_PERFORMED, "color")); //NOI18N        
    }

    public Dimension getMaximumSize(JComponent c) {
        if (!c.isMaximumSizeSet()) {
            return getPreferredSize (c);
        } else {
            return super.getMaximumSize(c);
        }
    }

    public Dimension getMinimumSize(JComponent c) {
        if (!c.isMinimumSizeSet()) {
            return getPreferredSize (c);
        } else {
            return super.getMinimumSize(c);
        }
    }

    public Dimension getPreferredSize(JComponent c) {
        if (!c.isPreferredSizeSet()) {
            return new Dimension (24, 24);
        } else {
            return super.getPreferredSize(c);
        }
    }
    
    private class L extends MouseAdapter implements FocusListener, KeyListener {
        private int paletteIndex = 0;
        private transient Point nextFocusPopupLocation = null;

        int getPaletteIndex() {
            return paletteIndex;
        }
        
        void initPaletteIndex(ColorChooser c, MouseEvent me) {
            paletteIndex = paletteIndexFromModifiers (me);
            checkRange(c);
        }

        private void checkRange(ColorChooser chooser) {
            Palette[] p = chooser.getPalettes();
            if (paletteIndex >= p.length) {
                paletteIndex = p.length-1;
            }
        }

        private void updatePaletteIndex(ColorChooser chooser, int value, boolean pressed) {
            int oldIndex = paletteIndex;
            int result = paletteIndex;
            if (pressed) {
                result |= value;
            } else {
                result ^= value;
            }
            if (oldIndex != result && PalettePopup.getDefault().isPopupVisible(chooser)) {
                paletteIndex = result;
                checkRange(chooser);
                PalettePopup.getDefault().setPalette(chooser.getPalettes()[paletteIndex]);
            }
        }

        
        public void mousePressed(MouseEvent me) {
            if (me.isPopupTrigger()) return;
            ColorChooser chooser = (ColorChooser) me.getSource();
            if (!chooser.isEnabled()) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            Point p = me.getPoint();
            SwingUtilities.convertPointToScreen(p, chooser);
            initPaletteIndex (chooser, me);
            PalettePopup.getDefault().setPalette(chooser.getPalettes()[
                    getPaletteIndex()]);
            if (chooser.hasFocus()) {
                PalettePopup.getDefault().showPopup(chooser, p);
            } else {
                nextFocusPopupLocation = p;
                chooser.requestFocus();
                return;
            }
            me.consume();
            nextFocusPopupLocation = null;
        }

        public void mouseReleased(MouseEvent me) {
            if (me.isPopupTrigger()) return;
            ColorChooser chooser = (ColorChooser) me.getSource();
            if (!chooser.isEnabled()) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            nextFocusPopupLocation = null;
            if (PalettePopup.getDefault().isPopupVisible(chooser)) {
                PalettePopup.getDefault().hidePopup(chooser);
                Color transientColor = chooser.transientColor();
                if (transientColor != null) {
                    RecentColors.getDefault().add(transientColor);
                    Color old = new Color (
                            transientColor.getRed(),
                            transientColor.getGreen(), 
                            transientColor.getBlue());
                    chooser.setTransientColor(null);
                    chooser.setColor (old);
                    fireColorChanged(chooser);
                    me.consume();
                }
            }
        }

        public void focusGained(FocusEvent e) {
            ColorChooser chooser = (ColorChooser) e.getSource();
            if (nextFocusPopupLocation != null && chooser.isEnabled()) {
                PalettePopup.getDefault().showPopup(chooser, 
                        nextFocusPopupLocation);
            }
            nextFocusPopupLocation = null;
            chooser.repaint();
        }

        public void focusLost(FocusEvent e) {
            ColorChooser chooser = (ColorChooser) e.getSource();
            chooser.repaint();
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            processKeyEvent (e, true);
        }

        public void keyReleased(KeyEvent e) {
            processKeyEvent (e, false);
        }
        
        protected void processKeyEvent (KeyEvent ke, boolean pressed) {
            ColorChooser chooser = (ColorChooser) ke.getSource();
            updatePaletteIndex(chooser, paletteIndexFromKeyCode(ke), pressed);
            if (ke.getKeyCode() == ke.VK_ALT || ke.getKeyCode() == ke.VK_CONTROL ||
                    ke.getKeyCode() == ke.VK_SHIFT) {
                ke.consume();
            } else if ((ke.getKeyCode() == ke.VK_SPACE || ke.getKeyCode() ==
                    ke.VK_ENTER) && ke.getID() == KeyEvent.KEY_RELEASED) {
                keyboardInvoke(chooser);
            }
        }
    }
}