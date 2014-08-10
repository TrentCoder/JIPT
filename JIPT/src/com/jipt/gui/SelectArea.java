package com.jipt.gui;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.MatteBorder;

import com.jipt.EditManager;
import com.jipt.ToolManager;

public class SelectArea extends JLayeredPane
{
	private static final int BORDERSIZE = 2;
	private static final Color BORDER_COLOR = Color.red;

    private static MainFrame main_frame = null;
    private ImageFrame img_frame = null;
    MultiSelectListener msl = null;
    public JLabel image = null;
	private Graphics g = null;

	//main image variabled
	private Point img_pos = null;           // (x,y) location of main image
	private int img_width = 0;              // width of main image
	private int img_height = 0;             // height of main image

	//booleans
	private boolean doPaste = false;        //specifies select/drag or paste operation
    private boolean doAddText = false;      //specifies if adding text to image
	private boolean mouseDown = false;      //specifies if [left] mouse button is down
    private boolean menuShowing = false;    //specifies if the right-click menu is showing
    private boolean justPasted = false;     //specifies if the detached image was just thatched

	//selection box variables
	private Point begin = null;             //top-left point of selection rectangle
	private Point end = null;               //bottom-right point of selection rectangle
	private Rectangle rectangle = null;     //selection rectangle

    //drag image variables
    private boolean isDragging = false;     //signals if detached image is being dragged
    private boolean isDetached = false;     //signals if a selection area is detached
	private Point clickedAt = null;         //denotes position on detached image mouse was clicked prior to drag
    private Point droppedAt = null;         //denotes position on main image that the detached image was dropped
    private JLabel detachedImage = new JLabel();    //contained for detached image (from selection area)

    //Mouse cursors
    private int dragCursor = Cursor.MOVE_CURSOR;
    private int selectCursor = Cursor.CROSSHAIR_CURSOR;
    private int defaultCursor = Cursor.DEFAULT_CURSOR;

    public SelectArea( ImageFrame im_f, MainFrame mf, JLabel img )
    {
        super();
        g = this.getGraphics();
        image = img;
        main_frame = mf;
        img_frame = im_f;
        msl = new MultiSelectListener(this);

		//remove layout manager
        this.setLayout( null );

        addMouseMotionListener(msl);
        addMouseListener(msl);

        //add main image
        this.add(image);

        image.setVisible(true);
        image.setAlignmentX((float)0.5);
        image.setAlignmentY((float)0.5);

		//Set image boundaries
		setBoundaries();
    }

	public void setBoundaries()
	{
        //update scroll bar
        this.setPreferredSize(new Dimension (image.getWidth(), image.getHeight()) );
        this.revalidate();

		//determine x, y, width, and height of Main Image
		img_pos = new Point( 0,0 );//img_frame.getImageX(), img_frame.getImageY() );
		img_width = img_frame.getImageWidth();
		img_height = img_frame.getImageHeight();

        int cx = (img_frame.getWidth() - img_width - ImageFrame.BORDER_WIDTH) / 2;
        int cy = (img_frame.getHeight() - img_height - ImageFrame.BORDER_HEIGHT) / 2;
       // System.out.println("CX= " + cx);
       // System.out.println("CY= " + cy);
//        image.setLocation(cx, cy);
	}

    public Point enforceBoundaries(int x, int y)
    {
        //x is between 0 and img_width
        if( x > ( img_pos.x + img_width ) )
            x = img_width;
        else if( x < img_pos.x )
                x = 0;
            else x -= img_pos.x;

        //y is between 0 and img_height
        if( y > ( img_pos.y + img_height ) )
            y = img_height;
        else if( y < img_pos.y )
            y = 0;
            else y -= img_pos.y;

        return new Point( x, y );
    }

	public Rectangle getRectangle()
	{
        int x = begin.x;
        int y = begin.y;
        int w = end.x - begin.x;
        int h = end.y - begin.y;

        double ratio = img_frame.getZoomRatio() / 100;

        x *= ratio;
        y *= ratio;
        w *= ratio;
        h *= ratio;

        return new Rectangle( x, y, w, h );
	}

    public void paste(Image i, boolean addingText)
    {
        doAddText = addingText;
        doPaste = true;
        rectangle = null;
        msl.paste(i);
    }

    public void setMenuShowing(boolean b)
    {
        menuShowing = b;
    }

    public boolean getMenuShowing()
    {
        return menuShowing;
    }

