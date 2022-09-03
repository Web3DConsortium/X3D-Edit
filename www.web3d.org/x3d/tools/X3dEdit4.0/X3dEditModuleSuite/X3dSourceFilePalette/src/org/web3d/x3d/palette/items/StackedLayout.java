/*
 * @(#)CardLayout.java	1.40 04/05/18
 *
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.web3d.x3d.palette.items; //java.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Vector;

import java.io.Serializable;

/**
 * Chopped from CardLayout.java, jdk 1.5.0
 * This is a layout manager that operates as the CardLayout manager
 * it is based upon, but it elimnates the first, next, methods.  These
 * methods in the original CardLayout "stacked" the components at 0,0,
 * and did setVisible(false) on all components except the one "on top".
 * This variations also stacks components, but does not concern itself
 * with component visiblity and allows direct user control over
 * enablement and visiblity.  This way, visual effects such as 
 * transparency may be used.
 * So, in effect, we've extracted the layout-only code from CardLayout
 * and allow direct user control over visiblity
 */
public class StackedLayout implements LayoutManager2
{
  /*
   * A pair of Component and String that represents its name.
   */
  class Card implements Serializable
  {
    public String name;
    public Component comp;

    public Card(String cardName, Component cardComponent)
    {
      name = cardName;
      comp = cardComponent;
    }
    }

  /*
   * A cards horizontal Layout gap (inset). It specifies
   * the space between the left and right edges of a 
   * container and the current component.
   * This should be a non negative Integer.
   * @see getHgap()
   * @see setHgap()
   */
  int hgap;

  /*
   * A cards vertical Layout gap (inset). It specifies
   * the space between the top and bottom edges of a 
   * container and the current component.
   * This should be a non negative Integer.
   * @see getVgap()
   * @see setVgap()
   */
  int vgap;
 
  /**
   * Creates a new card layout with gaps of size zero.
   */
  public StackedLayout()
  {
    this(0, 0);
  }

  /**
   * Creates a new card layout with the specified horizontal and
   * vertical gaps. The horizontal gaps are placed at the left and
   * right edges. The vertical gaps are placed at the top and bottom
   * edges.
   * @param     hgap   the horizontal gap.
   * @param     vgap   the vertical gap.
   */
  public StackedLayout(int hgap, int vgap)
  {
    this.hgap = hgap;
    this.vgap = vgap;
  }

  /**
   * Gets the horizontal gap between components.
   * @return    the horizontal gap between components.
   * @see       java.awt.CardLayout#setHgap(int)
   * @see       java.awt.CardLayout#getVgap()
   * @since     JDK1.1
   */
  public int getHgap()
  {
    return hgap;
  }

  /**
   * Sets the horizontal gap between components.
   * @param hgap the horizontal gap between components.
   * @see       java.awt.CardLayout#getHgap()
   * @see       java.awt.CardLayout#setVgap(int)
   * @since     JDK1.1
   */
  public void setHgap(int hgap)
  {
    this.hgap = hgap;
  }

  /**
   * Gets the vertical gap between components.
   * @return the vertical gap between components.
   * @see       java.awt.CardLayout#setVgap(int)
   * @see       java.awt.CardLayout#getHgap()
   */
  public int getVgap()
  {
    return vgap;
  }

  /**
   * Sets the vertical gap between components.
   * @param     vgap the vertical gap between components.
   * @see       java.awt.CardLayout#getVgap()
   * @see       java.awt.CardLayout#setHgap(int)
   * @since     JDK1.1
   */
  public void setVgap(int vgap)
  {
    this.vgap = vgap;
  }

  public void addLayoutComponent(Component comp, Object constraints)
  {
  }

  @Deprecated
  public void addLayoutComponent(String name, Component comp)
  {
  }

  public void removeLayoutComponent(Component comp)
  {
  }

