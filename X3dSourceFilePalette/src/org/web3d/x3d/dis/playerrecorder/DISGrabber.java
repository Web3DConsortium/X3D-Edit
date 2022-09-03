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
 * Based on edu.nps.moves.DISLogger by DMcG
 */
package org.web3d.x3d.dis.playerrecorder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * DISGrabber.java
 * Created on Oct 9, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
public class DISGrabber
{
  // Defaults
  private MulticastSocket socket;
  private InetAddress multicastGroup;
  private String multicastString = DEFAULT_ADDRESS;
  private long fileSizeLimit = 0x1000000;  // 1 Gb
  private int  port = DEFAULT_PORT;
  
  private RandomAccessFile randFile;
  private File saveFile;
  private RandomAccessFile randIndexFile;
  private File saveIndexFile;
  
  private List<byte[]> pointerArray = new ArrayList<>(1024*16);
  private List<byte[]> synchedList = Collections.synchronizedList(pointerArray);
  
  public static final int NUMBYTESINLONG = Long.SIZE/8;
  public static final int MAX_PDU_SIZE = 2048;
  public static final int NUMBYTESININT = Integer.SIZE/8;

  // 3 bytes of hdr, 8(typ) bytes of file ptr, 8(typ) bytes of nanosec interval
  public static final int IDXFILE_PDUHDR_IDX            = 0;
  public static final int IDXFILE_PDUHDR_IDX_SIZE       = 3;

  public static final int IDXFILE_FILEPTR_IDX           = IDXFILE_PDUHDR_IDX+IDXFILE_PDUHDR_IDX_SIZE;
  public static final int IDXFILE_FILEPTR_IDX_SIZE      = NUMBYTESINLONG;

  public static final int IDXFILE_RECDNANOTIME_IDX      = IDXFILE_FILEPTR_IDX+IDXFILE_FILEPTR_IDX_SIZE;
  public static final int IDXFILE_RECDNANOTIME_IDX_SIZE = NUMBYTESINLONG;

  public static final int IDXFILE_RECORDSIZE_IDX        = IDXFILE_RECDNANOTIME_IDX + IDXFILE_RECDNANOTIME_IDX_SIZE;
  public static final int IDXFILE_RECORDSIZE_SIZE       = NUMBYTESININT;
  
  public static final int IDXFILE_RECORD_SIZE = IDXFILE_PDUHDR_IDX_SIZE +
                                                IDXFILE_FILEPTR_IDX_SIZE +
                                                IDXFILE_RECDNANOTIME_IDX_SIZE +
                                                IDXFILE_RECORDSIZE_SIZE;
  
  public static final int PDU_LENGTH_FIELD_OFFSET = 8;  // 8 bytes into the hdr
  public static final int PDU_LENGTH_FIELD_LENGTH = 2;  // 16 bit field
  public static final int PDU_HEADER_LENGTH = 12; //bytes

  public static final String DEFAULT_BINARY_FILE_ENDING = ".disbin";
  public static final String DEFAULT_BINARY_INDEX_FILE_ENDING = ".disbinidx";
  
  public static final int DEFAULT_PORT = 3000;
  public static final String DEFAULT_ADDRESS = "239.1.2.3";
  
  // Default constructor
  public DISGrabber()
  {
    // uses defaults
    try {
      setMulticastGroup(InetAddress.getByName(multicastString));
    }
    catch (IllegalArgumentException | UnknownHostException ex) {
      throw new RuntimeException("The world has changed.");
    }
  }
  
  public DISGrabber(InetAddress multiGroup, int ipPort, long receiveLimit)
  {
    setMulticastGroup(multiGroup);
    setPort(ipPort);
    setFileLimit(receiveLimit);    
  }
  
  public void setFileLimit(long lim)
  {
    fileSizeLimit = lim;
  }
  
  public void setMulticastGroup(InetAddress addr) throws IllegalArgumentException
  {
    multicastGroup = addr;
    try {
      socket = new MulticastSocket(port);
      socket.setSoTimeout(0); // infinite timeout
      InetSocketAddress group = new InetSocketAddress(addr, port);
      socket.joinGroup(group, null);
    }
    catch(IOException ex) {
      throw new IllegalArgumentException("Error creating multicast socket: "+ex.getLocalizedMessage());
    }
  }
  
  public void setPort(int p) throws IllegalArgumentException
  {
    if(p >= 0x10000)
      throw new IllegalArgumentException("Port must be a 16-bit number");
    port = p;
    
    setMulticastGroup(multicastGroup);  // to parse the argument pair again
  }
  
  public File[] getSaveFileSet()
  {
    return new File[]{saveFile,saveIndexFile};
  }
  
  public List<byte[]> getPointerList()
  {
    return synchedList;
  }
  
  // Internal methods
  // ----------------
  
