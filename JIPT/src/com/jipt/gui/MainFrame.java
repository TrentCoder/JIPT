package com.jipt.gui;
/*
	James LaTour and Trent Lucier
	10/13/2001
	MainFrame.java


	This class represents the "main frame" of this program.  This frame contains the image frames and
	the menu bars.  This frame implements a menu listener, which lets it redirect menu selections to the
	appropriate managers.
*/

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.MemoryImageSource;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import com.jipt.ColorManager;
import com.jipt.EditManager;
import com.jipt.ExampleFileFilter;
import com.jipt.FileManager;
import com.jipt.JIPTAlert;
import com.jipt.JIPTColor;
import com.jipt.JIPTsettings;
import com.jipt.ToolManager;
import com.jipt.filter.FilterManager;

public class MainFrame extends JFrame implements InternalFrameListener, ActionListener
{
    boolean colorMenuItems = true;

	public JDesktopPane desktop_pane = null;  // Desktop pane of this frame
	public JIPTMenuBar  menu_bar     = null;  // Menu options.
    private JPopupMenu popup = null;
        private JMenuItem rightMaxMenuItem = null;
        private JMenuItem rightMinMenuItem = null;
        private JMenuItem rightRestoreMenuItem = null;
        private JMenuItem rightCascadeMenuItem = null;
        private JMenuItem rightTileMenuItem = null;
        private JMenuItem rightCloseAllMenuItem = null;

    private static JIPTsettings  jipt_settings = new JIPTsettings();
    private static ToolManager   tool_manager   = null;		// Manages tools
    private static EditManager   edit_manager   = null;
    private static FilterManager filter_manager = null;
    private static FrameManager  frame_manager  = null;		// Manages image frames.
    private static ColorManager  color_manager  = null;     // Manages "adjust colors" and arithmetic options.

	////////////////////////////////
	/////// main method ////////////
	////////////////////////////////
	public static void main(String args[])
    {
		MainFrame mframe = new MainFrame();
	}

	////////////////////////////////////////
	///////// Default constructor //////////
	////////////////////////////////////////
	public MainFrame()
	{
		super();

        ////////////////////////////////////////////////////////////////////////////
        /// Show splash screen and then close after several seconds, if enabled ////
        ////////////////////////////////////////////////////////////////////////////
        if( jipt_settings.getShowSplashScreen() )
        {
            try
            {
                JIPTSplashScreen sc = new JIPTSplashScreen();
                sc.setVisible(true);
                Thread.sleep(2500);
                sc.setVisible(false);
                sc.dispose();
            }
            catch(InterruptedException ex1)
            {
                // System.out.println("Exception with splash screen: " + ex1.toString());
            }
        }

        //senses if the Close Window button is pressed
		addWindowListener(new java.awt.event.WindowAdapter()
        {
			public void windowClosing(java.awt.event.WindowEvent e)
            {
				thisWindowClosing(e);
			}
		});

		// Set size
		this.setSize( jipt_settings.getParentWindowSize() );

        // Set position
        this.setLocation( jipt_settings.getWindowPosition() );

		// Set the desktop pane
		desktop_pane = new JDesktopPane();

//      jipt_settings.addCustomColor("Test purple", 128, 0, 128); //create custom color

        Color desktop_color = jipt_settings.getColorByName( jipt_settings.getBGColor() );
        desktop_pane.setBackground( desktop_color );
        //JScrollPane scroll_pane = new JScrollPane( desktop_pane );

 		//this.getContentPane().add(scroll_pane);
        this.getContentPane().add( desktop_pane );
        this.setTitle("Java Image Processing Toolkit");
        this.setIconImage( new ImageIcon( JIPTsettings.GRAPHICS_PATH + "JIPT.gif").getImage() );

		// Create the manager classes

		frame_manager = new FrameManager( this );
        edit_manager = new EditManager( this );
		tool_manager  = new ToolManager( this );
        filter_manager = new FilterManager(this);
        color_manager  = new ColorManager(this, tool_manager);

		// Set the menu bar
        menu_bar = new JIPTMenuBar(this);
        this.setJMenuBar(this.menu_bar);
        show();

//////////////////////////////////////////////////////////////////BEGIN
        //Create the popup menu.
        popup = new JPopupMenu();

        rightMinMenuItem = new JMenuItem("Minimize all windows", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "minwin.gif") );
        rightMinMenuItem.addActionListener(this);
        rightMinMenuItem.setEnabled(false);
        popup.add(rightMinMenuItem);

        rightMaxMenuItem = new JMenuItem("Maximize all windows", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "maxwin.gif") );
        rightMaxMenuItem.addActionListener(this);
        rightMaxMenuItem.setEnabled(false);
        popup.add(rightMaxMenuItem);

        rightRestoreMenuItem = new JMenuItem("Restore all windows", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "restoreWin.gif") );
        rightRestoreMenuItem.addActionListener(this);
        rightRestoreMenuItem.setEnabled(false);
        popup.add(rightRestoreMenuItem);

        popup.addSeparator();

        rightCascadeMenuItem = new JMenuItem("Cascade windows", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "cascadeWin.gif") );
        rightCascadeMenuItem.addActionListener(this);
        rightCascadeMenuItem.setEnabled(false);
        popup.add(rightCascadeMenuItem);

        rightTileMenuItem = new JMenuItem("Tile windows", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "tileWin.gif"));
        rightTileMenuItem.addActionListener(this);
        rightTileMenuItem.setEnabled(false);
        popup.add(rightTileMenuItem);

        popup.addSeparator();

        rightCloseAllMenuItem = new JMenuItem("Close all windows", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "blank.gif") );
        rightCloseAllMenuItem.addActionListener(this);
        popup.add(rightCloseAllMenuItem);

        //Add listener to components that can bring up popup menus.
        MouseListener popupListener = new PopupListener();
        desktop_pane.addMouseListener(popupListener);

