package org.web3d.x3d.xj3d.viewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Ignore;

import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

import org.openide.util.Exceptions;

import org.web3d.x3d.X3DDataObject;

import org.web3d.x3d.sai.BrowserEvent;
import org.web3d.x3d.sai.InvalidBrowserException;
import org.web3d.x3d.sai.X3DComponent;
import org.web3d.x3d.sai.X3DScene;

/**
 *
 * @author Terry Norbraten, NPS MOVES
 */
public class Xj3dViewerPanelTest {

    static JFrame frame;
    static String jarredScene;
    static String helloWorldPath;
    static String dirPath;
    Xj3dViewerPanel instance;

    public Xj3dViewerPanelTest() {}

    @BeforeClass
    public static void setUpClass() {
        jarredScene = "X3dExamples/MaterialExample.x3d";
        
        // Extreme workaround for unit testing NB Module projects
        dirPath = Path.of(System.getProperty("nbjunit.workdir")).getParent().toAbsolutePath().toString() + "/classes/";
        helloWorldPath = "org/web3d/x3d/xj3d/viewer/HelloWorld.x3d";
        
        frame = new JFrame(Xj3dViewerPanelTest.class.getSimpleName());
        frame.setSize(800, 600);
    }

    @AfterClass
    public static void tearDownClass() {
        frame.dispose();
        frame = null;
        jarredScene = null;
        helloWorldPath = null;
        dirPath = null;
    }

