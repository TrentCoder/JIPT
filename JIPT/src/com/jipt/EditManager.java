package com.jipt;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import com.jipt.gui.ImageFrame;
import com.jipt.gui.MainFrame;

public class EditManager 
{
    private static MainFrame main_frame = null;
    private static Image clipboard_image = null;
    private static Image dragboard_image = null;

    public EditManager(MainFrame mf)
    {
        main_frame = mf;
    }

     // COPY OR CUT SELECTED AREA
    public boolean doCutCopyCrop( boolean clearArea, boolean doCrop, boolean useClipboard )
    {
        ImageFrame img_frame = main_frame.getFrameManager().getSelectedFrame();
        Rectangle rect = getSelectedArea(img_frame);
        if( rect == null )
        return false;

        int x = (int)rect.getX();
        int y = (int)rect.getY();
        int width = (int)rect.getWidth();
        int height = (int)rect.getHeight();

        //get image from selected area
        Image img = img_frame.getJIPTImage().getImage();
        int imageSize = width * height;
        int newImageData[] = new int[imageSize];

        //use PixelGrabbery to grab selected area
        PixelGrabber pg = new PixelGrabber(img, x, y, width, height, true);
        try
        {
            pg.grabPixels();
        }
        catch(Exception e)
        {
            // System.out.println(e.toString());
        }
        newImageData = (int []) pg.getPixels();

        //create image from imageData
        Image newImage = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width,
                height,newImageData,0,width));

        if( doCrop )
        {
            //Don't overwrite clipboard for crop
            img_frame.setFrameImage( newImage, true );
        }
        else
        {
            //store selected image to clipboard or dragboard
            if( useClipboard )
            {
                clipboard_image = newImage;
                main_frame.menu_bar.pasteNewEditMenuItem.setEnabled(true);
                main_frame.menu_bar.pasteEditMenuItem.setEnabled(true);
                img_frame.enablePasteAction(true);
            }
            else dragboard_image = newImage;

            if( clearArea ) //cut selected area and add image to undo stack
                clearArea(img_frame, x, y, width, height);
            //else just add image to undo stack
            else img_frame.setFrameImage( img, true );

        }
        img_frame.clearSelectionRectangle();
        return true;
    }//end doCutCopyCrop()

    public void clearArea(ImageFrame img_frame, int x, int y, int width, int height)
    {
        //clear this area from main picture
        Image img = img_frame.getJIPTImage().getImage();    //get main image
        int totalWidth = img.getWidth(main_frame);      //get main picture width
        int totalHeight = img.getHeight(main_frame);    //get main picture height
        int originImageData[] = new int[ totalWidth * totalHeight ];    //make image array for main picture

        PixelGrabber pg = new PixelGrabber(img, 0, 0, -1, -1, true);     //get main picture array
        try
        {
            pg.grabPixels();
        }
        catch(Exception e)
        {
            // System.out.println(e.toString());
        }
        originImageData = (int []) pg.getPixels();

        //get clearPixelColor from jipt_settings
        int clearPixel = main_frame.getJIPTsettings().getClearPixelColor();

        //traverse image array and clear out selected area (x,y, x+width, y+height)
        int pos = 0;
        for(int j=0; j<totalHeight; j++ )
            for( int i=0; i<totalWidth; i++ )
            {
                if( ( (i>=x) && (i<(x+width)) ) && ( (j>=y) && (j<(y+height)) ) )
                {
                    originImageData[pos] = clearPixel;
                }
                pos++;
            }

        //create image from imageData
        img = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(totalWidth,
                totalHeight,originImageData,0,totalWidth));

        //apply new image
        img_frame.setFrameImage( img, true );
    }

    public void doSelectAll()
    {
        ImageFrame img_frame = main_frame.getFrameManager().getSelectedFrame();
        int x = img_frame.getImageX();
        int y = img_frame.getImageY();
        int w = img_frame.getImageWidth();
        int h = img_frame.getImageHeight();
        img_frame.setSelectedArea(x, y, w, h);
    }

    public void clearSelectedArea()
    {
        ImageFrame img_frame = main_frame.getFrameManager().getSelectedFrame();
        Rectangle r = getSelectedArea( img_frame );
        if( r != null )
        clearArea(img_frame, (int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight() );
    }

    public Rectangle getSelectedArea(ImageFrame img_frame)
    {
        Rectangle rect = img_frame.getSelectRect();
        //Rectangle bounds = new Rectangle( img_frame.getImageX(), img_frame.getImageY(), img_frame.getImageWidth(), img_frame.getImageHeight() );
        //this.simplifyRectangle( rect, bounds );
        return rect;
    }

    public void doPaste(boolean useClipboard, boolean addingText)
    {
        if( useClipboard )
        {
            if( this.clipboard_image != null )
            {
                ImageFrame img_frame = main_frame.getFrameManager().getSelectedFrame();
                img_frame.doPaste( this.clipboard_image, addingText );
            }
        }
        else //use drag board
        {
            if( this.dragboard_image != null )
            {
                ImageFrame img_frame = main_frame.getFrameManager().getSelectedFrame();
                img_frame.doPaste( this.dragboard_image, addingText );
            }
        }
    }

    public Image getClipboardImage()
    {
        return this.clipboard_image;
    }

    public Image getDragboardImage()
    {
        return this.dragboard_image;
    }

    public void setDragboardImage(Image im)
    {
        if( im != null )
            this.dragboard_image = im;
    }

    public Image detach(boolean clearArea)
    {
        this.doCutCopyCrop(clearArea, false, false);
        Image im = this.getDragboardImage();
        return im;
    }

    //////////////////
    /// THATCH
    //////////////////
    //  This image "pastes" the current image on the clipboard into the
    //   current frame at location (x,y)
    public void thatch(int thatchAtX, int thatchAtY, boolean doPaste, boolean doAddText)
    {
        //get current frame
        ImageFrame img_frame = this.main_frame.getFrameManager().getSelectedFrame();

        //get detachedImage from clipboard
        Image detachedImage = null;
        if( doPaste && !doAddText )
            detachedImage = this.getClipboardImage();
        else detachedImage = this.getDragboardImage();

        //get detached Image width and height
        int detachedWidth = detachedImage.getWidth(main_frame);
        int detachedHeight = detachedImage.getHeight(main_frame);

        //create detachedImageData array
        int detachedImageData[] = null;

        //adjust x,y, width and height to fit
        Rectangle in = new Rectangle( thatchAtX, thatchAtY, detachedWidth, detachedHeight );
        Rectangle bounds = new Rectangle( 0, 0, img_frame.getImageWidth(), img_frame.getImageHeight() );
        this.clipingRectangle( in, bounds );

        int xOffset = (int)in.getX();
        if( thatchAtX < 0 )
            thatchAtX = 0;
        int yOffset = (int)in.getY();
        if( thatchAtY < 0 )
            thatchAtY = 0;
        detachedWidth = (int)in.getWidth();
        detachedHeight = (int)in.getHeight();

        //load in full image
        Image mainImage = img_frame.getJIPTImage().getImage();

        int mainWidth = mainImage.getWidth(main_frame);      //get main picture width
        int mainHeight = mainImage.getHeight(main_frame);    //get main picture height
        int mainImageData[] = new int[ mainWidth * mainHeight ];    //make image array for main picture

        //get mainImage array
        PixelGrabber pg = new PixelGrabber(mainImage, 0, 0, -1, -1, true);
        try
        {
            pg.grabPixels();
        }
        catch(Exception e)
        {
            // System.out.println(e.toString());
        }
        mainImageData = (int []) pg.getPixels();

        //get detachedImage Array
        pg = new PixelGrabber(detachedImage, xOffset, yOffset, detachedWidth, detachedHeight, true);
        try
        {
            pg.grabPixels();
        }
        catch(Exception e)
        {
            // System.out.println(e.toString());
        }
        detachedImageData = new int[ detachedWidth * detachedHeight ];
        detachedImageData = (int []) pg.getPixels();

        //traverse image array and paste in selected area (x,y, x+width, y+height)
        int originPos = 0;
        int detachedPos = 0;
        for(int j=0; j<mainHeight; j++ )
            for( int i=0; i<mainWidth; i++ )
            {
                if( (i>=thatchAtX) && (i<(thatchAtX+detachedWidth)) )
                if( (j>=thatchAtY) && (j<(thatchAtY+detachedHeight)) )
                {
                    if( detachedImageData[detachedPos] != Color.TRANSLUCENT )
                        mainImageData[originPos] = detachedImageData[detachedPos];
                    detachedPos++;
                }
                originPos++;
            }

        //create image from imageData
        mainImage = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(mainWidth,
                mainHeight,mainImageData,0,mainWidth));

        //apply new image
        img_frame.setFrameImage( mainImage, doPaste );
    }

    public void clipingRectangle(Rectangle in, Rectangle bounds)
    {
        int pointX = (int)in.getX();
        int pointY = (int)in.getY();
        int x = (int)in.getX();         // starting x of selected region
        int y = (int)in.getY();         // starting y of selected region
        int w = (int)in.getWidth();     // width of selected region
        int h = (int)in.getHeight();    // height of selection region

        int MAX_X = (int)bounds.getX();         // 0 (relative)
        int MAX_Y = (int)bounds.getY();         // 0 (relative)
        int MAX_W = (int)bounds.getWidth();     // img_w
        int MAX_H = (int)bounds.getHeight();    // img_h


        //adjust X boundaries
        if( (pointX + w) > MAX_W )
        {
            x = 0;
            w = MAX_W - pointX;
        }
        else
        if( pointX < MAX_X )
        {
            x = -pointX;
            w = pointX + w;
        }
        else
        {
            x = 0;
            w = w;
        }

        //adjust Y boundaries
        if( (pointY + h) > MAX_H )
        {
            y = 0;
            h = MAX_H - pointY;
        }
        else
        if( pointY < MAX_Y )
        {
            y = -pointY;
            h = pointY + h;
        }
        else
        {
            y = 0;
            h = h;
        }

        in.setRect( new Rectangle(x, y, w, h));
    }
}