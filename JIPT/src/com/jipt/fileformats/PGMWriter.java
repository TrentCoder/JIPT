package com.jipt.fileformats;

// Victor Rego
// PGMWriter, saves image as PGM P2
// 11-17-01

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PGMWriter implements ImageObserver
{
    public String width;
    public String height;
    public int w;
    public int h;
    public int[] imageArray;
    public Integer[] IGray;

    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        if((infoflags & ImageObserver.ERROR) != 0)
        {
            // System.out.println("!!!!!ERROR!!!!!");
            return false;
        }
        return true;
    }

    //
    public void encode(Image img)
    {
        imageArray = new int[w*h];
        IGray     = new Integer[w*h];

        try
        {
            // get pixel data from image and store in imageArray
            PixelGrabber pg = new PixelGrabber(img,0,0,w,h,imageArray,0,w);
            pg.grabPixels();
        }
        catch(InterruptedException e)
        {
            // System.out.println(e.toString());
        }

        //
        for(int i=0; i<w*h; i++)
        {
            IGray[i] = new Integer(imageArray[i] & 0x000000ff);
        }
    }

    public boolean save(Image img, String filename)
    {
        Integer wd = new Integer(img.getWidth(this));
        w = wd.intValue();
        Integer ht = new Integer(img.getHeight(this));
        h = ht.intValue();
        width = wd.toString();
        height = ht.toString();
        File file = new File(filename);
        FileOutputStream output = null;
        BufferedWriter writer = null;
        int count = 0;

        encode(img);

        try
        {
            output = new FileOutputStream(file);
            writer = new BufferedWriter(new OutputStreamWriter(output));

            // write image header info
            writer.write("P2");
            writer.newLine();
            writer.write("# IMAGE SAVED BY JIPT");
            writer.newLine();
            writer.write(width);
            writer.write(" ");
            writer.write(height);
            writer.newLine();
            writer.write("255");
            writer.newLine();

            // write and format image data
            for(int i=0; i<w*h; i++)
            {
                writer.write(IGray[i].toString());
                writer.write(" ");
                count++;
                if(count == 10)
                {
                    writer.newLine();
                    count = 0;
                }
                else
                    writer.write(" ");
            }
            writer.close();

        }
        catch(IOException ex)
        {
            // System.out.println(ex.toString());
            return false;
        }
        return true;
    }

}
