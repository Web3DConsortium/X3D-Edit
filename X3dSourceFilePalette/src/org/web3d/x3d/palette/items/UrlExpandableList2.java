/*
Copyright (c) 1995-2021 held by the author(url).  All rights reserved.

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
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.web3d.vrml.util.URLChecker;
import org.web3d.x3d.UrlStatus;
import org.web3d.x3d.actions.conversions.ConversionsHelper;
import org.web3d.x3d.options.X3dOptions;
import org.web3d.x3d.palette.X3DPaletteUtilities;

/**
 * UrlExpandableList2.java
 * Created on August 13, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class UrlExpandableList2 extends JPanel implements ListSelectionListener
{
  private boolean URL_EDIT_VALID = true;
  private boolean URL_EDIT_CANCELLED = false;

  private static FileObject masterFo;
  private String newUrlString = "";
  private boolean alignLeft = false;  // default right alignment so that list of local and remote url addresses line up consistently

  public static Color redForeground = new Color(245, 116, 106);
  public static Color lightBlueBackground = new Color(240, 240, 255);
  private final Color defaultForegroundColor;

  private UrlCustomizerPanel urlCustomizerPanel = new UrlCustomizerPanel();

  private String headerIdentifierPath = "";

  private JTextComponent target;
  
  private boolean alternateFormatsExpected = false;
  
  private DefaultListModel<String> urlListModel;
  
  protected final String ADD_URL_HINT = "Select (Edit url value) button to edit";

  public UrlExpandableList2()
  {
    initComponents();
    urlJList.addListSelectionListener(UrlExpandableList2.this); // warning silenced
    urlJList.addMouseListener(mouseListener);
    //setTitle(NbBundle.getMessage(getClass(), "LBL_URL_LIST_2_TITLE"));  // done by creator if desired
    urlJList.setCellRenderer(new LinedRenderer());
    urlJList.setSelectionBackground(lightBlueBackground);
    defaultForegroundColor = sortButton.getForeground(); // save for reuse
    
    whoisButton.setVisible(false); // not needed since whois gets included in domain report
    buttonEnabler(true);
  }
  /**
   * Report url list size
   * @return number of url values in list
   */
  public final int getLength()
  {
	  return urlJList.getModel().getSize();
  }
  /**
   * Set selected index of table
   * @param index index to select
   * @return whether successfully selected
   */
  public final boolean setSelectedIndex(int index)
  {
	  if ((index >= 0) && (urlJList.getModel().getSize() >= index))
	  {
		   urlJList.setSelectedIndex(index);
		   return true;
	  }
	  else return false;
  }
  /**
   * If list is empty, add Initial Row Hint to prompt user on next step
   */
  public final void addInitialRowHint()
  { 
    if (urlJList.getModel().getSize() == 0)
    { 
        DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();
        mod.insertElementAt(ADD_URL_HINT, 0);
        urlJList.setSelectedIndex(0);  // keep it selected
        urlJList.ensureIndexIsVisible(0);
    }
  }
  /**
   * If list is empty, remove Initial Row Hint
   */
  public final void removeInitialRowHint()
  { 
    urlListModel = (DefaultListModel<String>) urlJList.getModel();
    for (int i = 0; i < urlListModel.size(); i++)
    {
        if (urlJList.getModel().getElementAt(i).equalsIgnoreCase(ADD_URL_HINT))
        { 
            urlListModel.remove(i);
        }
    }
  }
  /**
   * indicate if list is empty except for Initial Row Hint
   * @return whether hint is in initial row
   */
  public final boolean isInitialRowHint()
  { 
      if (urlJList.getModel().getSize() == 0)
           return false;
      else return urlJList.getModel().getElementAt(0).equalsIgnoreCase(ADD_URL_HINT);
  }
  /**
   * Check url values after initial loading
   */
  public final void checkUrlValues()
  {
    urlListModel = (DefaultListModel<String>) urlJList.getModel();
    for (int i = 0; i < urlListModel.size(); i++)
    {
      String address = urlListModel.elementAt(i);
      
      if (address.contains("https:/") && !address.contains("https://"))
      {              
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                "<html><p align='center'>url[" + i + "] starts with https:/ instead of https:// - fix it?</p><br/><p align='center'>" + address + "</p>", 
				NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor)== NotifyDescriptor.YES_OPTION)
          {
              address = address.replace("https:/", "https://");
              urlListModel.setElementAt(address, i);
              urlJList.setModel(urlListModel);
              urlJList.repaint();
          }
      }
      if (address.contains("http:/") && !address.contains("http://"))
      {              
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                "<html><p align='center'>url[" + i + "] starts with http:/ instead of http:// - fix it?</p><br/><p align='center'>" + address + "</p>", 
				NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor)== NotifyDescriptor.YES_OPTION)
          {
              address = address.replace("http:/", "http://");
              urlListModel.setElementAt(address, i);
              urlJList.setModel(urlListModel);
              urlJList.repaint();
          }
      }
      if (address.contains("http://"))
      {              
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                "<html><p align='center'>url[" + i + "] address is insecure, http:// instead of https:// - fix it?</p><br/><p align='center'>" + address + "</p>", 
			    "Insecure http address found",
				NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor)== NotifyDescriptor.YES_OPTION)
          {
              address = address.replace("http://", "https://");
              urlListModel.setElementAt(address, i);
              urlJList.setModel(urlListModel);
              urlJList.repaint();
          }
      }
      if (address.contains("sftp:/") && !address.contains("sftp://"))
      {              
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                "<html><p align='center'>url[" + i + "] contains sftp:/ instead of sftp:// - fix it?</p><br/><p align='center'>" + address + "</p>", 
				  NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor)== NotifyDescriptor.YES_OPTION)
          {
              address = address.replace("sftps:/", "sftps://");
              urlListModel.setElementAt(address, i);
              urlJList.setModel(urlListModel);
              urlJList.repaint();
          }
      }
      if (address.contains("\\"))
      {
          String plural = "";
          if (address.lastIndexOf("\\") > address.indexOf("\\"))
              plural = "s";
              
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                "<html><p align='center'>Found backslash character" + plural + " in url[" + i + "], change to forward slash?</p><br/><p align='center'>" + address + "</p>", 
				NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor)== NotifyDescriptor.YES_OPTION)
          {
              address = address.replaceAll("\\\\", "/"); // regex for Java-escaped backslash
              if (address.contains("://"))
              { 
                // avoid duplicate // artifacts in path
                address = address.substring(0,address.indexOf("://")) + "://" + address.substring(address.indexOf("://")+3).replaceAll("//", "/");  
              }
              urlListModel.setElementAt(address, i);
              urlJList.setModel(urlListModel);
              urlJList.repaint();
          }
      }
	  int hashCount = address.split("#").length - 1;
      if (hashCount > 1)
      {              
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                "<html><p align='center'><b>url[" + i + "] includes " + hashCount + " # signs</b> but only 1 is allowed for each bookmark, viewpoint or prototype definition. </p><br/>" + 
					  "<p align='center'> <b>Strip after second occurrence?</b></p><br/><p align='center'>" + address + "</p>", 
				  NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor)== NotifyDescriptor.YES_OPTION)
          {
			  while (hashCount > 1)
			  {
				  address = address.substring(0,address.lastIndexOf("#"));
				  hashCount = address.split("#").length - 1;
			  }
              urlListModel.setElementAt(address, i);
              urlJList.setModel(urlListModel);
              urlJList.repaint();
          }
      }
    }
  }

  public void setTitle(String title)
  {
    TitledBorder tb = new TitledBorder(new EmptyBorder(0, 0, 0, 0), title);
    tb.setTitleColor(this.getForeground());
    tb.setTitleFont(this.getFont());
    this.setBorder(tb);
  }

  /**
   * Check head section of X3D scene for <meta name='identifier' content='http://onlinePathToScene/sceneName.x3d'/>
   * @return
   */
  private String getHeaderIdentifierPath()
  {
    if (target == null)
    {
        System.out.println ("UrlExpandableList2 did not receive target value from parent class, so getHeaderIdentifierPath() cannot check DOM tree for meta tag value");
        return "";
    }
    Document doc = X3DPaletteUtilities.getJdom(target);
    Element headElement = doc.getRootElement().getChild("head");

    if(headElement != null)
    {
      @SuppressWarnings("unchecked")
      List<Element>metaElements = (List<Element>)headElement.getChildren("meta");
      if (metaElements != null)
      {
        Attribute attrName;
        Attribute attrCont;
        for(Element elm : metaElements)
        {
          if(( (attrName = elm.getAttribute("name"))    != null ) &&
             ( (attrCont = elm.getAttribute("content")) != null ) &&
             (  attrName.getValue().equals("identifier")))
          {
            String content = attrCont.getValue();
            if ((content.startsWith("http://")) || (content.startsWith("https://")) || (content.startsWith("ftp://")) || (content.startsWith("sftp://")))
            {
                return content.substring(0,content.lastIndexOf('/')+1);
            }
          }
        }
      }
    }
    return "";
  }

  public void setMasterDocumentLocation(FileObject fo)
  {
    masterFo = fo;
  }

  /**
   * Set url array with single value
   * @param url Single url address
   */
  public void setUrlData(String url)
  {
      String[] urlArray  = new String[1];
      if (url.trim().length() > 2)
      {
          if  (url.trim().contains("\" \""))
               urlArray    = url.trim().split(" ");
          else urlArray[0] = url.trim();
          setUrlData(urlArray);
      }
  }

  public void setUrlData(String[] urls)
  {
    _setUrlData(urls);

    urlJList.clearSelection();
    buttonEnabler(true);
    testSortOrder();
    checkUrlValuesMatchCase ();
    urlJList.repaint();
  }

  private void _setUrlData(String[] urls)
  {
    DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();
    mod.removeAllElements();
    for (String s : urls)
    {
        s = s.trim();
        if (s.startsWith("\""))
        {
            s = s.substring(1);                // strip leading quote
        }
        if (s.endsWith("\""))
        {
            s = s.substring(0,s.length() - 1); // strip trailing quote
        }
        if (s.length() > 0)
            mod.addElement(s);
    }
    addInitialRowHint(); // if needed
  }

  public JList<String> getJList()
  {
    return urlJList;
  }

  public boolean isGreenLink(int idx)
  {
    if(idx >= urlJList.getModel().getSize())
      return false;
    String[] sa = getUrlDataIncludingBlanks();
    StatusPack sp = urlToStatus.get(sa[idx]);
    if (sp == null)
      return false;
    return sp.status == UrlStatus.SUCCESS;
  }
  
  private void buttonEnabler(boolean checkUrl)
  {
    int idx = urlJList.getSelectedIndex(); // TODO use size instead?
    boolean indexSelected = idx >= 0;
    
    editUrlButton.setEnabled(true);
    if (isInitialRowHint())
    { 
        indexSelected = false;
        appendButton.setEnabled(false);
        additionalUrlsButton.setEnabled(false);
    }
    else 
    {
        appendButton.setEnabled(true);
        checkAddUrlsButtonEnabled ();
    }

     // plusButt.setEnabled(enabled);
     removeButton.setEnabled(indexSelected);
    
        upButton.setEnabled(indexSelected);
      downButton.setEnabled(indexSelected);

        launchButton.setEnabled(indexSelected);
          loadButton.setEnabled(indexSelected);
externalEditorButton.setEnabled(indexSelected);
      
      qaButton.setEnabled(indexSelected && (urlJList.getModel().getSize() > 0));
      // usability improvement: always show QA button, rather than use hasQaTest(urlJList.getSelectedValue()));
      // allowing user validation of local files by alerting players to use QA-page widget

//      String url = urlJList.getSelectedValue();
//      if ((url != null) && url.trim().endsWith(".x3d") && !isOnlineUrl(url))
//          qaButton.setEnabled(false); // TODO local path capability on servlet might not be possible
    
      if (indexSelected && (getHost(urlJList.getSelectedValue()).length() > 4))
      {
         domainButton.setEnabled(true);
           pingButton.setEnabled(true);
          whoisButton.setEnabled(true);
      }
      else
      {
         domainButton.setEnabled(false);
           pingButton.setEnabled(false);
          whoisButton.setEnabled(false);
      }

      sortButton.setEnabled (urlJList.getModel().getSize()  > 1);

    if (checkUrl)
    {
        hitUrlStatus();
    }
  }
  private void testSortOrder()
  {
    int len = urlJList.getModel().getSize();
    sortButton.setForeground(defaultForegroundColor); // reset prior to test
    urlListModel = (DefaultListModel<String>) urlJList.getModel();
    for (int i = 0; i < len - 1; i++) // don't overshoot array bounds during subsequent comparison
    {
        if (urlRank(urlListModel.elementAt(i)) > urlRank(urlListModel.elementAt(i+1)))
        {
            sortButton.setForeground(redForeground);  // list fails order test
            return;
        }
        if ((urlListModel.elementAt(i).endsWith(".gif")) && !(urlListModel.elementAt(i+1).endsWith(".gif")))
        {
            sortButton.setForeground(redForeground);  // list fails order test
            return;
        }
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e)
  {
    if (e.getValueIsAdjusting())
      return;
    int idx = urlJList.getSelectedIndex();
    int len = urlJList.getModel().getSize();
    upButton.setEnabled(len > 1 && idx > 0);
    downButton.setEnabled(len > 1 && idx < (len - 1));

    sortButton.setEnabled (len > 1);

    buttonEnabler(false);
    testSortOrder();
    checkUrlValuesMatchCase ();
    checkUrlValuesMatchType ();
  }
  
  // http://stackoverflow.com/questions/10786042/java-url-encoding-of-query-string-parameters (second solution)
  // http://blog.lunatech.com/2009/02/03/what-every-web-developer-must-know-about-url-encoding
  // https://en.wikipedia.org/wiki/Percent-encoding
  // http://stackoverflow.com/questions/5330104/encoding-url-query-parameters-in-java
  
  /** 
   * HTTP URL Address Encoding 
   * @param inputUrl address to encode
   * @return corrected result, also escaped for XML
   */
  public static String escapeUrl(String inputUrl)
  {
          String result;
          URL url;
          URI uri;
          try {
              inputUrl = URLChecker.prependFileScheme(inputUrl);
              url = new URL(inputUrl.trim()); // only fully elaborated constructor can break down into parts
              uri = new URI(url.getProtocol(),
                            url.getUserInfo(),   
                            url.getHost(),
                            url.getPort(),
                            url.getPath( ),
                            url.getQuery(),
                            url.getRef());
              result = uri.toASCIIString();
              String UNWANTED_URI_PREFIX = "file:///";
              if (result.startsWith(UNWANTED_URI_PREFIX))
                  result = result.substring(UNWANTED_URI_PREFIX.length());
          }
          catch (MalformedURLException | URISyntaxException e)
          {
//            Exceptions.attachMessage(e, "UrlExpandableList2 escapeUrl()"); // unused
              result = inputUrl; // recover: fail gracefully
          }
          return result.replace("&", "&amp;"); // ensure result is valid XML
  }

  public String[] getUrlData()
  {
    DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();

    ArrayList<String> urlArrayList = new ArrayList<>(mod.getSize());
    for (Enumeration<String> en = mod.elements(); en.hasMoreElements();) 
    {
        String s = en.nextElement().trim();
        if (!s.equalsIgnoreCase(ADD_URL_HINT)) // (s.length() > 0) && 
        {
             urlArrayList.add(escapeUrl(s));
            
        }
//      else urlArrayList.add(""); // do not add empty strings
    }
    return urlArrayList.toArray(new String[0]);
  }

  public String[] getUrlDataUnescaped()
  {
    DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();

    List<String> urlArrayList = new ArrayList<>(mod.getSize());
    for (Enumeration<String> en = mod.elements(); en.hasMoreElements();) 
    {
        String s = en.nextElement().trim();
        if (!s.equalsIgnoreCase(ADD_URL_HINT)) // (s.length() > 0) && 
             urlArrayList.add(s);
//      else urlArrayList.add(""); // do not add empty strings
    }
    return urlArrayList.toArray(new String[0]);
  }

  private String[] getUrlDataIncludingBlanks()
  {
    DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();
    String[] sa = new String[mod.getSize()];
    if(mod.getSize()>0)
      mod.copyInto(sa);
    return sa;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        listScrollPane = new javax.swing.JScrollPane();
        urlJList = new javax.swing.JList<>();
        alignLeftButton = new javax.swing.JButton();
        alignRightButton = new javax.swing.JButton();
        buttonBarPanel = new javax.swing.JPanel();
        editUrlButton = new javax.swing.JButton();
        appendButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        upButton = new javax.swing.JButton();
        downButton = new javax.swing.JButton();
        additionalUrlsButton = new javax.swing.JButton();
        sortButton = new javax.swing.JButton();
        buttonPanelSeparator = new javax.swing.JSeparator();
        loadButton = new javax.swing.JButton();
        launchButton = new javax.swing.JButton();
        externalEditorButton = new javax.swing.JButton();
        qaButton = new javax.swing.JButton();
        domainButton = new javax.swing.JButton();
        pingButton = new javax.swing.JButton();
        whoisButton = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(640, 120));
        setPreferredSize(new java.awt.Dimension(640, 170));
        setLayout(new java.awt.GridBagLayout());

        listScrollPane.setPreferredSize(new java.awt.Dimension(100, 100));

        urlJList.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        urlJList.setModel(new DefaultListModel<>());
        urlJList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        urlJList.setToolTipText(NbBundle.getMessage(getClass(), "LBL_URL_LIST_2_TOOLTIP")); // NOI18N
        urlJList.setSelectionBackground(new java.awt.Color(204, 255, 255));
        listScrollPane.setViewportView(urlJList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(listScrollPane, gridBagConstraints);

        alignLeftButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.alignLeftButton.text")); // NOI18N
        alignLeftButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.alignLeftButton.toolTipText")); // NOI18N
        alignLeftButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        alignLeftButton.setMinimumSize(new java.awt.Dimension(25, 25));
        alignLeftButton.setPreferredSize(new java.awt.Dimension(25, 25));
        alignLeftButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            alignLeftButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(alignLeftButton, gridBagConstraints);

        alignRightButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.alignRightButton.text")); // NOI18N
        alignRightButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.alignRightButton.toolTipText")); // NOI18N
        alignRightButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        alignRightButton.setMinimumSize(new java.awt.Dimension(25, 25));
        alignRightButton.setPreferredSize(new java.awt.Dimension(25, 25));
        alignRightButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            alignRightButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(alignRightButton, gridBagConstraints);

        buttonBarPanel.setLayout(new java.awt.GridBagLayout());

        editUrlButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.editUrlButton.text")); // NOI18N
        editUrlButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2_Edit_Button_tt")); // NOI18N
        editUrlButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            editUrlButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(editUrlButton, gridBagConstraints);

        appendButton.setText(org.openide.util.NbBundle.getMessage(UrlExpandableList2.class, "UrlExpandableList2.appendButton.text")); // NOI18N
        appendButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2_Plus_Button.txt")); // NOI18N
        appendButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            appendButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(appendButton, gridBagConstraints);

        removeButton.setText(org.openide.util.NbBundle.getMessage(UrlExpandableList2.class, "UrlExpandableList2.removeButton.text")); // NOI18N
        removeButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2_Minus_Button.txt")); // NOI18N
        removeButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            removeButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(removeButton, gridBagConstraints);

        upButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/upArrow.png"))); // NOI18N
        upButton.setText(NbBundle.getMessage(getClass(), "ExpandableList.jButton1.text")); // NOI18N
        upButton.setToolTipText(NbBundle.getMessage(getClass(), "URLEXPLIST_UP_BUTT_TT")); // NOI18N
        upButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        upButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            upButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(upButton, gridBagConstraints);

        downButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/downArrow.png"))); // NOI18N
        downButton.setText(NbBundle.getMessage(getClass(), "ExpandableList.jButton2.text")); // NOI18N
        downButton.setToolTipText(NbBundle.getMessage(getClass(), "URLEXPLIST_DOWN_BUTT_TT")); // NOI18N
        downButton.setMargin(new java.awt.Insets(0, 1, 0, 1));
        downButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            downButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(downButton, gridBagConstraints);

        additionalUrlsButton.setText(org.openide.util.NbBundle.getMessage(UrlExpandableList2.class, "UrlExpandableList2.additionalUrlsButton.text")); // NOI18N
        additionalUrlsButton.setToolTipText(org.openide.util.NbBundle.getMessage(UrlExpandableList2.class, "UrlExpandableList2.additionalUrlsButton.toolTipText")); // NOI18N
        additionalUrlsButton.setEnabled(false);
        additionalUrlsButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            additionalUrlsButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(additionalUrlsButton, gridBagConstraints);

        sortButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.sortButton.text")); // NOI18N
        sortButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.sortButton.toolTipText")); // NOI18N
        sortButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            sortButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(sortButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 6, 3);
        buttonBarPanel.add(buttonPanelSeparator, gridBagConstraints);

        loadButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.loadButton.text")); // NOI18N
        loadButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2OpenButton_tt")); // NOI18N
        loadButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            loadButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(loadButton, gridBagConstraints);

        launchButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.launchButton.text")); // NOI18N
        launchButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2_Launch_Button_tt")); // NOI18N
        launchButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            launchButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(launchButton, gridBagConstraints);

        externalEditorButton.setText(org.openide.util.NbBundle.getMessage(UrlExpandableList2.class, "UrlExpandableList2.externalEditorButton.text")); // NOI18N
        externalEditorButton.setToolTipText(org.openide.util.NbBundle.getMessage(UrlExpandableList2.class, "UrlExpandableList2.externalEditorButton.toolTipText")); // NOI18N
        externalEditorButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            externalEditorButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(externalEditorButton, gridBagConstraints);

        qaButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.qaButton.text")); // NOI18N
        qaButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.qaButton.toolTipText")); // NOI18N
        qaButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            qaButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(qaButton, gridBagConstraints);

        domainButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.domainButton.text")); // NOI18N
        domainButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.domainButton.toolTipText")); // NOI18N
        domainButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            domainButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(domainButton, gridBagConstraints);

        pingButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.pingButton.text")); // NOI18N
        pingButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.pingButton.toolTipText")); // NOI18N
        pingButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            pingButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        buttonBarPanel.add(pingButton, gridBagConstraints);

        whoisButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.whoisButton.text")); // NOI18N
        whoisButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.whoisButton.toolTipText")); // NOI18N
        whoisButton.setEnabled(false);
        whoisButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            whoisButtonActionPerformed(evt);
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        buttonBarPanel.add(whoisButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(buttonBarPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private void appendButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_appendButtonActionPerformed
  {//GEN-HEADEREND:event_appendButtonActionPerformed
      if (urlJList.getSelectedIndex() == -1)
          urlJList.setSelectedIndex(urlJList.getModel().getSize() - 1); // last
      appendUrl (ADD_URL_HINT); // insert suggestion String for editing
}//GEN-LAST:event_appendButtonActionPerformed
  private void appendUrl (String newUrl)
  {
    if (isInitialRowHint())
        return; // unselected row:  no action to perform
    
    if ((newUrl != null) && !newUrl.isEmpty())
         newUrlString = newUrl; // only override previously computed value if specifically invoked that way
    int insertRowNum = insertRowFilter.insertRowNumber(urlJList.getSelectedIndex());
    if (isInitialRowHint())
    {
        removeInitialRowHint();
        insertRowNum = 0;
    }
        
    if (insertRowNum == -1)
    {
        insertRowNum = urlJList.getModel().getSize() - 1; // unselected row:  append to end
    }

    DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();
    mod.insertElementAt(newUrlString, insertRowNum);
    urlJList.setModel(mod); // not clear why necessary but throws exception otherwise
    urlJList.clearSelection();
    urlJList.setSelectedIndex(insertRowNum);  // keep it selected
    urlJList.ensureIndexIsVisible(insertRowNum);
    
    boolean editDialogReturnValue = showEditUrlDialog();
    if (editDialogReturnValue == URL_EDIT_CANCELLED)
    {
         ((DefaultListModel) urlJList.getModel()).remove(insertRowNum); // remove just-inserted row since user cancelled operation
         buttonEnabler(false);  // if nothing new, no url status
    }
    else // hit status
    {
        buttonEnabler(true);
        checkUrlValuesMatchType ();
    }
    checkAddUrlsButtonEnabled ();
    testSortOrder();
    urlJList.repaint();
  }
  // If double clicking on list entry, bring up url editor
  MouseListener mouseListener = new MouseAdapter()
  {
    @Override
    public void mouseClicked(MouseEvent e)
    {
      if (e.getClickCount() == 2) {
        boolean editDialogReturnValue = showEditUrlDialog();
        if (editDialogReturnValue == URL_EDIT_VALID)
        {
          buttonEnabler(true);  // check url status
          notifiedMismatchedCase = false;
          checkUrlValuesMatchCase ();
          testSortOrder();
        }
      }
    }
  };

  private void removeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_removeButtonActionPerformed
  {//GEN-HEADEREND:event_removeButtonActionPerformed
    int deleteRowNum = removeRowFilter.removeRowNumber(urlJList.getSelectedIndex());
    if (deleteRowNum != -1)
      ((DefaultListModel) urlJList.getModel()).remove(deleteRowNum);
    if (urlJList.getModel().getSize() > 0)
    {
        urlJList.setSelectedIndex(deleteRowNum);  // keep it selected
        urlJList.ensureIndexIsVisible(deleteRowNum);
    }
    else addInitialRowHint();
    
    buttonEnabler(false);  // don't check status
    checkAddUrlsButtonEnabled ();
    testSortOrder();
    urlJList.repaint();
}//GEN-LAST:event_removeButtonActionPerformed

private void upButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtonActionPerformed
    int idx = urlJList.getSelectedIndex();
    if (idx == -1 || idx == 0)
        return;
    DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();
    String s = mod.remove(idx);
    mod.add(idx - 1, s);
    urlJList.setSelectedIndex(idx - 1);  // keep it selected
    urlJList.ensureIndexIsVisible(idx - 1);
    urlJList.repaint();
}//GEN-LAST:event_upButtonActionPerformed

private void downButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtonActionPerformed
    int idx = urlJList.getSelectedIndex();
    DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();
    if (idx == -1 || idx >= (mod.getSize() - 1))
      return;
    String s = mod.remove(idx);
    mod.add(idx + 1, s);
    urlJList.setSelectedIndex(idx + 1);  // keep it selected
    urlJList.ensureIndexIsVisible(idx + 1);
    urlJList.repaint();
}//GEN-LAST:event_downButtonActionPerformed

private void editUrlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUrlButtonActionPerformed
  addInitialRowHint();  // no value might be available
  if (urlJList.getSelectedIndex() == -1)
      urlJList.setSelectedIndex(0);
  boolean editDialogReturnValue = showEditUrlDialog(); // edit value
  if (editDialogReturnValue == URL_EDIT_VALID)
  {
    addInitialRowHint();  // blank value might have been entered
    buttonEnabler(true);  // check url status
    notifiedMismatchedCase = false;
    checkUrlValuesMatchCase ();
    checkUrlValuesMatchType ();
    testSortOrder();
    urlJList.repaint();
  }
}//GEN-LAST:event_editUrlButtonActionPerformed

private void launchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_launchButtonActionPerformed
  launchInBrowser(urlJList.getSelectedValue());
}//GEN-LAST:event_launchButtonActionPerformed

