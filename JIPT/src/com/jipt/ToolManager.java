package com.jipt;
/*
	Trent Lucier
	10/14/2001
	ToolManager.java

	This class deals with the Tools options.
         Resize, Rotate, Grayscale, Negative Image completed so far

*/
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

import com.jipt.gui.FlipRotateFrame;
import com.jipt.gui.GrayscaleFrame;
import com.jipt.gui.ImageFrame;
import com.jipt.gui.IsolateChannelFrame;
import com.jipt.gui.MainFrame;
import com.jipt.gui.NegativeFrame;
import com.jipt.gui.ResizeFrame;
import com.jipt.gui.TextFrame;

public class ToolManager
{
    private static MainFrame main_frame = null;
	// Constructor
	public ToolManager( MainFrame mf )
	{
        main_frame = mf;
	}

    public ToolManager()
    {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////// Add Text ////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void add_text(ImageFrame image_frame)
    {
        try
        {
            //image_frame.addToUndo(this);
            TextFrame tf = new TextFrame(main_frame, image_frame);
            tf.initComponents();
            tf.setVisible(true);
        }
        catch(Exception e)
        {
          //System.out.println("Error in grayscale" + e.toString());
          e.printStackTrace();
        }

    }

    /////////////////////////////////
    ////// Trim A Text Image ////////
    /////////////////////////////////
    public static Image trimTextImage(Image im, int width, int height)
    {
        /*
            Due to the slight inaccuracy of the font sizes, a text image may
            not need all the height allocated to it.  This method will crop and
            image so that all rows that are totally transparent are eliminated.

            This method assumes there is at least SOME text in th image.  Additionally,
            I do not bother cropping columns.
        */

        int transparent_pic = JIPTsettings.CLEAR_PIXEL_3; // Transparent pic
        //main_frame.getJIPTsettings().getClearPixelColor();

        int pix[] = JIPTUtilities.getPixelArray(im);

        // Scan each row.  Stop when the end of the array is reached or
        // a non-transparent color is hit.
        int count = 0; // A counter that keeps track of how many rows are transparent

        for(int i = 1; i < pix.length; i++)
        {
            int alpha = (pix[i] >> 24) & 0xff;
            if(alpha != 0)
                break;

            if(i%width == 0)
                count++;
        }
        // System.out.println("Count = " + count);
        int new_pix[] = new int[(height - count)*width];
        int index = 0;
        for(int i = count*width; i < pix.length; i++)
        {
            int alpha = (pix[i] >> 24) & 0xff;
            if(alpha == 0)
                new_pix[index] = transparent_pic;
            else
                new_pix[index] = pix[i];
            index++;
        }

        Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height - count, new_pix, 0, width));
        return new_im;

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////// Channel Isolation      ////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public void isolate_channel(ImageFrame image_frame)
    {
            try
            {
                //image_frame.addToUndo(this);
                IsolateChannelFrame icf = new IsolateChannelFrame(this, image_frame, main_frame);
                icf.initComponents();
                icf.setVisible(true);
            }
            catch(Exception e)
            {
              //System.out.println("Error in grayscale" + e.toString());
              e.printStackTrace();
            }
    }

    ///////////////////////////
    ///////// Isolate /////////
    ///////////////////////////
    public Image isolate(Image im, int width, int height, int color)

