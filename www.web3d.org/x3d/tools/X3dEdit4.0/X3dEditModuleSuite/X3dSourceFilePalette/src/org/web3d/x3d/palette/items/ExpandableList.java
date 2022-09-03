/*
Copyright (c) 2007-2021 held by the author(s) .  All rights reserved.

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
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import net.java.dev.colorchooser.ColorChooser;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import org.web3d.x3d.NsidedPolygon;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.N_SIDED_POLYGON;
import static org.web3d.x3d.types.X3DSchemaData4.parseX;

/**
 * ExpandableList.java
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class ExpandableList extends javax.swing.JPanel implements ListSelectionListener, TableModelListener
{
  // Number of displayed JTable columns is determined from the length of this array, NOT by the size of the data array, since
  // the data array is often empty for new elements.  If doTrailingColorChooser(true), doTrailingTextEditButt(true) or doIndexInFirstColumn(true)
  // has been called, extra colums will be put into the JTable accordingly.  The columnTitles array MUST account for these extra columns.
  // Normal use procedure:
  // 1. doTrailingColorChooser();     // if desired
  // 2. doIndexInFirstColumn(true);   // if desired
  // 3. doTrailingTextEditButt(true); // if desired
  // 4. setColumnTitles(String[] x);  // Include titles for optional cols specified above, the size of this array determines the # cols in the table
  // 5. setData(String[][] x);        // They can be empty arrays
  // 6. setKeyColumnIncluded(false);  // if desired

  private String[] columnTitles   = {"#", "x", "y", "z", "a", "b", "c"};
  private String[] columnToolTips = {"", "", "", "", "", "", "", "", ""};
  private int     numJTableColumns = 0;
  private String defaultCellValue = "0";

  // Parameters which have to do with rendering
  private int   redColumn = -1; // -1 indicates no color
  private int angleColumn = -1; // -1 sentinel means no angle col (in radians) present
  private boolean sortOrderAscending = true; // ground array is reversed
  private ArrayList<Integer> boldColumns     = new ArrayList<>();  // used to signify key columns

  // Parameters which effect editing
  private ArrayList<Integer> readOnlyColumns = new ArrayList<>();
  private ArrayList<Integer> readOnlyRows    = new ArrayList<>();
  private ArrayList<CellID>  readOnlyCells   = new ArrayList<>();

  // Parameters which affect addition of columns
  private boolean doColorChooser      = false;
  private boolean doNormals           = false;
  private boolean doOrientations      = false;
  private boolean doTextEditButton    = false;
  private boolean doIndex             = false;
  private boolean includesAlphaColumn = false;
  private boolean dataStringBased     = false; // only includes strings, not booleans
  private boolean dataBooleanBased    = false;
  private boolean hasChangedValues    = false;

  private int textAlignment = JLabel.TRAILING;
  
  private Object[] copiedRow=null;                   // Allow user to save row values
  private Object[] specifiedNewRow = columnToolTips; // Or, a row which is explicitly specified during startup via setNewRowData()

  private IndexRenderer idxRenderer = new IndexRenderer();

  private boolean showEditEnhancements, insertCommas, insertLineBreaks = false;
  private boolean includeMakeOpenClosedButton = false;
  private boolean angleColumnIncluded = false;
  private boolean   keyColumnIncluded = false;
  private boolean flippableRowData  = false;
  private boolean largeRadianAnglesConfirmed = false;

  private final int      ASSIGN_OPERATION = 0;
  private final int         ADD_OPERATION = 1;
  private final int    SUBTRACT_OPERATION = 2;
  private final int    MULTIPLY_OPERATION = 3;
  private final int   DIVIDE_BY_OPERATION = 4;
  private final int DIVIDE_INTO_OPERATION = 5;
  private final int    ROUNDOFF_OPERATION = 6;
  private final int      NEGATE_OPERATION = 7;
  private final int      CENTER_OPERATION = 8;
  private final int      ROTATE_OPERATION = 9; // currently only 2D coordinates supported; TODO rotation for 3D coordinates, normals, orientations
  private       int    operationSelection = ASSIGN_OPERATION;

  private final int   SELECTED_CELL_OPERATION = 0;
  private final int    SELECTED_ROW_OPERATION = 1;
  private final int SELECTED_COLUMN_OPERATION = 2;
  private final int       ALL_CELLS_OPERATION = 3;
  private       int operationScope            = SELECTED_CELL_OPERATION;
  private float     operationValue            = 0.0f;
  private float         scaleValue            = 1.0f;
  private int       numberOfPoints            = 0;
  private final int DEFAULTNUMBEROFPOINTS     = -1; // force default number of segments

  private String[]      generatePointsDescriptions, generatePointsChoices;
  private String[][][]  generatePointsEnumerationValues; // array of array choices
  private boolean       enumerationValueLoopClosed = false; // whether coordinates are open or closed
  
  private Font plainButtonFont, italicsButtonFont;
  
  private TitledBorder titledBorder; // optional header
  
  // cell averaging and center computation
  private float maxX  = 0.0f;
  private float maxY  = 0.0f;
  private float maxZ  = 0.0f;
  private float minX  = 0.0f;
  private float minY  = 0.0f;
  private float minZ  = 0.0f;
  private float value = 0.0f;
  private float averageX = 0.0f;
  private float averageY = 0.0f;
  private float averageZ = 0.0f;
  private  int xColumn = 1;
  
  private final int DEFAULT_INDEX_COLUMN_WIDTH = 30;
  private       int indexColumnPixelWidth = DEFAULT_INDEX_COLUMN_WIDTH;
  
  private       int defaultColumnWidth=100; // always holds something
  
  public static Color redForeground = new Color(245, 116, 106);
  private final Color defaultForegroundColor;
  
  private DefaultTableModel                  jTableModel;
  private DefaultRendererShowingRowSelection myDefaultRendererShowingRowSelection= new DefaultRendererShowingRowSelection();

  
  private IsCellEditableIf cellEditableChecker = new IsCellEditableIf()
  {
    // Default is-editable handler only looks at doIndex variable
    @Override
    public boolean isCellEditable(int row, int column)
    {
      return column != 0 || !doIndex;
    }
  };
  
  /** Creates new form ExpandableList */
  public ExpandableList()
  {
    initComponents();
    
    jTableModel = ((DefaultTableModel) jTable.getModel());
    jTableModel.setColumnCount(4);
    jTable.setAutoCreateRowSorter(true); // TODO investigate further, not yet used
    
    //  column width adjustable based on number of column titles
    copiedRow       = null;
    specifiedNewRow = null;

        insertCommasCheckBox.setVisible(isShowEditEnhancements());
    insertLineBreaksCheckBox.setVisible(isShowEditEnhancements());
                 insertLabel.setVisible(isShowEditEnhancements());
        insertCommasCheckBox.setSelected(isInsertCommasSet());
    insertLineBreaksCheckBox.setSelected(isInsertLineBreaksSet());

    jTable.getSelectionModel().addListSelectionListener(new MySelectionListener());
    jTable.addFocusListener(new MyFocusListener());
    
    TableColumnModel columnModel = jTable.getColumnModel();
    if (doIndex) columnModel.getColumn(0).setPreferredWidth(30); // index column
    
    // http://www.techrepublic.com/article/how-to-justify-data-in-a-jtable-cell/5032692
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    renderer.setHorizontalAlignment(JLabel.RIGHT);
    for (int i=0; i < jTable.getColumnCount(); i++)
    {
         jTable.setDefaultRenderer(jTable.getColumnClass(i),renderer);
    }
    jTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    jTable.setColumnSelectionAllowed(true);
    jTable.setRowSelectionAllowed(true);

    // http://edroidx.blogspot.com/2010/12/jcombobox-text-alignment.html
    DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
    dlcr.setHorizontalAlignment(DefaultListCellRenderer.RIGHT);
    operationComboBox.setRenderer(dlcr);
    operationComboBox.setMaximumRowCount(operationComboBox.getItemCount()); // avoid scrolling since it is awkward

                 sortButton.setVisible(false); // only visible if specifically enabled
  uniformKeyIntervalsButton.setVisible(false);
        generatePointsPanel.setVisible(false);
    generatePointsSeparator.setVisible(false);
      openClosedButtonGroup.add(openLoopRadioButton);
      openClosedButtonGroup.add(closedLoopRadioButton);
            openLoopRadioButton.setSelected(true);
            openLoopRadioButton.setVisible(false);
          closedLoopRadioButton.setVisible(false);
    
      plainButtonFont = flipRowOrderButton.getFont().deriveFont(Font.PLAIN);
    italicsButtonFont = flipRowOrderButton.getFont().deriveFont(Font.ITALIC);
    
    flipRowOrderButton.setText ("Unflip Rows");
    flipRowOrderButton.setFont(italicsButtonFont);
    flipRowOrderButton.setMinimumSize(flipRowOrderButton.getSize()); // want consistent button width, no flipping!
    flipRowOrderButton.setText ("Flip Rows");
    flipRowOrderButton.setFont(plainButtonFont);
    flipRowOrderButton.setEnabled(flippableRowData);
    
    titledBorder = new TitledBorder(new EmptyBorder(0, 0, 0, 0), "");
    titledBorder.setTitleColor(this.getForeground());
    defaultForegroundColor = sortButton.getForeground(); // save for reuse
	
	    addPointsButton.setEnabled(false); // default is disabled
	         scalePanel.setEnabled(false); // default is disabled
	openClosedLoopPanel.setEnabled(false); // default is disabled
	   statisticsButton.setVisible(!isDataStringBased() && !isDataBooleanBased()); // statistics only for numeric data
      
    checkSortButtonEnabled ();
    sortOrderTest();
	makeOpenClosedButton.setVisible(false); // default is hidden
  }
  public void setCellEditPanelVisible (boolean visible)
  {
      cellEditPanel.setVisible(visible);
  }
  public void setGeneratePointsPanelVisible (boolean visible)
  {
      generatePointsPanel.setVisible(visible);
  }
  public void setInsertPanelVisible (boolean visible)
  {
      insertPanel.setVisible(visible);
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

    /**
     * @return the doOrientations value
     */
    public boolean isDoOrientations() {
        return doOrientations;
    }

    /**
     * @param doOrientations the doOrientations value to set
     */
    public void setDoOrientations(boolean doOrientations) {
        this.doOrientations = doOrientations;
    }

    /**
     * @return the columnTooltips
     */
    public String[] getColumnToolTips() {
        return columnToolTips;
    }

    /**
     * @param columnToolTips the columnTooltips to set,  developer must ensure long enough
     */
    public void setColumnToolTips(String[] columnToolTips) {
        this.columnToolTips = columnToolTips;
    }

    /**
     * @return the flippableRowData
     */
    public boolean hasFlippableRowData() {
        return flippableRowData;
    }

    /**
     * @param newFlippableRowData the flippableRowData to set
     */
    public void setFlippableRowData(boolean newFlippableRowData) {
        this.flippableRowData = newFlippableRowData;
        flipRowOrderButton.setEnabled(newFlippableRowData);
    }

    /**
     * @return the defaultCellValue
     */
    public String getDefaultCellValue()
    {
        return defaultCellValue;
    }

    /**
     * @param defaultCellValue the defaultCellValue to set
     */
    public void setDefaultCellValue(String defaultCellValue)
    {
        this.defaultCellValue = defaultCellValue;
    }

    /**
     * @return the dataStringBased
     */
    public final boolean isDataStringBased()
    {
        return dataStringBased;
    }

    /**
     * @param dataStringBased the dataStringBased to set
     */
    public void setDataStringBased(boolean dataStringBased)
    {
        this.dataStringBased = dataStringBased;

        // do not show widgets to calculate/edit cell values if string based
        cellEditLabel.setVisible(!dataStringBased);
        cellEditPanel.setVisible(!dataStringBased);
    }

    /**
     * @return the hasChangedValues
     */
    public boolean hasChangedValues() {
        return hasChangedValues;
    }

    /**
     * @return the enumerationValueLoopClosed
     */
    public boolean isEnumerationValueLoopClosed() {
        return enumerationValueLoopClosed;
    }

    /**
     * @param enumerationValueLoopClosed the enumerationValueLoopClosed to set
     */
    public void setEnumerationValueLoopClosed(boolean enumerationValueLoopClosed) {
        this.enumerationValueLoopClosed = enumerationValueLoopClosed;
        if (enumerationValueLoopClosed)
            closedLoopRadioButton.setSelected(true);
        else  openLoopRadioButton.setSelected(true);
    }

    /**
     * @param largeRadianAnglesConfirmed the largeRadianAnglesConfirmed to set
     */
    public void setLargeRadianAnglesConfirmed(boolean largeRadianAnglesConfirmed) {
        this.largeRadianAnglesConfirmed = largeRadianAnglesConfirmed;
    }

    /**
     * @return the indexColumnPixelWidth
     */
    public int getIndexColumnPixelWidth() {
        return indexColumnPixelWidth;
    }

    /**
     * @param indexColumnPixelWidth the indexColumnPixelWidth to set
     */
    public void setIndexColumnPixelWidth(int indexColumnPixelWidth) {
        this.indexColumnPixelWidth = indexColumnPixelWidth;
    }

    /**
     * @return the sortOrderAscending
     */
    public boolean isSortOrderAscending() {
        return sortOrderAscending;
    }

    /**
     * @param sortOrderAscending the sortOrderAscending to set
     */
    public void setSortOrderAscending(boolean sortOrderAscending) {
        this.sortOrderAscending = sortOrderAscending;
    }

	/**
	 * @return the dataBooleanBased
	 */
	public final boolean isDataBooleanBased() {
		return dataBooleanBased;
	}

	/**
	 * @param dataBooleanBased the dataBooleanBased to set
	 */
	public void setDataBooleanBased(boolean dataBooleanBased) {
		this.dataBooleanBased = dataBooleanBased;
	}

  class MyFocusListener implements FocusListener
  {

    @Override
    public void focusGained(FocusEvent e)
    {
//      System.out.println("Focusgained cell: "+jTable.getSelectedColumn()+" "+jTable.getSelectedRow());
    }

    @Override
    public void focusLost(FocusEvent e)
    {
//      System.out.println("Focuslose cell: "+jTable.getSelectedColumn()+" "+jTable.getSelectedRow());
    }

  }
  class MySelectionListener implements ListSelectionListener
  {
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
//      System.out.println("--------sel");
    }
  }

  protected void setTitle(String title)
  {
    //tb.setTitleFont(this.getFont().deriveFont((float)this.getFont().getSize()+2.0f)); // note precise typing needed to get correct method for size
    titledBorder.setTitle(title);
    this.setBorder(titledBorder);
  }

  HashMap<Integer,TableCellEditor> editors = new HashMap<>(5);
  
  public void setColumnEditor(TableCellEditor ed, int col)
  {
    editors.put(col, ed);
  }
  
  public JTable getTable()
  {
    return jTable;
  }
  
  /**
   * If a horizontal scroll bar is desired (useHorizontalScroll=true, column width),
   * If not, (useHorizontalScroll=true, min col width)
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
      jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // normal default

    if(columnTitles != null)
      setColumnTitles(columnTitles);
  }

  public void setColumnTitles(String[] sa)
  {
    columnTitles = sa;
    ((DefaultTableModel) jTable.getModel()).setColumnCount(sa.length);
    TableColumnModel columnModel = jTable.getColumnModel();
    TableCellRenderer headerRenderer = jTable.getTableHeader().getDefaultRenderer();

    for (int c = 0; c < columnModel.getColumnCount(); c++) {
      int w = (c == 0 && doIndex) ? indexColumnPixelWidth : defaultColumnWidth;

      TableColumn column = columnModel.getColumn(c);
      column.setPreferredWidth(w);
      column.setMaxWidth(Integer.MAX_VALUE); // used to fill table width when no horizontal scroller
      if (c > 0) column.setMinWidth(w);

      column.setHeaderValue(columnTitles[c]);
      // set text alignment for header cells
      if(headerRenderer instanceof JLabel)
      ((JLabel)headerRenderer).setHorizontalAlignment(JLabel.CENTER);
    }
  }
  
  public void setHeaderTooltip(String s)
  {
    jTable.getTableHeader().setToolTipText(s); // warning:  overrides column tooltips
  }

  // No method available for
  // public void setHeaderTooltips(String[] array)

  public void setBoldColumn(int c)
  {
    boldColumns.add(c);
  }

  public void doIncludesAlphaColumn(boolean yn)
  {
    includesAlphaColumn = yn;
  }


  public void doTrailingColorChooser()
  {
    doColorChooser = true;
  }
  
  public void doTrailingTextEditButton(boolean yn)
  {
    doTextEditButton = yn;
  }
  /**
   * Identify whether table contains RGB color values, if so then last column of each row shows simple color widget
   * @param resetRedColumn table column holding Red value, -1 for no colors
   */
  public void setRedColumn(int resetRedColumn)
  {
    redColumn = resetRedColumn;
  }
  
  public void doIndexInFirstColumn(boolean tf)
  {
    doIndex = tf;
    if (doIndex)
    {
        TableColumnModel columnModel = jTable.getColumnModel();
        columnModel.getColumn(0).setMinWidth      (getIndexColumnPixelWidth()); // index column
        columnModel.getColumn(0).setMaxWidth  (2 * getIndexColumnPixelWidth()); // index column
        columnModel.getColumn(0).setPreferredWidth(getIndexColumnPixelWidth()); // index column
        boldColumns.add(0);
    }
    // else TODO reversal
  }
    
  public void setTextAlignment(int algn)
  {
    textAlignment = algn;
  }
  
  public void setReadOnlyRow(int row)
  {
    readOnlyRows.add(row);
  }
  
  public void setReadOnlyCell(int row, int col)
  {
    readOnlyCells.add(new CellID(row,col));
  }
  
  public void unsetReadOnlyCell(int row, int col)
  {
    readOnlyCells.remove(new CellID(row,col));
  }
  
  public void setReadOnlyColumn(int col)
  {
    readOnlyColumns.add(col);
  }
  
  public void setCellEditableHandler(IsCellEditableIf handler)
  {
    cellEditableChecker = handler;
  }

  /**
   * disable some buttons if no row is selected
   */
  private void enableDisableRowEditButtons ()
  {
    int       selectedRow = jTable.getSelectedRow();
    boolean isRowSelected = (selectedRow >= 0);
    boolean oneOrMoreRows = (jTable.getModel().getRowCount() >= 1);
    boolean twoOrMoreRows = (jTable.getModel().getRowCount() >= 2);

               copyButton.setEnabled(isRowSelected);
             removeButton.setEnabled(isRowSelected);
               downButton.setEnabled(isRowSelected && (selectedRow < jTable.getModel().getRowCount() - 1));
                 upButton.setEnabled(isRowSelected && (selectedRow > 0));

      removeAllRowsButton.setEnabled(oneOrMoreRows);
uniformKeyIntervalsButton.setEnabled(twoOrMoreRows);
               sortButton.setEnabled(twoOrMoreRows);
                 setFlippableRowData(twoOrMoreRows);

	checkMakeOpenClosedButtonEnabled ();
  }

  @Override
  public void valueChanged(ListSelectionEvent e)
  {
      // TODO consider do not immediately return if editing is still being adjusted, in order to ensure that
      // proper adjustments are made for this changing value before focus changes to another row
      // however must watch exception when removing row...
//    if (e.getValueIsAdjusting())
//        return;
      
    hasChangedValues = true;
    int row        = jTable.getSelectedRow();
    int numberRows = jTable.getModel().getRowCount();
      upButton.setEnabled(numberRows > 1 && row > 0);
    downButton.setEnabled(numberRows > 1 && row < (numberRows - 1));

    enableDisableRowEditButtons();

//    // special check for ProtoInstance fieldValue editing:  if author changes some value of fieldValue entry,
//    // then set "override" boolean to true so that it is saved upon closing
//    int column        = jTable.getSelectedColumn();
//    int numberColumns = jTable.getModel().getColumnCount();
//
//    if ((row != -1) && (row < numberRows) &&
//        jTable.getColumnName(numberColumns - 1).equals("value") && jTable.getColumnName(0).equals("override"))
//    {
//        jTable.getModel().setValueAt(Boolean.TRUE, row, 0);
//    }
  }

  class BoldRenderer extends DefaultTableCellRenderer
  {
    Font boldFont;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (boldFont == null)
        boldFont = comp.getFont().deriveFont(Font.BOLD);
      comp.setFont(boldFont);
      if (comp instanceof JLabel)
        ((JLabel) comp).setHorizontalAlignment(textAlignment);
      return comp;
    }
  }

  class ItalicRenderer extends DefaultTableCellRenderer
  {
    Font italicFont;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (italicFont == null)
          italicFont = comp.getFont().deriveFont(Font.ITALIC);
      comp.setFont(italicFont);
      if (comp instanceof JLabel)
        ((JLabel) comp).setHorizontalAlignment(textAlignment);
      return comp;
    }
  }
  
  class DefaultRendererShowingRowSelection extends DefaultTableCellRenderer
  {
    private JLabel selectedRowLabel;
    DefaultRendererShowingRowSelection()
    {
      selectedRowLabel = new GreyBackgroundRendererComponent(false);
    }
    
    /*
     * Use the default compononent only if the cell is selected...to match back and foreground
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if(comp instanceof JLabel) {
        if(isSelected) {   // treat like row selection
          ((JLabel)comp).setHorizontalAlignment(JLabel.TRAILING);
          return comp;
        }
        else if(table.getSelectedRow() == row) {
          selectedRowLabel.setText(((JLabel)comp).getText());
          return selectedRowLabel;       
        }
      }      
      return comp;
    }
   }
  
  class GreyBackgroundRendererComponent extends JLabel
  {
    GreyBackgroundRendererComponent(boolean tweakBorder)
    {
      setText("blah");
      if(tweakBorder)
        setBorder(BorderFactory.createMatteBorder(0,0,1,0, getBackground()));
      setOpaque(true);
      setBackground(new Color(0.85f,0.85f,0.85f));
      setHorizontalAlignment(JLabel.TRAILING);
    }
    GreyBackgroundRendererComponent()
    {
      this(true);
    }
  }
  
  class IndexRenderer extends DefaultTableCellRenderer
  {
    private JLabel indexLabel;
    IndexRenderer()
    {
      indexLabel = new GreyBackgroundRendererComponent();
    }
    
    /*
     * Use the default compononent only if the cell is selected...to match back and foreground
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if(comp instanceof JLabel) {
        if(isSelected || jTable.getSelectedRow() == row) {
          comp = super.getTableCellRendererComponent(table, value, true /*force*/, hasFocus, row, column);
          ((JLabel)comp).setHorizontalAlignment(JLabel.TRAILING);
        }
        else
          comp = indexLabel;
        
        overwriteText(((JLabel)comp),""+row + "  "); // add two characters of spacing to right
      }      
      return comp;
    }
    
    protected void overwriteText(JLabel lab, String s)
    {
      lab.setText(s);
    }
  }

  class ColorRenderer extends JLabel implements TableCellRenderer
  {
    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered = true;

    public ColorRenderer()
    {
      this(false);
    }

    public ColorRenderer(boolean isBordered)
    {
      this.isBordered = isBordered;
      setOpaque(true); //MUST do this for background to show up.
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int col)
    {
      Color newColor = new Color(0, 0, 0, 1); // default
      try {
        newColor = (Color) color;
      }
      catch (Exception ex) {
        System.out.println("ExpandableList.getTableCellRendererComponent() received illegal color=" + color);
        System.out.println(ex);
      }
      try {       
        if(includesAlphaColumn)
          newColor = new Color(tableFloat(row,col-4), tableFloat(row,col-3), tableFloat(row,col-2), tableFloat(row,col-1));
        else
          newColor = new Color(                       tableFloat(row,col-3), tableFloat(row,col-2), tableFloat(row,col-1), 1.0f);
      }
      catch(Exception ex){//Something wrong with data}
        System.err.println("bp");
      }
      setBackground(newColor);
      if (isBordered) {
        if (isSelected) {
          if (selectedBorder == null) {
            selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getSelectionBackground());
          }
          setBorder(selectedBorder);
        }
        else {
          if (unselectedBorder == null) {
            unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5, table.getBackground());
          }
          setBorder(unselectedBorder);
        }
      }

      // This is probably overridden by the table-wide tooltipper below
      setToolTipText("RGB value: " + newColor.getRed() + ", " + newColor.getGreen() + ", " + newColor.getBlue());
      return this;
    }
  }

  // This is here to update the color chooser when one of the r,g,b,a cells has been hand-editted
  @Override
  public void tableChanged(TableModelEvent e)
  {
    int row = e.getFirstRow();
    int col = e.getColumn();
    int chooserColumn = jTable.getModel().getColumnCount()-1;
    if(row<0 || col<0)
      return;
    if(redColumn < 0)
      return;
    int lastClrCol = redColumn+(includesAlphaColumn?3:2);
    if(col >= redColumn && col <=(redColumn+lastClrCol)) {
      try {
        float r = tableFloat(row,redColumn);
        float g = tableFloat(row,redColumn+1);
        float b = tableFloat(row,redColumn+2);

        if(includesAlphaColumn) {
          float a = tableFloat(row,redColumn+3);
          setValueAtLater(new Color(r,g,b,a), row, chooserColumn); //jTable.setValueAt(new Color(r,g,b,a), row, chooserColumn);
        }
        else
          setValueAtLater(new Color(r,g,b,1.0f), row, chooserColumn); //jTable.setValueAt(new Color(r,g,b), row, chooserColumn);
      }
      catch(Exception ex) {
        System.err.println("Exception in ExpandableList.tableChanged:"+ex.getClass().getSimpleName()+" "+ex.getLocalizedMessage());
      }
    }
  }
  private void setValueAtLater(final Color c, final int r, final int clm)
  {
    EventQueue.invokeLater(() -> {
        jTable.setValueAt(c, r, clm);
    });
  }
  class ColorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener
  {
    ColorChooser colorChooser;
    
    @SuppressWarnings("LeakingThisInConstructor")
    public ColorEditor()
    {
      colorChooser = new ColorChooser();
      colorChooser.addActionListener(this);
    }

    @Override
    public Object getCellEditorValue()
    {
      return colorChooser.getColor();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      fireEditingStopped();
      Color lastColor = colorChooser.getColor();
      int selRow = jTable.getSelectedRow();
      
      float[] fa = lastColor.getColorComponents(null);

      jTable.setValueAt(""+fa[0], selRow, redColumn);
      jTable.setValueAt(""+fa[1], selRow, redColumn+1);
      jTable.setValueAt(""+fa[2], selRow, redColumn+2);
// Color chooser doesn't return alpha
//      if(includesAlphaColumn)
//        jTable.setValueAt(""+fa[3], selRow, redColumn+3);
      
      int chooserColumn = jTable.getModel().getColumnCount()-1;
      jTable.setValueAt(lastColor, selRow, chooserColumn);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
      colorChooser.setColor((Color)value);
      return colorChooser;
    }
  }
  
  public class TextPanEditor extends AbstractCellEditor implements TableCellEditor,ActionListener
  {
    JButton button;

    protected static final String EDIT = "edit";
    private final DialogDescriptor dd;
    private final JTextArea ta;
    
        @SuppressWarnings("LeakingThisInConstructor")
    public TextPanEditor()
    {
      button = new JButton("...");
      button.setActionCommand(EDIT);
      button.addActionListener(this);
      button.setBorderPainted(false);
     
      //Set up the dialog that the button brings up.
      ta = new JTextArea();
      ta.setRows(5);
      ta.setColumns(40);
      ta.setWrapStyleWord(true);
      ta.setLineWrap(true);
      dd = new DialogDescriptor(new JScrollPane(ta),"Edit text");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      int textCol = jTable.getModel().getColumnCount() - 2;  // get previouse col value
      int selRow = jTable.getSelectedRow();
      String currentText = (String) jTable.getValueAt(selRow,textCol);

      //The user has clicked the cell, so
      //bring up the dialog.
      ta.setText(currentText);
      Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
      dialog.setVisible(true);

      fireEditingStopped(); //Make the renderer reappear.

      if (dd.getValue().equals(DialogDescriptor.OK_OPTION))
        jTable.setValueAt(ta.getText().trim(), selRow, jTable.getModel().getColumnCount()-2);
      //else if (dd.getValue().equals(DialogDescriptor.CANCEL_OPTION)) {}
    }

    //Implement the one CellEditor method that AbstractCellEditor doesn't.
    @Override
    public Object getCellEditorValue()
    {
      return "...";
    }

    //Implement the one method defined by TableCellEditor.
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
      return button;
    }
  }
  protected int getRowCount ()
  {
	  if  (getData() == null)
		   return 0;
	  else return getData().length;
  }
  public String[][] getData()
  {
    TableModel tableModel = jTable.getModel();
    int rc = tableModel.getRowCount();
    int cc = tableModel.getColumnCount();
    cc -= (doTextEditButton || doColorChooser)?1:0;
    cc -= doIndex?1:0;
    int tabColStart = doIndex?1:0;  // skip index column as appropriate
    //int tabColEnd = doIndex?cc+1:cc;
    
    String[][] data = new String[rc][cc];
    int col,tCol;
    for (int row = 0; row < rc; row++) 
    {
        for (col = 0, tCol = tabColStart; col < cc; col++, tCol++)
        {
            Object obj = tableModel.getValueAt(row, tCol);
            if (obj instanceof String)
            {
                data[row][col] = (String) obj;
            } 
            else if (obj instanceof Boolean)
            {
                data[row][col] = obj.toString();
            } 
            else
            {
                data[row][col] = "";
            }
        }
    }
    return data;
  }
  
  private Object[][] adjustForColorChooser(Object[][] data)
  {
    numJTableColumns = data[0].length; //((DefaultTableModel) jTable.getModel()).getColumnCount();
    numJTableColumns += doColorChooser ? 1 : 0;
    Object[][] oaa = new Object[data.length][numJTableColumns];
    for (int r = 0; r < data.length; r++) {
      for (int c = 0; c < numJTableColumns; c++) {
        if (doColorChooser && c == numJTableColumns - 1)
          oaa[r][c] = makeColor(data[r],redColumn); 
        else
          oaa[r][c] = data[r][c];
      }
    }
    return oaa;
  }
  
  private Object[][] adjustForIndex(Object[][] data)
  {
    if (!doIndex)
        return data; // no change
    numJTableColumns = data[0].length; //((DefaultTableModel) jTable.getModel()).getColumnCount();
    numJTableColumns += doIndex ? 1 : 0;
    Object[][] oaa = new Object[data.length][numJTableColumns];
    for (int r = 0; r < data.length; r++) {
      for (int c = 0; c < numJTableColumns; c++) {
        oaa[r][c] = (c==0)?r:data[r][c-1]; 
       }
    }
    return oaa;
  }
  
  private Object[][] adjustForKeyColumn(Object[][] data)
  {
    if (!isKeyColumnIncluded())
        return data; // no change
    numJTableColumns = data[0].length; //((DefaultTableModel) jTable.getModel()).getColumnCount();
    numJTableColumns += isKeyColumnIncluded() ? 1 : 0;
    Object[][] oaa = new Object[data.length][numJTableColumns];
    for (int r = 0; r < data.length; r++) {
      for (int c = 0; c < numJTableColumns; c++) {
        oaa[r][c] = (c==0)?r:data[r][c-1]; 
       }
    }
    return oaa;
  }
  
  private Object[][] adjustForTextButton(Object[][] data)
  {
    numJTableColumns = data[0].length; //((DefaultTableModel) jTable.getModel()).getColumnCount();
    numJTableColumns += doTextEditButton ? 1 : 0;
    Object[][] oaa = new Object[data.length][numJTableColumns];
    for (int r = 0; r < data.length; r++) {
      for (int c = 0; c < numJTableColumns; c++) {
        if (doTextEditButton && c == numJTableColumns - 1)
          oaa[r][c] = "..."; 
        else
          oaa[r][c] = data[r][c];
      }
    }
    return oaa;
  }

  float tableFloat(int row, int column)
  {
    String s = (String)jTable.getValueAt(row, column);
    return parseFloat(s);
  }

  private Color makeColor(Object[]sa, int startingColumn)
  {
    int column = startingColumn;
    if ((column < 0) && (sa.length >= 4)) {
      System.err.println("Warning, red column not set in ExpandableList");
      column = sa.length-4;    // 4th from end
    }
      else if (sa.length < 4) {
      System.err.println("Warning, insufficient object size in ExpandableList, using default Color of white");
      return new Color(1.0f,1.0f,1.0f,1.0f);
    }

    float r = parseFloat((String)sa[column+0]);
    float g = parseFloat((String)sa[column+1]);
    float b = parseFloat((String)sa[column+2]);
    // avoid exception when creating Color value
    r = clamp (r, 0.0f, 1.0f);
    g = clamp (g, 0.0f, 1.0f);
    b = clamp (b, 0.0f, 1.0f);
    if(includesAlphaColumn) {
      float a = parseFloat((String)sa[column+3]);
      a = clamp (a, 0.0f, 1.0f);
      return new Color(r,g,b,a);
    }
    return new Color(r,g,b,1.0f);
  }
  private float clamp (float value, float min, float max)
  {
      if      (value > max)
           return max;
      else if (value > min)
           return min;
      else return value;
  }
  
  private float parseFloat(String s)
  {
    float f = 0.0f;
    try {
      f = (new SFFloat(s)).getValue();
    }
    catch(Exception e) {}
    return f;
  }
  
  class myDefaultTableModel extends DefaultTableModel
  {
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
      int cc = this.getColumnCount();
      Object obj = getValueAt(0,columnIndex);
      if(obj == null)
        return Object.class;
      return obj.getClass();
    }
  }

  public void appendRow (Object[] newRow)
  {
    Object[][] oldData = getData();
    if ((oldData != null) && (oldData[0] != null) && (newRow != null) && (oldData[0].length == newRow.length))
    {
        Object[][] newData = new Object[oldData.length + 1][oldData[0].length];
        for (int row = 0; row < oldData.length; row++)
        {
            newData[row] = oldData[row];
        }
        newData[oldData.length] = newRow;
       _setData(newData);
    }
    else
    {
        System.out.println ("Error:  ExpandableList appendRow new data has incorrect length, ignored: " +
		Arrays.toString(newRow));
    }
  }

  public void setData(Object[][] data)
  {
    _setData(data);
  }
  public void setData(String[] stringArray)
  {
    String[][] odata;

    TableModel tableModel = jTable.getModel();
    int columnCount = tableModel.getColumnCount();
    // index column always included
    if ((columnCount == 2) && !isKeyColumnIncluded()) // single-value array
    {
        odata = new String[stringArray.length][1];
        for (int i=0; i < stringArray.length; i++)
        {
            odata[i][0] = stringArray[i];
        }
    }
    else if ((columnCount == 3) &&  isKeyColumnIncluded()) // single-value array
    {
        odata = new String[stringArray.length][2];
        for (int i=0; i < stringArray.length; i++)
        {
            odata[i][0] = "0"; // strongly encourage author to define key values
            odata[i][1] = stringArray[i];
        }
    }
    else if (((stringArray.length + 1) == columnCount) && !isKeyColumnIncluded()) // multi-value array
    {
        odata = new String[1][stringArray.length];
        System.arraycopy(stringArray, 0, odata[0], 0, stringArray.length);
    }
    else if (((stringArray.length + 1) == columnCount) &&  isKeyColumnIncluded()) // multi-value array
    {
        odata = new String[1][stringArray.length + 1];
        odata[0][0] = "0"; // strongly encourage author to define key values
        System.arraycopy(stringArray, 0, odata[0], 1, stringArray.length);
    }
    else // fill array as best possible
    {
        int dataColumnCount = (columnCount - 1); // account for index column
        int expectedRows = stringArray.length / dataColumnCount;
        
        odata = new String[expectedRows][dataColumnCount];
        int k = 0;
        for (int row=0; row < expectedRows; row++)
        {
            for (int col=0; col < dataColumnCount; col++)
            {
                if  (k < stringArray.length)
                     odata[row][col] = stringArray[k];
                else odata[row][col] = ""; // handles non-rectangular inputs
                k++;
            }
        }
    }

    _setData(odata);
  }
  private void _setData(Object[][] data)
  {
    boolean cameInEmpty = false;
    Object[][] odata = data;
    int columnWidth = _getDataWidthFromJTable();

    if (odata == null || odata.length == 0 || odata[0].length == 0 || (odata[0][0] == null))
    {
      Object[] oa = new Object[columnWidth];
      cameInEmpty = true;

      if (copiedRow != null)
      {
//      oa = Arrays.copyOf(copiedRow, oa.length);
        for (int col=1; col <= columnWidth; col++)
        {
            oa[col-1] = copiedRow[col]; // TODO verify zero-based, fix
        }
      }
      else if (specifiedNewRow != null)
        oa = Arrays.copyOf(specifiedNewRow, oa.length);
      else
        Arrays.fill(oa, defaultCellValue);

      odata = new Object[][]{oa};
    }

    // TODO questionable
    if (doIndex)
    {
        odata = adjustForIndex(odata);
        if ((odata != null) && (odata.length >= 100) && (indexColumnPixelWidth == DEFAULT_INDEX_COLUMN_WIDTH))
            indexColumnPixelWidth = Math.max (indexColumnPixelWidth, 2 * DEFAULT_INDEX_COLUMN_WIDTH);
        
        if (doIndex) // reset
        {
            TableColumnModel columnModel = jTable.getColumnModel();
            columnModel.getColumn(0).setMinWidth(getIndexColumnPixelWidth()); // index column
            columnModel.getColumn(0).setMaxWidth(2 *   getIndexColumnPixelWidth()); // index column
            columnModel.getColumn(0).setPreferredWidth(getIndexColumnPixelWidth()); // index column
            boldColumns.add(0);
            this.validate();
        }
    }
    if (doColorChooser)
      odata = adjustForColorChooser(odata);
    else if (doTextEditButton)
      odata = adjustForTextButton(odata);

// should not copy row here!
//    if ((odata[0].length > 0) && (copiedRow == null))// do not reset if all rows removed
//    {
//      copiedRow = (Object[]) odata[0].clone();
//      if (copiedRow[0].equals(" ") || copiedRow[0].equals(""))
//        copiedRow[0] = "0";
//    }

    // Add to data model
    numJTableColumns = columnTitles.length; // avoids any counting problems when copying columnTitles
    String[] cols = new String[numJTableColumns];
    System.arraycopy(columnTitles, 0, cols, 0, cols.length);

    // problematic fragment - should not be here? apparent cause of invocation errors, fails hard if placed in init section
    if (jTable.getModel() == null)
        jTable.setModel(new myDefaultTableModel());
    // do not override renderer, or else other overrides (such as ExternProtoDeclare) will not work
    // jTable.setDefaultRenderer(Object.class, myDefaultRendererShowingRowSelection);
    jTable.getModel().addTableModelListener(this);
    jTable.getSelectionModel().addListSelectionListener(this);
    ((DefaultTableModel) jTable.getModel()).setDataVector(odata, cols);

    // set text alignment for header cells
    TableCellRenderer hdrRend = jTable.getTableHeader().getDefaultRenderer();
    if(hdrRend instanceof JLabel)
      ((JLabel)hdrRend).setHorizontalAlignment(JLabel.CENTER);
    
    // text alignment, flipped-row italics for value columns
    TableCellRenderer cr = jTable.getDefaultRenderer(String.class);
    if (cr instanceof JLabel)
      ((JLabel) cr).setHorizontalAlignment(textAlignment);

    // special renderers for key columns
    if (boldColumns.size() > 0)
    {
      TableCellRenderer myRenderer = new   BoldRenderer();

      TableColumnModel columnModel = jTable.getColumnModel();
      for (int c : boldColumns) {
        TableColumn tc = columnModel.getColumn(c);
             tc.setCellRenderer(myRenderer);
      }
    }
    // To establish width
    if(columnTitles != null)
      setColumnTitles(columnTitles);
    
    JLabel dummy = new JLabel("OOO");
    int width3Cols = dummy.getPreferredSize().width;

    TableColumnModel tcm = jTable.getColumnModel();
    if (doIndex) {
      TableCellRenderer tcr = idxRenderer;
      TableColumn tc = tcm.getColumn(0);
      tc.setCellRenderer(tcr);
      tc.setPreferredWidth(width3Cols);
      tc.setMaxWidth(width3Cols);
      tc.setMinWidth(width3Cols);
    }
    if (doColorChooser) {
      TableColumn tc = tcm.getColumn(numJTableColumns - 1); // last one is chooser
      tc.setCellRenderer(new ColorRenderer());
      tc.setCellEditor(new ColorEditor());
    }
    else if (doTextEditButton) {
      TableColumn tc = tcm.getColumn(numJTableColumns - 1); // last one is chooser
      tc.setPreferredWidth(width3Cols);
      tc.setMaxWidth(width3Cols);
      tc.setMinWidth(width3Cols);

      tc.setCellEditor(new TextPanEditor());
    }

    if (!editors.isEmpty())
	{
		for (int i = 0; i < numJTableColumns; i++)
		{
			TableCellEditor ed = editors.get(i);
			if (ed != null)
			{
				TableColumn tc = jTable.getColumnModel().getColumn(i);
				tc.setCellEditor(ed);
			}
		}
	}

    if (cameInEmpty)
    {
        ((DefaultTableModel) jTable.getModel()).removeRow(0);
    }
    checkForBooleanContent ();

    enableDisableRowEditButtons(); // check buttons enabled/disabled
    checkSortButtonEnabled ();
    sortOrderTest();
    
    this.validate();
  }
  /**
   * Check for boolean content in table, if so then operationComboBox only prompts with relevant operator
   */
    private void checkForBooleanContent ()
    {
        String cellContents;
        TableModel tableModel = jTable.getModel();
        if                ((tableModel.getRowCount() > 0) &&
                           (tableModel.getValueAt(0, 0) != null))
             cellContents = tableModel.getValueAt(0, 0).toString();
        else cellContents = "";
        if (cellContents.equals("true") || cellContents.equals("false") || dataBooleanBased)
        {
            dataBooleanBased = true; // remember previous setting if data table goes down to zero
            operationComboBox.setSelectedIndex(NEGATE_OPERATION);
            operationComboBox.setToolTipText("Can only negate boolean values");
          editFactorTextField.setEnabled(false);
          editFactorTextField.setToolTipText("");
        }
    }

  /**
   * Set the default values when adding a new row of data, inserts "0" if not already set.
   * Example:  expandableList.setNewRowData(new String[]{"1","1"});
   * @param objectArray the array of default row data values to set
   */
  public void setNewRowData(Object[] objectArray)
  {
    specifiedNewRow = objectArray;
  }

  private int _getDataWidthFromJTable()
  {
    int dataWidth;
    try
    {
        dataWidth = jTable.getModel().getColumnCount();
    }
    catch (Exception e)
    {
        dataWidth = 0;
    }
    dataWidth -= this.doColorChooser?1:0;
    dataWidth -= this.doIndex?1:0;
    dataWidth -= this.doTextEditButton?1:0;
    return Math.max(dataWidth, 0);
  }

  private Object[] makeEmptyJTableDataRow(Object[] userDataOnly)
  {
    Object[] oa = new Object[jTable.getModel().getColumnCount()];
    int start = doIndex?1:0;
      for (Object userDataOnly1 : userDataOnly) {
          oa[start] = userDataOnly1;
          start++;
      }
    if(doColorChooser)
      oa[oa.length-1] = makeColor(oa,redColumn);  // needs the three (or 4 (alpha) ) columns starting at redColumn

    return oa;
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
    if (doColorChooser) {
        finalColumnIndex--;
    }
    if (doTextEditButton) {
        finalColumnIndex--;
    }
    finalRowIndex = jTable.getModel().getRowCount() - 1;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        openClosedButtonGroup = new javax.swing.ButtonGroup();
        jTableScrollPane = new javax.swing.JScrollPane();
        jTable = new JTableWithReadOnlyColumns();
        interfacePanel = new javax.swing.JPanel();
        rowButtonPanel = new javax.swing.JPanel();
        rowEditLabel = new javax.swing.JLabel();
        selectAllButton = new javax.swing.JButton();
        copyButton = new javax.swing.JButton();
        appendButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        flipRowOrderButton = new javax.swing.JButton();
        sortButton = new javax.swing.JButton();
        uniformKeyIntervalsButton = new javax.swing.JButton();
        spacerLabel = new javax.swing.JLabel();
        removeAllRowsButton = new javax.swing.JButton();
        makeOpenClosedButton = new javax.swing.JButton();
        cellEditSeparator = new javax.swing.JSeparator();
        cellEditPanel = new javax.swing.JPanel();
        cellEditLabel = new javax.swing.JLabel();
        operationComboBox = new javax.swing.JComboBox<>();
        editFactorTextField = new javax.swing.JTextField();
        cellSelectionComboBox = new javax.swing.JComboBox<>();
        applyCellFactorButton = new javax.swing.JButton();
        insertPanel = new javax.swing.JPanel();
        insertLabel = new javax.swing.JLabel();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        generatePointsPanel = new javax.swing.JPanel();
        generatePointsSeparator = new javax.swing.JSeparator();
        generateLabel = new javax.swing.JLabel();
        generatePointsComboBox = new javax.swing.JComboBox<>();
        addPointsButton = new javax.swing.JButton();
        numberOfPointsTextField = new javax.swing.JTextField();
        scalePanel = new javax.swing.JPanel();
        scaleLabel = new javax.swing.JLabel();
        scaleTextField = new javax.swing.JTextField();
        openClosedLoopPanel = new javax.swing.JPanel();
        openLoopRadioButton = new javax.swing.JRadioButton();
        closedLoopRadioButton = new javax.swing.JRadioButton();
        generatePointsSpacerLabel = new javax.swing.JLabel();
        statisticsButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(300, 110));
        setPreferredSize(new java.awt.Dimension(680, 400));
        setLayout(new java.awt.GridBagLayout());

        jTableScrollPane.setPreferredSize(new java.awt.Dimension(200, 80));

        jTable.setModel(new DefaultTableModel());
        jTable.setColumnSelectionAllowed(true);
        jTable.setRowHeight(14);
        jTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTableFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTableFocusLost(evt);
            }
        });
        jTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTablePropertyChange(evt);
            }
        });
        jTableScrollPane.setViewportView(jTable);
        jTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jTableScrollPane, gridBagConstraints);

        interfacePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        interfacePanel.setLayout(new java.awt.GridBagLayout());

        rowButtonPanel.setLayout(new java.awt.GridBagLayout());

        rowEditLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rowEditLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.rowEditLabel.text")); // NOI18N
        rowEditLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        rowEditLabel.setMaximumSize(new java.awt.Dimension(52, 16));
        rowEditLabel.setMinimumSize(new java.awt.Dimension(52, 16));
        rowEditLabel.setPreferredSize(new java.awt.Dimension(52, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 1);
        rowButtonPanel.add(rowEditLabel, gridBagConstraints);

        selectAllButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.selectAllButton.text_1")); // NOI18N
        selectAllButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.selectAllButton.toolTipText")); // NOI18N
        selectAllButton.setEnabled(false);
        selectAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(selectAllButton, gridBagConstraints);

        copyButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.copyButton.text_1")); // NOI18N
        copyButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COPY_BUTTON"));
        copyButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(copyButton, gridBagConstraints);

        appendButton.setText("Append");
        appendButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_ADD_BUTTON"));
        appendButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        appendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(appendButton, gridBagConstraints);

        removeButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.removeButton.text")); // NOI18N
        removeButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_DELETE_BUTTON"));
        removeButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(removeButton, gridBagConstraints);

        upButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/upArrow.png"))); // NOI18N
        upButton.setText(NbBundle.getMessage(getClass(), "ExpandableList.upButton.text")); // NOI18N
        upButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_UP_BUTTON"));
        upButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        upButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(upButton, gridBagConstraints);

        downButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/downArrow.png"))); // NOI18N
        downButton.setText(NbBundle.getMessage(getClass(), "ExpandableList.downButton.text")); // NOI18N
        downButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_DOWN_BUTTON"));
        downButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        downButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(downButton, gridBagConstraints);

        flipRowOrderButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.flipRowOrderButton.text_1")); // NOI18N
        flipRowOrderButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.flipRowOrderButton.toolTipText")); // NOI18N
        flipRowOrderButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        flipRowOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flipRowOrderButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(flipRowOrderButton, gridBagConstraints);

        sortButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.sortButton.text")); // NOI18N
        sortButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.sortButton.toolTipText")); // NOI18N
        sortButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        sortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 2, 3, 3);
        rowButtonPanel.add(sortButton, gridBagConstraints);

        uniformKeyIntervalsButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.uniformKeyIntervalsButton.text_1")); // NOI18N
        uniformKeyIntervalsButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.uniformKeyIntervalsButton.toolTipText")); // NOI18N
        uniformKeyIntervalsButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        uniformKeyIntervalsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uniformKeyIntervalsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(uniformKeyIntervalsButton, gridBagConstraints);

        spacerLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.spacerLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 100.0;
        rowButtonPanel.add(spacerLabel, gridBagConstraints);

        removeAllRowsButton.setText("--");
        removeAllRowsButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.removeAllRowsButton.toolTipText")); // NOI18N
        removeAllRowsButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        removeAllRowsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllRowsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        rowButtonPanel.add(removeAllRowsButton, gridBagConstraints);

        makeOpenClosedButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.makeOpenClosedButton.text_1")); // NOI18N
        makeOpenClosedButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.makeOpenClosedButton.toolTipText")); // NOI18N
        makeOpenClosedButton.setMargin(new java.awt.Insets(2, 4, 2, 4));
        makeOpenClosedButton.setMaximumSize(new java.awt.Dimension(85, 23));
        makeOpenClosedButton.setMinimumSize(new java.awt.Dimension(85, 23));
        makeOpenClosedButton.setPreferredSize(new java.awt.Dimension(85, 23));
        makeOpenClosedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeOpenClosedButtonActionPerformed(evt);
            }
        });
        rowButtonPanel.add(makeOpenClosedButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        interfacePanel.add(rowButtonPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 2, 0);
        interfacePanel.add(cellEditSeparator, gridBagConstraints);

        cellEditPanel.setLayout(new java.awt.GridBagLayout());

        cellEditLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cellEditLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.cellEditLabel.text")); // NOI18N
        cellEditLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        cellEditLabel.setMaximumSize(new java.awt.Dimension(52, 16));
        cellEditLabel.setMinimumSize(new java.awt.Dimension(52, 16));
        cellEditLabel.setPreferredSize(new java.awt.Dimension(52, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 1);
        cellEditPanel.add(cellEditLabel, gridBagConstraints);

        operationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Assign cell value:", "Add factor:", "Subtract factor:", "Multiply by factor:", "Divide by factor:", "Divide into factor:", "Round cell value", "Negate cell value", "Center all values:", "Rotate 2D values:" }));
        operationComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.operationComboBox.toolTipText")); // NOI18N
        operationComboBox.setMinimumSize(new java.awt.Dimension(125, 22));
        operationComboBox.setPreferredSize(new java.awt.Dimension(125, 22));
        operationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        cellEditPanel.add(operationComboBox, gridBagConstraints);

        editFactorTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        editFactorTextField.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.editFactorTextField.text")); // NOI18N
        editFactorTextField.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.editFactorTextField.toolTipText")); // NOI18N
        editFactorTextField.setMinimumSize(new java.awt.Dimension(60, 20));
        editFactorTextField.setPreferredSize(new java.awt.Dimension(60, 20));
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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        cellEditPanel.add(editFactorTextField, gridBagConstraints);

        cellSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "to selected cell", "to selected row", "to selected column", "to all cells" }));
        cellSelectionComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.cellSelectionComboBox.toolTipText")); // NOI18N
        cellSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cellSelectionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        cellEditPanel.add(cellSelectionComboBox, gridBagConstraints);

        applyCellFactorButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.applyCellFactorButton.text")); // NOI18N
        applyCellFactorButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.applyCellFactorButton.toolTipText")); // NOI18N
        applyCellFactorButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        applyCellFactorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyCellFactorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        cellEditPanel.add(applyCellFactorButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        interfacePanel.add(cellEditPanel, gridBagConstraints);

        insertPanel.setLayout(new java.awt.GridBagLayout());

        insertLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.insertLabel.text")); // NOI18N
        insertLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.insertLabel.toolTipText")); // NOI18N
        insertLabel.setMaximumSize(null);
        insertLabel.setMinimumSize(null);
        insertLabel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        insertPanel.add(insertLabel, gridBagConstraints);

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasCheckBox.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.insertCommasCheckBox.text")); // NOI18N
        insertCommasCheckBox.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COMMAS"));
        insertCommasCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertCommasCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        insertPanel.add(insertCommasCheckBox, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.insertLineBreaksCheckBox.text")); // NOI18N
        insertLineBreaksCheckBox.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_LINE_BREAKS"));
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        insertPanel.add(insertLineBreaksCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        interfacePanel.add(insertPanel, gridBagConstraints);

        generatePointsPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 0);
        generatePointsPanel.add(generatePointsSeparator, gridBagConstraints);

        generateLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        generateLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.generateLabel.text")); // NOI18N
        generateLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.generateLabel.toolTipText")); // NOI18N
        generateLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        generateLabel.setMaximumSize(new java.awt.Dimension(52, 16));
        generateLabel.setMinimumSize(new java.awt.Dimension(52, 16));
        generateLabel.setPreferredSize(new java.awt.Dimension(52, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 1);
        generatePointsPanel.add(generateLabel, gridBagConstraints);

        generatePointsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select geometry:", "Item 2", "Item 3", "Item 4" }));
        generatePointsComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.generatePointsComboBox.toolTipText")); // NOI18N
        generatePointsComboBox.setMinimumSize(new java.awt.Dimension(125, 22));
        generatePointsComboBox.setPreferredSize(new java.awt.Dimension(125, 22));
        generatePointsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatePointsComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        generatePointsPanel.add(generatePointsComboBox, gridBagConstraints);

        addPointsButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.addPointsButton.text_1")); // NOI18N
        addPointsButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.addPointsButton.toolTipText")); // NOI18N
        addPointsButton.setEnabled(false);
        addPointsButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        addPointsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPointsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        generatePointsPanel.add(addPointsButton, gridBagConstraints);

        numberOfPointsTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numberOfPointsTextField.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.numberOfPointsTextField.text")); // NOI18N
        numberOfPointsTextField.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.numberOfPointsTextField.toolTipText")); // NOI18N
        numberOfPointsTextField.setEnabled(false);
        numberOfPointsTextField.setMinimumSize(new java.awt.Dimension(60, 20));
        numberOfPointsTextField.setPreferredSize(new java.awt.Dimension(60, 20));
        numberOfPointsTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                numberOfPointsTextFieldFocusLost(evt);
            }
        });
        numberOfPointsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberOfPointsTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        generatePointsPanel.add(numberOfPointsTextField, gridBagConstraints);

        scalePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scalePanel.setLayout(new java.awt.GridBagLayout());

        scaleLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.scaleLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        scalePanel.add(scaleLabel, gridBagConstraints);

        scaleTextField.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.scaleTextField.text")); // NOI18N
        scaleTextField.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.scaleTextField.toolTipText")); // NOI18N
        scaleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 30;
        scalePanel.add(scaleTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        generatePointsPanel.add(scalePanel, gridBagConstraints);

        openClosedLoopPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        openClosedLoopPanel.setMinimumSize(new java.awt.Dimension(115, 25));
        openClosedLoopPanel.setPreferredSize(new java.awt.Dimension(115, 25));
        openClosedLoopPanel.setLayout(new java.awt.GridBagLayout());

        openClosedButtonGroup.add(openLoopRadioButton);
        openLoopRadioButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.openLoopRadioButton.text")); // NOI18N
        openLoopRadioButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.openLoopRadioButton.toolTipText")); // NOI18N
        openLoopRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openLoopRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        openClosedLoopPanel.add(openLoopRadioButton, gridBagConstraints);

        openClosedButtonGroup.add(closedLoopRadioButton);
        closedLoopRadioButton.setSelected(true);
        closedLoopRadioButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.closedLoopRadioButton.text")); // NOI18N
        closedLoopRadioButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.closedLoopRadioButton.toolTipText")); // NOI18N
        closedLoopRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closedLoopRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        openClosedLoopPanel.add(closedLoopRadioButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        generatePointsPanel.add(openClosedLoopPanel, gridBagConstraints);

        generatePointsSpacerLabel.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.generatePointsSpacerLabel.text")); // NOI18N
        generatePointsSpacerLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.generatePointsSpacerLabel.toolTipText")); // NOI18N
        generatePointsSpacerLabel.setMaximumSize(new java.awt.Dimension(12, 25));
        generatePointsSpacerLabel.setMinimumSize(new java.awt.Dimension(12, 25));
        generatePointsSpacerLabel.setPreferredSize(new java.awt.Dimension(12, 25));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        generatePointsPanel.add(generatePointsSpacerLabel, gridBagConstraints);

        statisticsButton.setText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.statisticsButton.text_1")); // NOI18N
        statisticsButton.setToolTipText(org.openide.util.NbBundle.getMessage(ExpandableList.class, "ExpandableList.statisticsButton.toolTipText")); // NOI18N
        statisticsButton.setMargin(new java.awt.Insets(2, 6, 2, 6));
        statisticsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statisticsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        generatePointsPanel.add(statisticsButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        interfacePanel.add(generatePointsPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(interfacePanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  protected void appendRow (java.awt.event.ActionEvent evt)
  {
      appendButtonActionPerformed (evt);
  }
  private int getInsertionRow ()
  {
      int insertRowNumber;
      if (jTable.getModel().getRowCount() == 0)
      {
          insertRowNumber = 0;
      }
      else if (jTable.getSelectedRow() == -1) // if no selection, go to last row
      {
          insertRowNumber = jTable.getModel().getRowCount();
      }
      else // selected row
      {
          insertRowNumber = insertRowFilter.insertRowNumber(jTable.getSelectedRow());
          if (insertRowNumber == -1) // none was selected
          {
            insertRowNumber = jTable.getModel().getRowCount(); // last row
          }
          else if (insertRowNumber < jTable.getModel().getRowCount())
                   insertRowNumber++; // appending
      }
      insertRowFilter.insertRowNumber(insertRowNumber);
      return insertRowNumber;
  }
  private void checkSortButtonEnabled ()
  {
      jTableModel = ((DefaultTableModel) jTable.getModel());
      int length = jTableModel.getRowCount();
      if (length < 2)
      {
           sortButton.setEnabled(false);
      }
      else sortButton.setEnabled(true);
  }
          
  private void appendButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_appendButtonActionPerformed
  {//GEN-HEADEREND:event_appendButtonActionPerformed
      int insertRowNumber = getInsertionRow ();
      int columnWidth = _getDataWidthFromJTable();
      Object[][] oa = new Object[1][columnWidth]; // data width does not include index column
      // now set object array oa
      if (copiedRow != null)
      {
          for (int col = 0; col < columnWidth; col++) // column 0 is panel index
          {
              if  (col >= copiedRow.length)
                   System.out.println ("Error:  ExpandableList addButton copiedRow index=" + col + " columnWidth=" + columnWidth + " error, ignored");
              else oa[0][col] = copiedRow[col];
          }
          if (isKeyColumnIncluded() && (insertRowNumber >= 1) && (!jTable.getModel().getValueAt(insertRowNumber-1, 1).equals("0")))
          {
              // set insertion key equal to key immediately preceding, try to keep monotonically increasing
              oa[0][0] = jTable.getModel().getValueAt(insertRowNumber-1, 1);
          }
      }
      else if (specifiedNewRow != null)
      {
          for (int col = 0; col < columnWidth; col++) // no index column
          {
              if  (columnWidth > specifiedNewRow.length)
                   System.out.println ("Error:  ExpandableList addButton specifiedNewRow columnWidth " + columnWidth + " error, ignored");
              else oa[0][col] = specifiedNewRow[col];
          }
          if (isKeyColumnIncluded() && (insertRowNumber >= 1))
              oa[0][0] = jTable.getModel().getValueAt(insertRowNumber-1, 1); // set insertion key equal to key immediately preceding
      }
      else // should be unreachable if a default set of row values was properly provided
      {
          for (int col = 0; col < columnWidth; col++)
          {
              oa[0][col] =  getDefaultCellValue();
          }
      }

//      /* If there's nothing in the table now, we've got to setup the initial renderers, etc. */
//      if (jTable.getModel().getRowCount() <= 0)
//      {
//          _setData(oa); //new Object[][]{oa});
//
//          if ((enumerationValues == null) || (enumerationValues.length == 0)) return;
//      }

      Object[] tableData = makeEmptyJTableDataRow(oa[0]);

//    if (this.doColorChooser) {
//      int numCols = jTable.getModel().getColumnCount();
//
//      if (oa.length < numCols) {
//        Object[] newOa = new Object[numCols];
//        Arrays.fill(newOa, "0");
//        int i = 0;
//        for (Object o : oa)
//          newOa[i++] = o;
//        oa = newOa;
//      }
//      oa[numCols - 1] = makeColor(oa, redColumn);
//    }

      ((DefaultTableModel) jTable.getModel()).insertRow(insertRowNumber, tableData);

      if (doIndex) // insert value in index column
      {
          jTable.getModel().setValueAt(Integer.toString(insertRowNumber), insertRowNumber, 0);
      }
      jTable.getSelectionModel().setSelectionInterval(insertRowNumber, insertRowNumber);
      jTable.scrollRectToVisible(jTable.getCellRect(insertRowNumber, 0, true));
      insertRowNumber++; // keep appending next time
          
      enableDisableRowEditButtons(); // check buttons enabled/disabled
      checkSortButtonEnabled ();
      sortOrderTest();
      this.firePropertyChange (evt.getActionCommand(), "", "AppendRow" + jTable.getModel().getRowCount());
}//GEN-LAST:event_appendButtonActionPerformed

  protected void setHighlightedRow (int row)
  {
      // TODO highlight index of selected row
      if ((row >= 0) && (row < jTable.getRowCount()))
      {
          jTable.getSelectionModel().setSelectionInterval(row, row);
          jTable.scrollRectToVisible(jTable.getCellRect(row, 0, true));
      }
  }
  private void removeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeButtonActionPerformed
  {//GEN-HEADEREND:event_removeButtonActionPerformed
    int deleteRowNumber = removeRowFilter.removeRowNumber(jTable.getSelectedRow());
    if ((deleteRowNumber == -1) || (deleteRowNumber > jTable.getModel().getRowCount()))
        return;
    ((DefaultTableModel) jTable.getModel()).removeRow(deleteRowNumber);

    if (deleteRowNumber > 0) deleteRowNumber--; // back up so that successive adds can be followed by successive deletes
    jTable.getSelectionModel().setSelectionInterval(deleteRowNumber, deleteRowNumber);
    jTable.scrollRectToVisible(jTable.getCellRect(deleteRowNumber, 0, true));
    
    enableDisableRowEditButtons(); // check buttons enabled/disabled
    checkSortButtonEnabled ();
    sortOrderTest();
    this.firePropertyChange (evt.getActionCommand(), "", "RemoveRow " + deleteRowNumber);
}//GEN-LAST:event_removeButtonActionPerformed

private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
  int row = jTable.getSelectedRow();
  if (row == -1 || row >= (jTable.getModel().getRowCount() - 1))
      return; // unselected or final row:  no action to perform

  jTable.getSelectionModel().setLeadSelectionIndex(row);
  ((DefaultTableModel)jTable.getModel()).moveRow(row,row,row+1);

  // swap keys back to restore original key order, unless key cell itself is selected
  boolean isKeyCellSelected = (isKeyColumnIncluded() && (jTable.getSelectedColumn() == 1));
  if (isKeyColumnIncluded() && !isKeyCellSelected)
  {
      // note index in column 0
      Object hold = jTable.getValueAt    (           row,   1);
      jTable.setValueAt(jTable.getValueAt(row+1, 1), row,   1);
      jTable.setValueAt(hold,             row+1, 1);
  }
  jTable.getSelectionModel().setSelectionInterval(row+1, row+1);
  jTable.scrollRectToVisible(jTable.getCellRect(row+1, 0, true));
  enableDisableRowEditButtons();
  sortOrderTest();
  this.firePropertyChange (evt.getActionCommand(), "", "downButton");
}//GEN-LAST:event_downButtonActionPerformed

private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
  int row = jTable.getSelectedRow();
  if (row <=0)
      return; // unselected or initial row:  no action to perform

  jTable.getSelectionModel().setLeadSelectionIndex(row); //
  ((DefaultTableModel)jTable.getModel()).moveRow(row,row,row-1);

  // swap keys back to restore original key order, unless key cell itself is selected
  boolean isKeyCellSelected = (isKeyColumnIncluded() && (jTable.getSelectedColumn() == 1));
  if (isKeyColumnIncluded() && !isKeyCellSelected)
  {
      // note index in column 0
      Object hold = jTable.getValueAt    (           row,   1);
      jTable.setValueAt(jTable.getValueAt(row-1, 1), row,   1);
      jTable.setValueAt(hold,             row-1, 1);
  }
  jTable.getSelectionModel().setSelectionInterval(row-1, row-1);
  jTable.scrollRectToVisible(jTable.getCellRect(row-1, 0, true));
  enableDisableRowEditButtons();
  sortOrderTest();
  this.firePropertyChange (evt.getActionCommand(), "", "upButton");
}//GEN-LAST:event_upButtonActionPerformed

private void insertLineBreaksCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
        setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
}//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed

private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
    if (generatePointsChoices != null)
        generatePointsComboBox.setSelectedIndex(0); // newly copied row takes precedence over menu selection
    
    String newCopiedRowHint = new String();
    int row = jTable.getSelectedRow(); // starts at 0
    newCopiedRowHint += "row[" + row + "] = ";
    if ((row < 0) || (row >= jTable.getModel().getRowCount())) {
        return;
    }

    int columnCount = _getDataWidthFromJTable();

//  copiedRow = getData()[row].clone(); // convenience method returns String, need Object instead
    copiedRow = new Object[columnCount];
    for (int col=0; col < columnCount; col++)
    {
        if  (doIndex) // column 0 is panel index, ignore
        {
            copiedRow[col] = jTable.getModel().getValueAt(row,   col+1); // returns Object
            newCopiedRowHint += " " + copiedRow[col].toString();
        }
        else  
        {
            copiedRow[col] = jTable.getModel().getValueAt(row, col); // returns Object
            newCopiedRowHint += " " + copiedRow[col].toString();
        }
    }
    boolean moreDataRemaining = false;
    if (((redColumn == -1) && (jTable.getModel().getColumnCount() > columnCount + 1)) ||   //  no color widget
        ((redColumn != -1) && (jTable.getModel().getColumnCount() > columnCount + 2))) // has color widget
        moreDataRemaining = true;
    if (moreDataRemaining)
        newCopiedRowHint += " ...";
    copyButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COPY_BUTTON") + "<br />" + newCopiedRowHint);
  appendButton.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_ADD_BUTTON")  + "<br />" + newCopiedRowHint);
}//GEN-LAST:event_copyButtonActionPerformed

private void operationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operationComboBoxActionPerformed
    checkForBooleanContent (); // may reset selection back to only-allowed NEGATE_OPERATION if boolean content
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
          case CENTER_OPERATION:
              operationSelection =      CENTER_OPERATION;
              break;
          case ROTATE_OPERATION:
              operationSelection =      ROTATE_OPERATION;
              break;
          default:
              System.out.println("ExpandableList error:  illegal selection (" + operationComboBox.getSelectedIndex() + ") for operationComboBox");
              break;
      }

    if ((operationSelection == CENTER_OPERATION) || (operationSelection == NEGATE_OPERATION))
         editFactorTextField.setEnabled(false); // unneeded entry field
    else editFactorTextField.setEnabled(true);  //   needed entry field
    
    if ((operationSelection == CENTER_OPERATION) || (operationSelection == ROTATE_OPERATION))
        cellSelectionComboBox.setSelectedIndex(ALL_CELLS_OPERATION);
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
              System.out.println("Illegal selection cellSelectionComboBox");
              break;
      }
}//GEN-LAST:event_cellSelectionComboBoxActionPerformed

  private void readEditFactorTextOperationValue ()
  {
       String cellContents, warningMessage;
       
       cellContents = editFactorTextField.getText().trim();
       if (((operationSelection == ROUNDOFF_OPERATION) && cellContents.isEmpty()) ||
           ( operationSelection ==   CENTER_OPERATION) ||
           ( operationSelection ==   NEGATE_OPERATION) ||
           ( cellContents.equals("value")            ))  return; // no edit factor text needed
       try
       {
           operationValue = (new SFFloat(cellContents)).getValue();
       }
       catch (Exception e)
       {
           if (cellContents.trim().length()==0)
                warningMessage = "No value provided to edit cells, please retry";
           else warningMessage = "Illegal float value (" + cellContents + "), please retry";
           System.out.println(warningMessage);
           NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    warningMessage, NotifyDescriptor.WARNING_MESSAGE);
           DialogDisplayer.getDefault().notify(descriptor);
           return;
       }
       if ((operationSelection == ROUNDOFF_OPERATION) &&
           (operationValue != Math.round(operationValue)))
       {
           warningMessage = "Illegal roundoff value (" + cellContents + "), please use a positive integer for number of places";
           System.out.println(warningMessage);
           NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    warningMessage, NotifyDescriptor.WARNING_MESSAGE);
           DialogDisplayer.getDefault().notify(descriptor);
           operationValue = 0.0f;
       }
  }
  private void readNumberOfPointsTextField ()
  {
       String cellContents;
       
       cellContents = numberOfPointsTextField.getText().trim();
       if (cellContents.isEmpty() || cellContents.contains("point"))  
       {
           // no numberOfPoints value found
           numberOfPoints = DEFAULTNUMBEROFPOINTS;
           return;
       }
       try
       {
           numberOfPoints = (new SFInt32(cellContents)).getValue();
       }
       catch (Exception e)
       {
           numberOfPoints = DEFAULTNUMBEROFPOINTS;
       }
  }
  private void readScaleTextField ()
  {
       String cellContents;
       
       cellContents = scaleTextField.getText().trim();
       if (cellContents.isEmpty())  
       {
           // no numberOfPoints value found
           scaleValue = 1.0f; // default
           return;
       }
       try
       {
           scaleValue = Float.parseFloat(cellContents);
           
           if      (scaleValue < 0.0f)
                    scaleValue = Math.abs(scaleValue);
           else if (scaleValue == 0.0f)
                    scaleValue = 1.0f;
       }
       catch (NumberFormatException e)
       {
           scaleValue = 1.0f; // default
       }
  }