    public void eraseRectangle()
    {
        //initialize drawing routines
        g = getGraphics();
        g.setXORMode( getBackground() );
        g.setColor(Color.blue);

        if( rectangle != null )
        {
            int w = (int)rectangle.getWidth();
            int h = (int)rectangle.getHeight();

            // erase the old rectange
            if( (w>0) && (h>0) )
                g.drawRect( (int)(rectangle.getX()+img_pos.getX()),
                            (int)(rectangle.getY()+img_pos.getY()),
                            w,
                            h );
        }

		//clear rectangle
		rectangle = null;

        //hide selection menu actions
        this.main_frame.menu_bar.disableCutCopyActions();
        img_frame.enableCutCopyActions(false);
    }

   public void setRectangle( int x, int y, int w, int h )
   {
        //initialize drawing routines
        g = getGraphics();
        g.setXORMode(getBackground());
		g.setColor(Color.blue);

        //set begin/end selection pts
        begin = new Point(x-img_pos.x, y-img_pos.y);
        end = new Point (begin.x+w, begin.y+h );

		// erase the old rectange
		if (rectangle != null)
			g.drawRect(rectangle.x+img_pos.x, rectangle.y+img_pos.y, rectangle.width, rectangle.height);

		// Track the last rectangle
        this.rectangle = new Rectangle( begin.x, begin.y, w, h );

        //show selection menu actions
        this.main_frame.menu_bar.enableCutCopyActions();
        img_frame.enableCutCopyActions(true);
   }

   public void drawRectangle(int mouseX, int mouseY)
   {
        //initialize drawing routines
        g = getGraphics();
		g.setXORMode(getBackground());
		g.setColor(Color.blue);

		Rectangle rect = new Rectangle();
        int x = begin.x + img_pos.x;
        int y = begin.y + img_pos.y;

        if( mouseX > x )
        {
            rect.x = x;
            rect.width = mouseX - x;
        }
        else
        {
            rect.x = mouseX;
            rect.width = x - mouseX;
        }

        if( mouseY > y )
        {
            rect.y = y;
            rect.height = mouseY - y;
        }
        else
        {
            rect.y = mouseY;
            rect.height = y - mouseY;
        }

		// erase the old rectange
		if (rectangle != null)
			g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

		// draw new rectangle
		g.drawRect(rect.x, rect.y, rect.width, rect.height);

		// Track the last rectangle
        rectangle = rect;

        //show selection menu actions
        this.main_frame.menu_bar.enableCutCopyActions();
        img_frame.enableCutCopyActions(true);
   }

   public void clearRectanglePoints(int x, int y)
   {
        rectangle = null;
        begin = new Point(x,y);
        end = new Point(x,y);
   }

   public void clearRectanglePoints()
   {
        rectangle = null;
        begin = new Point(0,0);
        end = new Point(0,0);
   }

   class MultiSelectListener extends MouseMotionAdapter implements MouseListener
   {
        private JLayeredPane owner = null;

        public MultiSelectListener(JLayeredPane jp)
        {
            owner = jp;
        }

