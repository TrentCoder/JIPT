package com.jipt;

/*
    ColorManager
    Trent Lucier

    This class contains the color adjustment options as well as the arithmetic functions.
 */


import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

import com.jipt.gui.BrightnessContrastFrame;
import com.jipt.gui.ColorCombineFrame;
import com.jipt.gui.FrameManager;
import com.jipt.gui.HSIAdjustFrame;
import com.jipt.gui.HueAdjustFrame;
import com.jipt.gui.ImageArithmeticFrame;
import com.jipt.gui.ImageFrame;
import com.jipt.gui.MainFrame;
import com.jipt.gui.RGBAdjustFrame;


public class ColorManager {

    private static MainFrame    main_frame    = null;
    private static FrameManager frame_manager = null;
    private static ToolManager  tool_manager  = null;

    //////////////////////////////////////////
    ///// Constants for image operations /////
    //////////////////////////////////////////
    public static final int AND = 0;
    public static final int OR  = 1;
    public static final int XOR = 2;
    public static final int ADD = 3;
    public static final int SUBTRACT = 4;
    public static final int DIFFERENCE = 5;
    public static final int LIGHTEST = 6;
    public static final int DARKEST = 7;
    public static final int AVERAGE = 8;
    public static final int MULTIPLY = 9;
    public static final int DIVIDE = 10;


    // Constructor
	public ColorManager( MainFrame mf, ToolManager tm)
	{
        main_frame = mf;
        tool_manager = tm;
    }

    public ColorManager() {
    }

    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////// Combine Channels /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void combine_channels(FrameManager fm)
    {
        frame_manager = fm;
        try
        {
            ColorCombineFrame ccf = new ColorCombineFrame(this, frame_manager, main_frame);
            ccf.initComponents();
            ccf.setVisible(true);
        }
        catch(Exception e)
        {
          //System.out.println("Error in grayscale" + e.toString());
          e.printStackTrace();
        }

    }

