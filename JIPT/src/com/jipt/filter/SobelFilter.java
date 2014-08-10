package com.jipt.filter;
/*
    Trent Lucier
    November 18, 2001

    This class applies the sobel filter to an image.

*/

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public class SobelFilter {
    float k1[][] = {{-1,-2,-1},
                    { 0, 0, 0},
                    { 1, 2, 1}};

    float k2[][] = {{ 1, 0, -1},
                    { 2, 0, -2},
                    { 1, 0, -1}};


    ///////////////////
    /// Constructor ///
    ///////////////////
    public SobelFilter()
    {
        scale(k1, 1/4.0f);
        scale(k2, 1/4.0f);
    }

    ///////////////////////
    // Scale the mattrix //
    ///////////////////////
    public void scale(float a[][], float s)
    {
        int rows = a.length;
        int cols = a[0].length;
        for(int r = 0; r < rows; r++)
            for(int c = 0; c < cols; c++)
                a[r][c] = a[r][c]*s;

    }

    ////////////////////////////
    // Apply the Sobel filter //
    ////////////////////////////
    public Image sobel(Image im, int im_width, int im_height)
    {
        float matrix[][];
        int filter_dim = 0;

        //////////////////////////////
        //// Get the image pixels ////
        //////////////////////////////
        int[] pix     = new int[im_width*im_height]; // Original pixels
        //int[] new_pix = new int[im_width*im_height]; // New pixels
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

        grayscaleInRed(pix);
        int length = pix.length;
        /////////////////////////////////////////////////////////////
        // Use k1 on the red plane and store result in green plane //
        /////////////////////////////////////////////////////////////
        matrix = k1;
        filter_dim = matrix[0].length;
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
                int green_sum = 0;
                int init_r = y - filter_dim/2;
                int init_c = x - filter_dim/2;

                for(int r = init_r; r <= y + filter_dim/2; r++)
                    for(int c = init_c; c <= x + filter_dim/2; c++)
                    {

    	                int red   = (pix[r*im_width + c] >> 16) & 0xff;

                        int new_green = (int) (red*matrix[r - init_r][c - init_c]);

                        green_sum = green_sum + new_green;

                    }
                if(green_sum < 0)
                    green_sum = 0;
                //System.out.println("Green sum = " + green_sum);
                // Extract old values
                int alpha = (pix[i] >> 24) & 0xff;
	            int red   = (pix[i] >> 16) & 0xff;
	            int green = (pix[i] >>  8) & 0xff;
	            int blue  = (pix[i]      ) & 0xff;

                // Store the new values
                pix[i] = 0;
		        pix[i] += (alpha       << 24);
		        pix[i] += (red         << 16); // r
		        pix[i] += (green_sum   <<  8); // g
		        pix[i] += (blue             ); // b

            }
        }

        /////////////////////////////////////////////////////////////
        // Use k2 on the red plane and store result in blue plane  //
        /////////////////////////////////////////////////////////////
        matrix = k2;
        filter_dim = matrix[0].length;
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
                int blue_sum = 0;
                int init_r = y - filter_dim/2;
                int init_c = x - filter_dim/2;

                for(int r = init_r; r <= y + filter_dim/2; r++)
                    for(int c = init_c; c <= x + filter_dim/2; c++)
                    {

    	                int red   = (pix[r*im_width + c] >> 16) & 0xff;

                        int new_blue = (int) (red*matrix[r - init_r][c - init_c]);

                        blue_sum = blue_sum + new_blue;

                    }
                if(blue_sum < 0)
                    blue_sum = 0;

                // Extract old values
                int alpha = (pix[i] >> 24) & 0xff;
	            int red   = (pix[i] >> 16) & 0xff;
	            int green = (pix[i] >>  8) & 0xff;
	            int blue  = (pix[i]      ) & 0xff;

                // Store the new values
                pix[i] = 0;
		        pix[i] += (alpha   << 24);
		        pix[i] += (red     << 16); // r
		        pix[i] += (green   <<  8); // g
		        pix[i] += (blue_sum     ); // b

            }
        }

        ///////////////////////////////////////////
        /// Put sqrt of g and b in all channels ///
        ///////////////////////////////////////////
        for(int i = 0; i < length; i++)
        {
                // Extract old values
                int alpha = (pix[i] >> 24) & 0xff;
	            int red   = (pix[i] >> 16) & 0xff;
	            int green = (pix[i] >>  8) & 0xff;
	            int blue  = (pix[i]      ) & 0xff;

                int new_color = (int) Math.sqrt(green*green + blue*blue);

                // Store the new values
                pix[i] = 0;
		        pix[i] += (alpha   << 24);
		        pix[i] += (new_color    << 16); // r
		        pix[i] += (new_color     <<  8); // g
		        pix[i] += (new_color          ); // b
        }

        Image new_image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(im_width, im_height, pix, 0, im_width));
        return new_image;

    }

    ///////////////////////////////////////////////////
    /// Put the grayscale values in the red channel ///
    ///////////////////////////////////////////////////
    public void grayscaleInRed(int p[])
    {
        int length = p.length;
        for(int i = 0; i < length; i++)
        {
                int a   = (p[i] >> 24) & 0xff;
                int r   = (p[i] >> 16) & 0xff;
	            int g   = (p[i] >>  8) & 0xff;
	            int b   = (p[i]      ) & 0xff;

                int gray = (r + g + b)/3;

                p[i] = 0;
		        p[i] += (a        << 24);
		        p[i] += (gray     << 16); // r
		        p[i] += (g        <<  8); // g
		        p[i] += (b             ); // b
        }
    }

}






