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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * FieldRenderer.java
 * Created on April 16, 2008, 1:40 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class FieldRenderer extends DefaultListCellRenderer
{

  int wh;
  RedIndicator redMarker;
  FieldPan fldPan = new FieldPan("", "", "");

  FieldRenderer(RedIndicator redMarker)
  {
    this.redMarker = redMarker;
    fldPan.setOpaque(true);
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
  {
    fldPan.setComponentOrientation(list.getComponentOrientation());
    if (isSelected) {
      fldPan.setBackground(list.getSelectionBackground());
      fldPan.setForeground(list.getSelectionForeground());
    }
    else {
      fldPan.setBackground(list.getBackground());
      fldPan.setForeground(list.getForeground());
    }
    fldPan.setEnabled(list.isEnabled());
    fldPan.setFont(list.getFont());

    Border border = null;
    if (cellHasFocus) {
      if (isSelected) {
        border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
      }
      if (border == null) {
        border = UIManager.getBorder("List.focusCellHighlightBorder");
      }
    }
    else {
      border = new EmptyBorder(1, 1, 1, 1);
    }
    fldPan.setBorder(border);

    // Above is from Swing DefaultListCellRenderer

    if (value instanceof FieldPan) {
      FieldPan fp = (FieldPan) value;
      fldPan.setFieldAccess(fp.getFieldAccess());
      fldPan.setFieldName(fp.getFieldName());
      fldPan.setFieldType(fp.getFieldType());
      if (redMarker != null && redMarker.markFieldTypeRed(fp.getFieldType()))
        fldPan.setForeground(Color.red);
      else
        fldPan.setForeground(Color.black);
    }
    else if (value instanceof Component) {
      return (Component) value;
    }
    else {
      fldPan.setFieldAccess("");
      fldPan.setFieldName("");
      fldPan.setFieldType("");
    }
    return fldPan;
  }

  public static interface RedIndicator
  {

    public boolean markFieldTypeRed(String nm);
  }

  public static abstract class FieldBaseComboEditor extends JPanel implements ComboBoxEditor, CaretListener, FocusListener
  {
    protected JTextField tf;
    protected JLabel typeLab,  accessLab;

    public FieldBaseComboEditor()
    {
      tf = new JTextField();
      tf.setBorder(null);
      typeLab = new JLabel();
      typeLab.setFont(typeLab.getFont().deriveFont(Font.ITALIC));
      typeLab.setOpaque(true);
      accessLab = new JLabel();
      accessLab.setFont(typeLab.getFont());
      accessLab.setOpaque(true);

      setOpaque(true);
      setBorder(new LineBorder(Color.gray, 1));
      setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      add(tf);
      add(Box.createHorizontalGlue());
      add(typeLab);
      add(Box.createHorizontalGlue());
      add(accessLab);

      tf.addCaretListener(this);
      tf.addFocusListener(this);
    }

    // This is to enphasize that if you type something into the box, we know nothing
    // about its suitability or type.
    public void caretUpdate(CaretEvent e)
    {
      typeLab.setVisible(false);
      accessLab.setVisible(false);
    }

    // This will act like hitting return when the user clicks out of the editor
    // The return causes the actionlisteners to get hit, which is what the combo
    // box uses to know about entered text.
    public void focusGained(FocusEvent e)
    {
    }

    public void focusLost(FocusEvent e)
    {
      tf.postActionEvent();
    }

    public Component getEditorComponent()
    {
      return this;
    }

    public void addActionListener(ActionListener l)
    {
      tf.addActionListener(l);
    }

    public void removeActionListener(ActionListener l)
    {
      tf.removeActionListener(l);
    }

    public void selectAll()
    {
      tf.selectAll();
    }
  }

  public static class FieldEditor extends FieldBaseComboEditor //JTextField implements ComboBoxEditor
  {

    Object setObj;

    public void setItem(Object o)
    {
      setObj = o;
      if (o == null) {
        setObj = new FieldPan("", "unknown", "unknown");
        tf.setText("");
        typeLab.setText(((FieldPan) setObj).getFieldType() + " ");
        typeLab.setVisible(true);
        accessLab.setText(((FieldPan) setObj).getFieldAccess() + " ");
        accessLab.setVisible(true);
      }
      else if (o instanceof FieldPan) {
        tf.setText(((FieldPan) o).getFieldName());
        typeLab.setText(((FieldPan) o).getFieldType() + " ");
        typeLab.setVisible(true);
        accessLab.setText(((FieldPan) o).getFieldAccess() + " ");
        accessLab.setVisible(true);
      }
      else if (o instanceof JLabel) {
        tf.setText(((JLabel) o).getText());
        typeLab.setVisible(false);
        accessLab.setVisible(false);
      }
      else if (o instanceof String) {
        tf.setText(o.toString());
        typeLab.setVisible(false);
        accessLab.setVisible(false);
      }
    }

    public Object getItem()
    {
      if (setObj == null)
        return ("");

      String s = tf.getText().trim();

      if (setObj instanceof FieldPan) {
        FieldPan fp = (FieldPan) setObj;
        if (!fp.getFieldName().equals(s)) {
          fp = new FieldPan(s, "unknown", "unknown");
        }
        else {
          typeLab.setVisible(true);
          accessLab.setVisible(true);
        }
        return fp;
      }
      return null; // check for legal
    }
  }
}
