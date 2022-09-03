// ecmascript:  // uncomment this line if this code appears in CDATA section embedded inside an X3D Script node

// TODO !!!     *** Authors please update/remove unneeded methods and metadata/information entries in this file ***

// Description: Editable example ECMAscript (javascript) source file for use with X3D Script node
// Filename:    newECMAscript.js
// Author:      Don Brutzman
// Identifier:  http://www.web3d.org/x3d/content/examples/newECMAscript.js
// Created:     11 December 2007
// Revised:      3 November 2013
// Reference:   http://www.web3d.org/x3d/content/X3dTooltips.html#Script
// Reference:   http://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Scripts
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-FDIS-19775-1.2-X3D-AbstractSpecification/Part01/components/scripting.html
// Reference:   http://www.web3d.org/x3d/specifications/ISO-IEC-19777-1-X3DLanguageBindings-ECMAScript/Part1/X3D_ECMAScript.html
// Reference:   http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter09-EventUtilitiesScripting/newECMAscriptTest.x3d
// License:     http://www.web3d.org/x3d/content/examples/license.html

function initialize () // no parameters allowed
{
    // The initialize() function is automatically invoked when the Script node is first activated, prior to other events
    // The initialize() function can also be invoked by other functions, if appropriate

    tracePrint ('initialize() method commenced...');
    
    test (); // *** authors can remove this line and the example unit block below  ***

    // TODO:    *** authors can insert initialization code here (if any is needed) ***

    tracePrint ('initialize() method complete' + '\n');
}

// Note that inputOnly fields can only appear as names of handling functions (like the following).
// Not providing a named method for each inputOnly field means that Script ignores those events at run time.
   
function someInputOnlyFieldName (eventValue, timestamp) // eventValue is routed into Script, timestamp parameter optional
{
    // Author TODO:  authors can insert script code here; variable names must match Script field definitions
    //               outputOnly      fields can only be on left-hand side  (LHS) of assignment statements
    //               initializeOnly  fields can only be on right-hand side (RHS) of assignment statements
    //               inputOutput     fields can either be on LHS or RHS of assignment statements

    tracePrint ('someInputOnlyFieldName received eventValue=' + eventValue + ' at timestamp=' + timestamp);
    
    // Author TODO: edit this assignment statement to perform computation of interest
    someOutputOnlyFieldName = someExpression (eventValue);  
}

// ================================================================================================
// predefined script methods are optional and may be overridden here for advanced functionality

function prepareEvents ()
{
    // optional method, can be omitted
    // invoked before any ROUTE processing occurs during the current frame's event cascade
    // example uses:  checking network buffers, checking asynchronous external processes
}
function eventsProcessed ()
{
    // optional method, can be omitted
    // invoked after the Script node handles all of its input events
    // use this method if multiple inputs are needed before generating a response
}
function shutdown ()
{
    // optional method, can be omitted
    // invoked when the X3D scene is being closed, or when Script node is being unloaded/replaced
    // allows graceful shutdown by releasing resources, closing network connections, providing final outputs, etc.
}
// utility methods ================================================================================

// These variable declarations may be hidden or removed if defined in parent X3D Script node:
var traceEnabled = true; // local variable
var scriptName   = 'newECMAscript.js'; // Author TODO:  authors need to edit this scriptName for trace, or else remove all uses

function tracePrint (stringValue)
{
  if (traceEnabled) forcePrint (stringValue);
}
function forcePrint (stringValue)
{
    // Browser.print, println is the function to output text on the X3D player's console window
    if     ((!stringValue) || (stringValue.length === 0)) // empty string
         Browser.print('\n');
    else if (scriptName.length > 0) 
         Browser.print ('[' + scriptName + '] ' + stringValue + '\n'); // newline character works around BS Contact bug
    else Browser.print (                          stringValue + '\n'); // so these statements are the moral equivalent of println
}
// ================================================================================================
// example test block, demonstrated and tested by newECMAscriptTest.x3d

// global variable declaration and initialization
var exampleData = [];
exampleData[0] = 'Hello';
exampleData[1] = 'World';

// SFString/MFString not defined in ECMAScript SAI
// var newSFString = new SFString (exampleData[0] + ' ' + exampleData[1]);
// var newMFString = new MFString (exampleData[0], exampleData[1]);

function test ()
{
    tracePrint ('exampleData=' + exampleData);
//  tracePrint ('newSFString=' + newSFString);
//  tracePrint ('newMFString=' + newMFString);
}
// ================================================================================================
