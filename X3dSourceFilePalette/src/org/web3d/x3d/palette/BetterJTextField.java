package org.web3d.x3d.palette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * BetterJTextField.java
 * Created on Oct 13, 2008
 *
 * A simple override which fires an action event (the standard event for text components
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey / jmbailey@nps.edu
 * @version $Id$
 */
public class BetterJTextField extends JTextField implements DocumentListener,FocusListener,ActionListener
{
  private boolean changed = false;
  private String old="";
  public PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  public BetterJTextField()
  {
    this.getDocument().addDocumentListener(this);
    this.addFocusListener(this);
    this.addActionListener(this);
  }

  public BetterJTextField(int cols)
  {
    this();
    setColumns(cols);
  }

  public void changedUpdate(DocumentEvent e)
  {
    changed = true;
  }

  public void insertUpdate(DocumentEvent e)
  {
    changed = true;
  }

  public void removeUpdate(DocumentEvent e)
  {
    changed = true;
  }

  public void focusGained(FocusEvent e)
  {
    changed = false;
    old = this.getText();
  }

  public void focusLost(FocusEvent e)
  {
    actionPerformed(null);
  }

  // Return hit
  public void actionPerformed(ActionEvent e)
  {
    if(changed && !old.equals(getText().trim())) {
      pcs.firePropertyChange(new PropertyChangeEvent(this,"value",old,getText()));
      changed = false;
    }

  }

}
