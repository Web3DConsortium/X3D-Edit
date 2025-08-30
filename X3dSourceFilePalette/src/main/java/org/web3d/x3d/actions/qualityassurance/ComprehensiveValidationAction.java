/*
 * Copyright (c) 1995-2024 held by the author(s).  All rights reserved.
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
package org.web3d.x3d.actions.qualityassurance;

//import net.sf.saxon.s9api.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JMenuItem;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import net.sf.saxon.Configuration;
import org.netbeans.api.xml.cookies.CookieMessage;
import org.netbeans.api.xml.cookies.CookieObserver;
import org.netbeans.spi.xml.cookies.CheckXMLSupport;
import org.netbeans.spi.xml.cookies.TransformableSupport;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport.X3dEditor;
import org.web3d.x3d.actions.conversions.BaseConversionsAction;
import org.web3d.x3d.tools.X3dDoctypeChecker;
import org.web3d.x3d.tools.X3dValuesRegexChecker;

@ActionID(id = "org.web3d.x3d.actions.qualityassurance.ComprehensiveValidationAction", category = "X3D-Edit")

@ActionRegistration(   iconBase = "org/web3d/x3d/resources/CheckMark.png",
                    displayName = "#CTL_ComprehensiveValidationAction",
                           lazy = true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/&Author Workflow", position = 40),
  @ActionReference(path = "Toolbars/X3D-Edit Author Workflow", position = 40),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Author Workflow", position = 40),
  @ActionReference(path = "Menu/&X3D-Edit/&Quality Assurance (QA)", position = 300),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Quality Assurance (QA)", position = 300),
  @ActionReference(path = "Shortcuts", name = "CS-Q"), // shortcut control-shift-Q
  // see Apache NetBeans > Help > Keyboard Shortcuts Card for other shortcuts
})

public class ComprehensiveValidationAction extends BaseConversionsAction //XmlValidationAction
{
  @Override
  protected String transformSingleFile(X3dEditor x3dEditor)
  {
    X3DDataObject x3dDataObject = (X3DDataObject) x3dEditor.getX3dEditorSupport().getDataObject();
    FileObject mySrc = x3dDataObject.getPrimaryFile();

    File sourceFile = FileUtil.toFile(mySrc);

    // TODO omit ant task once the following Java-based invocation is working

    // ----------------------------------------------------------------------
    // performAction() algorithm:
    // get active file in NetBeans editor
    // locate schematron build.xml:    org/web3d/x3d/externals/schematron/build.xml
    // locate schematron stylesheet:   org/web3d/x3d/externals/schematron/X3dSchematronValidityChecks.xslt
    // locate svrl to text stylesheet: org/web3d/x3d/externals/schematron/SvrlReportText.xslt
    // compute ant properties, collecting various filenames and paths
    // invoke stylesheets via build.xml, passing parameters as ant properties
    // ----------------------------------------------------------------------

    // necessary file parameters are reflected in NetBeans X3D-Edit layer.xml, and set in XmlValidationAction.java plus schematron build.xml

    // performValidationAntTask("validateSceneAll"); // schematron build.xml

    // difficulty to pass X3dC14n.jar visibility to ant build.xml file for invoking java class
    // instead follow pattern in X3dDoctypeCheckerAction:

    RequestProcessor.getDefault().post(new ComprehensiveValidationTask(sourceFile.getAbsolutePath(), x3dDataObject));
    return null;
  }

  private class ComprehensiveValidationTask implements Runnable
  {
        private final String sourcePath;
        private       String validationLog;
        private final X3DDataObject x3dDataObject;

        ComprehensiveValidationTask(String pathToFile, X3DDataObject x3dDataObject)
        {
          sourcePath = pathToFile;
          this.x3dDataObject = x3dDataObject;
        }

        @Override
        public void run()
        {   
            // Display output to user via console
            InputOutput io = IOProvider.getDefault().getIO("X3D Quality Assurance (QA)", false); // (QA)", false);
            io.select();  // Bring to the front
            try (OutputWriter outputWriterPlain = io.getOut()) 
            {
                try (OutputWriter outputWriterError = io.getErr()) 
                {
                    outputWriterPlain.println("--------- X3D Validator checks commenced for " + x3dDataObject.getPrimaryFile().getNameExt() + " ---------");
                    
                    CookieObserver myCookieObserver = new MyCookieObserver(null, false, outputWriterError, outputWriterPlain);

                    // Well-formed XML check
                    outputWriterPlain.println();
                    outputWriterPlain.println("Performing well-formed XML check...");
                    CheckXMLSupport checker = x3dDataObject.getCheckXmlHelper();
                    if (checker.checkXML(myCookieObserver)) { //observerSilent)) {
                        outputWriterPlain.println("Well-formed XML check: pass");
                    }
                    else {
                        outputWriterError.println("Well-formed XML check: fail!");
                    }

                    // X3dDoctypeChecker
                    outputWriterPlain.println();
                    outputWriterPlain.println("Performing DOCTYPE check...");
                    X3dDoctypeChecker doctypeChecker = new X3dDoctypeChecker();
                    validationLog = doctypeChecker.processScene(sourcePath);
                    if (validationLog.contains(X3dDoctypeChecker.errorToken) || validationLog.contains(X3dDoctypeChecker.warningToken)) {
                        //outErr.println(validationLog);    won't print red
                        String[] sa = validationLog.split("\n");
                        for (String s : sa) {
                            outputWriterError.println(s);
                        }
                    } else {
                        //out.println(sourcePath);
                        outputWriterPlain.println(validationLog);
                    }
                    outputWriterPlain.println();
                    outputWriterPlain.println("Performing DTD validation...");
                    ValidateXMLSupport dtdValidator = x3dDataObject.getDtdValidator();
                    if (dtdValidator.validateXML(myCookieObserver)) { //observerSilent)) {
                        outputWriterPlain.println("XML DTD validation: pass");
                    } else {
                        outputWriterError.println("XML DTD validation: fail!");
                    }
                    outputWriterPlain.println();
                    outputWriterPlain.println("Performing X3D schema validation...");

                    // XML Schema validation
                    ValidateXMLSupport schemaValidator = x3dDataObject.getSchemaValidator(); //dob.getValidateHelper();
                    if (schemaValidator.validateXML(myCookieObserver)) { //observerSilent)) {
                        outputWriterPlain.println("XML schema validation: pass");
                    } else {
                        outputWriterError.println("XML schema validation: fail!");
                    }

                    // continue with regex checker
                    outputWriterPlain.println();
                    outputWriterPlain.println("Performing X3D regular expression (regex) values check...");
                    X3dValuesRegexChecker x3dValuesRegexChecker = new X3dValuesRegexChecker(sourcePath);
                    validationLog = x3dValuesRegexChecker.processScene();
                    if (validationLog.length() > 0) {
                        String[] sa = validationLog.split("\n");
                        for (String s : sa) {
                            outputWriterError.println(s);
                        }
                    }
                    outputWriterPlain.println("X3D regex check: complete");
                    
                    // shared objects for conversions
                    FileSystem fileSystem;
                    FileObject fileObject;
                    File classicVrmlOutputFile, schematronOutputFile, schematronOutputFile2; // schematron is two-pass process
                    
                    StreamResult result;
                    BufferedReader rdr;
                    TransformableSupport transformer = x3dDataObject.getTransformXmlHelper();
                    
//////                if (false) // skip this test, code block uses obsolete version of saxon
//////                {
//////                    // X3dToX3dvClassicVrmlEncoding
//////                    try {
//////                        fileSystem = FileUtil.getConfigRoot().getFileSystem();
//////                        fileObject = fileSystem.findResource("Schematron/X3dToX3dvClassicVrmlEncoding.xslt");
//////                        
//////                        Source stylesheetStreamSource = new StreamSource(fileObject.getInputStream());
//////                        classicVrmlOutputFile = File.createTempFile(x3dDataObject.getPrimaryFile().getNameExt() + "_", "_classicVrmlOutput.txt");
//////                        classicVrmlOutputFile.deleteOnExit();
//////                        result = new StreamResult(classicVrmlOutputFile);
//////                        
//////                        outputWriterPlain.println();
//////                        outputWriterPlain.println("Performing X3dToX3dvClassicVrmlEncoding.xslt conversion check...");
//////
//////                        // continue with XSLT transformation (depends on saxon, not JAXB since XSLT 2.0)
//////                        // Example code from saxon-resources he.S9APIExamples TransformA public void run() throws SaxonApiException {
//////                        Processor processor = new Processor(false);
//////                        XsltCompiler compiler = processor.newXsltCompiler();
//////                        XsltExecutable stylesheet = compiler.compile(stylesheetStreamSource); // new File("styles/books.xsl")));
//////                        Serializer out = processor.newSerializer(classicVrmlOutputFile); // new File("books.html"));
//////                        out.setOutputProperty(Serializer.Property.METHOD, "html");
//////                        out.setOutputProperty(Serializer.Property.INDENT, "yes");
//////                        Xslt30Transformer xslt30Transformer = stylesheet.load30();
//////                        xslt30Transformer.transform(new StreamSource(classicVrmlOutputFile /*new File("data/books.xml")*/), out);
//////                        
//////                        outputWriterPlain.println("ClassicVrml output written to " + classicVrmlOutputFile.getPath()); // books.html");
//////                        outputWriterPlain.println("TODO how to report conversion log?  Looking for diagnostic messages as output of interest...");
//////                    }
//////                    catch (SaxonApiException sae)
//////                    {
//////                        System.out.println ("SaxonApiException " + sae.getMessage());
//////                        Exceptions.printStackTrace(sae);
//////                    } 
//////                    catch (FileStateInvalidException fsie) 
//////                    {
//////                        System.out.println ("FileStateInvalidException " + fsie.getMessage());
//////                        Exceptions.printStackTrace(fsie);
//////                    }
//////                    catch (FileNotFoundException fnf) 
//////                    {
//////                        System.out.println ("FileNotFoundException " + fnf.getMessage());
//////                        Exceptions.printStackTrace(fnf);
//////                    }
//////                    catch (IOException ioe) 
//////                    {
//////                        System.out.println ("IOException " + ioe.getMessage());
//////                        Exceptions.printStackTrace(ioe);
//////                    }
//////                } // hide
                    
