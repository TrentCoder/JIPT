package com.jipt.fileformats;

// Victor Rego
// PPMWriter, saves P3 and P6 type PPM images as P3
// 11-15-01


import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class PPMWriter implements ImageObserver
{
    public String width;
    public String height;
    public int w;
    public int h;
    public int[] imageArray;
    public Integer[] IRed;
    public Integer[] IGreen;
    public Integer[] IBlue;

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
        IRed     = new Integer[w*h];
        IGreen   = new Integer[w*h];
        IBlue    = new Integer[w*h];

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

        // split pixel data into RGB components
        for(int i=0; i<w*h; i++)
        {
            IRed[i]  = new Integer((imageArray[i]>>16) & 0x000000ff);
            IGreen[i]= new Integer((imageArray[i]>>8) & 0x000000ff);
            IBlue[i] = new Integer(imageArray[i] & 0x000000ff);
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
            writer.write("P3");
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
                writer.write(IRed[i].toString());
                writer.write(" ");
                writer.write(IGreen[i].toString());
                writer.write(" ");
                writer.write(IBlue[i].toString());
                count++;
                if(count == 5)
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