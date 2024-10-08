/*
* Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
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
*       (https://www.nps.edu and https://MovesInstitute.nps.edu)
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

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.netbeans.api.xml.cookies.CookieMessage;
import org.netbeans.api.xml.cookies.CookieObserver;
import org.netbeans.spi.xml.cookies.DefaultXMLProcessorDetail;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.web3d.x3d.InputOutputReporter;
import org.web3d.x3d.X3DCatalog;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;
import org.web3d.x3d.X3DEditorSupport.X3dEditor;
import static org.web3d.x3d.actions.conversions.X3dToXhtmlDomConversionAction.x3dToXhtmlDomConversionFrame;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

    /**
     * BaseConversionsAction.java
     * Created on Jan 25, 2008
     *
     * MOVES Institute
     * Naval Postgraduate School, Monterey, CA, USA
     * www.nps.edu
     *
     * @author Mike Bailey jmbailey@nps.edu
     * @version $Id: BaseConversionsAction.java 31811 2021-05-08 04:32:33Z brutzman $
     */
    public abstract class BaseConversionsAction extends CallableSystemAction
    {
        static File xsltFilesRoot = null;

        static
        {
            File destFolderF;
            try
            {
                File tmpDir = new File(System.getProperty("java.io.tmpdir")).getCanonicalFile();
                destFolderF = new File(tmpDir, "X3DEditTransforms");
                destFolderF.mkdir();
                destFolderF.deleteOnExit();

                FileObject destFolderFO = FileUtil.createFolder(destFolderF);

                FileObject stylesheets = FileUtil.getConfigRoot().getFileSystem().findResource("X3dTransforms"); //Repository.getDefault().getDefaultFileSystem().findResource ("X3dTransforms");

                if (stylesheets == null)
                {
                    System.err.println("*** failure to find resource: BaseConversionAction FileUtil.getConfigRoot().getFileSystem().findResource(\"X3dTransforms\")");
                } 
                else
                {
                    FileObject[] children = stylesheets.getChildren();
                    for (FileObject fo : children)
                    {
                        try
                        {
                            File destFile = new File(destFolderF, fo.getNameExt());
                            if (destFile.exists())
                            {
                                destFile.delete();
                            }
                            FileObject dstFo = FileUtil.copyFile(fo, destFolderFO, fo.getName());
                            FileUtil.toFile(dstFo).deleteOnExit();
                        } 
                        catch (IOException ee)
                        {
                            System.err.println("Can't copy " + fo.getName() + " to tmp");
                        }
                    }
                    xsltFilesRoot = destFolderF;
                }
        }
        catch (IOException e)
        {
          System.err.println("*** BaseConversionsAction: Error copying stylesheets to tmp. " + e.getMessage());
          e.printStackTrace();
        }
      }
  
    /** Do not depend on default constructor, just in case that causes problems */
    public BaseConversionsAction ()
    {
  //    System.out.println("*** BaseConversionsAction constructor...");
    }

    protected final static String Nb_Select_files_to_process      = NbBundle.getMessage(BaseConversionsAction.class, "Select_file(s)_to_process");
    protected final static String Nb_Select_X3D_Files             = NbBundle.getMessage(BaseConversionsAction.class, "Select_X3D_Files");
    protected final static String Nb_XSLT_Transformation_starting = NbBundle.getMessage(BaseConversionsAction.class, "XSLT_transformation_starting");
    protected final static String Nb_Writing                      = NbBundle.getMessage(BaseConversionsAction.class, "_Writing_");
    protected final static String Nb_XSLT_transformation_complete = NbBundle.getMessage(BaseConversionsAction.class, "XSLT_transformation_complete");
    protected final static String Nb_XSLT_transformation_cancelled= NbBundle.getMessage(BaseConversionsAction.class, "XSLT_transformation_cancelled");
    protected final static String Nb_Exception                    = NbBundle.getMessage(BaseConversionsAction.class, "Exception:__");
    protected final static String Nb_Using                        = NbBundle.getMessage(BaseConversionsAction.class, "_Using_");
  //  protected final static String Nb_against                      = NbBundle.getMessage(BaseConversionsAction.class, "_against_");
    protected final static String Nb_default_JRE_XSLT_processor   = NbBundle.getMessage(BaseConversionsAction.class, "_default_JRE_XSLT_processor");
    protected final static String Nb_XSLT_processor_from_         = NbBundle.getMessage(BaseConversionsAction.class, "_XSLT_processor_from_");
    protected final static String Nb_is_unwritable                = NbBundle.getMessage(BaseConversionsAction.class, "_is_unwritable");
    protected final static String Nb_in_BaseConversionsAction     = NbBundle.getMessage(BaseConversionsAction.class, "_in_BaseConversionsAction");

    /**
     * Subclass method to xsl the single file managed by the passed TC.
     * Will normally call back to xsltOneFile in this class.
     * @param x3dEditor our TopComponent instance managing a single x3d file
     * @return absolute path of saved transformed file
     */
    abstract protected String transformSingleFile(X3DEditorSupport.X3dEditor x3dEditor);

    /**
     * Method called from app menu
     */
    @Override
    public void performAction()
    {
        // Mode/TopComponent inspection and access must always be done in EventThread
        if (EventQueue.isDispatchThread()) // avoid duplication
        {
            if (this instanceof X3dToXhtmlDomConversionAction) // singleton special handling required
            {
                if (X3dToXhtmlDomConversionAction.isRunning)
                {
                    // already running
                }
                else // not yet running, start it
                {
                    X3dToXhtmlDomConversionAction.isRunning = true;
//                    conversionsWorkerRunnable.run();
                }
                if      (X3dToXhtmlDomConversionAction.getPlayer().equalsIgnoreCase(X3dToXhtmlDomConversionAction.X3DOM))
                         x3dToXhtmlDomConversionFrame.setPaneIndex(X3dToXhtmlDomConversionFrame.X3DOM_TAB);
                else if (this instanceof CorsHttpPanelAction)
                         x3dToXhtmlDomConversionFrame.setPaneIndex(X3dToXhtmlDomConversionFrame.CORS_TAB);
                else if (X3dToXhtmlDomConversionAction.getPlayer().equalsIgnoreCase(X3dToXhtmlDomConversionAction.X_ITE) || 
                         X3dToXhtmlDomConversionAction.getPlayer().equalsIgnoreCase(X3dToXhtmlDomConversionAction.COBWEB))
                         x3dToXhtmlDomConversionFrame.setPaneIndex(X3dToXhtmlDomConversionFrame.X_ITE_TAB);
                
                X3dToXhtmlDomConversionAction.x3dToXhtmlDomConversionFrame.toFront();
                X3dToXhtmlDomConversionAction.x3dToXhtmlDomConversionFrame.setVisible(true);
            }
            conversionsWorkerRunnable.run(); // all other conversions
        }
//        else if ((this instanceof CorsHttpPanelAction) && ((X3dToXhtmlDomConversionAction)this).x3dToXhtmlDomConversionFrame != null)
//        {
//            ((X3dToXhtmlDomConversionAction)this).x3dToXhtmlDomConversionFrame.toFront();
//            ((X3dToXhtmlDomConversionAction)this).x3dToXhtmlDomConversionFrame.setVisible(true);
//        }
        else
        {
            try
            {
                EventQueue.invokeAndWait(conversionsWorkerRunnable);
            } 
            catch (InterruptedException | InvocationTargetException ex)
            {
                System.err.println(Nb_Exception + ex.getClass().getSimpleName() + Nb_in_BaseConversionsAction);
            }
        }
    }
    /** Runnable thread that performs the conversion */
    private final Runnable conversionsWorkerRunnable = () ->
    {
        Mode windowManagerEditorMode = WindowManager.getDefault().findMode("editor"); // noi18n
        TopComponent selectedOneComponent = windowManagerEditorMode.getSelectedTopComponent();

        TopComponent[] topComponentArray = windowManagerEditorMode.getTopComponents();

        ArrayList<X3DEditorSupport.X3dEditor> x3dEditorTopComponentArrayList = new ArrayList<>();
        for (TopComponent topComponent : topComponentArray)
        {
            if (topComponent instanceof X3DEditorSupport.X3dEditor)
            {
                x3dEditorTopComponentArrayList.add((X3DEditorSupport.X3dEditor) topComponent);
            }
        }
        // no independent invocation of HTML tab to worry about
        if (this instanceof CorsHttpPanelAction)
        {
            return; // no further checks or action here
        }
        if (x3dEditorTopComponentArrayList.isEmpty())
        {
            String message = "Must first open an .x3d model prior to performing conversion.  No action taken.";
            System.err.println ("*** " + this.getClass().getName() + ": " + message);
            NotifyDescriptor.Message msg = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(msg);
            return; // no action to perform
        }
        // Do not immediately execute if autolaunched by X3DOM or X_ITE panel, rather count on deliberate invocation by button
        if (this instanceof X3dToXhtmlDomConversionAction) // X3DOM or X_ITE
        {
            // avoid runaway default conversion by complex panel until user is ready with one or more .x3d models
            if (!((X3dToXhtmlDomConversionAction) this).isReadyForConversion())
            {
                ((X3dToXhtmlDomConversionAction) this).setReadyForConversion(true);
            }
        }
        // ready to launch
        if (x3dEditorTopComponentArrayList.size() == 1) // no need to ask user which model
        {
            X3dEditor x3dEditor = x3dEditorTopComponentArrayList.get(0);
            transformSingleFile(x3dEditor);
//          transformSingleFile(x3dEditorTopComponentArrayList.get(0)); // prior
            return;
        }
        // more than one model found, continue after asking user to select model of interest
        Integer selectedModelIndex = null; // TODO no longer needed?
        Vector<String> filenames = new Vector<>();
        for (X3DEditorSupport.X3dEditor x3dEditor : x3dEditorTopComponentArrayList)
        {
            X3DDataObject x3dDataObject = (X3DDataObject) x3dEditor.getX3dEditorSupport().getDataObject();
            filenames.add(x3dDataObject.getPrimaryFile().getNameExt());
            if (x3dEditor.equals(selectedOneComponent)) {
                //selected = filenames.size() - 1;
                // changed to:
                // if there are several X3d files and one of them is the top, do that one only
                transformSingleFile(x3dEditor);
                return;
            }
        }
        // more than one model found but none selected, ask user to select model of interest
        JList<String> filenamesJList = new JList<>(filenames);
        filenamesJList.setVisibleRowCount(3);
        filenamesJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (selectedModelIndex != null)
            filenamesJList.setSelectedIndex(selectedModelIndex);
        JPanel filenameSelectionPanel = new JPanel(new BorderLayout());
        filenameSelectionPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        filenameSelectionPanel.add(new JLabel(Nb_Select_files_to_process), BorderLayout.NORTH);
        filenameSelectionPanel.add(new JScrollPane(filenamesJList), BorderLayout.CENTER);
        DialogDescriptor dialogDescriptor = new DialogDescriptor(filenameSelectionPanel, Nb_Select_X3D_Files);
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dialogDescriptor);
        int height = 200;
        if (filenames.size() > 4)
            height = 300;
        dialog.setSize(350, height);
        // TODO set single selection, i.e. setMultipSelectionEnables(false)
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true); // displays and blocks until user makes a return selection
        if (dialogDescriptor.getValue().equals(DialogDescriptor.CANCEL_OPTION))
            return;

        List<String>  selectedFilenamesList = filenamesJList.getSelectedValuesList();

        for (String filename : selectedFilenamesList) // transform all user-selected filenames
        {
            for (X3DEditorSupport.X3dEditor x3dEditor : x3dEditorTopComponentArrayList)
            {
                // TODO figure out url for each??
                // first time through: set url in Action class (and then in frame)
    //            X3DDataObject x3dDataObject = (X3DDataObject) x3dEditor.getX3dEditorSupport().getDataObject();
    //            FileObject primaryFileObject = x3dDataObject.getPrimaryFile();
    //            File primaryFile = FileUtil.toFile(primaryFileObject);

                if (x3dEditor.getX3dEditorSupport().getDataObject().getPrimaryFile().getNameExt().equals(filename))
                {
                    transformSingleFile(x3dEditor);
                    break;
                }
            }
        }
    };

    protected String processFileExtension(String s)
    {
        if (s == null)
            s = "";
        if (s.length() > 0)
        {
          if ((s.charAt(0) != '.') && !s.equalsIgnoreCase("Tidy.x3d") && !s.equalsIgnoreCase("Cobweb.html") &&
              !s.equalsIgnoreCase("X_ITE.html") && !s.equalsIgnoreCase("X3dom.xhtml")) // avoid special cases
            s = "." + s;
        }
        return s;
    }

    /**
     * Transform the currently open X3D disk file, putting the result into the passed destination disk file.
     * @param x3dEditor reference to X3dEditor
     * @param outputFile for results
     * @param xsltFileResourcePath path to XSLT stylesheet
     * @param xsltIsOSFile whether stylesheet is an operating system file
     * @param parameterMap map of key=value pairs to provide to spreadsheet
     * @param goodFinishMessage message on successful finish
     * @return
     * @throws java.io.FileNotFoundException if file problem occurs
     */
    public RequestProcessor.Task xsltOneFile(X3DEditorSupport.X3dEditor x3dEditor,
                                            File outputFile, 
                                            String xsltFileResourcePath,
                                            boolean xsltIsOSFile, 
                                            Map<String, Object> parameterMap, 
                                            String goodFinishMessage) throws FileNotFoundException
    {
        X3DDataObject x3dDataObject = (X3DDataObject) x3dEditor.getX3dEditorSupport().getDataObject();
        FileObject mySrc = x3dDataObject.getPrimaryFile();
        File mySrcFile = FileUtil.toFile(mySrc);

        return xsltOneFile(x3dEditor, mySrcFile.getName(), mySrc.getInputStream(), outputFile, xsltFileResourcePath, xsltIsOSFile, parameterMap, goodFinishMessage);
    }

    /**
     * Transform a disk file, putting the result into the passed destination disk file
     * @param x3dEditor reference to X3dEditor
     * @param sourceHandle
     * @param sourceInputStream
     * @param xsltFileResourcePath path to XSLT stylesheet
     * @param xsltIsOSFile whether stylesheet is an operating system file
     * @param parameterMap map of key=value pairs to provide to spreadsheet
     * @param goodFinishMessage message on successful finish
     * @return
     */
    public RequestProcessor.Task xsltOneFile(X3DEditorSupport.X3dEditor x3dEditor,
                                             String sourceHandle, 
                                             InputStream sourceInputStream, 
                                             File outputF, 
                                             String xsltFileResourcePath,
                                             boolean xsltIsOSFile, 
                                             Map<String, Object> parameterMap, 
                                             String goodFinishMessage)
    {
      RequestProcessor rp = getRequestProcessor();
      if  (rp != null)
           return rp.post(new XsltRunner(x3dEditor,sourceHandle,sourceInputStream,outputF,xsltFileResourcePath,xsltIsOSFile,parameterMap, goodFinishMessage));
      else return null;
    }

    private RequestProcessor requestProcessor;

    private synchronized RequestProcessor getRequestProcessor()
    {
        if (requestProcessor == null)
        {
            return requestProcessor = RequestProcessor.getDefault();
        }
        return null;
    }

    class XsltRunner implements Runnable
    {
        X3DEditorSupport.X3dEditor x3dEditor;
        String sourceHandle;
        InputStream sourceInputStream;
        File outputFile;
        String xsltFileResourcePath;
        boolean xsltIsOSFile;
        Map<String, Object> parameterMap;
        Runnable callback;
        String goodFinishMessage;

        XsltRunner(X3DEditorSupport.X3dEditor x3dEditor, String sourceHandle, InputStream sourceInputStream, File outputFile, String xsltFileResourcePath,
                   boolean xsltIsOSFile, Map<String, Object> parameterMap, String goodFinishMessage)
        {
            this.x3dEditor=x3dEditor;
            this.sourceHandle=sourceHandle;
            this.sourceInputStream=sourceInputStream;
            this.outputFile=outputFile;
            this.xsltFileResourcePath=xsltFileResourcePath;
            this.xsltIsOSFile = xsltIsOSFile;
            this.parameterMap = parameterMap;
            this.goodFinishMessage = goodFinishMessage;
        }

        @Override
        public void run()
        {
            TransformListener console = TransformListener.getInstance();
            try 
            {
                console.message(Nb_XSLT_Transformation_starting);
                StreamSource xslStream;
                if (xsltIsOSFile)
                {
                    File xsltF = new File(xsltFileResourcePath);
                    xslStream = new StreamSource(xsltF);
                    console.message(" " + xsltF.getName() + " transformation script applied to " + sourceHandle);
                } 
                else
                {
                    FileObject jarredTransformer = FileUtil.getConfigRoot().getFileSystem().findResource(xsltFileResourcePath);
                    xslStream = new StreamSource(jarredTransformer.getInputStream());
                    console.message(" " + jarredTransformer.getNameExt() + " transformation script applied to " + sourceHandle);
                }

                console.moveToFront();
                console.setNode(x3dEditor.getActivatedNodes()[0]);

                if (!outputFile.exists())
                {
                    outputFile.createNewFile();
                }

                if (!outputFile.canWrite())
                {
                    throw new IOException(outputFile.getAbsolutePath() + Nb_is_unwritable);
                }

                StreamResult outputStreamResult = new StreamResult(outputFile);
                console.message(Nb_Writing + outputFile.getAbsolutePath());

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                transformerFactory.setErrorListener(new BaseConversionsErrorHandler(console));
                transformerFactory.setURIResolver(X3DCatalog.getInstance());

                Transformer transformer = transformerFactory.newTransformer(xslStream);

                if (parameterMap != null)
                {
                    parameterMap.keySet().forEach(key ->
                    {
                        transformer.setParameter(key, parameterMap.get(key));
                    });
                }

                // code from NetBeans Transformable Support
                // inform user about used implementation
                ProtectionDomain protectionDomain = transformer.getClass().getProtectionDomain();
                CodeSource codeSource = protectionDomain.getCodeSource();
                if (codeSource == null)
                {
                    console.message(Nb_Using + transformer.getClass().getName() + Nb_default_JRE_XSLT_processor);
                }
                else
                {
                    URL locationURL = codeSource.getLocation();
                    console.message(Nb_Using + transformer.getClass().getName() + Nb_XSLT_processor_from_ + locationURL);
                }

                Proxy proxy = new Proxy(console);
                transformer.setErrorListener(proxy);

                transformer.transform(new StreamSource(sourceInputStream), outputStreamResult);
                console.message(Nb_XSLT_transformation_complete);

                if (goodFinishMessage != null)
                {
                    console.message(goodFinishMessage);
                }
            } 
            catch (IOException | IllegalArgumentException | TransformerException ex)
            {
                console.message(ex.getClass().getSimpleName()+": "+ex.getLocalizedMessage());
            }
            requestProcessor = null;
        }
    }

    class BaseConversionsErrorHandler implements ErrorListener
    {
      TransformListener consoleTransformListener;
      BaseConversionsErrorHandler(TransformListener consoleTransformListener)
      {
        this.consoleTransformListener = consoleTransformListener;
      }

      @Override
      public void error(TransformerException exception) throws TransformerException
      {
        consoleTransformListener.message("Error: "+exception.getLocalizedMessage());
      }

      @Override
      public void fatalError(TransformerException exception) throws TransformerException
      {
        consoleTransformListener.message("Fatal Error: "+exception.getLocalizedMessage());
      }

      @Override
      public void warning(TransformerException exception) throws TransformerException
      {
        consoleTransformListener.message("Warning: "+exception.getLocalizedMessage());
      }
    }
    /**
     * Transform the currently opened X3D file, putting up a chooser to pick the destination file.
     * @param x3dEditor reference to X3dEditor
     * @param xsltFileResourcePath path to xslt stylesheet
     * @param resultFileExtension extension for resultant file (e.g. .x3d)
     * @param wantOpenResultAccessory whether to include checkboxes accessory in result-file-save chooser
     * @param xsltIsOSFile whether stylesheet is an operating system file
     * @param parameterMap map of key=value pairs to provide to spreadsheet
     * @return
     */
    public ConversionsHelper.saveFilePack xsltOneFile(X3DEditorSupport.X3dEditor x3dEditor, 
                                                     String xsltFileResourcePath,
                                                     String resultFileExtension,
                                                     boolean wantOpenResultAccessory,
                                                     boolean xsltIsOSFile, 
                                                     Map<String,Object> parameterMap)
    {
        ConversionsHelper.saveFilePack saveFilePackResult;
        Node[] x3dEditorActivatedNodeArray = x3dEditor.getActivatedNodes();

        X3DDataObject x3dDataObject = (X3DDataObject) x3dEditor.getX3dEditorSupport().getDataObject();
        FileObject primaryFileObject = x3dDataObject.getPrimaryFile();
        File primaryFile = FileUtil.toFile(primaryFileObject);
        final TransformListener transformListener = TransformListener.getInstance();
        resultFileExtension = processFileExtension(resultFileExtension);
        try
        {
            // This path is setup in the X3D layer.xml file.
            transformListener.message(Nb_XSLT_Transformation_starting);
            StreamSource xsltStreamSource;
            if (xsltIsOSFile)
            {
                File xsltFile = new File(xsltFileResourcePath);
                xsltStreamSource = new StreamSource(xsltFile);
                transformListener.message(xsltFile.getName() + " transformation script applied to " + primaryFile.getAbsolutePath());
            } 
            else
            {
                FileObject jarredTransformer = FileUtil.getConfigRoot().getFileSystem().findResource(xsltFileResourcePath); //Repository.getDefault().getDefaultFileSystem().findResource (xsltFileResourcePath);
                xsltStreamSource = new StreamSource(jarredTransformer.getInputStream());
                transformListener.message(jarredTransformer.getNameExt() + " transformation script applied to " + primaryFile.getAbsolutePath());
            }
            transformListener.moveToFront(); // make consoleTransformListener visible
            transformListener.setNode(x3dEditorActivatedNodeArray[0]);

            // pop up dialog panel for destination file name
            ConversionsHelper.getOpenInEditorSetting(); // debug
            saveFilePackResult = ConversionsHelper.getDestinationFile(primaryFile, primaryFileObject.getName() + resultFileExtension, wantOpenResultAccessory);

            if (saveFilePackResult == null)
            {
                transformListener.message(Nb_XSLT_transformation_cancelled);
            } 
            else
            {
                File resultFile = saveFilePackResult.file;
                resultFile.createNewFile();
                StreamResult resultFileStreamResult = new StreamResult(resultFile);
                transformListener.message(Nb_Writing + resultFile.getAbsolutePath());

                //        old style; revamp to match ComprehensiveAction which works
                //        TransformerFactory fact = TransformerFactory.newInstance();
                //        fact.setURIResolver(X3DCatalog.getInstance());
                //        Transformer transf =  fact.newTransformer(xslStream);

                /* do this because I can't find a way to substitute saxon for xalan in TransformableSupport, xalan is needed for other things.*/
                /* The large validitychecks style sheet includes some xslt 2.0 statements */
                TransformerFactory saxonTransformerFactory = new net.sf.saxon.TransformerFactoryImpl();
                saxonTransformerFactory.setURIResolver(X3DCatalog.getInstance());
                Transformer saxonTransformer = saxonTransformerFactory.newTransformer(xsltStreamSource);
                saxonTransformer.setErrorListener(new ErrorListener() {
                    @Override
                    public void warning(TransformerException exception) throws TransformerException
                    {
                        transformListener.receive(new CookieMessage(exception.getLocalizedMessage(), CookieMessage.WARNING_LEVEL));
                    }

                    @Override
                    public void error(TransformerException exception) throws TransformerException
                    {
                        transformListener.receive(new CookieMessage(exception.getLocalizedMessage(), CookieMessage.ERROR_LEVEL));
                    }

                    @Override
                    public void fatalError(TransformerException exception) throws TransformerException
                    {
                        transformListener.receive(new CookieMessage(exception.getLocalizedMessage(), CookieMessage.FATAL_ERROR_LEVEL));
                    }
                });

                if (parameterMap != null)
                {
                    parameterMap.keySet().forEach(key ->
                    {
                        saxonTransformer.setParameter(key, parameterMap.get(key));
                    });
                }

                // code from NetBeans Transformable Support
                // inform user about used implementation
                ProtectionDomain domain = saxonTransformer.getClass().getProtectionDomain();
                CodeSource codeSource = domain.getCodeSource();
                if (codeSource == null)
                {
                    transformListener.message(Nb_Using + saxonTransformer.getClass().getName() + Nb_default_JRE_XSLT_processor);
                } 
                else
                {
                    URL location = codeSource.getLocation();
                    transformListener.message(Nb_Using + saxonTransformer.getClass().getName() + Nb_XSLT_processor_from_ + location);
                }

                Proxy proxy = new Proxy(transformListener);
                saxonTransformer.setErrorListener(proxy);

                saxonTransformer.transform(new StreamSource(primaryFileObject.getInputStream()), resultFileStreamResult);

                //tCookie.transform(xslStream, outStream, consoleTransformListener);
                transformListener.message(Nb_XSLT_transformation_complete);
            }
        } 
        catch (IOException | IllegalArgumentException | TransformerException ex)
        {
            transformListener.message(Nb_Exception + ex.getMessage());

            TransformerException transExcept;
            Object detail = null;

            if (ex instanceof TransformerException)
            {
                transExcept = (TransformerException) ex;
                if (ex instanceof TransformerConfigurationException)
                {
                    detail = new DefaultXMLProcessorDetail(transExcept);
                }
            } 
            else if (ex instanceof SAXParseException)
            {
                detail = new DefaultXMLProcessorDetail((SAXParseException) ex);
            } 
            else
            {
                transExcept = new TransformerException(ex);
                detail = new DefaultXMLProcessorDetail(transExcept);
            }

            if (detail != null)
            {
                CookieMessage message = new CookieMessage(message(ex), CookieMessage.FATAL_ERROR_LEVEL, detail);
                transformListener.receive(message);
            }
            saveFilePackResult = null;
        }
        transformListener.moveToFront(true);
        return saveFilePackResult;
    }

    /**
     *
     * @param xed our TopComponent instance managing a single x3d file
     * @param xsltFileResourcePath path to a NB "filesystem" file, such as "X3dTransforms/X3dToX3dvClassicVrmlEncoding.xslt",
     *   setup in the module layer file
     * @return Absolute path in OS filesystem of a file if it should be subsequently opened in the editor
     */
