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
/*
 * DISPlayerRecorderPanel.java
 *
 * Created on October 26, 2008, 4:06 PM
 */
package org.web3d.x3d.dis.playerrecorder;

import edu.nps.moves.dis7.enumerations.DisPduType;
import edu.nps.moves.dis7.pdus.*;
import edu.nps.moves.dis7.utilities.PduFactory;
import java.awt.Color;
import java.awt.Component;
import java.io.*;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import org.openide.util.NbBundle;
import static org.web3d.x3d.dis.playerrecorder.DISGrabber.*;
import org.web3d.x3d.dis.playerrecorder.DISGrabber.DISReceivedListener;
import org.web3d.x3d.dis.playerrecorder.DISPlayer.DISSenderStoppedListener;
import org.web3d.x3d.dis.playerrecorder.DISPlayer.DISSentListener;
import org.web3d.x3d.dis.playerrecorder.DISPlayerRecorderStateMachine.PlayerEvent;
import org.web3d.x3d.options.X3dOptions;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;

/**
 *
 * @author  mike
 */
public class DISPlayerRecorderPanel extends javax.swing.JPanel 
    implements DISReceivedListener, DISSenderStoppedListener, DISSentListener
{
  protected static final Color ACTIVE_COLOR = new Color(0.1f, 0.3f, 0.9f);

  DISGrabber grabber;
  DISPlayer  player;
  private static final HashMap<Class, Class> displayMap = new HashMap<Class, Class>();
  

  static {
//    displayMap.put(FastEntityStatePdu.class,    FastEntityStatePduDisplayPanel.class); // was experimental, no longer supported

    displayMap.put(AcknowledgePdu.class,        AcknowledgePduDisplayPanel.class); //15
    displayMap.put(ActionRequestPdu.class,      ActionRequestPduDisplayPanel.class); //16
    displayMap.put(ActionResponsePdu.class,     ActionResponsePduDisplayPanel.class); // 17
    displayMap.put(CollisionPdu.class,          CollisionPduDisplayPanel.class); //4
    displayMap.put(CommentPdu.class,            CommentPduDisplayPanel.class);  //22
    displayMap.put(CreateEntityPdu.class,       CreateEntityPduDisplayPanel.class); //11
    displayMap.put(DataPdu.class,               DataPduDisplayPanel.class);  //20
    displayMap.put(DataQueryPdu.class,          DataQueryPduDisplayPanel.class); // 18
    displayMap.put(DesignatorPdu.class,         DesignatorPduDisplayPanel.class); //24
    displayMap.put(DetonationPdu.class,         DetonationPduDisplayPanel.class); //3
    displayMap.put(ElectromagneticEmissionPdu.class,null); // tbd ElectronicEmissionsPduDisplayPanel.class); //23
    displayMap.put(EntityStatePdu.class,        EntityStatePduDisplayPanel.class); //1
    displayMap.put(EventReportPdu.class,        EventReportPduDisplayPanel.class); //21
    displayMap.put(FirePdu.class,               FirePduDisplayPanel.class); //2
    displayMap.put(ReceiverPdu.class,           ReceiverPduDisplayPanel.class); //27
    displayMap.put(RemoveEntityPdu.class,       RemoveEntityPduDisplayPanel.class); //12
    displayMap.put(RepairCompletePdu.class,     RepairCompletePduDisplayPanel.class); //9
    displayMap.put(RepairResponsePdu.class,     RepairResponsePduDisplayPanel.class); //10
    displayMap.put(ResupplyCancelPdu.class,     ResupplyCancelPduDisplayPanel.class); // 8
    displayMap.put(ResupplyOfferPdu.class,      ResupplyOfferPduDisplayPanel.class); //6
    displayMap.put(ResupplyReceivedPdu.class,   ResupplyReceivedPduDisplayPanel.class);  //7
    displayMap.put(ServiceRequestPdu.class,     ServiceRequestPduDisplayPanel.class); //5
    displayMap.put(StartResumePdu.class,        StartResumePduDisplayPanel.class); //13
    displayMap.put(SetDataPdu.class,            SetDataPduDisplayPanel.class);
    displayMap.put(SignalPdu.class,             SignalPduDisplayPanel.class);
    displayMap.put(StopFreezePdu.class,         StopFreezePduDisplayPanel.class);  //14
    displayMap.put(TransmitterPdu.class,        null); // tbd TransmitterPduDisplayPanel.class); //25
  }

  private DISPlayerRecorderStateMachine stateMachine;

  public DISPlayerRecorderPanel()
  {
    initComponents();
    
    stateMachine = new DISPlayerRecorderStateMachine(this);
    // all three sizes of play and pause butts have been manually set in matisse to == the largest of the three, the rev play butt
    // this is to make them not move around when one has been made invisible
    
//    textModeButton.setSelected(true); // TODO how to configure via interface?
 }
  
  File[] captureFileSet;
  File captureFile;
  RandomAccessFile randFile;

  private void setCaptureFileSet(File[] fa)
  {
    captureFileSet = fa;
    captureFile = fa[0];
    try {
      randFile = new RandomAccessFile(fa[0], "r");
    }
    catch (FileNotFoundException ex) {
      System.err.println("Can't make ra file from " + fa[0].getAbsolutePath() + " : " + ex.getLocalizedMessage());
      randFile = null;
    }
  }
  private File[] getCaptureFileSet()
  {
    return captureFileSet;
  }
  private void saveFiles(File selectedFile) throws Exception
  {
    FileIO.copyFile(new FileInputStream(captureFileSet[0]), new FileOutputStream(selectedFile),true);
    File indexFile = new File(selectedFile.getParent(), selectedFile.getName() + DISGrabber.DEFAULT_BINARY_INDEX_FILE_ENDING);
    FileIO.copyFile(new FileInputStream(captureFileSet[1]), new FileOutputStream(indexFile), true);
  }

  class ByteArrayListRenderer extends DefaultListCellRenderer
  {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
      Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      if (c instanceof JLabel && value instanceof byte[]) {
        String s = translateBytes((byte[]) value, index);
        ((JLabel) c).setText(s);
      }
      return c;
    }
  }
  
  StringBuilder rendererSB = new StringBuilder();
  private ByteBuffer rendererBB = ByteBuffer.allocate(IDXFILE_RECORD_SIZE);

  private String translateBytes(byte[] ba, int index)
  {
    try {
      // from pduFactory:
      // Promote a signed byte to an int, then do a bitwise AND to wipe out everthing but the
      // first eight bits. This effectively lets us read this as an unsigned byte
      int pduType = 0x000000FF & (int) ba[2]; // The pdu type is a one-byte, unsigned byte in the third byte position.

      // Do a lookup to get the enumeration instance that corresponds to this value.
      DisPduType pduTypeEnum = DisPduType.values()[pduType];
      rendererSB.setLength(0);
      rendererSB.append(index);
      rendererSB.append(' ');
      rendererSB.append(pduTypeEnum.toString());
      //rendererSB.append(' ');
      //rendererSB.append(pduTypeEnum.getDescription());

      rendererBB.clear();
      rendererBB.put(ba);
      long time = rendererBB.getLong(IDXFILE_RECDNANOTIME_IDX);
      double displayTime = time / 1000000000.;  // turn into seconds
      rendererSB.append(" ");
	  // display timestamp
	  DecimalFormat   formatPrecision3 = new DecimalFormat ("#0.000s");
      rendererSB.append(formatPrecision3.format(displayTime));
      return rendererSB.toString();
    }
    catch (Throwable ex) {
      System.err.println("Error decoding DIS packet in DISPlayerRecorderPanel.translateBytes() " + ex.getLocalizedMessage());
      return "";
    }
  }

  @SuppressWarnings("unchecked")
  private void displayPdu(Pdu pdu, Class<?> displayPanelClass)
  {
    try {
      if(displayPanelClass == null)
        throw new Exception(pdu.toString());
      
      Object displayPanel = null;
      Component c = displayPanelScroller.getViewport().getView();
      if (c != null && c.getClass().equals(displayPanelClass))
        displayPanel = c;
      if (displayPanel == null) {
        displayPanel = displayPanelClass.getDeclaredConstructor(displayPanelClass).newInstance();
        displayPanelScroller.setViewportView((Component) displayPanel);
        displayContainer.validate();
      }
      Method setDataMethod = displayPanelClass.getDeclaredMethod("setData", pdu.getClass());
      setDataMethod.invoke(displayPanel, pdu);
    }
    catch (Exception ex) {
      System.err.println("Error finding display panel: " + ex.getLocalizedMessage());
    }
  }

  private void displayByteArray(byte[] ba, int len)
  {
    ByteArrayDisplayPanel badp;
    Component current = displayPanelScroller.getViewport().getView();
    if(current != null && current instanceof ByteArrayDisplayPanel){
      badp = (ByteArrayDisplayPanel)current;
    }
    else {
      badp = new ByteArrayDisplayPanel();
      displayPanelScroller.setViewportView(badp);
      displayContainer.validate();     
    }
    badp.setData(ba,len);
  }
  
  @SuppressWarnings("unchecked")
  private void displayXml(Pdu pdu)
  {
    XmlDisplayPanel xmlDisplayPanel;
    Component currentPduDisplayPanel = displayPanelScroller.getViewport().getView();
    if(currentPduDisplayPanel != null && currentPduDisplayPanel instanceof XmlDisplayPanel){
      xmlDisplayPanel = (XmlDisplayPanel)currentPduDisplayPanel;
    }
    else {
      xmlDisplayPanel = new XmlDisplayPanel();
      displayPanelScroller.setViewportView(xmlDisplayPanel);
      displayContainer.validate();
    }

    String s;
    xmlDisplayPanel.setString("Marshalling...");
    try {
      StringWriter sw = new StringWriter();
      sw.getBuffer().append(pdu); // TODO: Rethink how to marshal PDUs to XML (String) form. Taking a "swag" here (tdn)
      s = sw.toString();
    }
    catch(Exception ex) {
      s = "Open-dis marshalling exception: " + ex.getLocalizedMessage();
      StringWriter sw = new StringWriter();
      ex.printStackTrace(new PrintWriter(sw));
      s += sw.toString();
    }
    xmlDisplayPanel.setString(s);
  }
  
  public boolean isDirty()
  {
    return jList1.getModel().getSize() > 0;
  }

  private void clearBuffers()
  {
    DefaultListModel dlm = (DefaultListModel) jList1.getModel();
    dlm.removeAllElements();

    // TODO clear PDU values, hex display, XML display

    Component currentPduDisplayPanel = displayPanelScroller.getViewport().getView();
    if (currentPduDisplayPanel == null)
        return;
    if(currentPduDisplayPanel instanceof ByteArrayDisplayPanel)
    {
        ByteArrayDisplayPanel badp = (ByteArrayDisplayPanel)currentPduDisplayPanel;
        byte [] emptyByteArray = {  };
        badp.setData(emptyByteArray, 0);
    }
    else if(currentPduDisplayPanel instanceof XmlDisplayPanel)
    {
        XmlDisplayPanel xmlDisplayPanel = (XmlDisplayPanel)currentPduDisplayPanel;
        xmlDisplayPanel.setString(""); // clear
    }
  }

  @Override
  public void senderStopped(String msg)
  {
    // We're not in swing thread here
    System.out.println("Sender stopped: "+msg);
    if(player != null)
      SwingUtilities.invokeLater(() -> {
          stateMachine.eventOccurs(PlayerEvent.RecordStopHit);
    });
  }

  @Override
  public void packetSent(DatagramPacket packet, int pktNum, boolean rev)
  {
    // We're not in swing thread here
    final int idx = pktNum;
    final boolean inReverse = rev;

    SwingUtilities.invokeLater(() -> {
        int nextToHighlight;
        
        if(inReverse) {
            nextToHighlight = idx - 1;
            if(nextToHighlight < 0)
                nextToHighlight = 0;
        }
        else {
            nextToHighlight = idx + 1;
            if (nextToHighlight >= jList1.getModel().getSize())
                nextToHighlight--;
        }

        jList1.setSelectedIndex(nextToHighlight);
        jList1.ensureIndexIsVisible(nextToHighlight);
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public void packetReceived(DISGrabber grabberInstance, int totalReceived)
  {
    // We're not in swing thread here
    final DISGrabber myGrabber = grabberInstance;
    SwingUtilities.invokeLater(() -> {
        DefaultListModel<byte[]> mod = (DefaultListModel<byte[]>) jList1.getModel();  // this cast is "unchecked"
        int startNum = mod.getSize();
        List<byte[]> pointerList = myGrabber.getPointerList();
        int delta = pointerList.size() - startNum;
        int idx = 0;
        for (int i = 0; i < delta; i++) {
            mod.insertElementAt(pointerList.get(startNum + i), startNum + i);
            idx = startNum + i;
        }
        //jList1.setSelectedIndex(idx);  if we do this, we load each panel on the right side
        jList1.ensureIndexIsVisible(idx);
    });
  }

  private File findFreeFile(File parent, File defFile)
  {
    int num = 1;
    String numStr = "";
    String nam = defFile.getName();
    if (nam.endsWith(DEFAULT_BINARY_FILE_ENDING))
      nam = nam.substring(0, nam.length() - DEFAULT_BINARY_FILE_ENDING.length());
    do {
      File f = new File(parent, nam + numStr + DEFAULT_BINARY_FILE_ENDING);
      if (!f.exists())
        return f;
      numStr = "" + num++;
    }
    while (true);

  //new File("dispackets" + DISGrabber.DEFAULT_BINARY_FILE_ENDING)); 
  }
  
  class disBinFilter extends FileFilter
  {

    @Override
    public boolean accept(File f)
    {
      if(f.isDirectory())
        return true;
      return f.getName().endsWith("disbin");
    }

    @Override
    public String getDescription()
    {
      return "DIS Player-Recorder binary data";
    }
    
  }
  private JFileChooser _fileChooser;

  private JFileChooser getChooser()   
  {
    if (_fileChooser == null) {
      _fileChooser = new JFileChooser();
      _fileChooser.setMultiSelectionEnabled(false);
      _fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      _fileChooser.addChoosableFileFilter(new disBinFilter());
    }
    return _fileChooser;   
  }
  
  /* return false if cancelled*/
  private boolean doSave()
  {
    // https://stackoverflow.com/questions/14407804/how-to-change-the-default-text-of-buttons-in-joptionpane-showinputdialog
	UIManager.put("OptionPane.yesButtonText","Save");
	UIManager.put("OptionPane.noButtonText", "Clear");
	int ret = JOptionPane.showConfirmDialog(this, "Save already-loaded data?", "Confirm...", JOptionPane.YES_NO_CANCEL_OPTION);
    UIManager.put("OptionPane.yesButtonText", "Yes");
	UIManager.put("OptionPane.noButtonText",  "No");
	
	if (ret == JOptionPane.CANCEL_OPTION)
      return false;
    if (ret == JOptionPane.YES_OPTION) {
      JFileChooser chooser = getChooser();
      chooser.setSelectedFile(findFreeFile(chooser.getCurrentDirectory(), new File("dispackets" + DEFAULT_BINARY_FILE_ENDING)));

      ret = chooser.showSaveDialog(this);
      if (ret == JFileChooser.CANCEL_OPTION)
        return false;
      try {
        saveFiles(chooser.getSelectedFile());
      }
      catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error saving files: " + ex.getLocalizedMessage());
        return false;
      }
    }
    return true;
  }
  private ByteBuffer bb = ByteBuffer.allocate(NUMBYTESINLONG);

  private void buildIndexFile(File dataF, File idxF) throws Exception
  {
      BufferedOutputStream idxOutStr;
      try (BufferedInputStream dataStr = new BufferedInputStream(new FileInputStream(dataF))) {
          idxOutStr = new BufferedOutputStream(new FileOutputStream(idxF));
          int nRead;
          long dataFilePtr = 0;
          byte[] dbuf = new byte[PDU_HEADER_LENGTH];
          byte[] ptrByteArray = new byte[IDXFILE_RECORD_SIZE];
          do {
              nRead = dataStr.read(dbuf);
              if (nRead == -1)
                  break;  // eof
              if (nRead != PDU_HEADER_LENGTH) {
                  throw new Exception("Truncated data file");
              }
              
              Arrays.fill(ptrByteArray, (byte)0);
              
              // write the first 3 bytes of hdr record
              int idx = IDXFILE_PDUHDR_IDX;
              ptrByteArray[idx + 0] = dbuf[0];  // save the 3 bytes which when decoded indicate packet type
              ptrByteArray[idx + 1] = dbuf[1];
              ptrByteArray[idx + 2] = dbuf[2];
              
              // write the position in the index file
              idx = IDXFILE_FILEPTR_IDX;
              bb.putLong(0, dataFilePtr);
              for (int i = 0; i < NUMBYTESINLONG; i++) {
                  ptrByteArray[idx++] = bb.array()[i];
              }
              
              // Fill the timestamp field
              idx = IDXFILE_RECDNANOTIME_IDX;
              bb.putLong(0, 0l);   // don't have any time info if original idx gone
              for (int i = 0; i < NUMBYTESINLONG; i++) {
                  ptrByteArray[idx++] = bb.array()[i];
              }
              
              // Fill the record size field
              idx = IDXFILE_RECORDSIZE_IDX;
              bb.clear();
              bb.put(0, dbuf[PDU_LENGTH_FIELD_OFFSET]);
              bb.put(1, dbuf[PDU_LENGTH_FIELD_OFFSET + 1]);
              int len2 = bb.getShort(0);
              
              if(len2 <= 0)   // no length in packet
                  throw new Exception("Length field not parsed in DIS packet.");
              bb.putInt(0, len2);
              for (int i = 0; i < NUMBYTESININT; i++) {
                  ptrByteArray[idx++] = bb.array()[i];
              }
              
              idxOutStr.write(ptrByteArray);
              // skip to next record in data file;
              int rest = len2-nRead;
              while (rest >0) {
                  long skipped = dataStr.skip(rest);  // see javadoc, skip method not guarenteed to skip all requested
                  rest -= skipped;
              }
              
              dataFilePtr += len2;
              
          } while (true);
      }
    idxOutStr.close();
  }

  public void doLoad()
  {
    if (isDirty()) {
      if (!doSave())
        return;
    }
    JFileChooser chooser = getChooser();
    int ret = chooser.showOpenDialog(this);
    if (ret == JFileChooser.CANCEL_OPTION)
      return;
    File f = chooser.getSelectedFile();
    File idxF = new File(f.getParentFile(), f.getName() + DEFAULT_BINARY_INDEX_FILE_ENDING);

    if (!idxF.exists() /*|| (Math.abs(f.lastModified()-idxF.lastModified())>1000l)*/ ) {  // match w/in a second?
      try {
        buildIndexFile(f,idxF);
      }
      catch(Exception ex) {
        JOptionPane.showMessageDialog(this, "Error building index file: " + ex.getLocalizedMessage());
        return;
      }
    }
    clearBuffers();

    loadItUp(f, idxF);
    
    stateMachine.eventOccurs(PlayerEvent.RecordStopHit);  // resets the buttons
    beginningButt.doClick();  // move to first
  }
  
  @SuppressWarnings("unchecked")
  private void loadItUp(File data, File idxF)
  {
    try {
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(idxF));
      DefaultListModel<byte[]> mod = (DefaultListModel<byte[]>) jList1.getModel(); // this cast is "unchecked"
      int ret;
      do {
        byte[] buf = new byte[IDXFILE_RECORD_SIZE];
        ret = bis.read(buf);
        if (ret != IDXFILE_RECORD_SIZE)
          break;
        mod.addElement(buf);
        jList1.ensureIndexIsVisible(mod.getSize() - 1);
      }
      while (true);
    }
    catch (IOException ex) {
      System.err.println("bad load: " + ex.getLocalizedMessage());
      return;
    }
    setCaptureFileSet(new File[]{data, idxF});
  }
  
  InetAddress getNetAddress()
  {
    try {
      return InetAddress.getByName(addressTF.getText().trim());
    }
    catch (UnknownHostException ex) {
      JOptionPane.showMessageDialog(this, "Bad parse of port integer, using default: " + DISGrabber.DEFAULT_PORT,
          "Format Error", JOptionPane.ERROR_MESSAGE);
      try {
        return InetAddress.getByName(DISGrabber.DEFAULT_ADDRESS);
      }
      catch (UnknownHostException ex2) {
        System.err.println("Program error in DISPlayerRecorder.getNetAddress() " + ex2.getLocalizedMessage());
        return null;
      }
    }
  }
  
  int getNetPort()
  {
    try {
      int i = (new SFInt32(portTF.getText()).getValue());
      return i;
    }
    catch(Throwable t) {
      JOptionPane.showMessageDialog(this, "Bad parse of port integer, using default: "+DISGrabber.DEFAULT_PORT, 
                                    "Format Error", JOptionPane.ERROR_MESSAGE);
      return DISGrabber.DEFAULT_PORT;
    }
  }

  /** action methods called by state machine.  Do not re-enter stateMachine in these methods. **/
  private Border defaultRecordBorder;
  private Border hilightRecordBorder;
  private Border defaultPlayBorder;
  private Border hilightPlayBorder;
  private Color mutedRed   = new Color(166, 75, 74);
  private Color mutedGreen = new Color(75, 166, 75);

  private void buildBorders()
  {
    // Stick an innerborder around the toolbars
    defaultRecordBorder = new CompoundBorder(recordToolbar.getBorder(),new EmptyBorder(1,1,1,1));
    hilightRecordBorder = new CompoundBorder(recordToolbar.getBorder(),new LineBorder(mutedRed,1));
    defaultPlayBorder   = new CompoundBorder(playToolBar.  getBorder(),new EmptyBorder(1,1,1,1));
    hilightPlayBorder   = new CompoundBorder(playToolBar.  getBorder(),new LineBorder(mutedGreen,1));

    // got to make the others the same size:
    beginToolbar.setBorder(new CompoundBorder(beginToolbar.getBorder(),new EmptyBorder(1,1,1,1)));
    endToolbar.setBorder  (new CompoundBorder(endToolbar.getBorder(),new EmptyBorder(1,1,1,1)));
  }

  public void showPlayHilightedState(boolean yn)
  {
    if(hilightPlayBorder == null)
      buildBorders();
    playToolBar.setBorder(yn?hilightPlayBorder:defaultPlayBorder);
  }

  public void showRecordHilightedState(boolean yn)
  {
    if(hilightRecordBorder == null)
      buildBorders();
    recordToolbar.setBorder(yn?hilightRecordBorder:defaultRecordBorder);
  }
  
  public void doStop()
  {
    if (grabber != null && grabber.isRunning())
      grabber.stop();
    if (player != null && player.isRunning()){
      DISPlayer lPlayer = player;  // prevent reentry
      player=null;
      lPlayer.stop();
    }
  }

  public void doRecord(boolean continuing)
  {
    if (continuing)
      grabber.resume();
    else {
      if (isDirty()) {
        if (!doSave())
          return;
        clearBuffers();
      }
      grabber = new DISGrabber();
      grabber.setMulticastGroup(getNetAddress());
      grabber.setPort(getNetPort());
      grabber.addReceivedListener(this);
      grabber.start();
      setCaptureFileSet(grabber.getSaveFileSet());
    //System.out.println("grabber file: "+grabber.getSaveFile().getAbsolutePath());
    }
  }

  public void doPauseRecording()
  {
    grabber.pause();
  }
  
  public void doResumeRecording()
  {
    grabber.resume();
  }
  
  public void doSingleStep()
  {
    if(player != null && player.isRunning()){
        player.setFastPlay(false);

        // First set the multicast address
        player.setMulticastGroup(getNetAddress());
        player.setPort(getNetPort());

        player.resume();
    }
    else
     _doPlay(false,false,1);

  }

  public void doReverseSingleStep()
  {
    if(player != null && player.isRunning()){
        player.setFastPlay(false);

        // First set the multicast address
        player.setMulticastGroup(getNetAddress());
        player.setPort(getNetPort());
        player.setLoop(isLoop());
        player.resume();
    }
    else
     _doPlay(false,true,1);
  }

  public void doPlay()
  {
    if(player != null && player.isRunning()){
        player.setFastPlay(false);

        // First set the multicast address
        player.setMulticastGroup(getNetAddress());
        player.setPort(getNetPort());
        player.setLoop(isLoop());
        player.resume();
    }
    else
     _doPlay(false,false,null);
  }
  
  public void doPlayFast()
  {
    if(player != null && player.isRunning()) {
        player.setFastPlay(true);
        
        // First set the multicast address
        player.setMulticastGroup(getNetAddress());
        player.setPort(getNetPort());
        player.setLoop(isLoop());
        player.resume();
    }
    else
    _doPlay(true,false,null);
  }

  public void doReversePlayFast()
  {
    if(player != null && player.isRunning()) {
        player.setFastPlay(true);

        // First set the multicast address
        player.setMulticastGroup(getNetAddress());
        player.setPort(getNetPort());
        player.setLoop(isLoop());
        player.resume();
    }
    else
    _doPlay(true,true,null);
  }

  public void doReversePlay()
  { 
   _doPlay(false,true,null);
  }
 
  private void _doPlay(boolean fast, boolean reverse, Integer pduCount)
  {
      File[] fa = getCaptureFileSet();
      if (fa == null || fa[0] == null || fa[1] == null) {
          return;
      }

      player = new DISPlayer(fa);
      player.addStoppedtListener(this);
      player.addSentListener(this);
//      player.setFastPlay(fast); // no longer supported
      player.setReversePlay(reverse);
      player.setLoop(isLoop());

      // First set the multicast address
      player.setMulticastGroup(getNetAddress());
      player.setPort(getNetPort());
      player.setCount(pduCount);

      int startingPacket = jList1.getSelectedIndex();
      int sz = jList1.getModel().getSize();
      if (!reverse) {
          player.start(startingPacket >= 0 ? startingPacket : 0);
      } else {
          player.start(startingPacket >= 0 ? startingPacket : sz - 1);
      }
  }

  public void doPause()
  {
    player.pause();
  }

  public void doBeginning()
  {
    jList1.setSelectedIndex(0);
    jList1.ensureIndexIsVisible(0);
  }

  public void doEnd()
  {
    int sz = jList1.getModel().getSize();
    if(sz >= 1) {
      jList1.setSelectedIndex(sz-1);
      jList1.ensureIndexIsVisible(sz-1);
    }
  }



  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  PduFactory pduFact = new PduFactory();
  byte[] packet = new byte[MAX_PDU_SIZE];
  
 @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        formattedRawBG = new javax.swing.ButtonGroup();
        selectionPanel = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        listScroller = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        displayContainer = new javax.swing.JPanel();
        displayPanelScroller = new javax.swing.JScrollPane();
        netParamPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addressTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        portTF = new javax.swing.JTextField();
        displayModeChoicePanel = new javax.swing.JPanel();
        displayModeLabel = new javax.swing.JLabel();
        textModeButton = new javax.swing.JRadioButton();
        formattedPduButton = new javax.swing.JRadioButton();
        rawHexBytesButton = new javax.swing.JRadioButton();
        toolbarPanel = new javax.swing.JPanel();
        operationStatusjToolBar = new javax.swing.JToolBar();
        operationStatusPanel = new javax.swing.JPanel();
        operationModeLabel = new javax.swing.JLabel();
        playbackStateTF = new javax.swing.JLabel();
        recordToolbar = new javax.swing.JToolBar();
        recordButt = new javax.swing.JButton();
        recordPauseButt = new javax.swing.JButton();
        recordStopButt = new javax.swing.JButton();
        saveButt = new javax.swing.JButton();
        loadButt = new javax.swing.JButton();
        vcrPanel = new javax.swing.JPanel();
        beginToolbar = new javax.swing.JToolBar();
        beginningButt = new javax.swing.JButton();
        fastReverseButt = new javax.swing.JButton();
        reverseStepButt = new javax.swing.JButton();
        playToolBar = new javax.swing.JToolBar();
        reversePlayButt = new javax.swing.JButton();
        pauseButt = new javax.swing.JButton();
        playButt = new javax.swing.JButton();
        endToolbar = new javax.swing.JToolBar();
        stepButt = new javax.swing.JButton();
        ffButt = new javax.swing.JButton();
        endButt = new javax.swing.JButton();
        loopToolBar = new javax.swing.JToolBar();
        loopToggleButt = new javax.swing.JToggleButton();

        setLayout(new java.awt.BorderLayout());

        selectionPanel.setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setDividerLocation(250);

        jList1.setModel(new DefaultListModel());
        jList1.setCellRenderer(new ByteArrayListRenderer());
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        listScroller.setViewportView(jList1);

        jSplitPane1.setLeftComponent(listScroller);

        displayContainer.setLayout(new java.awt.BorderLayout());
        displayContainer.add(displayPanelScroller, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(displayContainer);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        selectionPanel.add(jSplitPane1, gridBagConstraints);

        netParamPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.jLabel1.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        netParamPanel.add(jLabel1, gridBagConstraints);

        addressTF.setText(X3dOptions.getDISaddress(org.web3d.x3d.dis.DisEspduSenderControlPanel.DEFAULT_DIS_ADDRESS));
        addressTF.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.addressTF.toolTipText")); // NOI18N
        addressTF.setMinimumSize(new java.awt.Dimension(114, 22));
        addressTF.setPreferredSize(new java.awt.Dimension(114, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        netParamPanel.add(addressTF, gridBagConstraints);

        jLabel2.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.jLabel2.text")); // NOI18N
        jLabel2.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.portTF.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        netParamPanel.add(jLabel2, gridBagConstraints);

        portTF.setText(X3dOptions.getDISport(""+org.web3d.x3d.dis.DisEspduSenderControlPanel.DEFAULT_PORT));
        portTF.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.portTF.toolTipText")); // NOI18N
        portTF.setMinimumSize(new java.awt.Dimension(46, 22));
        portTF.setPreferredSize(new java.awt.Dimension(46, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        netParamPanel.add(portTF, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        selectionPanel.add(netParamPanel, gridBagConstraints);

        displayModeChoicePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        displayModeChoicePanel.setMinimumSize(new java.awt.Dimension(420, 31));
        displayModeChoicePanel.setPreferredSize(new java.awt.Dimension(420, 31));
        displayModeChoicePanel.setLayout(new java.awt.GridBagLayout());

        displayModeLabel.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.displayModeLabel.text")); // NOI18N
        displayModeLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.displayModeLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        displayModeChoicePanel.add(displayModeLabel, gridBagConstraints);

        formattedRawBG.add(textModeButton);
        textModeButton.setSelected(true);
        textModeButton.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.textModeButton.text")); // NOI18N
        textModeButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.textModeButton.toolTipText")); // NOI18N
        textModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formattedRawHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        displayModeChoicePanel.add(textModeButton, gridBagConstraints);

        formattedRawBG.add(formattedPduButton);
        formattedPduButton.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.formattedPduButton.text")); // NOI18N
        formattedPduButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.formattedPduButton.toolTipText")); // NOI18N
        formattedPduButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formattedRawHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        displayModeChoicePanel.add(formattedPduButton, gridBagConstraints);

        formattedRawBG.add(rawHexBytesButton);
        rawHexBytesButton.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.rawHexBytesButton.text")); // NOI18N
        rawHexBytesButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.rawHexBytesButton.toolTipText")); // NOI18N
        rawHexBytesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formattedRawHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        displayModeChoicePanel.add(rawHexBytesButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        selectionPanel.add(displayModeChoicePanel, gridBagConstraints);

        add(selectionPanel, java.awt.BorderLayout.CENTER);

        toolbarPanel.setLayout(new java.awt.GridBagLayout());

        operationStatusjToolBar.setRollover(true);

        operationStatusPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        operationStatusPanel.setLayout(new java.awt.GridBagLayout());

        operationModeLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        operationModeLabel.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.operationModeLabel.text")); // NOI18N
        operationModeLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.operationModeLabel.toolTipText")); // NOI18N
        operationStatusPanel.add(operationModeLabel, new java.awt.GridBagConstraints());

        playbackStateTF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playbackStateTF.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.playbackStateTF.text")); // NOI18N
        playbackStateTF.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.disabledText")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 6);
        operationStatusPanel.add(playbackStateTF, gridBagConstraints);

        operationStatusjToolBar.add(operationStatusPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        toolbarPanel.add(operationStatusjToolBar, gridBagConstraints);

        recordToolbar.setRollover(true);

        recordButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/RecordNormal.png"))); // NOI18N
        recordButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordButt.text")); // NOI18N
        recordButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordButt.toolTipText")); // NOI18N
        recordButt.setFocusable(false);
        recordButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recordButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recordButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordButtActionPerformed(evt);
            }
        });
        recordToolbar.add(recordButt);

        recordPauseButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/PauseRed24.gif"))); // NOI18N
        recordPauseButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordPauseButt.text")); // NOI18N
        recordPauseButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordPauseButt.toolTipText")); // NOI18N
        recordPauseButt.setActionCommand(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordPauseButt.actionCommand")); // NOI18N
        recordPauseButt.setFocusable(false);
        recordPauseButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recordPauseButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recordPauseButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordPauseButtActionPerformed(evt);
            }
        });
        recordToolbar.add(recordPauseButt);

        recordStopButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/StopRed24.png"))); // NOI18N
        recordStopButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordStopButt.text")); // NOI18N
        recordStopButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordStopButt.toolTipText")); // NOI18N
        recordStopButt.setActionCommand(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordStopButt.actionCommand")); // NOI18N
        recordStopButt.setFocusable(false);
        recordStopButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recordStopButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recordStopButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordStopButtActionPerformed(evt);
            }
        });
        recordToolbar.add(recordStopButt);

        saveButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Save24.gif"))); // NOI18N
        saveButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.saveButt.text")); // NOI18N
        saveButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.saveButt.toolTipText")); // NOI18N
        saveButt.setFocusable(false);
        saveButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtActionPerformed(evt);
            }
        });
        recordToolbar.add(saveButt);

        loadButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Open24.gif"))); // NOI18N
        loadButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.loadButt.text")); // NOI18N
        loadButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.loadButt.toolTipText")); // NOI18N
        loadButt.setFocusable(false);
        loadButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtActionPerformed(evt);
            }
        });
        recordToolbar.add(loadButt);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        toolbarPanel.add(recordToolbar, gridBagConstraints);

        beginToolbar.setRollover(true);

        beginningButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Beginning24.png"))); // NOI18N
        beginningButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.beginningButt.text")); // NOI18N
        beginningButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.beginningButt.toolTipText")); // NOI18N
        beginningButt.setFocusable(false);
        beginningButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        beginningButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        beginningButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beginningButtActionPerformed(evt);
            }
        });
        beginToolbar.add(beginningButt);

        fastReverseButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Rewind24.gif"))); // NOI18N
        fastReverseButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.fastReverseButt.text")); // NOI18N
        fastReverseButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.fastReverseButt.toolTipText")); // NOI18N
        fastReverseButt.setFocusable(false);
        fastReverseButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fastReverseButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fastReverseButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fastReverseButtActionPerformed(evt);
            }
        });
        beginToolbar.add(fastReverseButt);

        reverseStepButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/StepBack24.gif"))); // NOI18N
        reverseStepButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.reverseStepButt.text")); // NOI18N
        reverseStepButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.reverseStepButt.toolTipText")); // NOI18N
        reverseStepButt.setFocusable(false);
        reverseStepButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reverseStepButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        reverseStepButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reverseStepButtActionPerformed(evt);
            }
        });
        beginToolbar.add(reverseStepButt);

        vcrPanel.add(beginToolbar);

        playToolBar.setRollover(true);

        reversePlayButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/ReversePlay24.png"))); // NOI18N
        reversePlayButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.reversePlayButt.text")); // NOI18N
        reversePlayButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.reversePlayButt.toolTipText")); // NOI18N
        reversePlayButt.setFocusable(false);
        reversePlayButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reversePlayButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        reversePlayButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reversePlayButtActionPerformed(evt);
            }
        });
        playToolBar.add(reversePlayButt);

        pauseButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Pause24.gif"))); // NOI18N
        pauseButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.pauseButt.text")); // NOI18N
        pauseButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.pauseButt.toolTipText")); // NOI18N
        pauseButt.setFocusable(false);
        pauseButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pauseButt.setMaximumSize(new java.awt.Dimension(43, 51));
        pauseButt.setMinimumSize(new java.awt.Dimension(43, 51));
        pauseButt.setPreferredSize(new java.awt.Dimension(43, 51));
        pauseButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pauseButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtActionPerformed(evt);
            }
        });
        playToolBar.add(pauseButt);

        playButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Play24.gif"))); // NOI18N
        playButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.playButt.text")); // NOI18N
        playButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.playButt.toolTipText")); // NOI18N
        playButt.setFocusable(false);
        playButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButt.setMaximumSize(new java.awt.Dimension(43, 51));
        playButt.setMinimumSize(new java.awt.Dimension(43, 51));
        playButt.setPreferredSize(new java.awt.Dimension(43, 51));
        playButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        playButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtActionPerformed(evt);
            }
        });
        playToolBar.add(playButt);

        vcrPanel.add(playToolBar);

        endToolbar.setRollover(true);

        stepButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/StepForward24.gif"))); // NOI18N
        stepButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.stepButt.text")); // NOI18N
        stepButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.stepButt.toolTipText")); // NOI18N
        stepButt.setFocusable(false);
        stepButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stepButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        stepButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtActionPerformed(evt);
            }
        });
        endToolbar.add(stepButt);

        ffButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/FastForward24.gif"))); // NOI18N
        ffButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.ffButt.text")); // NOI18N
        ffButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.ffButt.toolTipText")); // NOI18N
        ffButt.setFocusable(false);
        ffButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ffButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ffButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ffButtActionPerformed(evt);
            }
        });
        endToolbar.add(ffButt);

        endButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/ResetEnd.png"))); // NOI18N
        endButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.endButt.text")); // NOI18N
        endButt.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.endButt.toolTipText")); // NOI18N
        endButt.setFocusable(false);
        endButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        endButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        endButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtActionPerformed(evt);
            }
        });
        endToolbar.add(endButt);

        vcrPanel.add(endToolbar);

        loopToolBar.setRollover(true);

        loopToggleButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Refresh24.png"))); // NOI18N
        loopToggleButt.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.loopToggleButt.text")); // NOI18N
        loopToggleButt.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.loopToggleButt.toolTipText")); // NOI18N
        loopToggleButt.setFocusable(false);
        loopToggleButt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loopToggleButt.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loopToggleButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loopToggleButtActionPerformed(evt);
            }
        });
        loopToolBar.add(loopToggleButt);

        vcrPanel.add(loopToolBar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        toolbarPanel.add(vcrPanel, gridBagConstraints);

        add(toolbarPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

  if (evt != null && evt.getValueIsAdjusting())
    return;

  byte[] ba = (byte[]) jList1.getSelectedValue();
  if (ba == null)
    return;

  // build a long from bytes3-11
  ByteBuffer byteBuffer = ByteBuffer.allocate(IDXFILE_FILEPTR_IDX_SIZE);
  byteBuffer.put(ba, IDXFILE_FILEPTR_IDX, IDXFILE_FILEPTR_IDX_SIZE);
  long filePtr = byteBuffer.getLong(0);
  // go there and read the data, just getting the largest hunk
  int numRead;
  try {
    randFile.seek(filePtr);
    numRead = randFile.read(packet);
  }
  catch (IOException ex) {
    System.err.println("Bad pdu build: " + ex.getLocalizedMessage());
    return;
  }
  byteBuffer.clear();
  byteBuffer.put(ba, IDXFILE_RECORDSIZE_IDX, IDXFILE_RECORDSIZE_SIZE);
  int sz = byteBuffer.getInt(0);

  if(formattedPduButton.isSelected() || textModeButton.isSelected()) {
    Pdu pdu = pduFact.createPdu(packet);
    if(pdu != null) {
      Class<?> displayClass = displayMap.get(pdu.getClass());
      if(displayClass != null) {
        if(formattedPduButton.isSelected())
          displayPdu(pdu,displayMap.get(pdu.getClass()));
        else if(textModeButton.isSelected())
          displayXml(pdu);
      }
    }
  }
  else if(rawHexBytesButton.isSelected()) {
    displayByteArray(packet,sz);
  }
}//GEN-LAST:event_jList1ValueChanged

private void loadButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtActionPerformed
  doLoad();
}//GEN-LAST:event_loadButtActionPerformed