        public void mousePressed(MouseEvent ev)
        {
            //ensure we are at actual size
            if( img_frame.getZoomRatio() != 100 )
            {
                img_frame.setXYPosition(-1,-1);
                img_frame.setDragPosition(-1,-1);
                eraseRectangle();
                return;
            }

            //set mouse is now down
            mouseDown = true;

			//get mouse Position
			int x = ev.getX();
			int y = ev.getY();

			//enforce boundaries
			x -= img_pos.x;
			y -= img_pos.y;

            if( isDetached )
            {
                //check if clicked on draggable image
                if( inBounds(ev.getX(), ev.getY() ) )//x, y) )
                {
                    //set clickedAt location
                        clickedAt = new Point( x-detachedImage.getX(), y-detachedImage.getY() );

                    //set draggable true
                    isDragging = true;

                    //clear rectangle
                    eraseRectangle();
                }
                else //thatch image (clicked outside detached image)
                {
                    int thatchX = x - clickedAt.x;
                    int thatchY = y - clickedAt.y;

                    //merge dragged area into main image
                    main_frame.getEditManager().thatch(droppedAt.x, droppedAt.y, doPaste, doAddText);

                    //set detached to false
                    isDetached = false;

                    //signal not to draw new rectangle
                    justPasted = true;

                    //remove draggable jlabel
                    remove(detachedImage);

                    //disable frame controls (until selection is thatched)
                    img_frame.enableFrameControls(true);

                    //enable undo/redo
                    main_frame.menu_bar.enableRedo();
                    main_frame.menu_bar.enableUndo();
                }
            }
            else //selection box:
            {
                //Check for click on selected area
                if( (rectangle != null) && (ev.getModifiers() != ev.BUTTON3_MASK) )
                {
                    if( rectangle.contains( x, y ) ) //Detach image:
                    {
                        //set dragging to true
                        isDragging = true;

                        //set detached to true
                        isDetached = true;

                        //not paste operation; set doPaste to false
                        doPaste = false;

                        //set clickedAt location
                        clickedAt = new Point( x-begin.x, y-begin.y );

                        //change mouse cursor
                        owner.setCursor( new Cursor(dragCursor) );

                        //get EditManager
                        EditManager em = main_frame.getEditManager();

                        //create new image
                        // if SHIFT is down, area will be COPIED
                        // else area will be CUT (default)
                        Image i = em.detach( !ev.isShiftDown() );

/** DISABLED IN THIS RELEASE **/
/*                        //get zoom ratio
                        double zoom = img_frame.getZoomRatio() / 100;
                        int iw = (int)(i.getWidth(main_frame) * img_frame.getZoomRatio() / 100);

                        //scale image
                        i = i.getScaledInstance(iw, -1, Image.SCALE_FAST);
*/
                        //create draggable image (jlabel)
                        detachedImage = new JLabel( new ImageIcon( i ));

                        //set size
                        detachedImage.setSize( em.getDragboardImage().getWidth(main_frame),
                                                em.getDragboardImage().getHeight(main_frame) );

                        //set location
                        detachedImage.setLocation( ev.getX()-clickedAt.x, ev.getY()-clickedAt.y );

                        //add detachedImage to panel
                        add( detachedImage, JLayeredPane.DRAG_LAYER );

                        clearRectanglePoints(x,y);

                        //disable frame controls (until selection is thatched)
                        img_frame.enableFrameControls(false);

                        //disable undo
                        main_frame.menu_bar.disableRedo();
                        main_frame.menu_bar.disableUndo();
                    }
                    else
                    {
                        //set begin point
                        begin = new Point( x,y );

                        //clear selection rectangle
                        eraseRectangle();
                    }
                }
                else //clear rectangle
                {
                    //if right mouse button clicked
                    if( ev.getModifiers() == ev.BUTTON3_MASK )
                    {
                        //menu is showing
                        setMenuShowing(true);

                        //display right-click menu
                        img_frame.popupListener.mousePressed(ev);
                    }
                    else
                    {
                        //set begin point
                        begin = new Point( x, y );

                        //clear selection rectangle
                        eraseRectangle();
                    }
                }
            }
        }//end mousePressed

        public void mouseClicked(MouseEvent ev)
        {
        }

        public void mouseReleased(MouseEvent ev)
        {
            //ensure we are at actual size
            if( img_frame.getZoomRatio() != 100 )
            {
                img_frame.setXYPosition(-1,-1);
                img_frame.setDragPosition(-1,-1);
                eraseRectangle();
                return;
            }

			//set mouse down false
			mouseDown = false;

            if( justPasted )
            {
                justPasted = false;
                return;
            }

            //get mouse position
            int x = ev.getX();
            int y = ev.getY();

			//if image is being dragged
			if( isDragging )
			{
				//set border (default color, since isDragging = false)
				setBorder(true);

                //set dropped At values
                droppedAt = new Point( x-clickedAt.x-img_pos.x, y-clickedAt.y-img_pos.y );

                //set location of detached Image
                detachedImage.setLocation( droppedAt.x, droppedAt.y );

                //clear area
                eraseRectangle();
			}
			else //set draw rectangle
			{
				//enforce boundaries
				x -= img_pos.x;
				y -= img_pos.y;

                if( !getMenuShowing() )
                {
    				//set end point
	    			end = new Point( x, y );

    				//normalize rectangle
	    			normalizeRectangle();
                }
			}

			//set drag mode
			isDragging = false;

			//set mouse cursor
			owner.setCursor( new Cursor(defaultCursor) );

			//call mouseMoved
			mouseMoved(ev);
        }

        public void mouseEntered(MouseEvent ev)
        {
            //set cursor
            owner.setCursor( new Cursor(selectCursor) );

			//Set image boundaries
			setBoundaries();
        }

        public void mouseExited(MouseEvent ev)
        {
            //restore cursor
            owner.setCursor( new Cursor(defaultCursor) );

			//Set image boundaries
			setBoundaries();
        }

        public void mouseMoved(MouseEvent ev)
        {
            //ensure we are at actual size
            if( img_frame.getZoomRatio() != 100 )
            {
                img_frame.setXYPosition(-1,-1);
                img_frame.setDragPosition(-1,-1);
                eraseRectangle();
                return;
            }

            //enforce image boundaries
            Point pt = enforceBoundaries( ev.getX(), ev.getY() );

            //set the (x,y) indicator on the menu bar
            if( mouseDown )
            {
                img_frame.setDragPosition( pt.x, pt.y);
            }
            else
            {
                //set the dragging size text
                img_frame.setXYPosition(pt.x, pt.y);
                img_frame.setDragPosition(-1, -1);
            }

        }

