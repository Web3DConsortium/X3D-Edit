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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;

/**
 * ExpandableTable.java
 * Created on 25 Apr, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class ExpandableKeyTupleTable extends javax.swing.JPanel implements TableModelListener
{
  private int tupleSize    = 3;  // overridden by constructor
  private int numberTuples = 1;  // x-dimension
  private int numberKeys   = 1;  // y-dimension
  private String[] defaultTupleValues = new String[]{"0","0","0"}; // value needs to be updated by customizer using this panel
  private boolean  keyColumnIncluded  = true;
  private boolean doIndex             = true; // unvarying
  private boolean doNormals           = false;

  private boolean insertCommas, insertLineBreaks = false;

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
  private float     operationValue            = 0.0f;

  public ExpandableKeyTupleTable()
  {
    initialize(tupleSize, numberTuples, numberKeys, "1", "0 0 0");
  }

  public ExpandableKeyTupleTable(int tupleSize, int numTuples, int numKeys, String unparsedKeys, String unparsedTuples)
  {
    initialize(tupleSize, numTuples, numKeys, unparsedKeys, unparsedTuples);
  }

  private void initialize(int tupleSize, int numTuples, int numKeys, String unparsedKeys, String unparsedTuples)
  {
    initComponents();
    this.tupleSize = tupleSize;
    
    setData(tupleSize, numTuples, numKeys, unparsedKeys, unparsedTuples);
    setSpecialRenderers();

    // http://edroidx.blogspot.com/2010/12/jcombobox-text-alignment.html
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.RIGHT);
    operationComboBox.setRenderer(dlcr);

    enableDisableButtons ();
  }
  private void enableDisableButtons ()
  {
      boolean    rowAvailable =                   (getNumberRows()  >= 1);
      boolean columnAvailable = ((getNumberColumns() - 1)/tupleSize >= 1);

       removeColumnButton.setEnabled(   rowAvailable && (jTable.getSelectedColumn()        >  0) &&  (getNumberColumns() > 1)); // account for key column
          removeRowButton.setEnabled(columnAvailable && (jTable.getSelectedRow()           > -1) &&  (getNumberRows()    > 0));
     moveColumnLeftButton.setEnabled(   rowAvailable && (jTable.getSelectedColumn() > tupleSize) && ((getNumberColumns() - 1)/tupleSize > 1));
    moveColumnRightButton.setEnabled(columnAvailable && (jTable.getSelectedColumn()        >  0) && ((getNumberColumns() - 1)/tupleSize > 1) && (jTable.getSelectedColumn() < getNumberColumns() - tupleSize));
          moveRowUpButton.setEnabled(   rowAvailable && (jTable.getSelectedRow()           >  0) &&  (getNumberRows() > 1));
        moveRowDownButton.setEnabled(columnAvailable && (jTable.getSelectedRow()           > -1) &&  (getNumberRows() > 1)                   && (jTable.getSelectedRow()    < getNumberRows() - 1));

           editCellsPanel.setEnabled(rowAvailable || columnAvailable);
              appendPanel.setEnabled(rowAvailable && columnAvailable);
              
    boolean twoOrMoreRows = (jTable.getModel().getRowCount() >= 2);
    
                sortByKeyButton.setEnabled(twoOrMoreRows && columnAvailable);
      uniformKeyIntervalsButton.setEnabled(twoOrMoreRows && columnAvailable);
  }

    /**
     * @return the doNormals value
     */
    public boolean isDoNormals() {
        return doNormals;
    }

    /**
     * @param doNormals the doNormals value to set
     */
    public void setDoNormals(boolean doNormals) {
        this.doNormals = doNormals;
    }

  private void setSpecialRenderers()
  {
    TableColumnModel cmod = jTable.getColumnModel();
  
    TableColumn tc = cmod.getColumn(0);
    tc.setCellRenderer(new BoldRenderer());

    DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();
    ThirdRenderer thirdRenderer = new ThirdRenderer();
    defaultRenderer.setHorizontalAlignment(JLabel.RIGHT); // consistent cell alignment
      thirdRenderer.setHorizontalAlignment(JLabel.RIGHT);
    boolean doGray = false;

    for (int c = 1; c < (getNumberColumns() - 1); c+=tupleSize) {   // forget the key col, col 0
      TableCellRenderer cr;
      if(doGray)
        cr = thirdRenderer;
      else
        cr = defaultRenderer;
      for(int i=0;i<tupleSize;i++)
      {
          cmod.getColumn(c+i).setCellRenderer(cr);
      }
      
      doGray = !doGray;
    }
  }

  public void setData(int tupleSize, int numTuples, int numKeys, String unparsedKeys, String unparsedTuples)
  {
    this.tupleSize = tupleSize;
    
    jTable.setModel(createTableModel(numTuples, numKeys, unparsedKeys, unparsedTuples));
    setSpecialRenderers();
    customizeColumnWidths();
    tableChanged(null); //update the labels
    enableDisableButtons ();
  }

  public void setData(int tupleSize, String[][] keysAndValues)
  {
    StringBuilder keys = new StringBuilder();
    StringBuilder vals = new StringBuilder();
      for (String[] keysAndValue : keysAndValues) {
          for (int c = 0; c < keysAndValues[0].length; c++) {
              if (c == 0) {
                  keys.append(keysAndValue[c]);
                  keys.append(" ");
              } else {
                  vals.append(keysAndValue[c]);
                  vals.append(" ");
              }
          }
      }
    int ntups = (keysAndValues[0].length - 1) / tupleSize;// number of tuples here
    int nrows = keysAndValues.length; // number of rows here
    setData(tupleSize, ntups, nrows, keys.toString().trim(), vals.toString().trim());
    enableDisableButtons ();
  }

  public int getNumberColumns()
  {
    return jTable.getModel().getColumnCount();
  }

  public int getNumberRows()
  {
    return jTable.getModel().getRowCount();
  }

  @SuppressWarnings("unchecked")
  public String[][] getData()
  {
    Vector<Vector> v = ((DefaultTableModel)jTable.getModel()).getDataVector();
    String[][] saa = new String[v.size()][];
    int r=0;
    for(Vector<String> vs : v) {
      saa[r] = new String[vs.size()];
      int c = 0;
      for(String s : vs)
        saa[r][c++] = s;
      r++;
    }
    return saa;
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
  }

  private TableModel createTableModel(int nTups, int nrows, String keysStr, String tuples)
  {
    TableModel tm;
    Vector<Vector<String>> master = new Vector<>();
    if (nTups <= 0 || keysStr == null || keysStr.length() <= 0 || tuples == null || tuples.length() <= 0)
    {
//      tm = new DefaultTableModel(0, this.tupleSize+this.numberKeys);
        tm = new DefaultTableModel(master, getHeaders((nTups * tupleSize) + 1)); 
    }
    else {
      String[] keys = keysStr.replace(',', ' ').trim().split("\\s+");

      tuples = tuples.replace(',', ' ');
      String[] tups = tuples.trim().split("\\s+");   // possible comma surrounded by possible whitespace

      int ti = 0;
      for (int r = 0; r < nrows; r++) {
        Vector<String> rowV = new Vector<>(nTups);
        if (keys.length > r)
          rowV.add(keys[r]);
        else
          rowV.add("" + r);

        for (int t = 0; t < nTups; t++) {
          String[] ta = new String[tupleSize];
          Arrays.fill(ta, "");
          for(int i=0;i<tupleSize;i++) {
            if(tups.length > ti)              
              ta[i] = tups[ti++];
            rowV.add(ta[i]);
          }
        }
        master.add(rowV);
      }

      tm = new DefaultTableModel(master, getHeaders((nTups * tupleSize) + 1));
    }
    tm.addTableModelListener(this);

    return tm;
  }

  private Vector<String> getHeaders(int ncols)
  {
    Vector<String> hdr = new Vector<>();
    hdr.add("key");
    int coorNum = 0; // 0-based
    for (int c = 1; c < ncols; c += tupleSize) {
      for(int i=0;i<tupleSize;i++) {
        if(tupleSize == 1)
          hdr.add(""+coorNum++);
        else {
          if(i==1)
            hdr.add(""+coorNum++);
          else
            hdr.add("");
        }
      }
    }
    return hdr;
  }

  @Override
  public void tableChanged(TableModelEvent e)
  {
    TableModel tm = jTable.getModel();

    int c = tm.getColumnCount();
    c--; // key
    c /= tupleSize; // triples
    numberColumnsLabel.setText("" + c);

    numberRowsLabel.setText("" + tm.getRowCount());
  }

  public void setTitle(String s)
  {
    Border b = getBorder();
    if (b instanceof TitledBorder) {
      ((TitledBorder) b).setTitle(s);
    }
  }

  public void setAddColumnButtonText(String s)
  {
    appendColumnButton.setText(s);
  }

  public void setAddColumnButtonTooltip(String s)
  {
    appendColumnButton.setToolTipText(s);
  }

  public void setRemoveColumnButtonText(String s)
  {
    removeColumnButton.setText(s);
  }

  public void setRemoveColumnButtonTooltip(String s)
  {
    removeColumnButton.setToolTipText(s);
  }

  public void setRemoveRowButtonText(String s)
  {
    removeRowButton.setText(s);
  }

  public void setRemoveRowButtonTooltip(String s)
  {
    removeRowButton.setToolTipText(s);
  }

  public void setRowsLabelText(String s)
  {
    numberRowsTagLabel.setText(s);
  }

  public void setColumnsLabelText(String s)
  {
    numberColumnsTagLabel.setText(s);
  }
  public String getAddRowButtonText()
  {
    return appendRowButton.getText();
  }

  public void setAddRowButtonText(String s)
  {
    appendRowButton.setText(s);
  }

  public void setAddRowButtonTooltip(String s)
  {
    appendRowButton.setToolTipText(s);
  }

  private int initialColumnIndex = 0;
  private int finalColumnIndex   = 0;
  private int initialRowIndex    = 0; // unvarying
  private int finalRowIndex      = 0;

  private void computeTableIndices ()
  {
    initialColumnIndex = 0;
    if (doIndex) {
        initialColumnIndex++;
    }
    // OK to modify key column

    finalColumnIndex = jTable.getModel().getColumnCount() - 1;
    finalRowIndex = jTable.getModel().getRowCount() - 1;
  }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        editColumnsPanel = new JPanel();
        numberColumnsLabel = new JLabel();
        numberColumnsTagLabel = new JLabel();
        editColumnButtonsPanel = new JPanel();
        appendColumnButton = new JButton();
        removeColumnButton = new JButton();
        moveColumnLeftButton = new JButton();
        moveColumnRightButton = new JButton();
        tableScrollPane = new JScrollPane();
        jTable = new JTable();
        editRowsPanel = new JPanel();
        numberRowsLabel = new JLabel();
        numberRowsTagLabel = new JLabel();
        editRowButtonsPanel = new JPanel();
        removeRowButton = new JButton();
        appendRowButton = new JButton();
        moveRowUpButton = new JButton();
        moveRowDownButton = new JButton();
        sortByKeyButton = new JButton();
        uniformKeyIntervalsButton = new JButton();
        editCellsPanel = new JPanel();
        operationComboBox = new JComboBox<String>();
        editFactorTextField = new JTextField();
        cellSelectionComboBox = new JComboBox<String>();
        applyCellFactorButton = new JButton();
        appendPanel = new JPanel();
        appendLabel = new JLabel();
        insertCommasCheckBox = new JCheckBox();
        insertLineBreaksCheckBox = new JCheckBox();

        setBorder(BorderFactory.createTitledBorder(null, NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableTable.border.title"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, getFont())); // NOI18N
        setLayout(new GridBagLayout());

        editColumnsPanel.setOpaque(false);
        editColumnsPanel.setLayout(new GridBagLayout());

        numberColumnsLabel.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableTable.numColsLab.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(20, 3, 3, 3);
        editColumnsPanel.add(numberColumnsLabel, gridBagConstraints);

        numberColumnsTagLabel.setText("getColumnLabelText()");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(20, 3, 3, 3);
        editColumnsPanel.add(numberColumnsTagLabel, gridBagConstraints);

        editColumnButtonsPanel.setBorder(BorderFactory.createTitledBorder(null, NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.editColumnButtonsPanel.border.title"), TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION)); // NOI18N
        editColumnButtonsPanel.setLayout(new GridBagLayout());

        appendColumnButton.setText(NbBundle.getMessage(getClass(), "ExpandableKeyTupleTable.appendColumnButton.text")); // NOI18N
        appendColumnButton.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "TT_add_column_button")); // NOI18N
        appendColumnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                appendColumnButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        editColumnButtonsPanel.add(appendColumnButton, gridBagConstraints);

        removeColumnButton.setText(NbBundle.getMessage(getClass(), "ExpandableKeyTupleTable.removeColumnButton.text")); // NOI18N
        removeColumnButton.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "TT_remove_column_button")); // NOI18N
        removeColumnButton.setMinimumSize(new Dimension(188, 29));
        removeColumnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                removeColumnButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        editColumnButtonsPanel.add(removeColumnButton, gridBagConstraints);

        moveColumnLeftButton.setIcon(new ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/leftArrow.png"))); // NOI18N
        moveColumnLeftButton.setText(NbBundle.getMessage(getClass(), "ExpandableKeyTupleTable.moveColumnLeftButton.text")); // NOI18N
        moveColumnLeftButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COLUMN_LEFT_BUTTON"));
        moveColumnLeftButton.setMargin(new Insets(0, 1, 0, 1));
        moveColumnLeftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                moveColumnLeftButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        editColumnButtonsPanel.add(moveColumnLeftButton, gridBagConstraints);

        moveColumnRightButton.setIcon(new ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/rightArrow.png"))); // NOI18N
        moveColumnRightButton.setText(NbBundle.getMessage(getClass(), "ExpandableKeyTupleTable.moveColumnRightButton.text")); // NOI18N
        moveColumnRightButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COLUMN_RIGHT_BUTTON"));
        moveColumnRightButton.setMargin(new Insets(0, 1, 0, 1));
        moveColumnRightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                moveColumnRightButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        editColumnButtonsPanel.add(moveColumnRightButton, gridBagConstraints);

        editColumnsPanel.add(editColumnButtonsPanel, new GridBagConstraints());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(editColumnsPanel, gridBagConstraints);

        tableScrollPane.setBorder(null);
        tableScrollPane.setPreferredSize(new Dimension(350, 150));

        jTable.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable.setColumnSelectionAllowed(true);
        jTable.setMaximumSize(new Dimension(2147483647, 100));
        jTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                jTableMousePressed(evt);
            }
        });
        jTable.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                jTablePropertyChange(evt);
            }
        });
        tableScrollPane.setViewportView(jTable);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(tableScrollPane, gridBagConstraints);

        editRowsPanel.setLayout(new GridBagLayout());

        numberRowsLabel.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableTable.numRowsLab.text")); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(13, 3, 3, 3);
        editRowsPanel.add(numberRowsLabel, gridBagConstraints);

        numberRowsTagLabel.setText("getRowsLabelText()");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(13, 3, 3, 3);
        editRowsPanel.add(numberRowsTagLabel, gridBagConstraints);

        editRowButtonsPanel.setBorder(BorderFactory.createTitledBorder(null, NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.editRowButtonsPanel.border.title"), TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION)); // NOI18N
        editRowButtonsPanel.setLayout(new GridBagLayout());

        removeRowButton.setText(NbBundle.getMessage(getClass(), "ExpandableKeyTupleTable.removeRowButton.text")); // NOI18N
        removeRowButton.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "TT_remove_row_button")); // NOI18N
        removeRowButton.setMinimumSize(new Dimension(192, 29));
        removeRowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                removeRowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        editRowButtonsPanel.add(removeRowButton, gridBagConstraints);

        appendRowButton.setText(NbBundle.getMessage(getClass(), "ExpandableKeyTupleTable.appendRowButton.text")); // NOI18N
        appendRowButton.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "TT_add_row_button")); // NOI18N
        appendRowButton.setMinimumSize(new Dimension(141, 29));
        appendRowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                appendRowButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        editRowButtonsPanel.add(appendRowButton, gridBagConstraints);

        moveRowUpButton.setIcon(new ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/upArrow.png"))); // NOI18N
        moveRowUpButton.setText(NbBundle.getMessage(getClass(), "ExpandableKeyTupleTable.moveRowUpButton.text")); // NOI18N
        moveRowUpButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_UP_BUTTON"));
        moveRowUpButton.setMargin(new Insets(0, 1, 0, 1));
        moveRowUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                moveRowUpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        editRowButtonsPanel.add(moveRowUpButton, gridBagConstraints);

        moveRowDownButton.setIcon(new ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/downArrow.png"))); // NOI18N
        moveRowDownButton.setText(NbBundle.getMessage(getClass(), "ExpandableKeyTupleTable.moveRowDownButton.text")); // NOI18N
        moveRowDownButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_DOWN_BUTTON"));
        moveRowDownButton.setMargin(new Insets(0, 1, 0, 1));
        moveRowDownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                moveRowDownButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        editRowButtonsPanel.add(moveRowDownButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        editRowsPanel.add(editRowButtonsPanel, gridBagConstraints);

        sortByKeyButton.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.sortByKeyButton.text")); // NOI18N
        sortByKeyButton.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.sortByKeyButton.toolTipText")); // NOI18N
        sortByKeyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                sortByKeyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(13, 3, 3, 3);
        editRowsPanel.add(sortByKeyButton, gridBagConstraints);

        uniformKeyIntervalsButton.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.uniformKeyIntervalsButton.text")); // NOI18N
        uniformKeyIntervalsButton.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.uniformKeyIntervalsButton.toolTipText")); // NOI18N
        uniformKeyIntervalsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                uniformKeyIntervalsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(13, 3, 3, 3);
        editRowsPanel.add(uniformKeyIntervalsButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(editRowsPanel, gridBagConstraints);

        editCellsPanel.setBorder(BorderFactory.createTitledBorder(null, NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.editCellsPanel.border.title"), TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION)); // NOI18N
        editCellsPanel.setLayout(new GridBagLayout());

        operationComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Assign cell value:", "Add factor:", "Subtract factor:", "Multiply by factor:", "Divide by factor:", "Divide into factor:", "Roundoff cell value", "Negate cell value" }));
        operationComboBox.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.operationComboBox.toolTipText")); // NOI18N
        operationComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                operationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(3, 3, 0, 3);
        editCellsPanel.add(operationComboBox, gridBagConstraints);

        editFactorTextField.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.editFactorTextField.text")); // NOI18N
        editFactorTextField.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.editFactorTextField.toolTipText")); // NOI18N
        editFactorTextField.setMinimumSize(new Dimension(60, 20));
        editFactorTextField.setPreferredSize(new Dimension(60, 20));
        editFactorTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editFactorTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(3, 3, 0, 3);
        editCellsPanel.add(editFactorTextField, gridBagConstraints);

        cellSelectionComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "to selected cell", "to selected row", "to selected column", "to all cells" }));
        cellSelectionComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cellSelectionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(3, 3, 0, 3);
        editCellsPanel.add(cellSelectionComboBox, gridBagConstraints);

        applyCellFactorButton.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.applyCellFactorButton.text")); // NOI18N
        applyCellFactorButton.setToolTipText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.applyCellFactorButton.toolTipText")); // NOI18N
        applyCellFactorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                applyCellFactorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(3, 3, 0, 3);
        editCellsPanel.add(applyCellFactorButton, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(editCellsPanel, gridBagConstraints);

        appendPanel.setLayout(new GridBagLayout());

        appendLabel.setFont(new Font("Tahoma", 2, 11)); // NOI18N
        appendLabel.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.appendLabel.text")); // NOI18N
        appendPanel.add(appendLabel, new GridBagConstraints());

        insertCommasCheckBox.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.insertCommasCheckBox.text")); // NOI18N
        insertCommasCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                insertCommasCheckBoxActionPerformed(evt);
            }
        });
        appendPanel.add(insertCommasCheckBox, new GridBagConstraints());

        insertLineBreaksCheckBox.setText(NbBundle.getMessage(ExpandableKeyTupleTable.class, "ExpandableKeyTupleTable.insertLineBreaksCheckBox.text")); // NOI18N
        insertLineBreaksCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        appendPanel.add(insertLineBreaksCheckBox, new GridBagConstraints());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.LAST_LINE_END;
        gridBagConstraints.insets = new Insets(3, 3, 7, 3);
        add(appendPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  private void removeColumnButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeColumnButtonActionPerformed
  {//GEN-HEADEREND:event_removeColumnButtonActionPerformed
      if  (getNumberColumns() <= 1)
           return; // don't delete (the last remaining) key column

      int      selectedColumn = jTable.getSelectedColumn();
      if      (selectedColumn == -1)
               selectedColumn = getNumberColumns() - 1; // delete last column if no column selected
      else if (selectedColumn == 0)
               selectedColumn = 1; // delete initial value column if key column selected


      selectedColumn = tupleStartColumn(selectedColumn); // Which column starts the selected tuple?
      DefaultTableModel mod = (DefaultTableModel) jTable.getModel();
      Vector<Vector> datav = mod.getDataVector();
      for (int r = 0; r < datav.size(); r++)
      {
          Vector row = datav.get(r);
          for (int c = 0; c < tupleSize; c++)
               row.remove(selectedColumn);
      }
      mod.setDataVector(datav, getHeaders(getNumberColumns() - tupleSize));// tuples here
      setSpecialRenderers();
      customizeColumnWidths();
      tableChanged(null); // update the labels
      enableDisableButtons ();
}//GEN-LAST:event_removeColumnButtonActionPerformed

  /**
   * Round off column value to first column of this n-tuple array
   * @param column of interest
   * @return first column of tuple
   */
  private int tupleStartColumn(int column)
  {
    if (column <= 0) // only key found
        column = 1;
    column--;   // account for key since it is in column 0; 2-tuple columns 1-2 map to column 1
    column /= tupleSize;
    column *= tupleSize;
    column++; // account for key since it is in column 0; adjust to go to first column of this tuple
    return column;
  }

  @SuppressWarnings("unchecked")
  private void appendColumnButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_appendColumnButtonActionPerformed
  {//GEN-HEADEREND:event_appendColumnButtonActionPerformed
        int selectedColumn = jTable.getSelectedColumn();
        if (selectedColumn == -1) // none selected
            selectedColumn = getNumberColumns() - 1; // apply to end of tuple columns

        if  (getNumberColumns() == 1)
             selectedColumn = 1; // only key column remains, will be adding initial column of values
        else selectedColumn = tupleStartColumn(selectedColumn) + tupleSize; // Which column starts the selected tuple?

        DefaultTableModel mod = (DefaultTableModel) jTable.getModel();
        Vector<Vector> datav = mod.getDataVector();
        for (Vector row : datav)
             for(int i=0;i<tupleSize;i++)
                 row.add(selectedColumn + i, defaultTupleValues[i]); // insert default value vice zeros

        mod.setDataVector(datav, getHeaders(getNumberColumns() + tupleSize));
        setSpecialRenderers();
        
        customizeColumnWidths();
        tableChanged(null); // update the labels
        enableDisableButtons ();
  }//GEN-LAST:event_appendColumnButtonActionPerformed

  private void appendRowButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_appendRowButtonActionPerformed
  {//GEN-HEADEREND:event_appendRowButtonActionPerformed
      int selectedRow = jTable.getSelectedRow();  // might be -1 if unselected...add to bottom if so
      if (selectedRow == -1)
          selectedRow = getNumberRows() - 1;
      String[] oa = new String[getNumberColumns()];
      int numberTupleColumns = (getNumberColumns() - 1) / tupleSize;
      if (selectedRow <= 0)
           oa[0] = "0.0";
      else oa[0] = jTable.getValueAt(selectedRow, 0).toString();
      for (int tuple = 0; tuple < numberTupleColumns; tuple++)
      {
          for (int i=0; i < tupleSize; i++)
             oa[tupleSize*tuple + i + 1] = defaultTupleValues[i]; // +1 offset accounts for key column[0]
      }
      ((DefaultTableModel) jTable.getModel()).insertRow(selectedRow + 1, oa); // append after selected row
      enableDisableButtons ();
  }//GEN-LAST:event_appendRowButtonActionPerformed

  private void removeRowButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeRowButtonActionPerformed
  {//GEN-HEADEREND:event_removeRowButtonActionPerformed
    if (getNumberRows() <= 0)
        return;
    int selectedRow = jTable.getSelectedRow();  // might be -1 if unselected... remove row from bottom if so
    if (selectedRow == -1)
        selectedRow = getNumberRows() - 1;
    ((DefaultTableModel) jTable.getModel()).removeRow(selectedRow);
    enableDisableButtons ();
  }//GEN-LAST:event_removeRowButtonActionPerformed

  private void moveRowUpButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_moveRowUpButtonActionPerformed
      int selectedRow = jTable.getSelectedRow();
      if (selectedRow <=0)
          return; // unselected or initial row:  no action to perform

      jTable.getSelectionModel().setLeadSelectionIndex(selectedRow); //
      ((DefaultTableModel)jTable.getModel()).moveRow(selectedRow,selectedRow,selectedRow-1);

      // swap keys back to restore original key order, unless key cell itself is selected
      boolean isKeyCellSelected = (isKeyColumnIncluded() && (jTable.getSelectedColumn() == 0));
      if (isKeyColumnIncluded() && !isKeyCellSelected)
      {
          // note no index in column 0
          Object hold =     jTable.getValueAt(           selectedRow, 0);
          jTable.setValueAt(jTable.getValueAt(selectedRow-1, 0), selectedRow, 0);
          jTable.setValueAt(hold,             selectedRow-1, 0);
      }
      jTable.getSelectionModel().setSelectionInterval(selectedRow-1, selectedRow-1);
      jTable.scrollRectToVisible(jTable.getCellRect(selectedRow-1, 0, true));
      
      enableDisableButtons ();
      
      if (selectedRow > 0)
          selectedRow--; // shift selection up with values
      jTable.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
      jTable.scrollRectToVisible(jTable.getCellRect(selectedRow, 0, true));
  }//GEN-LAST:event_moveRowUpButtonActionPerformed

  private void moveRowDownButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_moveRowDownButtonActionPerformed
      int selectedRow = jTable.getSelectedRow();
      if (selectedRow == -1 || selectedRow >= (jTable.getModel().getRowCount() - 1))
          return; // unselected or final row:  no action to perform

      ((DefaultTableModel)jTable.getModel()).moveRow(selectedRow,selectedRow,selectedRow+1);

      // swap keys back to restore original key order, unless key cell itself is selected
      boolean isKeyCellSelected = (isKeyColumnIncluded() && (jTable.getSelectedColumn() == 0));
      if (isKeyColumnIncluded() && !isKeyCellSelected)
      {
          // note no index in column 0
          Object hold =     jTable.getValueAt(           selectedRow, 0);
          jTable.setValueAt(jTable.getValueAt(selectedRow+1, 0), selectedRow, 0);
          jTable.setValueAt(hold,             selectedRow+1, 0);
      }
      jTable.getSelectionModel().setSelectionInterval(selectedRow+1, selectedRow+1);
      jTable.scrollRectToVisible(jTable.getCellRect(selectedRow+1, 0, true));
      
      enableDisableButtons ();
      
      if (selectedRow < jTable.getRowCount() - 1)
          selectedRow++; // shift selection down with values
      jTable.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
      jTable.scrollRectToVisible(jTable.getCellRect(selectedRow, 0, true));
}//GEN-LAST:event_moveRowDownButtonActionPerformed

  private void moveColumnLeftButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_moveColumnLeftButtonActionPerformed
      int selectedColumn = jTable.getSelectedColumn();
      if (selectedColumn <=0) // unselected or initial row
          return;
      selectedColumn = tupleStartColumn(selectedColumn); // Which column starts the selected tuple

      for (int row=0; row < jTable.getModel().getRowCount(); row++) // swap values
      {
          for (int tupleColumn=0; tupleColumn < tupleSize; tupleColumn++)
          {
              Object hold =     jTable.getValueAt(row,                                     selectedColumn - tupleSize + tupleColumn);
              jTable.setValueAt(jTable.getValueAt(row, selectedColumn + tupleColumn), row, selectedColumn - tupleSize + tupleColumn);
              jTable.setValueAt(hold,             row, selectedColumn + tupleColumn);
          }
      }
      enableDisableButtons ();
      
      if (selectedColumn > 0)
          selectedColumn--; // shift selection left with values
      // TODO not working...
      jTable.getSelectionModel().setSelectionInterval(selectedColumn, selectedColumn);
      jTable.scrollRectToVisible(jTable.getCellRect(jTable.getSelectedRow(), selectedColumn, true));
}//GEN-LAST:event_moveColumnLeftButtonActionPerformed

  private void moveColumnRightButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_moveColumnRightButtonActionPerformed
      int selectedColumn = jTable.getSelectedColumn();
      if (selectedColumn < 0) // unselected column
          return;
      selectedColumn = tupleStartColumn(selectedColumn); // Which column starts the selected tuple
      if (selectedColumn + tupleSize >= jTable.getModel().getColumnCount()) // final row
          return;

      for (int row=0; row < jTable.getModel().getRowCount(); row++) // swap values
      {
          for (int tupleColumn=0; tupleColumn < tupleSize; tupleColumn++)
          {
              Object hold =     jTable.getValueAt(row,                                     selectedColumn + tupleSize + tupleColumn);
              jTable.setValueAt(jTable.getValueAt(row, selectedColumn + tupleColumn), row, selectedColumn + tupleSize + tupleColumn);
              jTable.setValueAt(hold,             row, selectedColumn + tupleColumn);
          }
      }
      enableDisableButtons ();
      
      if (selectedColumn < jTable.getColumnCount() - 1)
          selectedColumn++; // shift selection right with values
      // TODO not working...
      jTable.getSelectionModel().setSelectionInterval(selectedColumn, selectedColumn);
      jTable.scrollRectToVisible(jTable.getCellRect(jTable.getSelectedRow(), selectedColumn, true));
}//GEN-LAST:event_moveColumnRightButtonActionPerformed

    private void operationComboBoxActionPerformed(ActionEvent evt)//GEN-FIRST:event_operationComboBoxActionPerformed
    {//GEN-HEADEREND:event_operationComboBoxActionPerformed
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
              System.out.println("ExpandableList error:  illegal selection (" + operationComboBox.getSelectedIndex() + ") for operationComboBox");
              break;
      }

        if ((operationSelection == ROUNDOFF_OPERATION) || (operationSelection == NEGATE_OPERATION))
             editFactorTextField.setEnabled(false); // unneeded entry field
        else editFactorTextField.setEnabled(true);  //   needed entry field
    }//GEN-LAST:event_operationComboBoxActionPerformed

    private void editFactorTextFieldActionPerformed(ActionEvent evt)//GEN-FIRST:event_editFactorTextFieldActionPerformed
    {//GEN-HEADEREND:event_editFactorTextFieldActionPerformed
        checkEditFactorText();
    }//GEN-LAST:event_editFactorTextFieldActionPerformed

    private void cellSelectionComboBoxActionPerformed(ActionEvent evt)//GEN-FIRST:event_cellSelectionComboBoxActionPerformed
    {//GEN-HEADEREND:event_cellSelectionComboBoxActionPerformed
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
              System.out.println("Illegal selection cellSelectionComboBox");
              break;
      }
    }//GEN-LAST:event_cellSelectionComboBoxActionPerformed

    private void applyCellFactorButtonActionPerformed(ActionEvent evt)//GEN-FIRST:event_applyCellFactorButtonActionPerformed
    {//GEN-HEADEREND:event_applyCellFactorButtonActionPerformed
        String cellContents;
        float cellValue;

        checkEditFactorText(); // reread, recheck field in case of problems
        if ((editFactorTextField.getText().trim().length() == 0) &&
            (operationSelection != ROUNDOFF_OPERATION) &&
            (operationSelection != NEGATE_OPERATION))
        {
            return; // no operation since no factor value provided
        }
        if ((operationValue == 0.0f) && (operationSelection == DIVIDE_BY_OPERATION)) {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "Division by zero not allowed", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            return; // nop
        }
        computeTableIndices ();

        for (int column = initialColumnIndex; column <= finalColumnIndex; column++) // note beginning and end values inclusive
        {
            for (int row = initialRowIndex; row <= finalRowIndex; row++)            // note beginning and end values inclusive
            {
                TableModel tableModel = jTable.getModel();
                if              (null != tableModel.getValueAt(row, column))
                          cellContents = tableModel.getValueAt(row, column).toString();
                else      cellContents = "";
                if (    (!cellContents.equals("true") && !cellContents.equals("false")) &&
                      ( (operationScope == ALL_CELLS_OPERATION)
                    || ((operationScope == SELECTED_CELL_OPERATION)   && (jTable.isCellSelected(row, column) || (jTable.isRowSelected(row) && jTable.isColumnSelected(column))))
                    || ((operationScope == SELECTED_ROW_OPERATION)    &&  jTable.isRowSelected(row))
                    || ((operationScope == SELECTED_COLUMN_OPERATION) &&  jTable.isColumnSelected(column))))
                {
                    // ensure valid cell value determined
                    try
                    {
                        cellValue = (new SFFloat(cellContents)).getValue();
                    }
                    catch (Exception e) // TODO consider more specific exception catching
                    {
                        String warningMessage;
                        if (cellContents.length() == 0)
                        {
                            warningMessage = "No value provided for cell[" + row + "][" + column + "], please retry";
                        }
                        else
                        {
                            warningMessage = "Illegal float value (" + cellContents + ") for cell[" + row + "][" + column + "], please retry";
                        }
                        NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                                warningMessage, NotifyDescriptor.WARNING_MESSAGE);
                        DialogDisplayer.getDefault().notify(descriptor);
                        System.out.println(warningMessage);
                        return;
                    }
                    if ((cellValue == 0.0f) && (operationSelection == DIVIDE_INTO_OPERATION)) {
                        NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                                "Division by zero not allowed,\ncell[" + row + "][" + column + "] unchanged", NotifyDescriptor.WARNING_MESSAGE);
                        DialogDisplayer.getDefault().notify(descriptor);
                        continue;
                    }
                    else if (operationSelection == ASSIGN_OPERATION) {
                        cellValue = operationValue;
                    }
                    else if (operationSelection == ADD_OPERATION) {
                        cellValue += operationValue;
                    }
                    else if (operationSelection == SUBTRACT_OPERATION) {
                        cellValue -= operationValue;
                    }
                    else if (operationSelection == MULTIPLY_OPERATION) {
                        cellValue *= operationValue;
                    }
                    else if (operationSelection == DIVIDE_BY_OPERATION) {
                        cellValue /= operationValue;
                    }
                    else if (operationSelection == DIVIDE_INTO_OPERATION) {
                        cellValue = operationValue / cellValue;
                    }
                    else if (operationSelection == ROUNDOFF_OPERATION) // to nearest meter
                    {
                        // TODO use operationValue for number of places
                        if (doNormals) {
                            cellValue = Math.round(cellValue * 10000.0f) / 10000.0f;
                        }
                        else {
                            cellValue = Math.round(cellValue);
                        }
                    }
                    else if (operationSelection == NEGATE_OPERATION) {
                        cellValue = -1.0f *  cellValue;
                    }
                    cellContents = Float.toString(cellValue);
                    while (cellContents.contains(".") && cellContents.endsWith("0")) // clean up
                    {
                        cellContents = cellContents.substring(0, cellContents.length() - 1);
                    }
                    if (cellContents.endsWith(".")) // clean up
                    {
                        cellContents = cellContents.substring(0, cellContents.length() - 1);
                    }
                    tableModel.setValueAt(cellContents, row, column);
                }
                else if (  (operationScope == ALL_CELLS_OPERATION)
                       || ((operationScope == SELECTED_CELL_OPERATION)   && (jTable.isCellSelected(row, column) || (jTable.isRowSelected(row) && jTable.isColumnSelected(column))))
                       || ((operationScope == SELECTED_ROW_OPERATION)    &&  jTable.isRowSelected(row))
                       || ((operationScope == SELECTED_COLUMN_OPERATION) &&  jTable.isColumnSelected(column)))
                {
                    if (cellContents.equals("true")  && (operationSelection == NEGATE_OPERATION))
                    {
                           tableModel.setValueAt(false, row, column); // must be object to get checkbox interface
                    }
                    else if (cellContents.equals("false") && (operationSelection == NEGATE_OPERATION))
                    {
                           tableModel.setValueAt(true,  row, column);
                    }
                }
            }
        }
    }//GEN-LAST:event_applyCellFactorButtonActionPerformed

    private void insertLineBreaksCheckBoxActionPerformed(ActionEvent evt)//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
    {//GEN-HEADEREND:event_insertLineBreaksCheckBoxActionPerformed
        setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
    }//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed

    private void insertCommasCheckBoxActionPerformed(ActionEvent evt)//GEN-FIRST:event_insertCommasCheckBoxActionPerformed
    {//GEN-HEADEREND:event_insertCommasCheckBoxActionPerformed
        setInsertCommas(insertCommasCheckBox.isSelected());
    }//GEN-LAST:event_insertCommasCheckBoxActionPerformed

    private void jTablePropertyChange(PropertyChangeEvent evt)//GEN-FIRST:event_jTablePropertyChange
    {//GEN-HEADEREND:event_jTablePropertyChange
        enableDisableButtons ();
    }//GEN-LAST:event_jTablePropertyChange

    private void jTableMousePressed(MouseEvent evt)//GEN-FIRST:event_jTableMousePressed
    {//GEN-HEADEREND:event_jTableMousePressed
        enableDisableButtons ();
    }//GEN-LAST:event_jTableMousePressed

    private void sortByKeyButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_sortByKeyButtonActionPerformed
        String[][] saa = getData();
        if      (saa.length == 0) return;
        else if (saa.length == 1) return;

        for (int i = 0; i < saa.length - 1; i++)
        {
            for (int j=i+1; j < saa.length; j++)
            {
                if ((new SFFloat(saa [i][0])).getValue() > (new SFFloat(saa [j][0])).getValue()) // avoid problems with empty strings
                {
                    // swap
                    String[] hold = saa [i];
                    saa [i]  = saa [j];
                    saa [j]  = hold;
                }
            }
        }
        setData(tupleSize, saa);
    }//GEN-LAST:event_sortByKeyButtonActionPerformed

    private void uniformKeyIntervalsButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_uniformKeyIntervalsButtonActionPerformed
        String[][] saa = getData();
      switch (saa.length) {
          case 0:
              return;
          case 1:
              saa [0][0] = "0";
              break;
      // (saa.length > 2)
          default:
              float increment = 1.0f / ((float) saa.length - 1);
              for (int i=0; i < saa.length; i++) {
                  saa [i][0] = Float.toString(Math.round(i * increment * 10000.0f)/10000.0f);
              } break;
      }
        setData(tupleSize, saa);
    }//GEN-LAST:event_uniformKeyIntervalsButtonActionPerformed

  private void checkEditFactorText ()
  {
       String cellContents = "";
       if ((operationSelection == ROUNDOFF_OPERATION) ||
           (operationSelection == NEGATE_OPERATION))  return; // no edit factor text needed
       try
       {
           cellContents = editFactorTextField.getText().trim();
           operationValue = (new SFFloat(cellContents)).getValue();
       }
       catch (Exception e)
       {
           String warningMessage;
           if (cellContents.trim().length()==0)
                warningMessage = "No value provided to edit cells, please retry";
           else warningMessage = "Illegal float value (" + cellContents + "), please retry";
           NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    warningMessage, NotifyDescriptor.WARNING_MESSAGE);
           DialogDisplayer.getDefault().notify(descriptor);
           System.out.println(warningMessage);
       }
  }

    /**
     * @return the insertCommas value
     */
    protected final boolean isInsertCommasSet() {
        return insertCommas;
    }

    /**
     * @return the insertLineBreaks value
     */
    protected final boolean isInsertLineBreaksSet() {
        return insertLineBreaks;
    }

    /**
     * @param insertCommas the insertCommas value to set
     */
    public void setInsertCommas(boolean insertCommas) {
        this.insertCommas = insertCommas;
        insertCommasCheckBox.setSelected(insertCommas);
    }

    /**
     * @param insertLineBreaks the insertLineBreaks value to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
        insertLineBreaksCheckBox.setSelected(insertLineBreaks);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton appendColumnButton;
    private JLabel appendLabel;
    private JPanel appendPanel;
    private JButton appendRowButton;
    private JButton applyCellFactorButton;
    private JComboBox<String> cellSelectionComboBox;
    private JPanel editCellsPanel;
    private JPanel editColumnButtonsPanel;
    private JPanel editColumnsPanel;
    private JTextField editFactorTextField;
    private JPanel editRowButtonsPanel;
    private JPanel editRowsPanel;
    private JCheckBox insertCommasCheckBox;
    private JCheckBox insertLineBreaksCheckBox;
    private JTable jTable;
    private JButton moveColumnLeftButton;
    private JButton moveColumnRightButton;
    private JButton moveRowDownButton;
    private JButton moveRowUpButton;
    private JLabel numberColumnsLabel;
    private JLabel numberColumnsTagLabel;
    private JLabel numberRowsLabel;
    private JLabel numberRowsTagLabel;
    private JComboBox<String> operationComboBox;
    private JButton removeColumnButton;
    private JButton removeRowButton;
    private JButton sortByKeyButton;
    private JScrollPane tableScrollPane;
    private JButton uniformKeyIntervalsButton;
    // End of variables declaration//GEN-END:variables

    /**
     * @param defaultTupleValues the defaultTupleValues value to set
     */
    public void setDefaultTupleValues(String[] defaultTupleValues) {
        this.defaultTupleValues = defaultTupleValues;
    }
    // End of variables declaration
  class BoldRenderer extends DefaultTableCellRenderer
  {

    Font boldFont;
    Color blueBkgrd = new Color(195, 212, 232);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (boldFont == null)
        boldFont = comp.getFont().deriveFont(Font.BOLD);
      comp.setFont(boldFont);
      comp.setBackground(blueBkgrd);
      return comp;
    }
  }

  class ThirdRenderer extends DefaultTableCellRenderer
  {

    Color darkerBkgrd = null;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (darkerBkgrd == null) {
        darkerBkgrd = comp.getBackground().darker();
        // make it a little lighter
        float[] rf = darkerBkgrd.getColorComponents(null);
        for(int i=0;i<rf.length;i++) {  
          float diff = 1.0f-rf[i];
          rf[i]+=(diff*0.45f);
        }
        darkerBkgrd = new Color(rf[0],rf[1],rf[2]);
      }
      comp.setBackground(darkerBkgrd);

      return comp;
    }
  }

    /**
     * @return the keyColumnIncluded value
     */
    public boolean isKeyColumnIncluded() {
        return keyColumnIncluded;
    }

    /**
     * @param keyColumnIncluded the keyColumnIncluded value to set
     */
    public void setKeyColumnIncluded(boolean keyColumnIncluded) {
        this.keyColumnIncluded = keyColumnIncluded;
    }
}