  private boolean running = false;
  private Thread runThread;
  private Runnable packetRunner;
  private byte[] buffer = new byte[MAX_PDU_SIZE];
  private DatagramPacket packet = new DatagramPacket(buffer,buffer.length);
  
  private boolean paused = false;
  public void pause()
  {
    paused = true;
  }
  public void resume()
  {
    paused = false;
  }
  public void start()
  {
    try {
      saveFile = File.createTempFile("DISGrabber", DEFAULT_BINARY_FILE_ENDING);
      saveFile.deleteOnExit();
      saveIndexFile = new File(saveFile.getParentFile(),saveFile.getName()+".idx");
      saveIndexFile.createNewFile();
      saveIndexFile.deleteOnExit();
      
      randFile = new RandomAccessFile(saveFile,"rw");
      randFile.seek(0); // should be unnecessary
      randIndexFile = new RandomAccessFile(saveIndexFile,"rw");
      randIndexFile.seek(0);
    }
    catch(IOException ex) {
      throw new RuntimeException("The world has changed.");
    }
    
    packetRunner = () -> {
        try {
            do {
                socket.receive(packet);
                if(!paused) {
                    long recvdNanoTime = System.nanoTime();
                    if(handlePacket(packet,recvdNanoTime))
                        break;  //exceed file limit
                }
            }
            while(true);  //infinite loop

        }
        catch(IOException ex) {}  // probably interrupted exception
        // System.out.println("stopping");
        try {
            randFile.close();
            randIndexFile.close();
        }
        catch(IOException ex) {}

        // Make them match
        // This doesn't do much...it has the same effect as touch(f1);touch(f2)
        // I think the 2 closes above are sufficient
        saveIndexFile.setLastModified(saveFile.lastModified());
        running = false;
        runThread=null;
    };
    runThread = new Thread(packetRunner,"DISGrabber");
    runThread.setPriority(Thread.NORM_PRIORITY);   //todo tweek
    
    running = true;
    runThread.start();
  }
  
  public void stop()
  {
    if(socket != null)
      socket.close();  // will interrupt blocked receive()
  }
  
  public boolean isRunning()
  {
    return running;
  }
  
  private ByteBuffer bb = ByteBuffer.allocate(NUMBYTESINLONG);
  
  boolean USERELATIVETIMES = true;
  long timeBase = 0;
  private long relativizeTime(long nano)
  {
    if(!USERELATIVETIMES)
      return nano;
    if(timeBase==0)
      timeBase = nano;
    return nano-timeBase;    
  }
  
  private ByteBuffer byte2wordBB = ByteBuffer.allocate(2);
  
  /**
   * Return true if we've exceeded our file limit
   * @param packet
   * @return
   */
  private boolean handlePacket(DatagramPacket packet, long nanoTime)
  {
    //System.out.println("got a packet");
    boolean returnVal = true;  // assume that we've exceed our limit
    
    byte[] pArr = packet.getData();
    byte[] ptrByteArray = new byte[IDXFILE_RECORD_SIZE];
    
    int idx = IDXFILE_PDUHDR_IDX;
    ptrByteArray[idx+0] = pArr[0];  // save the 3 bytes which when decoded indicate packet type
    ptrByteArray[idx+1] = pArr[1];
    ptrByteArray[idx+2] = pArr[2];

    try {
      idx = IDXFILE_FILEPTR_IDX;
      bb.putLong(0,randFile.getFilePointer());
      for (int i = 0; i < NUMBYTESINLONG; i++)
        ptrByteArray[idx++] = bb.array()[i];
      
      idx = IDXFILE_RECDNANOTIME_IDX;
      bb.putLong(0,relativizeTime(nanoTime));
      for (int i = 0; i < NUMBYTESINLONG; i++)
        ptrByteArray[idx++] = bb.array()[i];

      idx = IDXFILE_RECORDSIZE_IDX;
      bb.putInt(0,packet.getLength());
      for (int i=0;i<NUMBYTESININT;i++)
        ptrByteArray[idx++] = bb.array()[i];

      randFile.write(pArr,0,packet.getLength());   // save packet
      randIndexFile.write(ptrByteArray);
      
      pointerArray.add(ptrByteArray); // save ptr array (in ram)
      returnVal = randFile.length() > fileSizeLimit;
    }
    catch (IOException ex) {
      System.err.println("IOException in packet logger: "+ex.getLocalizedMessage());
    }

    notifyReceivedListeners();
    
    return returnVal;   // if exception or over limit, return true to stop
  }

  private void notifyReceivedListeners()
  {
      listeners.forEach(lis -> {
          lis.packetReceived(this, pointerArray.size());
      });
  }
  
  private Vector<DISReceivedListener> listeners = new Vector<>();
  public void addReceivedListener(DISReceivedListener lis)
  {
    listeners.add(lis);
  }
  
  public static interface DISReceivedListener
  {
    void packetReceived(DISGrabber grabberInstance, int totalReceived);
  }
}
