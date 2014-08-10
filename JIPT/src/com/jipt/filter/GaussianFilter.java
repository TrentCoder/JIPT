package com.jipt.filter;
/*
    Trent Lucier

    This class applies a gaussian filter to an image.
*/

import java.awt.Image;

public class GaussianFilter extends Filter {


    float g3x3[][] = { {0.0625f,    0.125f,     0.0625f},
                       {0.125f,      0.25f,      0.125f},
                       {0.0625f,    0.125f,     0.0625f}};




    public GaussianFilter() {
    }

    public Image gaussian(Image im, int width, int height)
    {
        matrix = g3x3;
        return filterImage(im, width, height);
    }


    // Filters the image and returns the result
    public Image filterImage(Image im, int im_width, int im_height, boolean filter_r, boolean filter_g, boolean filter_b)
    {

        return super.filterImage(im, im_width, im_height, filter_r, filter_g, filter_b);
    }
}