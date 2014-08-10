package com.jipt.fileformats;

// Victor Rego
// GIFWriter.java will save an Image as a GIF
// 12-06-01

import java.awt.AWTException;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.jipt.ConvertTo8Bit;


public class GIFWriter  implements ImageObserver
{

    public FileOutputStream output = null;


    ////////////////////////////////
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        if((infoflags & ImageObserver.ERROR) != 0)
        {
            // System.out.println("!!!!!ERROR!!!!!");
            return false;
        }
        return true;
    }// end public boolean imageUpdate(Image, int, int, int, int, int)

    ////////////////////////////////
    public boolean save(Image img, String filename)
    {
        File file = new File(filename);
        GIFEncoder gif;
        byte r[][] = new byte[img.getWidth(this)][img.getHeight(this)];
        byte g[][] = new byte[img.getWidth(this)][img.getHeight(this)];
        byte b[][] = new byte[img.getWidth(this)][img.getHeight(this)];

        ConvertTo8Bit convert = new ConvertTo8Bit(img,r,g,b);

        try
        {
            gif = new GIFEncoder(r,g,b);
            output = new FileOutputStream(file);
            gif.Write(output);
        }
        catch(IOException ex)
        {
            // System.out.println(ex.toString());
            return false;
        }
         catch(AWTException ax)
        {
            // System.out.println(ax.toString());
            return false;
        }
        return true;
    }
}