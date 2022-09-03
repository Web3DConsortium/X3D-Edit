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

import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import org.web3d.x3d.thirdparty.RowNumberTable;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;

/**
 * ExpandableElevationGridTable.java
 * Created on 25 Apr, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class ExpandableElevationGridTable extends javax.swing.JPanel implements TableModelListener
{
  private double    xSpacing,  zSpacing    = 1.0d;       // Use double precision for both ElevationGrid and GeoElevationGrid
  private String[] copiedRow, copiedColumn = null;       // Allow user to save values
  private boolean  geoElevationGrid        = false;      // whether supporting ElevationGrid or GeoElevationGrid

  // insertCommas, insertLineBreaks are handled by parent ELEVATIONGRIDCustomizer

  private final int      ASSIGN_OPERATION = 0;
  private final int         ADD_OPERATION = 1;
  private final int    SUBTRACT_OPERATION = 2;
  private final int    MULTIPLY_OPERATION = 3;
  private final int   DIVIDE_BY_OPERATION = 4;
  private final int DIVIDE_INTO_OPERATION = 5;
  private final int    ROUNDOFF_OPERATION = 6;
  private final int      NEGATE_OPERATION = 7;
  private       int    operationSelection = ASSIGN_OPERATION;

  private final int   SELECTED_CELL_OPERATION = 0;
  private final int    SELECTED_ROW_OPERATION = 1;
  private final int SELECTED_COLUMN_OPERATION = 2;
  private final int       ALL_CELLS_OPERATION = 3;
  private       int operationScope            = SELECTED_CELL_OPERATION;
  private double    operationValue            = 0.0d;
  
  public ExpandableElevationGridTable()
  {
    this (0,0,"12345"); // invoke full constructor
  }

  // TODO insert new initial column to provide index numbers
  
  /** Creates new form ExpandableTable
   * @param numColumns
   * @param numRows
   * @param unparsednumbers
   */
  public ExpandableElevationGridTable(int numColumns, int numRows, String unparsednumbers)
  {
    initComponents();
    
    // These three lines add a row "header" showing the row number
    JTable rowTable = new RowNumberTable(jTable);
    dataTableScrollPane.setRowHeaderView(rowTable);
    dataTableScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
    
    setData(numColumns, numRows, unparsednumbers);

    // http://edroidx.blogspot.com/2010/12/jcombobox-text-alignment.html
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.RIGHT);
    operationComboBox.setRenderer(dlcr);
    
    enableRowColumnEditButtons (); // check buttons enabled/disabled

    // jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // TODO figure out if selection of multiple cells is possible
    // TODO right justify cellSelectionComboBox
  }
  
  protected final void setData(int numColumns, int numRows, String unparsedNumbers)
  {
    jTable.setModel(createTableModel(numColumns,numRows,unparsedNumbers));
    
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment(JLabel.RIGHT); // consistent cell alignment
    jTable.setDefaultRenderer(Object.class, renderer);    // for data cells
    
    Object o = jTable.getTableHeader().getDefaultRenderer();
    if(o instanceof JLabel)
      ((JLabel)o).setHorizontalAlignment(JLabel.CENTER); // for header cells

    customizeColumnWidths();
    tableChanged(null); // update the labels; includes enableDisableButtons() invocation
  }
  public int getNumberColumns()
  {
    return jTable.getModel().getColumnCount();
  }
  
  public int getNumberRows()
  {
    return jTable.getModel().getRowCount();
  }
  
  public String getData()
  {
    StringBuilder sb = new StringBuilder();
    
    int nr = getNumberRows();
    int nc = getNumberColumns();
    TableModel tm = jTable.getModel();
    for(int r = 0; r<nr; r++) {
      for(int c=0; c<nc; c++) {
        String v = tm.getValueAt(r,c).toString();
        v = (v.length()<=0?"0":v);   // zero default
        sb.append(v);
        sb.append(" ");
      }
    }
    return sb.toString().trim();
  }
  
  // Assumes autoresize mode = off / scrollbar in use
  private void customizeColumnWidths()
  {
    TableColumnModel columnModel = jTable.getColumnModel();
    for (int c = 0; c < columnModel.getColumnCount(); c++) {
      TableColumn col = columnModel.getColumn(c);
      col.setMinWidth(defaultColumnWidth);
      col.setPreferredWidth(defaultColumnWidth);
      col.setMaxWidth(Integer.MAX_VALUE); // used to fill table width when no horz scroller
    }
  }
  private int defaultColumnWidth=100; // always holds something
  /**
   * If a horizontal scroll bar is desired (useHorizontalScroll=true, column width),
   * If not, (useHorizontalScroll=false, min col width)
   * Default is 2nd w/ min size = 100
   * @param useHorizontalScroll
   * @param defaultColumnWidth
   */
  public void setColumnWidthAndResizeStrategy(boolean useHorizontalScroll, int defaultColumnWidth)
  {
    this.defaultColumnWidth = defaultColumnWidth;
    if (useHorizontalScroll) {
      jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }
    else if (jTable.getAutoResizeMode() == JTable.AUTO_RESIZE_OFF)
      jTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS); // normal default

    customizeColumnWidths();
    tableChanged(null); // update the labels
