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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;
import java.util.Vector;
import static org.web3d.x3d.dis.playerrecorder.DISGrabber.*;

/**
 * DISPlayer.java
 * Created on Oct 9, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
public class DISPlayer
{
  // Defaults
  private MulticastSocket socket;
  private InetAddress multicastGroup;
  private String multicastString = "239.1.2.3";
  private int  port = 3000;

  private RandomAccessFile randFile;
  private File saveFile;
  private RandomAccessFile randIndexFile;
  private File saveIndexFile;
  private long FAST_PLAY_SLEEP_MS = 10;
  private Integer count = null;

  public DISPlayer(File[] files)
  {
      try {
          saveFile = files[0];
          randFile = new RandomAccessFile(files[0], "rw");
          saveIndexFile = files[1];
          randIndexFile = new RandomAccessFile(files[1], "rw");
      } catch (FileNotFoundException ex) {
          throw new RuntimeException("The world has changed.");
      }
  }

  public DISPlayer(InetAddress multiGroup, int ipPort, long receiveLimit)
  {
      setPort(ipPort);
      setMulticastGroup(multiGroup);
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
    
    // In case someone resets the port only
    setMulticastGroup(multicastGroup);
  }

  public void setCount(Integer i)
  {
    count = i;
  }
  public File[] getSaveFileSet()
  {
    return new File[]{saveFile,saveIndexFile};
  }

  // Internal methods
  // ----------------

  private boolean running = false;
  private Thread runThread;
  private Runnable packetWriter;

  byte[] indexBA = new byte[IDXFILE_RECORD_SIZE];
  byte[] pktBA   = new byte[MAX_PDU_SIZE];

  private ByteBuffer byte2LongBB = ByteBuffer.allocate(IDXFILE_RECORD_SIZE);
  private ByteBuffer byte2wordBB = ByteBuffer.allocate(2);

  int nextIndexRecord = 0;
  long lastTime = -1;

  private DatagramPacket returnTheNextOneAfterSleeping(boolean fastPlay, boolean reversePlay) throws Exception
  {
    randIndexFile.seek(nextIndexRecord * indexBA.length);
    if(reversePlay) {
      nextIndexRecord--;
    }
    else {
      nextIndexRecord++;
    }

    int ret = randIndexFile.read(indexBA);
    byte2LongBB.clear();
    byte2LongBB.put(indexBA);
    if (ret == indexBA.length) {
      long nanoTime = byte2LongBB.getLong(IDXFILE_RECDNANOTIME_IDX);
      //System.out.println("got nanoDelay = "+nanoTime);
      long filePtr = byte2LongBB.getLong(IDXFILE_FILEPTR_IDX);

      randFile.seek(filePtr);
      randFile.read(pktBA);
      byte2wordBB.clear();
      byte2wordBB.put(pktBA, DISGrabber.PDU_LENGTH_FIELD_OFFSET, DISGrabber.PDU_LENGTH_FIELD_LENGTH);
      int len = byte2wordBB.getShort(0);
      DatagramPacket p = new DatagramPacket(pktBA, len, multicastGroup, port);

      // sleep appropriately
      if (!fastPlay) {
        if (lastTime > 0) {
          long interval = Math.abs(nanoTime - lastTime);   // in case we're going backwards
          long millis = interval / 1000000l;
          long nanos = (interval % 1000000l);
          //System.out.println("sleeping for "+millis+" ms "+nanos+" ns");
          Thread.sleep(millis, (int) nanos);
        }
      }
      else
        Thread.sleep(FAST_PLAY_SLEEP_MS); //Thread.yield();

      lastTime = nanoTime;
      return p;
    }
    return null;
  }

  class Syncher
  {
    private boolean b;
    public void set(boolean b)
    {
      this.b = b;
    }
    public boolean is()
    {
      return b;
    }
  }

  final Syncher paused = new Syncher();

  public void pause()
  {
    if(this.isRunning()) {
      paused.set(true);
    }
  }

  public void resume()
  {
    if(paused.is()) {
      synchronized(paused) {
        paused.set(false);
        paused.notify();
      }
    }
  }

  public void setFastPlay(boolean b)
  {
//  fastPlay = b;
    fastPlay = false; // no longer supported
  }

  public void setLoop(boolean b)
  {
    loop = b;
  }

  public void setReversePlay(boolean b)
  {
    reversePlay = b;
  }

  public void start()
  {
    start(0);
  }

  private boolean fastPlay = false; // no longer supported
  private boolean reversePlay = false;
  private boolean loop = false;

  public void start(int beginningIdx)
  {
    nextIndexRecord = beginningIdx;

    packetWriter = () -> {
        String reasonForStopping;
        try {
            do {
                int pktNum = nextIndexRecord;
                DatagramPacket packet = returnTheNextOneAfterSleeping(fastPlay,reversePlay);
                if(packet==null) {
                    if(!loop) {
                        reasonForStopping = "End of File";
                        break;
                    }
                    else { //looping
                        nextIndexRecord = 0;
                        lastTime = -1;
                        packet = returnTheNextOneAfterSleeping(fastPlay,reversePlay);
                        if(packet==null) {
                            reasonForStopping = "End of File";
                            break;
                        }
                    }
                }

                synchronized(paused) {
                    while(paused.is())
                        paused.wait();
                }
                
                socket.send(packet);
                notifySentListeners(packet,pktNum);
                if(count != null){
                    if(--count <= 0) {
                        reasonForStopping = "Requested count completed";
                        break;
                    }
                }
                
                if(nextIndexRecord < 0 ) {  // worked down to zero
                    if(!loop) {
                        reasonForStopping = "Beginning of File";
                        break;
                    }
                    else {
                        long numRecs = randIndexFile.length()/indexBA.length;
                        nextIndexRecord = (int)numRecs -1;
                        lastTime=-1;
                    }
                }
            }
            while(true);  //infinite loop
        }
        catch(Exception ex) {
            reasonForStopping = "Exception: "+ex.getLocalizedMessage();
        }
        if(!socket.isClosed())
            socket.close();
        running = false;
        runThread=null;
        notifyStoppedListeners(reasonForStopping);
    };
    runThread = new Thread(packetWriter,"DISPlayer");
    runThread.setPriority(Thread.NORM_PRIORITY);   //todo tweek

    running = true;
    runThread.start();
  }

  public void stop()
  {
    if(socket != null){
      socket.close();  // will interrupt blocked receive()
    }
  }

  public boolean isRunning()
  {
    return running;
  }

  public static interface DISSentListener
  {
    public void packetSent(DatagramPacket packet, int totalSent, boolean inReverseMode);
  }
  private Vector<DISSentListener> sendListeners = new Vector<>();
  private void notifySentListeners(DatagramPacket packet, int count)
  {
      sendListeners.forEach(lis -> {
          lis.packetSent(packet,count,reversePlay);
      });
  }
  public void addSentListener(DISSentListener lis)
  {
    sendListeners.add(lis);
  }

  public static interface DISSenderStoppedListener
  {
    void senderStopped(String msg);
  }

  private Vector<DISSenderStoppedListener> stoppedListeners = new Vector<>();
  private void notifyStoppedListeners(String s)
  {
      stoppedListeners.forEach(lis -> {
          lis.senderStopped(s);
      });
  }
  public void addStoppedtListener(DISSenderStoppedListener lis)
  {
    stoppedListeners.add(lis);
  }
}