private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
  openInX3dEdit(urlJList.getSelectedValue());
}//GEN-LAST:event_loadButtonActionPerformed

private void alignLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignLeftButtonActionPerformed
  if (alignLeft != true) {
    alignLeft = true;
    alignButtCommon(true);
  }
}//GEN-LAST:event_alignLeftButtonActionPerformed

    // urlRank enumerations
    private final int          URN     =  0;
    private final int    LOCAL_X3D     =  1;
    private final int RELATIVE_X3D     =  2;
    private final int   REMOTE_X3D     =  3;
    private final int    LOCAL_X3DV    =  4;
    private final int RELATIVE_X3DV    =  5;
    private final int   REMOTE_X3DV    =  6;
    private final int    LOCAL_X3DB    =  7;
    private final int RELATIVE_X3DB    =  8;
    private final int   REMOTE_X3DB    =  9;
    private final int    LOCAL_WRL     = 10;
    private final int RELATIVE_WRL     = 11;
    private final int   REMOTE_WRL     = 12;
    private final int    LOCAL_IMAGE   = 13; // don't sort further (e.g. .png preferred to .gif) since author might sort by image quality
    private final int RELATIVE_IMAGE   = 14;
    private final int   REMOTE_IMAGE   = 15;
    private final int    LOCAL_AUDIO   = 16;
    private final int RELATIVE_AUDIO   = 17;
    private final int   REMOTE_AUDIO   = 18;
    private final int    LOCAL_MOVIE   = 19;
    private final int RELATIVE_MOVIE   = 20;
    private final int   REMOTE_MOVIE   = 21;
    private final int    LOCAL_SCRIPT  = 22;
    private final int RELATIVE_SCRIPT  = 23;
    private final int   REMOTE_SCRIPT  = 24;
    private final int    LOCAL_UNKNOWN = 25;
    private final int RELATIVE_UNKNOWN = 26;
    private final int   REMOTE_UNKNOWN = 27;
    private final int LOCAL_FILE_COLON = 28;

