package com.jipt.fileformats;
// Victor Rego
// BMPWriter.java writes image object as 24bit bmp file
// 11-27-01


import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BMPWriter implements ImageObserver
{

    private final static int FILEHEADER_SIZE = 14;
    private final static int INFOHEADER_SIZE = 40;

    //---  File Header  ---//
    private byte bfType [] = {'B', 'M'};
    private int bfSize = 0;
    private int bfReserved1 = 0;
    private int bfReserved2 = 0;
    private int bfOffset = FILEHEADER_SIZE + INFOHEADER_SIZE;

    //---  Info Header  ---//
    private int biSize = INFOHEADER_SIZE;
    private int biWidth = 0;
    private int biHeight = 0;
    private int biPlanes = 1;
    private int biBitCount = 24;
    private int biCompression = 0;
    private int biSizeImage = 0;
    private int biXPixPerMeter = 0;
    private int biYPixPerMeter = 0;
    private int biClrUsed = 0;
    private int biClrImportant = 0;
    public int[] imageArray;

    public FileOutputStream output = null;

    ////////////////////////////////
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
    {
        if((infoflags & ImageObserver.ERROR) != 0)
        {
            return false;
        }
        return true;
    }// end public boolean imageUpdate(Image, int, int, int, int, int)


    ////////////////////////////////
    public void convert(Image img)
    {
        int pad = ((biWidth * 3) % 4) * biHeight;
        imageArray = new int[biWidth * biHeight];
        biSizeImage = ((biWidth * biHeight) * 3) + pad;
        bfSize = biSizeImage + FILEHEADER_SIZE + INFOHEADER_SIZE;

        try
        {
            // get pixel data from image and store in imageArray
            PixelGrabber pg = new PixelGrabber(img,0,0,biWidth,biHeight,imageArray,0,biWidth);
            pg.grabPixels();
        }
        catch(InterruptedException e)
        {
            // System.out.println(e.toString());
        }

    }// end public void encode(Image img)


    ////////////////////////////////
    public byte[] writeWord(int val)
    {
        byte retValue [] = new byte [2];
        retValue [0] = (byte) (val & 0x00FF);
        retValue [1] = (byte) ((val >>  8) & 0x00FF);
        return (retValue);
    }// end public byte[] writeWord(int val)


    ////////////////////////////////
    public byte[] writeDWord(int val)
    {
        byte retValue [] = new byte [4];
        retValue [0] = (byte) (val & 0x00FF);
        retValue [1] = (byte) ((val >>  8) & 0x000000FF);
        retValue [2] = (byte) ((val >>  16) & 0x000000FF);
        retValue [3] = (byte) ((val >>  24) & 0x000000FF);
        return (retValue);
    }// end public void writeDWord(int dword) throws IOException

    ////////////////////////////////
    public void writeFileHeader()
    {
        try
        {
            output.write(bfType);
            output.write(writeDWord(bfSize));
            output.write(writeWord(bfReserved1));
            output.write(writeWord(bfReserved2));
            output.write(writeDWord(bfOffset));
        }
        catch(Exception e)
        {
            // System.out.println(e);
        }

    }// end public void writeBitFileHeader(void)

    ////////////////////////////////
    public void writeInfoHeader()
    {
        try
        {
            output.write (writeDWord (biSize));
            output.write (writeDWord (biWidth));
            output.write (writeDWord (biHeight));
            output.write (writeWord (biPlanes));
            output.write (writeWord (biBitCount));
            output.write (writeDWord (biCompression));
            output.write (writeDWord (biSizeImage));
            output.write (writeDWord (biXPixPerMeter));
            output.write (writeDWord (biYPixPerMeter));
            output.write (writeDWord (biClrUsed));
            output.write (writeDWord (biClrImportant));
        }
        catch(Exception e)
        {
            // System.out.println(e);
        }

    }// end public void writeBitInfoHeader(void)


    ////////////////////////////////
    public void writeBMP()
    {
        int size;
        int value;
        int rowCount;
        int rowIndex;
        int lastRowIndex;
        int pad;
        int padCount;
        byte rgb [] = new byte [3];
        size = (biWidth * biHeight) - 1;
        pad = ((biWidth * 3) % 4);
        rowCount = 1;
        padCount = 0;
        rowIndex = size - biWidth;
        lastRowIndex = rowIndex;
        try
        {
            for (int j = 0; j < size; j++)
            {
                value = imageArray[rowIndex];
                rgb [0] = (byte) (value & 0xFF);
                rgb [1] = (byte) ((value >> 8) & 0xFF);
                rgb [2] = (byte) ((value >>  16) & 0xFF);
                output.write (rgb);
                if (rowCount == biWidth)
                {
                    padCount += pad;
                    for (int i = 1; i <= pad; i++)
                    {
                        output.write (0x00);
                    }
                    rowCount = 1;
                    rowIndex = lastRowIndex - biWidth;
                    lastRowIndex = rowIndex;
                }
                else
                    rowCount++;
                rowIndex++;
            }

            bfSize += padCount - pad;
            biSizeImage += padCount - pad;
        }
        catch (Exception e)
        {
            // System.out.println(e);
        }

    }// end public void writeBMP()

    ////////////////////////////////
    public boolean save(Image img, String filename)
    {
        File file = new File(filename);
        Integer wd = new Integer(img.getWidth(this));
        biWidth = wd.intValue();
        Integer ht = new Integer(img.getHeight(this));
        biHeight = ht.intValue();

        try
        {
            output = new FileOutputStream(file);
            convert(img);
            writeFileHeader();
            writeInfoHeader();
            writeBMP();

            output.close();
        }// end try

        catch(IOException ex)
        {
            // System.out.println(ex.toString());
            return false;
        }
        return true;
    }// end public boolean save(Image img, String filename)

}// end public class BMPWriter implements ImageObserver