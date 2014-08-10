package com.jipt.fileformats;

// JPEGWriter.java writes Images object as jpg file
// 12-01-01

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


public class JPEGWriter implements ImageObserver
{
    public FileOutputStream output = null;
    private int width = 0;
    private int height = 0;
    private float compression = 1f;

    //private JPEGEncodeParam param = null;

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
    public BufferedImage convertToBufferedImage(Image img, int wd, int ht, int type)
    {
        BufferedImage b = new BufferedImage(wd,ht,type);
        Graphics2D g2d = b.createGraphics();
        g2d.drawImage(img,0,0,wd,ht,null);
        return b;
    }// end public BufferedImage convertToBufferedImage()


    ////////////////////////////////
    public boolean save(Image img, String filename)
    {
        File file = new File(filename);
        Integer wd = new Integer(img.getWidth(this));
        width = wd.intValue();
        Integer ht = new Integer(img.getHeight(this));
        height = ht.intValue();
        int type = BufferedImage.TYPE_INT_RGB;

        BufferedImage bi = new BufferedImage(width,height,type);
        bi = convertToBufferedImage(img,width,height,type);

        try
        {
            output = new FileOutputStream(file);
            ImageOutputStream  ios =  ImageIO.createImageOutputStream(output);
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
            ImageWriter writer = iter.next();
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(compression);
            writer.setOutput(ios);
            writer.write(null, new IIOImage(bi,null,null),iwp);
            writer.dispose();
            output.close();
            
        }
        catch(IOException ex)
        {
            // System.out.println(ex.toString());
            return false;
        }
        return true;

    }// end public boolean save()

    public void setQuality( float compression )
    {
        this.compression = compression;
    }

}// end public class JPEGWriter