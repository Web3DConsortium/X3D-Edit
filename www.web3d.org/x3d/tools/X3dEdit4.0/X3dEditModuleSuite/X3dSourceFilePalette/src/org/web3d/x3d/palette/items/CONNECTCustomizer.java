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
import java.util.Vector;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.JTextComponent;
import org.jdom.Element;
import org.openide.util.HelpCtx;
import org.web3d.x3d.palette.items.FieldRenderer.FieldEditor;
import org.web3d.x3d.palette.items.FieldRenderer.RedIndicator;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * METACustomizer.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class CONNECTCustomizer extends BaseCustomizer
{
  private CONNECT connect;
  private JTextComponent target;
  
  private String [] nodeFieldNames  = {"This list is populated with field names from",
                                       "enclosing IS node"};
  private String [] protoFieldNames = {"This list is populated with field or fieldValue names"+
                                       "from parent ProtoDeclare or ProtoInstance node"};
  private Color defaultLabForeground;
  
  public CONNECTCustomizer(CONNECT connect, JTextComponent target)
  {
    super(connect);
    this.connect = connect;
    this.target = target;
   
    HelpCtx.setHelpIDString(this, "CONNECT_ELEM_HELPID");
    
    initComponents();
    
    defaultLabForeground = nodeFieldLab.getForeground();

   // nodeFieldComboBox.setRenderer(new myFieldRenderer(TO));
    nodeFieldComboBox.setRenderer(new FieldRenderer(new RedIndicator()
    {
      @Override
      public boolean markFieldTypeRed(String nm)
      {
        return !nm.equals(currentProtoFieldType);
      }     
    }));
    //protoFieldComboBox.setRenderer(new myFieldRenderer(FROM));
    protoFieldComboBox.setRenderer(new FieldRenderer(new RedIndicator()
    {
      @Override
      public boolean markFieldTypeRed(String nm)
      {
        return !nm.equals(currentNodeFieldType);
      }      
    }));
    
    nodeFieldComboBox.setEditor(new FieldEditor()); //myFieldEditor());
    protoFieldComboBox.setEditor(new FieldEditor()); //myFieldEditor());
    
    fillFieldCBs();
    int idx = convFieldObj(connect.getNodeField(),nodeFieldComboBox);
    if(idx != -1)
      nodeFieldComboBox.setSelectedIndex(idx);
    else
      nodeFieldComboBox.setSelectedItem(new FieldPan(connect.getNodeField(),"unknown","unknown"));
    
    idx = convFieldObj(connect.getProtoField(),protoFieldComboBox);
    if(idx != -1)
      protoFieldComboBox.setSelectedIndex(idx);
    else
      protoFieldComboBox.setSelectedItem(new FieldPan(connect.getProtoField(),"unknown","unknown"));
    
    this.addAncestorListener(new AncestorListener()
    {
      @Override
      public void ancestorAdded(AncestorEvent event)
      {
        (CONNECTCustomizer.this.getRootPane()).setDefaultButton(null);
        nodeFieldComboBox.requestFocusInWindow();  // this makes it so the combo box editor is not in focus
      }
      @Override
      public void ancestorMoved(AncestorEvent event){}
      @Override
      public void ancestorRemoved(AncestorEvent event){}
    });

   }
  
  @SuppressWarnings("unchecked")
  private void fillFieldCBs()
  {
    Vector fields = connect.getFieldElementsWithinISparent();
    if(fields != null && fields.size() > 0) {
     if(fields.get(0) instanceof Element)
      fillCB(nodeFieldComboBox,fields);
     else if(fields.get(0) instanceof FieldPan)
       nodeFieldComboBox.setModel(new DefaultComboBoxModel(fields));
    }
    Vector<Element> if_fields = connect.getFieldElementsWithinProtoInterfaceOrInstance();
    if(if_fields != null && if_fields.size()>0)
      fillCB(protoFieldComboBox,if_fields);
  }
  
  @SuppressWarnings("unchecked")
  private void fillCB(JComboBox cb, Vector<Element> data)
  {
 //   String[] fns = new String[data.size()];
//    int i=0;
//    for(Element elem : data)
//      fns[i++] = elem.getAttributeValue(FIELD_ATTR_NAME_NAME);
//    cb.setModel(new DefaultComboBoxModel(fns));  
    
    Vector<JComponent> vec = new Vector<JComponent>();
    for(Element elm : data) {
      String nm = elm.getAttributeValue(FIELD_ATTR_NAME_NAME);
      nm = (nm==null?"":nm);
      String at = elm.getAttributeValue(FIELD_ATTR_ACCESSTYPE_NAME);
      at = (at==null?"":at);
      String ty = elm.getAttributeValue(FIELD_ATTR_TYPE_NAME);
      ty = (ty==null?"":ty);
      FieldPan fp = new FieldPan(nm,ty,at);
      vec.add(fp);
    }
    cb.setModel(new DefaultComboBoxModel(vec));   // todo unchecked warning
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nodeFieldLab = new javax.swing.JLabel();
        nodeFieldComboBox = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        protoFieldComboBox = new javax.swing.JComboBox<String>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nodeTypeLab = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        protoTypeLab = new javax.swing.JLabel();

        nodeFieldLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nodeFieldLab.setText("nodeField");
        nodeFieldLab.setToolTipText("name of connected field in Parent node");

        nodeFieldComboBox.setEditable(true);
        nodeFieldComboBox.setModel(new DefaultComboBoxModel<String>(nodeFieldNames));
        nodeFieldComboBox.setToolTipText("name of connected field in Parent node");
        nodeFieldComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodeFieldComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("protoField");
        jLabel2.setToolTipText("name of mapped field in parent ProtoInterface");

        protoFieldComboBox.setEditable(true);
        protoFieldComboBox.setModel(new DefaultComboBoxModel<String>(protoFieldNames));
        protoFieldComboBox.setToolTipText("name of mapped field in parent ProtoInterface");
        protoFieldComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protoFieldComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("name");

        jLabel4.setText("accessType");

        nodeTypeLab.setText("type");

        jLabel6.setText("name");

        jLabel7.setText("accessType");

        protoTypeLab.setText("type");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nodeFieldLab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nodeFieldComboBox, 0, 311, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                                .addComponent(nodeTypeLab)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addGap(24, 24, 24))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(protoFieldComboBox, 0, 311, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 146, Short.MAX_VALUE)
                                .addComponent(protoTypeLab)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addGap(24, 24, 24)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, nodeFieldLab});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(nodeTypeLab))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nodeFieldLab)
                    .addComponent(nodeFieldComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(protoTypeLab))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(protoFieldComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nodeFieldComboBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_nodeFieldComboBoxActionPerformed
    {//GEN-HEADEREND:event_nodeFieldComboBoxActionPerformed
    Object obj = nodeFieldComboBox.getSelectedItem();
    if(obj instanceof FieldPan)
      currentNodeFieldType = ((FieldPan)obj).getFieldType();
    else
      currentNodeFieldType = "";
    checkFieldMismatch();
    checkTypeErrors();
    protoFieldComboBox.invalidate();
}//GEN-LAST:event_nodeFieldComboBoxActionPerformed

    private void protoFieldComboBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_protoFieldComboBoxActionPerformed
    {//GEN-HEADEREND:event_protoFieldComboBoxActionPerformed
    Object obj = protoFieldComboBox.getSelectedItem();
    if(obj instanceof FieldPan)
      currentProtoFieldType = ((FieldPan)obj).getFieldType();
    else
      currentProtoFieldType = "";
    checkFieldMismatch();
    checkTypeErrors();
    nodeFieldComboBox.invalidate();

}//GEN-LAST:event_protoFieldComboBoxActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox<String> nodeFieldComboBox;
    private javax.swing.JLabel nodeFieldLab;
    private javax.swing.JLabel nodeTypeLab;
    private javax.swing.JComboBox<String> protoFieldComboBox;
    private javax.swing.JLabel protoTypeLab;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_CONNECT";
  }

  @Override
  public void unloadInput()
  {
    FieldPan fp = (FieldPan)nodeFieldComboBox.getSelectedItem();
    connect.setNodeField(fp.getFieldName());
    fp = (FieldPan)protoFieldComboBox.getSelectedItem();
    connect.setProtoField(fp.getFieldName());
  }
  
  // todo
  final int FROM=0;
  final int TO=1;
  String currentProtoFieldType="";
  String currentNodeFieldType="";

 
  /*
  class myFieldRenderer extends DefaultListCellRenderer //implements ListCellRenderer
  {
    int wh;

    FieldPan fldPan = new FieldPan("","","");
    myFieldRenderer(int wh)
    {
      this.wh = wh;
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
      String typ;
      if (wh == TO)
        typ = currentProtoFieldType;
      else
        typ = currentNodeFieldType;
      
      if (value instanceof FieldPan) {
        FieldPan fp = (FieldPan) value;
        fldPan.setFieldAccess(fp.getFieldAccess());
        fldPan.setFieldName(fp.getFieldName());
        fldPan.setFieldType(fp.getFieldType());
        if (!fp.getFieldType().equalsIgnoreCase(typ))
          fldPan.setForeground(Color.red);
        else
          fldPan.setForeground(Color.black);
      }
      else if (value instanceof Component){
        return (Component)value;
      }
      else {
        fldPan.setFieldAccess("");
        fldPan.setFieldName("");
        fldPan.setFieldType("");
      }
      return fldPan;
    }    
  }
 abstract class myBaseComboEditor extends JPanel implements ComboBoxEditor, CaretListener, FocusListener
  {
    protected JTextField tf;
    protected JLabel typeLab,accessLab;
    
    public myBaseComboEditor()
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
      setBorder(new LineBorder(Color.gray,1));
      setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
      add(tf);
      add(Box.createHorizontalGlue());
      add(typeLab);
      add(Box.createHorizontalStrut(5));
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
    public void focusGained(FocusEvent e){}
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
 
  class myFieldEditor extends myBaseComboEditor //JTextField implements ComboBoxEditor
  {
    Object setObj;
    
    public void setItem(Object o)
    {
      setObj = o;
      if (o == null){
        setObj = new FieldPan("","unknown","unknown");
        tf.setText("");
        typeLab.setText(((FieldPan)setObj).getFieldType());
        typeLab.setVisible(true);
        accessLab.setText(((FieldPan)setObj).getFieldAccess());
        accessLab.setVisible(true);
      }

      else if (o instanceof FieldPan) {   
        tf.setText(((FieldPan)o).getFieldName());
        typeLab.setText(((FieldPan)o).getFieldType());
        typeLab.setVisible(true);
        accessLab.setText(((FieldPan)o).getFieldAccess());
        accessLab.setVisible(true);
      }
      else if (o instanceof JLabel) { 
        tf.setText(((JLabel)o).getText());
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
        return("");
      
      String s = tf.getText().trim();
      
      if (setObj instanceof FieldPan) {
        FieldPan fp = (FieldPan)setObj;
        if(!fp.getFieldName().equals(s)) {
          fp = new FieldPan(s,"unknown","unknown");
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
*/  
  private final String SUFF = "_changed";
  private final String PREF = "set_";
    
  private int convFieldObj(String s, JComboBox cb)
  {
    s = convertSetChangedNames(s);
       
    if (s != null && s.length() > 0) {
      ComboBoxModel mod = cb.getModel();
      for (int i = 0; i < mod.getSize(); i++) {
        FieldPan fp = (FieldPan)mod.getElementAt(i);
        if(convertSetChangedNames(fp.getFieldName()).equals(s)) 
          return i;
      }
    }
    return -1;// legal for jcombobox.setselectedIndex()
  }
  
  private String convertSetChangedNames(String s)
  {
    if(s.endsWith(SUFF))
      s = s.substring(0, s.length()-SUFF.length());
    if(s.startsWith(PREF))
      s = s.substring(PREF.length());
    return s;
  }

  boolean fieldError;
  void checkFieldMismatch()
  {
    fieldError = false;
    Object pan = nodeFieldComboBox.getSelectedItem();
    String typ = "";
    if(pan instanceof FieldPan)
      typ = ((FieldPan)pan).getFieldType();
    else if(pan instanceof JLabel)
      typ = ((JLabel)pan).getText();
    
    if(!currentProtoFieldType.equalsIgnoreCase(typ)) {
        fieldError=true;
        return;
    }

    pan = protoFieldComboBox.getSelectedItem();
    typ = "";
    if(pan instanceof FieldPan)
      typ = ((FieldPan)pan).getFieldType();
    else if(pan instanceof JLabel)
      typ = ((JLabel)pan).getText();
    
    if(!currentNodeFieldType.equalsIgnoreCase(typ)) {
        fieldError=true;
        return;
    }   
  }
  private void checkTypeErrors()
  {
    Color foreColor = defaultLabForeground;
    if(fieldError)
      foreColor = Color.red;
    
    nodeTypeLab.setForeground(foreColor);
    protoTypeLab.setForeground(foreColor);
  }

}
