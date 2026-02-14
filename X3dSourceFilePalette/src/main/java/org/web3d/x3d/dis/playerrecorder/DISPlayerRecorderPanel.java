/*
Copyright (c) 1995-2026 held by the author(s).  All rights reserved.

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
      (https://www.nps.edu and https://MovesInstitute.nps.edu)
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
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;
import static org.web3d.x3d.dis.playerrecorder.DISGrabber.*;
import org.web3d.x3d.dis.playerrecorder.DISGrabber.DISReceivedListener;
import org.web3d.x3d.dis.playerrecorder.DISPlayer.DISSenderStoppedListener;
import org.web3d.x3d.dis.playerrecorder.DISPlayer.DISSentListener;
import org.web3d.x3d.dis.playerrecorder.DISPlayerRecorderStateMachine.PlayerEvent;
import org.web3d.x3d.options.X3dEditUserPreferences;
import static org.web3d.x3d.options.X3dEditUserPreferencesPanel.browserLaunch;
import static org.web3d.x3d.options.X3dEditUserPreferencesPanel.externalProcessLaunch;
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
    
    // TODO place icon
    
    stateMachine = new DISPlayerRecorderStateMachine(this);
    // all three sizes of play and pause butts have been manually set in matisse to == the largest of the three, the rev play butt
    // this is to make them not move around when one has been made invisible
    
//    textModeButton.setSelected(true); // TODO how to configure via interface?
 }
  
  File[] captureFileSet;
  File captureFile;
  RandomAccessFile randomAccessFile;

  private void setCaptureFileSet(File[] fa)
  {
    captureFileSet = fa;
    captureFile = fa[0];
    try {
      randomAccessFile = new RandomAccessFile(fa[0], "r");
    }
    catch (FileNotFoundException ex) {
      System.err.println("Can't make ra file from " + fa[0].getAbsolutePath() + " : " + ex.getLocalizedMessage());
      randomAccessFile = null;
    }
  }
  private File[] getCaptureFileSet()
  {
    return captureFileSet;
  }
  private boolean saveFiles(File selectedFile) throws Exception
  {
    FileIO.copyFile(new FileInputStream(captureFileSet[0]), new FileOutputStream(selectedFile),true);
    File indexFile = new File(selectedFile.getParent(), selectedFile.getName() + DISGrabber.DEFAULT_BINARY_INDEX_FILE_ENDING);
    FileIO.copyFile(new FileInputStream(captureFileSet[1]), new FileOutputStream(indexFile), true);
    return (indexFile.exists());
  }

  class ByteArrayListRenderer extends DefaultListCellRenderer
  {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
      Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      if (c instanceof JLabel && value instanceof byte[]) {
        // note internal list index is zero-based but output string is one-based
        String s = translateBytes((byte[]) value, index+1);
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
      rendererSB.append(". ");
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
      if (displayPanel == null) 
      {
        // choose the correct PDU display class
//      displayPanel = displayPanelClass.getDeclaredConstructor(displayPanelClass).newInstance(); // TODO not working
        // https://stackoverflow.com/questions/46393863/what-to-use-instead-of-class-newinstance
        displayPanel = displayPanelClass.getDeclaredConstructor().newInstance(); // fix deprecation
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
  
  public boolean hasPDUs()
  {
    return pduList.getModel().getSize() > 0;
  }

  private void clearPduBuffers()
  {
    DefaultListModel dlm = (DefaultListModel) pduList.getModel();
    dlm.removeAllElements();

    // TODO clear PDU values, hex display, XML display

    Component currentPduDisplayPanel = displayPanelScroller.getViewport().getView();
    if (currentPduDisplayPanel == null)
        return;
    if (currentPduDisplayPanel instanceof ByteArrayDisplayPanel)
    {
        ByteArrayDisplayPanel badp = (ByteArrayDisplayPanel)currentPduDisplayPanel;
        byte [] emptyByteArray = {  };
        badp.setData(emptyByteArray, 0);
    }
    else if (currentPduDisplayPanel instanceof XmlDisplayPanel)
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
            if (nextToHighlight >= pduList.getModel().getSize())
                nextToHighlight--;
        }

        pduList.setSelectedIndex(nextToHighlight);
        pduList.ensureIndexIsVisible(nextToHighlight);
    });
  }

  @Override
  @SuppressWarnings("unchecked")
  public void packetReceived(DISGrabber grabberInstance, int totalReceived)
  {
    // We're not in swing thread here
    final DISGrabber myGrabber = grabberInstance;
    SwingUtilities.invokeLater(() -> {
        DefaultListModel<byte[]> mod = (DefaultListModel<byte[]>) pduList.getModel();  // this cast is "unchecked"
        int startNum = mod.getSize();
        List<byte[]> pointerList = myGrabber.getPointerList();
        int delta = pointerList.size() - startNum;
        int idx = 0;
        for (int i = 0; i < delta; i++) {
            mod.insertElementAt(pointerList.get(startNum + i), startNum + i);
            idx = startNum + i;
        }
        //jList1.setSelectedIndex(idx);  if we do this, we load each panel on the right side
        pduList.ensureIndexIsVisible(idx);
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

  private JFileChooser getPduFileChooser()   
  {
    if (_fileChooser == null) {
      _fileChooser = new JFileChooser();
      _fileChooser.setMultiSelectionEnabled(false);
      _fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      _fileChooser.addChoosableFileFilter(new disBinFilter());
      _fileChooser.setFileFilter(new disBinFilter());
    }
    return _fileChooser;   
  }
  
  /** return false if user cancels */
  private boolean savePduListToFile(boolean deletePduListWhenDone)
  {
    // https://stackoverflow.com/questions/14407804/how-to-change-the-default-text-of-buttons-in-joptionpane-showinputdialog
	UIManager.put("OptionPane.yesButtonText",    "Save");
	UIManager.put("OptionPane.noButtonText",     "Delete All");
	UIManager.put("OptionPane.cancelButtonText", "Continue");
//  UIManager.put("OptionPane.yesButtonText", "Yes");
//	UIManager.put("OptionPane.noButtonText",  "No");
	int returnValue = JOptionPane.showConfirmDialog(this, "Save PDU list before deleting all PDUs?", "Confirm...", JOptionPane.YES_NO_CANCEL_OPTION);
	
	if (returnValue == JOptionPane.CANCEL_OPTION)
        return false;
	if (returnValue == JOptionPane.NO_OPTION) // Clear
    {
        if (deletePduListWhenDone)
            clearPduBuffers();
        return false;
    }
    if (returnValue == JOptionPane.YES_OPTION) 
    {
        JFileChooser chooser = getPduFileChooser();
        chooser.setSelectedFile(findFreeFile(chooser.getCurrentDirectory(), new File("dispackets" + DEFAULT_BINARY_FILE_ENDING)));

        returnValue = chooser.showSaveDialog(this);
        if (returnValue == JFileChooser.CANCEL_OPTION)
            return false;
        try 
        {
            boolean saveSuccess = saveFiles(chooser.getSelectedFile());
          
            if (deletePduListWhenDone && saveSuccess) // do not delete if save attempt was unsuccessful
                clearPduBuffers();
            return saveSuccess;
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

  public void loadPduFile()
  {
    if (hasPDUs()) 
    {
        String message = "<html><p aligh='center'>You might want to save PDUs first</p>"; // <p aligh='center'> before loading a new file</p>
        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
        DialogDisplayer.getDefault().notify(notifyDescriptor);
        
        savePduListToFile(true); // delete pdu list when done
            
        message = "<html><p aligh='center'>Now ready to load a new PDU file</p>";
        notifyDescriptor = new NotifyDescriptor.Message(message);
        DialogDisplayer.getDefault().notify(notifyDescriptor);
    }
    JFileChooser pduFileChooser = getPduFileChooser();
    int ret = pduFileChooser.showOpenDialog(this);
    if (ret == JFileChooser.CANCEL_OPTION)
      return;
    File f = pduFileChooser.getSelectedFile();
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
    clearPduBuffers();

    loadItUp(f, idxF);
    
    stateMachine.eventOccurs(PlayerEvent.RecordStopHit);  // resets the buttons
    beginningButton.doClick();  // move to first
  }
  
  @SuppressWarnings("unchecked")
  private void loadItUp(File data, File idxF)
  {
    try {
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(idxF));
      DefaultListModel<byte[]> mod = (DefaultListModel<byte[]>) pduList.getModel(); // this cast is "unchecked"
      int ret;
      do {
        byte[] buf = new byte[IDXFILE_RECORD_SIZE];
        ret = bis.read(buf);
        if (ret != IDXFILE_RECORD_SIZE)
          break;
        mod.addElement(buf);
        pduList.ensureIndexIsVisible(mod.getSize() - 1);
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
    else 
    {
//      if (hasPDUs())
//      {
//          // offer to save
//        savePduListToFile(false); // do not delete pdu list when done
//      }
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

      int startingPacket = pduList.getSelectedIndex();
      int sz = pduList.getModel().getSize();
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
    pduList.setSelectedIndex(0);
    pduList.ensureIndexIsVisible(0);
  }

  public void doEnd()
  {
    int sz = pduList.getModel().getSize();
    if(sz >= 1) {
      pduList.setSelectedIndex(sz-1);
      pduList.ensureIndexIsVisible(sz-1);
    }
  }
  
  public boolean hasPduListSelection()
  {
    if (!hasPDUs())
        return false;

    DefaultListModel pduListModel = (DefaultListModel) pduList.getModel();
    int pduListSize = pduListModel.getSize();

    for (int index = pduListSize - 1; index >=0; index--)
    {
        if (pduList.isSelectedIndex(index))
        {
            return true;
        }
    }
    return false;
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
        pduList = new javax.swing.JList();
        displayContainer = new javax.swing.JPanel();
        displayPanelScroller = new javax.swing.JScrollPane();
        networkParametersPanel = new javax.swing.JPanel();
        addressLabel = new javax.swing.JLabel();
        addressTF = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        portTF = new javax.swing.JTextField();
        alphabeticPduSendTestButton = new javax.swing.JButton();
        wiresharkButton = new javax.swing.JButton();
        wiresharkHelpButton = new javax.swing.JButton();
        displayModeChoicePanel = new javax.swing.JPanel();
        displayModeLabel = new javax.swing.JLabel();
        textModeButton = new javax.swing.JRadioButton();
        formattedPduModeButton = new javax.swing.JRadioButton();
        rawHexBytesButton = new javax.swing.JRadioButton();
        toolbarPanel = new javax.swing.JPanel();
        operationStatusjToolBar = new javax.swing.JToolBar();
        operationStatusPanel = new javax.swing.JPanel();
        operationModeLabel = new javax.swing.JLabel();
        playbackStateTF = new javax.swing.JLabel();
        recordToolbar = new javax.swing.JToolBar();
        recordButton = new javax.swing.JButton();
        recordPauseButton = new javax.swing.JButton();
        recordStopButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        vcrPanel = new javax.swing.JPanel();
        beginToolbar = new javax.swing.JToolBar();
        beginningButton = new javax.swing.JButton();
        fastReverseButton = new javax.swing.JButton();
        reverseStepButton = new javax.swing.JButton();
        playToolBar = new javax.swing.JToolBar();
        reversePlayButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JButton();
        playButton = new javax.swing.JButton();
        endToolbar = new javax.swing.JToolBar();
        stepButton = new javax.swing.JButton();
        ffButton = new javax.swing.JButton();
        endButton = new javax.swing.JButton();
        loopToolBar = new javax.swing.JToolBar();
        loopToggleButton = new javax.swing.JToggleButton();

        setLayout(new java.awt.BorderLayout());

        selectionPanel.setLayout(new java.awt.GridBagLayout());

        jSplitPane1.setDividerLocation(250);

        listScroller.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                listScrollerPropertyChange(evt);
            }
        });

        pduList.setModel(new DefaultListModel());
        pduList.setCellRenderer(new ByteArrayListRenderer());
        pduList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                pduListPropertyChange(evt);
            }
        });
        pduList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                pduListValueChanged(evt);
            }
        });
        listScroller.setViewportView(pduList);

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

        networkParametersPanel.setMinimumSize(new java.awt.Dimension(350, 26));
        networkParametersPanel.setPreferredSize(new java.awt.Dimension(350, 26));
        networkParametersPanel.setLayout(new java.awt.GridBagLayout());

        addressLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        addressLabel.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.addressLabel.text")); // NOI18N
        addressLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.addressLabel.toolTipText")); // NOI18N
        addressLabel.setAlignmentY(0.0F);
        addressLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        addressLabel.setMaximumSize(new java.awt.Dimension(100, 16));
        addressLabel.setMinimumSize(new java.awt.Dimension(45, 16));
        addressLabel.setPreferredSize(new java.awt.Dimension(45, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        networkParametersPanel.add(addressLabel, gridBagConstraints);

        addressTF.setText(org.web3d.x3d.options.X3dEditUserPreferences.getDISaddress(org.web3d.x3d.dis.DisEspduSenderControlPanel.DEFAULT_DIS_ADDRESS));
        addressTF.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.addressTF.toolTipText")); // NOI18N
        addressTF.setMinimumSize(new java.awt.Dimension(90, 22));
        addressTF.setPreferredSize(new java.awt.Dimension(90, 22));
        addressTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        networkParametersPanel.add(addressTF, gridBagConstraints);

        portLabel.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.portLabel.text")); // NOI18N
        portLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.portTF.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        networkParametersPanel.add(portLabel, gridBagConstraints);

        portTF.setText(org.web3d.x3d.options.X3dEditUserPreferences.getDISport(""+org.web3d.x3d.dis.DisEspduSenderControlPanel.DEFAULT_PORT));
        portTF.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.portTF.toolTipText")); // NOI18N
        portTF.setMinimumSize(new java.awt.Dimension(46, 22));
        portTF.setPreferredSize(new java.awt.Dimension(46, 22));
        portTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        networkParametersPanel.add(portTF, gridBagConstraints);

        alphabeticPduSendTestButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/OpenDisSurferDude.16x16.png"))); // NOI18N
        alphabeticPduSendTestButton.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.alphabeticPduSendTestButton.text")); // NOI18N
        alphabeticPduSendTestButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.alphabeticPduSendTestButton.toolTipText")); // NOI18N
        alphabeticPduSendTestButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        alphabeticPduSendTestButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alphabeticPduSendTestButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        networkParametersPanel.add(alphabeticPduSendTestButton, gridBagConstraints);

        wiresharkButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/Wireshark_favicon_16x16.png"))); // NOI18N
        wiresharkButton.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.wiresharkButton.text")); // NOI18N
        wiresharkButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.wiresharkButton.toolTipText")); // NOI18N
        wiresharkButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        wiresharkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wiresharkButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        networkParametersPanel.add(wiresharkButton, gridBagConstraints);

        wiresharkHelpButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        wiresharkHelpButton.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.wiresharkHelpButton.text")); // NOI18N
        wiresharkHelpButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.wiresharkHelpButton.toolTipText")); // NOI18N
        wiresharkHelpButton.setMargin(new java.awt.Insets(1, 1, 1, 3));
        wiresharkHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wiresharkHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        networkParametersPanel.add(wiresharkHelpButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        selectionPanel.add(networkParametersPanel, gridBagConstraints);

        displayModeChoicePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        displayModeChoicePanel.setMinimumSize(new java.awt.Dimension(400, 31));
        displayModeChoicePanel.setPreferredSize(new java.awt.Dimension(400, 31));
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

        formattedRawBG.add(formattedPduModeButton);
        formattedPduModeButton.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.formattedPduModeButton.text")); // NOI18N
        formattedPduModeButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.formattedPduModeButton.toolTipText")); // NOI18N
        formattedPduModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formattedRawHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        displayModeChoicePanel.add(formattedPduModeButton, gridBagConstraints);

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
        operationStatusPanel.setMinimumSize(new java.awt.Dimension(90, 50));
        operationStatusPanel.setPreferredSize(new java.awt.Dimension(90, 50));
        operationStatusPanel.setLayout(new java.awt.GridBagLayout());

        operationModeLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        operationModeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        operationModeLabel.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.operationModeLabel.text")); // NOI18N
        operationModeLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.operationModeLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        operationStatusPanel.add(operationModeLabel, gridBagConstraints);

        playbackStateTF.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playbackStateTF.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.text")); // NOI18N
        playbackStateTF.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.disabledText")));
        playbackStateTF.setMaximumSize(new java.awt.Dimension(85, 22));
        playbackStateTF.setMinimumSize(new java.awt.Dimension(85, 22));
        playbackStateTF.setName(""); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        operationStatusPanel.add(playbackStateTF, gridBagConstraints);

        operationStatusjToolBar.add(operationStatusPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        toolbarPanel.add(operationStatusjToolBar, gridBagConstraints);

        recordToolbar.setRollover(true);

        recordButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/RecordNormal.png"))); // NOI18N
        recordButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordButton.text")); // NOI18N
        recordButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordButton.toolTipText")); // NOI18N
        recordButton.setFocusable(false);
        recordButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recordButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordButtonActionPerformed(evt);
            }
        });
        recordToolbar.add(recordButton);

        recordPauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/PauseRed24.gif"))); // NOI18N
        recordPauseButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordPauseButton.text")); // NOI18N
        recordPauseButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordPauseButton.toolTipText")); // NOI18N
        recordPauseButton.setActionCommand(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordPauseButton.actionCommand")); // NOI18N
        recordPauseButton.setFocusable(false);
        recordPauseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recordPauseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recordPauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordPauseButtonActionPerformed(evt);
            }
        });
        recordToolbar.add(recordPauseButton);

        recordStopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/StopRed24.png"))); // NOI18N
        recordStopButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordStopButton.text")); // NOI18N
        recordStopButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordStopButton.toolTipText")); // NOI18N
        recordStopButton.setActionCommand(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.recordStopButton.actionCommand")); // NOI18N
        recordStopButton.setFocusable(false);
        recordStopButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recordStopButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recordStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recordStopButtonActionPerformed(evt);
            }
        });
        recordToolbar.add(recordStopButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Save24.gif"))); // NOI18N
        saveButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.saveButton.text")); // NOI18N
        saveButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.saveButton.toolTipText")); // NOI18N
        saveButton.setFocusable(false);
        saveButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        recordToolbar.add(saveButton);

        loadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Open24.gif"))); // NOI18N
        loadButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.loadButton.text")); // NOI18N
        loadButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.loadButton.toolTipText")); // NOI18N
        loadButton.setFocusable(false);
        loadButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loadButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });
        recordToolbar.add(loadButton);

        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Delete24.gif"))); // NOI18N
        clearButton.setText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.clearButton.text")); // NOI18N
        clearButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.clearButton.toolTipText")); // NOI18N
        clearButton.setFocusable(false);
        clearButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clearButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        recordToolbar.add(clearButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        toolbarPanel.add(recordToolbar, gridBagConstraints);

        beginToolbar.setRollover(true);

        beginningButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Beginning24.png"))); // NOI18N
        beginningButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.beginningButton.text")); // NOI18N
        beginningButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.beginningButton.toolTipText")); // NOI18N
        beginningButton.setFocusable(false);
        beginningButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        beginningButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        beginningButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beginningButtonActionPerformed(evt);
            }
        });
        beginToolbar.add(beginningButton);

        fastReverseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Rewind24.gif"))); // NOI18N
        fastReverseButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.fastReverseButton.text")); // NOI18N
        fastReverseButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.fastReverseButton.toolTipText")); // NOI18N
        fastReverseButton.setFocusable(false);
        fastReverseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        fastReverseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        fastReverseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fastReverseButtonActionPerformed(evt);
            }
        });
        beginToolbar.add(fastReverseButton);

        reverseStepButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/StepBack24.gif"))); // NOI18N
        reverseStepButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.reverseStepButton.text")); // NOI18N
        reverseStepButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.reverseStepButton.toolTipText")); // NOI18N
        reverseStepButton.setFocusable(false);
        reverseStepButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reverseStepButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        reverseStepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reverseStepButtonActionPerformed(evt);
            }
        });
        beginToolbar.add(reverseStepButton);

        vcrPanel.add(beginToolbar);

        playToolBar.setRollover(true);

        reversePlayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/ReversePlay24.png"))); // NOI18N
        reversePlayButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.reversePlayButton.text")); // NOI18N
        reversePlayButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.reversePlayButton.toolTipText")); // NOI18N
        reversePlayButton.setFocusable(false);
        reversePlayButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        reversePlayButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        reversePlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reversePlayButtonActionPerformed(evt);
            }
        });
        playToolBar.add(reversePlayButton);

        pauseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Pause24.gif"))); // NOI18N
        pauseButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.pauseButton.text")); // NOI18N
        pauseButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.pauseButton.toolTipText")); // NOI18N
        pauseButton.setFocusable(false);
        pauseButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pauseButton.setMaximumSize(new java.awt.Dimension(43, 51));
        pauseButton.setMinimumSize(new java.awt.Dimension(43, 51));
        pauseButton.setPreferredSize(new java.awt.Dimension(43, 51));
        pauseButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });
        playToolBar.add(pauseButton);

        playButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Play24.gif"))); // NOI18N
        playButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.playButton.text")); // NOI18N
        playButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.playButton.toolTipText")); // NOI18N
        playButton.setFocusable(false);
        playButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        playButton.setMaximumSize(new java.awt.Dimension(43, 51));
        playButton.setMinimumSize(new java.awt.Dimension(43, 51));
        playButton.setPreferredSize(new java.awt.Dimension(43, 51));
        playButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        playToolBar.add(playButton);

        vcrPanel.add(playToolBar);

        endToolbar.setRollover(true);

        stepButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/StepForward24.gif"))); // NOI18N
        stepButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.stepButton.text")); // NOI18N
        stepButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.stepButton.toolTipText")); // NOI18N
        stepButton.setFocusable(false);
        stepButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        stepButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepButtonActionPerformed(evt);
            }
        });
        endToolbar.add(stepButton);

        ffButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/FastForward24.gif"))); // NOI18N
        ffButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.ffButton.text")); // NOI18N
        ffButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.ffButton.toolTipText")); // NOI18N
        ffButton.setFocusable(false);
        ffButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ffButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        ffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ffButtonActionPerformed(evt);
            }
        });
        endToolbar.add(ffButton);

        endButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/ResetEnd.png"))); // NOI18N
        endButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.endButton.text")); // NOI18N
        endButton.setToolTipText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.endButton.toolTipText")); // NOI18N
        endButton.setFocusable(false);
        endButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        endButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        endButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endButtonActionPerformed(evt);
            }
        });
        endToolbar.add(endButton);

        vcrPanel.add(endToolbar);

        loopToolBar.setRollover(true);

        loopToggleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/dis/playerrecorder/resources/vcr/Refresh24.png"))); // NOI18N
        loopToggleButton.setText(NbBundle.getMessage(getClass(), "DISPlayerRecorderPanel.loopToggleButton.text")); // NOI18N
        loopToggleButton.setToolTipText(org.openide.util.NbBundle.getMessage(DISPlayerRecorderPanel.class, "DISPlayerRecorderPanel.loopToggleButton.toolTipText")); // NOI18N
        loopToggleButton.setFocusable(false);
        loopToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        loopToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        loopToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loopToggleButtonActionPerformed(evt);
            }
        });
        loopToolBar.add(loopToggleButton);

        vcrPanel.add(loopToolBar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        toolbarPanel.add(vcrPanel, gridBagConstraints);

        add(toolbarPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

private void pduListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_pduListValueChanged

  if (evt != null && evt.getValueIsAdjusting())
    return;
  
  byte[] ba = (byte[]) pduList.getSelectedValue();
  if (ba == null)
    return;

  // build a long from bytes3-11
  ByteBuffer byteBuffer = ByteBuffer.allocate(IDXFILE_FILEPTR_IDX_SIZE);
  byteBuffer.put(ba, IDXFILE_FILEPTR_IDX, IDXFILE_FILEPTR_IDX_SIZE);
  long filePtr = byteBuffer.getLong(0);
  // go there and read the data, just getting the largest hunk
  int numRead;
  try {
    randomAccessFile.seek(filePtr);
    numRead = randomAccessFile.read(packet);
  }
  catch (IOException ex) {
    System.err.println("Bad pdu build: " + ex.getLocalizedMessage());
    return;
  }
  byteBuffer.clear();
  byteBuffer.put(ba, IDXFILE_RECORDSIZE_IDX, IDXFILE_RECORDSIZE_SIZE);
  int sz = byteBuffer.getInt(0);

  // formattedPduModeButton shows panel diplay
  if(formattedPduModeButton.isSelected() || textModeButton.isSelected()) {
    Pdu pdu = pduFact.createPdu(packet);
    if(pdu != null) {
      Class<?> displayClass = displayMap.get(pdu.getClass());
      if(displayClass != null) {
        if(formattedPduModeButton.isSelected())
          displayPdu(pdu,displayMap.get(pdu.getClass()));
        else if(textModeButton.isSelected())
          displayXml(pdu);
      }
    }
  }
  else if(rawHexBytesButton.isSelected()) {
    displayByteArray(packet,sz);
  }
}//GEN-LAST:event_pduListValueChanged

private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
  loadPduFile();
}//GEN-LAST:event_loadButtonActionPerformed

