package com.jipt.filter;
/*
    Trent Lucier

    This class applies a mean filter to an image.
*/

import java.awt.Image;

public class MeanFilter extends Filter {


    float m3x3[][] = { {1,1,1},
                     {1,1,1},
                     {1,1,1}};

    float m5x5[][] = { {1,1,1,1,1},
                     {1,1,1,1,1},
                     {1,1,1,1,1},
                     {1,1,1,1,1},
                     {1,1,1,1,1}};

    float m7x7[][] = { {1,1,1,1,1,1,1},
                     {1,1,1,1,1,1,1},
                     {1,1,1,1,1,1,1},
                     {1,1,1,1,1,1,1},
                     {1,1,1,1,1,1,1},
                     {1,1,1,1,1,1,1},
                     {1,1,1,1,1,1,1}};

    public MeanFilter() {
    }

    // Sets the window size.  s is either 3,5, or 7.
    public void setWindowSize(int s)
    {
        switch(s)
        {
            case 3 : matrix = m3x3; division_factor = 9;  break;
            case 5 : matrix = m5x5; division_factor = 25; break;
            case 7 : matrix = m7x7; division_factor = 49; break;
        }
    }

    // Filters the image and returns the result
    public Image filterImage(Image im, int im_width, int im_height, boolean filter_r, boolean filter_g, boolean filter_b)
    {

        return super.filterImage(im, im_width, im_height, filter_r, filter_g, filter_b);
    }
}