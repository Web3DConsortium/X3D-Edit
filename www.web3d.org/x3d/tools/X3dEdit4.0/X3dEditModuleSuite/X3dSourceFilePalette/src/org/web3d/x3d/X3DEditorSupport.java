/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.

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
package org.web3d.x3d;

import java.io.IOException;
import java.io.ObjectInput;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.net.ContentHandlerFactory;
import java.net.URLConnection;

import java.util.Vector;

import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import org.netbeans.spi.palette.PaletteController;

import org.openide.cookies.EditCookie;
import org.openide.cookies.EditorCookie;
import org.openide.cookies.OpenCookie;
import org.openide.cookies.PrintCookie;
import org.openide.cookies.SaveCookie;

import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;

import org.openide.loaders.DataObject;

import org.openide.nodes.Node;
import org.openide.nodes.Node.Cookie;

import org.openide.text.CloneableEditor;
import org.openide.text.DataEditorSupport;

import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;

import org.openide.windows.CloneableOpenSupport;

import org.web3d.x3d.options.X3dOptions;

import org.web3d.x3d.palette.X3DPaletteFactory;
import org.web3d.x3d.palette.X3DPaletteUtilities.ElementLocation;
import org.web3d.x3d.palette.X3DPaletteUtilities.ValidateThread;

import org.web3d.x3d.xj3d.viewer.Xj3dTopComponent;
import org.web3d.x3d.xj3d.viewer.Xj3dViewerPanel;

/**
 * Editor support for X3D data objects.
 *
 * X3dEditorSupport.java Created on March 8, 2007, 3:32 PM
 *
 * MOVES Institute Naval Postgraduate School, Monterey, CA, USA www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 *
 * @see org.openide.text.DataEditorSupport
 */
public final class X3DEditorSupport extends DataEditorSupport implements OpenCookie, EditCookie, EditorCookie.Observable, PrintCookie {

    /**
     * Constructor.
     */
    X3DEditorSupport(X3DDataObject obj) {
        super(obj, new Environment(obj));
        setMIMEType("model/x3d+xml"); // NOI18N
    }

    // Taken from: http://bits.netbeans.org/7.4/javadoc/org-openide-text/apichanges.html#CloneableEditorSupport.asynchronousOpen
    @Override
    protected boolean asynchronousOpen() {
        boolean retVal = super.asynchronousOpen(); // default false
        return !retVal; // so, flip it to avoid blocking AWT thread
    }

    /**
     * Overrides superclass method. Adds adding of save cookie if the document
     * has been marked modified.
     *
     * @return true if the environment accepted being marked as modified or
     * false if it has refused and the document should remain unmodified
     */
    @Override
    protected boolean notifyModified() {
        if (!super.notifyModified()) {
            return false;
        }

        addSaveCookies();
        return true;
    }

    /**
     * Overrides superclass method. Adds removing of save cookie.
     */
    @Override
    protected void notifyUnmodified() {
        super.notifyUnmodified();
        removeSaveCookie();
    }

    /**
     * SaveCookie for this support instance. The cookie is adding/removing data
     * object's cookie set depending on if modification flag was set/unset.
     */
    private final SaveCookie saveCookie = () -> {
        X3DEditorSupport.this.saveDocument();
        X3DEditorSupport.this.getDataObject().setModified(false);

        // Validate resulting document only if user preference is set (on by default)
        Runnable r = () -> {
            if (X3dOptions.getAutoValidate()) {
                JEditorPane documentEditorPane = getOpenedPanes()[0];
                validateXML(documentEditorPane, getDataObject().getPrimaryFile().getPath());
            }
            
            Xj3dTopComponent xj3dTopComponent = Xj3dTopComponent.findInstance();

            if (!xj3dTopComponent.isOpened()) {
                xj3dTopComponent.open();
                xj3dTopComponent.requestActive();
            }
            xj3dTopComponent.getXj3DViewerPanel().refreshXj3dScene();
        };
        SwingUtilities.invokeLater(r);
    } /*** Implements <code>SaveCookie</code> interface. */;

    /**
     * Helper method. Adds save cookie to the data object.
     */
    private void addSaveCookies() {
        X3DDataObject obj = (X3DDataObject) getDataObject();

        // Adds save cookie to the data object.
        if (obj.getLookup().lookup(SaveCookie.class) == null) {
            obj.getCookieSet0().add(saveCookie);
            obj.setModified(true);
        }
    }

    /**
     * Helper method. Removes save cookie from the data object.
     */
    private void removeSaveCookie() {
        X3DDataObject obj = (X3DDataObject) getDataObject();

        // Remove save cookie from the data object.
        Cookie cookie = obj.getLookup().lookup(SaveCookie.class);
        if (cookie != null && cookie.equals(saveCookie)) {
            obj.getCookieSet0().remove(saveCookie);
            obj.setModified(false);
        }
    }

    public void validateXML(JTextComponent target, String filename) {
        
        // Schedule validation task in separate thread
        RequestProcessor.getDefault().post(new ValidateThread(target, filename, false));  // false=no dialog
    }

    /**
     * Nested class. Environment for this support. Extends
     * <code>DataEditorSupport.Env</code> abstract class.
     */
    private static class Environment extends DataEditorSupport.Env {

        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         */
        public Environment(X3DDataObject obj) {
            super(obj);
        }

        /**
         * Implements abstract superclass method.
         */
        @Override
        protected FileObject getFile() {
            return getDataObject().getPrimaryFile();
        }

        /**
         * Implements abstract superclass method.
         */
        @Override
        protected FileLock takeLock() throws IOException {
            return ((X3DDataObject) getDataObject()).getPrimaryEntry().takeLock();
        }

