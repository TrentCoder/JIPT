package com.jipt.filter;
/*
    Trent Lucier
    November 16, 2001

    This class representsa median filter.  The class extends Filter.

*/


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import com.jipt.JIPTUtilities;

public class MedianFilter extends Filter {

    int window_size = 3;




    public MedianFilter() {
    }

    // Sets the window size.  s is either 3,5, or 7.
    public void setWindowSize(int s)
    {
        switch(s)
        {
            case 3 : window_size = 3;  break;
            case 5 : window_size = 5;  break;
            case 7 : window_size = 7;  break;
        }
    }

    // Filters the image based on median filtering.  Overrides similar method in parent class Filter.
    public Image filterImage(Image im, int im_width, int im_height, boolean filter_r, boolean filter_g, boolean filter_b)
    {
        int filter_dim  = window_size; // Dimension of filter


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
                int red_array[] = new int[filter_dim*filter_dim];
                int green_array[] = new int[filter_dim*filter_dim];
                int blue_array[] = new int[filter_dim*filter_dim];
                int index = 0;

                int init_r = y - filter_dim/2;
                int init_c = x - filter_dim/2;

                int alpha_value  = (pix[i] >> 24) & 0xff;
                int red_median   = (pix[i] >> 16) & 0xff;
    	        int green_median = (pix[i] >>  8) & 0xff;
    	        int blue_median  = (pix[i]      ) & 0xff;


                for(int r = init_r; r <= y + filter_dim/2; r++)
                    for(int c = init_c; c <= x + filter_dim/2; c++)
                    {
                        //int alpha = (pix[r*im_width + c] >> 24) & 0xff;
    	                int red   = (pix[r*im_width + c] >> 16) & 0xff;
    	                int green = (pix[r*im_width + c] >>  8) & 0xff;
    	                int blue  = (pix[r*im_width + c]      ) & 0xff;

                        red_array[index] = red;
                        green_array[index] = green;
                        blue_array[index] = blue;
                        index++;
                    }


                // get the medians if that color channel is selected
                if(filter_r)
                    red_median = getMedian(red_array);

                if(filter_g)
                    green_median = getMedian(green_array);

                if(filter_b)
                    blue_median = getMedian(blue_array);

                new_pix[i] = 0;
		        new_pix[i] += (alpha_value << 24);
		        new_pix[i] += (red_median     << 16); // r
		        new_pix[i] += (green_median   <<  8); // g
		        new_pix[i] += (blue_median         ); // b
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

    int getMedian(int array[])
    {
        int length = array.length;

        // Sort array
        JIPTUtilities.quicksort(array);

        return array[length/2];

    }


}