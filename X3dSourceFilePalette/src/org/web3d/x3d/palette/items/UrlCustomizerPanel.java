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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.IOProvider;

/**
 * UrlCustomizerPanel.java
 * Created on August 13, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class UrlCustomizerPanel extends javax.swing.JPanel
{
  private static       String formatChoice = "X3D"; // "Image" "Sound" etc.  See UrlExpandableList2
  private static final String RELATIVE_CHOICE = "[relative]";
  private final int RELATIVE_PROTO_INDEX = 0;
  private static final String[] PROTOCOL_CHOICES = {
    RELATIVE_CHOICE,
    "http://",
    "https://",
    "ftp://",
    "sftp://",
    "file://",
    "xmpp://",
    "mailto:"
  };
  
  private FileObject masterFo;
  private JCheckBox chooserRelativeCB;
  
  private void initializeFileChooser()
  {
	  if (fileChooser == null) 
	  {
		  fileChooser = new JFileChooser();
		  fileChooser.setMultiSelectionEnabled(false);
		  fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		  fileChooser.setAccessory(makeCheckBox());
	  }
  }

  /** Creates new form UrlCustomizerPanel */
  public UrlCustomizerPanel()
  {
    initComponents();
    pathTF.requestFocusInWindow(); // simplify user ability to paste in a new value immediately
  }

  public void setData(String txt)
  {
    try {
      URL url = new URL(txt);
      protocolCombo.setSelectedItem(url.getProtocol() + "://");
      pathTF.setText(stripProtocol(url));
    }
    catch (MalformedURLException ex) {
      protocolCombo.setSelectedIndex(RELATIVE_PROTO_INDEX);
      pathTF.setText(txt);
    }
  }

  public void setMasterDocumentLocation(FileObject masterFo)
  {
    this.masterFo = masterFo;
  }

  public String getData()
  {
    String protocol = (String) protocolCombo.getSelectedItem();
    
    // reset if user already provided a protocol in the url field
    if  (protocol.equals(RELATIVE_CHOICE) || pathTF.getText().contains("://") || pathTF.getText().startsWith("mailto:"))
         protocol = "";

    // avoid extra slash(es)
    while  (pathTF.getText().contains("://") && pathTF.getText().trim().startsWith("/"))
    {
        String path = pathTF.getText().trim();
        pathTF.setText(path.substring(path.indexOf("/")));
    }

    return protocol + pathTF.getText().trim();
  }

  private String stripProtocol(URL url)
  {
    StringBuilder sb = new StringBuilder();
    String s = url.getUserInfo();
    if (s != null) {
      sb.append(s);
      sb.append('@');
    }
    s = url.getHost();
    if (s != null)
      sb.append(s);

    int p = url.getPort();
    if (p != -1) {
      sb.append(':');
      sb.append(p);
    }
    s = url.getFile();
    if (s != null)
      sb.append(s);

    s = url.getRef();
    if (s != null) {
      sb.append('#');
      sb.append(s);
    }
    return sb.toString();
  }

  class CenteredJLabel extends JLabel
  {
	@SuppressWarnings("OverridableMethodCallInConstructor")
    CenteredJLabel(String s)
    {
      super(s);
      this.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    }
  }

  private JComponent makeCheckBox()
  {
    JPanel pan = new JPanel();
    pan.setBorder(new EmptyBorder(8, 8, 8, 8));
    pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
    pan.add(Box.createVerticalGlue());
    pan.add(new CenteredJLabel("Select"));
    pan.add(new CenteredJLabel("using"));
    pan.add(new CenteredJLabel("relative"));
    pan.add(new CenteredJLabel("path"));
    pan.add(new CenteredJLabel("from"));
    pan.add(new CenteredJLabel("scene"));
    chooserRelativeCB = new JCheckBox();
    chooserRelativeCB.setAlignmentX(JCheckBox.CENTER_ALIGNMENT);
    chooserRelativeCB.setSelected(true);
    pan.add(chooserRelativeCB);
    pan.add(Box.createVerticalGlue());
    return pan;
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

        jPanel1 = new javax.swing.JPanel();
        protocolLabel = new javax.swing.JLabel();
        protocolCombo = new javax.swing.JComboBox<>();
        pathLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pathTF = new javax.swing.JTextField();
        clearButton = new javax.swing.JButton();
        browseButton = new javax.swing.JButton();
        rightSideSpacerLabel = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1026, 60));
        setMinimumSize(new java.awt.Dimension(458, 60));
        setPreferredSize(new java.awt.Dimension(542, 60));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMaximumSize(new java.awt.Dimension(800, 59));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        protocolLabel.setText(NbBundle.getMessage(getClass(), "UrlCustomizerPanel.protocolLabel.text")); // NOI18N
        protocolLabel.setToolTipText(org.openide.util.NbBundle.getMessage(UrlCustomizerPanel.class, "UrlCustomizerPanel.protocolLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 3);
        jPanel1.add(protocolLabel, gridBagConstraints);

        protocolCombo.setModel(new javax.swing.DefaultComboBoxModel<String>(PROTOCOL_CHOICES));
        protocolCombo.setToolTipText(org.openide.util.NbBundle.getMessage(UrlCustomizerPanel.class, "UrlCustomizerPanel.protocolCombo.toolTipText")); // NOI18N
        protocolCombo.setMaximumSize(new java.awt.Dimension(100, 22));
        protocolCombo.setMinimumSize(new java.awt.Dimension(85, 22));
        protocolCombo.setPreferredSize(new java.awt.Dimension(85, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        jPanel1.add(protocolCombo, gridBagConstraints);

        pathLabel.setText(NbBundle.getMessage(getClass(), "UrlCustomizerPanel.pathLabel.text")); // NOI18N
        pathLabel.setToolTipText(org.openide.util.NbBundle.getMessage(UrlCustomizerPanel.class, "UrlCustomizerPanel.pathLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 3, 3);
        jPanel1.add(pathLabel, gridBagConstraints);

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(150, 21));

        pathTF.setText(NbBundle.getMessage(getClass(), "UrlCustomizerPanel.pathTF.text")); // NOI18N
        pathTF.setToolTipText(org.openide.util.NbBundle.getMessage(UrlCustomizerPanel.class, "UrlCustomizerPanel.pathTF.toolTipText")); // NOI18N
        pathTF.setMinimumSize(new java.awt.Dimension(200, 22));
        pathTF.setPreferredSize(new java.awt.Dimension(200, 22));
        jScrollPane1.setViewportView(pathTF);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        clearButton.setText(org.openide.util.NbBundle.getMessage(UrlCustomizerPanel.class, "UrlCustomizerPanel.clearButton.text")); // NOI18N
        clearButton.setToolTipText(org.openide.util.NbBundle.getMessage(UrlCustomizerPanel.class, "UrlCustomizerPanel.clearButton.toolTipText")); // NOI18N
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel1.add(clearButton, gridBagConstraints);

        browseButton.setText(NbBundle.getMessage(getClass(), "UrlCustomizerPanel.browseButton.text")); // NOI18N
        browseButton.setToolTipText(org.openide.util.NbBundle.getMessage(UrlCustomizerPanel.class, "UrlCustomizerPanel.browseButton.toolTipText")); // NOI18N
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel1.add(browseButton, gridBagConstraints);

        rightSideSpacerLabel.setText(org.openide.util.NbBundle.getMessage(UrlCustomizerPanel.class, "UrlCustomizerPanel.rightSideSpacerLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 5;
        jPanel1.add(rightSideSpacerLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  
	private JFileChooser fileChooser;
	
private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
    initializeFileChooser(); // if necessary
	if (pathTF.getText().length() > 0)
    {
        fileChooser.setSelectedFile(new File(pathTF.getText()));
    }
    else // clear prior choice, if any; not apparent that this step really helps however..
    {
        fileChooser.setSelectedFile(new File(fileChooser.getCurrentDirectory().toString() + "/")); // do not retain prior filename
    }
    setChooserFileFilters(); // TODO sufficient for reset if file extension changes?
    
    File f;
    try
    {
      if (masterFo != null)
          fileChooser.setCurrentDirectory(FileUtil.toFile(masterFo.getParent()));
        
      String originalFileName = pathTF.getText().trim();
      f = new File(fileChooser.getCurrentDirectory().getPath() + "/" + originalFileName);

      // set selection in chooser, carefully
      if (f.exists() && (originalFileName.length() > 0)) 
      {
          boolean found = false;
          // avoid propagating possible filename case mismatch on chooser selection
          File parentDirectory = new File (f.getParent());
          for (int i = 0; i < parentDirectory.listFiles().length; i++)
          {
              String newFileName =  parentDirectory.listFiles()[i].getName();
              if (parentDirectory.listFiles()[i].isDirectory()) 
                  continue;
              if (!originalFileName.equals(newFileName) && originalFileName.equalsIgnoreCase(newFileName))
              {
                  File newFile = new File(fileChooser.getCurrentDirectory().getPath() + "/" + newFileName);
                  System.out.println ("Overrode incorrectly capitalized filename (" + originalFileName + " as " + newFileName);
                  if (newFile.exists())
                  {
                      fileChooser.setSelectedFile(newFile);
                      found = true;
                      break;
                  }
              }
          }
          if (!found)
              fileChooser.setSelectedFile(f);
      }
    }
    catch (Exception ex) {/* nice try, anyway */}

    int ret = fileChooser.showOpenDialog(this);
    if (ret == JFileChooser.CANCEL_OPTION)
      return;

    String path;
    boolean useRelativePath = chooserRelativeCB.isSelected();
    if (useRelativePath && masterFo == null)
      useRelativePath = false;  // can't calculate a relativePath if don't know where we are

    f = fileChooser.getSelectedFile();
    if (f.exists() && useRelativePath)
      path = goHunting(masterFo, f);
    else if (f.exists())
      path = absolutePath(f);
    else return; // safety net, why did we land here??

    setData(path);
  }

  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting XML encoding x3d file extension */
  public final X3DFilter    x3dFilter    = new X3DFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting ClassicVRML encoding x3dv file extension */
  public final X3DVFilter   x3dvFilter   = new X3DVFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting Compressed Binary Encoding x3db file extension */
  public final X3DBFilter   x3dbFilter   = new X3DBFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting VRML97 wrl file extension */
  public final WRLFilter    wrlFilter    = new WRLFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting all X3D file extensions: x3d x3dv x3db wrl */
  public final X3dAllFilter allX3dFilter = new X3dAllFilter();

  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting Portable Networked Graphics png image file extension */
  public final ImagePngFilter  imagePngFilter  = new ImagePngFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting jpg image file extension */
  public final ImageJpgFilter  imageJpgFilter  = new ImageJpgFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting jpeg image file extension */
  public final ImageJpegFilter imageJpegFilter = new ImageJpegFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting gif image file extension */
  public final ImageGifFilter  imageGifFilter  = new ImageGifFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting Scalable Vector Graphics svg image file extension */
  public final ImageSvgFilter  imageSvgFilter  = new ImageSvgFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting png jpg jpeg svg and gif image file extensions */
  public final ImagesAllFilter allImagesFilter = new ImagesAllFilter();

  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting mpeg video file extension */
  public final MovieMpegFilter movieMpegFilter = new MovieMpegFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting mpg video file extension */
  public final MovieMpgFilter  movieMpgFilter  = new MovieMpgFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting mp4 video file extension */
  public final MovieMp4Filter  movieMp4Filter  = new MovieMp4Filter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting ogv video file extension */
  public final MovieOgvFilter  movieOgvFilter  = new MovieOgvFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting mov video file extension */
  public final MovieMovFilter  movieMovFilter  = new MovieMovFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting QuickTime qt video file extension */
  public final MovieQtFilter   movieQtFilter   = new MovieQtFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting avi video file extension */
  public final MovieAviFilter  movieAviFilter  = new MovieAviFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting Windows wmv video file extension */
  public final MovieWmvFilter  movieWmvFilter  = new MovieWmvFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting all move file extensions, mpeg mpg mp4 ogv mov qt avi wmv */
  public final MoviesAllFilter movieAllFilter  = new MoviesAllFilter();

  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting au audio file extension */
  public final AudioAuFilter   audioAuFilter   = new AudioAuFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting ai audio file extension */
  public final AudioAiffFilter audioAiffFilter = new AudioAiffFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting midi audio file extension */
  public final AudioMidiFilter audioMidiFilter = new AudioMidiFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting mid audio file extension */
  public final AudioMidFilter  audioMidFilter  = new AudioMidFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting mp3 audio file extension */
  public final AudioMp3Filter  audioMp3Filter  = new AudioMp3Filter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting mp4 audio file extension */
  public final AudioMp4Filter  audioMp4Filter  = new AudioMp4Filter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting oga audio file extension */
  public final AudioOgaFilter  audioOgaFilter  = new AudioOgaFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting ogg audio file extension */
  public final AudioOggFilter  audioOggFilter  = new AudioOggFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting wav audio file extension */
  public final AudioWavFilter  audioWavFilter  = new AudioWavFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting all audio file extensions: au ai midi mid mp3 mp4 oga ogg wav */
  public final AudioAllFilter  audioAllFilter  = new AudioAllFilter();
  
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting bvh motion capture (mocap) file extension */
  public final MocapBvhFilter  bvhFilter       = new MocapBvhFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting all motion capture (mocap) file extensions: bvh */
  public final MocapAllFilter  mocapAllFilter  = new MocapAllFilter();

  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting js javascript file extension */
  public final ScriptJsFilter    scriptJsFilter    = new ScriptJsFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting java source file extension */
  public final ScriptJavaFilter  scriptJavaFilter  = new ScriptJavaFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting java compiled class file extension */
  public final ScriptClassFilter scriptClassFilter = new ScriptClassFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting java archive jar file extension */
  public final ScriptJarFilter   scriptJarFilter   = new ScriptJarFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting all Script file extensions: js java class jar */
  public final ScriptAllFilter   scriptAllFilter   = new ScriptAllFilter();
  
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting html file extension */
  public final HtmlFilter        htmlFilter        = new HtmlFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting html file extension */
  public final TextFilter        textFilter        = new TextFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting pdf file extension */
  public final PdfFilter         pdfFilter         = new PdfFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting xml file extension */
  public final XmlFilter         xmlFilter         = new XmlFilter();
  
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting nrrd volume file extension */
  public final VolumeRenderingNrrdFilter  volumeRenderingNrrdFilter  = new VolumeRenderingNrrdFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting vol volume file extension */
  public final VolumeRenderingVolFilter   volumeRenderingVolFilter   = new VolumeRenderingVolFilter();
  @SuppressWarnings("NonPublicExported")
  /** FileFilter subclass supporting all volume file extensions: nrrd vol */
  public final VolumeRenderingAllFilter   volumeRenderingAllFilter   = new VolumeRenderingAllFilter();

  public void setChooserFileFilters()
  {
	  initializeFileChooser(); // if necessary
      fileChooser.resetChoosableFileFilters();
      if (formatChoice.equalsIgnoreCase("X3D"))
      {
        fileChooser.addChoosableFileFilter(allX3dFilter);
        fileChooser.addChoosableFileFilter(x3dFilter);
        fileChooser.addChoosableFileFilter(x3dvFilter);
        fileChooser.addChoosableFileFilter(x3dbFilter);
        fileChooser.addChoosableFileFilter(wrlFilter);

        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(allX3dFilter);
      }
      else if (formatChoice.equalsIgnoreCase("Image"))
      {
        fileChooser.addChoosableFileFilter(allImagesFilter);
        fileChooser.addChoosableFileFilter(imagePngFilter);
        fileChooser.addChoosableFileFilter(imageJpgFilter);
        fileChooser.addChoosableFileFilter(imageJpegFilter);
        fileChooser.addChoosableFileFilter(imageGifFilter);
        fileChooser.addChoosableFileFilter(imageSvgFilter);

        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(allImagesFilter);
      }
      else if (formatChoice.equalsIgnoreCase("Movie"))
      {
        fileChooser.addChoosableFileFilter(movieAllFilter);
        fileChooser.addChoosableFileFilter(movieAviFilter);
        fileChooser.addChoosableFileFilter(movieMovFilter);
        fileChooser.addChoosableFileFilter(movieMp4Filter);
        fileChooser.addChoosableFileFilter(movieMpegFilter);
        fileChooser.addChoosableFileFilter(movieMpgFilter);
        fileChooser.addChoosableFileFilter(movieOgvFilter);
        fileChooser.addChoosableFileFilter(movieQtFilter);
        fileChooser.addChoosableFileFilter(movieWmvFilter);

        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(movieAllFilter);
      }
      else if (formatChoice.equalsIgnoreCase("Sound"))
      {
        fileChooser.addChoosableFileFilter(audioAllFilter);
        fileChooser.addChoosableFileFilter(audioAuFilter);
        fileChooser.addChoosableFileFilter(audioAiffFilter);
        fileChooser.addChoosableFileFilter(audioMidiFilter);
        fileChooser.addChoosableFileFilter(audioMidFilter);
        fileChooser.addChoosableFileFilter(movieMovFilter);
        fileChooser.addChoosableFileFilter(audioMp3Filter);
        fileChooser.addChoosableFileFilter(audioMp4Filter);
//      chooser.addChoosableFileFilter(movieMp4Filter);
        fileChooser.addChoosableFileFilter(movieMpegFilter);
        fileChooser.addChoosableFileFilter(movieMpgFilter);
        fileChooser.addChoosableFileFilter(audioOgaFilter);
        fileChooser.addChoosableFileFilter(audioOggFilter);
        fileChooser.addChoosableFileFilter(movieOgvFilter);
        fileChooser.addChoosableFileFilter(audioWavFilter);
        fileChooser.addChoosableFileFilter(movieWmvFilter);

        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(audioAllFilter);
      }
      else if (formatChoice.equalsIgnoreCase("Script"))
      {
        fileChooser.addChoosableFileFilter(scriptJsFilter);
        fileChooser.addChoosableFileFilter(scriptJavaFilter);
        fileChooser.addChoosableFileFilter(scriptClassFilter);
        fileChooser.addChoosableFileFilter(scriptJarFilter);
        fileChooser.addChoosableFileFilter(scriptAllFilter);

        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(scriptAllFilter);
      }
      else if (formatChoice.equalsIgnoreCase("Volume"))
      {
        fileChooser.addChoosableFileFilter(volumeRenderingNrrdFilter);
        fileChooser.addChoosableFileFilter(volumeRenderingVolFilter);
        fileChooser.addChoosableFileFilter(volumeRenderingAllFilter);

        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(volumeRenderingAllFilter);
      }
      else if (formatChoice.equalsIgnoreCase("PDF")) // includes PDF/A https://en.wikipedia.org/?title=PDF/A
      {
        fileChooser.addChoosableFileFilter(pdfFilter);
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(pdfFilter);
      }
      else if (formatChoice.equalsIgnoreCase("HTML"))
      {
        fileChooser.addChoosableFileFilter(htmlFilter);
        fileChooser.addChoosableFileFilter(xmlFilter);
        fileChooser.addChoosableFileFilter(pdfFilter);
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(htmlFilter);
      }
      else if (formatChoice.equalsIgnoreCase("XML"))
      {
        fileChooser.addChoosableFileFilter(xmlFilter);
        fileChooser.addChoosableFileFilter(htmlFilter);
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(xmlFilter);
      }
      else if (formatChoice.equalsIgnoreCase("BVH"))
      {
        fileChooser.addChoosableFileFilter(bvhFilter);
        fileChooser.addChoosableFileFilter(textFilter);
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(bvhFilter);
      }
      else if (formatChoice.equalsIgnoreCase("txt"))
      {
        fileChooser.addChoosableFileFilter(textFilter);
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(textFilter);
      }
      else // shouldn't reach this default
      {
        fileChooser.setAcceptAllFileFilterUsed(true);
      }
  }

  private String goHunting(FileObject sourceFo, File target)
  {
    FileObject targetFo = FileUtil.toFileObject(target);
    String path = null;
    int backCount = 0;
    sourceFo = sourceFo.getParent();  // start off with parent
    
    do {
      try
      {
          path = FileUtil.getRelativePath(sourceFo, targetFo);
      }
      catch (Exception e)
      {
          // netbeans methods are twitchy
      }
      if (path == null) {
        if (sourceFo.isRoot()) {
          IOProvider.getDefault().getIO("Output", false).getOut().append("No path from " + target.getName() + " to " + sourceFo.getNameExt());
          return absolutePath(target);
        }
        sourceFo = sourceFo.getParent();
        backCount++;
      }

    } while (path == null);

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < backCount; i++)
      sb.append("../");
    sb.append(path);
    return sb.toString();
  }

  private String absolutePath(File f)
  {
    String absolutePath = f.getAbsolutePath();
    try {
      URL url = Utilities.toURI(f).toURL();
      absolutePath = url.toExternalForm();
    //path = f.getCanonicalPath();
    }
    catch (MalformedURLException ex) {
    }
    return absolutePath;
}//GEN-LAST:event_browseButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        pathTF.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JTextField pathTF;
    private javax.swing.JComboBox<String> protocolCombo;
    private javax.swing.JLabel protocolLabel;
    private javax.swing.JLabel rightSideSpacerLabel;
    // End of variables declaration//GEN-END:variables

  private boolean commonAccept(File f, String fileType)
  {
    if (f.isDirectory())
      return true;

    String extension = "";
    if (f.getName().contains("."))
        extension = f.getName().substring(f.getName().lastIndexOf(".")+1,f.getName().length());
    
    if ((extension.length() > 0) && extension.equalsIgnoreCase(fileType))
         return true;
    else return false;
  }

  public String getFormatChoice ()
  {
      return formatChoice;
  }

  public void setFormatChoice (String newFormatChoice)
  {
      formatChoice = newFormatChoice;
	  setChooserFileFilters();
  }
  
  class X3DFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"x3d"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.x3d_file_description.text"); }
  }
  class X3DVFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"x3dv"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.x3dv_file_description.text"); }
  }
  class X3DBFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"x3db"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.x3db_file_description.text"); }
  }
  class WRLFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"wrl"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.wrl_file_description.text"); }
  }
  class X3dAllFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return x3dFilter.accept(f) || x3dvFilter.accept(f) ||
                                          x3dbFilter.accept(f) || wrlFilter.accept(f); }
    @Override
    public String getDescription(){return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.allx3d_file_description.text");}
  }

  class ImagePngFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"png"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.png_file_description.text"); }
  }
  class ImageJpgFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"jpg"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.jpg_file_description.text"); }
  }
  class ImageJpegFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"jpeg"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.jpeg_file_description.text"); }
  }
  class ImageGifFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"gif"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.gif_file_description.text"); }
  }
  class ImageSvgFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"svg"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.svg_file_description.text"); }
  }
  /** FileFilter subclass supporting png jpg jpeg svg and gif file extensions */
  class ImagesAllFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return imagePngFilter.accept(f)  || imageJpgFilter.accept(f) ||
                                           imageJpegFilter.accept(f) || imageGifFilter.accept(f) ||
                                           imageSvgFilter.accept(f); }
    @Override
    public String getDescription(){return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.allimage_file_description.text");}
  }

  class MovieMpegFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"mpeg"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.mpeg_file_description.text"); }
  }
  class MovieMpgFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"mpg"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.mpg_file_description.text"); }
  }
  class MovieMp4Filter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"mp4"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.mp4_file_description.text"); }
  }
  /** https://en.wikipedia.org/wiki/Ogg */
  class MovieOgvFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"ogv"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.ogv_file_description.text"); }
  }
  class MovieMovFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"mov"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.mov_file_description.text"); }
  }
  class MovieQtFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"qt"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.qt_file_description.text"); }
  }
  class MovieAviFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"avi"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.avi_file_description.text"); }
  }
  class MovieWmvFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"wmv"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.wmv_file_description.text"); }
  }
  class MoviesAllFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return movieMpegFilter.accept(f) || movieMpgFilter.accept(f) ||
                                           movieMp4Filter.accept(f)  || movieOgvFilter.accept(f) ||
                                           movieMovFilter.accept(f)  || movieQtFilter.accept(f)  ||
                                           movieAviFilter.accept(f)  || movieWmvFilter.accept(f); }
    @Override
    public String getDescription(){return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.allmovie_file_description.text");}
  }

  class AudioAuFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"au"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.au_file_description.text"); }
  }
  class AudioAiffFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"aiff"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.aiff_file_description.text"); }
  }
  class AudioMidiFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"midi"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.midi_file_description.text"); }
  }
  class AudioMidFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"mid"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.mid_file_description.text"); }
  }
  class AudioMp3Filter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"mp3"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.mp3_file_description.text"); }
  }
  class AudioMp4Filter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"mp4"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.mp4_file_description.text"); }
  }
  class AudioOgaFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"oga"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.oga_file_description.text"); }
  }
  class AudioOggFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"ogg"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.ogg_file_description.text"); }
  }
  class AudioWavFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"wav"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.wav_file_description.text"); }
  }
  class MocapBvhFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"bvh"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.bvh_file_description.text"); }
  }
  class MocapAllFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return bvhFilter.accept(f); } // TODO others?
    @Override
    public String getDescription(){return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.allmocap_file_description.text");}
  }
  class AudioAllFilter extends FileFilter
  {
    // movie files can also serve as an audio source
    @Override
    public boolean accept(File f) { return audioAuFilter.accept(f)   || audioAiffFilter.accept(f) ||
                                           audioMidiFilter.accept(f) || audioMidFilter.accept(f)  ||
                                           audioMp3Filter.accept(f)  || audioMp4Filter.accept(f)  ||
                                           audioOgaFilter.accept(f)  || audioOggFilter.accept(f)  ||
                                           audioWavFilter.accept(f)  ||
                                           movieMpegFilter.accept(f) || movieMpgFilter.accept(f)  ||
                                           movieMp4Filter.accept(f)  || movieOgvFilter.accept(f)  ||
                                           movieMovFilter.accept(f)  || movieWmvFilter.accept(f); }
    @Override
    public String getDescription(){return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.allaudio_file_description.text");}
  }

  class ScriptJsFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"js"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.js_file_description.text"); }
  }
  class ScriptJavaFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"java"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.java_file_description.text"); }
  }
  class ScriptClassFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"class"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.class_file_description.text"); }
  }
  class ScriptJarFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"jar"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.jar_file_description.text"); }
  }
  class ScriptAllFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return scriptJsFilter.accept(f)    || scriptJavaFilter.accept(f) ||
                                           scriptClassFilter.accept(f) || scriptJarFilter.accept(f); }
    @Override
    public String getDescription(){return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.allscript_file_description.text");}
  }
  class PdfFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { 
        return commonAccept(f,"pdf"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.pdf_file_description.text"); }
  }
  class HtmlFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return (commonAccept(f,"html") || commonAccept(f,"htm") || commonAccept(f,"xhtml")); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.html_file_description.text"); }
  }
  class TextFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return (commonAccept(f,"txt")); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.html_file_description.text"); }
  }
  class XmlFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return (commonAccept(f,"xml") || commonAccept(f,"kml") || commonAccept(f,"svg")); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.xml_file_description.text"); }
  }
  class VolumeRenderingNrrdFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"nrrd"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.nrrd_file_description.text"); }
  }
  class VolumeRenderingVolFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return commonAccept(f,"vol"); }
    @Override
    public String getDescription(){ return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.vol_file_description.text"); }
  }
  class VolumeRenderingAllFilter extends FileFilter
  {
    @Override
    public boolean accept(File f) { return volumeRenderingNrrdFilter.accept(f); }
    @Override
    public String getDescription(){return NbBundle.getMessage(getClass(), "UrlCustomizerPanel.allvolume_file_description.text");}
  }
}
