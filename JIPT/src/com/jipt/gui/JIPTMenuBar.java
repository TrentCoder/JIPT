package com.jipt.gui;
/* 	James LaTour & Trent Lucier
 	10/13/2001
    JIPTMenuBar.java
      	This class represents the menu bar for the MainFrame.
        ANY CHANGES to the menus for the MainFrame 	are made in this class.
*/
/*
//		Menu Bar Organization and Order
//		  - Please update this when adding/removing components to the Menu Bar
//
//		FILE
//		  - New...
//		  - Open...
//		  - Revert
//		  ---
//		  - Save
//		  - Save As...
//		  - Save All
//		  - Close
//		  - Close All
//		  ---
//		  <1-9> Recent file list
//		  ---
//		  Exit JIPT
//
//
//		EDIT
//		  - Undo
//		  - Redo
//		  ---
//		  - Cut
//		  - Copy
//		  - Paste
//		  - Paste as New Image
//		  ---
//		  - Clear
//		  - Select All
//		  ---
//		  - Crop
//
//
//		TOOLS
//		  - Resize...
//		  - Flip/Rotate...
//		  ---
//        - Text                    !!!!! Added (Trent, 12/10)
//        ---
//		  - Image Information
//
//
//		COLOR
//        	  - Adjust
//			- Hue Adjust...
//			- Hue/Saturation/Intensity...
//			- Red/Green/Blue...
//        	  - Reduce Color Depth              XXXXXXXXXXX DISABLED (Trent, 12/8)
//			- 1 bit per pixel  (2 colors)       XXXXXXXXXXX DISABLED (Trent, 12/8)
//			- 4 bits per pixel (16 colors)      XXXXXXXXXXX DISABLED (Trent, 12/8)
//			- 8 bits per pixel (256 colors)     XXXXXXXXXXX DISABLED (Trent, 12/8)
//		  ---
//        	  - Negative Image...
//        	  - Convert to GrayScale...
//        	  - Image Arithmatic...
//        	  ---
//		  - Channel isolation...
//        	  - Channel combine...
//	        ---
//	        - Count Unique Colors...
//      	  - Display distribution...
//
//
//		FILTERS
//		  - Mean/Median...
//		  - Edge Detection...
//		  ---
//		  - Manage Custom...
//		  - Create Custom...
//
//
//		OPTIONS
//		  - JIPT Preferences...
//
//
//		WINDOW
//		  - Minimize
//		  - Maximize
//		  - Restore
//		  - Cascade
//		  - Tile
//		  ---
//		  - <Open Window List>
//
//
//		HELP
//		  - Help Topics...
//		  - About...
//
*/

    import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.jipt.JIPTsettings;

public class JIPTMenuBar extends JMenuBar
{
    private static Icon blankIcon = null;
    private static int RECENT_LIST_INDEX;
    private static int DOC_LIST_INDEX;

    private static MainFrame main_frame = null;

    // Declare Menu Bar menus
    private JMenu fileMenu = null;
    private JMenu editMenu = null;
    private JMenu toolsMenu = null;
    private JMenu colorMenu = null;
    private JMenu filtersMenu = null;
    private JMenu optionsMenu = null;
    private JMenu backgroundOptionsMenu = null;
    private JMenu customBackgroundOptionsMenu = null;
    private JMenu windowMenu = null;
    private JMenu helpMenu = null;

    //File Menu MenuItems
    public static JMenuItem newFileMenuItem = null;
    public static JMenuItem openFileMenuItem = null;
    public static JMenuItem saveFileMenuItem = null;
    public static JMenuItem saveAsFileMenuItem = null;
    public static JMenuItem saveAllFileMenuItem = null;
    public static JMenuItem revertFileMenuItem = null;
    public static JMenuItem closeFileMenuItem = null;
    public static JMenuItem closeAllFileMenuItem = null;
    public static JMenuItem printFileMenuItem = null;
    public static JMenuItem exitFileMenuItem = null;

    /// Edit Menu MenuItems
    public static JMenuItem undoEditMenuItem = null;
    public static JMenuItem redoEditMenuItem = null;
    public static JMenuItem cutEditMenuItem = null;
    public static JMenuItem copyEditMenuItem = null;
    public static JMenuItem pasteEditMenuItem = null;
    public static JMenuItem pasteNewEditMenuItem = null;
    public static JMenuItem clearEditMenuItem = null;
    public static JMenuItem selectAllEditMenuItem = null;
    public static JMenuItem cropEditMenuItem = null;

    /// Tools Menu MenuItems
    public static JMenuItem resizeToolsMenuItem = null;
    public static JMenuItem flipRotateToolsMenuItem = null;
    public static JMenuItem infoToolsMenuItem = null;
    public static JMenuItem textToolsMenuItem = null;

    /// Color Menu MenuItems
    public static JMenuItem negativeColorMenuItem = null;
    public static JMenu     adjustColorMenu        = null;
    public static JMenu     reduceColorDepthColorMenu    = null;
    public static JMenuItem grayScaleColorMenuItem = null;
    public static JMenuItem arithmaticScaleColorMenuItem = null;
    public static JMenuItem isolationScaleColorMenuItem = null;
    public static JMenuItem combineColorMenuItem = null;
    public static JMenuItem countColorMenuItem = null;
    public static JMenuItem graphColorMenuItem = null;
    public static JMenuItem brightnessContrastColorMenuItem = null;
    public static JMenuItem hueAdjustColorMenuItem = null;
    public static JMenuItem hsiAdjustColorMenuItem = null;
    public static JMenuItem rgbAdjustColorMenuItem = null;

    public static JMenuItem bits1ColorMenuItem = null;
    public static JMenuItem bits4ColorMenuItem = null;
    public static JMenuItem bits8ColorMenuItem = null;


