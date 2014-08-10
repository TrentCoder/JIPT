package com.jipt;

// Victor Rego
// ConvertTo8Bit.java will convert an Image to 8bpp
// 12-06-01

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;

public class ConvertTo8Bit implements ImageObserver
{
    public ConvertTo8Bit(Image img, byte r[][], byte g[][], byte b[][])
    {

        int width = img.getWidth(this);
        int height = img.getHeight(this);
        int imageArray[] = new int[width*height];
        try
        {
            // get pixel data from image and store in imageArray
            PixelGrabber pg = new PixelGrabber(img,0,0,width,height,imageArray,0,width);
            pg.grabPixels();
        }
        catch(InterruptedException e)
        {
            // System.out.println(e.toString());
        }
        int i=0;
        for(int y=0; y<height; y++)
            for(int x=0; x<width; x++)
            {
                r[x][y] = (byte)((imageArray[i] >> 16 ) &0x000000ff);
                g[x][y] = (byte)((imageArray[i] >> 8) &0x000000ff);
                b[x][y] = (byte)( imageArray[i] &0x000000ff);
                i++;
            }
    }// end ConvertTo8Bit(Image img, byte r[][], byte g[][], b[][])


    ////////////////////////////////
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        if((infoflags & ImageObserver.ERROR) != 0)
        {
            return false;
        }
        return true;
    }// end public boolean imageUpdate(Image, int, int, int, int, int)

}