    @Before
    public void setUp() {
        instance =  new Xj3dViewerPanel();
        frame.add(instance);
        
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(true);
        });
    }

    @After
    public void tearDown() {
        SwingUtilities.invokeLater(() -> {
            frame.setVisible(false);
        });
        
        try {
            instance.shutDownXj3D();
        } catch (InvalidBrowserException ex) {}

        frame.remove(instance);
        frame.validate();
        instance = null;
    }

    /**
     * Test of initialize method, of class Xj3dViewerPanel.
     */
    @Test
    public void testInitialize_String() {
        System.out.println("initialize_String");

        instance.initialize(jarredScene);
        assertNotNull(instance.getX3dComponent().getImplementation());
    }

    /**
     * Test of browserChanged method, of class Xj3dViewerPanel.
     */
    @Test
    public void testBrowserChanged() {
        instance.initialize(jarredScene);
        System.out.println("browserChanged");
        
        BrowserEvent ev = new BrowserEvent(new Object(), 1);
        instance.browserChanged(ev); // just returns
        assertNotNull(instance.getScene());
    }

    /**
     * Test of getX3dComponent method, of class Xj3dViewerPanel.
     */
    @Test
    public void testGetX3dComponent() {
        instance.initialize(jarredScene);
        System.out.println("getX3dComponent");
        
        X3DComponent result = instance.getX3dComponent();
        assertNotNull(result.getImplementation());
    }

    /**
     * Test of getScene method, of class Xj3dViewerPanel.
     */
    @Test
    public void testGetScene() {
        instance.initialize(jarredScene);
        System.out.println("getScene");
        
        X3DScene result = instance.getScene();
        assertNotNull(result);
    }

    /**
     * Test of openXj3dScene method, of class Xj3dViewerPanel.
     * NOTE: On NB 12.4, there appears to be different unit test threading where
     * something happens before it normally would if invoked in a non unit test
     * manner, and fails. Therefore, ignoring this method as a unit test for 
     * now.
     */
    @Ignore
    @Test
    public void testOpenXj3dScene_String() {
        instance.initialize(jarredScene);
        System.out.println("openXj3dScene_String");
        
        String dir = dirPath;
        String path = helloWorldPath;
        instance.openXj3dScene(dir + path);
        assertNotNull(instance.getScene());
    }

    /**
     * Test of openXj3dScene method, of class Xj3dViewerPanel.
     */
    @Test
    public void testOpenXj3dScene_X3DDataObject() {
        instance.initialize(jarredScene);
        System.out.println("openXj3dScene_X3DDataObject");
        
        instance.openXj3dScene(instance.getCurrentX3DDataObject());
        assertNotNull(instance.getScene());
    }
    
    /** Test of openXj3dScene method, of class Xj3dViewerPanel. */
    @Test
    public void testOpenXj3dScene_From_Path_InputStream() {
        instance.initialize(jarredScene);
        System.out.println("openXj3dScene_From_Path_InputStream");
        
        InputStream fis = null;
        
        String dir = dirPath;
        String path = helloWorldPath;
        File f = Path.of(dir + path).toFile();
        if (f.exists())
            System.out.println("\nFound: " + f + "\n");
        else 
            // I'm done, can't find it testing this Module
            return;
        
        try {
            fis = new FileInputStream(f);
            instance.currentFileName = f.getName();
            instance.openXj3dScene(f.getParentFile().getAbsolutePath(), fis);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            fail(ex.getMessage());
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        assertNotNull(instance.getScene());
    }
    
    // From: https://stackoverflow.com/questions/13011892/how-to-locate-the-path-of-the-current-project-directory-in-java-ide
    // For whatever reason, when unit testing a NB Module project, the property
    // user.dir becomes user.home <- bizarre
    private void showProperties()
    {
        Properties properties = System.getProperties();
        Enumeration<Object> enumeration = properties.keys();
        for (int i = 0; i < properties.size(); i++) {
            Object obj = enumeration.nextElement();
            System.out.println("Key: " + obj + "\tOutPut=" + System.getProperty(obj.toString()));
        }
    }

    /**
     * Test of openXj3dScene method, of class Xj3dViewerPanel.
     */
    @Test
    public void testOpenXj3dScene_String_From_Jar_InputStream() {
        instance.initialize(jarredScene);
        System.out.println("openXj3dScene_String_From_Jar_InputStream");
        
        String path = "X3dExamples/HelloWorld.x3d";
        FileObject jarredFile;
        try {
            jarredFile = FileUtil.getConfigRoot().getFileObject(path);
            InputStream is = jarredFile.getInputStream();
            URL url = getClass().getClassLoader().getResource("org/web3d/x3d/templates/");
            instance.currentFileName = jarredFile.getNameExt();
            instance.openXj3dScene(url.toExternalForm(), is);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        
        assertNotNull(instance.getScene());
        assertNotNull(instance.getX3dComponent());
    }

    /**
     * Test of refreshXj3dScene method, of class Xj3dViewerPanel.
     */
    @Test
    public void testRefreshXj3dScene() {
        instance.initialize(jarredScene);
        System.out.println("refreshXj3dScene");

        instance.refreshXj3dScene();
        assertNotNull(instance.getScene());
    }

    /**
     * Test of closeXj3dScene method, of class Xj3dViewerPanel.
     */
    @Test
    public void testCloseXj3dScene() {
        instance.initialize(jarredScene);
        System.out.println("closeXj3dScene");
        
        X3DDataObject xObj = instance.getCurrentX3DDataObject();
        instance.closeXj3dScene(xObj);
        assertNotNull(instance.getScene());
    }

    /**
     * Test of shutDownXj3D method, of class Xj3dViewerPanel.
     */
    @Test
    public void testShutDownXj3D() {
        instance.initialize(jarredScene);
        System.out.println("shutDownXj3D");
        
        instance.shutDownXj3D();
        assertNull(instance.getX3dComponent());
    }

    /** Entry point for the class
     * 
     * @param args command line arguments (if any)
     */
    public static void main(String args[]) {
//      org.junit.runner.JUnitCore.main("org.web3d.x3d.xj3d.viewer.Xj3dViewerPanelTest");
        Xj3dViewerPanelTest.setUpClass();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Xj3dViewerPanelTest test = new Xj3dViewerPanelTest();
        test.setUp();
        
//        test.testInitialize_String();
        test.testOpenXj3dScene_String();
//        test.testOpenXj3dScene_From_Path_InputStream();
//        test.testOpenXj3dScene_String_From_Jar_InputStream();
    }
}