/* Xalan, fails on XSLT 2.0
                    try {
                        fs = FileUtil.getConfigRoot().getFileSystem();

                        // X3dToX3dvClassicVrmlEncoding
                        fo = fs.findResource("Schematron/X3dToX3dvClassicVrmlEncoding.xslt");
                        Source src = new StreamSource(fo.getInputStream());
                        classicVrmlOutput = File.createTempFile(x3dDataObject.getPrimaryFile().getNameExt() + "_", "_classicVrmlOutput.txt");
                        classicVrmlOutput.deleteOnExit();
                        result = new StreamResult(classicVrmlOutput);
                        outputWriterPlain.println();
                        outputWriterPlain.println("Performing X3dToX3dvClassicVrmlEncoding.xslt conversion check...");
                        transformer.transform(src, result, new MyCookieObserver(new SkipIfStartsWithUsing(), false, outputWriterError, outputWriterPlain)); //observerVerbose);
//        rdr = new BufferedReader(new FileReader(classicVrmlOutput));
//        while(rdr.ready())
//          out.println(rdr.readLine());
                    } catch (IOException | TransformerException ex) {
                        String errorMessage
                                = // ex.getClass().getSimpleName() + " in ComprehensiveValidationAction:\n" +
                                ex.getLocalizedMessage();
                        if ((errorMessage != null) && !errorMessage.isEmpty()) {
                            outputWriterError.println(errorMessage);
                        } else {
                            outputWriterPlain.println("No errors or warnings found.");
                        }
                    }
*/

                    // continue with X3D Schematron, a two-pass process
                    try {
                        fileSystem = FileUtil.getConfigRoot().getFileSystem(); // repeat
                        outputWriterPlain.println();
                        outputWriterPlain.println("Performing X3D Schematron check...");
                        SkipIfStartsWithUsing usingFilter = new SkipIfStartsWithUsing();
                        final CookieObserver myCo = new MyCookieObserver(usingFilter, true, outputWriterError, outputWriterPlain);
                        // X3D Schematron 1
                        fileObject = fileSystem.findResource("Schematron/X3dSchematronValidityChecks.xslt");
                        StreamSource inputStreamSource = new StreamSource(fileObject.getInputStream());
                        schematronOutputFile = File.createTempFile(x3dDataObject.getPrimaryFile().getNameExt() + "_", "_schematronOutput_1.txt");
                        schematronOutputFile.deleteOnExit();
                        result = new StreamResult(schematronOutputFile);

                        /* do this because I can't find a way to substitute saxon for xalan in TransformableSupport, xalan is needed for other things. */
                        /* The large validitychecks style sheet includes some xslt 2.0 statements */
                        
                
                // https://saxonica.plan.io/issues/5980
//              Processor processor = new Processor(false);
                Configuration configuration = new Configuration();
                configuration.setParseOptions(configuration.getParseOptions().withParserProperty("http://www.oracle.com/xml/jaxp/properties/" + "entityExpansionLimit", "120000"));
                configuration.setParseOptions(configuration.getParseOptions().withParserProperty("http://www.oracle.com/xml/jaxp/properties/" + "totalEntitySizeLimit", "50000000"));
                configuration.setParseOptions(configuration.getParseOptions().withParserProperty("http://www.oracle.com/xml/jaxp/properties/" + "maxGeneralEntitySizeLimit", "50000000"));
                
                        TransformerFactory saxonTransformerFactory = new net.sf.saxon.TransformerFactoryImpl(configuration);
                        Transformer        saxonTransformer = saxonTransformerFactory.newTransformer(inputStreamSource);
                        saxonTransformer.setErrorListener(new ErrorListener() {
                            @Override
                            public void warning(TransformerException exception) throws TransformerException {
                                System.err.println("*** saxonTransformer warning " + exception.getMessageAndLocation());
                                myCo.receive(new CookieMessage(exception.getLocalizedMessage(), CookieMessage.WARNING_LEVEL));
                            }

                            @Override
                            public void error(TransformerException exception) throws TransformerException {
                                System.err.println("*** saxonTransformer error( " + exception.getMessageAndLocation());
                                myCo.receive(new CookieMessage(exception.getLocalizedMessage(), CookieMessage.ERROR_LEVEL));
                            }

                            @Override
                            public void fatalError(TransformerException exception) throws TransformerException {
                                System.err.println("*** saxonTransformer fatalError( " + exception.getMessageAndLocation());
                                myCo.receive(new CookieMessage(exception.getLocalizedMessage(), CookieMessage.FATAL_ERROR_LEVEL));
                            }
                        });
                        saxonTransformer.transform(x3dDataObject.getFreshSaxSource(), result);
                        System.out.println("*** [debug] first scematron conversion complete");

                        // X3D Schematron 2
                        // replace the transformer with one based on the output from the last check
                        transformer = new TransformableSupport(new StreamSource(new FileInputStream(schematronOutputFile)));  // Use output from last
                        fileObject = fileSystem.findResource("Schematron/SvrlReportText.xslt");
                        inputStreamSource = new StreamSource(fileObject.getInputStream());
                        schematronOutputFile2 = File.createTempFile(x3dDataObject.getPrimaryFile().getNameExt() + "_", "_schematronOutput_2.txt");
                        schematronOutputFile2.deleteOnExit();
                        result = new StreamResult(schematronOutputFile2);

                        transformer.transform(inputStreamSource, result, myCo);
                        // Any output is an error
                        rdr = new BufferedReader(new FileReader(schematronOutputFile2));
                        while (rdr.ready()) {
                            String s = usingFilter.filter(rdr.readLine());
                            if (s != null) {
                                outputWriterError.println(s);
                            } else {
                                outputWriterPlain.println("No errors or warnings found.");
                            }
                        }
                        System.out.println("*** [debug] second scematron conversion complete");

                    // TODO also redo other validation actions to match
                    // future TODO:  figure out how to do Xj3D checks
                    } catch (IOException | IllegalArgumentException | TransformerException ex) {
                        String errorMessage = "Exception: " + ex.getLocalizedMessage();
                        if ((errorMessage != null) && !errorMessage.isEmpty()) {
                            outputWriterError.println(errorMessage);
                            ex.printStackTrace(System.err);
                        }
                    }
                    outputWriterPlain.println();
                    outputWriterPlain.println("--------- X3D Validator checks complete for " + x3dDataObject.getPrimaryFile().getNameExt() + " ---------");
