/**
 * // Author TODO:  change the following metadata
 *
 * newX3dScript.java
 *
 * @author      TODO-yourNameHere
 * Filename:    newX3dScript.java
 * Description: Editable example Java source file for use with X3D Script node
 * Identifier:  http://www.web3d.org/x3d/content/examples/newX3dScript.java
 * Created:     __ Month 201_
 * Revised:     __ Month 201_
 * Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
 * Reference:   http://www.web3d.org/documents/specifications/19775-1/V3.3/Part01/components/scripting.html
 * Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-19777-2-X3DLanguageBindings-Java/Part2/X3D_Java.html
 * License:     ../../license.html
 */
public class newX3dScript {  // Author TODO:  change class name

    // Author TODO:  ensure that this method's name has corresponding inputOnly field name
    // Note that inputOnly fields must appear as names of handling functions (like the following)
    // Failure to provide a named method matching each inputOnly field means Script ignores those events at run time
    
    public void myInputOnlyFieldName(myInputValue, timestamp) // all timestamp parameters optional
    {
        // Author TODO:  authors can insert script code here; variable names must match Script field definitions
        //               outputOnly      fields can only be on left-hand side  (LHS) of assignment statements
        //               initializeOnly  fields can only be on right-hand side (RHS) of assignment statements
        //               inputOutput     fields can either be on LHS or RHS of assignment statements

        myOutputOnlyFieldName = someExpression(myInputValue);  // Author TODO:  replace this assignment statement
    }

    //--------------------------------------------------------------------------------------------
    // The following predefined optional methods may also be overridden for advanced functionality
    //--------------------------------------------------------------------------------------------
    
    /** When did the last frame start */
    private long lastStartTime;

    /**
     * Notification that the script has completed the setup and should go
     * about its own internal initialization.
     */
    public void initialize() 
    {
        // example code:
        lastStartTime = System.currentTimeMillis();

        // TODO author code here, if any
    }

    /**
     * prepareEvents() is invoked before any ROUTE processing occurs during the current frame's event cascade.
     * Example uses:  checking network buffers, checking asynchronous external processes.
     */
    public void prepareEvents() 
    {
        // example code:
        float frameTime = (System.currentTimeMillis() - lastStartTime) / 1000f;
        lastStartTime = System.currentTimeMillis();
        float fps = 1.0f / frameTime;
        System.out.println("Frames per second rendering rate: " + fps);

        // TODO author code here, if any
    }

    /**
     * eventsProcessed() is invoked after the Script node handles all of its input events.
     * Use this method if multiple inputs are needed before generating a response.
     */
    public void eventsProcessed()
    {
        // TODO author code here, if any
    }

    /**
     * shutdown() is invoked when the X3D scene is being closed, or when Script node is being unloaded/replaced.
     * Allows graceful shutdown by releasing resources, closing network connections, providing final outputs, etc.
     */
    public void shutdown()
    {
        // TODO author code here, if any
    }
}