        public void mouseDragged(MouseEvent ev)
        {
            //ensure we are at actual size
            if( img_frame.getZoomRatio() != 100 )
            {
                img_frame.setXYPosition(-1,-1);
                img_frame.setDragPosition(-1,-1);
                eraseRectangle();
                return;
            }

            //cannot draw another rectangle until mouse is released and pressed again
            if( justPasted )
                return;

			//get mouse position
			int x = ev.getX() - img_pos.x;
			int y = ev.getY() - img_pos.y;

			//if not dragging image
			if( !isDragging )
			{
                //enforce image boundaries
                Point pt = enforceBoundaries( ev.getX(), ev.getY() );

				//report size of select box
				img_frame.setDragPosition( pt.x, pt.y );

				//draw Rectangle from (begin.x, begin.y) to current mouse position
				drawRectangle( ev.getX(), ev.getY() );
			}
			else //dragging image
			{
				//remove border
				setBorder(false);

				//move detached image
				detachedImage.setLocation( ev.getX()-clickedAt.x, ev.getY()-clickedAt.y );
			}
        }//end mouseDragged

		private void normalizeRectangle()
		{
			//temp variables
			int x = 0;
			int y = 0;
			int w = 0;
			int h = 0;

            // From upper-left?
            if(begin.x < end.x && begin.y < end.y)
            {
                x = begin.x;
                y = begin.y;
                w = end.x - begin.x;
                h = end.y - begin.y;
            }
            else// From lower-left?
            if(begin.x < end.x && begin.y > end.y)
            {
                x = begin.x;
                y = end.y;
                w = end.x - begin.x;
                h = begin.y - end.y;
            }
            else// From upper-right?
            if(begin.x > end.x && begin.y < end.y)
            {
                x = end.x;
                y = begin.y;
                w = begin.x - end.x;
                h = end.y - begin.y;
            }
            else// From lower-right?
            if(begin.x > end.x && begin.y > end.y)
            {
                x = end.x;
                y = end.y;
                w = begin.x - end.x;
                h = begin.y - end.y;
            }

			//set changes to rectangle
            begin.x = x;
            begin.y = y;
            end.x = begin.x + w;
            end.y = begin.y + h;
            img_width = w;
            img_height = h;

			rectangle = new Rectangle( x, y, w, h );
		}

        public void paste(Image img)
        {
            //image to drag is detached
            isDetached = true;

            //reset clicked at locations
            clickedAt = new Point(0,0);
            droppedAt = new Point(0,0);

            //create draggable JLabel
            detachedImage = new JLabel ( new ImageIcon (img) );

            //get image dimensions
            Dimension img_dim = ToolManager.getImageDimensions(img);

            //get image location and dimensions
            int x = 0;
            int y = 0;
            int w = (int)img_dim.getWidth();
            int h = (int)img_dim.getHeight();

            //reset beginX/Y locations
            begin = new Point(0,0);
			end = new Point( w, h );

			//set border
			setBorder(true);

            //add deatchedImage to Panel
            add(detachedImage, JLayeredPane.DRAG_LAYER);

            //set detachedImage location to top left corner of main image
            detachedImage.setLocation( 0, 0 );

            //set size (otherwise image is not visible while dragging)
            detachedImage.setSize( img_dim );

            //disable frame controls (until selection is thatched)
            img_frame.enableFrameControls(false);

            //disable undo
            main_frame.menu_bar.disableRedo();
            main_frame.menu_bar.disableUndo();
        }

        private void setBorder(boolean enabled)
        {
            if( enabled )
            {
                //get image location and dimensions
                int x = detachedImage.getX();
                int y = detachedImage.getY();
                int w = detachedImage.getWidth();
                int h = detachedImage.getHeight();

				//set border color
                MatteBorder border = new MatteBorder(BORDERSIZE,BORDERSIZE,BORDERSIZE,BORDERSIZE, BORDER_COLOR);
                border.paintBorder(detachedImage, g, x, y, w, h);
                detachedImage.setBorder( border );
            }
            else
            {
				//remove border
                detachedImage.setBorder(null);
            }
        }

        ////////////////////
        //// In Bounds? ////
        ////////////////////
        private boolean inBounds( int mouseX, int mouseY )
        {       ///this will tell you if you clicked on an image

			int img_x = detachedImage.getX();
			int img_y = detachedImage.getY();
			int img_w = detachedImage.getWidth();
			int img_h = detachedImage.getHeight();

            //test if click is on image
            boolean inBounds = true;
            if( mouseX < img_x )
                inBounds = false;
            if( mouseY < img_y )
                inBounds = false;
            if( mouseX > (img_x + img_w) )
                inBounds = false;
            if( mouseY > (img_y + img_h) )
                inBounds = false;

            //return result
            return inBounds;
        }//end inBounds

    }//end class
}//end drawArea
