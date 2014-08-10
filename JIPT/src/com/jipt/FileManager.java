package com.jipt;
/*
    Trent Lucier, James LaTour, and Victor Rego
    created 10/14/2001
    FileManager.java

    This class handles file operations.  THIS CLASS IS WHERE THE MULTIPLE FILE TYPES CAN BE READ

*/

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import com.jipt.fileformats.BMPLoader;
import com.jipt.fileformats.BMPWriter;
import com.jipt.fileformats.GIFWriter;
import com.jipt.fileformats.JPEGWriter;
import com.jipt.fileformats.PGMLoader;
import com.jipt.fileformats.PGMWriter;
import com.jipt.fileformats.PPMLoader;
import com.jipt.fileformats.PPMWriter;

public class FileManager
{
    /////////////////////////////////////////////////////////////
    ///// Opens an image. SUPPORT MULTIPLE FILE TYPES HERE //////
    /////////////////////////////////////////////////////////////
    public static Image openImage(String filename)
    {

        String extension = null;

        try
        {
            int index_of_extension = filename.lastIndexOf(".");
            extension = filename.substring(index_of_extension);

            if(extension.equalsIgnoreCase(".gif") )
            {
                return openGIF(filename);
            }
            else if(extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase("jpeg") )
            {
                return openJPG(filename);
            }
            else if(extension.equalsIgnoreCase(".bmp") )
            {
                return openBMP(filename);
            }
            else if(extension.equalsIgnoreCase(".pgm") )
            {
                return openPGM(filename);
            }
            else if(extension.equalsIgnoreCase(".ppm") )
            {
                return openPPM(filename);
            }
            else
            {
                // System.out.println("Unsupported file type");
            }
        }
        catch(Exception e)
        {
            // System.out.println("Error!!" + e.toString());
        }

        // Put a message here
        return null;
    }

    /////////////////////////////////////////////////////////////
    ///// Saves an image. SUPPORT MULTIPLE FILE TYPES HERE //////
    /////////////////////////////////////////////////////////////
    public static boolean saveImage(Image img, String filename)
    {

        String extension = null;
        try
        {
            int index_of_extension = filename.lastIndexOf(".");
            extension = filename.substring(index_of_extension);

            if(extension.equalsIgnoreCase(".gif") )
            {
                return saveGIF(img, filename);
            }
            else if(extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg") )
            {
                return saveJPG(img, filename);
            }
            else if(extension.equalsIgnoreCase(".bmp") )
            {
                return saveBMP(img, filename);
            }
            else if(extension.equalsIgnoreCase(".pgm") )
            {
                return savePGM(img, filename);
            }
            else if(extension.equalsIgnoreCase(".ppm") )
            {
                return savePPM(img, filename);
            }
            else
            {
                // System.out.println("Unsupported file type");
            }
        }
        catch(Exception e)
        {
            // System.out.println("Error!!" + e.toString());
        }

        // Put a message here
        return false;   //error saving file

    }

    /////////////////
    ///// GIF ///////
    /////////////////
    public static Image openGIF(String filename)
    {
        return Toolkit.getDefaultToolkit().getImage(filename);
    }

    public static boolean saveGIF(Image img, String filename)
    {
        GIFWriter gif = new GIFWriter();
        return gif.save(img,filename) ;
        //true if successful
   }

    /////////////////
    ///// JPG ///////
    /////////////////
    public static Image openJPG(String filename)
    {
        return Toolkit.getDefaultToolkit().getImage(filename);
    }

    public static boolean saveJPG(Image img, String filename)
    {
       JPEGWriter jpg = new JPEGWriter();
       return jpg.save(img,filename) ;
        //true if successful
    }

    /////////////////
    ///// BMP ///////
    /////////////////
    public static Image openBMP(String filename)
    {
          BMPLoader bmp = new BMPLoader();
          return bmp.load(filename);
    }

    public static boolean saveBMP(Image img, String filename)
    {
        BMPWriter bmp = new BMPWriter();
        return bmp.save(img, filename);
    }

    /////////////////
    ///// PGM ///////
    /////////////////
    public static Image openPGM(String filename)
    {
          PGMLoader pgm = new PGMLoader();
          return pgm.load(filename);
    }

    public static boolean savePGM(Image img, String filename)
    {
        PGMWriter pgm = new PGMWriter();
        return pgm.save(img,filename);

    }

    /////////////////
    ///// PPM ///////
    /////////////////
    public static Image openPPM(String filename)
    {
          PPMLoader ppm = new PPMLoader();
          return ppm.load(filename);
    }

    public static boolean savePPM(Image img, String filename)
    {
        PPMWriter ppm = new PPMWriter();
        return ppm.save(img,filename);
    }

	///////////////////////////////
	//////// Parse File Name //////
	///////////////////////////////
    // Get's the file name from a path + filename String
    public static String parseFileName( String filename )
    {
        int lastSlashIndex = filename.lastIndexOf( File.separator );
        return filename.substring(lastSlashIndex+1);
    }

	//////////////////////////
	//////// Parse Path //////
	//////////////////////////
    // Get's the path from a path + filename String
    public static String parsePath( String path )
    {
        int lastSlashIndex = path.lastIndexOf( File.separator );
        return path.substring(0, lastSlashIndex);
    }

}