private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
  savePduListToFile(false);
}//GEN-LAST:event_saveButtonActionPerformed

private void beginningButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beginningButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.BeginHit);
}//GEN-LAST:event_beginningButtonActionPerformed

private void fastReverseButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_fastReverseButtonActionPerformed
{//GEN-HEADEREND:event_fastReverseButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.ReversePlayHit);
}//GEN-LAST:event_fastReverseButtonActionPerformed

private void recordButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recordButtonActionPerformed
{//GEN-HEADEREND:event_recordButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.RecordHit);
}//GEN-LAST:event_recordButtonActionPerformed

private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pauseButtonActionPerformed
{//GEN-HEADEREND:event_pauseButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.PauseHit);
}//GEN-LAST:event_pauseButtonActionPerformed

private void playButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_playButtonActionPerformed
{//GEN-HEADEREND:event_playButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.PlayHit);
}//GEN-LAST:event_playButtonActionPerformed

private void ffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ffButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.FastForwardHit);
}//GEN-LAST:event_ffButtonActionPerformed

private void endButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.EndHit);
}//GEN-LAST:event_endButtonActionPerformed

private void formattedRawHandler(java.awt.event.ActionEvent evt)//GEN-FIRST:event_formattedRawHandler
{//GEN-HEADEREND:event_formattedRawHandler
  pduListValueChanged(null);
}//GEN-LAST:event_formattedRawHandler

