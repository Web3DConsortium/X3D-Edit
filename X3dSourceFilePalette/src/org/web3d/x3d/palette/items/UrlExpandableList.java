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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.HtmlBrowser;
import org.openide.util.NbBundle;

/**
 * UrlExpandableList.java
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 * @deprecated use UrlExpandableList2
 */
@Deprecated
public class UrlExpandableList extends ExpandableList //implements ListSelectionListener
{

  private final int TEST_COLUMN = 0;

  /** Creates new form ExpandableList */
  public UrlExpandableList()
  {
    super();
    setTitle(NbBundle.getMessage(UrlExpandableList.class, "LBL_URL_LIST_TITLE"));
    
    setColumnTitles(new String[] {
      NbBundle.getMessage(UrlExpandableList.class, "LBL_URL_TEST"),   // "test"
      NbBundle.getMessage(UrlExpandableList.class, "LBL_URL"),        // "<html><b>url"
      NbBundle.getMessage(UrlExpandableList.class, "LBL_URL_EDIT")}); // "edit"
    
    setToolTipText(NbBundle.getMessage(SCRIPTCustomizer.class, "LBL_URL_TOOLTIP"));

    doTrailingTextEditButton(true);
    setTextAlignment(JLabel.RIGHT);
    getTable().addMouseListener(new testListener());
  }
  
  private String dotString = "<html><u><font color=\"blue\">open";

  @Override
  public void setData(Object[][] data)
  {
    Object[][] oa = new Object[data.length][2];
    int i = 0;
    for (Object[] o : data) {
      oa[i][0] = dotString;
      oa[i][1] = data[i][0];
      i++;
    }
    super.setData(oa);

    getTable().getColumnModel().getColumn(0).setMaxWidth(new JLabel(dotString).getPreferredSize().width + 10);
    getTable().getColumnModel().getColumn(0).setCellRenderer(new stringCenterer());
  }
  
  @Override
  public String[][] getData()
  {
    TableModel mod = getTable().getModel();
    int rc = mod.getRowCount();

    String[][] data = new String[rc][1];
    for (int row = 0; row < rc; row++)
    {
        data[row][0] = (String) mod.getValueAt(row,1);
    }
    return data;
  }

  class stringCenterer extends DefaultTableCellRenderer
  {

    public stringCenterer()
    {
      super();
      if (this instanceof JLabel)
        ((JLabel) this).setHorizontalAlignment(JLabel.CENTER);
    }
  }

  class testListener extends MouseAdapter
  {

    @Override
    public void mouseClicked(MouseEvent e)
    {
      JTable tab = getTable();
      if (tab.getRowCount() <= 0)
        return;
      int col = tab.columnAtPoint(e.getPoint());
      if(col != TEST_COLUMN)
        return;
      int row = tab.rowAtPoint(e.getPoint());
      String url = (String) tab.getModel().getValueAt(row, 1);

      try {
        HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(url));
        return;
      }
      catch (Exception ex) {
        NotifyDescriptor.Message nd = new NotifyDescriptor.Message("<html>Url could not be displayed:<br>" + ex.getLocalizedMessage(),
                                                                   NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(nd);
      }
    }
  }
}
