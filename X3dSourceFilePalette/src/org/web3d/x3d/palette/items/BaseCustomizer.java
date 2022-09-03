/*
Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.

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

package org.web3d.x3d.palette.items;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.java.dev.colorchooser.ColorChooser;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.actions.LaunchX3dExamplesAction;
import org.web3d.x3d.options.X3dOptions;
import org.web3d.x3d.palette.BetterJTextField;
import org.web3d.x3d.types.X3DNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFColor;
import static org.web3d.x3d.actions.BaseViewAction.X3D_TOOLTIPS;
import org.web3d.x3d.palette.X3DPaletteUtilities;

/**
 * In the IDE Log, opening any of the customizer forms in the gui builder dumps a stack trace saying it cannot open this
 * file:  "Could not use the declared superclass: org.web3d.x3d.palette.items.BaseCustomizer".  It can't for two reasons:
 * 1. it's abstract, and I need it to be; 2) it's not coded as a Java Bean, and I don't care
 * about that.  According to NB postings, the dump is a "benign" error and can be ignored.
 */
/**
 * BaseCustomizer.java
 * Created on July 11, 2007, 1:57 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public abstract class BaseCustomizer extends JPanel
{
  private DialogDescriptor buttonsDialogDescriptor, exitDescriptor = null;
  private Dialog buttonsDialogDisplayer, exitDialog = null;
  private boolean dropOK;
  private boolean wantDialog;
  private BaseX3DElement baseX3DElement;
  private final DecimalFormat formatter = new DecimalFormat("0.######");
  static protected String newLine = System.getProperty("line.separator");

  private JCheckBox prependNewLineCB;
  private JCheckBox appendNewLineCB;
  private static boolean wantLeadingLineFeed = false; // by default for in-place edits
  private static boolean wantTrailingLineFeed = false;

  private final JCheckBox   appendTraceEventsCB = new JCheckBox("Trace", false);
  private final JCheckBox appendVisualizationCB = new JCheckBox("Visualize", false);

  // initialize the expected internalization (I18N) localization (L10N) of decimal separator (decimal point)
  private final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();

  public BaseCustomizer(BaseX3DElement currentBaseX3DElement)
  {
    this.baseX3DElement = currentBaseX3DElement;
    
//    SwingUtilities.invokeLater(() -> {
//        // TODO adjust for resizable DEFUSEPanel
////        getDEFUSEpanel().resetHtmlPaneVisibility(); // close HTML CSS section if not needed
//    });

    decimalFormatSymbols.setDecimalSeparator('.');
  }

  protected void enableTraceEventsCB (boolean enabler)
  {
      appendTraceEventsCB.setEnabled(enabler);
  }

  protected void enableAppendVisualizationCB (boolean enabler)
  {
      appendVisualizationCB.setEnabled(enabler);
  }

  /**
   * entered when item was successfully dropped
   * @param showNewLineOption
   * @return
   */
  public DialogReturnEnumeration showDialog(boolean showNewLineOption)
  {
    boolean dragDrop = true;
    return showDialog(NbBundle.getMessage(getClass(),"LBL_Customizer_InsertPrefix"), dragDrop, showNewLineOption);
  }
    
  // class visibility for callback scrutiny
  JButton windowCloserButton        = new JButton();
  JButton acceptChangesDialogButton = new JButton();
  JButton discardChangesButton      = new JButton();

  /**
   * entered directly if editing in place
   * @param titlePrefix
   * @param dragDrop
   * @param showNewLineOption
   * @return indication of accept, or cancel selections
   */
  public DialogReturnEnumeration showDialog(String titlePrefix, final boolean dragDrop, final boolean showNewLineOption)
  {
    final BaseX3DElement currentX3dElement = getBaseX3DElement(); // expose currentElement within inner class
    
    // this actionListener is multipurpose...
    final ActionListener actionListener = (ActionEvent event) -> {
        boolean saveModelCustomizerAccept  = ((buttonsDialogDescriptor != null) && buttonsDialogDescriptor.getValue().equals(DialogDescriptor.OK_OPTION));
        boolean saveModelWindowCloseAccept = ((         exitDescriptor != null) && exitDescriptor.getValue() == DialogDescriptor.YES_OPTION);
        if (saveModelCustomizerAccept || saveModelWindowCloseAccept)
        {
            try {
                unloadInput(); // defined by each customizer, saves displayed values
                currentX3dElement.setTraceEventsSelectionAvailable  (  appendTraceEventsCB.isSelected());
                currentX3dElement.setVisualizationSelectionAvailable(appendVisualizationCB.isSelected());
                dropOK = true; // accept input
                wantDialog = false;  // don't need to retry
                // TODO finalize serialization in case window closes?
            }
            catch (IllegalArgumentException ex) {
                String   acceptString = NbBundle.getMessage(getClass(), "LBL_accept");
                String tryAgainString = NbBundle.getMessage(getClass(), "LBL_tryagain");
                String   cancelString = NbBundle.getMessage(getClass(), "LBL_cancel");
                String[] buttonBundleMessages = new String[]{acceptString, tryAgainString, cancelString};
                
                dropOK = false;
                NotifyDescriptor illegalArgumentNotifyDescriptor = new NotifyDescriptor(
                        "<html><p align='center'>" + ex.getMessage() + "</p><br /><p align='center'>" + NbBundle.getMessage(getClass(), "LBL_try_again_question") + "</p></html>",
                        NbBundle.getMessage(getClass(), "LBL_data_error"),
                        NotifyDescriptor.DEFAULT_OPTION,
                        NotifyDescriptor.QUESTION_MESSAGE,
                        buttonBundleMessages,
                        acceptString);//,
                
                Object response = DialogDisplayer.getDefault().notify(illegalArgumentNotifyDescriptor); //dd);
                
                if      (response == tryAgainString) //NotifyDescriptor.NO_OPTION)  // No = "don't accept error: retry"
                    wantDialog = true;
                else if (response == acceptString) { //NotifyDescriptor.YES_OPTION) {  // yes = "accept what I chose, errors and all"
                    dropOK = true; // accept input
                    wantDialog = false;
                }
                else { //if(resp == NotifyDescriptor.CANCEL_OPTION)
                    dropOK = false;
                    wantDialog = false;
                }
            }
            if(showNewLineOption) // only possible during dragDrop
            {
                wantLeadingLineFeed  = prependNewLineCB.isSelected();
                wantTrailingLineFeed = appendNewLineCB.isSelected();
            }
            else if (dragDrop)    // use defaults instead of user selections (since none provided)
            {
                wantLeadingLineFeed  = X3dOptions.getPrependNewline();
                wantTrailingLineFeed = X3dOptions.getAppendNewline();
            }
            else // editing in place, not dragDrop, so both values remain false,
            {
                wantLeadingLineFeed  = false;
                wantTrailingLineFeed = false;
            }
        }
        else // cancelled
        {
                dropOK = true;
            wantDialog = false;
        }
    }; // end actionListener

      appendTraceEventsCB.setVisible(this.getBaseX3DElement().isTraceEventsSelectionAvailable());
      appendTraceEventsCB.setSelected(false);
      appendTraceEventsCB.setToolTipText(this.getBaseX3DElement().getTraceEventsTooltip());
      appendTraceEventsCB.setHorizontalAlignment(SwingConstants.RIGHT);

    appendVisualizationCB.setVisible(this.getBaseX3DElement().isVisualizationSelectionAvailable());
    appendVisualizationCB.setSelected(false);
    appendVisualizationCB.setToolTipText(this.getBaseX3DElement().getVisualizationTooltip());
    appendVisualizationCB.setHorizontalAlignment(SwingConstants.RIGHT);

    String name = NbBundle.getMessage(getClass(),getNameKey()); // getNameKey comes from the implementing customizer subclass

//    descriptor = new DialogDescriptor(this,
//            titlePrefix+" "+name,
//            true,
//            DialogDescriptor.OK_CANCEL_OPTION,
//            DialogDescriptor.OK_OPTION,
//            //todo....helpcontext here see materialcustomizer.java
//            al);
    // create optionArray interface to precede Accept/Display/Help buttons
    Object[] optionalButtonsArray;
    JLabel showNewLineLabel = new JLabel("New line: ");
    showNewLineLabel.setToolTipText("Whether to add linefeeds before/after element (see Preferences)");
    prependNewLineCB = new JCheckBox("before", wantLeadingLineFeed);
    appendNewLineCB  = new JCheckBox("after", wantTrailingLineFeed);
    if (showNewLineOption)
      optionalButtonsArray = new Object[]{
        showNewLineLabel,
        prependNewLineCB,
        appendNewLineCB,
        //new JLabel("   "),
          appendTraceEventsCB,
        appendVisualizationCB,
        DialogDescriptor.OK_OPTION,
        DialogDescriptor.CANCEL_OPTION
      };
    else {
      optionalButtonsArray = new Object[]{
        //new JLabel("       "),
          appendTraceEventsCB,
        appendVisualizationCB,
        DialogDescriptor.OK_OPTION,
        DialogDescriptor.CANCEL_OPTION
      };
      prependNewLineCB = new JCheckBox("before", false);  // don't show prepend, append checkboxes
       appendNewLineCB = new JCheckBox("after",  false);
    }

    prependNewLineCB.setToolTipText("prepend newLine before adding element to scene");
    prependNewLineCB.addActionListener((java.awt.event.ActionEvent evt) -> {
        X3dOptions.setPrependNewline(prependNewLineCB.isSelected());
    });
    appendNewLineCB.setToolTipText("append newLine after adding element to scene");
    appendNewLineCB.addActionListener((java.awt.event.ActionEvent evt) -> {
        X3dOptions.setAppendNewline(appendNewLineCB.isSelected());
    });

    buttonsDialogDescriptor = new DialogDescriptor(this,
            titlePrefix+" "+name,
            true,
            optionalButtonsArray,
            DialogDescriptor.OK_OPTION,     // default
            DialogDescriptor.DEFAULT_ALIGN, // align
            HelpCtx.DEFAULT_HELP,
            actionListener);

    // removed because too much real estate required:
    // descriptor.createNotificationLineSupport();

    // Dialog dialog is an AWT component, which allows it to be searched for buttons which can then be modified
    buttonsDialogDisplayer = DialogDisplayer.getDefault().createDialog(buttonsDialogDescriptor);
    acceptChangesDialogButton = findButton(buttonsDialogDisplayer, "OK");
    acceptChangesDialogButton.setText("Accept");
    acceptChangesDialogButton.setToolTipText("Accept and apply all changes");
    acceptChangesDialogButton.addActionListener(actionListener); // multipurpose
    
    discardChangesButton = findButton(buttonsDialogDisplayer, "Cancel");
    discardChangesButton.setText("Discard");
    discardChangesButton.setToolTipText("Discard and ignore all changes");
    discardChangesButton.addActionListener(actionListener); // multipurpose
    
// TODO trial
//    final JButton continueButton = findButton(buttonsDialogDisplayer, "Continue");
//    continueButton.setText("Continue");
//    continueButton.setToolTipText("Continue editing");
    
    final JButton helpButton = findButton(buttonsDialogDisplayer, "Help");
    final ActionListener helpActionListener = (ActionEvent event) -> {
        // launch appropriate X3D Tooltip
        LaunchX3dExamplesAction.sendBrowserTo(X3D_TOOLTIPS + "#" + currentX3dElement.getElementName());
    };
    // null pointer can happen during a unit test ?!  perhaps artifact of prior javahelp dependency...
    if (helpButton != null)
    {
        helpButton.setToolTipText("Show X3D Tooltip or related help page");
        helpButton.addActionListener(helpActionListener);
    }

    if (buttonsDialogDisplayer instanceof JDialog) {
      ((JDialog) buttonsDialogDisplayer).setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      // The previous line rules:  the dialog will not be closed by the menubar button.  (We don't
      // want it to close since we want to check a prompt to save if needed.)  Luckily, the
      // windowClosing event still gets thrown, although the window can't be closed.  We can use
      // this to show the informational message below, then call the button clickers if desired.
      buttonsDialogDisplayer.addWindowListener(new WindowAdapter()
      {
        @Override
        public void windowClosing(WindowEvent e)
        {
          final BaseX3DElement currentElement = getBaseX3DElement(); // expose currentElement within inner class
          JLabel contentLabel = new JLabel("Accept or discard element changes?");
          contentLabel.setBorder(new EmptyBorder(15, 20, 0, 20));

          exitDescriptor = new DialogDescriptor(
              contentLabel,
              "Close element editor",
              true,
              new Object[]
              {
                DialogDescriptor.YES_OPTION,     // Accept
                DialogDescriptor.NO_OPTION,      // Discard
//              DialogDescriptor.CANCEL_OPTION   // Cancel   // TODO fix and restore
              },
              DialogDescriptor.OK_OPTION, // default
              DialogDescriptor.DEFAULT_ALIGN, // align right
              null, //HelpCtx.DEFAULT_HELP,
              (ActionEvent e1) -> {
                  if (exitDescriptor.getValue() == DialogDescriptor.YES_OPTION)
                      windowCloserButton = acceptChangesDialogButton;
                  else if (exitDescriptor.getValue() == DialogDescriptor.NO_OPTION)
                      windowCloserButton = discardChangesButton;
//                  else if (exitDescriptor.getValue() == DialogDescriptor.CANCEL_OPTION)
//                      closer = continueButton; // TODO return to element editor
          }); // end exitDescriptor

          // Dialog dialog is an AWT component, which allows it to be searched for buttons which can then be modified
          exitDialog = DialogDisplayer.getDefault().createDialog(exitDescriptor);
          if (exitDialog instanceof JDialog)
            ((JDialog) exitDialog).setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

          JButton windowCloseYesButton = findButton(exitDialog, "yes");
          windowCloseYesButton.setText("Accept");
          windowCloseYesButton.setToolTipText("Accept and apply all changes");
          JButton windowCloseNoButton = findButton(exitDialog, "no");
          windowCloseNoButton.setText("Discard");
          windowCloseNoButton.setToolTipText("Discard and ignore all changes");
          // TODO fix and restore
//          JButton windowCloseCancelButton = findButton(exitDialog, "cancel");
//          windowCloseCancelButton.setText("Cancel");
//          windowCloseCancelButton.setToolTipText("Cancel window close, return to element editor");
          
          exitDialog.pack();
          exitDialog.setVisible(true); // asks and blocks for user response
          exitDialog.dispose();

          if (windowCloserButton != null)
            doCloser(windowCloserButton);
        }
      }); // end addWindowListener
    }

    // TODO: this code might cause issues by not invoking setVisible asynchronously 
    // on the EDT
    // Begin retry loop
    wantDialog = true;
    while (wantDialog) {
        buttonsDialogDisplayer.setVisible(true);
    }
    // end retry loop

    buttonsDialogDisplayer.dispose();
    repaint();

    if(!dropOK)
      return DialogReturnEnumeration.CANCEL;

    if (showNewLineOption || dragDrop)
    {
      if(wantLeadingLineFeed && wantTrailingLineFeed)
        return DialogReturnEnumeration.ACCEPT_BOTH_LINEFEEDS;
      if(wantLeadingLineFeed)
        return DialogReturnEnumeration.ACCEPT_PREPEND_LINEFEED;
      if(wantTrailingLineFeed)
        return DialogReturnEnumeration.ACCEPT_APPEND_LINEFEED;
    }

    return DialogReturnEnumeration.ACCEPT;
  } // end showDialog