    ////////////////////////
    /// Combine Channels ///
    ////////////////////////
    public Image combine(Image redImage, Image greenImage, Image blueImage)
    {
        int width  = redImage.getWidth(main_frame);
        int height = redImage.getHeight(main_frame);

        int red_array[]   = JIPTUtilities.getPixelArray(redImage);
        int green_array[] = JIPTUtilities.getPixelArray(greenImage);
        int blue_array[]  = JIPTUtilities.getPixelArray(blueImage);

        int new_pix[] = new int[red_array.length];

        for(int i = 0; i < new_pix.length; i++)
        {
            int alpha = 255;
	        int red   = (red_array[i]   >> 16) & 0xff;
	        int green = (green_array[i] >>  8) & 0xff;
	        int blue  = (blue_array[i]       ) & 0xff;

            /////////////////////////////////////////////
            ///////////// Put in new pixel array ////////
            /////////////////////////////////////////////
            new_pix[i] = 0;
    		new_pix[i] += (alpha         << 24);   // alpha
    		new_pix[i] += (red           << 16);   // r
    		new_pix[i] += (green         <<  8);   // g
    		new_pix[i] += (blue               );   // b
        }

        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, new_pix, 0, width));
        return new_im;

    }


    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////// Reduce Color /////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void reduce_color(ImageFrame image_frame, int new_depth)
    {
       Image new_image = null;

       switch(new_depth)
       {
           case 1 : new_image = get1BitImage(image_frame.getJIPTImage().getImage()); break;
           case 4 : new_image = get4BitImage(image_frame.getJIPTImage().getImage()); break;
           case 8 : return; //new_image = get8BitImage(image_frame.getJIPTImage().getImage()); break;
       }

       image_frame.setFrameImage(new_image, true);

        //update bpp
        image_frame.getJIPTImage().setBpp( new_depth );
    }

    /////////////////
    ///// 1 Bit /////
    /////////////////
    public Image get1BitImage(Image im)
    {
        int width  = im.getWidth(main_frame);
        int height = im.getHeight(main_frame);

        // convert to grayscale
        im = ToolManager.grayscale_image(im, width, height);

        // The following two lines were used to threshold about the median grey value in the image
        // int pix[] = JIPTUtilities.getPixelArray(im);
        //int threshold   = (pix[pix.length/2] >> 16) & 0xff;

        // threshold image about 128
        im = tool_manager.threshold_image(im, width, height, 255, 255, 255, 128);

        return im;
    }

    /////////////////////////////
    ///// 4 Bit (16 colors) /////
    /////////////////////////////
    public Image get4BitImage(Image im)
    {
        int width = im.getWidth(main_frame);
        int height = im.getHeight(main_frame);

        //////////////////////////////////////////
        /////////// Creating the palette /////////
        //////////////////////////////////////////
        int color_palette[]   = new int[16]; // Holds the color value
        int color_frequency[] = new int[16]; // olds the corresponding color's # of occurences

        for(int i = 0; i < 16; i++)
        {
            color_frequency[i] = -1;
            color_palette[i] = -1;
        }

        ///////////////////////
        // Sort color array  //
        ///////////////////////
        int pix[] = JIPTUtilities.getPixelArray(im);
        int new_pix[] = new int[pix.length];
        JIPTUtilities.quicksort(pix);

        int position = 0;
        for(int i = 0; i < 16; i++)
        {
            // Put current color in array if it isn't already there

            position = (pix.length-position)/16 + position;
            boolean not_found = true;

            while(not_found)
            {
                for(int j = 0; j < color_palette.length; j++)
                {
                    if(color_palette[j] == -1)
                    {
                        color_palette[j] = pix[position];
                        not_found = false;
                        break;
                    }
                }

                position++;
            }
        }


        ///////////////////////
        /// DISPLAY PALETTE ///
        ///////////////////////
       /* for(int i = 0; i < color_palette.length; i++)
        {
            System.out.println("Frequency: " + color_frequency[i] + " Color: " + color_palette[i]);
            for(int j = i*new_pix.length/16; j < (i+1)*new_pix.length/16; j++)
                new_pix[j] = color_palette[i];
        }*/

        /////////////////////////////////
        //// Remap pixels to palette ////
        /////////////////////////////////
        pix = JIPTUtilities.getPixelArray(im); // get original pix order back

        for(int i = 0; i < pix.length; i++)
        {

	        int red1   = (pix[i] >> 16) & 0xff;
	        int green1 = (pix[i] >>  8) & 0xff;
	        int blue1  = (pix[i]      ) & 0xff;

            int minimum_deviation = 999999;
            int index = 0;
            // Map to color with minimum deviation
            for(int j = 0; j < color_palette.length; j++)
            {
	            int red2   = (color_palette[j] >> 16) & 0xff;
	            int green2 = (color_palette[j] >>  8) & 0xff;
	            int blue2  = (color_palette[j]      ) & 0xff;

                int deviation = Math.abs(red1 - red2) + Math.abs(green1 - green2) + Math.abs(blue1 - blue2);
                if(deviation < minimum_deviation)
                {
                    minimum_deviation = deviation;
                    index = j;
                }


            }

            new_pix[i] = color_palette[index];
        }


        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, new_pix, 0, width));
        return new_im;
    }

    //////////////////////////////
    ///// 8 Bit (256 colors) /////
    //////////////////////////////
    public Image get8BitImage(Image im)
    {
        return null;
    }


    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////// RGB Frame ////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void rgb_frame(ImageFrame image_frame)
    {
        try
        {
            RGBAdjustFrame rgb = new RGBAdjustFrame(this, image_frame);
            rgb.initComponents();
            rgb.setVisible(true);
        }
        catch(Exception e)
        {
          //System.out.println("Error in grayscale" + e.toString());
          e.printStackTrace();
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////// HSI Frame ////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void hsi_frame(ImageFrame image_frame)
    {
        try
        {
            HSIAdjustFrame haf = new HSIAdjustFrame(this, image_frame);
            haf.initComponents();
            haf.setVisible(true);
        }
        catch(Exception e)
        {
          //System.out.println("Error in grayscale" + e.toString());
          e.printStackTrace();
        }

    }

    /////////////////////////////////
    //////// RGB Adjust   ///////////
    /////////////////////////////////
    public Image rgb_adjust(Image im, int width, int height, int r_val, int g_val, int b_val)
    {
        /*
            This method adjusts the image's red, green, and blue values.
            The parameters red, green, and blue represent the percentage by which the
            value is to be changed.  Negative numbers represent a decrease in value,
            positive numbers an increase.
        */



        int pix[] = JIPTUtilities.getPixelArray(im);
        int new_pix[] = new int[width*height];

        for(int i = 0; i < pix.length; i++)
        {
            //////// Get pixels ///////////////////
            int alpha = (pix[i] >> 24) & 0xff;
	        int red   = (pix[i] >> 16) & 0xff;
	        int green = (pix[i] >>  8) & 0xff;
	        int blue  = (pix[i]      ) & 0xff;

            if(r_val < 0)
                red = (int) (red + red*r_val/100.0);
            else
                red = (int) (red + (255-red)*r_val/100.0);

            if(g_val < 0)
                green = (int) (green + green*g_val/100.0);
            else
                green = (int) (green + (255-green)*g_val/100.0);

            if(b_val < 0)
                blue = (int) (blue + blue*b_val/100.0);
            else
                blue = (int) (blue + (255-blue)*b_val/100.0);


            /////////////////////////////////////////////
            ///////////// Put in new pixel array ////////
            /////////////////////////////////////////////
            new_pix[i] = 0;
    		new_pix[i] += (alpha         << 24);   // alpha
    		new_pix[i] += (red           << 16);   // r
    		new_pix[i] += (green         <<  8);   // g
    		new_pix[i] += (blue               );   // b


        }
        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, new_pix, 0, width));
        return new_im;
    }

    /////////////////////////////////
    //////// HSI Adjust   ///////////
    /////////////////////////////////
    public Image hsi_adjust(Image im, int width, int height, int hue, int saturation, int brightness)
    {
        // The hue, saturation, and brightness variables represent values in the range
        // from -100 to 100.  -100 is a full decrease in value, 0 is no change, and 100 is a full
        // increase.
        // NOTE: Brightness is the same as Intensity.  I use "brightness" to be consistent
        //       with the Color class's terminology.



        int pix[] = JIPTUtilities.getPixelArray(im);
        int new_pix[] = new int[width*height];

        for(int i = 0; i < pix.length; i++)
        {
            //////// Get pixels ///////////////////
            int alpha = (pix[i] >> 24) & 0xff;
	        int red   = (pix[i] >> 16) & 0xff;
	        int green = (pix[i] >>  8) & 0xff;
	        int blue  = (pix[i]      ) & 0xff;

            // Get HSB for this pixel
            float hsb_array[] = Color.RGBtoHSB(red, green, blue, null);

            /////////////////////////////
            /// Get new HSB values //////
            /////////////////////////////

            ///////////////////
            ///// Hue /////////
            ///////////////////
            float degree_hue = hsb_array[0]*360.0f;

            degree_hue = degree_hue + (hue*360.0f/100.0f);

            if(degree_hue > 360.0f)
                degree_hue = degree_hue - 360.0f;
            else if(degree_hue < 0.0f)
                degree_hue = degree_hue + 360;


            hsb_array[0] = degree_hue/360.0f;

            ////////////////////////
            ///// Saturation ///////
            ////////////////////////



            if(saturation <= 0)
                hsb_array[1] = hsb_array[1] + saturation*hsb_array[1]/100.0f;
            else
                hsb_array[1] = hsb_array[1] + saturation*(hsb_array[1])/100.0f;

           // hsb_array[1] = 1;
            ////////////////////////
            //// Put in range //////
            ////////////////////////
            if(hsb_array[0] > 1)
                hsb_array[0] = 1;

            if(hsb_array[1] > 1)
                hsb_array[1] = 1;

            if(hsb_array[2] > 1)
                hsb_array[2] = 1;

           //System.out.println("HSI = " + hsb_array[0] + " " + hsb_array[1] + " " + hsb_array[2]);
            Color new_color = new Color(Color.HSBtoRGB(hsb_array[0], hsb_array[1], hsb_array[2]));

            red = new_color.getRed();
            green = new_color.getGreen();
            blue = new_color.getBlue();

            ////////////////////////////////////////
            //// Adjust brightness in RGB domain ///
            ///////////////////////////////////////
            red   = red   + (int) ((brightness/100.0)*255);
            green = green + (int) ((brightness/100.0)*255);
            blue  = blue  + (int) ((brightness/100.0)*255);

            ///// Put in correct range (0...255) /////
            red   = putInRange(red);
            green = putInRange(green);
            blue  = putInRange(blue);

            /////////////////////////////////////////////
            ///////////// Put in new pixel array ////////
            /////////////////////////////////////////////
            new_pix[i] = 0;
    		new_pix[i] += (alpha         << 24);   // alpha
    		new_pix[i] += (red           << 16);   // r
    		new_pix[i] += (green         <<  8);   // g
    		new_pix[i] += (blue               );   // b


        }
        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, new_pix, 0, width));
        return new_im;
    }

    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////// Hue Frame ////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void hue_frame(ImageFrame image_frame)
    {
        try
        {
            HueAdjustFrame haf = new HueAdjustFrame(this, image_frame);
            haf.initComponents();
            haf.setVisible(true);
        }
        catch(Exception e)
        {
          //System.out.println("Error in grayscale" + e.toString());
          e.printStackTrace();
        }

    }
    /////////////////////////////////
    //////// Replace hues ///////////
    /////////////////////////////////
    public Image adjustHues(Image im, int width, int height,int[] old_hues, int[] new_hues)
    {
        int pix[] = JIPTUtilities.getPixelArray(im);
        int new_pix[] = new int[width*height];

        for(int i = 0; i < pix.length; i++)
        {
            //////// Get pixels ///////////////////
            int alpha = (pix[i] >> 24) & 0xff;
	        int red   = (pix[i] >> 16) & 0xff;
	        int green = (pix[i] >>  8) & 0xff;
	        int blue  = (pix[i]      ) & 0xff;

            // Get HSB for this pixel
            float hsb_array[] = Color.RGBtoHSB(red, green, blue, null);

            //System.out.println("HSB = " + hsb_array[0] + " " + hsb_array[1] + " " + hsb_array[2]);

            int hue_degree = (int) (360*hsb_array[0]);
          //  if(hue_degree < 0 || hue_degree == 0)
          //      System.out.println("Hue degree of pixel = " + hue_degree);
            int threshold = 18; // How close the current hue has to be to the old_hue value
            for(int j = 0; j < old_hues.length; j++)
            {
                if(inRange(old_hues[j], hue_degree, threshold))
                {
                    if(new_hues[j] != old_hues[j])
                        hsb_array[0] = new_hues[j]/360.0f;
                    break;
                }
            }

            Color new_color = new Color(Color.HSBtoRGB(hsb_array[0], hsb_array[1], hsb_array[2]));

            red = new_color.getRed();
            green = new_color.getGreen();
            blue = new_color.getBlue();

            new_pix[i] = 0;
    		new_pix[i] += (alpha         << 24);   // alpha
    		new_pix[i] += (red           << 16);   // r
    		new_pix[i] += (green         <<  8);   // g
    		new_pix[i] += (blue               );   // b


        }

        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, new_pix, 0, width));
        return new_im;
    }

    public boolean inRange(int replace_hue, int this_hue, int threshold)
    {
        int greater = 0;
        int lesser  = 0;

        if(replace_hue > this_hue)
        {
            greater = replace_hue;
            lesser  = this_hue;

        }
        else
        {
            greater = this_hue;
            lesser = replace_hue;
        }

        if((greater - lesser <= threshold)
         || ((lesser - greater)+360<=threshold))
            return true;
        return false;



    }

    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////// Brightness/Contrast Frame ////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void brightness_contrast_frame(ImageFrame image_frame)
    {
        try
        {
            BrightnessContrastFrame bcf = new BrightnessContrastFrame(this, image_frame);
            bcf.initComponents();
            bcf.setVisible(true);
        }
        catch(Exception e)
        {
          //System.out.println("Error in grayscale" + e.toString());
          e.printStackTrace();
        }

    }

    /////////////////////////////////
    //////// Brightness /////////////
    /////////////////////////////////
    public Image brightness(Image im, int width, int height, int brightness)
    {
        int pix[] = JIPTUtilities.getPixelArray(im);
        int new_pix[] = new int[width*height];

        for(int i = 0; i < pix.length; i++)
        {
            //////// Get pixels ///////////////////
            int alpha = (pix[i] >> 24) & 0xff;
	        int red   = (pix[i] >> 16) & 0xff;
	        int green = (pix[i] >>  8) & 0xff;
	        int blue  = (pix[i]      ) & 0xff;

            ////// Scale based on brightness //////
            red   = red   + (int) ((brightness/100.0)*255);
            green = green + (int) ((brightness/100.0)*255);
            blue  = blue  + (int) ((brightness/100.0)*255);

            ///// Put in correct range (0...255) /////
            red   = putInRange(red);
            green = putInRange(green);
            blue  = putInRange(blue);

            new_pix[i] = 0;
    		new_pix[i] += (alpha         << 24);   // alpha
    		new_pix[i] += (red           << 16);   // r
    		new_pix[i] += (green         <<  8);   // g
    		new_pix[i] += (blue               );   // b

        }

        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, new_pix, 0, width));
        return new_im;
    }

    /////////////////////////////////
    //////// Contrast   /////////////
    /////////////////////////////////
    public Image contrast(Image im, int width, int height, int contrast)
    {
        int pix[] = JIPTUtilities.getPixelArray(im);
        int new_pix[] = new int[width*height];
        int mean_luminance = 0;

        mean_luminance = getMeanLuminance(pix);

        for(int i = 0; i < pix.length; i++)
        {
            //////// Get pixels ///////////////////
            int alpha = (pix[i] >> 24) & 0xff;
	        int red   = (pix[i] >> 16) & 0xff;
	        int green = (pix[i] >>  8) & 0xff;
	        int blue  = (pix[i]      ) & 0xff;

            ////// Adjust for contrast /////////
            if(contrast <= 0)
            {
                red   = red   + (int) ((red   - mean_luminance)*(contrast/100.0));
                green = green + (int) ((green - mean_luminance)*(contrast/100.0));
                blue  = blue  + (int) ((blue  - mean_luminance)*(contrast/100.0));
            }
            else
            {

                if(red - mean_luminance > 0)
                    red = red +  (int) ((255-red)*contrast/100.0);
                else
                    red = red -  (int) (red*contrast/100.0);


                if(green - mean_luminance > 0)
                    green = green +  (int) ((255-green)*contrast/100.0);
                else
                    green = green -  (int) (green*contrast/100.0);


                if(blue - mean_luminance > 0)
                    blue = blue +  (int) ((255-blue)*contrast/100.0);
                else
                    blue = blue -  (int) (blue*contrast/100.0);



            }

            ///// Put in correct range (0...255) /////
            red   = putInRange(red);
            green = putInRange(green);
            blue  = putInRange(blue);

            new_pix[i] = 0;
    		new_pix[i] += (alpha         << 24);   // alpha
    		new_pix[i] += (red           << 16);   // r
    		new_pix[i] += (green         <<  8);   // g
    		new_pix[i] += (blue               );   // b

        }

        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, new_pix, 0, width));
        return new_im;
    }

    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////// Image Arithmetic /////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public void arithmetic_frame(FrameManager fm)
    {
        frame_manager = fm;
        try
        {
            ImageArithmeticFrame iaf = new ImageArithmeticFrame(this, frame_manager, main_frame);
            iaf.initComponents();
            iaf.setVisible(true);
        }
        catch(Exception e)
        {
          //System.out.println("Error in grayscale" + e.toString());
          e.printStackTrace();
        }

    }

    //////////////////////////////
    ///////// Arithmetic /////////
    //////////////////////////////
    public Image arithmetic(Image im1, Image im2, int width, int height, int op,
                                boolean r, boolean g, boolean b, boolean clip)
    {
        /* im1 = source image 1.
           im2 = source image 2.
           width  = width of images (they are the same size)
           height = height of images
           op     = operation.  See top of class for definitions.
                    NOTE: In subtraction, the result is im1 - im2.
                    in Division, the result is im1/im2.
           r, g, b = affected channels
           clip    =            X < 0           X > 255
                                -------------------------
                        true	X = 0	        X = 255
                        false	X = 256 +X	    X = X -256
        */

        int pix1[] = JIPTUtilities.getPixelArray(im1);
        int pix2[] = JIPTUtilities.getPixelArray(im2);

        // Get the sizes. Size1 and Size2 SHOULD BE the same, but this will catch Array exceptions
        // in case on is slightly longer than another.
        int size1 = pix1.length;
        int size2 = pix2.length;
        int size  = 0;
        if(size1 > size2)
            size = size2;
        else
            size = size1;
        int new_pix[] = new int[size];

        /////////////////////////////////////////
        ////////////// Main Loop ////////////////
        /////////////////////////////////////////
        for(int i = 0; i < size; i++)
        {
            /////////////////
            /// Source 1 ////
            /////////////////
            int alpha1 = (pix1[i] >> 24) & 0xff;
	        int red1   = (pix1[i] >> 16) & 0xff;
	        int green1 = (pix1[i] >>  8) & 0xff;
	        int blue1  = (pix1[i]      ) & 0xff;

            /////////////////
            /// Source 2 ////
            /////////////////
            int alpha2 = (pix2[i] >> 24) & 0xff;
	        int red2   = (pix2[i] >> 16) & 0xff;
	        int green2 = (pix2[i] >>  8) & 0xff;
	        int blue2  = (pix2[i]      ) & 0xff;

            /////////////////
            /// Result   ////
            /////////////////
            int alpha3 = 255;
            int red3   = 0;
            int green3 = 0;
            int blue3  = 0;

            if(r)
                red3   = performOperation(red1, red2, op, clip);
            if(g)
                green3 = performOperation(green1, green2, op, clip);
            if(b)
                blue3  = performOperation(blue1, blue2, op, clip);

            new_pix[i] = 0;
    		new_pix[i] += (alpha3         << 24);   // alpha
    		new_pix[i] += (red3           << 16);   // r
    		new_pix[i] += (green3         <<  8);   // g
    		new_pix[i] += (blue3               );   // b

        }


        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, new_pix, 0, width));
        return new_im;
    }

    //////////////////////////////////////////////
    ///// Perform an operation on 2 values.  /////
    //////////////////////////////////////////////
    public int performOperation(int v1, int v2, int op, boolean clip)
    {
    //System.out.println("Performing operation");
        int result = 0;
        switch(op)
        {
            case AND        : result = v1 & v2; break;
            case ADD        : result = v1 + v2; break;
            case SUBTRACT   : result = v1 - v2; break;
            case OR         : result = v1 | v2; break;
            case DIVIDE     :
                              if(v2 == 0)
                                result = 255;
                              else
                                result = v1 / v2;
                              break;
            case MULTIPLY   : result = v1 * v2; break;
            case DIFFERENCE : result = Math.abs(v1 - v2); break;
            case LIGHTEST   : if(v2 > v1)
                                    result = v2;
                              else
                                    result = v1;
                              break;
            case DARKEST    : if(v2 < v1)
                                    result = v2;
                              else
                                    result = v1;
                              break;
            case AVERAGE    : result = (v1 + v2)/2; break;
            case XOR        : result = v1 ^ v2; break;
        }

        if(result < 0)
        {
            if(clip)
                result = 0;
           else
           {
               result = result*-1;
               result = result%256;

            }
        }
        else if(result > 255)
        {
            if(clip)
                result = 255;
            else
                result = result%256;
        }

        return result;
    }



    /////////////////////////////
    //// Puts value in range ////
    /////////////////////////////
    public int putInRange(int c)
    {
        if(c < 0)
            return 0;

        if(c > 255)
            return 255;

        return c;
    }

    /////////////////////////////////////////////
    //// Gets the mean luminance of an image ////
    /////////////////////////////////////////////
    public int getMeanLuminance(int pix[])
    {
        int length   = pix.length;
        int mean_lum = 0;

        for(int i = 0; i < length; i++)
        {
            int alpha = (pix[i] >> 24) & 0xff;
	        int red   = (pix[i] >> 16) & 0xff;
	        int green = (pix[i] >>  8) & 0xff;
	        int blue  = (pix[i]      ) & 0xff;

            mean_lum += (int) (0.3*red + 0.59*green + 0.11*blue);
        }

        return mean_lum/length;
    }
}