private int urlRank (String urlInput)
{
    String url = urlInput.toLowerCase();

    sortButton.setForeground(defaultForegroundColor); // reset prior to return
    if      (url.startsWith("file:/"))
            return LOCAL_FILE_COLON;
    else if      (url.startsWith("urn:"))
            return URN;
    else if ((url.endsWith(".x3d") || url.contains(".x3d#"))
                                   && (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://")))
            return REMOTE_X3D;
    else if ((url.endsWith(".x3d") || url.contains(".x3d#"))
                                   && url.contains("../"))
            return RELATIVE_X3D;
    else if  (url.endsWith(".x3d") || url.contains(".x3d#"))
            return LOCAL_X3D;
    else if ((url.endsWith(".x3dv") || url.contains(".x3dv#"))
                                   && (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://")))
            return REMOTE_X3DV;
    else if ((url.endsWith(".x3dv") || url.contains(".x3dv#"))
                                   && url.contains("../"))
            return RELATIVE_X3DV;
    else if  (url.endsWith(".x3dv") || url.contains(".x3dv#"))
            return LOCAL_X3DV;
    else if ((url.endsWith(".x3db") || url.contains(".x3db#"))
                                   && (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://")))
            return REMOTE_X3DB;
    else if ((url.endsWith(".x3db") || url.contains(".x3db#"))
                                   && url.contains("../"))
            return RELATIVE_X3DB;
    else if  (url.endsWith(".x3db") || url.contains(".x3db#"))
            return LOCAL_X3DB;
    else if ((url.endsWith(".wrl") || url.endsWith(".wrz") || url.endsWith(".wrl.gz") || url.contains(".wrl#"))
                                   && (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://")))
            return REMOTE_WRL;
    else if ((url.endsWith(".wrl") || url.endsWith(".wrz") || url.endsWith(".wrl.gz") || url.contains(".wrl#"))
                                   && url.contains("../"))
            return RELATIVE_WRL;
    else if  (url.endsWith(".wrl") || url.endsWith(".wrz") || url.endsWith(".wrl.gz") || url.contains(".wrl#"))
            return LOCAL_WRL;
    else if ((url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".tif") || url.endsWith(".tiff") || url.endsWith(".rgb") || url.endsWith(".rgba"))
                                   && (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://")))
            return REMOTE_IMAGE;
    else if ((url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".tif") || url.endsWith(".tiff") || url.endsWith(".rgb") || url.endsWith(".rgba"))
                                   && url.contains("../"))
            return RELATIVE_IMAGE;
    else if  (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".gif") || url.endsWith(".tif") || url.endsWith(".tiff") || url.endsWith(".rgb") || url.endsWith(".rgba"))
            return LOCAL_IMAGE;
    else if ((url.endsWith(".wav") || url.endsWith(".au") || url.endsWith(".aiff") || url.endsWith(".mp4"))
                                   && (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://")))
            return REMOTE_AUDIO;
    else if ((url.endsWith(".wav") || url.endsWith(".au") || url.endsWith(".aiff") || url.endsWith(".mp4"))
                                   && url.contains("../"))
            return RELATIVE_AUDIO;
    else if  (url.endsWith(".wav") || url.endsWith(".au") || url.endsWith(".aiff") || url.endsWith(".mp4"))
            return LOCAL_AUDIO;
    else if ((url.endsWith(".mpg") || url.endsWith(".mpeg") || url.endsWith(".mov") || url.endsWith(".qt") || url.endsWith(".wmv"))
                                   && (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://")))
            return REMOTE_MOVIE;
    else if ((url.endsWith(".mpg") || url.endsWith(".mpeg") || url.endsWith(".mov") || url.endsWith(".qt") || url.endsWith(".wmv"))
                                   && url.contains("../"))
            return RELATIVE_MOVIE;
    else if  (url.endsWith(".mpg") || url.endsWith(".mpeg") || url.endsWith(".mov") || url.endsWith(".qt") || url.endsWith(".wmv"))
            return LOCAL_MOVIE;
    else if ((url.endsWith(".js") || url.endsWith(".class") || url.endsWith(".jar"))
                                   && (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://")))
            return REMOTE_SCRIPT;
    else if ((url.endsWith(".js") || url.endsWith(".class") || url.endsWith(".jar"))
                                   && url.contains("../"))
            return RELATIVE_SCRIPT;
    else if  (url.endsWith(".js") || url.endsWith(".class") || url.endsWith(".jar"))
            return LOCAL_SCRIPT;
    else if  (url.startsWith("http://") || url.startsWith("https://") || url.contains("ftp://"))
            return REMOTE_UNKNOWN;
    else if (url.contains("../"))
            return RELATIVE_UNKNOWN;
    else    return LOCAL_UNKNOWN;
}

private void sortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortButtonActionPerformed

  urlListModel = (DefaultListModel<String>) urlJList.getModel();

  int listSize = urlJList.getModel().getSize();
  if (listSize <= 1) return;
  int selectedIndex = urlJList.getSelectedIndex();
  boolean notifiedSame    = false;
  boolean notifiedSimilar = false;

  for (int i = 0; i < listSize; i++)
  {
      for (int j = i+1; j < listSize; j++)
      {
          String address1 = urlListModel.elementAt(i);
          String address2 = urlListModel.elementAt(j);
          if (urlRank(address1) > urlRank(address2))
          {
              // swap
              urlListModel.setElementAt(address2, i);
              urlListModel.setElementAt(address1, j);
              if (selectedIndex == i) selectedIndex = j; // keep track of selection
          }
		  // put .gif last among image formats
		  else if (((urlRank(address1) == LOCAL_IMAGE)  && (urlRank(address2) == LOCAL_IMAGE))  || 
				   ((urlRank(address1) == REMOTE_IMAGE) && (urlRank(address2) == REMOTE_IMAGE)) && (address1.endsWith(".gif")))
          {
              // swap
              urlListModel.setElementAt(address2, i);
              urlListModel.setElementAt(address1, j);
              if (selectedIndex == i) selectedIndex = j; // keep track of selection
          }
          else if (address1.equals(address2) && !notifiedSame)
          {
              NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "<html><p align='center'>Found duplicate url[" + i + "] and url[" + j + "]</p><br/><p align='center'>" + address1 + "</p>", 
					NotifyDescriptor.WARNING_MESSAGE);
              DialogDisplayer.getDefault().notify(descriptor);
              notifiedSame    = true;
          }
          else if (address1.equalsIgnoreCase(address2) && !notifiedSimilar)
          {
              NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "<html><p align='center'>Found similar url[" + i + "] and url[" + j + "] with different upper/lower case values</p><br/><p align='center'>" + address1 + "</p><p align='center'>" + address2 + "</p>",
					NotifyDescriptor.WARNING_MESSAGE);
              DialogDisplayer.getDefault().notify(descriptor);
              notifiedSimilar = true;
          }
      }
  }
  urlJList.setSelectedIndex(selectedIndex);  // keep it selected
  urlJList.ensureIndexIsVisible(selectedIndex);
  urlJList.repaint();
}//GEN-LAST:event_sortButtonActionPerformed

    private void additionalUrlsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_additionalUrlsButtonActionPerformed
    {//GEN-HEADEREND:event_additionalUrlsButtonActionPerformed
        // add urls according to file type

        int selectedIndex = urlJList.getSelectedIndex();
        if (selectedIndex == -1) selectedIndex = 0; // must be at least one, even if not selected, otherwise button not enabled
        DefaultListModel<String> listModel = (DefaultListModel<String>) urlJList.getModel();
        String filePathName = listModel.getElementAt(selectedIndex);
        String appendUrlString;

        // simple X3D filename, no online
        if ( (urlCustomizerPanel.getFormatChoice().equalsIgnoreCase("X3D")) &&
            !(filePathName.startsWith("http://") || filePathName.startsWith("https://") || filePathName.startsWith("ftp://") || filePathName.startsWith("sftp://")))
        {
            // append new url values in sort order to save an additional sorting step
            appendUrlString = getHeaderIdentifierPath() + filePathName;
            listModel.insertElementAt(appendUrlString, selectedIndex + 1);
            if      (filePathName.contains(".x3d")) appendUrlString = filePathName.replace(".x3d",".wrl");
            else if (filePathName.contains(".wrl")) appendUrlString = filePathName.replace(".wrl",".x3d");
            listModel.insertElementAt(appendUrlString, selectedIndex + 2);
            appendUrlString = getHeaderIdentifierPath() + appendUrlString;
            listModel.insertElementAt(appendUrlString, selectedIndex + 3);
        }
        // full path plus X3D filename:  strip path
        else if ( (urlCustomizerPanel.getFormatChoice().equalsIgnoreCase("X3D")) &&
             (filePathName.startsWith("http://") || filePathName.startsWith("https://") || filePathName.startsWith("ftp://") || filePathName.startsWith("sftp://")))
        {
            filePathName = filePathName.substring(filePathName.lastIndexOf('/')+1, filePathName.length());
            listModel.setElementAt(filePathName, 0);
            // append new url values in sort order to save an additional sorting step
            appendUrlString = getHeaderIdentifierPath() + filePathName;
            listModel.insertElementAt(appendUrlString, selectedIndex + 1);
            if      (filePathName.contains(".x3d")) appendUrlString = filePathName.replace(".x3d",".wrl");
            else if (filePathName.contains(".wrl")) appendUrlString = filePathName.replace(".wrl",".x3d");
            listModel.insertElementAt(appendUrlString, selectedIndex + 2);
            appendUrlString = getHeaderIdentifierPath() + appendUrlString;
            listModel.insertElementAt(appendUrlString, selectedIndex + 3);
        }
        // full path plus other filename
        else if (filePathName.startsWith("http://") || filePathName.startsWith("https://") || filePathName.startsWith("ftp://") || filePathName.startsWith("sftp://"))
        {
            filePathName = filePathName.substring(filePathName.lastIndexOf('/')+1, filePathName.length());
            listModel.setElementAt(filePathName, 0);
            appendUrlString = getHeaderIdentifierPath() + filePathName;
            listModel.insertElementAt(appendUrlString, selectedIndex + 1);
        }
        // simple other filename
        else
        {
            appendUrlString = getHeaderIdentifierPath() + listModel.getElementAt(selectedIndex);
            listModel.insertElementAt(appendUrlString, selectedIndex + 1);
        }

        urlJList.clearSelection();
        urlJList.ensureIndexIsVisible(selectedIndex);

        checkAddUrlsButtonEnabled ();
        buttonEnabler(true);
        testSortOrder();
        urlJList.repaint();
    }//GEN-LAST:event_additionalUrlsButtonActionPerformed

    /** Extract host name for a given url
    * @param url address for resource
    * @return host name (or number) */
    protected String getHost (String url)
    {
        if (url == null) 
            url = "";
        String host = url.trim();
        if (host.contains("://"))
        {
            host = host.substring(url.indexOf("://") + 3);
            if (host.contains("/")) 
                host = host.substring(0, host.indexOf("/"));
        }
        else if (url.contains("mailto:")) 
        {
            host = host.substring(url.indexOf("mailto:") + 7);
            if (host.contains("/")) 
                host = host.substring(0, host.indexOf("/"));
        }
        else host = "";
        
        return host;
    }
    /** Report whether the file type of the input fileUrl has a QA test
     * @param fileUrl from list being inspected
     * @return whether QA test is available */
    protected boolean hasQaTest (String fileUrl)
    {
        if (fileUrl == null)
            return false;
        String url = fileUrl.trim();
        
        String extension = new String();
        if (url.contains("."))
                url = url.substring(url.lastIndexOf("."));
        if (url.isEmpty())
             return false;
        
      return isHtmlExtension(extension) || 
              isImageSupportedExtension(extension) ||
              isImageAlternateExtension(extension) ||
              (url.endsWith(".css")) ||
              (url.endsWith(".js"))  ||
              (url.endsWith(".x3d")) ||
              (url.endsWith(".xml"));
    }
    /** Report whether url is online, otherwise local
     * @param fileUrl from list being inspected
     * @return true if online address, false if relative address */
    protected boolean isOnlineUrl (String fileUrl)
    {
        if (fileUrl == null)
            return false;
        String url = fileUrl.trim();
        // also skip preceding text as appropriate (for example, descriptions preceding urls for name=generator meta tags)
        if      (url.contains("http://"))
                 url = url.substring(url.indexOf("http://"));
        else if (url.contains("https://"))
                 url = url.substring(url.indexOf("https://"));
        else if (url.contains("ftp://"))
                 url = url.substring(url.indexOf("ftp://"));
        else if (url.contains("sftp://"))
                 url = url.substring(url.indexOf("sftp://"));
        return (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://") || url.startsWith("sftp://"));
    }
    /** Access appropriate online web page to report whether file at this url is valid
     * @param address of file resource to be validated
     * @return whether validation passes */
    protected boolean validateUrlContentViaOnlineServer (String address)
    {                    
        String url = address.trim();
        String extension = address.substring(url.lastIndexOf("."));
        // also skip preceding text as appropriate (for example, descriptions preceding urls for name=generator meta tags)
        if      (url.contains("http://"))
                 url = url.substring(url.indexOf("http://"));
        else if (url.contains("https://"))
                 url = url.substring(url.indexOf("https://"));
        else if (url.contains("ftp://"))
                 url = url.substring(url.indexOf("ftp://"));
        else if (url.contains("sftp://"))
                 url = url.substring(url.indexOf("sftp://"));
        if (url.length() <= 4)
            return false; // no action taken
        
        NotifyDescriptor descriptor = new NotifyDescriptor.Message(
              "<html><p align='center'>Once connected to validator website, use chooser to select your local file.</p><br /><p align='center'>(Browsers require user selection of files as a security precaution.)", NotifyDescriptor.INFORMATION_MESSAGE);

         // ============================================================================================================
         if       (url.endsWith(".x3d"))
         {
             if (isOnlineUrl(url))
                 launchInBrowser("https://savage.nps.edu/X3dValidator?url="  + url);
             else
             {
                // direct links to local disk not feasible, this is a common browser security precaution
                 DialogDisplayer.getDefault().notify(descriptor);
                 launchInBrowser("https://savage.nps.edu/X3dValidator");
             } // ?path=" + url);
         }
         // ============================================================================================================
         else if  (url.endsWith(".html") || url.endsWith(".htm") || url.endsWith(".xhtml")) // includes embedded MathML
         {
             if (isOnlineUrl(url))
             {
                 launchInBrowser("http://validator.w3.org/unicorn/?ucn_uri=" + url); // #validate-by-uri
                 launchInBrowser("http://validator.w3.org/nu/?doc=" + url);
             }
             else 
             {
                 DialogDisplayer.getDefault().notify(descriptor);
                 launchInBrowser("http://validator.w3.org/unicorn/#validate-by-upload+task_conformance"); // ?ucn_uri=" + url);
                 // TODO consider embedding nu.jar for local service
                 // TODO improve invocation if possible.
                 launchInBrowser("http://validator.w3.org/nu/?doc=" + url);
             }
         }
         // ============================================================================================================
         else if  (url.endsWith(".svg") || url.endsWith(".xml")) // includes MathML which uses .xml file extension
         {
             if (isOnlineUrl(url))
                 launchInBrowser("http://validator.w3.org/unicorn/?ucn_uri=" + url); // #validate-by-uri
             else 
             {
                 DialogDisplayer.getDefault().notify(descriptor);
                 launchInBrowser("http://validator.w3.org/unicorn/#validate-by-upload+task_conformance"); // ?ucn_uri=" + url);
             }
         }
         // ============================================================================================================
         else if  (url.endsWith(".mml")) // MathML
         {
             if (isOnlineUrl(url))
                 launchInBrowser("http://validator.w3.org/#validate_by_uri=" + url); // #validate-by-uri
             else 
             {
                 DialogDisplayer.getDefault().notify(descriptor);
                 launchInBrowser("http://validator.w3.org/#validate_by_upload"); // ?ucn_uri=" + url);
             }
         }
         // ============================================================================================================
         else if  (url.endsWith(".css")) // should also work in Unicorn
         {
             if (isOnlineUrl(url))
                 launchInBrowser("http://jigsaw.w3.org/css-validator/?uri="    + url); //#validate_by_uri
             else 
             {
                 DialogDisplayer.getDefault().notify(descriptor);
                 launchInBrowser("http://jigsaw.w3.org/css-validator/#validate_by_upload"); // ?uri=" + url);
             }
         }
         // ============================================================================================================
         else if  (url.endsWith(".js") || url.endsWith(".json") || url.endsWith(".x3dj")) // TODO confirm
         {
             // requires cut and paste, TODO automate
             descriptor = new NotifyDescriptor.Message(
              "<html><p align='center'>Once connected to validator website, enter url address or use chooser to select your file.</p><p align='center'>(Browsers require user selection of files as a security precaution.)", NotifyDescriptor.INFORMATION_MESSAGE);

            DialogDisplayer.getDefault().notify(descriptor);
            launchInBrowser("http://www.jslint.com");
         }
         // ============================================================================================================
         else if  (isImageSupportedExtension(extension) || isImageAlternateExtension(extension))
         {
            if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://") || url.startsWith("sftp://"))
            {
                launchInBrowser("http://www.w3.org/services/imageinfo?uri=" + url);
            }
            else 
            {
                descriptor = new NotifyDescriptor.Message(
                        "<html><p align='center'>W3C Image Info service only works with online url addresses.</p>");
                DialogDisplayer.getDefault().notify(descriptor);
                launchInBrowser("http://www.w3.org/services/imageinfo");
                return false; // no action taken
            }
         }
         // ============================================================================================================
         else 
         {
            descriptor = new NotifyDescriptor.Message(
                    "<html><p align='center'>No external Quality Assurance (QA) found for (" + extension + ") files.</p><p>&nbsp;</p><p align='center'>" + url + "</p>");
            DialogDisplayer.getDefault().notify(descriptor);
            return false; // no action taken
         }
         // ============================================================================================================
         
         // TODO audio and video formats, also YouTube and other video links checked
        
         // TODO pdf
        
         // TODO mailto url validation using regex http://www.regular-expressions.info/index.html
         
         // ============================================================================================================
        
         return true; // made it to here and so must have found match
    }
    private void whoisButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whoisButtonActionPerformed
        String host = getHost(urlJList.getSelectedValue());
        if (host.length() > 4)
            launchInBrowser("http://www.networksolutions.com/whois/results.jsp?domain=" + host);
    }//GEN-LAST:event_whoisButtonActionPerformed

    private void pingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pingButtonActionPerformed
        // similarly implemented in UrlExpandableList2 and METACustomizer, keep these code blocks consistent
        String host = getHost(urlJList.getSelectedValue());
        if (host.length() > 4)
            launchInBrowser("http://centralops.net/co/Ping.aspx?addr=" + host);
    }//GEN-LAST:event_pingButtonActionPerformed

    private void domainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_domainButtonActionPerformed
        // similarly implemented in UrlExpandableList2 and METACustomizer, keep these code blocks consistent
        String url = urlJList.getSelectedValue().trim();
        String host = getHost(url);
        if (host.length() > 4)
        {
            if (url.contains("https://") || url.contains("sftp://"))  // check https certificate
            {
               
                launchInBrowser("http://www.digicert.com/help?host=" + host);
            }
            launchInBrowser("http://centralops.net/co/DomainDossier.aspx?addr=" + host +
                            "&dom_whois=true&dom_dns=true&traceroute=true&net_whois=true&svc_scan=true");
        }
    }//GEN-LAST:event_domainButtonActionPerformed

    private void qaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qaButtonActionPerformed
        // similarly implemented in UrlExpandableList2 and METACustomizer, keep these code blocks consistent
        String url = urlJList.getSelectedValue();
        validateUrlContentViaOnlineServer (url);
    }//GEN-LAST:event_qaButtonActionPerformed

    private void alignRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alignRightButtonActionPerformed
        if (alignLeft != false) {
            alignLeft = false;
            alignButtCommon(false);
        }
    }//GEN-LAST:event_alignRightButtonActionPerformed

  private void externalEditorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_externalEditorButtonActionPerformed
    // similarly implemented in UrlExpandableList2 and METACustomizer, keep these code blocks consistent
    String urlString = urlJList.getSelectedValue();
    launchExternalEditor (urlString);
  }//GEN-LAST:event_externalEditorButtonActionPerformed

  protected void launchExternalEditor (String urlString)
  {
    String extension = urlString.substring(urlString.lastIndexOf("."));
    // also skip preceding text as appropriate (for example, descriptions preceding urls for name=generator meta tags)
    if      (urlString.contains("http://"))
             urlString = urlString.substring(urlString.indexOf("http://"));
    else if (urlString.contains("https://"))
             urlString = urlString.substring(urlString.indexOf("https://"));
    else if (urlString.contains("ftp://"))
             urlString = urlString.substring(urlString.indexOf("ftp://"));
    else if (urlString.contains("sftp://"))
             urlString = urlString.substring(urlString.indexOf("sftp://"));
    if (urlString.length() <= 4)
        return; // no action taken
    
    if (urlString.contains("#"))
    {
        urlString = urlString.substring(1,urlString.indexOf("#"));
    }
    
    ProcessBuilder pb;
    
    URL url = buildUrl(urlString);
    String fullPath;
    if (url != null && url.getProtocol().equalsIgnoreCase("file"))
    {
      fullPath = url.getPath();
      if (fullPath.startsWith("/") && fullPath.contains(":"))
        fullPath = fullPath.substring(1); // strip leading slash for paths beginning C: D: etc.
    }
    else
      fullPath = urlString;     // Try this way
    
    // note that external editors do not necessarily support opening of online url addresses.  Gimp handles it OK.

    NotifyDescriptor descriptor;
    boolean toolFound = false;
    try {
      if      (isSoundSupportedExtension(extension) || isSoundAlternateExtension(extension))
      {
        if (X3dOptions.isAudacityAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getAudacityEditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isMuseScoreAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getMuseScoreEditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isOtherAudioEditorAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getOtherAudioEditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
      }
      else if (isHtmlExtension(extension))
      {
        if (X3dOptions.isAmayaAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getAmayaEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isUltraEditAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getUltraEditX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isOtherHtml5EditorAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getOtherHtml5EditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
      }
      else if (isImageSupportedExtension(extension) || isImageAlternateExtension(extension))
      {
        if (X3dOptions.isGimpAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getGimpImageEditorPath(),    fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isFijiAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getFijiImageEditorPath(),    fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isImageJAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getImageJEditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isImageMagickAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getImageMagickEditorPath(),  fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isOtherImageEditorAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getOtherImageEditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
      }
      else if (isMovieSupportedExtension(extension))
      {
        if (X3dOptions.isVlcAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getVlcPlayerPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isOtherVideoEditorAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getOtherVideoEditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
      }
      else if (isVolumeExtension(extension))
      {
        if (X3dOptions.isFijiAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getFijiImageEditorPath(),    fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isImageJAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getImageJEditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isItksnapAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getItksnapEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isSeg3dAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getSeg3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isSlicer3dAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getSlicer3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isOtherVolumeEditorAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getOtherVolumeEditorPath(),       fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
      }
      else if (isX3dSupportedExtension(extension))
      {
        if (X3dOptions.isBlenderAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getBlenderX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isMeshLabAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getMeshLabX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isParaviewAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getParaviewX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isSeamless3dAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getSeamlessX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isUltraEditAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getUltraEditX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isWings3dAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getWingsX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
        if (X3dOptions.isOtherX3dEditorAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getOtherX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
      }
      else if (isHtmlExtension(extension) || isXmlExtension(extension) || isTextExtension(extension)) // can use UltraEdit if available
      {
        if (X3dOptions.isUltraEditAutoLaunch().equalsIgnoreCase("true"))
        {
          toolFound = true;
          pb = new ProcessBuilder(X3dOptions.getUltraEditX3dEditorPath(), fullPath);
          pb.start();
          Thread.sleep(X3dOptions.getLaunchIntervalMilliseconds());
        }
      }
      else // no support for this file type
      {
          descriptor = new NotifyDescriptor.Message(
                  "No external editors found for this file type (" + extension + ")");
          DialogDisplayer.getDefault().notify(descriptor);
          return;
      }
      
      if (!toolFound) // nothing installed or enabled/selected by user
      {
          descriptor = new NotifyDescriptor.Message(
                  "<html><p align='center'>No external editors installed or enabled for (" + extension + ") files.</p><p>&nbsp;</p><p align='center'>See X3D-Edit Preferences, External Tools tab for setup.</p>");
          DialogDisplayer.getDefault().notify(descriptor);
          // TODO dialog to confirm, launch
      }
    }
    catch (IOException | InterruptedException ex) {
      InputOutput io = IOProvider.getDefault().getIO("Output", false);
      io.select();
      io.getOut().println(new StringBuilder().append("Error launching external editor: ").append(ex.getLocalizedMessage()).toString());
    }
  }
  private void alignButtCommon(boolean isLeft)
  {
    redrawList();
    // Horizontal
    JScrollBar hSb = listScrollPane.getHorizontalScrollBar();
    int scrollPos = isLeft ? hSb.getMinimum() : hSb.getMaximum();
    hSb.setValue(scrollPos);
    // Vertical
    int sel = urlJList.getSelectedIndex();
    if (sel >= 0)
      urlJList.ensureIndexIsVisible(sel);
  }

  private void redrawList()
  {
    int sel = urlJList.getSelectedIndex();
    DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();
    String[] oa = new String[mod.getSize()];
    mod.copyInto(oa);
    _setUrlData(oa); // causes redraw
    if(sel != -1)
      urlJList.setSelectedIndex(sel);
    urlJList.repaint();
  }
  
  static protected void launchInBrowser(String urlString)
  {
    URL url = buildUrl(urlString);
    if (url != null)
      ConversionsHelper.openInBrowser(url);
    else
      ConversionsHelper.openInBrowser(urlString);     // Try this way    
  }

  protected void openInX3dEdit(String urlString)
  {
    URL url = buildUrl(urlString);
    if (url != null && url.getProtocol().equalsIgnoreCase("file"))
      ConversionsHelper.openInEditor(url.getPath());
    else
      ConversionsHelper.openInEditor(url.getPath());
//      ConversionsHelper.openInEditor(urlString);     // Try this way      
  }

  protected void openInX3dEdit(String urlString, String fileData)
  {
      String checkedPath = "";
      URL urlOpenInX3dEdit;
      StringBuilder notificationMessage = new StringBuilder();
      NotifyDescriptor descriptor;
  
      if (!fileData.trim().isEmpty()) 
      {
          urlOpenInX3dEdit = buildUrl(urlString);
          if (urlOpenInX3dEdit != null && urlOpenInX3dEdit.getProtocol().equalsIgnoreCase("file"))
              checkedPath = urlOpenInX3dEdit.getPath();
          else
              checkedPath = urlString;     // Try this way 
    
          try
          {
              boolean writeOK = true;
              File existingFile = new File(checkedPath);
              if  (existingFile.exists())
              {
                    writeOK = false;
                    notificationMessage.append("<html><p align='center'>");
                    notificationMessage.append("Overwrite existing script file?</p>");
                    notificationMessage.append("<br />");
                    notificationMessage.append("<p align='center'>");
                    if (checkedPath.startsWith("/"))
                         notificationMessage.append(checkedPath.substring(1));
                    else notificationMessage.append(checkedPath);
                    notificationMessage.append("</p>");
                    descriptor = new NotifyDescriptor.Confirmation(
                          notificationMessage.toString(),
                          "Overwrite existing script file?", NotifyDescriptor.YES_NO_OPTION);
                    if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION) 
                    {
                        writeOK = true;
                    }
              }
              if (writeOK)
              {
                  try
                  (FileWriter outputStream = new FileWriter(checkedPath)) {
                      if      (fileData.trim().startsWith("ecmascript:"))
                           outputStream.write(fileData.substring(fileData.indexOf("ecmascript:") + "ecmascript:".length()));
                      else if (fileData.trim().startsWith("javascript:"))
                           outputStream.write(fileData.substring(fileData.indexOf("javascript:") + "javascript:".length()));
                      else outputStream.write(fileData);
                  }
              }
          }
          catch (IOException ioe)
          {
              System.err.println("openInX3dEdit exception:\n" + ioe);
//              e.printStackTrace(System.err);
          }
      }
      boolean urlStringFoundOnList = false;
      for (int i = 0; i < getUrlData().length; i++)
      {
          if (urlString.equals(getUrlDataUnescaped()[i])) {
              urlStringFoundOnList = true; // don't re-add this filename if it is already there
          }
      }
      if ((urlString.length() > 0) && !urlStringFoundOnList) // only ask if invoked from Script node button
      {
          notificationMessage = new StringBuilder();
          notificationMessage.append("<html><p align='center'>");
          notificationMessage.append("Append filename to url list for this Script node?</p>");
          notificationMessage.append("<br />");
          notificationMessage.append("<p align='center'>");
          notificationMessage.append(urlString);
          notificationMessage.append("</p>");
          descriptor = new NotifyDescriptor.Confirmation(
                  notificationMessage.toString(),
                  "Append filename to url list?", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              appendUrl(urlString); // only append list with passed filename, not full path
          }
      }
      ConversionsHelper.openInEditor(checkedPath); // finally open it (whew!)
  }

  public static URL buildUrl(String s)
  {
    return buildUrl(masterFo,s);
  }

  public static URL buildUrl(FileObject rootDocument, String s)
  {
    try {
      URL u = new URL(s);
      return u;
    }
    catch (MalformedURLException ex) {}

    try {
      if (s.startsWith("/") || s.contains(":")) {
        URL u2 = new URL("file", "", s);
        return u2;
      }
    }
    catch (MalformedURLException ex) {}

    // Must be a relative path
    try {
      if (rootDocument != null) {
        FileObject parentFo = rootDocument.getParent();
        if (parentFo != null) {
          URI parentUri = Utilities.toURI(FileUtil.toFile(parentFo));
          URL parentUrl = parentUri.toURL();
          URL finalUrl = new URL(parentUrl, s);
          return finalUrl;
        }
      }
    }
    catch (MalformedURLException ex) {
       System.err.println ("UrlExpandableList2 internal error: " + ex);
	}

    return null;
  }
    
  /**
   * Externally invocable:  display url edit dialog panel
   * @param urlString contains address of asset to be edited
   * @return chooser result
   */
  protected String showEditDialog(String urlString)
  {
    urlCustomizerPanel.setMasterDocumentLocation(masterFo);
    if (!urlString.isEmpty())
        urlCustomizerPanel.setData(urlString);

    DialogDescriptor descriptor = new DialogDescriptor(urlCustomizerPanel, NbBundle.getMessage(getClass(), "LBL_URL_LIST_2_EDIT_URL_DIALOG"));

    Dialog dlg = null;
    try {
      dlg = DialogDisplayer.getDefault().createDialog(descriptor);
      dlg.setResizable(true);
      dlg.setMinimumSize(new Dimension(700,250));
      //dlg.setMaximumSize(new Dimension(300,200));
      dlg.pack();
      dlg.setVisible(true);
    }
    finally {
      if (dlg != null)
        dlg.dispose();
    }
    if (!descriptor.getValue().equals(DialogDescriptor.CANCEL_OPTION))
    {
      // adjust file chooser if appropriate
      String fileName = urlCustomizerPanel.getData();
	  
      // adjust file chooser/address result if appropriate
	  if  (fileName != null)
	  {
		fileName = urlCustomizerPanel.getData().trim();
		String original = fileName;
		if  (fileName.contains("://"))
			 fileName = fileName.substring(0,fileName.indexOf("://") + 3) + 
						fileName.substring(  fileName.indexOf("://") + 3).replace("//","/");
		else fileName = fileName.replace("//","/");
		// also fix \/ and /\
		fileName = fileName.replace("\\/","/").replace("/\\","/");
		
        if (!original.equals(fileName)) // change occurred
        {
            int ret = JOptionPane.showConfirmDialog(this, 
                        "<html><p align='center'><>Clean up url slashes? </b></p><r />" +
                        "<p align='left'>" + original + " to </p>" +
                        "<p align='left'>" + fileName +    " </p>",
                        "Confirm modification...", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.NO_OPTION)
            {
                fileName = original; // user doesn't want correction
            }
        }
	  }
      if (alternateFormatsExpected && (fileName != null) && fileName.contains("."))
          selectFileChooserFilter(fileName.substring(fileName.lastIndexOf(".")));
      
      return urlCustomizerPanel.getData();
    }
    return "";
  }

  /**
   * Display url edit dialog panel
   * @return true if user accepts change, false if user cancels edit
   */
  private boolean showEditUrlDialog()
  {
    urlCustomizerPanel.setMasterDocumentLocation(masterFo);
    if (isInitialRowHint() || urlJList.getSelectedValue().equalsIgnoreCase(ADD_URL_HINT))
         urlCustomizerPanel.setData("");
    else urlCustomizerPanel.setData(urlJList.getSelectedValue());
    
    int idx = 0;
    if (urlJList.getSelectedIndex() > -1)
    {
        idx = urlJList.getSelectedIndex();
    }

    DialogDescriptor descriptor = new DialogDescriptor(urlCustomizerPanel, NbBundle.getMessage(getClass(), "LBL_URL_LIST_2_EDIT_URL_DIALOG"));

    Dialog dialog = null;
    try {
      dialog = DialogDisplayer.getDefault().createDialog(descriptor);
      dialog.setResizable(true);
      dialog.setMinimumSize(new Dimension(700,250));
      //dlg.setMaximumSize(new Dimension(300,200));
      dialog.pack();
      dialog.setVisible(true);
    }
    finally {
      if (dialog != null)
        dialog.dispose();
    }
    if (!descriptor.getValue().equals(DialogDescriptor.CANCEL_OPTION))
    {
      DefaultListModel<String> mod = (DefaultListModel<String>) urlJList.getModel();
      mod.setElementAt(urlCustomizerPanel.getData(), idx);
      // adjust file chooser if appropriate
      String fileName = urlCustomizerPanel.getData();
      
      if (fileName.contains("http://"))
      {              
          NotifyDescriptor descriptorHttpAlert = new NotifyDescriptor.Confirmation(
                "<html><p align='center'>url address is insecure, http:// instead of https:// - fix it?</p><br/><p align='center'>" + fileName + "</p>", 
              "Insecure http address found",
				NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptorHttpAlert)== NotifyDescriptor.YES_OPTION)
          {
              fileName = fileName.replace("http://", "https://");
          }
      }
      if (alternateFormatsExpected & fileName.contains("."))
          selectFileChooserFilter(fileName.substring(fileName.lastIndexOf(".")));
      addInitialRowHint();
      return URL_EDIT_VALID;
    }
    addInitialRowHint();
    return URL_EDIT_CANCELLED;
  }
  
  UrlStatus.UrlStatusListener myUrlStatusListener = new UrlStatus.UrlStatusListener()
  {
    @Override
    public void statusIn(Object id, int status)
    {
      StatusPack sp = statusPends.get(id);
      if (sp == null) return;
      sp.status = status;
      hitUrlStatus();
    }
  };

  private       HashMap<Object,StatusPack> statusPends = new HashMap<>();
  private final HashMap<String,StatusPack> urlToStatus = new HashMap<>();

    /**
     * Capture invoking node as a hook back to scene jdom model
     * @param target target is passed from implementing node containing this UrlExpandableList2 component
     */
    public void setTarget(JTextComponent target)
    {
        this.target = target;

        headerIdentifierPath = getHeaderIdentifierPath();
        checkAddUrlsButtonEnabled ();
    }
    /**
     * Set addUrlsButton enabled as appropriate
     */
    private void checkAddUrlsButtonEnabled ()
    {
        if   (  headerIdentifierPath.isEmpty() ||
              ( urlJList.getModel().getSize() == 0) ||
              ((urlJList.getModel().getSize()  > 1) && (urlJList.getSelectedIndex() == -1)) ||
               (isInitialRowHint()))
        {
            additionalUrlsButton.setEnabled(false);
        }
        else
        {
            additionalUrlsButton.setEnabled(true);
        }
    }

    /**
     * @param alternateFormatsExpected the alternateFormatsExpected to set
     */
    public void setAlternateFormatsExpected(boolean alternateFormatsExpected) {
        this.alternateFormatsExpected = alternateFormatsExpected;
    }

  class StatusPack
  {
    Object statusID;
    int status = UrlStatus.INDETERMINATE;
    String url;
    StatusPack(String url)
    { 
        this.url = url; 
    }
  }
  
  private void hitUrlStatus()
  {
      String[] urls = getUrlDataUnescaped();
    
      if (masterFo == null)
      {
          System.err.println ("UrlExpandableList2 internal error, masterFileObject not set");
          if (urls.length > 0)
               System.err.println (" for " + urls[0]);
          else System.err.println ();
      }

    for (int i = 0; i < urls.length; i++)
    {
      String url = urls[i];
      StatusPack statusPack;
      
      try 
      {
          statusPack = urlToStatus.get(url);
          if (statusPack != null)
            markUrl(i, statusPack.status);
          else
          {
            if (url.length() > 0)
            {  // must be something there
              statusPack = new StatusPack(url);
              urlToStatus.put(url, statusPack);
              if (masterFo != null)
              {
                  Object statusID = UrlStatus.checkStatus(url, FileUtil.toFile(masterFo.getParent()), myUrlStatusListener);
                  statusPends.put(statusID, statusPack);
              }
            }
          }
      }
      catch (Exception e)
      {
                System.err.println ("UrlExpandableList2 internal error: " + e);
//              sp = new StatusPack(url);
//              urlToStatus.put(url, sp);
//              statusPends.put(UrlStatus.INDETERMINATE, sp);
      }
    }
  }
  private boolean notifiedMismatchedCase = false;
  private void checkUrlValuesMatchCase ()
  {
      String[] urls = getUrlData();
      if (urls.length < 2) return;
      if (notifiedMismatchedCase) return;
      for (int i = 0; i < urls.length-1; i++)
      {
          String url1 = urls[i];
          for (int j = i + 1; j < urls.length; j++)
          {
              String url2 = urls[j];
              if ((url2.toLowerCase ().contains (url1.toLowerCase ()) && !url2.contains (url1)) ||
                  (url1.toLowerCase ().contains (url2.toLowerCase ()) && !url1.contains (url2)))
              {
                  NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                        "<html><p align='center'>Found mismatched capitalization in url[" + i + "] and url[" + j + "]</p><br/><p align='center'>File and directory name capitalization must match exactly in related urls!</p>", NotifyDescriptor.ERROR_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
                  notifiedMismatchedCase = true; // less insistent
                  return;
              }
          }
      }
  }
  protected boolean isX3dSupportedExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".x3d")    || extension.equalsIgnoreCase(".x3dv")    ||
              extension.equalsIgnoreCase(".x3d.gz") || extension.equalsIgnoreCase(".x3dv.gz") ||
              extension.equalsIgnoreCase(".x3db")   || extension.equalsIgnoreCase(".wrl")     ||
              extension.equalsIgnoreCase(".wrz")    || extension.equalsIgnoreCase(".wrl.gz")     );
  }
  protected boolean isImageSupportedExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".png")    || extension.equalsIgnoreCase(".jpg")     ||
              extension.equalsIgnoreCase(".jpeg")   || extension.equalsIgnoreCase(".gif")        );
  }
  protected boolean isImageAlternateExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".bmp")    || extension.equalsIgnoreCase(".pbm")    ||
              extension.equalsIgnoreCase(".xbm")    || extension.equalsIgnoreCase(".xpm")    ||
              extension.equalsIgnoreCase(".tif")    || extension.equalsIgnoreCase(".tiff")   ||
              extension.equalsIgnoreCase(".rgb")    || extension.equalsIgnoreCase(".rgba")       );
      
      // GeoTIFF uses TIFF and embeds formally structured metadata
      // http://www.remotesensing.org/geotiff/faq.html
  }
  protected boolean isMovieSupportedExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".mpg")    || extension.equalsIgnoreCase(".mpeg")      );
  }
  protected boolean isMovieAlternateExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".mov")    || extension.equalsIgnoreCase(".qt")        );
  }
  protected boolean isSoundSupportedExtension (String extension)
  {
      if (extension == null)
          return false;
      // X3D specification
      // https://www.web3d.org/files/specifications/19775-1/V3.3/Part01/components/sound.html#AudioClip
      
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".au")     || extension.equalsIgnoreCase(".aiff")    ||
              extension.equalsIgnoreCase(".mpg")    || extension.equalsIgnoreCase(".mpeg")    ||   // recommended by X3D specification
              extension.equalsIgnoreCase(".midi")   || extension.equalsIgnoreCase(".mid")     ||   // recommended by X3D specification
              extension.equalsIgnoreCase(".mp3")    || extension.equalsIgnoreCase(".wav")                                                ); // required    by X3D specification
  }
  protected boolean isSoundAlternateExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".qt")     || extension.equalsIgnoreCase(".mp4")       );
  }
  protected boolean isScriptSupportedExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".js")     || extension.equalsIgnoreCase(".class")   || 
              extension.equalsIgnoreCase(".jar")    || extension.equalsIgnoreCase(".java")      );
  }
  protected boolean isHtmlExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".htm")    || extension.equalsIgnoreCase(".html")    ||
              extension.equalsIgnoreCase(".xhtml")                                              );
  }
  protected boolean isXmlExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".xml")    || extension.equalsIgnoreCase(".kml")    ||
              extension.equalsIgnoreCase(".svg")                                                );
  }
  protected boolean isTextExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".txt")   || 
              extension.equalsIgnoreCase(".js")    ||    // be careful to avoid logical collisions with isScriptSupportedExtension
              extension.equalsIgnoreCase(".php")   );
  }
  protected boolean isPdfExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".pdf"));
  }
  protected boolean isVolumeExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".nrrd")); // TODO other alternate formats?
  }
  protected boolean isOtherExtension (String extension)
  {
      if (extension == null)
          return false;
      if    (!extension.startsWith(".")) extension = "." + extension;
      return (extension.equalsIgnoreCase(".txt")    || extension.equalsIgnoreCase(".log"));
  }
  
  private void checkUrlValuesMatchType ()
  {
      boolean   supportedFileExtensionFound = false;
      boolean alternativeFileExtensionFound = false;
      String  alternativeFileExtensions     = "";
      int     alternativeFileExtensionCount = 0;
      boolean     unknownFileExtensionFound;
      String[] urls = getUrlData();
      if ((urls == null) || (urls.length < 1)) return;
      for (int i = 0; i < urls.length; i++)
      {
          String url = urls[i];
          String fileExtension = "";
          if (url.contains("."))
          {
              fileExtension = url.substring(url.lastIndexOf('.'),url.length());
          }
          if      ( url.contains("#"))
                   fileExtension = url.substring(url.lastIndexOf('.'),url.lastIndexOf('#')); // strip X3D proto names, X3D viewpoints and HTML bookmarks from file extension
          else if ( url.trim().startsWith("urn:"))
          {
                   continue; // TODO more detailed handling of urns
          }
          else if (!url.contains("."))
                   continue; // no # bookmark/protoname or . extension provided, ignore this url
          
          // first check supported file types
          if       (urlCustomizerPanel.getFormatChoice().equals("All")) // Anchor can launch any file content type into web browser
          {
              supportedFileExtensionFound = true;
          }
          else if  (fileExtension.isEmpty()) // no file extension found, possibly malformed url
          {
              supportedFileExtensionFound = false;
          }
          else if ((urlCustomizerPanel.getFormatChoice().equals("X3D")    && isX3dSupportedExtension    (fileExtension)) ||

                   (urlCustomizerPanel.getFormatChoice().equals("Image")  && isImageSupportedExtension  (fileExtension)) ||

                   (urlCustomizerPanel.getFormatChoice().equals("Movie")  && isMovieSupportedExtension  (fileExtension)) ||
 
                   (urlCustomizerPanel.getFormatChoice().equals("Sound")  && isSoundSupportedExtension  (fileExtension)) || 

                   (urlCustomizerPanel.getFormatChoice().equals("Script") && isScriptSupportedExtension (fileExtension)) ||

                   (urlCustomizerPanel.getFormatChoice().equals("Volume") && isVolumeExtension          (fileExtension))    )
          {
              supportedFileExtensionFound = true;
          }
          // next check file types without guaranteed support
          else if ((urlCustomizerPanel.getFormatChoice().equals("Image")  && isImageAlternateExtension  (fileExtension)) ||

                   (urlCustomizerPanel.getFormatChoice().equals("Movie")  && isMovieAlternateExtension  (fileExtension)) ||

                   (urlCustomizerPanel.getFormatChoice().equals("HTML")   && isHtmlExtension            (fileExtension)) ||

                   (urlCustomizerPanel.getFormatChoice().equals("XML")    && isXmlExtension             (fileExtension)) ||

                   (urlCustomizerPanel.getFormatChoice().equals("PDF")    && isPdfExtension             (fileExtension)) ||

                   (urlCustomizerPanel.getFormatChoice().equals("Sound")  && isSoundAlternateExtension  (fileExtension)) ||

                   (                                                         isOtherExtension           (fileExtension)))
          {
              alternativeFileExtensionFound = true;
              alternativeFileExtensions     = (alternativeFileExtensions + " " + fileExtension).trim();
              alternativeFileExtensionCount++;
          }
          // otherwise unrecognized
          else 
          {
                unknownFileExtensionFound = true;

                if (!alternateFormatsExpected) // no warning needed for Anchor
                {
                    NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                            "<html><p align='center'>Found unknown file extension " + fileExtension + " in url[" + i + "] </p>" +
                            "<p align='center'>" + url + "</p>" +
                            "<br/><p align='center'>Alternative file formats are allowed but results may be unsupported!</p>", NotifyDescriptor.WARNING_MESSAGE);
                    DialogDisplayer.getDefault().notify(descriptor);
                }
          }
      }
      if (alternativeFileExtensionFound && !supportedFileExtensionFound)
      {
          if  (alternativeFileExtensionCount > 1)
               alternativeFileExtensions = "s " + alternativeFileExtensions;
          else alternativeFileExtensions =  " " + alternativeFileExtensions;

          if (!alternateFormatsExpected) // no warning needed for Anchor
          {
              NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "<html><p align='center'>Found alternative file extension" + alternativeFileExtensions + "</p>" +
                    "<p align='center'>without finding a file having a standard file extension.</p>" +
                    "<br/><p align='center'>Alternative file formats are allowed but results may be unsupported!</p>", NotifyDescriptor.WARNING_MESSAGE);
              DialogDisplayer.getDefault().notify(descriptor);
          }
      }
      // TODO possibly other dialog reports based on logic above
  }

  private void markUrl(final int rowIndex, final int status)
  {
    SwingUtilities.invokeLater(() -> {
        DefaultListModel<String> mod = (DefaultListModel<String>)urlJList.getModel();
        if (rowIndex < mod.getSize())
        {
            String urlString = mod.getElementAt(rowIndex);
            mod.setElementAt(urlString, rowIndex);   // just causes a redraw, the renderer will know
        }
    });
  }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton additionalUrlsButton;
    private javax.swing.JButton alignLeftButton;
    private javax.swing.JButton alignRightButton;
    javax.swing.JButton appendButton;
    private javax.swing.JPanel buttonBarPanel;
    private javax.swing.JSeparator buttonPanelSeparator;
    private javax.swing.JButton domainButton;
    private javax.swing.JButton downButton;
    private javax.swing.JButton editUrlButton;
    private javax.swing.JButton externalEditorButton;
    private javax.swing.JButton launchButton;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton pingButton;
    private javax.swing.JButton qaButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton sortButton;
    private javax.swing.JButton upButton;
    private javax.swing.JList<String> urlJList;
    private javax.swing.JButton whoisButton;
    // End of variables declaration//GEN-END:variables

  /*
   * Class to render lines between list items
   */
  class LinedRenderer extends DefaultListCellRenderer
  {
    Border bottomLine = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(153, 153, 153));
    Color defaultColor = new JLabel().getForeground();
    Color   greenColor = new Color( 93, 124, 51);
    Color  orangeColor = new Color(255, 140,  0);
    Color redColor = redForeground;
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
      Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

      if (c instanceof JComponent) {
        int sz = urlJList.getModel().getSize();
        if (sz == 1)
          ((JComponent) c).setBorder(null);  // none
        else
          ((JComponent) c).setBorder(bottomLine);   // only bottom line

        if (c instanceof JLabel) {
          int status = UrlStatus.INDETERMINATE;
          Color col;
          StatusPack statusPack = urlToStatus.get(((JLabel) c).getText());
          if (statusPack != null)
            status = statusPack.status;
          
          if (urlJList.getModel().getElementAt(index).trim().startsWith("#"))
            status = UrlStatus.INDETERMINATE; // TODO check if viewpoint name is found in scene
    
          switch (status) {
            case UrlStatus.FAILURE:
              col = redColor;
              break;
            case UrlStatus.MISMATCHED_CASE:
              col = orangeColor;
              break;
            case UrlStatus.SUCCESS:
              col = greenColor;
              break;
            case UrlStatus.INDETERMINATE:
            default:
              col = defaultColor;
              ;
          }
          ((JLabel) c).setForeground(col);
          
          ((JLabel) c).setHorizontalAlignment(alignLeft?JLabel.LEFT:JLabel.RIGHT);
        }
      }
      return c;
    }
  }

  public static interface InsertRowFilterIf
  {
    /**
     * @param selectedRow is selected row, or -1 if none
     * @return row number to pass to TableModel.insertRow, or -1 if insert not allowed
     */
    public int insertRowNumber(int selectedRow);
  }

  class InsertRowFilter implements InsertRowFilterIf
  {
    @Override
    public int insertRowNumber(int selRow)
    {
      if(selRow == -1)
        return urlJList.getModel().getSize();
  
      return selRow + 1; // append after selected row
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
      if (selRow == -1 || urlJList.getModel().getSize() <= 0)
        return -1;
      return selRow;
    }
  }
  private RemoveRowFilterIf removeRowFilter = new RemoveRowFilter();
  private InsertRowFilterIf insertRowFilter = new InsertRowFilter();

  public void setInsertRowFilter(InsertRowFilterIf filter)
  {
    if (filter == null)
      insertRowFilter = new InsertRowFilter();
    else
      insertRowFilter = filter;
  }

  public void setRemoveRowFilter(RemoveRowFilterIf filter)
  {
    if (filter == null)
      removeRowFilter = new RemoveRowFilter();
    else
      removeRowFilter = filter;
  }
  /**
   * Select correct file chooser filter based on file extension
   * @param fileExtension
   * @return true if filter selection found
   */
  public boolean selectFileChooserFilter (String fileExtension)
  {
        if      (isX3dSupportedExtension(fileExtension))
        {
            setFileChooserX3d();
        }
        else if (isImageSupportedExtension(fileExtension) || isImageAlternateExtension(fileExtension))
        {
            setFileChooserImage();
        }
        else if (isMovieSupportedExtension(fileExtension) || isMovieAlternateExtension(fileExtension))
        {
            setFileChooserMovie();
        }
        else if (isSoundSupportedExtension(fileExtension) || isSoundAlternateExtension(fileExtension))
        {
            setFileChooserSound();
        }
        else if (isScriptSupportedExtension(fileExtension))
        {
            setFileChooserScript();
        }
        else if (isVolumeExtension(fileExtension))
        {
            setFileChooserVolume();
        }
        else if (isHtmlExtension(fileExtension))
        {
            setFileChooserHtml();
        }
        else if (isPdfExtension(fileExtension))
        {
            setFileChooserPdf();
        }
        else if (isXmlExtension(fileExtension))
        {
            setFileChooserXml();
        }
        else return false; // not found
        
        return true;       // found
  }

  public void setFileChooserAll()
  {
    urlCustomizerPanel.setFormatChoice ("All");
  }
  public void setFileChooserX3d() // default in UrlCustomizerPanel
  {
    urlCustomizerPanel.setFormatChoice ("X3D");
    additionalUrlsButton.setText("Add urls"); // plural, override property
    additionalUrlsButton.setToolTipText(additionalUrlsButton.getToolTipText().replace("value","values"));
  }
  public void setFileChooserImage()
  {
    urlCustomizerPanel.setFormatChoice ("Image");
  }
  public void setFileChooserSound()
  {
    urlCustomizerPanel.setFormatChoice ("Sound");
  }
  public void setFileChooserMovie()
  {
    urlCustomizerPanel.setFormatChoice ("Movie");
  }
  public void setFileChooserScript()
  {
    urlCustomizerPanel.setFormatChoice ("Script");
  }
  public void setFileChooserHtml()
  {
    urlCustomizerPanel.setFormatChoice ("HTML");
  }
  public void setFileChooserPdf()
  {
    urlCustomizerPanel.setFormatChoice ("PDF");
  }
  public void setFileChooserText()
  {
    urlCustomizerPanel.setFormatChoice ("Text");
  }
  public void setFileChooserXml()
  {
    urlCustomizerPanel.setFormatChoice ("XML");
  }
  public void setFileChooserVolume()
  {
    urlCustomizerPanel.setFormatChoice ("Volume");
  }
}
