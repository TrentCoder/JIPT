package com.jipt.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.PixelGrabber;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.jipt.JIPTUtilities;
import com.jipt.JIPTsettings;

/**
 * Title:        Java Image Processing Toolkit
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      cis480
 * @author Trent Lucier, James LaTour, Victor Rego
 * @version 1.0
 */

public class HistogramFrame extends JInternalFrame
{
    private static FrameManager frame_manager = null;
    private ImageFrame owner = null;

	int new_w, new_h;

	private int red_count[] = new int[256];
	private int blue_count[] = new int[256];
	private int green_count[] = new int[256];
    private int gray_count[] = new int[256];

	int total_pixels = 0;

    JCheckBox redCheckBox = null;
    JCheckBox greenCheckBox = null;
    JCheckBox blueCheckBox = null;
    JCheckBox grayscaleCheckBox = null;
    JButton okButton = null;
    HistogramPanel graph = null;
    JLabel highestValueLabel = null;


    public HistogramFrame( FrameManager fm, ImageFrame img_frame )
    {
        super();
        frame_manager = fm;
        owner = img_frame;

        this.setSize(300,200);
        this.setVisible( false );
        this.setResizable(true);

        this.setTitle("Histogram");
        this.setFrameIcon( new ImageIcon(JIPTsettings.GRAPHICS_PATH+"graph.gif") );
        this.setEnabled(true);
        this.setMinimumSize(new Dimension(300, 200));
        this.setPreferredSize(new Dimension(300, 200));
        this.setDoubleBuffered(true);
        this.setBorder( BorderFactory.createLineBorder(Color.black) );

        //red check box
        redCheckBox = new JCheckBox("Red", true);
        this.setBackground( redCheckBox.getBackground() );
        redCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                redCheckBox_actionPerformed(e);
            }
        });

        //green check box
        greenCheckBox = new JCheckBox("Green", true);
        greenCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                greenCheckBox_actionPerformed(e);
            }
        });

        //blue check box
        blueCheckBox = new JCheckBox("Blue", true);
        blueCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                blueCheckBox_actionPerformed(e);
            }
        });

        //grayscale box
        grayscaleCheckBox = new JCheckBox("Grayscale", false);
        grayscaleCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                grayscaleCheckBox_actionPerformed(e);
            }
        });

        okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(60, 25));
        okButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okButton_actionPerformed(e);
            }
        });

        graph = new HistogramPanel(red_count, green_count, blue_count, gray_count);
        graph.setBorder( new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(142, 142, 142)) );
        graph.setPreferredSize( new Dimension(200, 100) );
        graph.setBackground( Color.white );

        /////////////////////////////
        //// Highest value label ////
        /////////////////////////////
        highestValueLabel = new JLabel("Highest: 0");
        highestValueLabel.setSize(40, 20);
        highestValueLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        highestValueLabel.setVisible(true);


        this.getContentPane().setLayout( new GridBagLayout() );

        this.getContentPane().add(highestValueLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
           ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        this.getContentPane().add(redCheckBox,          new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(greenCheckBox,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(grayscaleCheckBox,           new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(okButton,       new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        this.getContentPane().add(graph,          new GridBagConstraints(1, 1, 1, 3, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(blueCheckBox,     new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        /*this.getContentPane().add(redCheckBox,          new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(greenCheckBox,     new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(grayscaleCheckBox,           new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(okButton,       new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        this.getContentPane().add(graph,          new GridBagConstraints(1, 0, 1, 3, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(blueCheckBox,     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));*/




    }

    public void show()
    {
        super.show();
        updateGraph();
        this.setVisible( true );
    }

    /////////////////////////////////////////////////////////////////////////
    //////////////////////// Checkbox Selected //////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    void redCheckBox_actionPerformed(ActionEvent e)
    {
        updateGraph();
    }

    void greenCheckBox_actionPerformed(ActionEvent e)
    {
        updateGraph();
    }

    void blueCheckBox_actionPerformed(ActionEvent e)
    {
        updateGraph();
    }

    void grayscaleCheckBox_actionPerformed(ActionEvent e)
    {
      updateGraph();
    }

    ///////////////////////////////////////////////////////////////////////
    /////////////////////////// Repaints the graph ////////////////////////
    ///////////////////////////////////////////////////////////////////////
    public void updateGraph()
    {
        calculateColorDistribution();
        graph.setShowBlue(blueCheckBox.isSelected());
        graph.setShowRed(redCheckBox.isSelected());
        graph.setShowGreen(greenCheckBox.isSelected());
        graph.setShowGray(grayscaleCheckBox.isSelected());
        graph.repaint();
    }

    void okButton_actionPerformed(ActionEvent e)
    {
        ImageFrame im_frame = frame_manager.getFrame( frame_manager.getInfoFrameFilename() );
        im_frame.setShowHistogram(false);
        this.setVisible( false );
        owner.setVisible(true);
        try
        {
            owner.setSelected(true);
        }
        catch(Exception exp)
        {
            // System.out.println("Exception: " + exp.toString() );
        }
    }

	// Count up the distribution of colors
	public void calculateColorDistribution()
	{
//        Image im = frame_manager.getFrame( frame_manager.getInfoFrameFilename() ).getJIPTImage().getImage();
        Image im = owner.getJIPTImage().getImage();

        //create histogram
		for(int i = 0; i < 256; i++)
		{
			red_count[i] = 0;
			green_count[i] = 0;
			blue_count[i] = 0;
            gray_count[i] = 0;
		}

        int[] pix = new int[im.getWidth(this) * im.getHeight(this)];
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

		total_pixels = pix.length;

		//System.out.println("pix.length = " + pix.length);
		for(int i = 0; i < total_pixels; i++)
		{
			int alpha = (pix[i] >> 24) & 0xff;
			int red   = (pix[i] >> 16) & 0xff;
			int green = (pix[i] >>  8) & 0xff;
			int blue  = (pix[i]      ) & 0xff;
            int gray = (red + green + blue)/3;
		 	//System.out.println(green);
            gray_count[gray]   = gray_count[gray]   + 1;
		 	red_count[red]     = red_count[red]     + 1;
		 	blue_count[blue]   = blue_count[blue]   + 1;
		 	green_count[green] = green_count[green] + 1;

		}


	}

    /////////////////////////////////////////////////
    ///////////// Internal histogram class //////////
    /////////////////////////////////////////////////
    class HistogramPanel extends JPanel
    {
        int r[] = new int[256];
        int g[] = new int[256];
        int b[] = new int[256];
        int gray[] = new int[256];

        boolean show_red;
        boolean show_blue;
        boolean show_green;
        boolean show_gray;

        HistogramPanel(int ra[], int ga[], int ba[], int gray_arr[])
        {
            r = ra;
            g = ga;
            b = ba;
            gray = gray_arr;
        }

        public void setShowRed(boolean b)
        {
            show_red = b;
        }

        public void setShowBlue(boolean b)
        {
            show_blue = b;
        }

        public void setShowGreen(boolean b)
        {
            show_green = b;
        }

        public void setShowGray(boolean b)
        {
            show_gray = b;
        }

        public void paint(Graphics g)
    	{
    		this.update(g);
    	}


        public void update(Graphics g)
	    {
        	int x_origin = 1;
    		int y_origin = 1;

    		int graph_width = this.getWidth();
    		int graph_height = this.getHeight();

            //// Erase background with white ////
            g.setColor(Color.white);
            g.fillRect(0,0,graph_width, graph_height);

    		int highest_g = 0;
    		int highest_r = 0;
    		int highest_b = 0;
            int highest_gray = 0;

    		for(int i = 0; i < 256; i++)
    		{
    			if(green_count[i] > highest_g)
    				highest_g = green_count[i];

    			if(red_count[i] > highest_r)
    				highest_r = red_count[i];

    			if(blue_count[i] > highest_b)
    				highest_b = blue_count[i];

                if(gray_count[i] > highest_gray)
                    highest_gray = gray_count[i];
    		}

    		int old_y_g = y_origin;
    		int old_x_g = x_origin;

        	int g_x_points[] = new int[256];
        	int g_y_points[] = new int[256];

        	int r_x_points[] = new int[256];
        	int r_y_points[] = new int[256];

        	int b_x_points[] = new int[256];
        	int b_y_points[] = new int[256];

        	int gray_x_points[] = new int[256];
        	int gray_y_points[] = new int[256];


            ///////////////////////////////////////////////////
            /// Get the average value of each color ///////////
            ///////////////////////////////////////////////////

            double avg_red   = 0;
            double avg_green = 0;
            double avg_blue  = 0;
            double avg_gray  = 0;

            double total_red = 0;
            double total_green = 0;
            double total_blue = 0;
            double total_gray = 0;

            for(int x = 0; x < red_count.length; x++)
            {
                avg_red   += x*red_count[x];
                total_red += red_count[x];

                avg_green += x*green_count[x];
                total_green += green_count[x];

                avg_blue  += x*blue_count[x];
                total_blue += blue_count[x];

                avg_gray  += x*gray_count[x];
                total_gray += gray_count[x];
            }

            avg_red   = avg_red/(total_red);
            avg_green = avg_green/(total_green);
            avg_blue  = avg_blue/(total_blue);
            avg_gray  = avg_gray/(total_gray);

            ///////////////////////////////////////////////////////////////////////////
            /////// Find the highest # of occurences.  This is used as the        /////
            ////// denominator when calculating the frequency of the other colors /////
            ///////////////////////////////////////////////////////////////////////////
            int freq[] = new int[4];
            int count = 0;
            if(show_red)
            {
                freq[count] = highest_r;
                count++;
            }

            if(show_green)
            {
                freq[count] = highest_g;
                count++;
            }

            if(show_blue)
            {
                freq[count] = highest_b;
                count++;
            }

            if(show_gray)
            {
                freq[count] = highest_gray;
                count++;
            }

            if(count > 0)
            {
                JIPTUtilities.quicksort(freq, 0, count-1);

                int highest = freq[count-1];

                highestValueLabel.setText("Highest : " + highest);

                //System.out.println("Highest: " + highest);
                // Only divide if that value is show

                ///////////////////////////////////////////////////
                ////////// Calculate the points for the graph /////
                ///////////////////////////////////////////////////
            	for(int i = 0; i < 256; i++)
            		{


            			g_x_points[i] = x_origin - 1 +(int)( i/(256.0/graph_width));
            			g_y_points[i] = y_origin + (int) ((green_count[i]*1.0/highest)*graph_height);

            			r_x_points[i] = x_origin - 1+(int)( i/(256.0/graph_width));
            			r_y_points[i] = y_origin + (int) ((red_count[i]*1.0/highest)*graph_height);

            			b_x_points[i] = x_origin - 1 +(int)( i/(256.0/graph_width));
            			b_y_points[i] = y_origin + (int) ((blue_count[i]*1.0/highest)*graph_height);

                        gray_x_points[i] = x_origin - 1 +(int)( i/(256.0/graph_width));
            			gray_y_points[i] = y_origin + (int) ((gray_count[i]*1.0/highest)*graph_height);

                        /// In order to flip the graph
                        g_y_points[i] = g_y_points[i]*-1 + graph_height;
                        gray_y_points[i] = gray_y_points[i]*-1 + graph_height;
                        b_y_points[i] = b_y_points[i]*-1 + graph_height;
                        r_y_points[i] = r_y_points[i]*-1 + graph_height;

            		}

                    if(show_green)
                    {
            		    g.setColor(Color.green);
            		    g.drawPolyline(g_x_points, g_y_points, 256);
                    }

                    if(show_blue)
                    {
            		    g.setColor(Color.blue);
            		    g.drawPolyline(b_x_points, b_y_points, 256);
                    }

                    if(show_red)
                    {
            		    g.setColor(Color.red);
            		    g.drawPolyline(r_x_points, r_y_points, 256);
                    }

                    if(show_gray)
                    {
                        // System.out.println("Showing gray points");
            		    g.setColor(Color.black);
            		    g.drawPolyline(gray_x_points, gray_y_points, 256);
                    }
            }
            else
            {
                // None selected
                highestValueLabel.setText("No channels selected");
            }
    	}


        }

}