  /**
   * Determines the preferred size of the container argument using
   * this layout.
   * @param   parent the parent container in which to do the layout
   * @return  the preferred dimensions to lay out the subcomponents
   *                of the specified container
   * @see     java.awt.Container#getPreferredSize
   * @see     java.awt.CardLayout#minimumLayoutSize
   */
  public Dimension preferredLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();
      int w = 0;
      int h = 0;

      for (int i = 0; i < ncomponents; i++) {
        Component comp = parent.getComponent(i);
        Dimension d = comp.getPreferredSize();
        if (d.width > w) {
          w = d.width;
        }
        if (d.height > h) {
          h = d.height;
        }
      }
      return new Dimension(insets.left + insets.right + w + hgap * 2,
          insets.top + insets.bottom + h + vgap * 2);
    }
  }

  /**
   * Calculates the minimum size for the specified panel.
   * @param     parent the parent container in which to do the layout
   * @return    the minimum dimensions required to lay out the
   *                subcomponents of the specified container
   * @see       java.awt.Container#doLayout
   * @see       java.awt.CardLayout#preferredLayoutSize
   */
  public Dimension minimumLayoutSize(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();
      int w = 0;
      int h = 0;

      for (int i = 0; i < ncomponents; i++) {
        Component comp = parent.getComponent(i);
        Dimension d = comp.getMinimumSize();
        if (d.width > w) {
          w = d.width;
        }
        if (d.height > h) {
          h = d.height;
        }
      }
      return new Dimension(insets.left + insets.right + w + hgap * 2,
          insets.top + insets.bottom + h + vgap * 2);
    }
  }

  /**
   * Returns the maximum dimensions for this layout given the components
   * in the specified target container.
   * @param target the component which needs to be laid out
   * @see Container
   * @see #minimumLayoutSize
   * @see #preferredLayoutSize
   */
  public Dimension maximumLayoutSize(Container target)
  {
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  /**
   * Returns the alignment along the x axis.  This specifies how
   * the component would like to be aligned relative to other
   * components.  The value should be a number between 0 and 1
   * where 0 represents alignment along the origin, 1 is aligned
   * the furthest away from the origin, 0.5 is centered, etc.
   */
  public float getLayoutAlignmentX(Container parent)
  {
    return 0.5f;
  }

  /**
   * Returns the alignment along the y axis.  This specifies how
   * the component would like to be aligned relative to other
   * components.  The value should be a number between 0 and 1
   * where 0 represents alignment along the origin, 1 is aligned
   * the furthest away from the origin, 0.5 is centered, etc.
   */
  public float getLayoutAlignmentY(Container parent)
  {
    return 0.5f;
  }

  /**
   * Invalidates the layout, indicating that if the layout manager
   * has cached information it should be discarded.
   */
  public void invalidateLayout(Container target)
  {
  }

  /**
   * Lays out the specified container using this layout.
   * <p>
   * Each component in the <code>parent</code> container is reshaped
   * to be the size of the container, minus space for surrounding
   * insets, horizontal gaps, and vertical gaps.
   *
   * @param     parent the parent container in which to do the layout
   * @see       java.awt.Container#doLayout
   */
  public void layoutContainer(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();
      Component comp = null;

      for (int i = 0; i < ncomponents; i++) {
        comp = parent.getComponent(i);
        comp.setBounds(hgap + insets.left, vgap + insets.top,
            parent.getWidth() - (hgap * 2 + insets.left + insets.right),
            parent.getHeight() - (vgap * 2 + insets.top + insets.bottom));
      }
    }
  }

  /**
   * Make sure that the Container really has a layout of this type installed.
   * Otherwise havoc can ensue!
   */
  void checkLayout(Container parent)
  {
    if (parent.getLayout() != this) {
      throw new IllegalArgumentException("wrong parent for "+getClass().getName());
    }
  }

  /**
   * Returns a string representation of the state of this layout.
   * @return a string representation of this layout.
   */
  @Override
  public String toString()
  {
    return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + "]";
  }
}
