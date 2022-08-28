/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.

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

package org.web3d.x3d.palette;

import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.openide.text.NbDocument;

/**
 * NetBeans module palette utilities
 * https://netbeans.apache.org/tutorials/nbm-palette-api1.html
 * @author brutzman@nps.edu
 */
public class X3DPaletteUtilities {

    public static void insert(final String s, final JTextComponent target) throws BadLocationException {

        final StyledDocument doc = (StyledDocument)target.getDocument();

        class AtomicChange implements Runnable
        {
            @Override
            public void run() {
                Document value = target.getDocument();
                if (value == null)
                    return;
                try {
                    insert(s, target, doc);
                } catch (BadLocationException e) {}
            }
        }
        try
        {
            NbDocument.runAtomicAsUser(doc, new AtomicChange());
        } 
        catch (BadLocationException ex) 
        {
            System.err.println("*** X3DPaletteUtilities.insert() BadLocationException " + ex.getMessage());
        }
    }

    private static int insert(String s, JTextComponent target, Document doc) throws BadLocationException
    {
        int start = -1;

        try
        {
            //firstly, find selected text range:
            Caret caret = target.getCaret();
            int p0 = Math.min(caret.getDot(), caret.getMark());
            int p1 = Math.max(caret.getDot(), caret.getMark());
            doc.remove(p0, p1 - p0);

            //then, replace selected text range with the inserted one:
            start = caret.getDot();
            doc.insertString(start, s, null);
        } 
        catch (BadLocationException ble) 
        {
            System.err.println("*** X3DPaletteUtilities.insert() start=" + start + " BadLocationException " + ble.getMessage());
        }
        return start;
    }

}