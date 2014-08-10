package com.jipt.filter;
/*
    Trent Lucier
    November 23, 2001

    This class applies the Roberts filter to an image.

*/

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import com.jipt.JIPTUtilities;

public class RobertsFilter {


    ///////////////////

    /// Constructor ///
    ///////////////////
    public RobertsFilter()
    {

    }

    ////////////////////////////
    // Apply the Sobel filter //
    ////////////////////////////
    public Image roberts(Image im, int im_width, int im_height)
    {
        float matrix[][];
        int filter_dim = 0;

        //////////////////////////////
        //// Get the image pixels ////
        //////////////////////////////
        int[] pix     = JIPTUtilities.getPixelArray(im);

        grayscaleInRed(pix);

        int p[] = new int[4];
        int delta_u = 0;
        int delta_v = 0;
        short t;
        for(int x = 0; x < im_width - 1; x++)
            for(int y = 0; y < im_height - 1; y++)
            {

                int a   = (pix[x + y*im_width] >> 24) & 0xff;

                p[0] = (pix[x + y*im_width] >> 16) & 0xff;
                p[1] = (pix[x + 1 + y*im_width] >> 16) & 0xff;
                p[2] = (pix[x + (y+1)*im_width] >> 16) & 0xff;
                p[3] = (pix[x + 1 + (y+1)*im_width] >> 16) & 0xff;

                delta_u = p[0] - p[3];
                delta_v = p[1] - p[2];

                t = (short) Math.sqrt(delta_u*delta_u + delta_v*delta_v);

                pix[x + y*im_width] = 0;
		        pix[x + y*im_width] += (a        << 24);
		        pix[x + y*im_width] += (t        << 16); // r
		        pix[x + y*im_width] += (t        <<  8); // g
		        pix[x + y*im_width] += (t             ); // b

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