private void recordStopButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recordStopButtonActionPerformed
{//GEN-HEADEREND:event_recordStopButtonActionPerformed
    stateMachine.eventOccurs(PlayerEvent.RecordStopHit);
}//GEN-LAST:event_recordStopButtonActionPerformed

private void recordPauseButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recordPauseButtonActionPerformed
{//GEN-HEADEREND:event_recordPauseButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.RecordPauseHit);
}//GEN-LAST:event_recordPauseButtonActionPerformed

private void reverseStepButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_reverseStepButtonActionPerformed
{//GEN-HEADEREND:event_reverseStepButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.ReverseStepHit);
}//GEN-LAST:event_reverseStepButtonActionPerformed

private void reversePlayButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_reversePlayButtonActionPerformed
{//GEN-HEADEREND:event_reversePlayButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.ReversePlayHit);
}//GEN-LAST:event_reversePlayButtonActionPerformed

private void stepButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stepButtonActionPerformed
{//GEN-HEADEREND:event_stepButtonActionPerformed
  stateMachine.eventOccurs(PlayerEvent.StepHit);
}//GEN-LAST:event_stepButtonActionPerformed

private boolean isLoop()
{
  return loopToggleButton.isSelected();
}

private void loopToggleButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_loopToggleButtonActionPerformed
{//GEN-HEADEREND:event_loopToggleButtonActionPerformed
  if(player != null && player.isRunning())
    player.setLoop(isLoop());
}//GEN-LAST:event_loopToggleButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        if (!hasPDUs())
        {
            clearButton.setEnabled(false);
            return; // this is safety check, state machine should keep button disabled if pduList is empty
        }
        
        boolean foundSelection = false;
        int selectionCount = 0;
        DefaultListModel pduListModel = (DefaultListModel) pduList.getModel();
        int pduListSize = pduListModel.getSize();
        
        for (int index = pduListSize - 1; index >=0; index--)
        {
            if (pduList.isSelectedIndex(index))
            {
                foundSelection = true;
                selectionCount++;
            }
        }        
        if (foundSelection)
        {
            String lastWord = "entries";
            if (selectionCount == 1)
                   lastWord = "entry";
            UIManager.put("OptionPane.yesButtonText","Delete Selection");
            UIManager.put("OptionPane.noButtonText", "Continue");
            int ret = JOptionPane.showConfirmDialog(this, "Delete " + selectionCount + " selected PDU " + lastWord + "?", 
                      "Confirm...", JOptionPane.YES_NO_OPTION);

            if (ret == JOptionPane.YES_OPTION) // Delete selected PDUs
            {
                for (int index = pduListSize - 1; index >=0; index--)
                {
                    if (pduList.isSelectedIndex(index))
                    {
                        foundSelection = true;
                        selectionCount++;
                        pduListModel.remove(index);
                    }
                }
                clearButton.setEnabled(hasPDUs()); // disable button, if list is now empty
                return;
            }
            if (pduListSize == 1)
                return; // user said no, they did not want to delete this single entry
            
            // it is currently hard to deselect all entries, and so continue by asking whether to save full list before eleting.
            savePduListToFile(true); // delete list when done
            clearButton.setEnabled(hasPDUs()); // disable button
        }
        else // no selection, offer to save and clear all
        {
            savePduListToFile(true); // delete list when done
            clearButton.setEnabled(hasPDUs()); // disable button
        }
    }//GEN-LAST:event_clearButtonActionPerformed

    private void listScrollerPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_listScrollerPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_listScrollerPropertyChange

    private void pduListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_pduListPropertyChange

    }//GEN-LAST:event_pduListPropertyChange

    public String getWiresharkFilter()
    {
        // https://www.wireshark.org/docs/wsug_html_chunked/ChCustCommandLine.html
        String wiresharkFilter = "udp && dis";                                    // simplest
//      String wiresharkFilter = "(tcp.port == " + portTF.getText() + " || udp.port == " + portTF.getText() + ") && dis"; // MV3500 default
//      String wiresharkFilter = "ip.addr " + addressTF.getText() + " && port " + portTF.getText() + " && dis";
        return wiresharkFilter;
    }
    public String getWiresharkCommandLineInvocation() // TODO fix
    {
        // https://www.wireshark.org/docs/wsug_html_chunked/ChCustCommandLine.html
        String wiresharkCommandLineInvocation = " -f \"ip.addr " + addressTF.getText() + "\"  && port " + portTF.getText() + " && dis "; // 
        return wiresharkCommandLineInvocation;
    }
    
    private void wiresharkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wiresharkButtonActionPerformed