private void editFactorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editFactorTextFieldActionPerformed
    readEditFactorTextOperationValue();
}//GEN-LAST:event_editFactorTextFieldActionPerformed

private void editFactorTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_editFactorTextFieldFocusLost
    readEditFactorTextOperationValue();
}//GEN-LAST:event_editFactorTextFieldFocusLost

private void computeCenterMinMaxValues ()
{
    TableModel tableModel = jTable.getModel();
    computeTableIndices ();

    xColumn = initialColumnIndex;
    if (isKeyColumnIncluded())
         xColumn++;
    
    if (tableModel.getRowCount() < 1)
    {
        return; // no operation since insufficient rows found to center about 0
    } 
    else if (tableModel.getRowCount() == 1)
    {
        value = Float.parseFloat(tableModel.getValueAt(0, xColumn).toString());
        minX = maxX = averageX = value;
        value = Float.parseFloat(tableModel.getValueAt(0, xColumn+1).toString());
        minY = maxY = averageY = value;
        if ((tableModel.getColumnCount() > xColumn+2) && (null != tableModel.getValueAt(0, xColumn+2)))
        {
        value = Float.parseFloat(tableModel.getValueAt(0, xColumn+2).toString());
        minZ = maxZ = averageZ = value;
        }
        return;
    }
    // compute mins, maxes, averages
    for  (int row = 0; row < tableModel.getRowCount(); row++) // operation is performed on all rows
    {
        if ((tableModel.getColumnCount() > xColumn  ) && (null != tableModel.getValueAt(0, xColumn))) // columns start at 0
        {
            value = Float.parseFloat(tableModel.getValueAt(row, xColumn).toString());
            if ((row == 0) || (minX > value)) 
                minX = value;
            if ((row == 0) || (maxX < value)) 
                maxX = value;
        }
        if ((tableModel.getColumnCount() > xColumn+1) && (null != tableModel.getValueAt(row, xColumn+1))) // columns start at 0
        {
            value = Float.parseFloat(tableModel.getValueAt(row, xColumn+1).toString());
            if ((row == 0) || (minY > value))
                minY = value;
            if ((row == 0) || (maxY < value))
                maxY = value;
        }
        if ((tableModel.getColumnCount() > xColumn+2) && (null != tableModel.getValueAt(row, xColumn+2))) // columns start at 0
        {
            value = Float.parseFloat(tableModel.getValueAt(row, xColumn+2).toString());
            if ((row == 0) || (minZ > value)) 
                minZ = value;
            if ((row == 0) || (maxZ < value)) 
                maxZ = value;
        }
    }
    averageX = (maxX + minX) / 2.0f;
    averageY = (maxY + minY) / 2.0f;
    averageZ = (maxZ + minZ) / 2.0f;
}
        
