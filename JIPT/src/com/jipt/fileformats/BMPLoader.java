package com.jipt.fileformats;

/*
  Victor Rego
  10-20-01

  This class loads a bmp file passed to it by FileManger
  Converts it from little to big endian
  Returns an Image object

*/

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.FileInputStream;
import java.io.IOException;

public class BMPLoader
{

  public BMPLoader(){}

  // build an int from a byte array - convert little to big endian
  public static int constructInt(byte[] in,int offset)
  {
    int ret =          ((int)in[offset + 3] & 0xff);
    ret = (ret << 8) | ((int)in[offset + 2] & 0xff);
    ret = (ret << 8) | ((int)in[offset + 1] & 0xff);
    ret = (ret << 8) | ((int)in[offset + 0] & 0xff);
    return(ret);
  }
  // build an int from a byte array - convert little to big endian
  // set high order bytes to 0xfff
  public static int constructInt3(byte[] in,int offset)
  {
    int ret =            0xff;
    ret = (ret << 8) | ((int)in[offset + 2] & 0xff);
    ret = (ret << 8) | ((int)in[offset + 1] & 0xff);
    ret = (ret << 8) | ((int)in[offset + 0] & 0xff);
    return(ret);
  }

  // build a long from a byte array - convert little to big endian
  public static long constructLong(byte[] in,int offset)
  {
    long ret =          ((long)in[offset + 7] & 0xff);
    ret |= (ret << 8) | ((long)in[offset + 6] & 0xff);
    ret |= (ret << 8) | ((long)in[offset + 5] & 0xff);
    ret |= (ret << 8) | ((long)in[offset + 4] & 0xff);
    ret |= (ret << 8) | ((long)in[offset + 3] & 0xff);
    ret |= (ret << 8) | ((long)in[offset + 2] & 0xff);
    ret |= (ret << 8) | ((long)in[offset + 1] & 0xff);
    ret |= (ret << 8) | ((long)in[offset + 0] & 0xff);
    return(ret);
  }


  // build a double from a byte array - convert little to big endian
  public static double constructDouble(byte[] in,int offset)
  {
    long ret = constructLong(in,offset);
    return(Double.longBitsToDouble(ret));
  }

  // build a short from a byte array - convert little to big endian
  public static short constructShort(byte[] in,int offset)
  {
    short ret =        (short)((short)in[offset + 1] & 0xff);
    ret = (short)((ret << 8) | (short)((short)in[offset + 0] & 0xff));
    return(ret);
  }

  // bmp header structure
  static class BitmapHeader
  {
    public int nsize;
    public int nbisize;
    public int nwidth;
    public int nheight;
    public int nplanes;
    public int nbitcount;
    public int ncompression;
    public int nsizeimage;
    public int nxpm;
    public int nypm;
    public int nclrused;
    public int nclrimp;

    // read in the bmp header
    public void read(FileInputStream fs) throws IOException
    {
      final int bflen=14;   // 14 byte BITMAPFILEHEADER
      byte bf[]=new byte[bflen];
      fs.read(bf,0,bflen);
      final int bilen=40;   // 40-byte BITMAPINFOHEADER
      byte bi[]=new byte[bilen];
      fs.read(bi,0,bilen);

      // fill bmp header structure
      nsize = constructInt(bf,2);
      nbisize = constructInt(bi,2);
      nwidth = constructInt(bi,4);
      nheight = constructInt(bi,8);
      nplanes = constructShort(bi,12);
      nbitcount = constructShort(bi,14);
      ncompression = constructInt(bi,16);
      nsizeimage = constructInt(bi,20);
      nxpm = constructInt(bi,24);
      nypm = constructInt(bi,28);
      nclrused = constructInt(bi,32);
      nclrimp = constructInt(bi,36);

      if (nsizeimage == 0)
        nsizeimage = nwidth * nheight * 3 ;

    }
  }

