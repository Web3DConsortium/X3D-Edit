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

import javax.swing.AbstractButton;

/**
 * DISPlayerRecorderStateMachine.java
 * Created on Oct 24, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey / jmbailey@nps.edu
 * @version $Id$
 */
public class DISPlayerRecorderStateMachine
{
  private DISPlayerRecorderPanel pan;
  private AbstractButton[] allButts;
  private String mutedRed = "#BC0000";
  private String mutedGrn = "#009C00";

  public static enum PlayerEvent {
    BeginHit,
    FastReverseHit,
    ReverseStepHit,
    ReversePlayHit,

    PauseHit,

    PlayHit,
    StepHit,
    FastForwardHit,
    EndHit,

    RecordHit,
    RecordPauseHit,
    RecordStopHit
  }

  /*
   case BeginHit:
     break;
   case FastReverseHit:
     break;
   case ReverseStepHit:
     break;
   case ReversePlayHit:
     break;
   case PauseHit:
     break;
   case PlayHit:
     break;
   case StepHit:
     break;
   case FastForwardHit:
     break;
   case EndHit:
     break;
   case RecordHit:
     break;
   case RecordPauseHit:
     break;
   case RecordStopHit:
     break;
 */


  // States: stopped, recording, recordingPaused, playing, playing fast, reversePlay
  
  public DISPlayerRecorderStateMachine(DISPlayerRecorderPanel pan)
  {
    this.pan = pan;
    allButts = new AbstractButton[]{pan.loadButt,
                             pan.beginningButt,  pan.fastReverseButt, pan.reverseStepButt, pan.reversePlayButt,
                             pan.pauseButt,
                             pan.playButt,       pan.stepButt,        pan.ffButt,          pan.endButt,
                             pan.loopToggleButt,
                             pan.recordButt,     pan.recordStopButt,  pan.recordPauseButt,
                             pan.saveButt,};

    currentState = new StoppedState();
  }
  
  public void eventOccurs(PlayerEvent ev)
  {
    currentState = currentState.eventIn(ev);
  }
  
  private PlayerState currentState;
  
  interface PlayerState
  {
    PlayerState eventIn(PlayerEvent ev);
    void action();
  }
  
  private String assertionErrorMsg(PlayerEvent ev, Object obj)
  {
    return "Program Error in DISPlayerRecorderStateMachine: got "+ev.name()+" in "+ obj.getClass().getName();
  }
  /*
   *States: stopped, recording, recordingpaused, paused, playing, playing fast, reversePlay
   */
  
  class StoppedState implements PlayerState
  {
    StoppedState()
    {
      setAllButts(pan.isDirty());  //will turn everything on if there is data loaded
      pan.playbackStateTF.setText("idle");
      pan.pauseButt.setVisible(false);
      pan.playButt.setVisible(true);
      pan.reversePlayButt.setVisible(true);
      pan.showPlayHilightedState(false);

      pan.recordButt.setEnabled(true);
      pan.recordPauseButt.setEnabled(false);
      pan.recordStopButt.setEnabled(false);
      pan.showRecordHilightedState(false);
      
      pan.loadButt.setEnabled(true);
      action();
    }

    @Override
    public void action()
    {
      pan.doStop();
    }

    @Override
    public PlayerState eventIn(PlayerEvent ev)
    {
      switch(ev) {
        case BeginHit:
          pan.doBeginning();
          return this;       // no state change
        case FastReverseHit:
          return new PlayingFastReverseState();
        case ReverseStepHit:
          pan.doReverseSingleStep();
          return this;       // no state change

        case ReversePlayHit:
          return new PlayingReverseState();
        //case PauseHit:
        case PlayHit:
          return new PlayingState();

        case StepHit:
          pan.doSingleStep();
          return this;       // no state change
        case FastForwardHit:
          return new PlayingFastState();
        case EndHit:
          pan.doEnd();
          return this;       // no state change

        case RecordHit:
          return new RecordingState(false);
        case RecordStopHit:
          return new StoppedState();    // allowed for loading from disk...already stopped, but twiddle the butts
        //case RecordPauseHit:

        default:
          assert false : assertionErrorMsg(ev, this);
          return this;
      }
    }
  }
  
  class RecordingState implements PlayerState
  {
    boolean continuing;
    RecordingState(boolean continuing)
    {
      this.continuing = continuing;
      disableAllButts();
      pan.playbackStateTF.setText("<html><font color="+mutedRed+">recording");  //dark red
      pan.showRecordHilightedState(true);

      pan.recordPauseButt.setEnabled(true);
      pan.recordStopButt.setEnabled(true);
      action();
    }

    @Override
    public void action()
    {
      pan.doRecord(continuing);
    }
    
    @Override
    public PlayerState eventIn(PlayerEvent ev)
    {
      switch(ev) {
        //case PlayHit:
        //case PauseHit:
        //case BeginHit:
        //case ReversePlayHit:
        //case FastForwardHit:
        //case EndHit:

        //case RecordHit:
        case RecordStopHit:
          return new StoppedState();
        case RecordPauseHit:
          return new RecordingPausedState();
         default:
          assert false : assertionErrorMsg(ev, this);
          return this;
      }
    }
  }