@SuppressWarnings("BooleanConstructorCall")
private void applyCellFactorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyCellFactorButtonActionPerformed
    String cellContents;
    float cellValue;

    readEditFactorTextOperationValue(); // reread, recheck field in case of problems
    if ((editFactorTextField.getText().trim().length() == 0) &&
        (operationSelection != ROUNDOFF_OPERATION) &&
        (operationSelection !=   CENTER_OPERATION) &&
        (operationSelection !=   NEGATE_OPERATION))
    {
        return; // no operation since no factor value provided
    }
    if ((operationValue == 0.0f) && (operationSelection == DIVIDE_BY_OPERATION)) {
        NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "Division by zero not allowed", NotifyDescriptor.WARNING_MESSAGE);
        DialogDisplayer.getDefault().notify(descriptor);
        return; // nop
    }
    TableModel tableModel = jTable.getModel();
    computeTableIndices ();
    
    if ((operationSelection == CENTER_OPERATION) )
    {
        computeCenterMinMaxValues ();
        for  (int row = 0; row < tableModel.getRowCount(); row++)            // operation is performed on all rows
        {
            if ((tableModel.getColumnCount() > xColumn  ) && (null != tableModel.getValueAt(0, xColumn))) // columns start at 0
            {
                value = Float.parseFloat(tableModel.getValueAt(row, xColumn).toString());
                tableModel.setValueAt((new SFFloat(value - averageX)).toString(), row, xColumn);
            }
            if ((tableModel.getColumnCount() > xColumn+1) && (null != tableModel.getValueAt(row, xColumn+1))) // columns start at 0
            {
                value = Float.parseFloat(tableModel.getValueAt(row, xColumn+1).toString());
                tableModel.setValueAt((new SFFloat(value - averageY)).toString(), row, xColumn+1);
            }
            if ((tableModel.getColumnCount() > xColumn+2) && (null != tableModel.getValueAt(row, xColumn+2))) // columns start at 0
            {
                value = Float.parseFloat(tableModel.getValueAt(row, xColumn+2).toString());
                tableModel.setValueAt((new SFFloat(value - averageZ)).toString(), row, xColumn+2);
            }
        }
        this.firePropertyChange (evt.getActionCommand(), "", operationComboBox.getSelectedItem().toString());
        return; // CENTER_OPERATION complete
    }
    if (operationSelection == ROTATE_OPERATION)
    {
        computeCenterMinMaxValues ();
    }

    for (int column = initialColumnIndex; column <= finalColumnIndex; column++) // note beginning and end values inclusive
    {
        for (int row = initialRowIndex; row <= finalRowIndex; row++)            // note beginning and end values inclusive
        {
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
                    System.out.println(warningMessage);
                    NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                            warningMessage, NotifyDescriptor.WARNING_MESSAGE);
                    DialogDisplayer.getDefault().notify(descriptor);
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
                    readEditFactorTextOperationValue (); // use operationValue for number of places
                    
                    if ((operationValue < 3) && (doColorChooser || doNormals || doOrientations)) {
                        cellValue = Math.round(cellValue * 10000.0f) / 10000.0f;
                    }
                    else if (operationValue == 0.0)
                    {
                        cellValue = Math.round(cellValue);
                    }
                    else if (operationValue > 0.0)
                    {
                        int places = (int) operationValue;
                        for (int i = 0; i < places; i++)
                            cellValue *= 10.0;
                        cellValue = Math.round(cellValue);
                        for (int i = 0; i < places; i++)
                            cellValue /= 10.0;
                    }
                    else // (operationValue < 0.0)
                    {
                        int places = (int) Math.abs(operationValue);
                        for (int i = 0; i < places; i++)
                            cellValue /= 10.0;
                        cellValue = Math.round(cellValue);
                        for (int i = 0; i < places; i++)
                            cellValue *= 10.0;
                    }
                }
                else if (operationSelection == NEGATE_OPERATION) 
                {
                    cellValue = -1.0f *  cellValue;
                }
                
                if (doColorChooser)
                {
                     cellContents = colorFormat.format(cellValue);
                }
                else if (doNormals)
                {
                     cellContents = floatFormat.format(cellValue);
                }
                else if (doOrientations)
                {
                     cellContents = radiansFormat.format(cellValue);
                }
                else cellContents = fiveDigitFormat.format(cellValue);
                
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
            // perform rotation only once for each row.  have already performed computeCenterMinMaxValues ();

            if ((operationSelection == ROTATE_OPERATION) && (column == initialColumnIndex))
            {
                double     x = 0.0f;
                double     y = 0.0f;
                double     r;
                double angle;
                double  newX;
                double  newY;
                if ((finalColumnIndex - initialColumnIndex + 1) == 2) // x-y rotation of 2D coordinates
                {
                    if ((tableModel.getColumnCount() > xColumn  ) && (null != tableModel.getValueAt(0, xColumn))) // columns start at 0
                    {
                        x = Float.parseFloat(tableModel.getValueAt(row, xColumn).toString());
                    }
                    if ((tableModel.getColumnCount() > xColumn+1) && (null != tableModel.getValueAt(row, xColumn+1))) // columns start at 0
                    {
                        y = Float.parseFloat(tableModel.getValueAt(row, xColumn+1).toString());
                    }
                }
                if (jTable.getModel().getRowCount() == 1)
                {
                    // single point is rotated about origin, perform calculations without offsetting to the origin
                    r     = Math.sqrt (x*x + y*y);
                    angle = Math.atan2(y, x);
                    newX  = r * Math.sin(angle - (operationValue * Math.PI / 180.0)); // user-provided angle in degrees, clockwise rotation
                    newY  = r * Math.cos(angle - (operationValue * Math.PI / 180.0));
                }
                else // recenter to origin before rotating, then restore offset
                {
                    r     = Math.sqrt((x - averageX)*(x - averageX) + (y - averageY)*(y - averageY));
                    angle = Math.atan2((y - averageY), (x - averageX));
                    newX  = r * Math.cos(angle + (operationValue * Math.PI / 180.0)) + averageX; // user-provided angle in degrees, counterclockwise rotation;
                    newY  = r * Math.sin(angle + (operationValue * Math.PI / 180.0)) + averageY; // also restore prior local origin: averageX, averageY
                }
                tableModel.setValueAt((new SFFloat((float)newX)).toString(), row, xColumn);
                tableModel.setValueAt((new SFFloat((float)newY)).toString(), row, xColumn+1);
            }
        }
    }
    this.firePropertyChange (evt.getActionCommand(), "", operationComboBox.getSelectedItem().toString());
}//GEN-LAST:event_applyCellFactorButtonActionPerformed