        /**
         * Overrides superclass method.
         *
         * @return text editor support (instance of enclosing class)
         */
        @Override
        public CloneableOpenSupport findCloneableOpenSupport() {
            return getDataObject().getLookup().lookup(X3DEditorSupport.class);
        }
    }

    /**
     * A method to create a new component. Overridden in subclasses.
     *
     * @return the {@link HtmlEditor} for this support
     */
    @Override
    protected CloneableEditor createCloneableEditor() {
        return new X3dEditor(this);
    }

    public static class X3dEditor extends CloneableEditor {

        public static final String PREFERRED_ID = "X3DEDITOR";

        private org.jdom.Document jdomDoc;
        private Vector<ElementLocation> saxLocations;

        private X3DEditorSupport supp;
        private ContentHandlerFactory factory;

        /**
         * Default no-arg constructor
         */
        public X3dEditor() {
            //System.out.println("X3dEditor noarg constr");
        }

        @Override
        protected String preferredID() {
            return PREFERRED_ID;
        }

        /**
         * Creates new editor
         *
         * @param s
         */
        public X3dEditor(X3DEditorSupport s) {
            super(s);
            this.supp = s;
            initialize();
        }

        public X3DEditorSupport getX3dEditorSupport() {
            return supp;
        }

        void associatePalette(X3DEditorSupport s) {
            if (supp == null) {
                supp = s;
            }
            DataObject dataObject = (X3DDataObject) s.getDataObject();
            if (dataObject instanceof X3DDataObject) {
                try {
                    PaletteController pc = X3DPaletteFactory.getPalette();
                    Lookup pcl = Lookups.singleton(pc);
                    Lookup anl = getActivatedNodes()[0].getLookup();
                    Lookup actionMap = Lookups.singleton(getActionMap());
                    ProxyLookup l = new ProxyLookup(new Lookup[]{anl, actionMap, pcl});
                    associateLookup(l);
                } catch (IOException ioe) {
                    
                    // TODO: exception handling
                    ioe.printStackTrace(System.err);
                }
            }
        }

        void associateX3DDataObject(X3DEditorSupport s) {
            if (supp == null) {
                supp = s;
            }
        }

        private void initialize() {
            Node nodes[] = {((DataEditorSupport) cloneableEditorSupport()).getDataObject().getNodeDelegate()};
            setActivatedNodes(nodes);
            associatePalette((X3DEditorSupport) cloneableEditorSupport());
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            super.readExternal(in);
            initialize();
        }

        public org.jdom.Document getJdomDoc() {
            return jdomDoc;
        }

        public void setJdomDoc(org.jdom.Document jdomDoc) {
            this.jdomDoc = jdomDoc;
        }

        public void setJdomSaxLocations(Vector<ElementLocation> saxLocations) {
            this.saxLocations = saxLocations;
        }

        public Vector<ElementLocation> getJdomSaxLocations() {
            return saxLocations;
        }

        @Override
        public int getPersistenceType() {
            return X3dEditor.PERSISTENCE_ONLY_OPENED;
        }

        @Override
        protected void componentShowing() {
            
            super.componentShowing();

            // Need to temporarily disable the
            // org.eclipse.osgi.framework.internal.protocol.ContentHandlerFactory
            // as it interferes with content loading of images for Xj3D
            uninstallContentHandlerFactory();

            X3DDataObject xObj = (X3DDataObject) getX3dEditorSupport().getDataObject();

            Xj3dTopComponent topC = Xj3dTopComponent.findInstance();
            Xj3dViewerPanel pnl = topC.getXj3DViewerPanel();

            // Don't keep re-opening the same scene
            if (pnl.getCurrentX3DDataObject() != xObj) {
                pnl.openXj3dScene(xObj);
            }
        }

        @Override
        protected void componentClosed() {
            
            super.componentClosed();

            Xj3dTopComponent topC = Xj3dTopComponent.findInstance();

            X3DDataObject xObj = (X3DDataObject) getX3dEditorSupport().getDataObject();
            topC.getXj3DViewerPanel().closeXj3dScene(xObj);

            // Switch back to the Eclipse OSGI ContentHandlerFactory
            installContentHandlerFactory();
        }

        // The code below borrowed from:
        // https://git.eclipse.org/c/equinox/rt.equinox.framework.git/plain/bundles/org.eclipse.osgi/core/framework/org/eclipse/osgi/framework/internal/core/Framework.java?h=R3_7_maintenance&id=R37x_v20110808-1106
        private void uninstallContentHandlerFactory() {
            try {
                Field factoryField = getField(URLConnection.class, ContentHandlerFactory.class, false);
                if (factoryField == null) {
                    return; // oh well, we tried.
                }
                synchronized (URLConnection.class) {
                    ContentHandlerFactory fac = (ContentHandlerFactory) factoryField.get(null);

                    // null out the field so that we can successfully reintall it later								
                    factoryField.set(null, null);

                    factory = fac;
                }
            } catch (IllegalAccessException | IllegalArgumentException | SecurityException e) {
                System.err.println(e);
            }
        }

        private static Field getField(Class<?> clazz, Class<?> type, boolean instance) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                boolean isStatic = Modifier.isStatic(field.getModifiers());
                if (instance != isStatic && field.getType().equals(type)) {
                    field.setAccessible(true);
                    return field;
                }
            }
            return null;
        }

        private void installContentHandlerFactory() {
            if (factory != null) {
                URLConnection.setContentHandlerFactory(factory);
            }
        }
    }

}