private void saveButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtActionPerformed
  doSave();
}//GEN-LAST:event_saveButtActionPerformed

private void beginningButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beginningButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.BeginHit);
}//GEN-LAST:event_beginningButtActionPerformed

private void fastReverseButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_fastReverseButtActionPerformed
{//GEN-HEADEREND:event_fastReverseButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.ReversePlayHit);
}//GEN-LAST:event_fastReverseButtActionPerformed

private void recordButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recordButtActionPerformed
{//GEN-HEADEREND:event_recordButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.RecordHit);
}//GEN-LAST:event_recordButtActionPerformed

private void pauseButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pauseButtActionPerformed
{//GEN-HEADEREND:event_pauseButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.PauseHit);
}//GEN-LAST:event_pauseButtActionPerformed

private void playButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_playButtActionPerformed
{//GEN-HEADEREND:event_playButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.PlayHit);
}//GEN-LAST:event_playButtActionPerformed

private void ffButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ffButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.FastForwardHit);
}//GEN-LAST:event_ffButtActionPerformed

private void endButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.EndHit);
}//GEN-LAST:event_endButtActionPerformed

private void formattedRawHandler(java.awt.event.ActionEvent evt)//GEN-FIRST:event_formattedRawHandler
{//GEN-HEADEREND:event_formattedRawHandler
  this.jList1ValueChanged(null);
}//GEN-LAST:event_formattedRawHandler

