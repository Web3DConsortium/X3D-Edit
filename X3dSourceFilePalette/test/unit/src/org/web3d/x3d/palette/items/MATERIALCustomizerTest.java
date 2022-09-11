package org.web3d.x3d.palette.items;

import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Terry Norbraten, NPS MOVES
 */
public class MATERIALCustomizerTest {

    MATERIALCustomizer instance;

    public MATERIALCustomizerTest() {
        instance = null;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public static void main(String args[]) {
//      org.junit.runner.JUnitCore.main("org.web3d.x3d.xj3d.viewer.MATERIALCustomizerTest");
        MATERIALCustomizerTest test = new MATERIALCustomizerTest();
        test.setUp();
        test.testGetNameKey();
        test.tearDown();
    }

    @Before
    public void setUp() {

        MATERIAL mat = new MATERIAL();
        mat.initialize();

        // Bogus menu pull down list enumerator
        mat.setUSEVector(new Vector<>(10));

        instance = new MATERIALCustomizer(mat, null);
        instance.showDialog(false);
    }

    @After
    public void tearDown() {
        instance = null;
    }

    /**
     * Test of getNameKey method, of class MATERIALCustomizer.
     */
    @Test
    public void testGetNameKey() {
        System.out.println("getNameKey");
        String expResult = "NAME_X3D_MATERIAL";
        String result = instance.getNameKey();
        assertEquals(expResult, result);
    }

    /**
     * Test of unloadInput method, of class MATERIALCustomizer.
     */
    @Test
    public void testUnloadInput() {
        System.out.println("unloadInput");
        instance.unloadInput();
        assertNotNull(instance.getMATERIAL().getDiffuseColor());
    }

    /**
     * Test of extractContent method, of class MATERIALCustomizer.
     */
    @Test
    public void testExtractContent() {
        System.out.println("extractContent");
        JComponent result = instance.extractContent();
        assertNotNull(result);
    }

    /**
     * Test of getMATERIAL method, of class MATERIALCustomizer.
     */
    @Test
    public void testGetMATERIAL() {
        System.out.println("getMATERIAL");
        MATERIAL result = instance.getMATERIAL();
        assertNotNull(result);
    }

    /**
     * Test of nullTo0 method, of class MATERIALCustomizer.
     */
    @Test
    public void testNullTo0() {
        System.out.println("nullTo0");
        JTextField tf = new JTextField();
        tf.setText("22");
        String expResult = tf.getText();
        String result = instance.nullTo0(tf);
        assertEquals(expResult, result);
    }

}