//        String[] invocationArray = {X3dEditUserPreferences.getWiresharkPath(), getWiresharkCommandLineInvocation()};

        File    checkExistingFile = new File(X3dEditUserPreferences.getWiresharkPath());
        boolean isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
        if (!isExecutableFile)
        {
            JOptionPane.showMessageDialog(this, "Check X3D-Edit User Preferences for Wireshark", 
                        "Wireshark installation not found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        externalProcessLaunch(X3dEditUserPreferences.getWiresharkPath());
        JOptionPane.showMessageDialog(this, "<html><p align='center'>Simple filter: " + getWiresharkFilter() + "</p>", 
                                    "Need Wireshark filter to see DIS PDUs", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_wiresharkButtonActionPerformed

    private void addressTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressTFActionPerformed
        getWiresharkCommandLineInvocation(); // update tooltip
        X3dEditUserPreferences.setDISaddress(addressTF.getText()); // save new value
    }//GEN-LAST:event_addressTFActionPerformed

    private void portTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portTFActionPerformed
        // reset default
        X3dEditUserPreferences.setDISport(portTF.getText()); // save new value
    }//GEN-LAST:event_portTFActionPerformed

    private void alphabeticPduSendTestButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alphabeticPduSendTestButtonActionPerformed
        // see DisPduSenderTestAction
        System.out.println("========================================================================================");
        System.out.println("DIS Alphabetical PDU Sender Test (expecting 72 PDUs total)");
        System.out.println();
        edu.nps.moves.dis7.examples.AlphabeticalPduSender.main(new String[]{addressTF.getText(), portTF.getText()}); // output goes to console
        System.out.println("========================================================================================");
        if (!playbackStateTF.getText().contains("recording"))
        {
            JOptionPane.showMessageDialog(this, 
                        "Select Record mode to see DIS PDUs", "PDUs sent to network, but not visible here", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_alphabeticPduSendTestButtonActionPerformed

    private void wiresharkHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wiresharkHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteWireshark);
        browserLaunch(X3dEditUserPreferences.helpMV3500CourseWireshark);
        browserLaunch(X3dEditUserPreferences.helpMV3500CourseWiresharkREADME);
    }//GEN-LAST:event_wiresharkHelpButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTF;
    private javax.swing.JButton alphabeticPduSendTestButton;
    private javax.swing.JToolBar beginToolbar;
    public javax.swing.JButton beginningButton;
    public javax.swing.JButton clearButton;
    private javax.swing.JPanel displayContainer;
    private javax.swing.JPanel displayModeChoicePanel;
    private javax.swing.JLabel displayModeLabel;
    private javax.swing.JScrollPane displayPanelScroller;
    public javax.swing.JButton endButton;
    private javax.swing.JToolBar endToolbar;
    public javax.swing.JButton fastReverseButton;
    public javax.swing.JButton ffButton;
    private javax.swing.JRadioButton formattedPduModeButton;
    private javax.swing.ButtonGroup formattedRawBG;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JScrollPane listScroller;
    public javax.swing.JButton loadButton;
    public javax.swing.JToggleButton loopToggleButton;
    private javax.swing.JToolBar loopToolBar;
    private javax.swing.JPanel networkParametersPanel;
    private javax.swing.JLabel operationModeLabel;
    private javax.swing.JPanel operationStatusPanel;
    private javax.swing.JToolBar operationStatusjToolBar;
    public javax.swing.JButton pauseButton;
    private javax.swing.JList pduList;
    public javax.swing.JButton playButton;
    private javax.swing.JToolBar playToolBar;
    public javax.swing.JLabel playbackStateTF;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTF;
    private javax.swing.JRadioButton rawHexBytesButton;
    public javax.swing.JButton recordButton;
    public javax.swing.JButton recordPauseButton;
    public javax.swing.JButton recordStopButton;
    private javax.swing.JToolBar recordToolbar;
    public javax.swing.JButton reversePlayButton;
    public javax.swing.JButton reverseStepButton;
    public javax.swing.JButton saveButton;
    private javax.swing.JPanel selectionPanel;
    public javax.swing.JButton stepButton;
    private javax.swing.JRadioButton textModeButton;
    private javax.swing.JPanel toolbarPanel;
    private javax.swing.JPanel vcrPanel;
    private javax.swing.JButton wiresharkButton;
    private javax.swing.JButton wiresharkHelpButton;
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
