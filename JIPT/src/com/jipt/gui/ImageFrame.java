package com.jipt.gui;
/*
	James LaTour
	10/14/2001
	JIPTImageFrame.java

	This class is used to display an image.
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.jipt.FileManager;
import com.jipt.JIPTimage;
import com.jipt.JIPTsettings;
import com.jipt.ToolManager;

public class ImageFrame extends JInternalFrame implements ActionListener, ItemListener
{
    public static final int BORDER_HEIGHT = 86; //actual height (in pixels) of the top and bottom borders
    public static final int BORDER_WIDTH = 23;  //actual width (in pixels) of the left and right borders
    public static final int DEFAULT_WIDTH = 350;  //smallest window width possible
    public static final int DEFAULT_HEIGHT = 200; //smallest window height possible
    public static final int WINDOW_START = 30;      //initial window offset
    public static final int WINDOW_SPACING = 15;    //default window spacing

    private int ID; // unique image ID
    private String docTitle;    //this frame's window menu document title
    private boolean showInfo = false;   //determine if show file info
    private boolean showHistogram = false;   //determine if show histogram
    private boolean hasChanged = false; //determines if file is saved or not
    private int zoomRatio = 100;        //% of image size to display
    private boolean resizeUponZoom = false; //resize the frame when zoom is performed?

    //Image Revert/Undo/Redo datatypes
    private Image revert_image = null;
    private ArrayList undo_stack = null;
    private ArrayList redo_stack = null;

    private SelectArea select_area = null;  //image draw area
    private JToolBar taskbar = null;
        private JButton zoomInButton = null;
        private JButton zoomOutButton = null;
        private JButton zoomMenuButton = null;
        private JMenuBar zoomMenuBar = null;
        private JMenu zoomMenu = null;
        private JCheckBox autoResize = null;
        private JLabel xyPos = null;
        private JLabel xyDragPos = null;

    private JPopupMenu popup;       //right-click popup menu
        private JMenuItem rightCopyMenuItem = null;
        private JMenuItem rightCutMenuItem = null;
        private JMenuItem rightPasteMenuItem = null;
        private JMenuItem rightPasteNewMenuItem = null;
        private JMenuItem rightClearMenuItem = null;
        private JMenuItem rightSelectAllMenuItem = null;
        private JMenuItem rightCropMenuItem = null;
        private JMenuItem rightCloseMenuItem = null;
    public MouseListener popupListener = null;

	private JIPTimage image     = null;
    private JLabel im_canvas = null;
    private MainFrame main_frame= null;

	// Constructor w/ directory name & filename
	public ImageFrame( MainFrame mf, String f )
	{
        super();

        //store main frame
        this.main_frame = mf;

        //create image
        image = new JIPTimage( f );

        //finish initializing image frame
        configureImageFrame(image);
	}

    // Constructor from image
	public ImageFrame( MainFrame mf, Image img )
    {
        super();

        //store main frame
        this.main_frame = mf;

        //establish temp filename
        String filename = JIPTsettings.DEFAULT_PATH + "untitled." + mf.getJIPTsettings().getUntitledFileNumber();

        //create image
        image = new JIPTimage( img, filename );

        //finish initializing image frame
        configureImageFrame(image);
    }

	// configureImageFrame from JIPTimage
    public void configureImageFrame( JIPTimage image )
    {
        //create zoom taskbar
        taskbar = new JToolBar();
        this.makeZoomMenu();
        taskbar.setFloatable(false);

        //set frame's image
        im_canvas = new JLabel( new ImageIcon( image.getImage() ) );
        im_canvas.setSize( image.getImage().getWidth(main_frame), image.getImage().getHeight(main_frame) );

        //get filename
        String filename = image.getFilename();

        //set document list title
        this.setDocTitle( FileManager.parseFileName(filename) );

        //set imageFrame title
        this.setTitle( FileManager.parseFileName(filename) );

        //store revert_image
        this.revert_image = image.getImage();

        //create ArrayLists for Undo/Redo
        this.undo_stack = new ArrayList();
        this.redo_stack = new ArrayList();

		try
		{
			setIconifiable(true);
			setMaximizable(true);
			setClosable(true);

            //set window location
            int numWindows = main_frame.getFrameManager().getNumFrames() - 1;
            setLocation( (WINDOW_START + WINDOW_SPACING*numWindows), (WINDOW_START + WINDOW_SPACING*numWindows) );

            //establish minimal size
            this.setMinimumSize( new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT) );

			setResizable(true);
			setVisible(true);
            this.setFrameIcon( new ImageIcon(JIPTsettings.GRAPHICS_PATH + "image.gif") );
		}
		catch(Exception e)
		{
			// System.out.println("Exception " + e.toString());
			// PUT A MESSAGE HERE
		}

        //create select_area
        select_area = new SelectArea( this, main_frame, im_canvas );

        //create scrollable pane
        JScrollPane scroll_pane = new JScrollPane( select_area );
        scroll_pane.createHorizontalScrollBar();
        scroll_pane.createVerticalScrollBar();

        popup = this.createPopupMenu();
        //Add listener to components that can bring up popup menus.
        popupListener = new PopupListener();
        this.addMouseListener(popupListener);
        select_area.addMouseListener(popupListener);
        taskbar.addMouseListener(popupListener);

        //add to frame
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add( taskbar, BorderLayout.NORTH);
        this.getContentPane().add( scroll_pane, BorderLayout.CENTER );

        //set window height
        enforceWindowSize();
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //  setMaximum
    //               (Override from JInternalFrame)
    //    This clears the selection rectangle when frame is maximized/restored
    //
    public void setMaximum(boolean b) throws PropertyVetoException
    {
        //CALL ORIGINAL setMaximum routine
        super.setMaximum(b);

        ///CLEAR THE SELECTION RECTANGE (added JRL)
        select_area.eraseRectangle();
    }

	///////////////////////////////////
	////// Process a focus event //////
	///////////////////////////////////
	protected void processFocusEvent(FocusEvent e)
	{
		if(e.getID() == FocusEvent.FOCUS_LOST)
		{
			try
			{
				setSelected(false);
			}
			catch(Exception e1)
			{
			}
			return;
		}
		else
		{
            //resize window
			super.processFocusEvent(e);
		}
        this.clearSelectionRectangle();
	}

    //////////////////////
    //// GET FILENAME ////
    //////////////////////
    public String getFilename()
    {
        return this.image.getFilename();
    }

    public void setFilename(String f)
    {
        this.image.setFilename(f);
        this.setTitle( FileManager.parseFileName(f) );
    }

    public boolean isColor()
    {
        return !this.image.getGrayscale();
    }

    public boolean getGrayscale()
    {
        return this.image.getGrayscale();
    }

    public int getID()
    {
        return this.ID;
    }

    public void setID(int id)
    {
        this.ID = id;
    }

    public void setDocTitle( String s )
    {
        this.docTitle = s;
    }

    public String getDocTitle()
    {
        return this.docTitle;
    }

    public void setGrayscale( boolean b )
    {
        this.image.setGrayscale( b );
    }

    public boolean getShowFileInfo()
    {
        return this.showInfo;
    }

    public void setShowFileInfo( boolean b )
    {
        this.showInfo =  b;
    }

    public boolean getShowHistogram()
    {
        return this.showHistogram;
    }

    public void setShowHistogram( boolean b )
    {
        this.showHistogram = b;
    }

    public boolean hasChanged()
    {
        return this.hasChanged;
    }

    public void reloadImage()
    {
        String filename = this.getFilename();
        // System.out.println("Filename: "+filename);
        im_canvas.setIcon( new ImageIcon( FileManager.openImage(filename)) );
    }

    public void setHasChanged(boolean isModified)
    {
        this.hasChanged = isModified;
        String title = this.getTitle();
        if( isModified )
        {
            if( !title.endsWith("*") )    //if the label isn't already applied, apply it
                this.setTitle( title + "*");
        }
        else //remove modified label
            if( title.endsWith("*") )     //if label is applied, remove it
            {
                int index = title.indexOf("*");
                this.setTitle( title.substring(0,index) );
            }
    }

    public Rectangle getSelectRect()
    {
        return this.select_area.getRectangle();
    }

    public int getZoomRatio()
    {
        return this.zoomRatio;
    }

    public int getImageWidth()
    {
        return this.image.getImage().getWidth(this);
    }

    public int getImageHeight()
    {
        return this.image.getImage().getHeight(this);
    }

    public int getImageX()
    {
        return this.im_canvas.getX();
    }

    public int getImageY()
    {
        return this.im_canvas.getY();
    }

    public void enforceWindowSize()
    {
        int width = im_canvas.getWidth() + BORDER_WIDTH;
        int height = im_canvas.getHeight() + BORDER_HEIGHT;

        if( width < DEFAULT_WIDTH )
            width = DEFAULT_WIDTH;
        if( height < DEFAULT_HEIGHT )
            height = DEFAULT_HEIGHT;
        this.setSize( width, height );
    }

    public JIPTimage getJIPTImage()
    {
        return this.image;
    }

    public void setSelectedArea(int x, int y, int width, int height)
    {
        select_area.setRectangle(x, y, width, height);
    }

    private void makeZoomMenu()
    {
        Icon zoomInIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "zoomin.gif");
        Icon zoomOutIcon = new ImageIcon(JIPTsettings.GRAPHICS_PATH + "zoomout.gif");

        zoomInButton = new JButton( zoomInIcon );
        zoomInButton.setToolTipText("Zoom In");
        zoomInButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ZoomIn();
            }
        });

        zoomOutButton = new JButton( zoomOutIcon );
        zoomOutButton.setToolTipText("Zoom Out");
        zoomOutButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                ZoomOut();
            }
        });

        zoomMenuBar = new JMenuBar();
        zoomMenu = new JMenu("Zoom 100%");
        JMenuItem menuItem = new JMenuItem("25%");
        menuItem.addActionListener(this);
        zoomMenu.add( menuItem );

        menuItem = new JMenuItem("50%");
        menuItem.addActionListener(this);
        zoomMenu.add( menuItem );

        menuItem = new JMenuItem("75%");
        menuItem.addActionListener(this);
        zoomMenu.add( menuItem );

        menuItem = new JMenuItem("100%");
        menuItem.addActionListener(this);
        zoomMenu.add( menuItem );

        menuItem = new JMenuItem("125%");
        menuItem.addActionListener(this);
        zoomMenu.add( menuItem );

        menuItem = new JMenuItem("150%");
        menuItem.addActionListener(this);
        zoomMenu.add( menuItem );

        menuItem = new JMenuItem("200%");
        menuItem.addActionListener(this);
        zoomMenu.add( menuItem );
        zoomMenuBar.add( zoomMenu );

        //create checkBox
        autoResize = new javax.swing.JCheckBox("Auto Resize", this.resizeUponZoom );
        autoResize.addItemListener(this);

        //create X and Y position labels
        xyPos = new JLabel();
        xyDragPos = new JLabel();

        taskbar.add(zoomOutButton);
        taskbar.add(zoomMenuBar);
        taskbar.add(zoomInButton);
        taskbar.addSeparator();
        taskbar.add(autoResize);
        taskbar.addSeparator();
        taskbar.add(xyPos);
        taskbar.add(xyDragPos);

    }

    public void actionPerformed(ActionEvent e)
    {
       // System.out.println("Menu item: " + e.getActionCommand() );
        if( e.getActionCommand().equalsIgnoreCase( this.rightCopyMenuItem.getText() ) )
        {
            main_frame.getEditManager().doCutCopyCrop(false, false, true);
        }
        else
        if( e.getActionCommand().equalsIgnoreCase( this.rightCutMenuItem.getText() ) )
        {
            main_frame.getEditManager().doCutCopyCrop(true, false, true);
        }
        else
        if( e.getActionCommand().equalsIgnoreCase( this.rightPasteMenuItem.getText() ) )
        {
            main_frame.getEditManager().doPaste(true, false);
        }
        else
        if( e.getActionCommand().equalsIgnoreCase( this.rightPasteNewMenuItem.getText() ) )
        {
            main_frame.getEditManager().doCutCopyCrop(false,false, true);
            main_frame.createNewImage( main_frame.getEditManager().getClipboardImage() );
        }
        else
        if( e.getActionCommand().equalsIgnoreCase( this.rightClearMenuItem.getText() ) )
        {
            main_frame.getEditManager().clearSelectedArea();
        }
        else
        if( e.getActionCommand().equalsIgnoreCase( this.rightSelectAllMenuItem.getText() ) )
        {
            main_frame.getEditManager().doSelectAll();
        }
        else
        if( e.getActionCommand().equalsIgnoreCase( this.rightCropMenuItem.getText() ) )
        {
            main_frame.getEditManager().doCutCopyCrop(false, true, false);
        }
        else
        if( e.getActionCommand().equalsIgnoreCase( this.rightCloseMenuItem.getText() ) )
        {
            main_frame.closeThisFrame(this);
        }
        else //do zoom command
        {
            String command = e.getActionCommand();
            if( command.equalsIgnoreCase( "25%" ) )
                zoomRatio = 25;
            else if( command.equalsIgnoreCase( "50%" ) )
                zoomRatio = 50;
            else if( command.equalsIgnoreCase( "75%" ) )
                zoomRatio = 75;
            else if( command.equalsIgnoreCase( "100%" ) )
                zoomRatio = 100;
            else if( command.equalsIgnoreCase( "125%" ) )
                zoomRatio = 125;
            else if( command.equalsIgnoreCase( "150%" ) )
                zoomRatio = 150;
            else if( command.equalsIgnoreCase( "200%" ) )
                zoomRatio = 200;
            updateZoom();
            // System.out.println("Zoom level: " + zoomRatio );
        }

        //menu no longer showing
        select_area.setMenuShowing(false);
    }

    public void itemStateChanged(ItemEvent e)
    {
        //update resizeUponZoom
        resizeUponZoom = !( e.getStateChange() == ItemEvent.DESELECTED );
        this.clearSelectionRectangle();
    }

    public void updateZoom()
    {
        //set zoom level
        this.zoomMenu.setText( "Zoom " + zoomRatio + "%");

        //scale iamge
        int scaled_w = this.getImageWidth() * zoomRatio / 100;
        Image img = image.getImage().getScaledInstance( scaled_w, -1, Image.SCALE_FAST );
        im_canvas.setIcon( new ImageIcon( img ) );
        im_canvas.setSize( img.getWidth(this), img.getHeight(this) );

        //resize window (if needed)
        if( this.resizeUponZoom )
        {
            this.reshape(this.getX(), this.getY(),
                img.getWidth(this) + BORDER_WIDTH,
                img.getHeight(this) + BORDER_HEIGHT);
            enforceWindowSize();
        }
        //Set image boundaries
        select_area.setBoundaries();

        if( this.zoomRatio != 100 )
        {
            select_area.clearRectanglePoints();
        }
    }

    private void ZoomOut()
    {
        //Change Zoom Rate
        if( zoomRatio == 200 )
            zoomRatio = 150;
        else zoomRatio -= 25;

        if( !zoomInButton.isEnabled() )
            zoomInButton.setEnabled( true );

        if( zoomRatio <= 25 )
        {
            zoomOutButton.setEnabled( false );
            zoomRatio = 25;
        }

        //Apply new Zoom Rate
        updateZoom();
    }

    private void ZoomIn()
    {
        //Update Zoom Rate
        if( zoomRatio == 150 )
            zoomRatio = 200;
        else zoomRatio += 25;

        if( !zoomOutButton.isEnabled() );
            zoomOutButton.setEnabled( true );

        if( zoomRatio >= 200 )
        {
            zoomInButton.setEnabled( false );
            zoomRatio = 200;
        }

        //Apply new Zoom Rate
        updateZoom();
    }

    /// disable/enable frame controls while selection data is being dragged
    public void enableFrameControls(boolean b)
    {
        zoomOutButton.setEnabled(b);
        zoomInButton.setEnabled(b);
        zoomMenu.setEnabled(b);
        autoResize.setEnabled(b);
        this.setMaximizable(b);
        this.setIconifiable(b);
        this.setResizable(b);
    }

    public void setAutoZoom(boolean b)
    {
        this.autoResize.setSelected( b );
    }

    public boolean getAutoZoom()
    {
        return this.autoResize.isSelected();
    }

    /////////////////////////////////////
    ////////// Set the frame's image
    /////////////////////////////////////
    public void setFrameImage(Image im, boolean addToUndoStack)
    {
        //clear redo stack
        redo_stack.clear();

        //Add current Image to undo stack
        if( addToUndoStack )
            this.addToUndo();

        //Disable redo menu item
        this.main_frame.menu_bar.disableRedo();

        //set HasChanged (for Save function)
        this.setHasChanged(true);

        //update image
        this.update(im);
    }

    private void update(Image im)
    {
        //update Image with altered image j
        this.image.setImage( im );

        //update ImageFrame zoom
        this.updateZoom();

        //clear selection rectangle
        this.clearSelectionRectangle();

        //update histogram to reflect changes
        main_frame.getFrameManager().updateHistogramFrame(this);
    }

    ///////////////////////////////////////
    ///////////// REVERT IMAGE
    ///////////////////////////////////////
    public void revertImage()
    {
        this.undo_stack.clear();
        this.redo_stack.clear();

        main_frame.menu_bar.disableUndo();
        main_frame.menu_bar.disableRedo();

        this.setHasChanged(false);
        this.update( this.revert_image );
    }

    ///////////////////////
    /// UNDO
    ///////////////////////
    public void undo()
    {
        //compress redo stack (remove 1st element) if stack is full
        int numUndo = main_frame.getJIPTsettings().getMaxUndoSize();
        if( (numUndo!=JIPTsettings.UNLIMITED_UNDO) && (redo_stack.size() == numUndo) )
            redo_stack.remove(0);

        //add current image to redo stack
        redo_stack.add( image.getImage() );

        //enable redo menu item
        main_frame.menu_bar.enableRedo();

        //get and remove previous image from undo stack
        Image img = (Image)undo_stack.remove( undo_stack.size()-1 );

        //if undo stack is empty, disable undo menu item
        if( undo_stack.isEmpty() )
            main_frame.menu_bar.disableUndo();

        //update image
        this.update( img );
    }

    ////////////////////
    /// addToUndo
    ////////////////////
    private void addToUndo()
    {
        int numUndo = main_frame.getJIPTsettings().getMaxUndoSize();
        if( numUndo > 0 )
        {
            //if undo stack is full, remove first element
            if( undo_stack.size() == numUndo )
                undo_stack.remove(0);

            //add current image to undo stack
            undo_stack.add( image.getImage() );

            //enable Undo menu item
            main_frame.menu_bar.enableUndo();
        }

    }

    ////////////////////
    /// addToUndo (protected)
    ////////////////////
    public void addToUndo(ToolManager tool_manager)
    {
        addToUndo();
    }

    ///////////////////////
    /// REDO
    ///////////////////////
    public void redo()
    {
        //if undo stack is full, remove first element
        int numUndo = main_frame.getJIPTsettings().getMaxUndoSize();
        if( (numUndo!=JIPTsettings.UNLIMITED_UNDO) && (undo_stack.size()==numUndo) )
            undo_stack.remove(0);

        //add current image to stack
        undo_stack.add( image.getImage() );

        //get and remove previous image from redo stack
        Image img = (Image)redo_stack.remove( redo_stack.size()-1 );

        //make undo action available
        main_frame.menu_bar.enableUndo();

        //if redo stack is empty, disable redo menu item
        if( redo_stack.isEmpty() )
            main_frame.menu_bar.disableRedo();

        //update image
        this.update( img );
    }

    ///////////////////////
    /// clearSelectionRectangle
    ///////////////////////
    public void clearSelectionRectangle()
    {
        this.select_area.eraseRectangle();
    }

    ///////////////////////
    /// removePrematureImagePush
    ///////////////////////
    public void removePrematureUndoImagePush(ImagePreviewFrame ipf)
    {
        // This is called when a PreviewFrame, which already pushed its original
        //  image onto the undo stack, gets its Cancel button pushed.
        //  without this, an extra copy of the current image is on the undo stack
        //  and this causes problems with the undo functionality.
        //
        // This procedure simply removes that extra image from the undo stack
        //
        // ImagePreviewFrame is required as a parameter to ensure that this procedure
        //  does not get called by unauthorized functions (only ImagePreviewFrames)

        if( !undo_stack.isEmpty() )
            this.undo_stack.remove( undo_stack.size()-1 );

        //if undo stack is empty, disable undo menu item
        if( undo_stack.isEmpty() )
            main_frame.menu_bar.disableUndo();
    }

    public boolean isUndoEnabled()
    {
       return !this.undo_stack.isEmpty();
    }

    public boolean isRedoEnabled()
    {
       return !this.redo_stack.isEmpty();
    }

    public void setXYPosition(int x, int y)
    {
        if( (x == -1) || (y == -1) )
            this.xyPos.setText("");
        else this.xyPos.setText( "(" + x + "," + y + ")" );
    }

    public void setDragPosition(int x, int y)
    {
        if( (x == -1) || (y == -1) )
            this.xyDragPos.setText("");
        else this.xyDragPos.setText( " - (" + x + "," + y + ")" );
    }

    public void doPaste( Image img, boolean addingText )
    {
        select_area.paste( img, addingText );
    }

    public void enablePasteAction(boolean b)
    {
        rightPasteMenuItem.setEnabled( b );
    }

    public void enableCutCopyActions(boolean b)
    {
        rightCutMenuItem.setEnabled( b );
        rightCopyMenuItem.setEnabled( b );
        rightClearMenuItem.setEnabled( b );
        rightCropMenuItem.setEnabled( b );
        rightPasteNewMenuItem.setEnabled( b );
    }

    private JPopupMenu createPopupMenu()
    {
        /////////////////////////////////////////////////////BEGIN POPUP MENU
        //Create the popup menu.
        JPopupMenu m = new JPopupMenu();

        /// Edit Menu MenuItems
        rightCutMenuItem = new JMenuItem("Cut", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "cut.gif") );
        rightCopyMenuItem = new JMenuItem("Copy", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "copy.gif") );
        rightPasteMenuItem = new JMenuItem("Paste", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "paste.gif") );
        rightPasteNewMenuItem = new JMenuItem("Paste selection as new image", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "pasteNew.gif") );
        rightClearMenuItem = new JMenuItem("Clear", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "clear.gif") );
        rightSelectAllMenuItem = new JMenuItem("Select All", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "blank.gif") );
        rightCropMenuItem = new JMenuItem("Crop", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "crop.gif") );
        rightCloseMenuItem = new JMenuItem("Close image", new ImageIcon(JIPTsettings.GRAPHICS_PATH + "close.gif") );

        //Assign Edit Menu tooltips
        rightCutMenuItem.setToolTipText("Cuts selection to the clipboard");
        rightCopyMenuItem.setToolTipText("Copies selection to the clipboard");
        rightPasteMenuItem.setToolTipText("Pastes from the clipboard");
        rightPasteNewMenuItem.setToolTipText("Pastes from the clipboard as a new image");
        rightClearMenuItem.setToolTipText("Deletes selected area");
        rightSelectAllMenuItem.setToolTipText("Selects everything in the active window");
        rightCropMenuItem.setToolTipText("Crops the image to the selection");
        rightCloseMenuItem.setToolTipText("Closes this image");

        rightCutMenuItem.addActionListener(this);
        rightCopyMenuItem.addActionListener(this);
        rightPasteMenuItem.addActionListener(this);
        rightPasteNewMenuItem.addActionListener(this);
        rightClearMenuItem.addActionListener(this);
        rightSelectAllMenuItem.addActionListener(this);
        rightCropMenuItem.addActionListener(this);
        rightCloseMenuItem.addActionListener(this);

        m.add(rightCutMenuItem);
        m.add(rightCopyMenuItem);
        m.add(rightPasteMenuItem);
        m.add(rightPasteNewMenuItem);
        m.add(rightClearMenuItem);
        m.add(rightSelectAllMenuItem);
        m.add(rightCropMenuItem);
        m.addSeparator();
        m.add(rightCloseMenuItem);

        return m;
    }    ///////////////////////////////////////////////////////END POPUP MENU


//////////////////////////////////////////////////BEGIN
    class PopupListener extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            maybeShowPopup(e);
            clearSelectionRectangle();
        }
        public void mouseEntered(MouseEvent e)
        {
        }
        public void mouseExited(MouseEvent e)
        {
        }
        public void mouseDragged(MouseEvent e)
        {
        }
        public void mouseMoved(MouseEvent e)
        {
        }
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
                popup.show( e.getComponent(), e.getX(), e.getY() );
        }
    }
//////////////////////////////////////////////////END
}
