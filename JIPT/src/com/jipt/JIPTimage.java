package com.jipt;

import java.awt.Image;

public class JIPTimage
{
    private Image image = null;
    private String filename = null;
    private boolean grayscale = false;
    private int bpp;

    public JIPTimage()
    {
        determineBpp();
    }

    public JIPTimage(Image i)
    {
        this.image = i;
        determineBpp();
    }

    public JIPTimage(Image i, String f)
    {
        this.image = i;
        this.filename = f;
        determineBpp();
    }

    public JIPTimage(Image i, String f, boolean g, int d)
    {
        this.image = i;
        this.filename = f;
        this.grayscale = g;
        this.bpp = d;
    }

    public JIPTimage( String f )
    {
        this.filename = f;
        this.image = FileManager.openImage( f );
        determineBpp();
    }

    private void determineBpp()
    {
        bpp = 24;
        if( this.filename != null )
        {
            String t = FileManager.parseFileName(this.filename);
            t = t.toLowerCase();
            if( t.endsWith("gif") )
                bpp = 8;
        }
    }

    public Image getImage()
    {
        return this.image;
    }

    public void setImage(Image i)
    {
        this.image = i;
    }

    public boolean getGrayscale()
    {
        return this.grayscale;
    }

    public void setGrayscale(boolean b)
    {
        this.grayscale = b;
    }

    public String getFilename()
    {
        return this.filename;
    }

    public void setFilename( String f )
    {
        this.filename = f;
    }

    public int getBpp()
    {
        return this.bpp;
    }

    public void setBpp(int d)
    {
        this.bpp = d;
    }

}