// TODO restore     outputWriterPlain.println("--------- X3D Validator online at https://savage.nps.edu/X3dValidator ---------");
                    outputWriterPlain.println();
                    // removes asterisk
                }
            }
        }
    }

  private class MyCookieObserver implements CookieObserver
  {
    ObserverFilter obsFilter;
    OutputWriter outErr, out;
    MyCookieObserver(ObserverFilter obsFilter, boolean useErrOutOnly, OutputWriter outErr, OutputWriter out)
    {
      this.obsFilter = obsFilter;
      this.out = out;
      if(useErrOutOnly)
        this.out = outErr;
      else
        this.outErr = outErr;
    }

    @Override
    public void receive(CookieMessage msg)
    {
      String s = obsFilter==null?msg.getMessage():obsFilter.filter(msg.getMessage());

      if(s != null && s.length()>0) {
      int level = msg.getLevel();
      if(level==CookieMessage.ERROR_LEVEL || level==CookieMessage.FATAL_ERROR_LEVEL)
        outErr.println(msg.getMessage());
      else
        out.println(msg.getMessage());
      }
    }
  }

  private class SkipIfStartsWithUsing implements ObserverFilter
  {
    @Override
    public String filter(String s)
    {
      if(s.startsWith("Using"))
        return null;
      return s;
    }
  }

  private interface ObserverFilter
  {
    public String filter(String s);
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(ComprehensiveValidationAction.class, "CTL_ComprehensiveValidationAction");
  }

  /**
   * Do this because this call in the super creates a new one every time, losing any
   * previous tooltip.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(ComprehensiveValidationAction.class, "CTL_ComprehensiveValidationAction_tt"));
    return mi;
  }

  @Override
  protected String iconResource()
  {
    return "org/web3d/x3d/resources/CheckMark.png";
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }
}