//  private JButton findOkButton(Dialog dialog)
//  {
//    return findButton(dialog, "OK");
//  }
//
//  private JButton findCancelButton(Dialog dialog)
//  {
//    return findButton(dialog, "Cancel");
//  }
//
//  private JButton findHelpButton(Dialog dialog)
//  {
//    return findButton(dialog, "Help");
//  }

  private JButton findButton(Component thisComponent, String searchText)
  {
    if (thisComponent instanceof JButton) {
      String buttonText = ((AbstractButton)thisComponent).getText();
      if (buttonText != null && buttonText.equalsIgnoreCase(searchText))
        return (JButton) thisComponent;
    }
    if (thisComponent instanceof Container) {
      Component[] containerComponentArray = ((Container) thisComponent).getComponents();
      for (Component nextComponent : containerComponentArray) {
        JButton foundbutton = findButton(nextComponent, searchText);
        if (foundbutton != null)
          return foundbutton;
      }
    }
    return null;
  }

  private void doCloser(final JButton closer)
  {
    if (closer == null)
      System.err.println("*** Error in BaseCustomizer..can't find close/cancel button");
    else
      SwingUtilities.invokeLater(() -> {
          closer.doClick();
      });
  }

  // Enable if the initter call above is enabled
//  protected void displayWarningString(String s)
//  {
//    clearMessageString();
//    descriptor.getNotificationLineSupport().setWarningMessage(s);
//  }
//
//  protected void displayErrorString(String s)
//  {
//    clearMessageString();
//    descriptor.getNotificationLineSupport().setErrorMessage(s);
//  }
//
//  protected void displayInformationString(String s)
//  {
//    clearMessageString();
//    descriptor.getNotificationLineSupport().setInformationMessage(s);
//  }
//
//  protected void clearMessageString()
//  {
//    descriptor.getNotificationLineSupport().clearMessages();
//  }

  /*
  */
  private class HelpAction extends AbstractAction
  {
    HelpAction(String s)
    {
      super(s);
    }
    public void actionPerformed(ActionEvent e)
    {
      System.out.println("*** TODO implement context-sensitive help");
    }

  }
  abstract public String getNameKey();

  /**
   * Put the data from the dialog into the passed object (e.g., BOX, CYLINDER, etc.).
   * @throws java.lang.IllegalArgumentException if range-checking, etc., fails
   */
  abstract public void unloadInput() throws IllegalArgumentException;

  protected String formatDecimal(float f)
  {
    return formatter.format(f);
  }

  private DEFUSEpanel defUSEpanel;

  protected DEFUSEpanel getDEFUSEpanel()
  {
    if(defUSEpanel == null) {
      defUSEpanel = new DEFUSEpanel();

      initializeDEFUSEpanel();

      DEFUSElistener defUSElistener = new DEFUSElistener();
      defUSEpanel.getDefRB().addActionListener(defUSElistener);
      defUSEpanel.getUseRB().addActionListener(defUSElistener);
    }
    return defUSEpanel;
  }

  protected void initializeDEFUSEpanel()
  {
    defUSEpanel.setParentPanel(this);
    defUSEpanel.getUseCB().setModel(new DefaultComboBoxModel<>(getBaseX3DElement().getUSEVector()));

    boolean isDef = getBaseX3DElement().isDEF();
    defUSEpanel.getDefRB().setSelected(isDef);
    defUSEpanel.getUseRB().setSelected(!isDef);
    if (isDef)
    {
      defUSEpanel.getDefTF().setText(getBaseX3DElement().getDEFUSEvalue());
//      defUSEPanel.set TODO
    } 
    else 
    {
      defUSEpanel.getUseCB().setSelectedItem(getBaseX3DElement().getDEFUSEvalue());
    }

    defUSEpanel.getDefTF().setEnabled(isDef);
    defUSEpanel.getUseCB().setEnabled(!isDef);
    enableWidgets(isDef);

    defUSEpanel.setContainerFieldChoices(new String[]{((X3DNode)getBaseX3DElement()).getDefaultContainerField()});
    initializeContainerField ();

    String name   = NbBundle.getMessage(getClass(),getNameKey()); // getNameKey comes from the implementing customizer subclass
    if (defUSEpanel.getDefaultDEFname().length() == 0) // don't override suggested name if already set
        defUSEpanel.setDefaultDEFname(name + "New");   // editable DEF name suggestion for this node
    
    defUSEpanel.setHtmlID  (getBaseX3DElement().getHtmlID());
    defUSEpanel.setCssClass(getBaseX3DElement().getCssClass());
    defUSEpanel.setCssStyle(getBaseX3DElement().getCssStyle());
    
    defUSEpanel.highlightX3dDEFUSEpane();
    defUSEpanel.highlightHtmlCssPane();
    
    if (defUSEpanel.hasHtmlCssFields())
    {
        defUSEpanel.selectHtmlCssPane();
    }
    
//        BaseX3DElement parentPanelX3DElement = parentCustomizerPanel.getBaseX3DElement();
//        // TODO test: initialize open so that initial panel sizing occurs correctly
//        setHtmlPaneVisibility (true); 
//             
//        // http://stackoverflow.com/questions/10904639/how-to-refresh-the-jcombobox-data
//        // https://stackoverflow.com/questions/15242692/java-swing-gridbaglayout-panel-resize-issues
////                       this.revalidate();
////                       this.repaint();
////      parentCustomizerPanel.revalidate();
////      parentCustomizerPanel.repaint();
//        
//        if ((parentCustomizerPanel != null) && (parentCustomizerPanel.getBaseX3DElement() != null))
//        {
//              htmlIdTextField.setText(parentPanelX3DElement.getHtmlID());
//            cssClassTextField.setText(parentPanelX3DElement.getCssClass());
//             cssStyleTextArea.setText(parentPanelX3DElement.getCssStyle());
//        }
//        else // ordinarily unreachable, report unexpected occurrence
//        {
//            System.err.println ("*** DEFUSEpanel initialization by setParentPanel() received null parentCustomizerPanel or BaseX3DElement");
//        }
    
    X3DPaletteUtilities x3dPaletteUtilities = new X3DPaletteUtilities();
    
    if ( defUSEpanel.hasHtmlCssFields() && 
        !x3dPaletteUtilities.isCurrentDocumentX3dVersion4())
    {
        // post warning dialog if not X3D version 4
        NotifyDescriptor.Message warningDescriptor = new NotifyDescriptor.Message(
                "<html><p>X3D version='4.0' required <br />for use of HTML CSS attributes</p>", 
              NotifyDescriptor.WARNING_MESSAGE);
        warningDescriptor.setTitle("Ensure correct X3D version!");
        DialogDisplayer.getDefault().notify(warningDescriptor);
    }
  }
      
  protected void initializeContainerField ()
  {
    String containerFieldProvided = getBaseX3DElement().getContainerField();
    
    if(containerFieldProvided != null && containerFieldProvided.length()>0)
	{
		 defUSEpanel.setContainerField(containerFieldProvided);
		 defUSEpanel.setUseContainerField(true);
	}
	else defUSEpanel.setUseContainerField(false);
	
    defUSEpanel.setUseContainerField(getBaseX3DElement().isUseContainerField());
  }

  protected void unLoadDEFUSE() throws IllegalArgumentException
  {
    getBaseX3DElement().setDEF(defUSEpanel.getDefRB().isSelected());
    if (defUSEpanel.getDefRB().isSelected())
        getBaseX3DElement().setDEFUSEvalue(defUSEpanel.getDefTF().getText().trim());
    else
    {
        String USEstring = (String)defUSEpanel.getUseCB().getSelectedItem();
        if (USEstring == null || USEstring.length()<=0)
            throw new IllegalArgumentException(NbBundle.getMessage(getClass(),"LBL_Customizer_Use_message"));

        getBaseX3DElement().setDEFUSEvalue(USEstring);
    }
    getBaseX3DElement().setUseContainerField(defUSEpanel.getUseContainerField());
    if (defUSEpanel.getUseContainerField())
        getBaseX3DElement().setContainerField(defUSEpanel.getContainerField());
    
    getBaseX3DElement().setHtmlID  (defUSEpanel.getHtmlID());
    getBaseX3DElement().setCssClass(defUSEpanel.getCssClass());
    getBaseX3DElement().setCssStyle(defUSEpanel.getCssStyle());
  }

  protected void enableWidgets(boolean wh)
  {
    Component[] comps = getComponents();
    for(Component c : comps){
      if(c != defUSEpanel)
        c.setEnabled(wh);
    }
  }

  private class DEFUSElistener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      enableWidgets(defUSEpanel.getDefRB().isSelected());
    }
  }
  protected void setDomainObject(BaseX3DElement obj)
  {
    baseX3DElement = obj;
  }

  protected BaseX3DElement getDomainObject()
  {
    return getBaseX3DElement();
  }

  protected void initChooser(ColorChooser colorChooser, String rString, String gString, String bString)
  {
    try
    {
        colorChooser.setColor((new SFColor(rString, gString, bString)).getColor());
    }
    catch (IllegalArgumentException ex) {
      System.err.println("Bad color parse (" + rString + " " + gString + " " + bString + ") in "+this.getClass().getName());
    }
  }
  protected void bindColorChooserToBetterJTextFields(BetterJTextField rTF, BetterJTextField gTF, BetterJTextField bTF, ColorChooser chooser)
  {
    class myListener implements PropertyChangeListener
    {
      ColorChooser chooser;
      BetterJTextField rTF, gTF, bTF;

      myListener(ColorChooser chooser, BetterJTextField rTF, BetterJTextField gTF, BetterJTextField bTF)
      {
        this.chooser = chooser;
        this.rTF = rTF;
        this.gTF = gTF;
        this.bTF = bTF;
      }
      @Override
      public void propertyChange(PropertyChangeEvent evt)
      {
        chooser.setColor((new SFColor(rTF.getText(), gTF.getText(), bTF.getText())).getColor());
      }
    }
    myListener pcl = new myListener(chooser, rTF, gTF, bTF);
    rTF.pcs.addPropertyChangeListener(pcl);
    gTF.pcs.addPropertyChangeListener(pcl);
    bTF.pcs.addPropertyChangeListener(pcl);

    pcl.propertyChange(null); // first time
  }

  protected void adjustComboSize(JComboBox<String> cb,String s)
  {
    Dimension p = new JComboBox<>(new String[]{s}).getPreferredSize();
    cb.setPreferredSize(new Dimension(p));
    cb.setMinimumSize(new Dimension(20,p.height));
    cb.setMaximumSize(new Dimension(Integer.MAX_VALUE,p.height));
  }

  public static enum DialogReturnEnumeration
  {
    CANCEL, ACCEPT, ACCEPT_PREPEND_LINEFEED, ACCEPT_APPEND_LINEFEED, ACCEPT_BOTH_LINEFEEDS;
  }
  /**
   * Type-specific check for legal value
   * @param index index of illegal value (-1 if not applicable)
   * @param testValue being checked
   * @return false if no testValue provided, true otherwise
   */
  protected boolean checkBooleanValue (int index, String testValue)
  {
      if ((testValue == null) || testValue.trim().isEmpty())
      {
            String message = "BaseCustomizer check-value warning: missing boolean value";
            if (index >= 0)
                message += " at index [" + index + "]";
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
      if (testValue.trim().equals("true") || testValue.trim().equals("false"))
      {
          return true;
      }
      else
      {
            String message = "BaseCustomizer check-value warning: illegal boolean value";
            if (index >= 0)
                message += " at index [" + index + "] " + testValue;
            else
                message +=                        " " + testValue;
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
  }
  /**
   * Type-specific check for legal value
   * @param index index of illegal value (-1 if not applicable)
   * @param testValue being checked
   * @return false if no testValue provided, true otherwise
   */
  protected boolean checkDoubleValue (int index, String testValue)
  {
      if ((testValue == null) || testValue.trim().isEmpty() || testValue.trim().equals(" "))
      {
            String message = "BaseCustomizer check-value warning: missing double value";
            if (index >= 0)
                message += " at index [" + index + "]";
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
      try
      {
          Double.valueOf(testValue);
      }
      catch (NumberFormatException e)
      {
            String message = "BaseCustomizer check-value warning: illegal double value";
            if (index >= 0)
                message += " at index [" + index + "] " + testValue;
            else
                message +=                         " "  + testValue;
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
      return true;
  }
  /**
   * Type-specific check for legal value
   * @param index index of illegal value (-1 if not applicable)
   * @param testValue being checked
   * @return false if no testValue provided, true otherwise
   */
  protected boolean checkFloatValue (int index, String testValue)
  {
      if ((testValue == null) || testValue.trim().isEmpty() || testValue.trim().equals(" "))
      {
            String message = "BaseCustomizer check-value warning: missing float value";
            if (index >= 0)
                  message += " at index [" + index + "] " + testValue;
            else  message +=                         " "  + testValue;
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
      try
      {
          Float.valueOf(testValue);
      }
      catch (NumberFormatException e)
      {
            String message = "BaseCustomizer check-value warning: illegal float value";
            if (index >= 0)
                message += " at index [" + index + "] " + testValue;
            else
                message +=                         " "  + testValue;
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
      return true;
  }
  /**
   * Type-specific check for legal value
   * @param index index of illegal value (-1 if not applicable)
   * @param testValue being checked
   * @return false if no testValue provided, true otherwise
   */
  protected boolean checkIntegerValue (int index, String testValue)
  {
      if ((testValue == null) || testValue.trim().isEmpty() || testValue.trim().equals(" "))
      {
            String message = "BaseCustomizer check-value warning: missing integer value";
            if (index >= 0)
                message += " at index [" + index + "] " + testValue;
            else
                message +=                         " "  + testValue;
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
      try
      {
          Integer.valueOf(testValue);
      }
      catch (NumberFormatException e)
      {
            String message = "BaseCustomizer check-value warning: illegal integer value";
            if (index >= 0)
                message += " at index [" + index + "] " + testValue;
            else
                message +=                         " "  + testValue;
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
      return true;
  }
  /**
   * Type-specific check for legal value
   * @param index index of illegal value (-1 if not applicable)
   * @param testValue being checked
   * @return false if no testValue provided, true otherwise
   */
  protected boolean checkStringValue (int index, String testValue)
  {
      if ((testValue == null) || testValue.trim().isEmpty())
      {
            String message = "BaseCustomizer check-value warning: missing string value";
            if (index >= 0)
                message += " at index [" + index + "], treated as empty string \"\"";
            System.out.println (message); // ensure appearance on console
            message = "<html><center>" + message;
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
            return false;
      }
      return true;
  }

    /**
     * @return the baseX3DElement
     */
    public BaseX3DElement getBaseX3DElement() {
        return baseX3DElement;
    }
  }
