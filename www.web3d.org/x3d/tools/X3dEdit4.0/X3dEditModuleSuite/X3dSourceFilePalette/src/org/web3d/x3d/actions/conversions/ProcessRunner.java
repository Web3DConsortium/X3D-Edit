/*
 * Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer
 *       in the documentation and/or other materials provided with the
 *       distribution.
 *  * Neither the names of the Naval Postgraduate School (NPS)
 *       Modeling Virtual Environments and Simulation (MOVES) Institute
 *       (http://www.nps.edu and https://MovesInstitute.nps.edu)
 *       nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific
 *       prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.web3d.x3d.actions.conversions;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.openide.windows.OutputWriter;

/**
 * ProcessRunner.java
 * Created on Jun 10, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
public class ProcessRunner
{
  public static int execJava(OutputWriter ow, String... classAndArgs)
  {
    String pathToJava = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
    String cp = System.getProperty("java.class.path");
    String[] newArgs = new String[3 + classAndArgs.length];
    newArgs[0] = pathToJava;
    newArgs[1] = "-classpath";
    newArgs[2] = cp;
    int i = 3;
    for (String s : classAndArgs)
      newArgs[i++] = s;

    return exec(ow, newArgs);
  }

  public static int exec(final OutputWriter ow, List<String> args)
  {
    return exec(ow, new ProcessBuilder(args));
  }

  public static int exec(final OutputWriter ow, String... execString)
  {
    return exec(ow, new ProcessBuilder(execString));
  }

  public static int exec(final OutputWriter ow, ProcessBuilder pb)
  {
    try {
      final Process p = pb.start();

      // Spawn thread to read output of spawned program
      // sample code from mindprod.com
      new Thread()
      {
        @Override
        public void run()
        {
          // hook into output from spawned program
          final BufferedReader br;
          try {
            final InputStream is = p.getInputStream();
            final InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr, 100/* buffsize in chars */);
            String line;
            while ((line = br.readLine()) != null) {
              System.err.println("debug....writing " + line);
              ow.println(line);
            }
            br.close();
          }
          catch (Exception e) {
          }
        // returning from run kills the thread.
        }
      }.start();

      // Wait for the process to finish
      try {
        p.waitFor();
        return p.exitValue();  // only exit for good return
      }
      catch (InterruptedException ex) {
        Thread.currentThread().interrupt();
        throw new Exception(" Thread interrupted");
      }
    }
    catch (Exception e) {
      if (ow != null)
        ow.println("Error running external process: " + e.getLocalizedMessage());
    }
    return -1;

  }
}