//  ConversionsHelper.saveFilePack origxsltOneFile(X3DEditorSupport.X3dEditor x3dEditor, String xsltFileResourcePath,
//                                             String resultFileExtension, boolean wantOpenResultAccessory,
//                                             boolean xsltIsOSFile)
//  {
//    ConversionsHelper.saveFilePack retrn = null;
//
//    Node[] node = x3dEditor.getActivatedNodes();
//    TransformableCookie tCookie = node[0].getCookie(TransformableCookie.class);
//
//    X3DDataObject dob = (X3DDataObject)x3dEditor.getX3dEditorSupport().getDataObject();
//    FileObject mySrc  = dob.getPrimaryFile();
//    File mySrcFile    = FileUtil.toFile(mySrc);
//    TransformListener consoleTransformListener = TransformListener.getInstance();
//    resultFileExtension = processFileExtension(resultFileExtension);
//    try {
//      // This path is setup in the X3D layer.xml file.
//      consoleTransformListener.message(NbBundle.getMessage(getClass(),"XSLT_transformation_starting")+":");
//      StreamSource xslStream;
//      if(xsltIsOSFile) {
//        File xsltF = new File(xsltFileResourcePath);
//        xslStream = new StreamSource(xsltF);
//        consoleTransformListener.message(xsltF.getName()+"("+mySrcFile.getAbsolutePath()+")");
//      }
//      else {
//        FileObject jarredTransformer = Repository.getDefault().getDefaultFileSystem().findResource (xsltFileResourcePath);
//        xslStream = new StreamSource(jarredTransformer.getInputStream());
//        consoleTransformListener.message(jarredTransformer.getNameExt()+"("+mySrcFile.getAbsolutePath()+")");
//      }
//      consoleTransformListener.moveToFront();
//      consoleTransformListener.setNode(node[0]);
//
//      retrn = ConversionsHelper.getDestinationFile(mySrcFile,mySrc.getName()+resultFileExtension,wantOpenResultAccessory);
//
//      if(retrn == null) {
//        consoleTransformListener.message(NbBundle.getMessage(getClass(),"XSLT_transformation_cancelled"));
//      }
//      else {
//        File myOutFile = retrn.file;
//        myOutFile.createNewFile();
//        StreamResult outStream = new StreamResult(myOutFile);
//        consoleTransformListener.message(NbBundle.getMessage(getClass(),"Writing_") + myOutFile.getAbsolutePath());
//
//        tCookie.transform(xslStream, outStream, consoleTransformListener);
//
//        consoleTransformListener.message(NbBundle.getMessage(getClass(),"XSLT_transformation_complete"));
//      }
//    }
//    catch(Exception ex) {
//      consoleTransformListener.message(NbBundle.getMessage(getClass(),"Exception:__")+ex.getMessage());
//      retrn = null;
//    }
//    consoleTransformListener.moveToFront(true);
//    return retrn;
//  }

    /**
     * Subclass only to put up a separate tab.
     */
    public static class TransformListener extends InputOutputReporter
    {
        public static TransformListener getInstance()
        {
            if (instance == null)
            {
                instance = new TransformListener();
            }
            return instance;
        }
        private static TransformListener instance;

        private TransformListener()
        {
            super(NbBundle.getMessage(BaseConversionsAction.class, "XSLT_Conversion"));
        }
    }

    // Copy from Transformable Support
    private static Throwable unwrapException(Throwable exc)
    {
        Throwable wrapped;
        if (exc instanceof TransformerException)
        {
            wrapped = ((TransformerException) exc).getException();
        } 
        else if (exc instanceof SAXException)
        {
            wrapped = ((SAXException) exc).getException();
        } 
        else
        {
            return exc;
        }
        if (wrapped == null)
        {
            return exc;
        }
        return unwrapException(wrapped);
    }

    /**
     * Extract message from exception or use exception name.
     */
    private static String message(Throwable t)
    {
        String msg = t.getLocalizedMessage();
        return (msg != null ? msg : new ExceptionWriter(t).toString());
    }

    /**
     * Print first four exception lines.
     */
    private static class ExceptionWriter extends PrintWriter
    {
        private int counter = 4;
        private final Throwable t;

        public ExceptionWriter(Throwable t)
        {
            super(new StringWriter());
            this.t = t;
        }

        @Override
        public void println(String s)
        {
            if (counter-- > 0)
            {
                super.println(s);
            }
        }

        @Override
        public void println(Object o)
        {
            if (counter-- > 0)
            {
                super.println(o);
            }
        }

        @Override
        public String toString()
        {
            t.printStackTrace(this);
            flush();
            return ((StringWriter) out).getBuffer().toString();
        }
    }

    private static class Proxy implements ErrorListener
    {
        private final CookieObserver peer;

        public Proxy(CookieObserver peer)
        {
            if (peer == null)
            {
                throw new NullPointerException();
            }
            this.peer = peer;
        }

        @Override
        public void error(TransformerException tex) throws TransformerException
        {
            report(CookieMessage.ERROR_LEVEL, tex);
        }

        @Override
        public void fatalError(TransformerException tex) throws TransformerException
        {
            report(CookieMessage.FATAL_ERROR_LEVEL, tex);

            throw tex;
        }

        @Override
        public void warning(TransformerException tex) throws TransformerException
        {
            report(CookieMessage.WARNING_LEVEL, tex);
        }

        private void report(int level, TransformerException tex) throws TransformerException
        {
    //      if (Util.THIS.isLoggable()) /* then */ {
    //        Util.THIS.debug("[TransformableSupport::Proxy]: report [" + level + "]: ", tex);
    //        Util.THIS.debug("    exception's message = " + tex.getLocalizedMessage());
    //
    //        Throwable tempExc = unwrapException(tex);
    //        Util.THIS.debug("    wrapped exception = " + tempExc.getLocalizedMessage());
    //      }

            Throwable unwrappedExc = unwrapException(tex);
            CookieMessage message = new CookieMessage(
                    message(unwrappedExc),
                    level,
                    new DefaultXMLProcessorDetail(tex));

            peer.receive(message);
        }
    }

}