private void jTableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableFocusGained
//    enableDisableRowEditButtons(); // check buttons enabled/disabled
}//GEN-LAST:event_jTableFocusGained

private void jTableFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTableFocusLost
//    enableDisableRowEditButtons(); // check buttons enabled/disabled
}//GEN-LAST:event_jTableFocusLost

private void generatePointsComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_generatePointsComboBoxActionPerformed
{//GEN-HEADEREND:event_generatePointsComboBoxActionPerformed
    setGeneratePointsChoicesTooltip(generatePointsDescriptions[generatePointsComboBox.getSelectedIndex()]);
    generatePointsComboBoxSelectionChecked ();
    if (generatePointsComboBox.getSelectedIndex() > 1)
	{
		    addPointsButton.setEnabled(true);
		         scalePanel.setEnabled(true);
		openClosedLoopPanel.setEnabled(true);
	}
    else 
	{
	     	addPointsButton.setEnabled(false);
		         scalePanel.setEnabled(false);
		openClosedLoopPanel.setEnabled(false);
	}
}//GEN-LAST:event_generatePointsComboBoxActionPerformed

private void generatePointsComboBoxSelectionChecked ()
{
    if (generatePointsComboBox.getSelectedItem().toString().contains("N-sided Polygon"))
    {
         numberOfPointsTextField.setEnabled(true);
         // TODO not yet working:  automatically select next text field
         numberOfPointsTextField.selectAll();
         numberOfPointsTextField.setCaretPosition(0);
         numberOfPointsTextField.setRequestFocusEnabled(true);
    }
    else numberOfPointsTextField.setEnabled(false);
}
private boolean flipped = false;
private void flipRowOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flipRowOrderButtonActionPerformed
    computeTableIndices ();
    // TODO RowSorter capabilities?
    
    flipped = !flipped;
    
    for (int rowIndex = initialRowIndex; rowIndex < (finalRowIndex+1)/2; rowIndex++)
    {
        // swap all data values
        int oppositeRowIndex = finalRowIndex - rowIndex;
        for (int columnIndex = initialColumnIndex; columnIndex <= finalColumnIndex; columnIndex++)
        {
            Object holdFirstValue                           = jTable.getValueAt(rowIndex, columnIndex);
            jTable.setValueAt(jTable.getValueAt(oppositeRowIndex, columnIndex), rowIndex, columnIndex);
            jTable.setValueAt(holdFirstValue,                           oppositeRowIndex, columnIndex);
        }
    }    
    if  (flipped)
    {
         flipRowOrderButton.setText       ("Unflip Row Data");
         flipRowOrderButton.setFont       (italicsButtonFont);
         flipRowOrderButton.setToolTipText("Restore order of rows from top to bottom, moving only data");
    }
    else
    { 
         flipRowOrderButton.setText       (" Flip Row Data ");
         flipRowOrderButton.setFont       (plainButtonFont);
         flipRowOrderButton.setToolTipText("Reverse order of rows from bottom to top, moving only data");
    }
    // italicize flipped data
    TableColumnModel   columnModel = jTable.getColumnModel();
    TableCellRenderer cellRenderer = jTable.getDefaultRenderer(String.class);
    if (flipped)      cellRenderer = new ItalicRenderer();
    for (int columnIndex = initialColumnIndex; columnIndex <= finalColumnIndex; columnIndex++)
    {
        TableColumn tc = columnModel.getColumn(columnIndex);
        tc.setCellRenderer(cellRenderer);
    }
    revalidate(); // redraw swing component to ensure proper layout
    enableDisableRowEditButtons ();
    this.firePropertyChange (evt.getActionCommand(), "", "flipRowOrder");
}//GEN-LAST:event_flipRowOrderButtonActionPerformed

    private void uniformKeyIntervalsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_uniformKeyIntervalsButtonActionPerformed
    {//GEN-HEADEREND:event_uniformKeyIntervalsButtonActionPerformed
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
              for (int i=0; i < saa.length; i++)
              {
                  saa [i][0] = Float.toString(Math.round(i * increment * 10000.0f)/10000.0f);
              } break;
      }
        setData(saa);
        enableDisableRowEditButtons();
        this.firePropertyChange (evt.getActionCommand(), "", "uniformKeyIntervals");
    }//GEN-LAST:event_uniformKeyIntervalsButtonActionPerformed

    private void sortButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_sortButtonActionPerformed
    {//GEN-HEADEREND:event_sortButtonActionPerformed
        String[][] saa = getData();
        if      (saa.length == 0) return;
        else if (saa.length == 1) return;

        for (int i = 0; i < saa.length - 1; i++)
        {
            for (int j=i+1; j < saa.length; j++)
            {
                if (saa [i][0].length() == 0) // avoid problems with empty strings
                    saa [i][0] = "0";
                if (saa [j][0].length() == 0)
                    saa [j][0] = "0";
                double rank1, rank2;
                if      (saa [i][0].equalsIgnoreCase("zenith"))
                     rank1 =     -1.0; // zenith always displayed first
                else if (saa [i][0].equalsIgnoreCase("horizon"))
                     rank1 =  1.57079; // horizon always has this value
                else if (saa [i][0].equalsIgnoreCase("nadir") && isSortOrderAscending())
                     rank1 =  10000.0; // nadir always displayed last
                else if (saa [i][0].equalsIgnoreCase("nadir"))
                     rank1 = -10000.0; // nadir always displayed last
                else rank1 = Double.parseDouble(saa [i][0]);
                if      (saa [j][0].equalsIgnoreCase("zenith"))
                     rank2 =     -1.0; // zenith always displayed first
                else if (saa [j][0].equalsIgnoreCase("horizon"))
                     rank2 =  1.57079; // horizon always has this value
                else if (saa [j][0].equalsIgnoreCase("nadir") && isSortOrderAscending())
                     rank2 =  10000.0; // nadir always displayed last
                else if (saa [j][0].equalsIgnoreCase("nadir"))
                     rank2 = -10000.0; // nadir always displayed last
                else rank2 = Double.parseDouble(saa [j][0]);
                if (((rank1 > rank2) &&  isSortOrderAscending()) || 
                    ((rank1 < rank2) && !isSortOrderAscending())
//                        || // duplicate logic unnecessary
//                    (saa [i][0].equalsIgnoreCase("nadir" ) &&  isSortOrderAscending()) ||             // nadir  always last,  need to swap
//                    (saa [j][0].equalsIgnoreCase("zenith") &&  isSortOrderAscending()) ||             // zenith always first, need to swap
//                    (saa [j][0].equalsIgnoreCase("nadir" ) && !isSortOrderAscending()) ||             // opposite
//                    (saa [i][0].equalsIgnoreCase("zenith") && !isSortOrderAscending())                // opposite
                    )
                {
                    // swap rows
                    String[] hold = saa [i];
                    saa [i]  = saa [j];
                    saa [j]  = hold;
                }
            }
        }
        setData(saa);
        enableDisableRowEditButtons();
        this.firePropertyChange (evt.getActionCommand(), "", "sortByKey");
    }//GEN-LAST:event_sortButtonActionPerformed

    private void openLoopRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openLoopRadioButtonActionPerformed
        setEnumerationValueLoopClosed(false);
    }//GEN-LAST:event_openLoopRadioButtonActionPerformed

    private void closedLoopRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closedLoopRadioButtonActionPerformed
        setEnumerationValueLoopClosed(true);
    }//GEN-LAST:event_closedLoopRadioButtonActionPerformed

    private void jTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTablePropertyChange
        if  (getData().length > 0)
             selectAllButton.setEnabled(true);
        else selectAllButton.setEnabled(false);
        // fire external propertyChangeEvent to invoking panel
        this.firePropertyChange (evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
    }//GEN-LAST:event_jTablePropertyChange

    private void removeAllRowsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllRowsButtonActionPerformed
        int rowCount = jTable.getModel().getRowCount();
        for (int row=0; row < rowCount; row++)
        {
            ((DefaultTableModel) jTable.getModel()).removeRow(0);
        }        
        enableDisableRowEditButtons(); // check buttons enabled/disabled
        this.firePropertyChange (evt.getActionCommand(), "", "RemoveAllRows");
    }//GEN-LAST:event_removeAllRowsButtonActionPerformed
    
    /**
     * Generate points
     * @param evt 
     */
    private void addPointsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPointsButtonActionPerformed
      
      int insertRowNumber = getInsertionRow ();
      int     columnWidth = _getDataWidthFromJTable();
      Object[][] oa = new Object[1][columnWidth]; // data width does not include index column

