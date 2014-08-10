package com.jipt.filter;
/*
    Trent Lucier
    November 16, 2001

    This class represents an image filter.

*/

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jipt.JIPTsettings;

public class Filter implements Serializable {


    protected float matrix[][];            // The filter matrix.  [rows][columns]
    protected float division_factor = 1.0f;   // Division factor
    protected String name;               // Filter name

    // Constructor receiving matrix, dimension, and name
    public Filter(float m[][], float d, String n)
    {
        matrix = m;
        division_factor = d;
        name = n;
    }

    public Filter() {
    }

    // Get name
    public String getName()
    {
        return name;
    }

    // Get the division factor
    public float getDivisionFactor()
    {
        return division_factor;
    }

    // Get the matrix
    public float[][] getMatrix()
    {
        return matrix;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    /////////////////////// Filter Image (w/ independant channel support) /////////////
    ///////////////////////////////////////////////////////////////////////////////////
    public Image filterImage(Image im, int im_width, int im_height, boolean filter_r, boolean filter_g, boolean filter_b)
    {
       /* System.out.println("-------------------------------");
        System.out.println("Name : " + name);
        System.out.println("Division: " + division_factor);
        for(int r = 0; r < matrix.length; r++)
        {
            for(int c = 0; c < matrix[0].length; c++)
                System.out.print("\t" + matrix[r][c]);
            System.out.println();
        }
        System.out.println("-------------------------------");*/

        int filter_dim  = matrix[0].length; // DImension of filter
//        System.out.println(filter_r + " " + filter_g + " " + filter_b);

        //////////////////////////////
        //// Get the image pixels ////
        //////////////////////////////
        int[] pix     = new int[im_width*im_height]; // Original pixels
        int[] new_pix = new int[im_width*im_height]; // New pixels
	    PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);
	    try
	    {
	      pg.grabPixels();
	    }
            catch(Exception e)
	    {
	      // System.out.println(e.toString());
	    }
        pix = (int []) pg.getPixels();

        ///////////////////////////////
        //// Loop through pixels //////
        ///////////////////////////////
        int length = pix.length;
        for(int i = 0; i < length; i++)
        {
            // Get x,y, coords of pixel
            int x = i%im_width;
            int y = i/im_width;

            // if pixel is in range, filter it
            if(    (x - filter_dim/2) >= 0 && (x + filter_dim/2) < im_width
                && (y - filter_dim/2) >= 0 && (y + filter_dim/2) < im_height)
            {
                // Apply filter
                int red_sum   = 0;
                int green_sum = 0;
                int blue_sum  = 0;
                int alpha_sum = 0;
                int init_r = y - filter_dim/2;
                int init_c = x - filter_dim/2;
                int alpha_value = (pix[i] >> 24) & 0xff;

	            int old_red   = (pix[i] >> 16) & 0xff;
	            int old_green = (pix[i] >>  8) & 0xff;
	            int old_blue  = (pix[i]      ) & 0xff;

                for(int r = init_r; r <= y + filter_dim/2; r++)
                    for(int c = init_c; c <= x + filter_dim/2; c++)
                    {
                        int alpha = (pix[r*im_width + c] >> 24) & 0xff;
    	                int red   = (pix[r*im_width + c] >> 16) & 0xff;
    	                int green = (pix[r*im_width + c] >>  8) & 0xff;
    	                int blue  = (pix[r*im_width + c]      ) & 0xff;

                        int new_alpha = alpha;
                        int new_red   = red;
                        int new_green = green;
                        int new_blue  = blue;

                        alpha_value = alpha;

                        new_red   = (int) (red*matrix[r - init_r][c - init_c]);
                        new_green = (int) (green*matrix[r - init_r][c - init_c]);
                        new_blue =  (int) (blue*matrix[r - init_r][c - init_c]);

                        red_sum   = red_sum + new_red;
                        green_sum = green_sum + new_green;
                        blue_sum  = blue_sum + new_blue;
                    }

                red_sum   = (int) (red_sum/division_factor);
                green_sum = (int) (green_sum/division_factor);
                blue_sum  = (int) (blue_sum/division_factor);

                //System.out.println("d, r,g,b = " + division_factor + " " + red_sum + " " + green_sum + " " + blue_sum);
                if(!filter_r)
                {
                    red_sum = old_red;
                }

                if(!filter_g)
                {
                    green_sum = old_green;
                }

                if(!filter_b)
                {
                    blue_sum = old_blue;
                }

                if(red_sum < 0)
                    red_sum = 0;
                if(green_sum < 0)
                    green_sum = 0;
                if(blue_sum < 0)
                    blue_sum = 0;

                if(red_sum > 255)
                    red_sum = 255;
                if(green_sum > 255)
                    green_sum = 255;
                if(blue_sum > 255)
                    blue_sum = 255;

                //System.out.println("Alpha = " + alpha_value);
                new_pix[i] = 0;
		        new_pix[i] += (alpha_value << 24);
		        new_pix[i] += (red_sum     << 16); // r
		        new_pix[i] += (green_sum   <<  8); // g
		        new_pix[i] += (blue_sum         ); // b
            }
            else
            {
                // else write it directly to destination
                new_pix[i] = pix[i];
            }
        }

        Image new_image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(im_width, im_height, new_pix, 0, im_width));
        return new_image;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    /////////////////////// Filter Image (no independant channel support) /////////////
    ///////////////////////////////////////////////////////////////////////////////////
    public Image filterImage(Image im, int im_width, int im_height)
    {
        return filterImage(im, im_width, im_height, true, true, true);
    }

    /////////////////////////////////////////
    ///////////// Restore ///////////////////
    /////////////////////////////////////////
    public void restore(String filename)
    {
    }

    //////////////////////////////////////////
    ////////////// Delete ////////////////////
    //////////////////////////////////////////
    public void delete()
    {
        // This method causes the filter to delete itself from the directory
        File file = new File(JIPTsettings.FILTERS_PATH + name + JIPTsettings.FILTERS_EXTENSION);
        if(file.exists())
        {
           // Delete previous instance of file and then save over.
           // System.out.println("Deleting " + JIPTsettings.FILTERS_PATH + name + JIPTsettings.FILTERS_EXTENSION );
           file.delete();
        }
    }

    /////////////////////////////////////////
    /////////////// Save ////////////////////
    /////////////////////////////////////////
    public void save()
    {
        // This method writes the object to the jipt directory for filters.
        // If the file is already there, it gets overwritten.  If the file does not exists,
        // this method creates it.
        String filename = name + JIPTsettings.FILTERS_EXTENSION; // NOTE: The extension already include the '.' separator
        File   dir      = new File(JIPTsettings.FILTERS_PATH);

        // System.out.println(JIPTsettings.FILTERS_PATH);
        ///////////////////////////////////////////
        /// Make directory if it does not exist ///
        ///////////////////////////////////////////
        if(!dir.exists())
        {
            // System.out.println("Making dir");
            dir.mkdir();
            //return filter_names;
        }

        File file = new File(JIPTsettings.FILTERS_PATH + filename);

        // System.out.println("File: " +JIPTsettings.FILTERS_PATH + filename);
        if(file.exists())
        {
           // Delete previous instance of file and then save over.
           // System.out.println("Deleting old file");
           file.delete();
        }


        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this);
        }
        catch(Exception e2)
        {
            // System.out.println("Error in writing filter object" + e2.toString());
            //JIPTAlert.error(this, "Error saving filter.", "Filter Save Error","OK");
            return;
        }



    }



}