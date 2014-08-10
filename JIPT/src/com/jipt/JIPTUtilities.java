package com.jipt;

/*
    Trent Lucier
    November 18, 2001

    This class includes utility functions, such as quicksorting.

*/

import java.awt.Image;
import java.awt.image.PixelGrabber;

public class JIPTUtilities {

    public JIPTUtilities()
    {
    }
    ////////////////////////////////////////////////
    /////////// Get Pixel array ////////////////////
    ////////////////////////////////////////////////
    public static int[] getPixelArray(Image im)
    {
        int pix[];
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
        return pix;
    }


    /////////////////////////////////////////////////
    ///////////////////// Quicksort /////////////////
    /////////////////////////////////////////////////

    /////////////////
    //// Integer ////
    /////////////////
    public static void quicksort(int a[])
    {
        quicksort(a, 0, a.length - 1);
    }

    public static void quicksort(int a[], int l, int r)
    {
        int i;
        if (r <= l)
            return;

        i = partition(a,l,r);
        quicksort(a, l, i-1);
        quicksort(a, i+1, r);
    }

    public static int partition(int a[], int l, int r)
    {
        int i = l - 1;
        int j = r;
        int v = a[r];

        int temp;

        for(;;)
        {
            while(a[++i] < v);

            while(v < a[--j])
                if(j==l)
                    break;

            if(i>=j)
                break;

            temp = a[i];
            a[i] = a[j];
            a[j] = temp;


        }

        temp = a[i];
        a[i] = a[r];
        a[r] = temp;

        return i;
    }

    /////////////////
    //// Float //////
    /////////////////
    public static void quicksort(float a[])
    {
        quicksort(a, 0, a.length - 1);
    }

    public static void quicksort(float a[], int l, int r)
    {
        int i;
        if (r <= l)
            return;

        i = partition(a,l,r);
        quicksort(a, l, i-1);
        quicksort(a, i+1, r);
    }

    public static int partition(float a[], int l, int r)
    {
        int i = l - 1;
        int j = r;
        float v = a[r];

        float temp;

        for(;;)
        {
            while(a[++i] < v);

            while(v < a[--j])
                if(j==l)
                    break;

            if(i>=j)
                break;

            temp = a[i];
            a[i] = a[j];
            a[j] = temp;


        }

        temp = a[i];
        a[i] = a[r];
        a[r] = temp;

        return i;
    }

}