private void recordStopButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recordStopButtActionPerformed
{//GEN-HEADEREND:event_recordStopButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.RecordStopHit);
}//GEN-LAST:event_recordStopButtActionPerformed

private void recordPauseButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recordPauseButtActionPerformed
{//GEN-HEADEREND:event_recordPauseButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.RecordPauseHit);
}//GEN-LAST:event_recordPauseButtActionPerformed

private void reverseStepButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_reverseStepButtActionPerformed
{//GEN-HEADEREND:event_reverseStepButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.ReverseStepHit);
}//GEN-LAST:event_reverseStepButtActionPerformed

private void reversePlayButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_reversePlayButtActionPerformed
{//GEN-HEADEREND:event_reversePlayButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.ReversePlayHit);
}//GEN-LAST:event_reversePlayButtActionPerformed

private void stepButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stepButtActionPerformed
{//GEN-HEADEREND:event_stepButtActionPerformed
  stateMachine.eventOccurs(PlayerEvent.StepHit);
}//GEN-LAST:event_stepButtActionPerformed

private boolean isLoop()
{
  return loopToggleButt.isSelected();
}

private void loopToggleButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_loopToggleButtActionPerformed
{//GEN-HEADEREND:event_loopToggleButtActionPerformed
  if(player != null && player.isRunning())
    player.setLoop(isLoop());
}//GEN-LAST:event_loopToggleButtActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTF;
    private javax.swing.JToolBar beginToolbar;
    public javax.swing.JButton beginningButt;
    private javax.swing.JPanel displayContainer;
    private javax.swing.JPanel displayModeChoicePanel;
    private javax.swing.JLabel displayModeLabel;
    private javax.swing.JScrollPane displayPanelScroller;
    public javax.swing.JButton endButt;
    private javax.swing.JToolBar endToolbar;
    public javax.swing.JButton fastReverseButt;
    public javax.swing.JButton ffButt;
    private javax.swing.JRadioButton formattedPduButton;
    private javax.swing.ButtonGroup formattedRawBG;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JScrollPane listScroller;
    public javax.swing.JButton loadButt;
    public javax.swing.JToggleButton loopToggleButt;
    private javax.swing.JToolBar loopToolBar;
    private javax.swing.JPanel netParamPanel;
    private javax.swing.JLabel operationModeLabel;
    private javax.swing.JPanel operationStatusPanel;
    private javax.swing.JToolBar operationStatusjToolBar;
    public javax.swing.JButton pauseButt;
    public javax.swing.JButton playButt;
    private javax.swing.JToolBar playToolBar;
    public javax.swing.JLabel playbackStateTF;
    private javax.swing.JTextField portTF;
    private javax.swing.JRadioButton rawHexBytesButton;
    public javax.swing.JButton recordButt;
    public javax.swing.JButton recordPauseButt;
    public javax.swing.JButton recordStopButt;
    private javax.swing.JToolBar recordToolbar;
    public javax.swing.JButton reversePlayButt;
    public javax.swing.JButton reverseStepButt;
    public javax.swing.JButton saveButt;
    private javax.swing.JPanel selectionPanel;
    public javax.swing.JButton stepButt;
    private javax.swing.JRadioButton textModeButton;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JPanel vcrPanel;
    // End of variables declaration//GEN-END:variables

  /*
  public RecordButt recordButt;
  public PlayButt playButt;
  public PauseButt pauseButt;

  public static class RecordButt extends BorderedButt
  {
    public RecordButt()
    {
      super(new Color(166, 75, 74),"Recording","Record"); // muted red
    }
  }

  public static class PlayButt extends BorderedButt
  {
    public PlayButt()
    {
      super(new Color(75, 166, 75),"Playing","Play"); // green
    }
  }

  public static class PauseButt extends BorderedButt
  {
    public PauseButt()
    {
      super(new Color(75, 166, 75),"Pause","Pause"); //green
    }
  }
  public static class BorderedButt extends JButton
  {
    protected Color myColor;
    private Border origBorder;
    private Insets origInsets;
    private boolean origBorderSet = false;

    private String activeText;
    private String inactiveText;

    public BorderedButt(Color c, String activeText, String inactiveText)
    {
      myColor = c;
      this.activeText = activeText;
      this.inactiveText = inactiveText;
    }

    public void setActiveState(boolean state)
    {
      if (!origBorderSet) {
        origBorder = getBorder();
        if(origBorder != null)
          origInsets = origBorder.getBorderInsets(this);
        origBorderSet = true;
      }

      if (state == true) {
        if(activeText != null)
          setText(activeText);
        if (origBorder != null)
          setBorder(new LineBorder(myColor, origInsets.left, true));      // match at least the width      
        else
          setBorder(new LineBorder(myColor, 1, true));
      }
      else {
        if(inactiveText != null)
          setText(inactiveText);
        setBorder(origBorder);
      }
    }
  }
   */
}
