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
package org.web3d.x3d.dis.playerrecorder;

import edu.nps.moves.dis7.pdus.VariableParameter;
import edu.nps.moves.dis7.enumerations.*;

import java.awt.Component;
import java.awt.Dimension;

import java.io.UnsupportedEncodingException;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.LongBuffer;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.openide.util.NbBundle;

/**
 * ArticulationParametersDisplayPanel.java
 * Created on 16 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class ArticulationParametersDisplayPanel extends javax.swing.JPanel
{
  public static int COL_INDEX = 0;
  public static int COL_TYPE_DESIGNATOR  = 1;
  public static int COL_CHANGE = 2;
  public static int COL_ATTACHED_TO = 3;
  public static int COL_TYPE = 4;
  public static int COL_VALUE_FLOAT   = 5;
  public static int COL_VALUE_STRING  = 6;
  public static int COL_VALUE_HEX     = 7;
  public static int COL_VALUE_INTEGER = 8;

  public static int NUM_COLS = 9;

  public ArticulationParametersDisplayPanel()
  {
    initComponents();

    TableCellRenderer hdrRenderer = jTable1.getTableHeader().getDefaultRenderer();
    for(int i=0;i<NUM_COLS;i++) {
      TableColumn col = jTable1.getColumnModel().getColumn(i);
      int hdrWidth = hdrRenderer.getTableCellRendererComponent(null,col.getHeaderValue(), false, false, 0, 0).getPreferredSize().width;
      col.setPreferredWidth(hdrWidth);
    }
    // TODO index column: right justify
    jTable1.getColumnModel().getColumn(COL_INDEX).setPreferredWidth(24);
    jTable1.getColumnModel().getColumn(COL_INDEX).setMaxWidth(24);
    
    jTable1.getColumnModel().getColumn(COL_TYPE_DESIGNATOR).setCellRenderer(new VariableParameterRecordTypeRenderer());
    jTable1.getColumnModel().getColumn(COL_TYPE).setCellRenderer(new ParamTypeRenderer());
    jTable1.getColumnModel().getColumn(COL_VALUE_FLOAT).setCellRenderer(new ParamFloatValueRenderer());
    jTable1.getColumnModel().getColumn(COL_VALUE_STRING).setCellRenderer(new ParamStringValueRenderer());
    jTable1.getColumnModel().getColumn(COL_VALUE_HEX).setCellRenderer(new ParamHexValueRenderer());
    jTable1.getColumnModel().getColumn(COL_VALUE_INTEGER).setCellRenderer(new ParamIntValueRenderer());
  }

  public void setData(int num, List params)
  {
    @SuppressWarnings(value="unchecked")
    List<VariableParameter>aLis = (List<VariableParameter>)params;
    numberTF.setText(""+num);

    DefaultTableModel mod = (DefaultTableModel)jTable1.getModel();
    mod.setRowCount(params.size());
    int r=0;
    for(VariableParameter aP : aLis) {
      mod.setValueAt(r,                               r, COL_INDEX);
      
      // TODO: Here is the old way w/ actual ArticulatedParameters. Now they these
      // are VariableParameters, your guess might be better then mine at this point (tdn)
//      mod.setValueAt(aP.getParameterTypeDesignator(), r, COL_TYPE_DESIGNATOR);
//      mod.setValueAt(aP.getChangeIndicator(),         r, COL_CHANGE);
//      mod.setValueAt(aP.getPartAttachedTo(),          r, COL_ATTACHED_TO);
//      mod.setValueAt(aP.getParameterType(),           r, COL_TYPE);
//      mod.setValueAt(aP.getParameterValue(),          r, COL_VALUE_HEX);
//      mod.setValueAt(aP.getParameterValue(),          r, COL_VALUE_FLOAT);
//      mod.setValueAt(aP.getParameterValue(),          r, COL_VALUE_INTEGER);
//      mod.setValueAt(aP.getParameterValue(),          r, COL_VALUE_STRING);
      r++;
    }
    int rowHeight = jTable1.getRowHeight();
    int hdrHeight = jTable1.getTableHeader().getPreferredSize().height;
    Dimension d = new Dimension(10,hdrHeight+10*rowHeight);  // preferred display size in scrollpan if there is content
    if(num < 1)                           // the height is the important one
      d = new Dimension(10,10);           // don't take up any space if empty

    jTable1.setPreferredScrollableViewportSize(d);
  }

  String xgetAsciiByteString(double d)
  {
    ByteBuffer bb = ByteBuffer.allocate(Double.SIZE/Byte.SIZE);
    CharBuffer cb = bb.asCharBuffer();
    bb.putDouble(d);
    return cb.toString();
  }

  StringBuffer ascBuff = new StringBuffer();
  String getAsciiByteString(double d)
  {
    ascBuff.setLength(0);
    ByteBuffer bb = ByteBuffer.allocate(Double.SIZE/Byte.SIZE);
    bb.putDouble(d);
    byte[] ba = bb.array();
    String s = "";
    try {
      s = new String(ba,"US-ASCII");
    }
    catch(UnsupportedEncodingException ex) {
          return s;
    }
    for(int i=0;i<s.length();i++) {
      if(ba[i] <= 0x20 || ba[i]>0x7e)
        ascBuff.append('.');
      else
        ascBuff.append(s.charAt(i));
    }
    return ascBuff.toString();
  }

  String getHexByteString(double d)
  {
    StringBuilder sb = new StringBuilder();
    ByteBuffer bb = ByteBuffer.allocate(Double.SIZE/Byte.SIZE);
    bb.putDouble(d);
    byte[] ba = bb.array();
    for(int i=0;i<ba.length;i++) {
      sb.append(toHex(ba[i]));
      sb.append(' ');
    }
    return(sb.toString().trim());
  }

  String getLongString(double d)
  {
    ByteBuffer bb = ByteBuffer.allocate(Double.SIZE/Byte.SIZE);
    LongBuffer lb = bb.asLongBuffer();
    bb.putDouble(d);
    return ""+lb.get(0);
  }

  String getDoubleString(double d)
  {
    return ""+d;
  }

  char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
  String toHex(byte b)
  {
    int i = (b&0xf0) >> 4;
    int j = b&0x0f;
    char[] ca = new char[2];
    ca[0] = hexArray[i];
    ca[1] = hexArray[j];
    return new String(ca);
  }

  class ParamHexValueRenderer extends DefaultTableCellRenderer
  {
    @Override
    protected void setValue(Object value) {
      if (value == null) {
        setText("null");
      }
      else {
        Double d = (Double) value;
        setText(getHexByteString(d));
      }
    }
  }

  class ParamFloatValueRenderer extends DefaultTableCellRenderer
  {
    @Override
    protected void setValue(Object value)
    {
    if (value == null) {
        setText("null");
      }
      else {
        Double d = (Double) value;
        setText(getDoubleString(d));
      }
    }
  }

  class ParamIntValueRenderer extends DefaultTableCellRenderer
  {
   @Override
    protected void setValue(Object value) {
        if (value == null) {
        setText("null");
      }
      else {
        Double d = (Double) value;
        setText(getLongString(d));
      }
    }
  }

  class ParamStringValueRenderer extends DefaultTableCellRenderer
  {
   @Override
    protected void setValue(Object value) {
      if (value == null) {
        setText("null");
      }
      else {
        Double d = (Double) value;
        setText(getAsciiByteString(d));
      }
    }
  }

  class VariableParameterRecordTypeRenderer extends DefaultTableCellRenderer {

    @Override
    protected void setValue(Object value)
    {
      if (value == null) {
        setText("null");
      } else {
        int i = ((Short) value).intValue();
        try {
          VariableParameterRecordType ptd = VariableParameterRecordType.values()[i];
          setText("" + i + " " + ptd.getDescription());
        }
        catch (ArrayIndexOutOfBoundsException ex) {
          setText("" + i);
        }
      }
    }
  }

  class ParamTypeRenderer extends DefaultTableCellRenderer
  {
    int curRow = 0;
    int curCol = 0;
    StringBuffer sb = new StringBuffer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      curRow = row;
      curCol = column;
      return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    @Override
    protected void setValue(Object value)
    {
      if (value == null) {
        setText("null");
        return;
      }
      int i = ((Integer) value);
      try {
        Short sh = (Short)jTable1.getValueAt(curRow,1);
        if (sh.intValue() == VariableParameterRecordType.ATTACHED_PART.getValue()) {
          // Attached
          AttachedParts att = AttachedParts.values()[i];
          if (att == null) {
            throw new ArrayIndexOutOfBoundsException();
          }
          sb.setLength(0);
          sb.append(i);
          sb.append(' ');
          sb.append(att.getDescription());
          setText(sb.toString());
        }
        else {
          // articulated
          int frac = i & 0x1f;
          int base = i - frac;

          ArticulatedPartsTypeClass baseEnum = ArticulatedPartsTypeClass.values()[base];
          ArticulatedPartsTypeMetric fracEnum = ArticulatedPartsTypeMetric.values()[frac];
          if (baseEnum == null || fracEnum == null) {
            throw new ArrayIndexOutOfBoundsException();
          }
          sb.setLength(0);
          sb.append(i);
          sb.append(' ');
          sb.append(baseEnum.getDescription());
          sb.append(' ');
          sb.append(fracEnum.getDescription());
          setText(sb.toString());
        }
      }
      catch (ArrayIndexOutOfBoundsException ex) {
        setText("" + i);
      }
    }
  }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        numberTF = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(NbBundle.getMessage(getClass(), "ArticulationParametersDisplayPanel.jLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(jLabel1, gridBagConstraints);

        numberTF.setEditable(false);
        numberTF.setForeground(new java.awt.Color(0, 102, 102));
        numberTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        numberTF.setText(NbBundle.getMessage(getClass(), "ArticulationParametersDisplayPanel.numberTF.text")); // NOI18N
        numberTF.setBorder(null);
        numberTF.setMaximumSize(new java.awt.Dimension(20, 40));
        numberTF.setMinimumSize(new java.awt.Dimension(16, 40));
        numberTF.setPreferredSize(new java.awt.Dimension(16, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.05;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 2);
        add(numberTF, gridBagConstraints);

        jTable1.setFont(new java.awt.Font("Courier New", 0, 11));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "##", "Type Designator", "Change Indicator", "Part Attached To", "Type", "Value (float)", "(ascii)", "(hex)", "(int)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable1.setAutoscrolls(false);
        jTable1.setColumnSelectionAllowed(true);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title2")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title3")); // NOI18N
        jTable1.getColumnModel().getColumn(4).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title4")); // NOI18N
        jTable1.getColumnModel().getColumn(5).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title5")); // NOI18N
        jTable1.getColumnModel().getColumn(6).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title6")); // NOI18N
        jTable1.getColumnModel().getColumn(7).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title7")); // NOI18N
        jTable1.getColumnModel().getColumn(8).setHeaderValue(org.openide.util.NbBundle.getMessage(ArticulationParametersDisplayPanel.class, "ArticulationParametersDisplayPanel.jTable1.columnModel.title8")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField numberTF;
    // End of variables declaration//GEN-END:variables
}
