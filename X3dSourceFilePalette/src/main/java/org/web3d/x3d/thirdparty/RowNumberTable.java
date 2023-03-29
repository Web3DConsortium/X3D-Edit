package org.web3d.x3d.thirdparty;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * Use a JTable as a renderer for row numbers of a given main table. 
 * This table must be added to the row header of the scrollpane that contains 
 * the main table.
 * Retrieved 22 Jun 2012 from http://tips4java.wordpress.com/2008/11/18/row-number-table/
 */
public class RowNumberTable extends JTable implements ChangeListener, PropertyChangeListener
{
  private JTable main;

  public RowNumberTable(JTable table)
  {
    main = table;
    table.addPropertyChangeListener(RowNumberTable.this);

    setFocusable(false);
    setAutoCreateColumnsFromModel(false);
    setModel(main.getModel());
    setSelectionModel(main.getSelectionModel());
    setGridColor(Color.gray);
    
    TableColumn column = new TableColumn();
    column.setHeaderValue("row");
    addColumn(column);
    column.setCellRenderer(new RowNumberRenderer());

    Object o = getTableHeader().getDefaultRenderer();
    if(o instanceof JLabel)
      ((JLabel)o).setHorizontalAlignment(JLabel.CENTER);
    
    getColumnModel().getColumn(0).setPreferredWidth(50);
    setPreferredScrollableViewportSize(getPreferredSize());
  }

  @Override
  public void addNotify()
  {
    super.addNotify();

    Component c = getParent();

    //  Keep scrolling of the row table in sync with the main table.

    if (c instanceof JViewport) {
      JViewport viewport = (JViewport) c;
      viewport.addChangeListener(this);
    }
  }

  /*
   * Delegate method to main table
   */
  @Override
  public int getRowCount()
  {
    return main.getRowCount();
  }

  @Override
  public int getRowHeight(int row)
  {
    return main.getRowHeight(row);
  }

  /*
   * This table does not use any data from the main TableModel, so just return a value based on the row parameter.
   */
  @Override
  public Object getValueAt(int row, int column)
  {
    return Integer.toString(row + 1);
  }

  /*
   * Don't edit data in the main TableModel by mistake
   */
  @Override
  public boolean isCellEditable(int row, int column)
  {
    return false;
  }
//
//  Implement the ChangeListener
//

  @Override
  public void stateChanged(ChangeEvent e)
  {
    //  Keep the scrolling of the row table in sync with main table

    JViewport viewport = (JViewport) e.getSource();
    JScrollPane scrollPane = (JScrollPane) viewport.getParent();
    scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
  }
//
//  Implement the PropertyChangeListener
//

  @Override
  public void propertyChange(PropertyChangeEvent e)
  {
    //  Keep the row table in sync with the main table

    if ("selectionModel".equals(e.getPropertyName())) {
      setSelectionModel(main.getSelectionModel());
    }

    if ("model".equals(e.getPropertyName())) {
      setModel(main.getModel());
    }
  }

  /*
   * Borrow the renderer from JDK1.4.2 table header
   */
  private static class RowNumberRenderer extends JLabel implements TableCellRenderer
  {

    public RowNumberRenderer()
    {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      if (isSelected) {
        setFont(getFont().deriveFont(Font.BOLD+Font.ITALIC));
      }
      else
        setFont(getFont().deriveFont(Font.PLAIN));

      setText((value == null) ? "" : value.toString());
      setBackground(Color.lightGray);
      setHorizontalAlignment(JLabel.CENTER);
      setOpaque(true);
      return this;
    }
  }
}