//    doLayout();
  }

  private TableModel createTableModel(int ncols, int nrows, String data)
  {
    TableModel tm;
    if (ncols <= 0 || data == null || data.length() <= 0)
      tm = new DefaultTableModel(0, 0); //defltData,defltHeader);
    else {
      data = data.replace(",", " ");
      String[] sa = data.trim().split("\\s+"); // whitespace
      Vector<Vector<String>> master = new Vector<>();
      int i = 0;
      int r = 0;
      do {
        Vector<String> rowV = new Vector<>(ncols);
        for (int c = 0; c < ncols && i < sa.length; c++) {          
          rowV.add(sa[i++]);
        }
        master.add(rowV);
      }
      while (i < sa.length && ++r <nrows); // end of do-while
      Vector<String>hdr = new Vector<>();
      for(i=1;i<=ncols;i++)
        hdr.add("col " + i);
      tm = new DefaultTableModel(master, hdr);
    }
    tm.addTableModelListener(this);

    return tm;
  }
 
  @Override
  public void tableChanged(TableModelEvent e)
  {
    TableModel tm = jTable.getModel();
    int   cols  = tm.getColumnCount();
    int   rows  = tm.getRowCount();
    double width = (cols-1)*getXSpacing();
    double depth = (rows-1)*getZSpacing();
    if (cols == 0) width = 0.0f;
    if (rows == 0) depth = 0.0f;
    numberColumnsDisplayLabel.setText(""+cols);
       numberRowsDisplayLabel.setText(""+rows);
         totalLabel.setText("Geometry size ("+cols+" columns)*("+rows+" rows) = "+(cols*rows)+" vertices  =" + ((cols-1)*(rows-1))+ " quadrilaterals");
    dimensionsLabel.setText("Grid size (x width "+width+"m)*(z depth "+depth+"m) = area of "+(width*depth)+" square meters");
    enableRowColumnEditButtons (); // check buttons enabled/disabled
  }
 
  public void setTitle(String s)
  {
    Border b = getBorder();
    if(b instanceof TitledBorder) {
      ((TitledBorder)b).setTitle(s);
    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        headPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        totalLabel = new javax.swing.JLabel();
        dimensionsLabel = new javax.swing.JLabel();
        editColumnButtonPanel = new javax.swing.JPanel();
        copyColumnButton = new javax.swing.JButton();
        addColumnButton = new javax.swing.JButton();
        removeColumnButton = new javax.swing.JButton();
        moveColumnLeftButton = new javax.swing.JButton();
        moveColumnRightButton = new javax.swing.JButton();
        numberColumnsDisplayLabel = new javax.swing.JLabel();
        numColumnsDescriptionLabel = new javax.swing.JLabel();
        dataTableScrollPane = new javax.swing.JScrollPane();
        jTable = new JTable(new javax.swing.table.DefaultTableModel())
        // custom JTable creation code for tooltip generation, adapted from
        // http://download.oracle.com/javase/tutorial/uiswing/components/table.html#celltooltip
        // http://download.oracle.com/javase/tutorial/uiswing/examples/components/TableToolTipsDemoProject/src/components/TableToolTipsDemo.java
        {
            /**
            * Implement custom table cell tool tips
            */
            @Override
            public String getToolTipText(MouseEvent e)
            {
                java.awt.Point p = e.getPoint();
                int rowIndex =    rowAtPoint(p);
                int colIndex = columnAtPoint(p);

                StringBuilder tooltip = new StringBuilder();
                tooltip.append("cell [")
                .append(rowIndex)
                .append(" ")
                .append(colIndex)
                .append("]  offset distance [")
                .append(colIndex*getXSpacing())
                .append(" ")
                .append(rowIndex*getZSpacing())
                .append("]");

                // TODO add computation of geographic offset
                //        if (isGeoElevationGrid())
                //            tooltip.append(", location [")
                //                   .append(rowIndex*getXSpacing())
                //                   .append(" ")
                //                   .append(colIndex*getXSpacing())
                //                   .append("]");
                return tooltip.toString();
            }
        };
        footPanel = new javax.swing.JPanel();
        editCellsPanel = new javax.swing.JPanel();
        operationComboBox = new javax.swing.JComboBox<>();
        cellSelectionComboBox = new javax.swing.JComboBox<>();
        editFactorTextField = new javax.swing.JTextField();
        applyCellFactorButton = new javax.swing.JButton();
        editRowButtonPanel = new javax.swing.JPanel();
        copyRowButton = new javax.swing.JButton();
        addRowButton = new javax.swing.JButton();
        removeRowButton = new javax.swing.JButton();
        moveRowUpButton = new javax.swing.JButton();
        moveRowDownButton = new javax.swing.JButton();
        numberRowsDisplayLabel = new javax.swing.JLabel();
        numberRowsDescriptionLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(900, 400));
        setLayout(new java.awt.GridBagLayout());

        headPanel.setLayout(new java.awt.GridBagLayout());

        leftPanel.setLayout(new java.awt.GridBagLayout());

        totalLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableTable.totalLabel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        leftPanel.add(totalLabel, gridBagConstraints);

        dimensionsLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.dimensionsLabel.text")); // NOI18N
        dimensionsLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.dimensionsLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        leftPanel.add(dimensionsLabel, gridBagConstraints);

        headPanel.add(leftPanel, new java.awt.GridBagConstraints());

        editColumnButtonPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, NbBundle.getMessage(getClass(), "ExpandableElevationGridTable.editColumnButtonPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP)); // NOI18N
        editColumnButtonPanel.setLayout(new java.awt.GridBagLayout());

        copyColumnButton.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.copyColumnButton.text")); // NOI18N
        copyColumnButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COPY_COLUMN_BUTTON"));
        copyColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyColumnButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        editColumnButtonPanel.add(copyColumnButton, gridBagConstraints);

        addColumnButton.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.addColumnButton.text")); // NOI18N
        addColumnButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_ADD_COLUMN_BUTTON"));
        addColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addColumnButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        editColumnButtonPanel.add(addColumnButton, gridBagConstraints);

        removeColumnButton.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.removeColumnButton.text")); // NOI18N
        removeColumnButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_DELETE_COLUMN_BUTTON"));
        removeColumnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeColumnButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        editColumnButtonPanel.add(removeColumnButton, gridBagConstraints);

        moveColumnLeftButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/leftArrow.png"))); // NOI18N
        moveColumnLeftButton.setText(NbBundle.getMessage(getClass(), "ExpandableElevationGridTable.moveColumnLeftButton.text")); // NOI18N
        moveColumnLeftButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COLUMN_LEFT_BUTTON"));
        moveColumnLeftButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        moveColumnLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveColumnLeftButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        editColumnButtonPanel.add(moveColumnLeftButton, gridBagConstraints);

        moveColumnRightButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/rightArrow.png"))); // NOI18N
        moveColumnRightButton.setText(NbBundle.getMessage(getClass(), "ExpandableElevationGridTable.moveColumnRightButton.text")); // NOI18N
        moveColumnRightButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COLUMN_RIGHT_BUTTON"));
        moveColumnRightButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        moveColumnRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveColumnRightButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        editColumnButtonPanel.add(moveColumnRightButton, gridBagConstraints);

        numberColumnsDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numberColumnsDisplayLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.numberColumnsDisplayLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        editColumnButtonPanel.add(numberColumnsDisplayLabel, gridBagConstraints);

        numColumnsDescriptionLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.numColumnsDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        editColumnButtonPanel.add(numColumnsDescriptionLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        headPanel.add(editColumnButtonPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(headPanel, gridBagConstraints);

        dataTableScrollPane.setBorder(null);

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable.setCellSelectionEnabled(true);
        jTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTableFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTableFocusLost(evt);
            }
        });
        dataTableScrollPane.setViewportView(jTable);
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.jTable.columnModel.title0")); // NOI18N
            jTable.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.jTable.columnModel.title1")); // NOI18N
            jTable.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.jTable.columnModel.title2")); // NOI18N
            jTable.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.jTable.columnModel.title3")); // NOI18N
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(dataTableScrollPane, gridBagConstraints);

        footPanel.setLayout(new java.awt.GridBagLayout());

        editCellsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.editCellsPanel.border.title_1"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
        editCellsPanel.setLayout(new java.awt.GridBagLayout());

        operationComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Assign cell value:", "Add factor:", "Subtract factor:", "Multiply by factor:", "Divide by factor:", "Divide into factor:", "Roundoff cell value", "Negate cell value" }));
        operationComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.operationComboBox.toolTipText_1")); // NOI18N
        operationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        editCellsPanel.add(operationComboBox, gridBagConstraints);

        cellSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "to selected cell", "to selected row", "to selected column", "to all cells" }));
        cellSelectionComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.cellSelectionComboBox.toolTipText_1")); // NOI18N
        cellSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cellSelectionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        editCellsPanel.add(cellSelectionComboBox, gridBagConstraints);

        editFactorTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        editFactorTextField.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.editFactorTextField.text_1")); // NOI18N
        editFactorTextField.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.editFactorTextField.toolTipText")); // NOI18N
        editFactorTextField.setMinimumSize(new java.awt.Dimension(100, 20));
        editFactorTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        editFactorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editFactorTextFieldActionPerformed(evt);
            }
        });
        editFactorTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                editFactorTextFieldFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        editCellsPanel.add(editFactorTextField, gridBagConstraints);

        applyCellFactorButton.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.applyCellFactorButton.text_1")); // NOI18N
        applyCellFactorButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.applyCellFactorButton.toolTipText_1")); // NOI18N
        applyCellFactorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyCellFactorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        editCellsPanel.add(applyCellFactorButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        footPanel.add(editCellsPanel, gridBagConstraints);

        editRowButtonPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, NbBundle.getMessage(getClass(), "ExpandableElevationGridTable.editRowButtonPanel.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP)); // NOI18N
        editRowButtonPanel.setLayout(new java.awt.GridBagLayout());

        copyRowButton.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.copyRowButton.text")); // NOI18N
        copyRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COPY_BUTTON"));
        copyRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyRowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        editRowButtonPanel.add(copyRowButton, gridBagConstraints);

        addRowButton.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.addRowButton.text")); // NOI18N
        addRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_ADD_BUTTON"));
        addRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        editRowButtonPanel.add(addRowButton, gridBagConstraints);

        removeRowButton.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.removeRowButton.text")); // NOI18N
        removeRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_DELETE_BUTTON"));
        removeRowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeRowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        editRowButtonPanel.add(removeRowButton, gridBagConstraints);

        moveRowUpButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/upArrow.png"))); // NOI18N
        moveRowUpButton.setText(NbBundle.getMessage(getClass(), "ExpandableElevationGridTable.moveRowUpButton.text")); // NOI18N
        moveRowUpButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_UP_BUTTON"));
        moveRowUpButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        moveRowUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveRowUpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        editRowButtonPanel.add(moveRowUpButton, gridBagConstraints);

        moveRowDownButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/downArrow.png"))); // NOI18N
        moveRowDownButton.setText(NbBundle.getMessage(getClass(), "ExpandableElevationGridTable.moveRowDownButton.text")); // NOI18N
        moveRowDownButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_DOWN_BUTTON"));
        moveRowDownButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        moveRowDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveRowDownButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        editRowButtonPanel.add(moveRowDownButton, gridBagConstraints);

        numberRowsDisplayLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numberRowsDisplayLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.numberRowsDisplayLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        editRowButtonPanel.add(numberRowsDisplayLabel, gridBagConstraints);

        numberRowsDescriptionLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableElevationGridTable.class, "ExpandableElevationGridTable.numberRowsDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        editRowButtonPanel.add(numberRowsDescriptionLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        footPanel.add(editRowButtonPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(footPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private void removeColumnButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeColumnButtonActionPerformed
  {//GEN-HEADEREND:event_removeColumnButtonActionPerformed
    if(getNumberColumns()<=0)
      return;
    int selectedColumn = jTable.getSelectedColumn();  // might be -1... remove last column if so
    if (selectedColumn == -1) 
        selectedColumn = getNumberColumns();
    // TODO shift columns left by copying values right-to=left to overwrite selected column
    ((DefaultTableModel)jTable.getModel()).setColumnCount(getNumberColumns()-1);

    customizeColumnWidths();
    tableChanged(null); // update the labels
    enableRowColumnEditButtons (); // check buttons enabled/disabled
}//GEN-LAST:event_removeColumnButtonActionPerformed

  private void addColumnButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addColumnButtonActionPerformed
  {//GEN-HEADEREND:event_addColumnButtonActionPerformed
    int  selectedColumn = jTable.getSelectedColumn() + 1;
    // getSelectedColumn() might have been -1, i.e. unselected... reset to add after last column if so
    if ((selectedColumn <= 0) || (selectedColumn > getNumberColumns()))
         selectedColumn = getNumberColumns(); // ensure legal value
    String[] oa = new String[Math.max(getNumberRows(),1)];
    Arrays.fill(oa, "0");
    int numberColumns = getNumberColumns();
    ((DefaultTableModel) jTable.getModel()).addColumn("col "+(numberColumns+1),oa);  // 1-based counting
    numberColumns = getNumberColumns(); // should now be bigger by one
    // jTable addColumn() method only adds to end, so now must move over column values if inserting into middle
    for (int column = numberColumns-1; column > selectedColumn; column--)
    {
      for (int row=0; row < jTable.getModel().getRowCount(); row++)
      {
          jTable.setValueAt (jTable.getValueAt(row, column-1), row, column);
      }
    }
    // ensure that column is only copied if size matches, recalculate each time since it may change
    if ((copiedColumn != null) && (copiedColumn.length != jTable.getModel().getRowCount()))
    {
          // mismatch, clear copiedColumn buffer
          copiedColumn = null;
          copyRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COPY_BUTTON"));
           addRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_ADD_BUTTON"));
    }
    for (int row=0; row < jTable.getModel().getRowCount(); row++)
    {
      if (copiedColumn != null)
          jTable.setValueAt (copiedColumn[row], row, selectedColumn);
      else
          jTable.setValueAt ("0", row, selectedColumn); // need to rezero column inserted into middle
    }
    customizeColumnWidths();
    tableChanged(null); // update the labels
    enableRowColumnEditButtons (); // check buttons enabled/disabled
  }//GEN-LAST:event_addColumnButtonActionPerformed

  private void addRowButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addRowButtonActionPerformed
  {//GEN-HEADEREND:event_addRowButtonActionPerformed
    DefaultTableModel mod = (DefaultTableModel)jTable.getModel();
    if(getNumberRows()<=0)
    {
        String[] oa = new String[Math.max(getNumberColumns(),1)];
        Arrays.fill(oa, "0");
        if(oa.length<=1) {
         mod.setColumnCount(1);
         mod.setColumnIdentifiers(new Object[]{"col 1"});
        }

      mod.addRow(oa);
    }
    else {
      int  selectedRow = jTable.getSelectedRow();  // might be -1...add to bottom if so
      if ((selectedRow < 0) || (selectedRow > getNumberRows()-1))
           selectedRow = getNumberRows()-1;
      String[] oa = new String[getNumberColumns()];
      Arrays.fill(oa, "0");
      // append after selected row
      mod.insertRow(selectedRow + 1, oa);
      // ensure that row is only copied if size matches, recalculate each time since it may change
      if ((copiedRow != null) && (copiedRow.length != mod.getColumnCount()))
      {
          // mismatch, clear copiedRow buffer
          copiedRow = null;
          copyRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COPY_BUTTON"));
           addRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_ADD_BUTTON"));
      }
      for (int column = 0; column < mod.getColumnCount(); column++)
      {
          if (copiedRow != null)
              jTable.setValueAt (copiedRow[column], selectedRow + 1, column);
      }
    }
    enableRowColumnEditButtons (); // check buttons enabled/disabled
  }//GEN-LAST:event_addRowButtonActionPerformed

  private void removeRowButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeRowButtonActionPerformed
  {//GEN-HEADEREND:event_removeRowButtonActionPerformed
    int selectedRow = jTable.getSelectedRow();
    if (jTable.getModel().getRowCount() <= 0)
      return;
    if (selectedRow == -1) selectedRow = jTable.getModel().getRowCount() - 1;
    ((DefaultTableModel) jTable.getModel()).removeRow(selectedRow);
    enableRowColumnEditButtons (); // check buttons enabled/disabled
  }//GEN-LAST:event_removeRowButtonActionPerformed

  private void copyRowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyRowButtonActionPerformed
    String newCopiedRowValue = new String();
    int row = jTable.getSelectedRow();
    newCopiedRowValue += "row[" + row + "] = ";
    if ((row < 0) || (row >= jTable.getModel().getRowCount()))
    {
        return;
    }
    if ((copiedRow == null) || (copiedRow.length != jTable.getModel().getColumnCount()))
        copiedRow = new String[jTable.getModel().getColumnCount()];
    int tooltipColumnsTotal = 5; // fixed small value
    for (int column=0; column < jTable.getModel().getColumnCount(); column++)
    {
        copiedRow[column] = jTable.getModel().getValueAt(row, column).toString();
        if (column <= tooltipColumnsTotal)
            newCopiedRowValue += " " + copiedRow[column];
    }
    boolean moreDataRemaining = false;
    if (jTable.getModel().getColumnCount() > tooltipColumnsTotal + 1)
        moreDataRemaining = true;
    if (moreDataRemaining) newCopiedRowValue += " ...";
    copyRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COPY_BUTTON") + "<br />" + newCopiedRowValue);
     addRowButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_ADD_BUTTON")  + "<br />" + newCopiedRowValue);
  }//GEN-LAST:event_copyRowButtonActionPerformed

  private void copyColumnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyColumnButtonActionPerformed
    String newCopiedColumnValue = new String();
    int column = jTable.getSelectedColumn();
    newCopiedColumnValue += "column[" + (column + 1) + "] = ";
    if ((column < 0) || (column >= jTable.getModel().getColumnCount()))
    {
        return;
    }
    if ((copiedColumn == null) || (copiedColumn.length != jTable.getModel().getRowCount()))
        copiedColumn = new String[jTable.getModel().getRowCount()];
    int tooltipColumnsTotal = 5; // fixed small value
    for (int row=0; row < jTable.getModel().getRowCount(); row++)
    {
        copiedColumn[row] = jTable.getModel().getValueAt(row, column).toString();
        if (row <= tooltipColumnsTotal)
            newCopiedColumnValue += " " + copiedColumn[row];
    }
    boolean moreDataRemaining = false;
    if (jTable.getModel().getColumnCount() > tooltipColumnsTotal + 1)
        moreDataRemaining = true;
    if (moreDataRemaining) newCopiedColumnValue += " ...";
    copyColumnButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COPY_COLUMN_BUTTON") + "<br />" + newCopiedColumnValue);
     addColumnButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_ADD_COLUMN_BUTTON")  + "<br />" + newCopiedColumnValue);
  }//GEN-LAST:event_copyColumnButtonActionPerformed

  private void operationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operationComboBoxActionPerformed
      switch (operationComboBox.getSelectedIndex()) {
          case ASSIGN_OPERATION:
              operationSelection =      ASSIGN_OPERATION;
              break;
          case ADD_OPERATION:
              operationSelection =         ADD_OPERATION;
              break;
          case SUBTRACT_OPERATION:
              operationSelection =    SUBTRACT_OPERATION;
              break;
          case MULTIPLY_OPERATION:
              operationSelection =    MULTIPLY_OPERATION;
              break;
          case DIVIDE_BY_OPERATION:
              operationSelection =   DIVIDE_BY_OPERATION;
              break;
          case DIVIDE_INTO_OPERATION:
              operationSelection = DIVIDE_INTO_OPERATION;
              break;
          case ROUNDOFF_OPERATION:
              operationSelection =    ROUNDOFF_OPERATION;
              break;
          case NEGATE_OPERATION:
              operationSelection =      NEGATE_OPERATION;
              break;
          default:
              System.out.println("ExpandableElevationGridTable error:  illegal selection (" + operationComboBox.getSelectedIndex() + ") for operationComboBox");
              break;
      }

        if ((operationSelection == ROUNDOFF_OPERATION) || (operationSelection == NEGATE_OPERATION))
             editFactorTextField.setEnabled(false); // unneeded entry field
        else editFactorTextField.setEnabled(true);  //   needed entry field
}//GEN-LAST:event_operationComboBoxActionPerformed

  private void cellSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cellSelectionComboBoxActionPerformed
      switch (cellSelectionComboBox.getSelectedIndex()) {
          case SELECTED_CELL_OPERATION:
              operationScope =   SELECTED_CELL_OPERATION;
              break;
          case SELECTED_ROW_OPERATION:
              operationScope =    SELECTED_ROW_OPERATION;
              break;
          case SELECTED_COLUMN_OPERATION:
              operationScope = SELECTED_COLUMN_OPERATION;
              break;
          case ALL_CELLS_OPERATION:
              operationScope =       ALL_CELLS_OPERATION;
              break;
          default:
              System.out.println("ExpandableElevationGridTable error:  illegal selection cellSelectionComboBox");
              break;
      }
  }//GEN-LAST:event_cellSelectionComboBoxActionPerformed

  private void editFactorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editFactorTextFieldActionPerformed
    checkEditFactorText ();
  }//GEN-LAST:event_editFactorTextFieldActionPerformed

  private void checkEditFactorText ()
  {
       String cellContents = "";
       if (operationSelection == ROUNDOFF_OPERATION) return;
       try
       {
           cellContents = editFactorTextField.getText().trim();
           operationValue = (new SFDouble(cellContents)).getValue();
       }
       catch (Exception e)
       {
           String warningMessage;
           if (cellContents.trim().length()==0)
                warningMessage = "No value provided to edit cells, please retry";
           else warningMessage = "Illegal value (" + cellContents + "), please retry";
           NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    warningMessage, NotifyDescriptor.WARNING_MESSAGE);
           DialogDisplayer.getDefault().notify(descriptor);
           System.out.println(warningMessage);
       }
  }
  private void applyCellFactorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyCellFactorButtonActionPerformed
      String cellContents;
      double  cellValue;

      checkEditFactorText (); // reread, recheck field in case of problems
      if ((editFactorTextField.getText().trim().length() == 0) &&
          (operationSelection != ROUNDOFF_OPERATION) &&
          (operationSelection != NEGATE_OPERATION))
      {
          return; // no operation since no factor value provided
      }
      if ((operationValue==0.0f) && (operationSelection == DIVIDE_BY_OPERATION))
      {
           NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "Division by zero not allowed", NotifyDescriptor.WARNING_MESSAGE);
           DialogDisplayer.getDefault().notify(descriptor);
           return; // nop
      }
      for (int column = 0; column < jTable.getModel().getColumnCount(); column++)
      {
          for (int row = 0; row < jTable.getModel().getRowCount(); row++)
          {
              if ( (operationScope ==       ALL_CELLS_OPERATION) ||
                  ((operationScope ==   SELECTED_CELL_OPERATION) && (jTable.isCellSelected(row, column) || (jTable.isRowSelected(row) && jTable.isColumnSelected(column)))) ||
                  ((operationScope ==    SELECTED_ROW_OPERATION) &&  jTable.isRowSelected(row))      ||
                  ((operationScope == SELECTED_COLUMN_OPERATION) &&  jTable.isColumnSelected(column)))
              {
                   TableModel tableModel = jTable.getModel();
                   cellContents = tableModel.getValueAt(row,column).toString();
                   
                   // ensure valid cell value determined
                   try
                   {
                       cellValue = (new SFDouble(cellContents)).getValue();
                   }
                   catch (Exception e)
                   {
                       String warningMessage;
                       if (cellContents.length()==0)
                            warningMessage = "No value provided for cell[" + row + "][" + column + "], please retry";
                       else warningMessage = "Illegal value (" + cellContents + ") for cell[" + row + "][" + column + "], please retry";
                       NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                                warningMessage, NotifyDescriptor.WARNING_MESSAGE);
                       DialogDisplayer.getDefault().notify(descriptor);
                       System.out.println(warningMessage);
                       return;
                   }
                   if ((cellValue == 0.0f) && (operationSelection == DIVIDE_INTO_OPERATION))
                   {
                        NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                                "Division by zero not allowed,\ncell[" + row + "][" + column + "] unchanged", NotifyDescriptor.WARNING_MESSAGE);
                        DialogDisplayer.getDefault().notify(descriptor);
                        continue;
                   }
                   else if (operationSelection == ASSIGN_OPERATION)
                   {
                       cellValue = operationValue;
                   }
                   else if (operationSelection == ADD_OPERATION)
                   {
                       cellValue += operationValue;
                   }
                   else if (operationSelection == SUBTRACT_OPERATION)
                   {
                       cellValue -= operationValue;
                   }
                   else if (operationSelection == MULTIPLY_OPERATION)
                   {
                       cellValue *= operationValue;
                   }
                   else if (operationSelection == DIVIDE_BY_OPERATION)
                   {
                       cellValue /= operationValue;
                   }
                   else if (operationSelection == DIVIDE_INTO_OPERATION)
                   {
                       cellValue = operationValue / cellValue;
                   }
                   else if (operationSelection == ROUNDOFF_OPERATION) // to nearest centimeter
                   {
                       // TODO use operationValue for number of places
                       cellValue = Math.round(cellValue * 100.0f) / 100.0f;
                   }
                    else if (operationSelection == NEGATE_OPERATION) {
                        cellValue = -1.0f *  cellValue;
                    }
                   cellContents =  Double.toString(cellValue);
                   while (cellContents.contains(".") && cellContents.endsWith("0")) // clean up
                   {
                          cellContents = cellContents.substring(0, cellContents.length()-1);
                   }
                   if    (cellContents.endsWith(".")) // clean up
                          cellContents = cellContents.substring(0, cellContents.length()-1);
                   tableModel.setValueAt(cellContents,row,column);
              }
          }
      }
  }//GEN-LAST:event_applyCellFactorButtonActionPerformed

  private void editFactorTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editFactorTextFieldFocusLost
      checkEditFactorText ();
  }//GEN-LAST:event_editFactorTextFieldFocusLost

  private void moveColumnLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveColumnLeftButtonActionPerformed
      int column = jTable.getSelectedColumn();
      if (column <=0) // unselected or initial row
          return;
      for (int row=0; row < jTable.getModel().getRowCount(); row++) // swap values
      {
          Object hold =     jTable.getValueAt(              row, column-1);
          jTable.setValueAt(jTable.getValueAt(row, column), row, column-1);
          jTable.setValueAt(hold,             row, column);
      }
      // TODO how to reset selection?
      enableRowColumnEditButtons (); // check buttons enabled/disabled
  }//GEN-LAST:event_moveColumnLeftButtonActionPerformed

  private void moveColumnRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveColumnRightButtonActionPerformed
      int column = jTable.getSelectedColumn();
      if ((column < 0) || (column >= jTable.getModel().getColumnCount())) // unselected or final row
          return;
      for (int row=0; row < jTable.getModel().getRowCount(); row++) // swap values
      {
          Object hold =     jTable.getValueAt(                row, column);
          jTable.setValueAt(jTable.getValueAt(row, column+1), row, column);
          jTable.setValueAt(hold,             row, column+1);
      }
      // TODO how to reset selection?
      enableRowColumnEditButtons (); // check buttons enabled/disabled
}//GEN-LAST:event_moveColumnRightButtonActionPerformed

  private void moveRowUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveRowUpButtonActionPerformed
      int row = jTable.getSelectedRow();
      if (row <=0) // unselected or initial row
          return;
      jTable.getSelectionModel().setLeadSelectionIndex(row); //
      ((DefaultTableModel)jTable.getModel()).moveRow(row,row,row-1);
      for (int column=0; column < jTable.getModel().getColumnCount(); column++)
      {
          Object hold =     jTable.getValueAt(row,   column);
          jTable.setValueAt(jTable.getValueAt(row-1, column), row,   column);
          jTable.setValueAt(hold,                             row-1, column);
      }
      jTable.getSelectionModel().setSelectionInterval(row-1, row-1);
      jTable.scrollRectToVisible(jTable.getCellRect(row-1, 0, true));
      enableRowColumnEditButtons (); // check buttons enabled/disabled
  }//GEN-LAST:event_moveRowUpButtonActionPerformed

  private void moveRowDownButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveRowDownButtonActionPerformed
      int row = jTable.getSelectedRow();
      if (row == -1 || row >= (jTable.getModel().getRowCount() - 1)) // unselected or final row
          return;

      ((DefaultTableModel)jTable.getModel()).moveRow(row,row,row+1);
      for (int column=0; column < jTable.getModel().getColumnCount(); column++)
      {
          Object hold =     jTable.getValueAt(row,   column);
          jTable.setValueAt(jTable.getValueAt(row+1, column), row,   column);
          jTable.setValueAt(hold,                             row+1, column);
      }
      jTable.getSelectionModel().setSelectionInterval(row+1, row+1);
      jTable.scrollRectToVisible(jTable.getCellRect(row+1, 0, true));
      enableRowColumnEditButtons (); // check buttons enabled/disabled
}//GEN-LAST:event_moveRowDownButtonActionPerformed

  private void jTableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableFocusGained
      enableRowColumnEditButtons (); // check buttons enabled/disabled
  }//GEN-LAST:event_jTableFocusGained

  private void jTableFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableFocusLost
      enableRowColumnEditButtons (); // check buttons enabled/disabled
  }//GEN-LAST:event_jTableFocusLost
    private void enableRowColumnEditButtons ()
    {
        if (jTable.getSelectedRow() > -1) // any row
        {
              copyRowButton.setEnabled(true);
            // addRowButton always enabled, no selection defaults to end
            removeRowButton.setEnabled(true);
        }
        else // none selected
        {
              copyRowButton.setEnabled(false);
            // addRowButton always enabled, no selection defaults to end
            removeRowButton.setEnabled(false);
        }
        if (jTable.getSelectedRow() > 0) // second row or after
            moveRowUpButton.setEnabled(true);
        else if (jTable.getSelectedRow() == 0) // first row
            moveRowUpButton.setEnabled(false);

        if ((jTable.getSelectedRow() >= 0) && (jTable.getSelectedRow() < jTable.getModel().getRowCount() - 1)) // any but last row
            moveRowDownButton.setEnabled(true);
        else if (jTable.getSelectedRow() ==  (jTable.getModel().getRowCount() - 1)) // last row
            moveRowDownButton.setEnabled(false);

        if (jTable.getSelectedColumn() > -1) // any column
        {
              copyColumnButton.setEnabled(true);
            // addColumnButton always enabled, no selection defaults to end
            removeColumnButton.setEnabled(true);
        }
        else // none selected
        {
              copyColumnButton.setEnabled(false);
            // addColumnButton always enabled, no selection defaults to end
            removeColumnButton.setEnabled(false);
        }
        if (jTable.getSelectedColumn() > 0) // second column or after
            moveColumnLeftButton.setEnabled(true);
        else if (jTable.getSelectedColumn() == 0) // first column
            moveColumnLeftButton.setEnabled(false);

        if ((jTable.getSelectedColumn() >= 0) && (jTable.getSelectedColumn() < jTable.getModel().getColumnCount() - 1)) // any but last column
            moveColumnRightButton.setEnabled(true);
        else if (jTable.getSelectedColumn() == jTable.getModel().getColumnCount() - 1) // last column
            moveColumnRightButton.setEnabled(false);
        jTable.repaint(); // Repaint the component
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addColumnButton;
    private javax.swing.JButton addRowButton;
    private javax.swing.JButton applyCellFactorButton;
    private javax.swing.JComboBox<String> cellSelectionComboBox;
    private javax.swing.JButton copyColumnButton;
    private javax.swing.JButton copyRowButton;
    private javax.swing.JScrollPane dataTableScrollPane;
    private javax.swing.JLabel dimensionsLabel;
    private javax.swing.JPanel editCellsPanel;
    private javax.swing.JPanel editColumnButtonPanel;
    private javax.swing.JTextField editFactorTextField;
    private javax.swing.JPanel editRowButtonPanel;
    private javax.swing.JPanel footPanel;
    private javax.swing.JPanel headPanel;
    private javax.swing.JTable jTable;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton moveColumnLeftButton;
    private javax.swing.JButton moveColumnRightButton;
    private javax.swing.JButton moveRowDownButton;
    private javax.swing.JButton moveRowUpButton;
    private javax.swing.JLabel numColumnsDescriptionLabel;
    private javax.swing.JLabel numberColumnsDisplayLabel;
    private javax.swing.JLabel numberRowsDescriptionLabel;
    private javax.swing.JLabel numberRowsDisplayLabel;
    private javax.swing.JComboBox<String> operationComboBox;
    private javax.swing.JButton removeColumnButton;
    private javax.swing.JButton removeRowButton;
    private javax.swing.JLabel totalLabel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the xSpacing
     */
    public double getXSpacing() {
        return xSpacing;
    }

    /**
     * @param xSpacing the xSpacing to set
     */
    public void setXSpacing(double xSpacing) {
        this.xSpacing = xSpacing;
    }

    /**
     * @return the zSpacing
     */
    public double getZSpacing() {
        return zSpacing;
    }

    /**
     * @param zSpacing the zSpacing to set
     */
    public void setZSpacing(double zSpacing) {
        this.zSpacing = zSpacing;
    }

    /**
     * @return  whether table supports GeoElevationGrid (true) or else ElevationGrid (false)
     */
    public boolean isGeoElevationGrid() {
        return geoElevationGrid;
    }

    /**
     * @param geoElevationGrid whether table supports GeoElevationGrid (true) or else ElevationGrid (false)
     */
    public void setGeoElevationGrid(boolean geoElevationGrid) {
        this.geoElevationGrid = geoElevationGrid;
    }
  
}