  public static Image read(FileInputStream fs)
  {
    try
    {
      BitmapHeader bh = new BitmapHeader();
      bh.read(fs);

      if (bh.nbitcount==24)
        return(readMap24(fs,bh));

      if (bh.nbitcount==8)
        return(readMap8(fs,bh));

      fs.close();
    }
    catch (IOException e)
    {
      // System.out.println("Caught exception in loadbitmap!");
    }
    return(null);
  }

  // read 24-bit bmp
  protected static Image readMap24(FileInputStream fs,BitmapHeader bh) throws IOException
  {
    Image image;
    // No Palatte data for 24-bit format but scan lines are
    // padded out to even 4-byte boundaries.
    int npad = (bh.nsizeimage / bh.nheight) - bh.nwidth * 3;
    int ndata[] = new int [bh.nheight * bh.nwidth];
    byte brgb[] = new byte [( bh.nwidth + npad) * 3 * bh.nheight];
    fs.read (brgb, 0, (bh.nwidth + npad) * 3 * bh.nheight);
    int nindex = 0;
    for (int j = 0; j < bh.nheight; j++)
    {
      for (int i = 0; i < bh.nwidth; i++)
      {
        ndata [bh.nwidth * (bh.nheight - j - 1) + i] = constructInt3(brgb,nindex);
        nindex += 3;
      }
      nindex += npad;
    }

    image = Toolkit.getDefaultToolkit().createImage
            ( new MemoryImageSource (bh.nwidth, bh.nheight, ndata, 0, bh.nwidth));
    fs.close();
    return(image);
  }

  // read 8-bit bmp
  protected static Image readMap8(FileInputStream fs,BitmapHeader bh) throws IOException
  {
    Image image;
    // Have to determine the number of colors, the clrsused
    // parameter is dominant if it is greater than zero.  If
    // zero, calculate colors based on bitsperpixel.
    int nNumColors = 0;
    if (bh.nclrused > 0)
    {
      nNumColors = bh.nclrused;
    }
    else
    {
      nNumColors = (1&0xff)<< bh.nbitcount;
    }

    if (bh.nsizeimage == 0)
    {
      bh.nsizeimage = ((((bh.nwidth* bh.nbitcount)+31) & ~31 ) >> 3);
      bh.nsizeimage *= bh.nheight;
    }

    // Read the palatte colors.
    int  npalette[] = new int [nNumColors];
    byte bpalette[] = new byte [nNumColors*4];
    fs.read (bpalette, 0, nNumColors*4);
    int nindex8 = 0;
    for (int n = 0; n < nNumColors; n++)
    {
      npalette[n] = constructInt3(bpalette,nindex8);
      nindex8 += 4;
    }

    // Read the image data (actually indices into the palette)
    // Scan lines are still padded out to even 4-byte
    // boundaries.
    int npad8 = (bh.nsizeimage / bh.nheight) - bh.nwidth;
    //    System.out.println("nPad is:"+npad8);

    int  ndata8[] = new int [bh.nwidth * bh.nheight];
    byte bdata[] = new byte [(bh.nwidth+npad8)* bh.nheight];
    fs.read (bdata, 0, (bh.nwidth+npad8)*bh.nheight);
    nindex8 = 0;
    for (int j8 = 0; j8 < bh.nheight; j8++)
    {
      for (int i8 = 0; i8 < bh.nwidth; i8++)
      {
        ndata8 [bh.nwidth*(bh.nheight-j8-1)+i8] =
        npalette [((int)bdata[nindex8]&0xff)];
        nindex8++;
      }
      nindex8 += npad8;
    }

    image = Toolkit.getDefaultToolkit().createImage
            ( new MemoryImageSource (bh.nwidth, bh.nheight, ndata8, 0, bh.nwidth));

    return(image);
  }

  // loads bmp filename passed by FileManager
  public static Image  load(String filename)
  {
    try
    {
      FileInputStream fs=new FileInputStream(filename);
      return(read(fs));
    }
    catch(IOException ex)
    {
      return(null);
    }
  }

}