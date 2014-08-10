package com.jipt.filter;
/*
    Trent Lucier
    November 18, 2001

    This filter performs a Laplace edge detection convolution on an image

*/

import java.awt.Image;

public class LaplaceFilter extends Filter{
    float laplaceMatrix[][] = {
                                { 0, -2,  0},
                                {-2,  8, -2},
                                { 0, -2,  0}};

    public LaplaceFilter() {
    }

    public Image laplace(Image im, int width, int height)
    {
        matrix = laplaceMatrix;
        return filterImage(im, width, height);
    }

}