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

import java.io.File;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.nodes.Node;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport.X3dEditor;

/**
 * Xj3DConverterBaseImportAction.java
 * Created on Jun 11, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
public abstract class Xj3DConverterBaseExportAction extends BaseConversionsAction
{
  @Override
  protected String transformSingleFile(X3dEditor xed)
  {
    OutputWriter ow = null;
    try {
      Node[] node = xed.getActivatedNodes();

      X3DDataObject dob = (X3DDataObject) xed.getX3dEditorSupport().getDataObject();
      FileObject mySrc = dob.getPrimaryFile();
      File mySrcF = FileUtil.toFile(mySrc);

      InputOutput io = IOProvider.getDefault().getIO("Output", false);
      io.setFocusTaken(true);
      ow = io.getOut();

      String outputExt = "."+getOutputFileType();
      ConversionsHelper.saveFilePack data = ConversionsHelper.getDestinationFile(mySrcF, mySrc.getName() + outputExt, false);

      if (data == null) {
        ow.println(getConversionCancelledMessage());
        return null;
      }
      File myOutF = data.file;

      ow.println(getPreConversionMessage(myOutF));
      ConversionsHelper.getXj3dConverter().convert(mySrcF, myOutF.getAbsolutePath());
      ow.println(getPostConversionMessage());
      return myOutF.getAbsolutePath();
    }
    catch (Throwable t) {
      ow.println(getConversionExceptionPrefixMessage() + t.getLocalizedMessage());
      return null;
    }
  }

  //abstract public String getChooserTitle();

  abstract public String getInputFileType();

  //abstract public String getInputFileTypeDescription();

  abstract public String getOutputFileType();

  abstract public String getPreConversionMessage(File outFile);

  abstract public String getPostConversionMessage();

  abstract public String getConversionCancelledMessage();

  abstract public String getConversionExceptionPrefixMessage();
}