    /// Filters Menu MenuItems
    public static JMenuItem meanMedianFiltersMenuItem = null;
    public static JMenuItem edgeFiltersMenuItem = null;
    public static JMenuItem manageCustomFiltersMenuItem = null;
    public static JMenuItem createCustomFiltersMenuItem = null;

    /// Options Menu MenuItem
    public static JMenuItem settingsMenuItem = null;

    /// Window Menu MenuItems
    public static JMenuItem minWindowMenuItem = null;
    public static JMenuItem maxWindowMenuItem = null;
    public static JMenuItem restoreWindowMenuItem = null;
    public static JMenuItem cascadeWindowMenuItem = null;
    public static JMenuItem tileWindowMenuItem = null;
    public static JMenuItem[] docWindowMenuItem = null;

    /// Help Menu MenuItems
    public static JMenuItem topicHelpMenuItem = null;
    public static JMenuItem aboutHelpMenuItem = null;

 	// Default constructor
    public JIPTMenuBar()
    {
        super();
    }

    // Constructor that receives MainFrame as a menu listener
    //  and jipt_settings to checkbox status
    public JIPTMenuBar( MainFrame mf )
    {
        super();
        main_frame = mf;
        blankIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "blank.gif");

    //// Menu Bar ////
        JMenuBar menu_bar = new JMenuBar();

        ////////////////////       @@@@@@  @@  @@     @@@@@@
        // CREATE FILE MENU        @@      @@  @@     @@
        //                         @@@@    @@  @@     @@@@
        ////////////////////       @@      @@  @@@@@  @@@@@@

        fileMenu = new JMenu("File");

        //File Menu Icons
        Icon newMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "new.gif");
        Icon openMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "open.gif");
        Icon saveMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "save.gif");
        Icon saveAsMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "saveas.gif");
        Icon saveAllMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "saveall.gif");
        Icon revertMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "revert.gif");
        Icon closeMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "close.gif");
        Icon closeAllMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "closeall.gif");
        Icon printMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "print.gif");
        Icon exitMenuItemIcon = new