//      Object[] tableData = makeEmptyJTableDataRow(oa[0]);

      generatePointsComboBoxSelectionChecked ();
      
      boolean closed = closedLoopRadioButton.isSelected();
      
      readNumberOfPointsTextField ();
      
      readScaleTextField ();
    
      int generatePointsChoiceIndex = generatePointsComboBox.getSelectedIndex();
      if ((generatePointsChoices != null) && (generatePointsChoices.length > 0))
      {
          NsidedPolygon nsidedPolygon = new NsidedPolygon();
          nsidedPolygon.setClosed(closedLoopRadioButton.isSelected());
          nsidedPolygon.setScale (scaleValue);
          String[]   sa  = {};
          Object[][] oaa; // = {};

          // N-sided Polygon, 2D
          if (generatePointsComboBox.getSelectedItem().equals(N_SIDED_POLYGON))
          {
                  sa = parseX(nsidedPolygon.circlePointsArray2D(numberOfPoints, closed));
                  
                  oaa = new Object[sa.length/2][3]; // instantiate at correct size
                  for (int index=0; index < sa.length; index+=2)
                  {
                      oaa[index/2][0] = ""; // overstep index column
                      oaa[index/2][1] = sa[index];
                      oaa[index/2][2] = sa[index+1];
                  }
                  oaa = adjustForKeyColumn(oaa);
                  for (int i=0; i < oaa.length; i++)
                  {
                      ((DefaultTableModel) jTable.getModel()).insertRow(insertRowNumber, oaa[i]);
                      if (isKeyColumnIncluded() && (oaa.length > 1)) // fix key
                          jTable.getModel().setValueAt(fiveDigitFormat.format((float)i / (oaa.length - 1)), insertRowNumber, 1);
                      insertRowNumber++; // keep appending
                  }
          }
          else if ((generatePointsChoiceIndex >= 7) && (generatePointsChoiceIndex <= 9)) // N-sided polygon
          {
              switch (generatePointsChoiceIndex) {
              // 7: N-sided Polygon (z=0) XY plane
                  case 7:
                      sa = parseX(nsidedPolygon.circlePointsArrayXY(numberOfPoints, closed));
                      break;
              //8: N-sided Polygon (x=0) YZ plane
                  case 8:
                      sa = parseX(nsidedPolygon.circlePointsArrayYZ(numberOfPoints, closed));
                      break;
              // 9: N-sided Polygon (y=0) XZ plane
                  case 9:
                      sa = parseX(nsidedPolygon.circlePointsArrayXZ(numberOfPoints, closed));
                      break;
                  default:
                      break;
              }
              oaa = new Object[sa.length/3][4]; // instantiate at correct size
              for (int index=0; index < sa.length; index+=3)
              {
                  oaa[index/3][0] = ""; // overstep index column
                  oaa[index/3][1] = sa[index];
                  oaa[index/3][2] = sa[index+1];
                  oaa[index/3][3] = sa[index+2];
              }
              oaa = adjustForKeyColumn(oaa);
              for (int i=0; i < oaa.length; i++)
              {
                  ((DefaultTableModel) jTable.getModel()).insertRow(insertRowNumber, oaa[i]);
                  if (isKeyColumnIncluded() && (oaa.length > 1)) // fix key
                      jTable.getModel().setValueAt(fiveDigitFormat.format((float)i / (oaa.length - 1)), insertRowNumber, 1);
                  insertRowNumber++; // keep appending
              }
          }
          // if no other values are available, use selected enumeration choice label as the value to fill first cell in column 0
          else if ((generatePointsEnumerationValues == null))
          {
              jTable.getModel().setValueAt(generatePointsComboBox.getSelectedItem().toString(), insertRowNumber, 0);
              jTable.getSelectionModel().setSelectionInterval(insertRowNumber, insertRowNumber);
              jTable.scrollRectToVisible(jTable.getCellRect(insertRowNumber, 0, true));
          }
          else // use provided value array
          {
              if ((generatePointsComboBox.getSelectedItem().toString().isEmpty())) // empty choice
                  return; // no useful value
              else if ((generatePointsChoiceIndex >= generatePointsEnumerationValues.length)  || (generatePointsEnumerationValues[generatePointsChoiceIndex] == null))
              {
                  System.err.println ("X3D-Edit error, illegal enumeration values array for choice " + generatePointsChoiceIndex + ", no action taken");
                  return; // no useful value
              }
              int numberRowsInserted = generatePointsEnumerationValues[generatePointsChoiceIndex].length;
              if (isEnumerationValueLoopClosed())
                  numberRowsInserted++;
              
              // add one or more rows of data, TODO check size
              String[][] scaledValues = generatePointsEnumerationValues[generatePointsChoiceIndex];
              
              for (String[] scaledValue : scaledValues) {
                  for (int j = 1; j < scaledValue.length; j++) // skip initial index which is "#" character
                  {
                      scaledValue[j] = String.valueOf(Float.parseFloat(scaledValue[j]) * scaleValue);
                  }
              }

              for (int i=0; i < scaledValues.length; i++)
              {
                  ((DefaultTableModel) jTable.getModel()).insertRow(insertRowNumber, adjustForKeyColumn(scaledValues)[i]);
                  if (isKeyColumnIncluded() && (numberRowsInserted > 1)) // fix key
                      jTable.getModel().setValueAt(fiveDigitFormat.format((float)i / (numberRowsInserted - 1)), insertRowNumber, 1);
                  insertRowNumber++; // keep appending
              }
              if (isEnumerationValueLoopClosed()) // closed loop means add the initial value again; helpful for IndexedLineSet use
              {
                  ((DefaultTableModel) jTable.getModel()).insertRow(insertRowNumber, adjustForKeyColumn(scaledValues)[0]);
                  if (isKeyColumnIncluded()) // fix key
                      jTable.getModel().setValueAt(1, insertRowNumber, 1);
                  insertRowNumber++; // keep appending
              }
                  
              jTable.getSelectionModel().setSelectionInterval(insertRowNumber - scaledValues.length, insertRowNumber - 1);
              jTable.scrollRectToVisible(jTable.getCellRect(insertRowNumber, 0, true));
          }
      }
      enableDisableRowEditButtons();
      this.firePropertyChange (evt.getActionCommand(), "", "addPoints");
    }//GEN-LAST:event_addPointsButtonActionPerformed

    private void insertCommasCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertCommasCheckBoxActionPerformed

        setInsertCommas(insertCommasCheckBox.isSelected());     }//GEN-LAST:event_insertCommasCheckBoxActionPerformed

    private void numberOfPointsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberOfPointsTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberOfPointsTextFieldActionPerformed

    private void numberOfPointsTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_numberOfPointsTextFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_numberOfPointsTextFieldFocusLost

    private void scaleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleTextFieldActionPerformed
        // ensure nonnegative
        if (scaleTextField.getText().trim().startsWith("-"))
            scaleTextField.setText(scaleTextField.getText().trim().substring(1));
    }//GEN-LAST:event_scaleTextFieldActionPerformed

    private void selectAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllButtonActionPerformed
        jTable.selectAll();
    }//GEN-LAST:event_selectAllButtonActionPerformed

    private void statisticsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statisticsButtonActionPerformed
		DecimalFormat   formatPrecision3 = new DecimalFormat ("####0.000");
		StringBuilder statisticsText     = new StringBuilder();
		StringBuilder statisticsHTML     = new StringBuilder("<html>");
		String[][] saa = getData();
		String sign;
		
        if     ((saa.length == 0) || (saa.length == 1) || (saa[0].length == 0))
		{
			System.out.println ("No data in table.");
			return; // no action
		}
		statisticsText.append("Statistics for ").append(titledBorder.getTitle()).append("\n");
		statisticsHTML.append("<p><b>Statistics for ").append(titledBorder.getTitle()).append("</b></p>\n");
		statisticsHTML.append("<table>\n");
		int numberRows    = saa.length;    // data rows,    not table rows
		int numberColumns = saa[0].length; // data columns, not table columns
		int indexColumn = 0;
		if (doIndex)
			indexColumn = 1;
		
		double minValue[] = new double[numberColumns];
		double maxValue[] = new double[numberColumns];
		double sumValue[] = new double[numberColumns];
		
		for (int col=0; col < numberColumns; col++)
		{
                    minValue[col] = 0.0;
                    maxValue[col] = 0.0;
                    sumValue[col] = 0.0;
                    double cellValue;
                    for (int row=0; row < numberRows; row++)
                    {
                        try  
                        {  
                          cellValue = Double.valueOf(saa[row][col]);
                        }  
                        catch(NumberFormatException nfe)  
                        {  
                          break; 
                        }
                        if (row == 0)
                        {
                                minValue[col] = maxValue[col] = cellValue;
                        }
                        else
                        {
                                if (minValue[col] > cellValue)
                                    minValue[col] = cellValue;
                                if (maxValue[col] < cellValue)
                                    maxValue[col] = cellValue;
                                sumValue[col] += cellValue;
                        }
                    }
		}
		// output
		statisticsHTML.append("<tr><td></td>\n");
		for (int col=0; col < numberColumns; col++)
		{
			if (columnTitles[col + indexColumn].length() < 4)
				statisticsText.append("\t\t\t");
			statisticsText.append("\t").append(  columnTitles[col + indexColumn]);
			statisticsHTML.append("<td>").append(columnTitles[col + indexColumn]).append("</td>");
		}
		statisticsText.append("\n");
		statisticsHTML.append("</tr>\n");
		
		statisticsText.append("min ");
		statisticsHTML.append("<tr>\n");
		for (int col=0; col < numberColumns; col++)
		{
			if (minValue[col] >= 0)
				 sign = "+";
			else sign = "";
			statisticsText.append("\t").append(sign).append(formatPrecision3.format(minValue[col]));
			statisticsHTML.append("<td>").append(sign).append(formatPrecision3.format(minValue[col])).append("</td>");
		}
		statisticsText.append("\n");
		statisticsHTML.append("</tr>\n");

