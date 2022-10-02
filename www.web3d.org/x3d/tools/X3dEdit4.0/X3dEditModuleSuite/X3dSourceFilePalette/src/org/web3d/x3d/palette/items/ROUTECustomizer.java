/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.

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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.JTextComponent;
import org.jdom.Attribute;
import org.jdom.Element;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;

import org.openide.util.NbBundle;
import org.web3d.x3d.palette.items.FIELDdefaults.RouteDefault;
import org.web3d.x3d.palette.items.FieldRenderer.FieldBaseComboEditor;
import org.web3d.x3d.palette.items.FieldRenderer.FieldEditor;
import org.web3d.x3d.palette.items.FieldRenderer.RedIndicator;
import org.web3d.x3d.sai.X3DFieldDefinition;
import org.web3d.x3d.sai.X3DFieldTypes;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * ROUTECustomizer.java
 * Created on August 15, 2007, 2:25 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class ROUTECustomizer extends BaseCustomizer
{
  private ROUTE route;
  JTextComponent target;
  private final String hdr = "<html>";
  private final String sep =" <b>";

  private String currentFromFieldType="";
  private String currentToFieldType="";

  private boolean fieldError=false;

  private Color defaultLabForeground;

  private FIELDdefaults defaultFields = new FIELDdefaults();

 /** Creates new form ROUTECustomizer */
  public ROUTECustomizer(ROUTE route, JTextComponent target)
  {
    super(route);
    this.route = route;
    this.target = target;

    HelpCtx.setHelpIDString(this, "ROUTE_ELEM_HELPID");

    initComponents();

    fillNodeCombos();  // Get list of nodes in place
    defaultLabForeground = toTypeLab.getForeground();

    setCBRenderersAndEditors();  // The field lists are rendered specially

    int idx = convRouteObj(route.getFromNode(),fromNodeCB);
    fromNodeCB.setSelectedIndex(idx);   // hits the action listener, -1 for no selection is OK

    idx = convRouteObj(route.getToNode(),toNodeCB);
    toNodeCB.setSelectedIndex(idx);     // hits the action listener, -1 for no selection is OK

    // At this point, the combos have been loaded with valid fields.
    // Now try setting the field we came in with

    idx = getFieldIndex(route.getFromField(),fromFieldCB);
    fromFieldCB.setSelectedIndex(idx);  // hits the action listener, -1 for no selection is OK

    idx = getFieldIndex(route.getToField(),toFieldCB);
    toFieldCB.setSelectedIndex(idx);    // hits the action listener, -1 for no selection is OK
      
    this.addAncestorListener(new AncestorListener()
    {
      @Override
      public void ancestorAdded(AncestorEvent event)
      {
        (ROUTECustomizer.this.getRootPane()).setDefaultButton(null);
      }
      @Override
      public void ancestorMoved(AncestorEvent event){}

      @Override
      public void ancestorRemoved(AncestorEvent event){}
    });
  }

  /**
   * Get the node list into the combo boxes, alphabetizing
   */
  @SuppressWarnings("unchecked")
  private void fillNodeCombos()
  {
    Vector<Element> v = route.getSpecialNodeList(target, protoBodyMarker);
    Vector vx = new Vector(v.size() > 0 ? v.size() : 1);

   int index = -1;
   for (Element el : v) {
      index ++;
      if (el.getName().equalsIgnoreCase(ROUTE_ELNAME)) //"ROUTE"
        continue;
      Attribute attr = el.getAttribute("DEF");
      if (attr == null)
        continue;
      vx.add(new xNode(el));

      // check, warn if duplicate DEF names exist
      for (int i=0; i < index; i++)
      {
        Attribute priorDEF = v.get(i).getAttribute("DEF");
        if ((priorDEF != null) && (priorDEF.toString().equals(attr.toString())))
        {
            String nodeMessage;
            if (v.get(i).getName().equals(el.getName()))
                 nodeMessage = " for two " + el.getName() + " nodes";
            else nodeMessage = " for a " + v.get(i).getName() + " node and a " + el.getName() + " node";
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "<html><p align='center'>Found duplicated DEF name " + attr.toString() +
                    "</p><br /><p align='center'> " + nodeMessage +
                    "</p><br /><p align='center'>Please find duplicate DEF names in your scene, then rename or remove them!</p></html>", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
        }
      }
    }

    if (vx.size() > 1)
      Collections.sort(vx);

    if (vx.size() <= 0) {
      String msg = NbBundle.getMessage(getClass(), "ROUTE_NO_DEFS_MSG");
      fromNodeWarningLabel.setText(msg);
        toNodeWarningLabel.setText(msg);
    }
    else {
      fromNodeWarningLabel.setText("");
        toNodeWarningLabel.setText("");
    }
    fromNodeCB.setModel(new DefaultComboBoxModel(vx));
      toNodeCB.setModel(new DefaultComboBoxModel(vx));
  }

  private final String SYNONYM_SUFFIX = "_changed";
  private final String SYNONYM_PREFIX = "set_";

  private int convRouteObj(String s, JComboBox cb)
  {
    if (s != null && s.length() > 0) {
      ComboBoxModel mod = cb.getModel();
      for (int i = 0; i < mod.getSize(); i++) {
        String def = unMangleNodeString(mod.getElementAt(i).toString());
        if (def.equals(s))
          return i;
      }
    }
    return -1;// legal for jcombobox.setselectedIndex()
  }
  private int getFieldIndex(String fieldName, JComboBox cb)
  {
    String convertedFieldName = convertSetChangedNames(fieldName);

    if (convertedFieldName != null && convertedFieldName.length() > 0) {
      ComboBoxModel mod = cb.getModel();
      for (int i = 0; i < mod.getSize(); i++) {
        FieldPan fp = (FieldPan)mod.getElementAt(i);
        if(convertSetChangedNames(fp.getFieldName()).equals(convertedFieldName))
          return i;
      }
    }
    return -1;// legal for jcombobox.setselectedIndex()
  }
  private String convertSetChangedNames(String s)
  {
    String result = s; // avoid resetting input parameter
    if(result.endsWith(SYNONYM_SUFFIX))
       result = s.substring(0, s.length()-SYNONYM_SUFFIX.length());
    if(result.startsWith(SYNONYM_PREFIX))
       result = s.substring(SYNONYM_PREFIX.length());
    return result;
  }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        fromNodeCB = new javax.swing.JComboBox<>();
        fromNodeLabel = new javax.swing.JLabel();
        fromFieldLabel = new javax.swing.JLabel();
        fromFieldCB = new javax.swing.JComboBox<>();
        fromTypeLab = new javax.swing.JLabel();
        fromAccessLab = new javax.swing.JLabel();
        fromNodeWarningLabel = new javax.swing.JLabel();
        fromFieldWarningLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        toNodeLabel = new javax.swing.JLabel();
        toNodeCB = new javax.swing.JComboBox<>();
        toFieldLabel = new javax.swing.JLabel();
        toFieldCB = new javax.swing.JComboBox<>();
        toTypeLab = new javax.swing.JLabel();
        toAccessLab = new javax.swing.JLabel();
        toNodeWarningLabel = new javax.swing.JLabel();
        toFieldWarningLabel = new javax.swing.JLabel();
        hintPanel = new javax.swing.JPanel();
        eventLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(385, 400));
        setPreferredSize(new java.awt.Dimension(600, 400));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Event Source", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 2, 12))); // NOI18N
        jPanel1.setToolTipText("Source node provides a typed value for animation");
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        fromNodeCB.setEditable(true);
        fromNodeCB.setToolTipText("select DEF name of source node");
        fromNodeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fromNodeCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        jPanel1.add(fromNodeCB, gridBagConstraints);

        fromNodeLabel.setText("fromNode");
        fromNodeLabel.setToolTipText("Source fromNode must have a DEF name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(6, 9, 3, 3);
        jPanel1.add(fromNodeLabel, gridBagConstraints);

        fromFieldLabel.setText("fromField");
        fromFieldLabel.setToolTipText("fromField is part of fromNode");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 9, 3, 3);
        jPanel1.add(fromFieldLabel, gridBagConstraints);

        fromFieldCB.setEditable(true);
        fromFieldCB.setToolTipText("select name of field producing events from source node");
        fromFieldCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fromFieldCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel1.add(fromFieldCB, gridBagConstraints);

        fromTypeLab.setText("type");
        fromTypeLab.setToolTipText("types must match");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel1.add(fromTypeLab, gridBagConstraints);

        fromAccessLab.setText("accessType");
        fromAccessLab.setToolTipText("only outputs can be a source");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 25);
        jPanel1.add(fromAccessLab, gridBagConstraints);

        fromNodeWarningLabel.setForeground(java.awt.Color.red);
        fromNodeWarningLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fromNodeWarningLabel.setText(org.openide.util.NbBundle.getMessage(ROUTECustomizer.class, "ROUTE_NO_DEFS_MSG")); // NOI18N
        fromNodeWarningLabel.setPreferredSize(new java.awt.Dimension(340, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel1.add(fromNodeWarningLabel, gridBagConstraints);

        fromFieldWarningLabel.setForeground(java.awt.Color.red);
        fromFieldWarningLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fromFieldWarningLabel.setText(org.openide.util.NbBundle.getMessage(ROUTECustomizer.class, "ROUTE_NO_DEFS_MSG")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel1.add(fromFieldWarningLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Event Destination", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 2, 12))); // NOI18N
        jPanel2.setToolTipText("changes a value in the scene graph");
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        toNodeLabel.setText("toNode");
        toNodeLabel.setToolTipText("Destination toNode must have a DEF name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 23, 3, 3);
        jPanel2.add(toNodeLabel, gridBagConstraints);

        toNodeCB.setEditable(true);
        toNodeCB.setToolTipText("select DEF name of destination node");
        toNodeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toNodeCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        jPanel2.add(toNodeCB, gridBagConstraints);

        toFieldLabel.setText("toField");
        toFieldLabel.setToolTipText("toField is part of toNode");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        jPanel2.add(toFieldLabel, gridBagConstraints);

        toFieldCB.setEditable(true);
        toFieldCB.setToolTipText("select name offield receiving events in destination node");
        toFieldCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toFieldCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel2.add(toFieldCB, gridBagConstraints);

        toTypeLab.setText("type");
        toTypeLab.setToolTipText("types must match");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel2.add(toTypeLab, gridBagConstraints);

        toAccessLab.setText("accessType");
        toAccessLab.setToolTipText("only inputs can be a destination");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 25);
        jPanel2.add(toAccessLab, gridBagConstraints);

        toNodeWarningLabel.setForeground(java.awt.Color.red);
        toNodeWarningLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toNodeWarningLabel.setText(org.openide.util.NbBundle.getMessage(ROUTECustomizer.class, "ROUTE_NO_DEFS_MSG")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel2.add(toNodeWarningLabel, gridBagConstraints);

        toFieldWarningLabel.setForeground(java.awt.Color.red);
        toFieldWarningLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        toFieldWarningLabel.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel2.add(toFieldWarningLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jPanel2, gridBagConstraints);

        hintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hintPanel.setPreferredSize(new java.awt.Dimension(500, 200));
        hintPanel.setLayout(new java.awt.GridBagLayout());

        eventLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventLabel.setText("<html> <p align=\"center\"><b>ROUTE</b> enables event passing by connecting a source node's output field to a destination node's input field </p>\n<ul>\n    <li> Warning: each ROUTE must follow the DEF definition of both event-source and event-target nodes. </li>\n    <li> Warning: strongly typed event checking means that data type (SFInt32, MFVec3f, etc.) must match identically for both event-source and event-target fields. </li>\n    <li> Warning: event-source fields can have accessType outputOnly or inputOutput, while event-target nodes can have accessType inputOnly or inputOutput. </li>\n    <li> Hint: X3D-Edit addition of event tracing supports animation debugging. </li>\n</ul>\n\n\n");
        eventLabel.setToolTipText("ROUTE passes events by connecting fields between source and destination nodes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hintPanel.add(eventLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private String lastFromNodeType = "";
  private void fromNodeCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_fromNodeCBActionPerformed
  {//GEN-HEADEREND:event_fromNodeCBActionPerformed
    Object obj = fromNodeCB.getSelectedItem();
    if(obj == null || !(obj instanceof xNode))
      return;
    xNode xn = (xNode)obj;
    String nodeType = xn.e.getName();
    if(nodeType.equals(lastFromNodeType))
      return; // Don't reload field combo; throws away existing field selection
    lastFromNodeType = nodeType;

    fromFieldCB.setModel(loadFieldsFromSelectedNode(nodeType,false,fromNodeCB.getSelectedItem()));
    if(fromFieldCB.getModel().getSize() <= 0)
    {
        if (nodeType.equalsIgnoreCase("unknown"))
            fromFieldWarningLabel.setText("select source node DEF label");
        else
            fromFieldWarningLabel.setText(nodeType + " output fields not found using Xj3D (missing node support?)");
    }
    else {
      selectDefaultField(nodeType,fromFieldCB,false);
      fromFieldWarningLabel.setText("");
      checkCircularRoute();
    }
  }//GEN-LAST:event_fromNodeCBActionPerformed

  private String lastToNodeType = "";
  private void toNodeCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_toNodeCBActionPerformed
  {//GEN-HEADEREND:event_toNodeCBActionPerformed
    Object obj = toNodeCB.getSelectedItem();
    if(obj == null || !(obj instanceof xNode))
      return;
    xNode xn = (xNode)obj;
    String nodeType = xn.e.getName();
    if(nodeType.equals(lastToNodeType))
      return; // Don't reload field combo; throws away existing field selection
    lastToNodeType = nodeType;

    toFieldCB.setModel(loadFieldsFromSelectedNode(nodeType,true,toNodeCB.getSelectedItem()));
    if(toFieldCB.getModel().getSize() <= 0)
    {
        if (nodeType.equalsIgnoreCase("unknown"))
            toFieldWarningLabel.setText("select destination node DEF label");
        else
            toFieldWarningLabel.setText(nodeType + " input fields not found using Xj3D (missing node support?)");
    }
    else {
      selectDefaultField(nodeType,toFieldCB,true);
      toFieldWarningLabel.setText("");
      checkCircularRoute();
    }
  }//GEN-LAST:event_toNodeCBActionPerformed

  private void fromFieldCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_fromFieldCBActionPerformed
  {//GEN-HEADEREND:event_fromFieldCBActionPerformed
    Object obj = fromFieldCB.getSelectedItem();
    if(obj instanceof FieldPan)
      currentFromFieldType = ((FieldPan)obj).getFieldType();
    else
      currentFromFieldType = "";
    checkFieldMismatch();
    checkTypeErrors();
    checkCircularRoute();
    toFieldCB.invalidate();
  }//GEN-LAST:event_fromFieldCBActionPerformed

  private void toFieldCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_toFieldCBActionPerformed
  {//GEN-HEADEREND:event_toFieldCBActionPerformed
    Object obj = toFieldCB.getSelectedItem();
    if(obj instanceof FieldPan)
      currentToFieldType = ((FieldPan)obj).getFieldType();
    else
      currentToFieldType = "";
    checkFieldMismatch();
    checkTypeErrors();
    checkCircularRoute();
    fromFieldCB.invalidate();
  }//GEN-LAST:event_toFieldCBActionPerformed

  private void checkTypeErrors()
  {
    Color foreColor = defaultLabForeground;
    if(fieldError)
      foreColor = Color.red;

    fromTypeLab.setForeground(foreColor);
    toTypeLab.setForeground(foreColor);
  }

  private void checkCircularRoute()
  {
    Object obj = toNodeCB.getSelectedItem();
    if(obj == null || !(obj instanceof xNode))
      return;
    xNode xn = (xNode)obj;
    String toNodeName = xn.getDef();

    obj = fromNodeCB.getSelectedItem();
    if(obj == null || !(obj instanceof xNode))
      return;
    xn = (xNode)obj;
    String fromNodeName = xn.getDef();
    if(!fromNodeName.equals(toNodeName))
      return;

    obj = toFieldCB.getSelectedItem();
    if(obj == null || !(obj instanceof FieldPan))
      return;
    FieldPan fp = (FieldPan)obj;
    String toFieldName = fp.getFieldName();

    obj = fromFieldCB.getSelectedItem();
    if(obj == null || !(obj instanceof FieldPan))
      return;
    fp = (FieldPan)obj;
    String fromFieldName = fp.getFieldName();
    if(!fromFieldName.equals(toFieldName))
      return;

    if((toNodeName.length()==0) || (fromNodeName.length()==0) || (toFieldName.length()==0) || (fromFieldName.length()==0))
      return;

    showCircularWarningDialog();
  }

  private void showCircularWarningDialog()
  {
    NotifyDescriptor descriptor = new NotifyDescriptor.Message(
        "Circular ROUTE connects same field in same node.",NotifyDescriptor.WARNING_MESSAGE);

    DialogDisplayer.getDefault().notify(descriptor);
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel eventLabel;
    private javax.swing.JLabel fromAccessLab;
    private javax.swing.JComboBox<String> fromFieldCB;
    private javax.swing.JLabel fromFieldLabel;
    private javax.swing.JLabel fromFieldWarningLabel;
    private javax.swing.JComboBox<String> fromNodeCB;
    private javax.swing.JLabel fromNodeLabel;
    private javax.swing.JLabel fromNodeWarningLabel;
    private javax.swing.JLabel fromTypeLab;
    private javax.swing.JPanel hintPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel toAccessLab;
    private javax.swing.JComboBox<String> toFieldCB;
    private javax.swing.JLabel toFieldLabel;
    private javax.swing.JLabel toFieldWarningLabel;
    private javax.swing.JComboBox<String> toNodeCB;
    private javax.swing.JLabel toNodeLabel;
    private javax.swing.JLabel toNodeWarningLabel;
    private javax.swing.JLabel toTypeLab;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_ROUTE";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
      if (fromNodeCB.getSelectedItem() != null)
           route.setFromNode (unMangleNodeString(fromNodeCB.getSelectedItem().toString()));
      else route.setFromNode ("");
      
      if (fromFieldCB.getSelectedItem() != null)
           route.setFromField(fromFieldCB.getSelectedItem().toString());
      else route.setFromNode ("");
      
      if (toNodeCB.getSelectedItem() != null)
           route.setToNode   (  unMangleNodeString(toNodeCB.getSelectedItem().toString()));
      else route.setToNode ("");
      
      if (toFieldCB.getSelectedItem() != null)
           route.setToField  (  toFieldCB.getSelectedItem().toString());
      else route.setToField ("");
      
      Object obj = toFieldCB.getSelectedItem();
      if (obj instanceof FieldPan)
          currentToFieldType = ((FieldPan) obj).getFieldType();
      else
          currentToFieldType = "";
      route.setEventType(currentToFieldType);
      
//    Object fldObj = fromFieldCB.getSelectedItem();
//    String fromFldStr = "";
//    FieldPan fromFieldPan = null;
//    if(fldObj instanceof FieldPan) {
//      fromFieldPan = (FieldPan)fldObj;
//      fromFldStr = fromFieldPan.getFieldName();
//    }
//
//    fldObj = toFieldCB.getSelectedItem();
//    String toFldStr = "";
//    FieldPan toFieldPan = null;
//    if(fldObj instanceof FieldPan) {
//      toFieldPan = (FieldPan)fldObj;
//      toFldStr = toFieldPan.getFieldName();
//    }
//
//    Object nodeObj = fromNodeCB.getSelectedItem();
//
//    route.setFromNode (unMangleNodeString(nodeObj.toString()));
//    route.setFromField(fromFldStr);
//
//    nodeObj = toNodeCB.getSelectedItem();
//    route.setToNode (unMangleNodeString(nodeObj.toString()));
//    route.setToField(toFldStr);
//
//    if(fromFieldPan == null || toFieldPan == null)
//      return;
//
//    route.setEventType(fromFieldPan.getFieldType());
//
//    if(!fromFieldPan.getFieldType().equalsIgnoreCase(toFieldPan.getFieldType()))
//      throw new IllegalArgumentException("Field types ("+fromFieldPan.getFieldType()+", "+toFieldPan.getFieldType()+") do not match.");
  }

  private String unMangleNodeString(Object o, int wh)
  {
    String s = (String)o;
    if(s.length()<=0)
      return s;
    s = s.substring(hdr.length());
    String[] sa = s.split(sep);
    return (sa.length>wh?sa[wh]:"").trim();    // 0=type, 1=def
  }

  private String unMangleNodeString(Object o)
  {
    return unMangleNodeString(o,1);   // get def
  }

  void checkFieldMismatch() // TODO test
  {
    fieldError = false;
    Object pan = toFieldCB.getSelectedItem();
    String selectedType = "";
    if(pan instanceof FieldPan)
      selectedType = ((FieldPan)pan).getFieldType();
    else if(pan instanceof JLabel)
      selectedType = ((JLabel)pan).getText();

    if (selectedType.startsWith("set_"))
        selectedType = selectedType.substring("set_".length());
    if (selectedType.endsWith("_changed"))
        selectedType = selectedType.substring(0,selectedType.length() - "_changed".length());
    if(!currentFromFieldType.equalsIgnoreCase(selectedType)) {
        fieldError=true;
        return;
    }

    pan = fromFieldCB.getSelectedItem();
    selectedType = "";
    if(pan instanceof FieldPan)
      selectedType = ((FieldPan)pan).getFieldType();
    else if(pan instanceof JLabel)
      selectedType = ((JLabel)pan).getText();

    if (selectedType.startsWith("set_"))
        selectedType = selectedType.substring("set_".length());
    if (selectedType.endsWith("_changed"))
        selectedType = selectedType.substring(0,selectedType.length() - "_changed".length());
    if(!currentToFieldType.equalsIgnoreCase(selectedType)) {
        fieldError=true;
        return;
    }
  }

  final int FROM=0;
  final int TO=1;

  class myNodeRenderer extends DefaultListCellRenderer
  {
    int wh;
    myNodeRenderer(int wh)
    {
      this.wh = wh;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
      if(value instanceof JLabel)
        return (JLabel)value;

      return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }

  }

  class myNodeEditor extends FieldBaseComboEditor
  {
    Object setObj;

    @Override
    public void setItem(Object o)
    {
      setObj = o;
      if (o == null) {
        tf.setText("");
        typeLab.setVisible(false);
      }
      else if(o instanceof xNode) {
        tf.setText(((xNode)o).getDef());
        typeLab.setText(((xNode)o).getName());
        typeLab.setVisible(true);
      }
      else if (o instanceof JLabel) {
        tf.setText(((JLabel)o).getText());
        typeLab.setVisible(false);
      }
      else if(o instanceof String) {
        tf.setText(o.toString());
        typeLab.setVisible(false);
      }
    }

    @Override
    public Object getItem()
    {
      if (setObj == null)
        return("");

      String s = tf.getText().trim();

      if(setObj instanceof xNode) {
        xNode xn = (xNode)setObj;
        if(!xn.getDef().equals(s)) {
          xn = new xNode(new Element("unknown"));
          xn.setDef(s);
        }
        else
          typeLab.setVisible(true);
        return xn;
      }
      return null; // check for legal
    }
 }
/*
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
  private void setCBRenderersAndEditors()
  {
    //fromFieldCB.setRenderer(new myFieldRenderer(FROM));
    fromFieldCB.setRenderer(new FieldRenderer(new RedIndicator()
    {
      @Override
      public boolean markFieldTypeRed(String fieldName)
      {
        return !fieldName.equals(currentToFieldType);
      }

    }));

    //  toFieldCB.setRenderer(new myFieldRenderer(TO));
    toFieldCB.setRenderer(new FieldRenderer(new RedIndicator()
    {
      @Override
      public boolean markFieldTypeRed(String fieldName)
      {
        return !fieldName.equals(currentFromFieldType);
      }

    }));
    fromFieldCB.setEditor(new FieldEditor()); //myFieldEditor());
      toFieldCB.setEditor(new FieldEditor()); //myFieldEditor());

     fromNodeCB.setRenderer(new myNodeRenderer (FROM));
       toNodeCB.setRenderer(new myNodeRenderer (TO));
     fromNodeCB.setEditor(new myNodeEditor());
       toNodeCB.setEditor(new myNodeEditor());
  }

  private void selectDefaultField(String nodeName, JComboBox cb, boolean inputsVsOutputs)
  {
    int idx = 0;
    String fld = null;

    RouteDefault rd = defaultFields.getDefaults(nodeName);
    if (rd != null) {
      if (inputsVsOutputs && rd.to != null)
        fld = rd.to;
      else if (rd.from != null)
        fld = rd.from;
    }
    if (fld != null) {
      int sz = cb.getItemCount();
      for (int i = 0; i < sz; i++) {
        Object ob = cb.getItemAt(i);
        if (ob instanceof FieldPan) {
          String listedFieldName = ((FieldPan)ob).getFieldName();
          if (listedFieldName.equals(fld) ||
              listedFieldName.equals(SYNONYM_PREFIX+fld) ||
              listedFieldName.equals(fld+SYNONYM_SUFFIX)) {
            idx = i;
            break;
          }
        }
      }
    }
    cb.setSelectedIndex(idx);
   }

  @SuppressWarnings("unchecked")
  private ComboBoxModel<String> loadFieldsFromSelectedNode(String nodeName, boolean inputsVsOutputs, Object nodeObj)
  {
    Vector<JComponent> vec = new Vector<>();
    if(nodeName.length() <=0){
     // vec.add(new JLabel(""));
      return (ComboBoxModel<String>)new DefaultComboBoxModel(vec); // this is an "unchecked conversion"
    }

    Vector<String> vs = new Vector<>();
    X3DFieldDefinition[] fields;
    if(nodeName.equalsIgnoreCase(SCRIPT_ELNAME) || // "Script"
       nodeName.equalsIgnoreCase(PROTOINSTANCE_ELNAME)) //"ProtoInstance"
      fields = getSpecialCaseFields(nodeName,nodeObj);
    else
      fields = ROUTE.getNodeFields(nodeName);

    for(X3DFieldDefinition fld : fields) {
      int acc = fld.getAccessType();
      if(acc == X3DFieldTypes.INPUT_OUTPUT)
        vs.add(mangleFieldStrings(fld));
      else if(acc == X3DFieldTypes.INITIALIZE_ONLY)
        continue;
      else if(acc == X3DFieldTypes.INPUT_ONLY && inputsVsOutputs)
        vs.add(mangleFieldStrings(fld));
      else if(acc == X3DFieldTypes.OUTPUT_ONLY && !inputsVsOutputs)
        vs.add(mangleFieldStrings(fld));
    }
    Collections.sort(vs);
    for(String s : vs) {
      String[] sa = s.split("\t");
      vec.add(new FieldPan(sa[0],sa[1],sa[2]));
    }
    /*
    if(vec.isEmpty()) {
      //mike here do better
      String ios = inputsVsOutputs?"input":"output";
     // vec.add(new JLabel("No "+ios+" fields defined in referenced "+nodeName));
      //todo warning label here.
      FieldPan fp = new FieldPan("unknown","unknown","unknown");
      vec.add(fp);
    }
     * */
    return (ComboBoxModel<String>)(new DefaultComboBoxModel(vec));
  }
  //todo clean up; this code is also used in BaseX3DElement
  private String mangleFieldStrings(X3DFieldDefinition fld)
  {
    return fld.getName()+"\t"+fld.getFieldTypeString()+"\t"+accessToString(fld.getAccessType());
  }

  private static String io   = "inputOutput";
  private static String init = "initializeOnly";
  private static String inpt = "inputOnly";
  private static String outp = "outputOnly";

  public static String accessToString(int acc)
  {
    switch(acc) {
      case X3DFieldTypes.INPUT_OUTPUT:
        return io;
      case X3DFieldTypes.INITIALIZE_ONLY:
        return init;
      case X3DFieldTypes.INPUT_ONLY:
        return inpt;
      case X3DFieldTypes.OUTPUT_ONLY:
        return outp;
      default:
        return "";
    }
  }

  private int stringToAccess(String acc)
  {
    if(acc.equalsIgnoreCase(io))
      return X3DFieldTypes.INPUT_OUTPUT;
    if(acc.equalsIgnoreCase(init))
      return X3DFieldTypes.INITIALIZE_ONLY;
    if(acc.equalsIgnoreCase(inpt))
      return X3DFieldTypes.INPUT_ONLY;
    if(acc.equalsIgnoreCase(outp))
      return X3DFieldTypes.OUTPUT_ONLY;
    return -1;
  }

  @SuppressWarnings("unchecked")
  private X3DFieldDefinition[] getSpecialCaseFields(String nodeName, Object nodeObj)
  {
    X3DFieldDefinition[] fArr = new xFld[0];
    if(!(nodeObj instanceof xNode))
      return fArr;

    xNode xn = (xNode)nodeObj;
    Element elm = xn.e;

    if(nodeName.equalsIgnoreCase(PROTOINSTANCE_ELNAME)) {
      elm = getProtoInstanceFieldsParent(xn); // got to go to declare and get ProtoInterface element
      if (elm == null)
        return fArr;
    }

    Vector<X3DFieldDefinition> flds = new Vector<>();
    for(Element child : ((List<Element>)elm.getChildren(FIELD_ELNAME))) {  //"field"
      String nm = child.getAttribute(FIELD_ATTR_NAME_NAME)==null?""      :child.getAttributeValue(FIELD_ATTR_NAME_NAME);       //"name"
      String ty = child.getAttribute(FIELD_ATTR_TYPE_NAME)==null?""      :child.getAttributeValue(FIELD_ATTR_TYPE_NAME);       //"type"
      String ac = child.getAttribute(FIELD_ATTR_ACCESSTYPE_NAME)==null?"":child.getAttributeValue(FIELD_ATTR_ACCESSTYPE_NAME); //"accessType"
      X3DFieldDefinition xdef = new xFld(nm,ty,ac);
      flds.add(xdef);
    }

    return flds.toArray(fArr);
  }

  private Element getProtoInstanceFieldsParent(xNode node)
  {
    Element elm = node.e;
    String pinst = elm.getAttributeValue(PROTOINSTANCE_ATTR_NAME_NAME); // name
    if(pinst == null)
      return null;
    Map<String,Vector<Element>> hm = route.getNodeMap(target); // need access to declares      //,noProtoDecls);
    Vector<Element> v1 = hm.get(PROTODECLARE_ELNAME);
    Vector<Element> v2 = hm.get(EXTERNPROTODECLARE_ELNAME);
    Vector<Element> vf = new Vector<Element>();
    if(v1 != null)
      vf.addAll(v1);
    if(v2 != null)
      vf.addAll(v2);
    if(vf.size()<=0)
      return null;

    for(Element e : vf) {
      String pdecl = e.getAttributeValue(PROTODECLARE_ATTR_NAME_NAME);
      if(pdecl.equals(pinst)) {
        if(e.getName().equals(PROTODECLARE_ELNAME))
          return e.getChild(PROTOINTERFACE_ELNAME);  // I found the proto declare, if it doesn't have a protoInterface child, cest la vie.
        else //if(e.getName().equals(EXTERNPROTODECLARE_ELNAME))
          return e;
      }
    }
    return null;
  }

  //private static Vector<String> noProtoDecls;
  private static final Vector<String> protoBodyMarker;
  static {
//    noProtoDecls = new Vector<String>();
//    noProtoDecls.add(PROTODECLARE_ELNAME);
//    noProtoDecls.add(EXTERNPROTODECLARE_ELNAME);

    protoBodyMarker = new Vector<>();
    protoBodyMarker.add(PROTOBODY_ELNAME);
  }

  class xNode implements Comparable
  {
    Element e;
    String markerString="__notComparedProperly__";
    String realName = null;

    public xNode(String name, String def)
    {
      e = new Element(name);
      e.setAttribute("DEF", def);
    }
    public xNode(String name, String def, boolean specialName)
    {
      if(specialName) {
        realName = name;
        name=markerString;
      }
      e = new Element(name);
      e.setAttribute("DEF",def);
    }
    public xNode(Element e)
    {
      this.e = e;
    }
    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append(hdr);
      sb.append(e.getName());
      if (e.getName().equals("ProtoInstance"))
      {
          sb.append (" ");
          sb.append (e.getAttribute("name").getValue()); // also show ProtoInstance name
      }
      sb.append(sep); //" <b>"
      sb.append(e.getAttributeValue("DEF"));
      return sb.toString();
    }

    public String getName()
    {
      StringBuilder sb = new StringBuilder();
      if(e != null)
      {
          sb.append(e.getName());
          if (e.getName().equals("ProtoInstance"))
          {
              sb.append (" ");
              sb.append (e.getAttribute("name").getValue()); // also show ProtoInstance name
          }
      }
      return sb.toString();
    }

    public void setName(String s)
    {
      if(e != null)
        e.setName(s);
    }

    public String getDef()
    {
      String s = "";
      if(e != null)
        if(e.getAttribute("DEF") != null)
          s = e.getAttribute("DEF").getValue();
      return s;
    }

    public void setDef(String s)
    {
      if(e != null)
        e.setAttribute("DEF", s);
    }

   @Override
    public int compareTo(Object o)
    {
      return toString().compareTo(o.toString());
    }
  }
  class myJLabel extends JLabel
  {
    public myJLabel(String s)
    {
      super(s);
    }
    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append(hdr);
      sb.append(this.getText().trim());
      sb.append(sep); //" <b>"
      sb.append("unknown");
      return sb.toString();
    }
  }
  class xFld implements X3DFieldDefinition
  {
    String typ;
    String name;
    int acc;

    public xFld(String name, String type, String access)
    {
      this.name = name;
      this.typ = type;
      this.acc = stringToAccess(access);
    }
      @Override
      public int getAccessType() {
          return acc;
      }

      @Override
      public String getFieldTypeString() {
          return typ;
      }

      @Override
      public String getName() {
          return name;
      }

      @Override
      public int getFieldType() {
          throw new UnsupportedOperationException("Program error, RouteCustomizer getFieldType()");
      }
  }

}