ImageIcon(JIPTsettings.GRAPHICS_PATH + "exit.gif");

        //File Menu MenuItems
        newFileMenuItem = new JMenuItem("New...", newMenuItemIcon );
        openFileMenuItem = new JMenuItem("Open...", openMenuItemIcon );
        saveFileMenuItem = new JMenuItem("Save", saveMenuItemIcon );
        saveAsFileMenuItem = new JMenuItem("Save As...", blankIcon );
        saveAllFileMenuItem = new JMenuItem("Save All", saveAllMenuItemIcon );
        revertFileMenuItem = new JMenuItem("Revert", revertMenuItemIcon );
        closeFileMenuItem = new JMenuItem("Close", closeMenuItemIcon );
        closeAllFileMenuItem = new JMenuItem("Close All", blankIcon );
        printFileMenuItem = new JMenuItem("Print...", printMenuItemIcon );
        exitFileMenuItem = new JMenuItem("Exit", exitMenuItemIcon );

        //Disable MenuItems
        saveFileMenuItem.setEnabled( false );
        saveAsFileMenuItem.setEnabled( false );
        saveAllFileMenuItem.setEnabled( false );
        revertFileMenuItem.setEnabled( false );
        closeFileMenuItem.setEnabled( false );
        closeAllFileMenuItem.setEnabled( false );
        printFileMenuItem.setEnabled( false );

        //Assign File Menu tooltips
        newFileMenuItem.setToolTipText("Opens a new, blank window");
        openFileMenuItem.setToolTipText("Opens an existing image file");
        saveFileMenuItem.setToolTipText("Saves the active image");
        saveAsFileMenuItem.setToolTipText("Saves the active image with a new name");
        saveAllFileMenuItem.setToolTipText("Saves all open images");
        revertFileMenuItem.setToolTipText("Reverts the active image back to its original condition");
        closeFileMenuItem.setToolTipText("Closes the active image");
        closeAllFileMenuItem.setToolTipText("Closes all open images");
        printFileMenuItem.setToolTipText("Prints the active image");
        exitFileMenuItem.setToolTipText("Exits the program and prompts to close all open windows");

        //Assign Mnemonic's to File Menu MenuItems
        newFileMenuItem.setMnemonic('N');
        openFileMenuItem.setMnemonic('O');
        saveFileMenuItem.setMnemonic('S');
        saveAsFileMenuItem.setMnemonic('A');
        saveAllFileMenuItem.setMnemonic('e');
        revertFileMenuItem.setMnemonic('R');
        closeFileMenuItem.setMnemonic('C');
        closeAllFileMenuItem.setMnemonic('l');
        printFileMenuItem.setMnemonic('P');
        exitFileMenuItem.setMnemonic('x');

        //Assign Accelerators to File Menu MenuItems
        newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK ) );
        openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK ) );
        saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK ) );
        printFileMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_P, ActionEvent.CTRL_MASK ) );
        closeFileMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_F4, ActionEvent.CTRL_MASK ) );
        closeAllFileMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_F4, ActionEvent.CTRL_MASK + ActionEvent.SHIFT_MASK ) );

        //Add MenuItem listeners
        newFileMenuItem.addActionListener(main_frame);
        openFileMenuItem.addActionListener(main_frame);
        saveFileMenuItem.addActionListener(main_frame);
        saveAsFileMenuItem.addActionListener(main_frame);
        saveAllFileMenuItem.addActionListener(main_frame);
        revertFileMenuItem.addActionListener(main_frame);
        closeFileMenuItem.addActionListener(main_frame);
        closeAllFileMenuItem.addActionListener(main_frame);
        exitFileMenuItem.addActionListener(main_frame);

        //Add MenuItems to File Menu
        fileMenu.add( newFileMenuItem );
        fileMenu.add( openFileMenuItem );
        fileMenu.add( revertFileMenuItem );
        fileMenu.addSeparator();
        fileMenu.add( saveFileMenuItem );
        fileMenu.add( saveAsFileMenuItem );
        fileMenu.add( saveAllFileMenuItem );
        fileMenu.add( closeFileMenuItem );
        fileMenu.add( closeAllFileMenuItem );
        //fileMenu.addSeparator();
        //fileMenu.add( printFileMenuItem );
        fileMenu.addSeparator();

        RECENT_LIST_INDEX = fileMenu.getItemCount();
        //add recently opened files List
        this.addRecentList();

        ////////////////////       @@@@@@  @@@@@     @@  @@@@@@
        // CREATE EDIT MENU        @@      @@   @@   @@    @@
        //                         @@@@    @@   @@   @@    @@
        ////////////////////       @@@@@@  @@@@@     @@    @@

        editMenu = new JMenu("Edit");

        // Edit Menu Icons
        Icon undoMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "undo.gif");
        Icon redoMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "redo.gif");
        Icon cutMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "cut.gif");
        Icon copyMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "copy.gif");
        Icon pasteMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "paste.gif");
        Icon pasteNewMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "pasteNew.gif");
        Icon clearMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "clear.gif");
        Icon cropMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "crop.gif");

        /// Edit Menu MenuItems
        undoEditMenuItem = new JMenuItem("Undo", undoMenuItemIcon );
        redoEditMenuItem = new JMenuItem("Redo", redoMenuItemIcon );
        cutEditMenuItem = new JMenuItem("Cut", cutMenuItemIcon );
        copyEditMenuItem = new JMenuItem("Copy", copyMenuItemIcon );
        pasteEditMenuItem = new JMenuItem("Paste", pasteMenuItemIcon );
        pasteNewEditMenuItem = new JMenuItem("Paste clipboard image as new", pasteNewMenuItemIcon );
        clearEditMenuItem = new JMenuItem("Clear", clearMenuItemIcon );
        selectAllEditMenuItem = new JMenuItem("Select All", blankIcon);
        cropEditMenuItem = new JMenuItem("Crop", cropMenuItemIcon);

        //Disable MenuItems
        undoEditMenuItem.setEnabled( false );
        redoEditMenuItem.setEnabled( false );
        cutEditMenuItem.setEnabled( false );
        copyEditMenuItem.setEnabled( false );
        pasteEditMenuItem.setEnabled( false );
        pasteNewEditMenuItem.setEnabled( false );
        clearEditMenuItem.setEnabled( false );
        selectAllEditMenuItem.setEnabled( false );
        cropEditMenuItem.setEnabled( false );

        //Assign Edit Menu tooltips
        undoEditMenuItem.setToolTipText("Un-performs the last action");
        redoEditMenuItem.setToolTipText("Re-performs the last action undone");
        cutEditMenuItem.setToolTipText("Cuts selection to the clipboard");
        copyEditMenuItem.setToolTipText("Copies selection to the clipboard");
        pasteEditMenuItem.setToolTipText("Pastes from the clipboard");
        pasteNewEditMenuItem.setToolTipText("Pastes from the clipboard as a new image");
        clearEditMenuItem.setToolTipText("Deletes selected area");
        selectAllEditMenuItem.setToolTipText("Selects everything in the active window");
        cropEditMenuItem.setToolTipText("Crops the image to the selection");

        //Assign Accelerators to Edit Menu MenuItems
        undoEditMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_Z, ActionEvent.CTRL_MASK ) );
        redoEditMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_Z, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK ) );
        copyEditMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_C, ActionEvent.CTRL_MASK ) );
        cutEditMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.CTRL_MASK ) );
        pasteEditMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_V, ActionEvent.CTRL_MASK ) );
        selectAllEditMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_A, ActionEvent.CTRL_MASK ) );
        clearEditMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0 ) );

        //Assign Mnemonic's to Edit Menu MenuItems
        undoEditMenuItem.setMnemonic('U');
        redoEditMenuItem.setMnemonic('R');
        cutEditMenuItem.setMnemonic('C');
        copyEditMenuItem.setMnemonic('Y');
        pasteEditMenuItem.setMnemonic('P');
        pasteNewEditMenuItem.setMnemonic('n');
        clearEditMenuItem.setMnemonic('l');
        selectAllEditMenuItem.setMnemonic('S');
        cropEditMenuItem.setMnemonic('C');

        // Add Edit MenuItem Listeners
        undoEditMenuItem.addActionListener(main_frame);
        redoEditMenuItem.addActionListener(main_frame);
        cutEditMenuItem.addActionListener(main_frame);
        copyEditMenuItem.addActionListener(main_frame);
        pasteEditMenuItem.addActionListener(main_frame);
        pasteNewEditMenuItem.addActionListener(main_frame);
        clearEditMenuItem.addActionListener(main_frame);
        selectAllEditMenuItem.addActionListener(main_frame);
        cropEditMenuItem.addActionListener(main_frame);

        // Add MenuItems to Edit Menu
        editMenu.add( undoEditMenuItem );
        editMenu.add( redoEditMenuItem );
        editMenu.addSeparator();
        editMenu.add( cutEditMenuItem );
        editMenu.add( copyEditMenuItem );
        editMenu.add( pasteEditMenuItem );
        editMenu.add( pasteNewEditMenuItem );
        editMenu.addSeparator();
        editMenu.add( clearEditMenuItem );
        editMenu.add( selectAllEditMenuItem );
        editMenu.addSeparator();
        editMenu.add( cropEditMenuItem );

        ////////////////////        @@@@@@@@  @@@@@@@@   @@@@@@@@  @@     @@@@@
        // CREATE TOOLS MENU           @@     @@    @@   @@    @@  @@      @@@
        //                             @@     @@    @@   @@    @@  @@       @@@
        ////////////////////           @@     @@@@@@@@   @@@@@@@@  @@@@@  @@@@@

        toolsMenu = new JMenu("Tools");

        // Tools Menu Icons
        Icon resizeMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "resize.gif");
        Icon flipRotateMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "fliprotate.gif");
        Icon infoMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "info.gif");
        Icon textMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "text.gif");

        /// Tools Menu MenuItems
        resizeToolsMenuItem = new JMenuItem("Resize...", resizeMenuItemIcon);

        flipRotateToolsMenuItem = new JMenuItem("Flip/Rotate...", flipRotateMenuItemIcon);
        textToolsMenuItem = new JMenuItem("Add Text...", textMenuItemIcon);
        infoToolsMenuItem = new JMenuItem("Image Information", infoMenuItemIcon );

        //Disable MenuItems
        resizeToolsMenuItem.setEnabled( false );
        flipRotateToolsMenuItem.setEnabled( false );
        infoToolsMenuItem.setEnabled( false );
        textToolsMenuItem.setEnabled( false );

        //Assign Tools Menu tooltips
        resizeToolsMenuItem.setToolTipText("Resizes the active image");
        flipRotateToolsMenuItem.setToolTipText("Flips / Rotates the active image");
        infoToolsMenuItem.setToolTipText("Displays information on the active image");
        textToolsMenuItem.setToolTipText("Add Text to an Image");

        //Assign Mnemonic's to Tools Menu MenuItems
        resizeToolsMenuItem.setMnemonic('R');
        flipRotateToolsMenuItem.setMnemonic('F');
        infoToolsMenuItem.setMnemonic('I');
        textToolsMenuItem.setMnemonic('T');

        // Add Tools MenuItem Listeners
        resizeToolsMenuItem.addActionListener(main_frame);
        flipRotateToolsMenuItem.addActionListener(main_frame);
        infoToolsMenuItem.addActionListener(main_frame);
        textToolsMenuItem.addActionListener(main_frame);

        // Add MenuItems to Tools Menu
        toolsMenu.add( resizeToolsMenuItem );
        toolsMenu.add( flipRotateToolsMenuItem );
        toolsMenu.addSeparator();
        toolsMenu.add( textToolsMenuItem );
        toolsMenu.addSeparator();
        toolsMenu.add( infoToolsMenuItem );

        ///////////////////////   @@@@@@  @@@@@@  @@     @@@@@@  @@@@@    @@@@
        // CREATION COLORS MENU   @@      @@  @@  @@     @@  @@  @@  @@  @@@
        //                        @@      @@  @@  @@     @@  @@  @@@@     @@@
        ///////////////////////   @@@@@@  @@@@@@  @@@@@  @@@@@@  @@  @@  @@@@@

        colorMenu = new JMenu("Color");

        // Color Menu Icons
        Icon negativeMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "negative.gif");
        Icon adjustMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "adjust.gif");
        Icon grayScaleMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "greyScale.gif");
        Icon countMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "count.gif");
        Icon graphMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "graph.gif");
        Icon arithmaticMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "arithmetic.gif");
        Icon isolationMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "isolation.gif");
        Icon combineMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "combine.gif");

        Icon brightnessContrastMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "bright.gif"); // Trent 12/1
        Icon hueAdjustMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "hue.gif"); // Trent 12/1
        Icon hsiAdjustMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "hsi.gif"); // Trent 12/2
        Icon reduceColorDepthColorMenuIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "r_color_depth.gif"); // Trent 12/2
        Icon rgbAdjustMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "rgb.gif"); // Trent 12/2


        /// Color Menu MenuItems
        negativeColorMenuItem = new JMenuItem("Negative Image...", negativeMenuItemIcon );
        adjustColorMenu = new JMenu("Adjust");
        reduceColorDepthColorMenu = new JMenu("Reduce Color Depth");
        grayScaleColorMenuItem = new JMenuItem("Convert to grayscale...", grayScaleMenuItemIcon);
        arithmaticScaleColorMenuItem = new JMenuItem("Image Arithmetic...", arithmaticMenuItemIcon);
        isolationScaleColorMenuItem = new JMenuItem("Split Channels...", isolationMenuItemIcon);
        combineColorMenuItem = new JMenuItem("Combine Channels...", combineMenuItemIcon);
        countColorMenuItem = new JMenuItem("Count unique colors...", countMenuItemIcon);
        graphColorMenuItem = new JMenuItem("Display distribution...", graphMenuItemIcon);
        brightnessContrastColorMenuItem = new JMenuItem("Brightness/Contrast...", brightnessContrastMenuItemIcon ); // Trent 12/1
        hueAdjustColorMenuItem = new JMenuItem("Hue Adjust...", hueAdjustMenuItemIcon);
        hsiAdjustColorMenuItem = new JMenuItem("Hue/Saturation/Intensity...", hsiAdjustMenuItemIcon);
        rgbAdjustColorMenuItem = new JMenuItem("Red/Green/Blue...", rgbAdjustMenuItemIcon);

        bits1ColorMenuItem = new JMenuItem("1 bit per pixel  (2 colors)");
        bits4ColorMenuItem = new JMenuItem("4 bits per pixel (16 colors)");
        bits8ColorMenuItem = new JMenuItem("8 bits per pixel (256 colors)");

        //Disable MenuItems
        negativeColorMenuItem.setEnabled( false );
        adjustColorMenu.setEnabled(false);
        reduceColorDepthColorMenu.setEnabled(false);
        grayScaleColorMenuItem.setEnabled( false );
        arithmaticScaleColorMenuItem.setEnabled( false );
        isolationScaleColorMenuItem.setEnabled( false );
        combineColorMenuItem.setEnabled( false );
        countColorMenuItem.setEnabled( false );
        graphColorMenuItem.setEnabled( false );
        hueAdjustColorMenuItem.setEnabled(false);
        hsiAdjustColorMenuItem.setEnabled(false);
        rgbAdjustColorMenuItem.setEnabled(false);

        bits1ColorMenuItem.setEnabled(false);
        bits4ColorMenuItem.setEnabled(false);
        bits8ColorMenuItem.setEnabled(false);

        //Assign Mnemonic's to Color Menu MenuItems
        negativeColorMenuItem.setToolTipText("Inverts the colors of the active image");
        grayScaleColorMenuItem.setToolTipText("Performs grayscale operations on this image");
        arithmaticScaleColorMenuItem.setToolTipText("Performs AND, OR, etc. operations on two images");
        isolationScaleColorMenuItem.setToolTipText("Isolate color channels for selected image");
        isolationScaleColorMenuItem.setToolTipText("Isolate color channels for selected image");
        combineColorMenuItem.setToolTipText("Combine channels from 3 images into a single image");
        countColorMenuItem.setToolTipText("Counts the number of unique colors in active image");
        graphColorMenuItem.setToolTipText("Displays the histogram of the active image");
        brightnessContrastColorMenuItem.setToolTipText("Adjust the selected image's brightness and contrast");
        hueAdjustColorMenuItem.setToolTipText("Replace the given hues with new values");
        hsiAdjustColorMenuItem.setToolTipText("Adjust the hue, saturation, and brightness values for the entire image");
        rgbAdjustColorMenuItem.setToolTipText("Adjust the red, green, and blue values for the entire image");


        bits1ColorMenuItem.setToolTipText("Reduce image to 2 colors");
        bits4ColorMenuItem.setToolTipText("Reduce image to 16 colors");
        bits8ColorMenuItem.setToolTipText("Reduce image to 256 colors");

        //Assign Mnemonic's to Colors Menu MenuItems
        negativeColorMenuItem.setMnemonic('N');
        grayScaleColorMenuItem.setMnemonic('G');
        arithmaticScaleColorMenuItem.setMnemonic('r');
        isolationScaleColorMenuItem.setMnemonic('I');
        combineColorMenuItem.setMnemonic('K');
        countColorMenuItem.setMnemonic('C');
        graphColorMenuItem.setMnemonic('G');
        brightnessContrastColorMenuItem.setMnemonic('B');
        hueAdjustColorMenuItem.setMnemonic('H');
        hsiAdjustColorMenuItem.setMnemonic('S');
        rgbAdjustColorMenuItem.setMnemonic('A');


        // Add Edit MenuItem Listeners
        graphColorMenuItem.addActionListener(main_frame);
        negativeColorMenuItem.addActionListener(main_frame);
        arithmaticScaleColorMenuItem.addActionListener(main_frame);
        isolationScaleColorMenuItem.addActionListener(main_frame);
        combineColorMenuItem.addActionListener(main_frame);
        countColorMenuItem.addActionListener(main_frame);
        grayScaleColorMenuItem.addActionListener(main_frame);
        brightnessContrastColorMenuItem.addActionListener(main_frame);
        hueAdjustColorMenuItem.addActionListener(main_frame);
        hsiAdjustColorMenuItem.addActionListener(main_frame);
        rgbAdjustColorMenuItem.addActionListener(main_frame);

        bits1ColorMenuItem.addActionListener(main_frame);
        bits4ColorMenuItem.addActionListener(main_frame);
        bits8ColorMenuItem.addActionListener(main_frame);

        // Add MenuItems to Color Menu
        colorMenu.add( adjustColorMenu );
       // colorMenu.add(reduceColorDepthColorMenu);  DISABLED 12/8, TRENT
        colorMenu.addSeparator();

        colorMenu.add( negativeColorMenuItem );
        colorMenu.add( grayScaleColorMenuItem );
        colorMenu.add( arithmaticScaleColorMenuItem );
        colorMenu.addSeparator();

        colorMenu.add( isolationScaleColorMenuItem );
        colorMenu.add( combineColorMenuItem );
        colorMenu.addSeparator();

        colorMenu.add( countColorMenuItem );
        colorMenu.add( graphColorMenuItem );

        reduceColorDepthColorMenu.add(bits1ColorMenuItem);
        reduceColorDepthColorMenu.add(bits4ColorMenuItem);
        reduceColorDepthColorMenu.add(bits8ColorMenuItem);

        adjustColorMenu.add(brightnessContrastColorMenuItem);
        adjustColorMenu.add(hueAdjustColorMenuItem);
        adjustColorMenu.add(hsiAdjustColorMenuItem);
        adjustColorMenu.add(rgbAdjustColorMenuItem);

        ///////////////////////   @@@@@@@  @@  @@     @@@@@@  @@@@@  @@@@@    @@@@
        // CREATE FILTERS MENU    @@       @@  @@       @@    @@     @@  @@  @@@
        //                        @@@@     @@  @@       @@    @@@@   @@@@     @@@
        ///////////////////////   @@       @@  @@@@@@   @@    @@@@@  @@  @@  @@@@@

        filtersMenu = new JMenu("Filters");

        // Filters Menu Icons
        Icon meanMedianFilterMenuItemIcon = new ImageIcon( JIPTsettings.GRAPHICS_PATH + "median.gif" );
        Icon edgeDetectionCustomFilterMenuItemIcon = new ImageIcon( JIPTsettings.GRAPHICS_PATH + "edge.gif" );
        Icon loadCustomFilterMenuItemIcon = new ImageIcon( JIPTsettings.GRAPHICS_PATH + "loadFilter.gif" );
        Icon createCustomFilterMenuItemIcon = new ImageIcon( JIPTsettings.GRAPHICS_PATH + "createFilter.gif" );

        /// Filters Menu MenuItems
        meanMedianFiltersMenuItem = new JMenuItem("Mean/Median...", meanMedianFilterMenuItemIcon);
        edgeFiltersMenuItem = new JMenuItem("Edge detection...", edgeDetectionCustomFilterMenuItemIcon);
        manageCustomFiltersMenuItem = new JMenuItem("Manage custom...", loadCustomFilterMenuItemIcon);
        createCustomFiltersMenuItem = new JMenuItem("Create custom...", createCustomFilterMenuItemIcon);

        //Disable MenuItems
        meanMedianFiltersMenuItem.setEnabled( false );
        edgeFiltersMenuItem.setEnabled( false );
        manageCustomFiltersMenuItem.setEnabled( false );
        createCustomFiltersMenuItem.setEnabled( true ); //never disabled

        //Assign Filters MenuItem Tooltips
        meanMedianFiltersMenuItem.setToolTipText("Applies a Mean or Median Filter to the active image");
        edgeFiltersMenuItem.setToolTipText("Applies the Sobel Filter to the active image");
        manageCustomFiltersMenuItem.setToolTipText("Applies a custom filter to the active image");
        createCustomFiltersMenuItem.setToolTipText("Creates a new custom filter");

        //Assign Mnemonic's to Filters Menu MenuItems
        meanMedianFiltersMenuItem.setMnemonic('M');
        edgeFiltersMenuItem.setMnemonic('S');
        manageCustomFiltersMenuItem.setMnemonic('L');
        createCustomFiltersMenuItem.setMnemonic('C');

        // Add Filters MenuItem Listeners
        meanMedianFiltersMenuItem.addActionListener(main_frame);
        edgeFiltersMenuItem.addActionListener(main_frame);
        manageCustomFiltersMenuItem.addActionListener(main_frame);
        createCustomFiltersMenuItem.addActionListener(main_frame);

        // Add MenuItems to Filters Menu
        filtersMenu.add( meanMedianFiltersMenuItem );
        filtersMenu.add( edgeFiltersMenuItem );
        filtersMenu.addSeparator();
        filtersMenu.add( manageCustomFiltersMenuItem );
        filtersMenu.add( createCustomFiltersMenuItem );

        ////////////////////////   @@@@@@@  @@@@@@ @@@@@@  @@  @@@@@@@  @@  @@  @@@@@
        // CREATION OPTIONS MENU   @@   @@  @@  @@   @@    @@  @@   @@  @@@ @@  @@@
        //                         @@   @@  @@@@@    @@    @@  @@   @@  @@ @@@   @@@
        ////////////////////////   @@@@@@@  @@       @@    @@  @@@@@@@  @@  @@  @@@@@

        optionsMenu = new JMenu("Options");

        // Options Menu Icons
        Icon settingsMenuItemIcon = new ImageIcon( JIPTsettings.GRAPHICS_PATH + "settings.gif" );

        /// Options Menu MenuItems
        settingsMenuItem = new JMenuItem("JIPT Settings...", settingsMenuItemIcon );

        //Assign Mnemonic's to Options Menu MenuItems
        settingsMenuItem.setToolTipText("Configure JIPT");

        //Assign Mnemonics to Options Menu MenuItems
        settingsMenuItem.setMnemonic('S');

        // Add Options MenuItem Listeners
        settingsMenuItem.addActionListener(main_frame);

        optionsMenu.add( settingsMenuItem );

        ////////////////////////   @@   @@  @@  @@  @@  @@@@@    @@@@@@@  @@   @@
        // CREATION WINDOW MENU    @@   @@  @@  @@@ @@  @@   @@  @@   @@  @@   @@
        //                         @@ @ @@  @@  @@ @@@  @@   @@  @@   @@  @@ @ @@
        ////////////////////////   @@@@@@@  @@  @@  @@  @@@@@@   @@@@@@@  @@@@@@@

        windowMenu = new JMenu("Window");

        // Window Menu Icons
        Icon minWindowMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "minwin.gif");
        Icon maxWindowMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "maxwin.gif");
        Icon restoreWindowMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "restoreWin.gif");
        Icon cascadeWindowMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "cascadeWin.gif");
        Icon tileWindowMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "tileWin.gif");

        /// Window Menu MenuItems
        minWindowMenuItem = new JMenuItem("Minimize", minWindowMenuItemIcon);
        maxWindowMenuItem = new JMenuItem("Maximize", maxWindowMenuItemIcon);
        restoreWindowMenuItem = new JMenuItem("Restore", restoreWindowMenuItemIcon);
        cascadeWindowMenuItem = new JMenuItem("Cascade", cascadeWindowMenuItemIcon);
        tileWindowMenuItem = new JMenuItem("Tile", tileWindowMenuItemIcon);

        //Disable MenuItems
        minWindowMenuItem.setEnabled( false );
        maxWindowMenuItem.setEnabled( false );
        restoreWindowMenuItem.setEnabled( false );
        cascadeWindowMenuItem.setEnabled( false );
        tileWindowMenuItem.setEnabled( false );

        //Assign Mnemonic's to Window Menu MenuItems
        minWindowMenuItem.setToolTipText("Minimizes the active window");
        maxWindowMenuItem.setToolTipText("Maximizes the active window");
        restoreWindowMenuItem.setToolTipText("Restores the active window to its previous size");
        cascadeWindowMenuItem.setToolTipText("Cascades all open windows");
        tileWindowMenuItem.setToolTipText("Tiles all open windows");

        //Assign Mnemonics to Window Menu MenuItems
        minWindowMenuItem.setMnemonic('M');
        maxWindowMenuItem.setMnemonic('X');
        restoreWindowMenuItem.setMnemonic('R');
        cascadeWindowMenuItem.setMnemonic('C');
        tileWindowMenuItem.setMnemonic('T');

        // Add Window MenuItem Listeners
        minWindowMenuItem.addActionListener(main_frame);
        maxWindowMenuItem.addActionListener(main_frame);
        restoreWindowMenuItem.addActionListener(main_frame);
        cascadeWindowMenuItem.addActionListener(main_frame);
        tileWindowMenuItem.addActionListener(main_frame);

        // Add MenuItems to Window Menu
        windowMenu.add( minWindowMenuItem );
        windowMenu.add( maxWindowMenuItem );
        windowMenu.add( restoreWindowMenuItem );
        windowMenu.add( cascadeWindowMenuItem );
        windowMenu.add( tileWindowMenuItem );
        DOC_LIST_INDEX = windowMenu.getItemCount();

        //this is where a listing of currently open window will go: (under the separator)

        ////////////////////////   @@   @@  @@@@@@  @@@    @@@@@@
        // CREATION HELP MENU      @@   @@  @@@     @@     @@   @@
        //                         @@@@@@@  @@@@    @@     @@@@@@
        ////////////////////////   @@   @@  @@@@@@  @@@@@  @@

        helpMenu = new JMenu("Help");

        // Help Menu Icons
        Icon topicHelpMenuItemIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "helpTopics.gif");

        /// Help Menu MenuItems
        topicHelpMenuItem = new JMenuItem("Help Topics", topicHelpMenuItemIcon );
        aboutHelpMenuItem = new JMenuItem("About...", blankIcon);

        //Assign Mnemonic's to Help Menu MenuItems
        topicHelpMenuItem.setToolTipText("Displays help topics");
        aboutHelpMenuItem.setToolTipText("Displays JIPT about screen");

        //Assign Mnemonics to Help Menu MenuItems
        topicHelpMenuItem.setMnemonic('T');
        aboutHelpMenuItem.setMnemonic('A');

        //Assign Accelerators to Help Menu MenuItems
        topicHelpMenuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_H, ActionEvent.CTRL_MASK ) );

        // Add Help MenuItem Listeners
        topicHelpMenuItem.addActionListener(main_frame);
        aboutHelpMenuItem.addActionListener(main_frame);

        // Add MenuItems to Help Menu
        
        // Disabled Help Documentation.  Website hosting help doc is no longer active.
        //helpMenu.add( topicHelpMenuItem );
        
        helpMenu.add( aboutHelpMenuItem );

        //Add Mnemonics to menu bar items
        fileMenu.setMnemonic('F');
        editMenu.setMnemonic('E');
        toolsMenu.setMnemonic('T');
        colorMenu.setMnemonic('C');
        filtersMenu.setMnemonic('I');
        optionsMenu.setMnemonic('O');
        windowMenu.setMnemonic('W');
        helpMenu.setMnemonic('H');

        //Add menus to menubar
        this.add(fileMenu);
        this.add(editMenu);
        this.add(toolsMenu);
        this.add(colorMenu);
        this.add(filtersMenu);
        this.add(optionsMenu);
        this.add(windowMenu);
        
        
        this.add(helpMenu);
        
    }//end constructor

    public void setWindowStateMenuItems(boolean icon, boolean max)
    {
        if( max && !icon )
        {
            minWindowMenuItem.setEnabled(true);
            maxWindowMenuItem.setEnabled(false);
            restoreWindowMenuItem.setEnabled(true);
        }
        else if( icon && !max )
            {
                minWindowMenuItem.setEnabled(false);
                maxWindowMenuItem.setEnabled(true);
                restoreWindowMenuItem.setEnabled(true);
            }
            else //regular size
            {
                minWindowMenuItem.setEnabled(true);
                maxWindowMenuItem.setEnabled(true);
                restoreWindowMenuItem.setEnabled(false);
            }
    }

    public void enableCutCopyActions()
    {
        this.cutEditMenuItem.setEnabled( true );
        this.copyEditMenuItem.setEnabled( true );
        this.clearEditMenuItem.setEnabled( true );
        this.cropEditMenuItem.setEnabled( true );
    }

    public void disableCutCopyActions()
    {
        this.cutEditMenuItem.setEnabled( false );
        this.copyEditMenuItem.setEnabled( false );
        this.clearEditMenuItem.setEnabled( false );
        this.cropEditMenuItem.setEnabled( false );
    }

    public void enableFrameActiveActions( boolean imageOnClipboard )
    {
        selectAllEditMenuItem.setEnabled(true);
        revertFileMenuItem.setEnabled(true);
        saveFileMenuItem.setEnabled(true);
        saveAsFileMenuItem.setEnabled(true);
        closeFileMenuItem.setEnabled(true);
        printFileMenuItem.setEnabled(true);
        resizeToolsMenuItem.setEnabled(true);
        flipRotateToolsMenuItem.setEnabled(true);
        textToolsMenuItem.setEnabled(true);
        infoToolsMenuItem.setEnabled(true);
        negativeColorMenuItem.setEnabled(true);
        adjustColorMenu.setEnabled(true);
        reduceColorDepthColorMenu.setEnabled(true);
        bits1ColorMenuItem.setEnabled(true);
        bits4ColorMenuItem.setEnabled(true);
        bits8ColorMenuItem.setEnabled(true);
        hueAdjustColorMenuItem.setEnabled(true);
        hsiAdjustColorMenuItem.setEnabled(true);
        rgbAdjustColorMenuItem.setEnabled(true);
        grayScaleColorMenuItem.setEnabled(true);
        isolationScaleColorMenuItem.setEnabled(true);
        countColorMenuItem.setEnabled(true);
        graphColorMenuItem.setEnabled(true);
        meanMedianFiltersMenuItem.setEnabled(true);
        edgeFiltersMenuItem.setEnabled(true);
        manageCustomFiltersMenuItem.setEnabled(true);
        if( imageOnClipboard )
            pasteEditMenuItem.setEnabled(true);
    }

    public void disableFrameActiveActions( boolean imageOnClipboard )
    {
        selectAllEditMenuItem.setEnabled(false);
        revertFileMenuItem.setEnabled(false);
        saveFileMenuItem.setEnabled(false);
        saveAsFileMenuItem.setEnabled(false);
        closeFileMenuItem.setEnabled(false);
        printFileMenuItem.setEnabled(false);
        resizeToolsMenuItem.setEnabled(false);
        flipRotateToolsMenuItem.setEnabled(false);
        textToolsMenuItem.setEnabled(false);
        infoToolsMenuItem.setEnabled(false);
        negativeColorMenuItem.setEnabled(false);
        adjustColorMenu.setEnabled(false);
        reduceColorDepthColorMenu.setEnabled(false);
         bits1ColorMenuItem.setEnabled(false);
        bits4ColorMenuItem.setEnabled(false);
        bits8ColorMenuItem.setEnabled(false);
        hsiAdjustColorMenuItem.setEnabled(false);
        rgbAdjustColorMenuItem.setEnabled(false);
        grayScaleColorMenuItem.setEnabled(false);
        isolationScaleColorMenuItem.setEnabled(false);
        countColorMenuItem.setEnabled(false);
        graphColorMenuItem.setEnabled(false);
        meanMedianFiltersMenuItem.setEnabled(false);
        edgeFiltersMenuItem.setEnabled(false);
        manageCustomFiltersMenuItem.setEnabled(false);
        pasteEditMenuItem.setEnabled(false);
    }

    public void enableAtLeastOneFrameOpenActions()
    {
        this.closeAllFileMenuItem.setEnabled(true);
        this.saveAllFileMenuItem.setEnabled(true);
        this.arithmaticScaleColorMenuItem.setEnabled(true);
        this.combineColorMenuItem.setEnabled(true);
        this.cascadeWindowMenuItem.setEnabled(true);
        this.tileWindowMenuItem.setEnabled(true);
    }

    public void disableAtLeastOneFrameOpenActions()
    {
        this.closeAllFileMenuItem.setEnabled(false);
        this.saveAllFileMenuItem.setEnabled(false);
        this.arithmaticScaleColorMenuItem.setEnabled(false);
        this.combineColorMenuItem.setEnabled(false);
        this.cascadeWindowMenuItem.setEnabled(false);
        this.tileWindowMenuItem.setEnabled(false);
        this.minWindowMenuItem.setEnabled(false);
        this.maxWindowMenuItem.setEnabled(false);
        this.restoreWindowMenuItem.setEnabled(false);
    }

    public void enableUndo()
    {
        this.undoEditMenuItem.setEnabled( true );
    }

    public void enableRedo()
    {
        this.redoEditMenuItem.setEnabled( true );
    }

    public void disableUndo()
    {
        this.undoEditMenuItem.setEnabled( false );
    }

    public void disableRedo()
    {
        this.redoEditMenuItem.setEnabled( false );
    }

    public void updateMenuBar()
    {
        //remove recent list from file menu
        for( int loop=fileMenu.getItemCount()-1; loop>=RECENT_LIST_INDEX; loop-- )
            fileMenu.remove(loop);
        //recreate recent list
        addRecentList();


        //remove document list from window menu
        for( int loop=windowMenu.getItemCount()-1; loop>=DOC_LIST_INDEX; loop-- )
            windowMenu.remove(loop);
        //recreate document list
        addDocumentList();
    }

    public void addRecentList()
    {
        //Add Recently-opened files (if any)
        boolean wasAdded = false;
        int count = 1;
        int numRecentFiles = main_frame.getJIPTsettings().getNumRecentFiles();
        JMenuItem recent[] = new JMenuItem[ numRecentFiles ];
        for( int loop = 0; loop < numRecentFiles; loop++ )
        {
			String filename = main_frame.getJIPTsettings().getRecentFile( loop );
            if( (filename != null) && (!filename.equalsIgnoreCase("null")) )
            {
                wasAdded = true;
                //recent list will show entire path and filename
                String file = filename;
                recent[ loop ] = new JMenuItem( count + " " + file, blankIcon );
                recent[ loop ].setToolTipText( filename );
                recent[ loop ].setMnemonic( ((String)""+(loop+1)).charAt(0) );
                recent[ loop ].addActionListener(main_frame);
                fileMenu.add( recent[ loop ] );
                count++;
            }
        }

        if( wasAdded )
            fileMenu.addSeparator();
        fileMenu.add( exitFileMenuItem );
    }

    public void addDocumentList()
    {
        int numDocs = main_frame.getFrameManager().getNumFrames();
        docWindowMenuItem = new JMenuItem[ numDocs ];
        if( numDocs > 0 )
        {
            cascadeWindowMenuItem.setEnabled( true );
            tileWindowMenuItem.setEnabled( true );
            windowMenu.addSeparator();
            for(int loop=0; loop < numDocs; loop++)
            {
                ImageFrame f = (ImageFrame)main_frame.getFrameManager().getFrame( loop );
                docWindowMenuItem[loop] = new JMenuItem( f.getDocTitle() , blankIcon );
                docWindowMenuItem[loop].setMnemonic( ((String)""+(loop+1)).charAt(0) );
                docWindowMenuItem[loop].addActionListener( main_frame );
                windowMenu.add( docWindowMenuItem[loop] );
            }
        }
    }
}