    {
        // Color:  0 = red,  1 = green,  2 = blue,  3 = alpha
        int pix[] = JIPTUtilities.getPixelArray(im);
        int size  = pix.length;
        for(int i = 0; i < size; i++)
        {

	        int alpha = (pix[i] >> 24) & 0xff;
	        int red   = (pix[i] >> 16) & 0xff;
	        int green = (pix[i] >>  8) & 0xff;
	        int blue  = (pix[i]      ) & 0xff;

            int new_color = 0;

            switch(color)
            {
                case 0 : new_color = red; break;
                case 1 : new_color = green; break;
                case 2 : new_color = blue; break;
                case 3 : new_color = alpha;
            }

    		pix[i] = 0;
    		pix[i] += (alpha         << 24);   // alpha
    		pix[i] += (new_color     << 16);   // r
    		pix[i] += (new_color     <<  8);   // g
    		pix[i] += (new_color         );    // b
            }

            Image new_im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pix, 0, width));
            return new_im;
    }

        ////////////////////////////////////////////////////////////////////////////////////////////////
	    //////////////////////////////////////// Grayscale & Threshold /////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////
        public void grayscale_frame(ImageFrame image_frame)
        {
            try
            {
                image_frame.addToUndo(this);
                GrayscaleFrame grayscale_frame = new GrayscaleFrame(this, image_frame);
                grayscale_frame.initComponents();
                grayscale_frame.setVisible(true);
            }
            catch(Exception e)
            {
              //System.out.println("Error in grayscale" + e.toString());
              e.printStackTrace();
            }
        }

        public void grayscale_image(ImageFrame image_frame)
        {
            image_frame.setGrayscale(true);
//            Image im     = this.cloneImage( image_frame.getJIPTImage() );
            Image im = image_frame.getJIPTImage().getImage();
            int width  = image_frame.getImageWidth();
            int height = image_frame.getImageHeight();
            image_frame.setFrameImage(grayscale_image(image_frame.getJIPTImage(), width, height), false);
        }

        public Image grayscale_image(JIPTimage jipt_image, int width, int height)
        {
            jipt_image.setGrayscale(true);
            Image im     = jipt_image.getImage();

            int[] pix = new int[width*height];
	    PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

            //pg.startGrabbing();
	    try
	    {
	      pg.grabPixels();
	    }
            catch(Exception e)
	    {
	      // System.out.println(e.toString());
	    }
            pix = (int []) pg.getPixels();

            for(int i = 0; i < pix.length; i++)
	    {
    	        int alpha = (pix[i] >> 24) & 0xff;
    	        int red   = (pix[i] >> 16) & 0xff;
    	        int green = (pix[i] >>  8) & 0xff;
    	        int blue  = (pix[i]      ) & 0xff;

	        //System.out.println("Old = " + pix[i]);
                int gray_color = (red + green + blue)/3;

		pix[i] = 0;
		pix[i] += (alpha << 24);
		pix[i] += (gray_color     << 16); // r
		pix[i] += (gray_color   <<  8); // g
		pix[i] += (gray_color         ); // b
            }
            im = null;
            im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pix, 0, width));
            //image_frame.setImage(im);
            jipt_image.setImage(im);
            return im;
        }

        public static Image grayscale_image(Image im, int width, int height)
        {
            //jipt_image.setGrayscale(true);
            //Image im     = jipt_image.getImage();

            int[] pix = new int[width*height];
	    PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

            //pg.startGrabbing();
	    try
	    {
	      pg.grabPixels();
	    }
            catch(Exception e)
	    {
	      // System.out.println(e.toString());
	    }
            pix = (int []) pg.getPixels();

            for(int i = 0; i < pix.length; i++)
	    {
    	        int alpha = (pix[i] >> 24) & 0xff;
    	        int red   = (pix[i] >> 16) & 0xff;
    	        int green = (pix[i] >>  8) & 0xff;
    	        int blue  = (pix[i]      ) & 0xff;

	        //System.out.println("Old = " + pix[i]);
                int gray_color = (red + green + blue)/3;

		pix[i] = 0;
		pix[i] += (alpha << 24);
		pix[i] += (gray_color     << 16); // r
		pix[i] += (gray_color   <<  8); // g
		pix[i] += (gray_color         ); // b
            }
            im = null;
            im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pix, 0, width));
            //image_frame.setImage(im);
           // jipt_image.setImage(im);
            return im;
        }

        ////////////////////////
        //// Threshold /////////
        ////////////////////////
        public Image threshold_image(ImageFrame image_frame, int r, int g, int b, int threshold)
        {
            int w = image_frame.getImageWidth();
            int h = image_frame.getImageHeight();
            Image im = threshold_image(image_frame.getJIPTImage(), w, h, r, g, b, threshold);
            image_frame.setFrameImage(im, false);
            return im;
        }

        public Image threshold_image(JIPTimage jipt_image, int width, int height, int r, int g, int b, int threshold)
        {
            // r = red
            // g = green
            // b = blue
            // threshold = threshold
            // If a pixel's grayscale value is > threshold, it gets the
            // color of rgb.  Else, it gets assigned black
            jipt_image.setGrayscale(true);
            Image im     = jipt_image.getImage();
///            Image im = this.cloneImage(jipt_image);

            int[] pix = new int[width*height];
	    PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

            //pg.startGrabbing();
	    try
	    {
	      pg.grabPixels();
	    }
            catch(Exception e)
	    {
	      // System.out.println(e.toString());

	    }
            pix = (int []) pg.getPixels();
            float percent = 0;
            for(int i = 0; i < pix.length; i++)
	    {
    	        int alpha = (pix[i] >> 24) & 0xff;
    	        int red   = (pix[i] >> 16) & 0xff;
    	        int green = (pix[i] >>  8) & 0xff;
    	        int blue  = (pix[i]      ) & 0xff;

	        //System.out.println("Old = " + pix[i]);
                int new_color = (red + green + blue)/3;

               if(threshold > -1)
               {
                //////////////////////////////
                ////// Monochrome ////////////
                //////////////////////////////
                if(new_color <= threshold)
                  {
                  //System.out.println("Setting black");
                  red = 0;
                  green = 0;
                  blue = 0;
                  }
                else
                {
                  //System.out.println("Setting a color");
                  red = r;
                  green = g;
                  blue = b;
                }

                pix[i] = 0;
        		pix[i] += (alpha << 24);
        		pix[i] += (red     << 16); // r
        		pix[i] += (green   <<  8); // g
        		pix[i] += (blue         ); // b
                }
                else
                {
                    ////////////////////////////
                    //// "Color" grayscale ////
                    //////////////////////////

                    percent = new_color/255.0f;
                    //System.out.println("Percent = " + percent);
                    int nr = (int) (percent * r);
                    int ng = (int) (percent * g);
                    int nb = (int) (percent * b);
                    //System.out.println("r, b, g, = " + r + " " + b + " " +g);

        		pix[i] = 0;
        		pix[i] += (alpha << 24);
        		pix[i] += (nr     << 16); // r
        		pix[i] += (ng   <<  8); // g
        		pix[i] += (nb         ); // b
                }
            }
            //System.out.println("Percent = " + percent);
            //System.out.println("r, b, g, = " + r + " " + b + " " +g);
            im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pix, 0, width));
            //image_frame.setImage(im);
            jipt_image.setImage(im);
            return im;
        }

        public static Image threshold_image(Image im, int width, int height, int r, int g, int b, int threshold)
        {
            // r = red
            // g = green
            // b = blue
            // threshold = threshold
            // If a pixel's grayscale value is > threshold, it gets the
            // color of rgb.  Else, it gets assigned black
            //jipt_image.setGrayscale(true);
            //Image im     = jipt_image.getImage();
///            Image im = this.cloneImage(jipt_image);

            int[] pix = new int[width*height];
	    PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

            //pg.startGrabbing();
	    try
	    {
	      pg.grabPixels();
	    }
            catch(Exception e)
	    {
	      // System.out.println(e.toString());

	    }
            pix = (int []) pg.getPixels();
            float percent = 0;
            for(int i = 0; i < pix.length; i++)
	    {
    	        int alpha = (pix[i] >> 24) & 0xff;
    	        int red   = (pix[i] >> 16) & 0xff;
    	        int green = (pix[i] >>  8) & 0xff;
    	        int blue  = (pix[i]      ) & 0xff;

	        //System.out.println("Old = " + pix[i]);
                int new_color = (red + green + blue)/3;

               if(threshold > -1)
               {
                //////////////////////////////
                ////// Monochrome ////////////
                //////////////////////////////
                if(new_color <= threshold)
                  {
                  //System.out.println("Setting black");
                  red = 0;
                  green = 0;
                  blue = 0;
                  }
                else
                {
                  //System.out.println("Setting a color");
                  red = r;
                  green = g;
                  blue = b;
                }

                pix[i] = 0;
		pix[i] += (alpha << 24);
		pix[i] += (red     << 16); // r
		pix[i] += (green   <<  8); // g
		pix[i] += (blue         ); // b
                }
                else
                {
                    ////////////////////////////
                    //// "Color" grayscale ////
                    //////////////////////////

                    percent = new_color/255.0f;
                    //System.out.println("Percent = " + percent);
                    int nr = (int) (percent * r);
                    int ng = (int) (percent * g);
                    int nb = (int) (percent * b);
                    //System.out.println("r, b, g, = " + r + " " + b + " " +g);

        		pix[i] = 0;
        		pix[i] += (alpha << 24);
        		pix[i] += (nr     << 16); // r
        		pix[i] += (ng   <<  8); // g
        		pix[i] += (nb         ); // b
                }
            }
            //System.out.println("Percent = " + percent);
            //System.out.println("r, b, g, = " + r + " " + b + " " +g);
            im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pix, 0, width));
            //image_frame.setImage(im);
            //jipt_image.setImage(im);
            return im;
        }


        ////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////// Negative //////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////
        public void negative_image(ImageFrame image_frame)
        {

            //int   width  = image_frame.getImageWidth();
	        //int   height = image_frame.getImageHeight();
            //image_frame.setImage(negative_image(image_frame.getJIPTImage(), width, height));
            try
            {
                image_frame.addToUndo(this);
                NegativeFrame negative_frame = new NegativeFrame(this, image_frame);
                negative_frame.initComponents();
                negative_frame.setVisible(true);
            }
            catch(Exception e)
            {
              //System.out.println("Error in grayscale" + e.toString());
              e.printStackTrace();
            }

        }

        public Image negative_image(JIPTimage jipt_image, int width, int height,
                    boolean bool_r, boolean bool_g, boolean bool_b)
        {
            Image im     = jipt_image.getImage();
            int[] pix = new int[width*height];
	    PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

            //pg.startGrabbing();
	    try
	    {
	      pg.grabPixels();
	    }
            catch(Exception e)
	    {
	      // System.out.println(e.toString());
	    }
            pix = (int []) pg.getPixels();

            for(int i = 0; i < pix.length; i++)
	        {
    	        int alpha = (pix[i] >> 24) & 0xff;
    	        int red   = (pix[i] >> 16) & 0xff;
    	        int green = (pix[i] >>  8) & 0xff;
    	        int blue  = (pix[i]      ) & 0xff;

        	    //System.out.println("Old = " + pix[i]);
                if(bool_r)
                    red = -1*red + 255;
                if(bool_g)
                    green = -1*green + 255;
                if(bool_b)
                    blue = -1*blue + 255;

        		pix[i] = 0;
        		pix[i] += (alpha << 24);
        		pix[i] += ((red)     << 16); // r
        		pix[i] += ((green)   <<  8); // g
        		pix[i] += ((blue)         ); // b
            }
            im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width, height, pix, 0, width));
            //image_frame.setImage(im);
            jipt_image.setImage(im);
            return im;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////// Flip/Rotate ///////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////
        public void flip_rotate(ImageFrame selected_frame)
        {
            try
            {
                FlipRotateFrame flip_rotate_frame = new FlipRotateFrame(this, selected_frame);
                flip_rotate_frame.initComponents();
                flip_rotate_frame.setVisible(true);
            }
            catch(Exception e)
            {
              // System.out.println("Error in flip/rotate frame");
            }
        }

        ////////////////////////////////////////
        /// Rotate an image based on degrees ///
        ////////////////////////////////////////
        public void rotateImage(ImageFrame image_frame, float degrees)
        {
             Image im = image_frame.getJIPTImage().getImage();
             if(degrees % 360 == 0)
            {
                return;
            }

	    PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

		//pg.startGrabbing();
		try
		{
    		pg.grabPixels();
		}
	    catch(Exception e)
		{
			// System.out.println(e.toString());
		}

		int old_width  = image_frame.getImageWidth();
		int old_height = image_frame.getImageHeight();
		int[] pix = new int[old_width*old_height];

		// System.out.println("Old width, old height = " + old_width + " " + old_height);

		//System.out.println("old_width*Sin = " + Math.sin(45*Math.PI/180.0));

		double new_width_double  =   (Math.abs(old_width*Math.cos(degrees*Math.PI/180.0))
						      + Math.abs(old_height*Math.sin(degrees*Math.PI/180.0)));
		double new_height_double =   (Math.abs(old_width*Math.sin(degrees*Math.PI/180.0))
						      + Math.abs(old_height*Math.cos(degrees*Math.PI/180.0)));

                int new_width  = (int) new_width_double;
                int new_height = (int) new_height_double;

                if((Math.abs(new_height - new_height_double) > 0.001) || (Math.abs(new_width - new_width_double) > 0.001))
                {
                    // System.out.println("ROunding error");
                }
                int[] new_pix = new int[new_width * new_height];
		// System.out.println("new width, new height = " + new_width + " " + new_height);

                // Clear all nex pix to white
                for(int i = 0; i < new_pix.length; i++)
                {
                  new_pix[i] = 0;
		  new_pix[i] += (255 << 24);
		  new_pix[i] += ((255)     << 16); // r
		  new_pix[i] += ((255)   <<  8); // g
	          new_pix[i] += ((255)         ); // b
                  //new_pix[z] = 0x1f;
                  //System.out.println("New pix = " + new_pix);
                }
                int empty_color = 0;
                empty_color += (255 << 24);
		empty_color += ((255)     << 16); // r
                empty_color += ((255)   <<  8); // g
	        empty_color += ((255)         ); // b

		pix = (int []) pg.getPixels();


		if(degrees % 180 == 0)
		{
			// System.out.println("In 180");
			for(int i = 0; i < pix.length; i++)
			{
				int y = (i/old_width);
				int x = i - (y*old_width);

				y = new_height - 1 - y;
				x = new_width - 1 - x;
				//System.out.println("x,y = " + x + " " + y);
				new_pix[y*new_width + x] = pix[i];
			}

		}
		else if(degrees % 270 == 0)
		{
			// System.out.println("In 270");
			for(int i = 0; i < pix.length; i++)
			{
				int y = (i/old_width);
				int x = i - (y*old_width);
				int temp;

				temp = y;
				y    = x;
				x    = new_width - 1 - temp;

				new_pix[y*new_width + x] = pix[i];
			}

		}
		else if(degrees % 90 == 0)
		{
			// System.out.println("In 90");
			for(int i = 0; i < pix.length; i++)
			{
				int y = (i/old_width);
				int x = i - (y*old_width);

				int temp;

				temp = y;
				y    = new_height - x - 1;
				x    = temp;
				new_pix[y*new_width + x] = pix[i];
			}
		}
		else
		{
			// Angle is not a multiple of 90 degrees

                        //// !!!!!!!!!!! NEW WAY
                        for(int i = 0; i < new_pix.length; i++)
			{
				int y = (i/new_width);
				int x = i%new_width;

				// Convert y coord to cartesian coord
				y = new_height - y;

                                // Map coords to old image
				x = (old_width/2) - ((new_width/2) - x);
				y = (old_height/2) - ((new_height/2) - y);

                                // Orient coords around origin
				x = 0 - ((old_width/2) - x);
				y = 0 - ((old_height/2) - y);

                                // Rotate coords
				int old_x = x;
				int old_y = y;

				double x_d = (old_x*Math.cos(-1*degrees*Math.PI/180.0) - old_y*Math.sin(-1*degrees*Math.PI/180.0));
				double y_d = (old_x*Math.sin(-1*degrees*Math.PI/180.0) + old_y*Math.cos(-1*degrees*Math.PI/180.0));
                                x = (int) x_d;
                                y = (int) y_d;
                                /* System.out.println("" + (x_d - x));
                                if((Math.abs(x-x_d) > 1) ||
(Math.abs(y-y_d) > 1))
                                {
                                    System.out.println("Rounding error");
                                } */

				// Map coords to new image
                                x = (old_width/2) +  x;
				y = (old_height/2) + y;

                                // Convert Y back
                                y = old_height - y;

                                try
                                {
                                    if((x < 0) || (x > old_width) || (y < 0) || (y > old_height))
                                    {
                                        //System.out.println("Breaking");
                                        //break;
                                    }
                                    else
                                        new_pix[i] = pix[x + y*old_width];
                                }
                                catch(Exception e)
                                {
                                }

			}
                        /////// !!!!!!!!!!!! NEW WAY

                         /* // !!!!!!!!!!!!! OLD WAY
			for(int i = 0; i < pix.length; i++)
			{
				int y = (i/old_width);
				int x = i - (y*old_width);

				// Convert y coord to cartesian coord
				y = old_height - y;

				// Map coords to new image
				x = (new_width/2) - ((old_width/2) - x);
				y = (new_height/2) - ((old_height/2) - y);

				// Orient coords around origin
				x = 0 - ((new_width/2) - x);
				y = 0 - ((new_height/2) - y);


				// Rotate coords
				int old_x = x;
				int old_y = y;

				x = (int) (old_x*Math.cos(degrees*Math.PI/180.0) - old_y*Math.sin(degrees*Math.PI/180.0));
				y = (int) (old_x*Math.sin(degrees*Math.PI/180.0) + old_y*Math.cos(degrees*Math.PI/180.0));


				// Translate again
				x = (new_width/2) + x;
				y = (new_height/2) + y;

				//Convert y back to array coords
				y = new_height - y;

				try
				{

					new_pix[y*new_width + x] = pix[i];
				}
				catch(Exception e)
				{
				}
			}
                        !!!!!!!!!!!!!! */

		}

		im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(new_width, new_height, new_pix, 0, new_width));
                image_frame.setFrameImage(im, true);
        }


        //////////////////////
        // Flip Horizontal ///
        //////////////////////
        public void flipHorizontal(ImageFrame image_frame, boolean flipVertical)
        {
            Image im = image_frame.getJIPTImage().getImage();
            PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);
            try
    	    {
    	      pg.grabPixels();
    	    }
    	    catch(Exception e)
    	    {
    	      System.out.println("Error in flip horizontal " + e.toString());
    	    }
            try
            {
            int old_width  = image_frame.getImageWidth();
	    int old_height = image_frame.getImageHeight();
	    int[] pix = new int[old_width*old_height];
            pix = (int []) pg.getPixels();
            int[] new_pix = new int[old_width*old_height];
            for(int i = 0; i < pix.length; i++)
            {
              //System.out.println("In loop");
              int old_x = i%old_width;
              int old_y = i/old_width;

              int new_x = old_x*-1 + (old_width-1);
              new_pix[old_y*old_width + new_x] = pix[i];
            }
            im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(old_width, old_height, new_pix, 0, old_width));

            image_frame.setFrameImage(im, !flipVertical);
            }
            catch(Exception e1)
            {
              // System.out.println("Exception: " + e1.toString());
            }



        }

        ///////////////////////
        ///// Flip Vertical ///
        ///////////////////////
        public void flipVertical(ImageFrame image_frame)
        {
            Image im = image_frame.getJIPTImage().getImage();
            PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);
            try
    	    {
    	      pg.grabPixels();
    	    }
    	    catch(Exception e)
    	    {
    	      // System.out.println("Error in flip vertical " + e.toString());
    	    }
            try
            {
            int old_width  = image_frame.getImageWidth();
	    int old_height = image_frame.getImageHeight();
	    int[] pix = new int[old_width*old_height];
            pix = (int []) pg.getPixels();
            int[] new_pix = new int[old_width*old_height];
            for(int i = 0; i < pix.length; i++)
            {
              //System.out.println("In loop");
              int old_x = i%old_width;
              int old_y = i/old_width;

              int new_y = old_y*-1 + (old_height-1);
              new_pix[new_y*old_width + old_x] = pix[i];
            }
            im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(old_width, old_height, new_pix, 0, old_width));

            image_frame.setFrameImage(im, true);
            }
            catch(Exception e1)
            {
              System.out.println("Exception: " + e1.toString());
            }

        }


        ////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////// Resize ///////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////
	public void resize(ImageFrame selected_frame)
	{
		try
		{
			// Show dialog box to get resize
			ResizeFrame resize_frame = new ResizeFrame(this, selected_frame);
			resize_frame.initComponents();
			resize_frame.setVisible(true);

                    //resize_frame.setToolManager(this);
                    //resize_frame.setImageFrame(selected_frame);

			// Get the image from the frame

			// resize the image
		}
		catch(Exception e)
		{
			// System.out.println("Error in resize frame");
		}


	}

    //////////////////////////////////
    /// Resize an image based on % ///
    //////////////////////////////////
    public void resizeImage(ImageFrame image_frame, int r_width, int r_height)
	{
        Image im = image_frame.getJIPTImage().getImage();
        int RESIZE_RATIO_WIDTH  = r_width; // % resize in x direction
		int RESIZE_RATIO_HEIGHT = r_height; // % resize in y direction


		PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

		//pg.startGrabbing();
		try
		{
		pg.grabPixels();
		}
		catch(Exception e)
		{
			// System.out.println(e.toString());
		}

		int old_width  = image_frame.getImageWidth();
		int old_height = image_frame.getImageHeight();
		int[] pix = new int[old_width*old_height];

		//System.out.println("Old width, old height = " + old_width + " " + old_height);

		int new_width  = (int) (old_width*(RESIZE_RATIO_WIDTH/100.0));
		int new_height = (int) (old_height*(RESIZE_RATIO_HEIGHT/100.0));
		int[] new_pix = new int[new_width * new_height];
		//System.out.println("new width, new height = " + new_width + " " + new_height);


		pix = (int []) pg.getPixels();

		//System.out.println("pix.length = " + pix.length);
		int new_pix_length = new_pix.length;
		//System.out.println("new pix.length = " + new_pix.length);
		for(int i = 0; i < new_pix_length; i++)
		{
			int new_x_coord = i - (i/new_width)*new_width;
			int new_y_coord = (i/new_width);

			int old_x_coord = (int) ((new_x_coord)*(old_width)/((float) new_width));
			int old_y_coord = (int) ((new_y_coord)*(old_height)/((float) new_height));

			new_pix[new_x_coord + (new_y_coord * new_width)] = pix[old_x_coord + old_y_coord*old_width];

		}
		im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(new_width, new_height, new_pix, 0, new_width));

        image_frame.setFrameImage(im, true);
        //j_im.setImage(im);
        //return im;
	}

    //////////////////////////////////
    /// Resize an image based on %.  Also receives image width & height ///
    //////////////////////////////////
    public void resizeImage(JIPTimage jipt_image, int r_width, int r_height, int old_width, int old_height)
	{
        Image im = jipt_image.getImage();
        int RESIZE_RATIO_WIDTH  = r_width; // % resize in x direction
		int RESIZE_RATIO_HEIGHT = r_height; // % resize in y direction


		PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

		//pg.startGrabbing();
		try
		{
		pg.grabPixels();
		}
		catch(Exception e)
		{
			// System.out.println(e.toString());
		}

		int[] pix = new int[old_width*old_height];

		//System.out.println("Old width, old height = " + old_width + " " + old_height);

		int new_width  = (int) (old_width*(RESIZE_RATIO_WIDTH/100.0));
		int new_height = (int) (old_height*(RESIZE_RATIO_HEIGHT/100.0));
		int[] new_pix = new int[new_width * new_height];
		//System.out.println("new width, new height = " + new_width + " " + new_height);

        //get pixels from PixelGrabber
		pix = (int []) pg.getPixels();

		//System.out.println("pix.length = " + pix.length);
		int new_pix_length = new_pix.length;
		//System.out.println("new pix.length = " + new_pix.length);
		for(int i = 0; i < new_pix_length; i++)
		{
			int new_x_coord = i - (i/new_width)*new_width;
			int new_y_coord = (i/new_width);

			int old_x_coord = (int) ((new_x_coord)*(old_width)/((float) new_width));
			int old_y_coord = (int) ((new_y_coord)*(old_height)/((float) new_height));

			new_pix[new_x_coord + (new_y_coord * new_width)] = pix[old_x_coord + old_y_coord*old_width];

		}
		im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(new_width, new_height, new_pix, 0, new_width));

        //image_frame.setImage(im);
        jipt_image.setImage(im);
        //j_im.setImage(im);
        //return im;
	}

    public Image resizeImage(Image im, int r_width, int r_height, int old_width, int old_height)
    {
         int RESIZE_RATIO_WIDTH  = r_width; // % resize in x direction
		int RESIZE_RATIO_HEIGHT = r_height; // % resize in y direction


		PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);

		//pg.startGrabbing();
		try
		{
		pg.grabPixels();
		}
		catch(Exception e)
		{
			// System.out.println(e.toString());
		}

		int[] pix = new int[old_width*old_height];

		//System.out.println("Old width, old height = " + old_width + " " + old_height);

		int new_width  = (int) (old_width*(RESIZE_RATIO_WIDTH/100.0));
		int new_height = (int) (old_height*(RESIZE_RATIO_HEIGHT/100.0));
		int[] new_pix = new int[new_width * new_height];


        //get pixels from PixelGrabber
		pix = (int []) pg.getPixels();
		int new_pix_length = new_pix.length;
		for(int i = 0; i < new_pix_length; i++)
		{
			int new_x_coord = i - (i/new_width)*new_width;
			int new_y_coord = (i/new_width);

			int old_x_coord = (int) ((new_x_coord)*(old_width)/((float) new_width));
			int old_y_coord = (int) ((new_y_coord)*(old_height)/((float) new_height));

			new_pix[new_x_coord + (new_y_coord * new_width)] = pix[old_x_coord + old_y_coord*old_width];

		}
		im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(new_width, new_height, new_pix, 0, new_width));
        return im;
    }

    ////////////////////////////////////
    ////// COUNT UNIQUE COLORS (James)
    ////////////////////////////////////
    public int countUniqueColors(ImageFrame img_frame)
    {
        Image im = img_frame.getJIPTImage().getImage();
        int im_width = im.getWidth(main_frame);
        int im_height = im.getHeight(main_frame);

        PixelGrabber pg = new PixelGrabber(im, 0, 0, -1, -1, true);
 		//pg.startGrabbing();
		try
		{
    		pg.grabPixels();
		}
		catch(Exception e)
		{
			// System.out.println(e.toString());
		}

        //create data array
		int[] pix = new int[im_width*im_height];

        //get Pixels from PixelGrabber
        pix = (int []) pg.getPixels();

        //quicksort pixel array
        JIPTUtilities.quicksort(pix);

        //initialize color counter
        int count = 1;


        //get first color
        int toMatch = pix[0];

        //begin traversing image data array
        for(int i = 1; i < pix.length; i++)
        {
            if( pix[i] != toMatch )
            {
                //count this color as unique
                count++;

                //set toMatch to new pixel
                toMatch=pix[i];

            }//end if
        }

        ////THE FOLLOWING TWO LINES DISPLAY THE SORTED IMAGE
		//im = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(im_width, im_height, pix, 0, im_width));
        //img_frame.setFrameImage(im, true);

        return count;
    }//end countUniqueColors

    //////////////////////////////
    //// Get Image Dimensions ////
    //////////////////////////////
    public static Dimension getImageDimensions( Image i )
    {
        //returns the width and height of Image I as a Dimension object
        return new Dimension(i.getWidth(main_frame), i.getHeight(main_frame) );
    }

}