  class RecordingPausedState implements PlayerState
  {
    RecordingPausedState()
    {
      disableAllButts();
      pan.playbackStateTF.setText("<html><font color="+mutedRed+">paused");

      pan.recordStopButt.setEnabled(true);
      pan.recordButt.setEnabled(true);
      action();
    }

    @Override
    public void action()
    {
      pan.doPauseRecording();
    }
    
    @Override
    public PlayerState eventIn(PlayerEvent ev)
    {
      switch (ev) {
        //case PlayHit:
        //case PauseHit:
        //case BeginHit:
        //case ReversePlayHit:
        //case FastForwardHit:
        //case EndHit:
        case RecordHit:
          return new RecordingState(true);
        case RecordStopHit:
          return new StoppedState();
        default:
          assert false : assertionErrorMsg(ev, this);
          return this;
      }
    }
  }
  
  class PlayingState implements PlayerState
  {
    PlayingState()
    {
      disableAllButts();
      pan.playbackStateTF.setText("<html><font color=#3B963B>playback"); //Color(75, 166, 75) muted green
      pan.loopToggleButt.setEnabled(true);
      pan.playButt.setVisible(false);
      //pan.reversePlayButt.setVisible(false);
      pan.pauseButt.setVisible(true);
      pan.pauseButt.setEnabled(true);
      pan.showPlayHilightedState(true);
      action();
    }

    @Override
    public void action()
    {
      pan.doPlay();
    }
    
    @Override
    public PlayerState eventIn(PlayerEvent ev)
    {
      switch (ev) {
        //case PlayHit:
        case PauseHit:
          return new StoppedState();
        //case BeginHit:
        //case ReversePlayHit:
        //FastForwardHit:
        //case EndHit:
        //case RecordHit:
        //case RecordPausedHit:
        case RecordStopHit:
          return new StoppedState();   // event when reaches end
        default:
          assert false : assertionErrorMsg(ev, this);
          return this;
      }
    }
  }

  class PlayingFastReverseState extends PlayingFastState
  {
    PlayingFastReverseState()
    {
      super();
      pan.playbackStateTF.setText("<html><font color="+mutedGrn+">rev fast play");
    }

    @Override
    public void action()
    {
      pan.doReversePlayFast();
    }
  }
  class PlayingFastState implements PlayerState
  {
    PlayingFastState()
    {
      disableAllButts();
      pan.playbackStateTF.setText("<html><font color="+mutedGrn+">fast play");

      pan.loopToggleButt.setEnabled(true);
      pan.playButt.setVisible(false);
      pan.pauseButt.setVisible(true);
      pan.pauseButt.setEnabled(true);
      pan.showPlayHilightedState(true);
      
      action();
    }

    @Override
    public void action()
    {
      pan.doPlayFast();
    }

    @Override
    public PlayerState eventIn(PlayerEvent ev)
    {
      switch(ev) {
        //case PlayHit:
        case PauseHit:
          return new StoppedState();
        //case BeginHit:
        //case ReversePlayHit:
        //case FastForwardHit:
        //case EndHit:
        //case RecordHit:
        //case RecordPauseHit:
        case RecordStopHit:
          return new StoppedState(); // comes end on eof
        default:
          assert false : assertionErrorMsg(ev, this);
          return this;
      }
    }
  }
  
  class PlayingReverseState implements PlayerState
  {
    PlayingReverseState()
    {
      disableAllButts();
      pan.playbackStateTF.setText("<html><font color="+mutedGrn+">reverse play");
      //pan.playButt.setVisible(false);
      pan.loopToggleButt.setEnabled(true);
      pan.reversePlayButt.setVisible(false);
      pan.pauseButt.setVisible(true);
      pan.pauseButt.setEnabled(true);
      pan.showPlayHilightedState(true);
      action();
    }

    @Override
    public void action()
    {
      pan.doReversePlay();
    }
   
    @Override
    public PlayerState eventIn(PlayerEvent ev)
    {
      switch(ev) {
        //case PlayHit:
        case PauseHit:
          return new StoppedState();

        //case BeginHit:
        //case ReversePlayHit:
        //case FastForwardHit:
        //case EndHit:
        //case RecordHit:
        case RecordStopHit:
          return new StoppedState(); // comes end on eof

        //case RecordPauseHit:
        default:
          assert false : assertionErrorMsg(ev, this);
          return this;
      }
    }
  }
  
  private void disableAllButts()
  {
    setAllButts(false);
    pan.showPlayHilightedState(false);
    pan.showRecordHilightedState(false);
  }
  private void enableAllButts()
  {
    setAllButts(true);
  }
  private void setAllButts(boolean wh)
  {
    for(AbstractButton butt : allButts)
      butt.setEnabled(wh);
  }
}