//		statisticsText.append("mean");
//		statisticsHTML.append("<tr>\n");
//		for (int col=0; col < numberColumns; col++)
//		{
//			if (minValue[col] >= 0)
//				 sign = "+";
//			else sign = "";
//			statisticsText.append("\t").append(sign).append(formatPrecision3.format(sumValue[col] / numberRows));
//			statisticsHTML.append("<td>").append(sign).append(formatPrecision3.format(sumValue[col] / numberRows)).append("</td>");
//		}
//		statisticsText.append("\n");
//		statisticsHTML.append("</tr>\n");

		statisticsText.append("mid ");
		statisticsHTML.append("<tr>\n");
		for (int col=0; col < numberColumns; col++)
		{
			if ((minValue[col] + maxValue[col]) >= 0)
				 sign = "+";
			else sign = "";
			statisticsText.append("\t").append(sign).append(formatPrecision3.format((minValue[col] + maxValue[col]) / 2.0));
			statisticsHTML.append("<td>").append(sign).append(formatPrecision3.format((minValue[col] + maxValue[col]) / 2.0)).append("</td>");
		}
		statisticsText.append("\n");
		statisticsHTML.append("</tr>\n");

		statisticsText.append("max");
		statisticsHTML.append("<tr>\n");
		for (int col=0; col < numberColumns; col++)
		{
			if (maxValue[col] >= 0)
				 sign = "+";
			else sign = "";
			statisticsText.append("\t").append(sign).append(formatPrecision3.format(maxValue[col]));
			statisticsHTML.append("<td>").append(sign).append(formatPrecision3.format(maxValue[col])).append("</td>");
		}
		statisticsText.append("\n");
		statisticsHTML.append("</tr>\n");
			
		statisticsHTML.append("</table>\n</html>\n");
		
		System.out.println ("=================================\n");
		System.out.println (statisticsText);
		System.out.println ("=================================\n");
		NotifyDescriptor descriptor = new NotifyDescriptor.Message(
				 statisticsText.toString(), NotifyDescriptor.INFORMATION_MESSAGE); // TODO problem with HTML table rendering :(
		DialogDisplayer.getDefault().notify(descriptor);
    }//GEN-LAST:event_statisticsButtonActionPerformed

    private void makeOpenClosedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeOpenClosedButtonActionPerformed
        if (isClosed())
        {
            while (isClosed()) // make open by removing last row
            {
                int deleteRowNumber = jTable.getModel().getRowCount() - 1;
                if (deleteRowNumber < 1) // safety check
                    return;
                
                ((DefaultTableModel) jTable.getModel()).removeRow(deleteRowNumber);

                jTable.scrollRectToVisible(jTable.getCellRect(deleteRowNumber - 1, 0, true));
                this.firePropertyChange (evt.getActionCommand(), "", "deleteRow, MakeOpen");
            }
        }
        else // make closed by appending initial row
        {
            int columnCount = _getDataWidthFromJTable();
            Object[]  initialRowCopied = new Object[columnCount];
            for (int column=0; column < columnCount; column++)
            {
                if  (doIndex) // column 0 is panel index, ignore
                {
                    initialRowCopied[column] = jTable.getModel().getValueAt(0, column+1); // returns Object
                }
                else
                {
                    initialRowCopied[column] = jTable.getModel().getValueAt(0, column);   // returns Object
                }
            }
            appendRow(initialRowCopied);
            this.firePropertyChange (evt.getActionCommand(), "", "appendRow, MakeOpen");
        }
        checkMakeOpenClosedButtonEnabled(); // update display to match
    }//GEN-LAST:event_makeOpenClosedButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPointsButton;
    private javax.swing.JButton appendButton;
    private javax.swing.JButton applyCellFactorButton;
    private javax.swing.JLabel cellEditLabel;
    private javax.swing.JPanel cellEditPanel;
    private javax.swing.JSeparator cellEditSeparator;
    private javax.swing.JComboBox<String> cellSelectionComboBox;
    private javax.swing.JRadioButton closedLoopRadioButton;
    private javax.swing.JButton copyButton;
    private javax.swing.JButton downButton;
    private javax.swing.JTextField editFactorTextField;
    private javax.swing.JButton flipRowOrderButton;
    private javax.swing.JLabel generateLabel;
    private javax.swing.JComboBox<String> generatePointsComboBox;
    private javax.swing.JPanel generatePointsPanel;
    private javax.swing.JSeparator generatePointsSeparator;
    private javax.swing.JLabel generatePointsSpacerLabel;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JLabel insertLabel;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JPanel insertPanel;
    private javax.swing.JPanel interfacePanel;
    private javax.swing.JTable jTable;
    private javax.swing.JScrollPane jTableScrollPane;
    private javax.swing.JButton makeOpenClosedButton;
    private javax.swing.JTextField numberOfPointsTextField;
    private javax.swing.ButtonGroup openClosedButtonGroup;
    private javax.swing.JPanel openClosedLoopPanel;
    private javax.swing.JRadioButton openLoopRadioButton;
    private javax.swing.JComboBox<String> operationComboBox;
    private javax.swing.JButton removeAllRowsButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JPanel rowButtonPanel;
    private javax.swing.JLabel rowEditLabel;
    private javax.swing.JLabel scaleLabel;
    private javax.swing.JPanel scalePanel;
    private javax.swing.JTextField scaleTextField;
    private javax.swing.JButton selectAllButton;
    private javax.swing.JButton sortButton;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JButton statisticsButton;
    private javax.swing.JButton uniformKeyIntervalsButton;
    private javax.swing.JButton upButton;
    // End of variables declaration//GEN-END:variables

  class JTableWithReadOnlyColumns extends JTable
  {
    @Override
    public boolean isCellEditable(int row, int column)
    {
      if (cellEditableChecker != null)
        return cellEditableChecker.isCellEditable(row, column);

      return !(readOnlyRows.contains(row) ||
          readOnlyColumns.contains(column) ||
          readOnlyCells.contains(new CellID(row, column)));
    }

    @Override
    public String getToolTipText(MouseEvent e)
    {
      Point p = e.getPoint();
      int rowIndex = rowAtPoint(p);
      int colIndex = convertColumnIndexToModel(columnAtPoint(p));
      Object o = getValueAt(rowIndex, colIndex);

      if (o instanceof Color)
      {
        Color c = (Color) o;
        return "RGB value: " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue();
      }
      else if ((angleColumn == colIndex) && o.toString().equalsIgnoreCase("zenith"))
      {
             return "zenith is directly overhead at 90 degrees (pi/2 radians)";
      }
      else if ((angleColumn == colIndex) && o.toString().equalsIgnoreCase("zenith"))
      {
             return "zenith is directly below at -90 degrees (-pi/2 radians)";
      }
      else if (angleColumn == colIndex)
      {
         String tooltip;
         if      (o.toString().equalsIgnoreCase("zenith"))
         {
             tooltip = "zenith is directly above, 0 radians (0 degrees)";
             return tooltip;
         }
         else if (o.toString().equalsIgnoreCase("horizon"))
         {
             tooltip = "horizon is 1.57079 radians (90 degrees)";
             return tooltip;
         }
         else if (o.toString().equalsIgnoreCase("nadir"))
         {
             tooltip = "nadir is directly below"; // pi radians for sky, pi/2 radians for ground
             return tooltip;
         }
         double angle = new SFDouble(o.toString()).getValue();
         // check if value is still in degrees and hasn't been normalized yet
         if ((Math.abs(angle) > 2.0 * Math.PI) && !largeRadianAnglesConfirmed) 
         {
             tooltip = (radiansFormat.format(angle) + " degrees = " + radiansFormat.format(angle / 180.0 * Math.PI) + " radians (needs normalization)");
         }
         else // value in radians
         {
             tooltip = (radiansFormat.format(angle) + " radians = " + radiansFormat.format(angle * 180.0 / Math.PI) + " degrees");
         }

         return tooltip;
      }
      else if ((columnToolTips != null) && columnToolTips[colIndex] != null && (columnToolTips[colIndex].length() > 0))
        return columnToolTips[colIndex];
      else if (o instanceof String)
        return ((String) o);
      else return ""; // empty string
    }
  }

  class CellID
  {
    int row, col;

    public CellID(int row, int col)
    {
      this.row = row;
      this.col = col;
    }

    @Override
    public boolean equals(Object obj)
    {
      return obj instanceof CellID &&
          ((CellID) obj).row == row &&
          ((CellID) obj).col == col;
    }
        // autogenerated by netbeans
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 29 * hash + this.row;
            hash = 29 * hash + this.col;
            return hash;
        }
  }

  public static interface IsCellEditableIf
  {
    public boolean isCellEditable(int row, int column);
  }

  public static interface InsertRowFilterIf
  {
    /**
     * @param selectedRow selected row, or -1 if none
     * @return row number to pass to TableModel.insertRow, or -1 if insert not allowed
     */
    public int insertRowNumber(int selectedRow);
  }

  class InsertRowFilter implements InsertRowFilterIf
  {
    @Override
    public int insertRowNumber(int selRow)
    {
      //selRow = (selRow == -1 ? 0 : selRow);
      return selRow + 1; // append after selected row
    }
  }
  class InsertRowAtBottomFilter implements InsertRowFilterIf
  {
    @Override
    public int insertRowNumber(int selRow)
    {
      if (selRow == -1) return jTable.getModel().getRowCount(); // append to end
      else              return selRow + 1; // append after selected row
    }
  }

  public static interface RemoveRowFilterIf
  {
    /**
     * @param selectedRow
     * @return
     */
    public int removeRowNumber(int selectedRow);
  }
  
  class RemoveRowFilter implements RemoveRowFilterIf
  {
    @Override
    public int removeRowNumber(int selRow)
    {
      if (selRow == -1 || jTable.getModel().getRowCount() <= 0)
        return -1;
      return selRow;
    }
  }
  
  private RemoveRowFilterIf removeRowFilter = new RemoveRowFilter();
  private InsertRowFilterIf insertRowFilter = new InsertRowAtBottomFilter();

  public void setInsertRowFilter(InsertRowFilterIf filter)
  {
    if(filter == null)
      insertRowFilter = new InsertRowFilter();
    else
      insertRowFilter = filter;
  }

    public void setRemoveRowFilter(RemoveRowFilterIf filter)
    {
        if (filter == null) {
            removeRowFilter = new RemoveRowFilter();
        } else {
            removeRowFilter = filter;
        }
    }
    public void setAngleColumn(int value)
    {
      angleColumn = value;
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

    /**
     * @return the showEditEnhancements value
     */
    private boolean isShowEditEnhancements() {
        return showEditEnhancements;
    }

    /**
     * @param showAppendCommasLineBreaks whether the Append Commas, Line Breaks radio boxes are displayed
     */
    public void setShowAppendCommasLineBreaks(boolean showAppendCommasLineBreaks) {
        this.showEditEnhancements = showAppendCommasLineBreaks;
        insertCommasCheckBox.setVisible(showAppendCommasLineBreaks);
    insertLineBreaksCheckBox.setVisible(showAppendCommasLineBreaks);
                 insertLabel.setVisible(showAppendCommasLineBreaks);
    }

    /**
     * @return the angleColumnIncluded value
     */
    public boolean isAngleColumnIncluded() {
        return angleColumnIncluded;
    }

    /**
     * @return the keyColumnIncluded value
     */
    public boolean isKeyColumnIncluded() {
        return keyColumnIncluded;
    }

    /**
     * @param angleColumnIncluded whether angleColumnIncluded is set
     */
    public void setAngleColumnIncluded(boolean angleColumnIncluded) {
        this.angleColumnIncluded = angleColumnIncluded;
                  sortButton.setVisible(angleColumnIncluded);
                  sortButton.setText("Sort by angle");
    }

    /**
     * @param keyColumnIncluded whether keyColumnIncluded is set
     */
    public void setKeyColumnIncluded(boolean keyColumnIncluded) {
        this.keyColumnIncluded = keyColumnIncluded;
                  sortButton.setVisible(keyColumnIncluded);
        uniformKeyIntervalsButton.setVisible(keyColumnIncluded);
                  sortButton.setText("Sort by key"); // default
    }
    private void sortOrderTest()
    {
      if (!(angleColumnIncluded || keyColumnIncluded))
          return;
      sortButton.setForeground(defaultForegroundColor); // reset prior to test
      
      jTableModel = ((DefaultTableModel) jTable.getModel());
      int length = jTableModel.getRowCount();
      
      for (int i = 0; i < length - 1; i++)
      {
          double rank1, rank2;
          if      (jTableModel.getValueAt(i  ,0).toString().equalsIgnoreCase("zenith"))
               rank1 =     -1.0; // zenith always displayed first
          else if (jTableModel.getValueAt(i  ,0).toString().equalsIgnoreCase("horizon"))
               rank1 =  1.57079; // horizon always has this value
          else if (jTableModel.getValueAt(i  ,0).toString().equalsIgnoreCase("nadir") && isSortOrderAscending())
               rank1 =  10000.0; // nadir always displayed last
          else if (jTableModel.getValueAt(i  ,0).toString().equalsIgnoreCase("nadir"))
               rank1 = -10000.0; // nadir always displayed last
          else rank1 = Double.parseDouble(jTableModel.getValueAt(i, 0).toString());
          if      (jTableModel.getValueAt(i+1,0).toString().equalsIgnoreCase("zenith"))
               rank2 =     -1.0; // zenith always displayed first
          else if (jTableModel.getValueAt(i+1,0).toString().equalsIgnoreCase("horizon"))
               rank2 =   1.57079; // horizon always has this value
          else if (jTableModel.getValueAt(i+1,0).toString().equalsIgnoreCase("nadir") && isSortOrderAscending())
               rank2 =  10000.0; // nadir always displayed last
          else if (jTableModel.getValueAt(i+1,0).toString().equalsIgnoreCase("nadir"))
               rank2 = -10000.0; // nadir always displayed last
          else rank2 = Double.parseDouble(jTableModel.getValueAt(i+1, 0).toString());
          if (((rank1 > rank2) &&  isSortOrderAscending()) || 
              ((rank1 < rank2) && !isSortOrderAscending()))
          {
              sortButton.setForeground(redForeground);  // list fails order test
              return;
          }
      }
    }

    /**
     * @return the enumerationLabels
     */
    public String[] getGeneratePointsDescriptions()
    {
        return generatePointsDescriptions;
    }

    /**
     * @param generatePointsDescriptions the generatePointsDescriptions to set
     */
    public void setGeneratePointsDescriptions(String[] generatePointsDescriptions)
    {
        this.generatePointsDescriptions = generatePointsDescriptions;
        // TODO callback function matches description
    }

    /**
     * @return the generatePointsChoices
     */
    public String[] getGeneratePointsChoices()
    {
        return generatePointsChoices;
    }

    /**
     * @param generatePointsChoices the generatePointsChoices to set
     */
    public void setGeneratePointsChoices(String[] generatePointsChoices)
    {
        this.generatePointsChoices = generatePointsChoices;
        generatePointsComboBox.setModel(new DefaultComboBoxModel<>(generatePointsChoices));
        generatePointsComboBox.setMaximumRowCount(generatePointsChoices.length); // avoid scrolling since it is awkward
        setEnumerationsChoicePanelVisible(true);
    }

    /**
     * @return the generatePointsEnumerationValues
     */
    public String[][][] getGeneratePointsEnumerationValues() {
        return generatePointsEnumerationValues;
    }

    /**
     * @param generatePointsEnumerationValues the generatePointsEnumerationValues to set
     */
    public void setGeneratePointsEnumerationValues(String[][][] generatePointsEnumerationValues) {
        this.generatePointsEnumerationValues = generatePointsEnumerationValues;
        if ((generatePointsEnumerationValues == null) || (generatePointsEnumerationValues.length <= 1) || (generatePointsEnumerationValues[1] == null) || (generatePointsEnumerationValues[1].length <= 1))
        {
            openLoopRadioButton.setVisible(false);
          closedLoopRadioButton.setVisible(false);
        }
        else
        {
            openLoopRadioButton.setVisible(true);
          closedLoopRadioButton.setVisible(true);
        }
    }

    public void preserveGeneratedPointsPanelVerticalSpacing()
    {
        setEnumerationsChoicePanelVisible(true);
                 generateLabel.setVisible(false);
        generatePointsComboBox.setVisible(false);
       numberOfPointsTextField.setVisible(false);
           openLoopRadioButton.setVisible(false);
         closedLoopRadioButton.setVisible(false);
               addPointsButton.setVisible(false);
                    scalePanel.setVisible(false);
           openClosedLoopPanel.setVisible(false);
    }

    /**
     * @param tooltip the help text to set
     */
    public void setGeneratePointsChoicesTooltip(String tooltip)
    {
        generatePointsComboBox.setToolTipText(tooltip);
    }

    /**
     * @param visible whether the subpanel is visible, ordinarily hidden unless enumeration values are provided
     */
    public void setEnumerationsChoicePanelVisible(boolean visible)
    {
        generatePointsPanel.setVisible(visible);
    generatePointsSeparator.setVisible(visible);
    }
    protected void computeRotationAngles (float initialAngle, float finalAngle)
    {
        // TODO work on selected range only; similarly for other buttons
        
        String[][] saa = getData();
        if     ((saa.length == 0) || (saa.length == 1))
            return; // no action

        int rotationColumn = 3;  // angleColumn
        if (keyColumnIncluded) rotationColumn++; // skip  key  column as appropriate
        float increment = (finalAngle - initialAngle) / ((float) saa.length - 1);
        for (int i=0; i < saa.length; i++)
        {
            saa [i][rotationColumn] = floatFormat.format(initialAngle + (i * increment));
            if (saa [i][rotationColumn].equals("-0"))
                saa [i][rotationColumn] = "0";
        }
        setData(saa);
    }

	/**
	 * @return whether includeMakeOpenClosedButton is included/enabled
	 */
	public boolean isIncludeMakeOpenClosedButton() {
		return includeMakeOpenClosedButton;
	}

	/**
	 * @param value set whether includeMakeOpenClosedButton is included/enabled
	 */
	public void setIncludeMakeOpenClosedButton(boolean value) {
		includeMakeOpenClosedButton = value;
		makeOpenClosedButton.setVisible(value);
		if (includeMakeOpenClosedButton)
			checkMakeOpenClosedButtonEnabled (); // initialize button label, tooltips
	}
	/**
	 * Status of current list
	 * @return whether first and last rows are not coincident, i.e. not numerically equal
	 */
	public boolean isOpen()
	{
		return !isClosed();
	}
	/**
	 * Status of current list
	 * @return whether first and last rows are coincident, i.e. numerically equal
	 */
	public boolean isClosed()
	{
		if (isIncludeMakeOpenClosedButton() && (getData() != null) && (getData().length >= 2))
		{
			boolean firstLastRowsEqual = true; // value to determine...
			int firstColumn = 0;
			int  lastColumn = getData()[0].length - 1;
			int  lastRow    = getData().length    - 1;
			
			for (int column = firstColumn; column <= lastColumn; column++)
			{
				String initialRowValue = getData()[0][column];
				String    lastRowValue = getData()[lastRow][column];
				if (!isNumeric(initialRowValue) || !isNumeric(lastRowValue) || 
					(Double.parseDouble(initialRowValue) != Double.parseDouble(lastRowValue)))
				{
					firstLastRowsEqual = false;
					break;
				}
			}
			return firstLastRowsEqual;
		}
		return false;
	}
	// https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
	private boolean isNumeric(String value)
	{
		try
		{
		    Double.parseDouble(value);
		}
		catch (NumberFormatException nfe)
		{
		    return false;
		}
		return true;
	}
	private void checkMakeOpenClosedButtonEnabled ()
	{
		int length = jTableModel.getRowCount();
		if (isIncludeMakeOpenClosedButton() && (length >=3)) // double-back closed segments aren't helpful, avoid
		{
			 makeOpenClosedButton.setEnabled(true);
			 if (isClosed())
			 {
				 makeOpenClosedButton.setText("Make open");
				 makeOpenClosedButton.setToolTipText("Remove last row, make list values open (disconnected)");
				 this.firePropertyChange ("expandableList().checkMakeOpenClosedButtonEnabled()", "", "MakeOpenButton");
			 }
			 else
			 {
				 makeOpenClosedButton.setText("Make closed");
				 makeOpenClosedButton.setToolTipText("Append copy of first row to end, make list values closed (connected)");
				 this.firePropertyChange ("expandableList().checkMakeOpenClosedButtonEnabled()", "", "MakeOpenButton");
			 }
		}
		else makeOpenClosedButton.setEnabled(false);
	}
}