///////////////////////////////////////////////////////////////////END

		show();
}

	/////////////////////////////////////////////////////////
    /////////////////// Menu item selected //////////////////
    /////////////////////////////////////////////////////////
	public void actionPerformed(ActionEvent e)
	{
        // System.out.println("Menu item: " + e.getActionCommand() );
        //NEW
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.newFileMenuItem.getText() ) )
        {
            NewImageFrame nif = new NewImageFrame(this);
        }
        else
        //OPEN FILE
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.openFileMenuItem.getText() ) )
        {
            open_file();
        }
        else
        //REVERT FILE
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.revertFileMenuItem.getText() ) )
        {
            //get selected frame
            ImageFrame img_frame = frame_manager.getSelectedFrame();

            //revert image of selected frame
            img_frame.revertImage();
        }
        else
        //CLOSE FILE
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.closeFileMenuItem.getText() ) )
        {
            //get selected frame (frame to close)
            ImageFrame img_frame = frame_manager.getSelectedFrame();
            //call the internal frame event for closing the selected frame
            this.internalFrameClosing( new InternalFrameEvent( img_frame, img_frame.getID() ) );
        }
        else
        //CLOSE ALL FILES
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.closeAllFileMenuItem.getText() ) )
	    {
            closeAllFiles();
	    }
        else
        //SAVE FILE
        if(e.getActionCommand().equalsIgnoreCase( menu_bar.saveFileMenuItem.getText() ) )
        {
            ImageFrame img_frame = frame_manager.getSelectedFrame();
            if( !img_frame.hasChanged() )
                save_file( img_frame, false ); //prompt for filename= false
        }
        else
        //SAVE FILE AS
        if(e.getActionCommand().equalsIgnoreCase( menu_bar.saveAsFileMenuItem.getText() ) )
        {
            ImageFrame img_frame = frame_manager.getSelectedFrame();
            save_file( img_frame, true ); //prompt for filename = true
        }
        else
        //SAVE ALL FILES
        if(e.getActionCommand().equalsIgnoreCase( menu_bar.saveAllFileMenuItem.getText() ) )
        {
            for( int loop=0; loop < frame_manager.getNumFrames(); loop++ )
            {
                ImageFrame img_frame = frame_manager.getFrame( loop );
                if( !img_frame.hasChanged() )
                    save_file( img_frame, false ); //prompt for filename = false;
            }
        }
        else
        //COPY
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.copyEditMenuItem.getText() ) )
        {
           edit_manager.doCutCopyCrop(false, false, true);    //false to clear area, false to crop
        }
        else
        //CUT
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.cutEditMenuItem.getText() ) )
        {
           edit_manager.doCutCopyCrop(true, false, true);    //true to clear area, false to crop
        }
        else
        //CROP
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.cropEditMenuItem.getText() ) )
        {
            edit_manager.doCutCopyCrop(false, true, false);    //false if clear area, true to crop
        }
        else
        //PASTE
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.pasteEditMenuItem.getText() ) )
        {
            edit_manager.doPaste(true, false);
        }
        else
        //PASTE AS NEW IMAGE
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.pasteNewEditMenuItem.getText() ) )
        {
            this.createNewImage( edit_manager.getClipboardImage() );
        }
        else
        //CLEAR
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.clearEditMenuItem.getText() ) )
        {
           edit_manager.clearSelectedArea();    //clear window's selected area
        }
        else
        //SELECT ALL
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.selectAllEditMenuItem.getText() ) )
        {
            edit_manager.doSelectAll();
        }
        else
        //RESIZE image
        if(e.getActionCommand().equalsIgnoreCase( menu_bar.resizeToolsMenuItem.getText() ) )
        {
            resize_image();
        }
        else
        //Flip/Rotate image
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.flipRotateToolsMenuItem.getText() ) )
        {
            flip_rotate_image();
        }
        //Add Text
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.textToolsMenuItem.getText() ) )
        {
            text();
        }
        else
        //Negative image
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.negativeColorMenuItem.getText() ) )
        {
            negative_image();
        }
        else
        //Grayscale image
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.grayScaleColorMenuItem.getText() ) )
        {
            grayscale_image();
        }
        else
        //Channel isolation
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.isolationScaleColorMenuItem.getText() ) )
        {
            isolate_channel();
        }
        else
        //Channel Combination
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.combineColorMenuItem.getText() ) )
        {
            combine_channels();
        }
        else
        //Image Arithmetic
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.arithmaticScaleColorMenuItem.getText() ) )
        {
            image_arithmetic();
        }
        else
        //Brightness Contrast
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.brightnessContrastColorMenuItem.getText() ) )
        {
           brightness_contrast();
        }
        //Hue Adjustment
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.hueAdjustColorMenuItem.getText() ) )
        {
           hue();
        }
        else
        // Color depth
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.bits1ColorMenuItem.getText() ) )
        {
           reduce_color(1);
        }
        else
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.bits4ColorMenuItem.getText() ) )
        {
           reduce_color(4);
        }
        else
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.bits8ColorMenuItem.getText() ) )
        {
           reduce_color(8);
        }
        else
        //HSI Adjustment
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.hsiAdjustColorMenuItem.getText() ) )
        {
           hsi();
        }
        else
        //RGB Adjustment
        if(e.getActionCommand().equalsIgnoreCase(menu_bar.rgbAdjustColorMenuItem.getText() ) )
        {
           rgb();
        }
        else
        //EXIT JIPT
        if(e.getActionCommand().equalsIgnoreCase( menu_bar.exitFileMenuItem.getText() ) )
        {
            this.shutdown();
        }
        else
        //Open Image Information frame
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.infoToolsMenuItem.getText() ) )
        {
            this.openInfoFrame();
        }
        else
        //OPEN HISTOGRAM FRAME
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.graphColorMenuItem.getText() ) )
        {
            this.openHistogramFrame();
        }
        else
        //COUNT UNIQUE COLORS
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.countColorMenuItem.getText() ) )
        {
            this.countUniqueColors();
        }
        else
        // Mean/Median frame
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.meanMedianFiltersMenuItem.getText() ) )
        {
            this.meanMedianFilter();
        }
        else
        // Edge detection
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.edgeFiltersMenuItem.getText() ) )
        {
            this.edgeDetectionFilter();
        }
        else
        // Create Custom Filter
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.createCustomFiltersMenuItem.getText() ) )
        {
            this.createFilter();
        }
        else
        // Load filter
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.manageCustomFiltersMenuItem.getText() ) )
        {
            this.loadFilter();
        }
        else
        // Cascade windows      /// CASCADE
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.cascadeWindowMenuItem.getText() ) )
        {
            this.cascadeWindows();
        }
        else
        //Tile windows          /// TILE
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.tileWindowMenuItem.getText() ) )
        {
            this.tileWindows();
        }
        else
        // ABOUT DIALOG
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.aboutHelpMenuItem.getText() ) )
        {
           JIPTAlert.ok(this, "Authors: Trent Lucier, James LaTour, and Victor Rego\nCIS 480\nFall Semester, 2001", "About JIPT...", "OK", JOptionPane.INFORMATION_MESSAGE );
        }
        else
        // HELP WINDOW
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.topicHelpMenuItem.getText() ) )
        {
           new HelpFrame();
        }
        else
        //UNDO
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.undoEditMenuItem.getText() ) )
        {
            ImageFrame img_frame = frame_manager.getSelectedFrame();
            img_frame.undo();
        }
        else
        //REDO
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.redoEditMenuItem.getText() ) )
        {
            ImageFrame img_frame = frame_manager.getSelectedFrame();
            img_frame.redo();
        }

        else
        //OPTIONS
        if( e.getActionCommand().equalsIgnoreCase( menu_bar.settingsMenuItem.getText() ) )
        {
            JIPTOptionFrame jof = new JIPTOptionFrame(this);
        }
        else//Check right-click menu items
        //RIGHT CLICK RESTORE ALL WINDOWS
        if( e.getActionCommand().equalsIgnoreCase( this.rightRestoreMenuItem.getText() ) )
        {
            this.maxMinAllWindows(false, false);    //false,false = restore
        }
        else
        //RIGHT CLICK MIN ALL WINDOWS
        if( e.getActionCommand().equalsIgnoreCase( this.rightMinMenuItem.getText() ) )
        {
            this.maxMinAllWindows(false, true);     //false, true = minimize
        }
        else
        //RIGHT CLICK MAXIMIZE ALL WINDOWS
        if( e.getActionCommand().equalsIgnoreCase( this.rightMaxMenuItem.getText() ) )
        {
            this.maxMinAllWindows(true, false);     //true, false = maximine
        }
        else
        //RIGHT CLICK TILE WINDOWS
        if( e.getActionCommand().equalsIgnoreCase( this.rightTileMenuItem.getText() ) )
        {
            this.tileWindows();
        }
        else
        // RIGHT-CLICK CASCADE WINDOWS
        if( e.getActionCommand().equalsIgnoreCase( this.rightCascadeMenuItem.getText() ) )
        {
            this.cascadeWindows();
        }
        else
        //RIGHT-CLICK CLOSE ALL FILES
        if( e.getActionCommand().equalsIgnoreCase( this.rightCloseAllMenuItem.getText() ) )
        {
            this.closeAllFiles();
        }
        else //check for window operations, recently opened list
        {
            //WINDOW MENU OPERATIONS
            if( this.doWindowOperation(e) );
            else
                //check if RECENTLY OPENED FILE
                this.checkRecentFile(e);
        }

	}//end action performed
    ///////////////////////                                 ////////////////////
    ///////////////////////     ACTION PERFORMED: END       ////////////////////
    ///////////////////////                                 ////////////////////

	// Close the window when the close box is clicked
	private void thisWindowClosing(java.awt.event.WindowEvent e)
	{
        this.shutdown();
	}

    private void shutdown()
    {
        //Close all open files
        this.closeAllFiles();

		//update window size
		jipt_settings.setSavedWindowSize( this.getX(), this.getY(), this.getWidth(), this.getHeight() );

        boolean retry = true;
        while(retry)
        {
        	
            //SAVE WINDOW SETTINGS
            if( jipt_settings.saveSettings() )
            {
                setVisible(false);
                dispose();
                System.exit(0);
            }

            else
            {
                //0 - ignore, 1 - retry, 2 - cancel
                int result = JIPTAlert.yesNoCancel(this, "An error occured writing to file " + jipt_settings.getINIFilename(), "File I/O error", "Ignore", "Retry", "Cancel", JOptionPane.ERROR_MESSAGE );
                //if OK to continue, exit anyway
                if( result == 0 ) //ignore
                    System.exit(0);
                if( result == 2 ) //cancel
                    retry = false;
            }
        }//end while
    }

	/////////////////////////////
	////// Open file ////////////
	/////////////////////////////
	public void open_file()
	{
		/// Open a file ///
		JFileChooser chooser = new JFileChooser();
		String file_name	 = null;   // Name of the file

        File dir = new File( jipt_settings.getCurrentPath() );

        //Establish current directory
        chooser.setCurrentDirectory( dir );

        // Apply graphics-type filter
        // Note: source for ExampleFileFilter can be found in FileChooserDemo,
        // under the demo/jfc directory in the Java 2 SDK, Standard Edition.

    	ExampleFileFilter filter = new ExampleFileFilter();
        ExampleFileFilter GIFfilter = new ExampleFileFilter();
        ExampleFileFilter BMPfilter = new ExampleFileFilter();
        ExampleFileFilter JPGfilter = new ExampleFileFilter();
        ExampleFileFilter PPMfilter = new ExampleFileFilter();
        ExampleFileFilter PGMfilter = new ExampleFileFilter();

        filter.addExtension("bmp");
        filter.addExtension("gif");
        filter.addExtension("jpg");
        filter.addExtension("jpeg");
        filter.addExtension("pgm");
        filter.addExtension("ppm");
        filter.setDescription("All supported formats");
        filter.setExtensionListInDescription(false);

        BMPfilter.addExtension("bmp");
        BMPfilter.setDescription("Bitmap Images (*.bmp)");
        BMPfilter.setExtensionListInDescription(false);

        GIFfilter.addExtension("gif");
        GIFfilter.setDescription("GIF Images (*.gif)");
        GIFfilter.setExtensionListInDescription(false);

        JPGfilter.addExtension("jpg");
        JPGfilter.addExtension("jpeg");
        JPGfilter.setDescription("JPG Images (*.jpg, *.jpeg)");
        JPGfilter.setExtensionListInDescription(false);

        PGMfilter.addExtension("pgm");
        PGMfilter.setDescription("PGM Images (*.pgm)");
        PGMfilter.setExtensionListInDescription(false);

        PPMfilter.addExtension("ppm");
        PPMfilter.setDescription("PPM Images (*.ppm)");
        PPMfilter.setExtensionListInDescription(false);

        chooser.addChoosableFileFilter(filter);
        chooser.addChoosableFileFilter(BMPfilter);
        chooser.addChoosableFileFilter(GIFfilter);
        chooser.addChoosableFileFilter(JPGfilter);
        chooser.addChoosableFileFilter(PGMfilter);
        chooser.addChoosableFileFilter(PPMfilter);
        chooser.setFileFilter( filter );

        int returnVal = chooser.showOpenDialog(this);

		// Get the path & filename
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			file_name = chooser.getSelectedFile().getAbsolutePath();
		}
		else
		{
			return;
		}

        //Save image load path as current path
        jipt_settings.setCurrentPath( FileManager.parsePath( file_name ) );

        //create frame with file_name
        createImageFrame(file_name);
	}

    //Actually creates the image frame with the file filename
    private void createImageFrame( String filename )
    {
		ImageFrame img_frame = new ImageFrame( this, filename );
        img_frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //set file name
        img_frame.setTitle( FileManager.parseFileName(filename) );
        jipt_settings.addRecentFile(filename);
        configureNewImageFrame(img_frame);
    }

    public ImageFrame createNewImage( Image img )
    {
        ImageFrame img_frame = new ImageFrame( this, img );
        img_frame.setHasChanged(true);
        configureNewImageFrame(img_frame);

        return img_frame;
    }

    public void createNewImage( int x, int y )
    {
        //prompt for window size
        int pix[] = new int[x*y];
        int clearPixel = jipt_settings.getClearPixelColor();
        for(int i=0; i<(x*y); i++)
            pix[i] = clearPixel;
        Image im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(x, y, pix, 0, x));
        createNewImage(im);
    }

    private void configureNewImageFrame(ImageFrame img_frame)
    {
        img_frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        img_frame.setShowHistogram( jipt_settings.getAutoHistogram() );
        img_frame.addInternalFrameListener(this);
        img_frame.setDoubleBuffered( true );
		frame_manager.addFrame( img_frame );
		desktop_pane.add(img_frame);
		img_frame.show();

        //update Menu Bar
        this.recreateMenuBar();

        //allow maximize/minimize of icons
        menu_bar.setWindowStateMenuItems( img_frame.isIcon(), img_frame.isMaximum() );
        this.enableRightMenuItems();

        //auto show info frame?
        if( jipt_settings.getAutoFileInfo() )
            img_frame.setShowFileInfo( true );
        else img_frame.setShowFileInfo( false );

        updateInfoFrame();

        //make new window active
        try
        {
            img_frame.setSelected( true );
        }
        catch(Exception e)
        {
            // System.out.println("Exception setting im_frame ("+img_frame.getTitle()+") as selected");
        }
    }

	/////////////////////////////
	////// Save file ////////////
	/////////////////////////////
	public void save_file( ImageFrame img_frame, boolean promptFilename )
	{
        String file_name = img_frame.getFilename();
        String ext = file_name.substring( file_name.indexOf(".")+1 );

        //prompt for filename?
        if( promptFilename )
        { //get filename from chooser
            JFileChooser chooser = new JFileChooser();

            //get current directory

            File dir = new File( jipt_settings.getCurrentPath() );

            //Establish current directory
            chooser.setCurrentDirectory( dir );

            //Establish current filename
            chooser.setSelectedFile( new File( img_frame.getFilename() ) );

            // Apply graphics-type filter
            // Note: source for ExampleFileFilter can be found in FileChooserDemo,
            // under the demo/jfc directory in the Java 2 SDK, Standard Edition.

            ExampleFileFilter GIFfilter = new ExampleFileFilter();
            ExampleFileFilter BMPfilter = new ExampleFileFilter();
            ExampleFileFilter JPGfilter = new ExampleFileFilter();
            ExampleFileFilter PPMfilter = new ExampleFileFilter();
            ExampleFileFilter PGMfilter = new ExampleFileFilter();

            BMPfilter.addExtension("bmp");
            BMPfilter.setDescription("Bitmap Image (*.bmp)");
            BMPfilter.setExtensionListInDescription(false);

            GIFfilter.addExtension("gif");
            GIFfilter.setDescription("GIF Image (*.gif)");
            GIFfilter.setExtensionListInDescription(false);

            JPGfilter.addExtension("jpg");
            JPGfilter.addExtension("jpeg");
            JPGfilter.setDescription("JPG Image (*.jpg)");
            JPGfilter.setExtensionListInDescription(false);

            PGMfilter.addExtension("pgm");
            PGMfilter.setDescription("PGM Image (*.pgm)");
            PGMfilter.setExtensionListInDescription(false);

            PPMfilter.addExtension("ppm");
            PPMfilter.setDescription("PPM Image (*.ppm)");
            PPMfilter.setExtensionListInDescription(false);

            chooser.addChoosableFileFilter(BMPfilter);
            chooser.addChoosableFileFilter(GIFfilter);
            chooser.addChoosableFileFilter(JPGfilter);
            chooser.addChoosableFileFilter(PGMfilter);
            chooser.addChoosableFileFilter(PPMfilter);
            if( ext.equalsIgnoreCase("gif") )
                chooser.setFileFilter( GIFfilter );
            else if( ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("jpg") )
                chooser.setFileFilter( JPGfilter );
            else if( ext.equalsIgnoreCase("bmp") )
                chooser.setFileFilter( BMPfilter );
            else if( ext.equalsIgnoreCase("pgm") )
                chooser.setFileFilter( PGMfilter );
            else chooser.setFileFilter( PPMfilter );

            int returnVal = chooser.showSaveDialog(this);

    		// Get the path & filename
    		if(returnVal == JFileChooser.APPROVE_OPTION)
    			file_name = chooser.getSelectedFile().getAbsolutePath();
    		else
		    	return;

            //set .extension
            if(file_name.indexOf(".") == -1)
            {
                ext= chooser.getFileFilter().getDescription();
                ext = ext.substring( ext.indexOf("."), ext.length()-1 );
                file_name = file_name + ext;
            }

        }//end promptFilename if

        //Save image load path as current path
        jipt_settings.setCurrentPath( FileManager.parsePath( file_name ) );

        //write image to file
        FileManager.saveImage( img_frame.getJIPTImage().getImage(), file_name );

        //write current file to recent files list
        jipt_settings.addRecentFile( file_name );

        //change image file name
        img_frame.setFilename( file_name );

        //update image to reflect file-save changes (quality, etc)
        img_frame.reloadImage();

        //update bpp
        file_name = FileManager.parseFileName(file_name);
        file_name.toLowerCase();
        int bpp = 24;
        if( file_name.endsWith("gif") )
            bpp = 8;
        img_frame.getJIPTImage().setBpp(bpp);

        //create new menu bar to reflect changes
        this.recreateMenuBar();

        //update info frame
        updateInfoFrame();
	}

    private void updateInfoFrame()
    {
        ImageFrame im_frame = frame_manager.getSelectedFrame();
        if( im_frame != null )
            frame_manager.updateInfoFrame( im_frame );
    }

    private void enableMenuItems( boolean isColor )
    {
        //isColor specifies if they active window uses color or grayscale
        this.colorMenuItems = isColor;
    }

    public void closeThisFrame(ImageFrame img_frame)
    {
        this.internalFrameClosing( new InternalFrameEvent( img_frame, img_frame.getID() ) );
    }

    ////////////////////////////////////////
    ///// Get the number of open frames ////
    ////////////////////////////////////////
    public int getNumFrames()
    {
        return frame_manager.getNumFrames();
    }

    ////////////////////////////
    //////// Resize Image //////
    ////////////////////////////
    public void resize_image()
    {
        tool_manager.resize(frame_manager.getSelectedFrame());
    }

    ////////////////////////////////
    ////////// Edge Detection //////
    ////////////////////////////////
    public void edgeDetectionFilter()
    {
       filter_manager.edgeDetection(frame_manager.getSelectedFrame());
    }

    //////////////////////////////////
    //// Mean/Median filter //////////
    //////////////////////////////////
    public void meanMedianFilter()
    {
        filter_manager.meanMedian(frame_manager.getSelectedFrame());
    }

    ////////////////////////////////////
    /////////// Create Filter //////////
    ////////////////////////////////////
    public void createFilter()
    {
        filter_manager.createFilter();
    }

    ////////////////////////////////////
    /////////// Load Filter   //////////
    ////////////////////////////////////
    public void loadFilter()
    {
        filter_manager.loadFilter(frame_manager.getSelectedFrame());
    }

    ////////////////////////////////////
    ///////// Flip/Rotate Image ////////
    ////////////////////////////////////
    public void flip_rotate_image()
    {
            tool_manager.flip_rotate(frame_manager.getSelectedFrame());
    }

    ////////////////////////////////////
    ///////// Add Text To Image ////////
    ////////////////////////////////////
    public void text()
    {
            tool_manager.add_text(frame_manager.getSelectedFrame());
    }

    ////////////////////////////////////
    ///////// Negative Image ///////////
    ////////////////////////////////////
    public void negative_image()
    {
            tool_manager.negative_image(frame_manager.getSelectedFrame());
    }

    ////////////////////////////////////
    ///////// Grayscale Image //////////
    ////////////////////////////////////
    public void grayscale_image()
    {
          tool_manager.grayscale_frame(frame_manager.getSelectedFrame());
    }

    ////////////////////////////////////
    ///////// Isolate Channel //////////
    ////////////////////////////////////
    public void isolate_channel()
    {
       tool_manager.isolate_channel(frame_manager.getSelectedFrame());
    }

    ////////////////////////////////////
    ///////// Combine Channel //////////
    ////////////////////////////////////
    public void combine_channels()
    {
       color_manager.combine_channels(frame_manager);
    }

    ////////////////////////////////////
    ///////// Image Arithmetic /////////
    ////////////////////////////////////
    public void image_arithmetic()
    {
        color_manager.arithmetic_frame(frame_manager);
    }

    /////////////////////////////////////
    ///////// Brightness/Contrast ///////
    /////////////////////////////////////
    public void brightness_contrast()
    {
        color_manager.brightness_contrast_frame(frame_manager.getSelectedFrame());
    }

    ///////////////////////////////////
    //////////////// Hue //////////////
    ///////////////////////////////////
    public void hue()
    {
        color_manager.hue_frame(frame_manager.getSelectedFrame());
    }
    ///////////////////////////////////
    //////////////// HSI //////////////
    ///////////////////////////////////
    public void hsi()
    {
        color_manager.hsi_frame(frame_manager.getSelectedFrame());
    }

     ///////////////////////////////////
    //////////////// RGB //////////////
    ///////////////////////////////////
    public void rgb()
    {
        color_manager.rgb_frame(frame_manager.getSelectedFrame());
    }

    //////////////////////////////////////////////
    ////////// Check Doc Title for Uniqueness ////
    //////////////////////////////////////////////
    public boolean isUniqueName(String name)
    {
        return frame_manager.isUniqueName(name);
    }



    ////////////////////////////////////
    /////// Reduce color depth /////////
    ////////////////////////////////////
    public void reduce_color(int new_depth)
    {
        // depths are either 1, 4, or 8
        color_manager.reduce_color(frame_manager.getSelectedFrame(), new_depth);

    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////// INTERNAL FRAME EVENTS ///////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    public void internalFrameClosing(InternalFrameEvent e)
    {
        ImageFrame img_frame = (ImageFrame)e.getInternalFrame();
        if( img_frame.hasChanged() )
        {
            //save file (if needed)
            if( promptSaveFile( img_frame ) )
            {
                frame_manager.removeFrame( img_frame );
                img_frame.dispose();
            }
        }
        else
        {
            frame_manager.removeFrame( img_frame );
            img_frame.dispose();
        }
    }

    public void internalFrameClosed(InternalFrameEvent e)
    {
        menu_bar.disableFrameActiveActions( edit_manager.getClipboardImage() != null );
        //Check to see if all windows are closed
        if( frame_manager.getNumFrames() == 0 )
        {
            this.disableRightMenuItems();
        }

        //update Menu Bar
        recreateMenuBar();
    }

    public void internalFrameOpened(InternalFrameEvent e)
    {
        //XXX: We don't seem to get any of these.
        //displayMessage("Internal frame opened", e);
    }

    public void internalFrameIconified(InternalFrameEvent e)
    {
        //displayMessage("Internal frame iconified", e);
        ImageFrame j = frame_manager.getFrame( e.getInternalFrame().getTitle() );
        menu_bar.setWindowStateMenuItems( j.isIcon(), j.isMaximum() );

        menu_bar.disableFrameActiveActions( edit_manager.getClipboardImage() != null );
    }

    public void internalFrameDeiconified(InternalFrameEvent e)
    {
        //displayMessage("Internal frame deiconified", e);
        ImageFrame j = frame_manager.getFrame( e.getInternalFrame().getTitle() );
        menu_bar.setWindowStateMenuItems( j.isIcon(), j.isMaximum() );

        menu_bar.enableFrameActiveActions( edit_manager.getClipboardImage() != null );

        //clear selection rectangle
        j.clearSelectionRectangle();
    }

    public void internalFrameActivated(InternalFrameEvent e)
    {
        //displayMessage("Internal frame activated", e);
        ImageFrame j = frame_manager.getSelectedFrame();
        if( j != null )
        {
            j.revalidate();
            //enable Active-Frame Menubar Actions
            menu_bar.enableFrameActiveActions( edit_manager.getClipboardImage() != null );
            j.enablePasteAction( edit_manager.getClipboardImage() != null );

            //enable undo and redo operations
            if( j.isUndoEnabled() )
                menu_bar.enableUndo();
            else menu_bar.disableUndo();
            if( j.isRedoEnabled() )
                menu_bar.enableRedo();
            else menu_bar.disableRedo();

            //update info frame
            updateInfoFrame();

            //clear selection rectangle
            j.clearSelectionRectangle();

            //make new window active
            try
            {
                j.setSelected( true );
                j.updateZoom();
            }
            catch(Exception exp)
            {
                // System.out.println("Exception setting im_frame ("+j.getTitle()+") as selected");
            }
        }
        else
        {
            //hide histogram and info frame
            frame_manager.showHistogramFrame( false );
            frame_manager.showInfoFrame( false );
        }
    }

    public void internalFrameDeactivated(InternalFrameEvent e)
    {
/*        //displayMessage("Internal frame deactivated", e);
        ImageFrame j = frame_manager.getFrame( e.getInternalFrame().getTitle() );

        //clear selection rectangle
        j.clearSelectionRectangle();

        //resize window
        if( j.getSize().getWidth() < ImageFrame.BORDER_WIDTH )
                j.setSize( ImageFrame.BORDER_WIDTH, (int)j.getSize().getHeight() );
        if( j.getSize().getHeight() < ImageFrame.BORDER_HEIGHT )
                j.setSize( (int)j.getSize().getWidth(), ImageFrame.BORDER_HEIGHT );
*/    }

    // END INTERNAL FRAME EVENTS /////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////

    //called when "Image Information" is selected from the Tools Menu or when AutoInfoFrame is true
    private void openInfoFrame()
    {
        ImageFrame im_frame = frame_manager.getSelectedFrame();
        im_frame.setShowFileInfo( true );
        updateInfoFrame();
    }

    //called when "Histogram" is selected from the Tools Menu or when AutoInfoFrame is true
    private void openHistogramFrame()
    {
        ImageFrame im_frame = frame_manager.getSelectedFrame();
        im_frame.setShowHistogram( true );
        HistogramFrame hist_frame = frame_manager.getHistogramFrame( frame_manager.getFrameIndex( frame_manager.getSelectedFrame() ));
        hist_frame.setVisible( true );
        //updateHistogramFrame();
    }

    //called when "Tile" is selected from the Window Menu
    private void tileWindows()
    {   //tiles horizontally
        int maxWidth = this.desktop_pane.getWidth();
        int maxHeight = this.desktop_pane.getHeight();
        int sizeX = maxWidth / frame_manager.getNumFrames();
        int dx = 0;
        for( int loop=0; loop<frame_manager.getNumFrames(); loop++ )
        {
            ImageFrame f = frame_manager.getFrame( loop );
            try
            {
                f.setIcon( false );
                f.setMaximum( false );
            }
            catch(Exception exc)
            {
                // System.out.println("Exception tiling windows..." + exc.toString() );
            }
            f.setSize( sizeX, maxHeight );
            f.setLocation( dx, 0 );
            dx += sizeX;
            f.clearSelectionRectangle();
        }
    }

    //called when "Cascade" is selected from the Window Menu

    private void cascadeWindows()
    {
        double dx = 0.5;
        double dy = 0.5;
        int x = 30;
        int y = 30;
        for( int loop=0; loop<frame_manager.getNumFrames(); loop++ )
        {
            ImageFrame f = frame_manager.getFrame( loop );
            try
            {
                f.setIcon( false );
                f.setMaximum( false );
                f.moveToFront();
            }
            catch(Exception exc)
            {
                // System.out.println("Exception cascading windows..." + exc.toString() );
            }
            f.setSize( (int)(this.desktop_pane.getWidth()*dx), (int)(this.desktop_pane.getHeight()*dy) );
            f.setLocation( x,y );
            x += 30;  y += 30;
            f.clearSelectionRectangle();
        }
    }

    //called when the background color is changed (Options menu, background)
    private boolean checkColorChange(ActionEvent e)
    {
        //check for color change
        for( int loop=0; loop < jipt_settings.getNumColors(); loop++ )
            if( e.getActionCommand().equalsIgnoreCase( jipt_settings.getColor( loop ).getName() ) )
            {
                this.desktop_pane.setBackground( jipt_settings.getColor( loop ).getColor() );
                jipt_settings.setBGColor( e.getActionCommand() );

                return true;
            }
        //check for color change (custom colors)
        for( int loop=0; loop < jipt_settings.getNumCustomColors(); loop++ )
        {
            JIPTColor c = (JIPTColor) jipt_settings.getCustomColor( loop );
            if( e.getActionCommand().equalsIgnoreCase( c.getName() ) )
            {
                this.desktop_pane.setBackground( c.getColor() );
                jipt_settings.setBGColor( e.getActionCommand() );
                return true;
            }
        }
        return false;
    }

    //checks if Recently Opened File is selected
    private boolean checkRecentFile(ActionEvent e)
    {
        for( int loop = 0; loop < jipt_settings.getNumRecentFiles(); loop++ )
        {
            if( e.getActionCommand().startsWith(""+(loop+1)) )
            {
                createImageFrame( jipt_settings.getRecentFile( loop ) );
                return true;
            }
        }//end for
        return false;
    }

    //check if "Maximize", "Minimize", or "Restore" was selected from Window menu
    // not to be confused with maxMinAllWindows, this only effects a SINGLE window
    private boolean doWindowOperation(ActionEvent e)
    {
        //check if currently opened file - window listing
        for( int loop = 0; loop < frame_manager.getNumFrames(); loop++ )
        {
            ImageFrame img_frame = (ImageFrame)frame_manager.getFrame( loop );
            String title = img_frame.getDocTitle();

            try
            {
                if( e.getActionCommand().equalsIgnoreCase( title ) )
                {
                    img_frame.setIcon( false );
                    img_frame.setMaximum( false );
                    img_frame.setSelected( true );
                    return true;
                }
                else
                if( img_frame.isSelected() )
                {
                    if( e.getActionCommand().equalsIgnoreCase( menu_bar.maxWindowMenuItem.getText() ) )
                    {
                        img_frame.setMaximum(true);
                        img_frame.setIcon(false);
                        return true;
                    }
                    else
                    if( e.getActionCommand().equalsIgnoreCase( menu_bar.minWindowMenuItem.getText() ) )
                    {
                        img_frame.setIcon(true);
                        img_frame.setMaximum(false);
                        return true;
                    }
                    else
                    if( e.getActionCommand().equalsIgnoreCase( menu_bar.restoreWindowMenuItem.getText() ) )
                    {
                        img_frame.setIcon( false );
                        img_frame.setMaximum( false );
                        return true;
                    }
                }
            }
            catch( Exception exc )
            {
                // System.out.println( "Cannot make window selected") ;
            }

        }//end for
        return false;
    }//end doWindowOperation

    //check for change in SIZE OF UNDO STEPS
    //   returns true because menu item matched
    public boolean checkUndoSizeChange(ActionEvent e)
    {
        if( e.getActionCommand().equalsIgnoreCase("Disable undo") )
        {
            jipt_settings.setMaxUndoSize( 0 );
            return true;
        }
        else
        if( e.getActionCommand().endsWith("Action") || e.getActionCommand().endsWith("Actions") )
        {
            //get num of undo steps
            int numSteps = Integer.parseInt( e.getActionCommand().substring(0,1) );
            jipt_settings.setMaxUndoSize( numSteps );
            return true;
        }
        return false;
    }

    public FrameManager getFrameManager()
    {
        return this.frame_manager;
    }

    public ToolManager getToolManager()
    {
        return this.tool_manager;
    }

    public EditManager getEditManager()
    {
        return this.edit_manager;
    }

    public JIPTsettings getJIPTsettings()
    {
        return this.jipt_settings;
    }

    public void countUniqueColors()
    {
        int numColors = tool_manager.countUniqueColors( frame_manager.getSelectedFrame() );
        JIPTAlert.ok(this, "Number of colors: " + numColors, "Count Unique Colors", "OK", JOptionPane.INFORMATION_MESSAGE );
    }

    ////////////////////////
    /// PROMPT SAVE FILE ///
    ////////////////////////
    //  returns true if OK to close file
    //  returns false if CANCEL is selected (don't close file)
    public boolean promptSaveFile( ImageFrame img_frame )
    {
        // save       = option 1, returns 0
        // dont save  = option 2, returns 1
        // cancel     = option 3, returns 2
        int result = JIPTAlert.yesNoCancel(this, "Save file before closing?", "File is not saved", "Save As...", "Don't Save", "Cancel", JOptionPane.QUESTION_MESSAGE );

        if( result == 0 )
        {
            this.save_file( img_frame, true );
            System.out.println("WILL CLOSE");
            return true;  //close
        }
        else if( result == 1 )
        {
        	System.out.println("WILL CLOSE");
            return true;  //close
        }
        //else if( result == 1 )
            //do something to stop from closing image frame  ////////////////////////   DO THIS!!!
        
        
        System.out.println("NOT CLOSING");
        return false; //don't close
    }

    private void closeAllFiles()
    {
        while( frame_manager.getNumFrames() > 0 )
        {
            ImageFrame img_frame = frame_manager.getFrame( 0 );
            this.internalFrameClosing( new InternalFrameEvent( img_frame, img_frame.getID() ) );
        }
    }

    private void maxMinAllWindows(boolean max, boolean min)
    {
        int numFrames = frame_manager.getNumFrames();
        for( int i=0; i<numFrames; i++)
        {
            ImageFrame img_frame = frame_manager.getFrame(i);
            try
            {
                if( max )
                {
                    img_frame.setMaximum(true);
                    img_frame.setIcon(false);
                }
                else if( min )
                    {
                        img_frame.setMaximum(false);
                        img_frame.setIcon(true);
                    }
                    else
                    {
                        img_frame.setMaximum(false);
                        img_frame.setIcon(false);
                    }
            }
            catch(Exception e)
            {
                // System.out.println("Cannot change window properties");
            }
            //clear selection rectangle
            img_frame.clearSelectionRectangle();

        }
    }

    public void disableRightMenuItems()
    {
        rightMaxMenuItem.setEnabled( false );
        rightMinMenuItem.setEnabled( false );
        rightRestoreMenuItem.setEnabled( false );
        rightCascadeMenuItem.setEnabled( false );
        rightTileMenuItem.setEnabled( false );
    }

    public void enableRightMenuItems()
    {
        rightMaxMenuItem.setEnabled( true );
        rightMinMenuItem.setEnabled( true );
        rightRestoreMenuItem.setEnabled( true );
        rightCascadeMenuItem.setEnabled( true );
        rightTileMenuItem.setEnabled( true );
    }

    public void recreateMenuBar()
    {
        menu_bar.updateMenuBar();
    }

///////////////////////////////////////////////////////////BEGIN
    class PopupListener extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e)
        {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e)
        {
            if (e.isPopupTrigger())
            {
                popup.show( e.getComponent(),e.getX(), e.getY() );
            }
        }
    }
///////////////////////////////////////////////////////////END

}
