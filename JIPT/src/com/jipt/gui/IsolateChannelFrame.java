package com.jipt.gui;

/*
    IsolateChannelFrame
    Trent Lucier

    This frame is used when the user wishes to convert an image to grayscale
    using the r, g, b, or a values as the intensity.

 */

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

import com.jipt.JIPTimage;
import com.jipt.ToolManager;

public class IsolateChannelFrame extends ImagePreviewFrame
{

    ToolManager tool_manager = null;
    MainFrame   main_frame = null;
    ImageFrame  image_frame  = null;

    JIPTimage  original_image     = null;
    JIPTimage  altered_image      = null;
    int original_height;
    int original_width;
    int p_height;       // Height & Width of preview images
    int p_width;

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel colorLabel = new javax.swing.JLabel();
	javax.swing.JRadioButton redRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton greenRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton blueRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton allRadio = new javax.swing.JRadioButton();
// END GENERATED CODE

	public IsolateChannelFrame(ToolManager tm, ImageFrame i_frame, MainFrame mf)
	{
        main_frame   = mf;
        tool_manager = tm;
        image_frame = i_frame;
	}

	public void initComponents() throws Exception
	{
        super.initComponents();
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		colorLabel.setSize(new java.awt.Dimension(60, 20));
		colorLabel.setVisible(true);
		colorLabel.setText("Channel");
		colorLabel.setLocation(new java.awt.Point(180, 240));

		redRadio.setSize(new java.awt.Dimension(95, 20));
		redRadio.setVisible(true);
		redRadio.setText("Red");
		redRadio.setLocation(new java.awt.Point(180, 270));

		greenRadio.setSize(new java.awt.Dimension(72, 20));
		greenRadio.setVisible(true);
		greenRadio.setText("Green");
		greenRadio.setLocation(new java.awt.Point(180, 300));

		blueRadio.setSize(new java.awt.Dimension(67, 20));
		blueRadio.setVisible(true);
		blueRadio.setText("Blue");
		blueRadio.setLocation(new java.awt.Point(180, 330));

		allRadio.setSize(new java.awt.Dimension(85, 20));
		allRadio.setVisible(true);
		allRadio.setText("All");
		allRadio.setLocation(new java.awt.Point(180, 360));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("Split Channels");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(colorLabel);
		getContentPane().add(redRadio);
		getContentPane().add(greenRadio);
		getContentPane().add(blueRadio);
		getContentPane().add(allRadio);

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.isolationScaleColorMenuItem.getIcon();
        this.setIconImage(ii.getImage());


		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE
        //////////////////////
        /// Set the images ///
        //////////////////////
        Image i = image_frame.getJIPTImage().getImage();
        original_image = new JIPTimage(i);

        if( original_image.getImage().getWidth(this) > original_image.getImage().getHeight(this))
                original_image.setImage( original_image.getImage().getScaledInstance( originalImageLabel.getWidth(), -1, Image.SCALE_FAST ));
        else original_image.setImage( original_image.getImage().getScaledInstance( -1, originalImageLabel.getHeight(), Image.SCALE_FAST ));

        setOriginalImage(original_image.getImage());
        p_width  = original_image.getImage().getWidth(originalLabel);
        p_height = original_image.getImage().getHeight(originalLabel);

        // Width and height of thhe full size image
        original_height = image_frame.getJIPTImage().getImage().getHeight(this);
        original_width  = image_frame.getJIPTImage().getImage().getWidth(this);


        okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				okButtonActionPerformed(e);
			}
		});
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				cancelButtonActionPerformed(e);
			}
		});

        ButtonGroup bg = new ButtonGroup();
        bg.add(allRadio);
        bg.add(redRadio);
        bg.add(blueRadio);
        bg.add(greenRadio);
        redRadio.setSelected(true);

        redRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAlteredImage();
			}
		});

        greenRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAlteredImage();
			}
		});

        blueRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAlteredImage();
			}
		});

        allRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAlteredImage();
			}
		});

        updateAlteredImage();

	}

  	private boolean mShown = false;

	public void addNotify()
	{
		super.addNotify();

		if (mShown)
			return;

		// resize frame to account for menubar
		JMenuBar jMenuBar = getJMenuBar();
		if (jMenuBar != null) {
			int jMenuBarHeight = jMenuBar.getPreferredSize().height;
			Dimension dimension = getSize();
			dimension.height += jMenuBarHeight;
			setSize(dimension);
		}

		mShown = true;
	}

	// Close the window when the close box is clicked
	void thisWindowClosing(java.awt.event.WindowEvent e)
	{
		setVisible(false);
		dispose();
		//System.exit(0);
	}

	public void okButtonActionPerformed(java.awt.event.ActionEvent e)
	{
            String original_name = ""; //image_frame.getDocTitle();
            String iname = "";
            if(redRadio.isSelected())
            {

                Image red_image = tool_manager.isolate(image_frame.getJIPTImage().getImage(), original_width, original_height, 0);
                /////////////////////////////////
                //// Create Red Image ///////////
                /////////////////////////////////
                ImageFrame red_frame = main_frame.createNewImage(red_image);
                iname = original_name + "Red";
                if(!main_frame.isUniqueName(iname))
                {
                    int count = 1;
                    do
                    {
                        iname = original_name + "Red" + count;
                        count++;
                    } while(!main_frame.isUniqueName(iname));
                }
                // System.out.println("Setting name " + iname);
                red_frame.setTitle(iname);

                int frame_count = main_frame.getNumFrames();

                String zero = "";
                if(frame_count < 10)
                    zero = "0";

                red_frame.setDocTitle(zero + frame_count + " " + "..\\" + iname);
                red_frame.setFilename("..\\" + iname);
                main_frame.recreateMenuBar();
            }
            else if(greenRadio.isSelected())
            {
                Image green_image = tool_manager.isolate(image_frame.getJIPTImage().getImage(), original_width, original_height, 1);
                ////////////////////////////////////
                ////////// Create Green Image //////
                ////////////////////////////////////
                ImageFrame green_frame = main_frame.createNewImage(green_image);
                iname = original_name + "Green";
                if(!main_frame.isUniqueName(iname))
                {
                    int count = 1;
                    do
                    {
                        iname = original_name + "Green" + count;
                        count++;
                    } while(!main_frame.isUniqueName(iname));
                }
                // System.out.println("Setting name " + iname);
                green_frame.setTitle(iname);

                int frame_count = main_frame.getNumFrames();
                String zero = "";
                if(frame_count < 10)
                    zero = "0";

                green_frame.setDocTitle(zero + frame_count + " " + "..\\" + iname);
                green_frame.setFilename("..\\" + iname);
                main_frame.recreateMenuBar();
            }
            else if(blueRadio.isSelected())
            {

                ////////////////////////////////////
                ////////// Create Blue Image //////
                ////////////////////////////////////
                Image blue_image = tool_manager.isolate(image_frame.getJIPTImage().getImage(), original_width, original_height, 2);
                ImageFrame blue_frame = main_frame.createNewImage(blue_image);
                iname = original_name + "Blue";
                if(!main_frame.isUniqueName(iname))
                {
                    int count = 1;
                    do
                    {
                        iname = original_name + "Blue" + count;
                        count++;
                    } while(!main_frame.isUniqueName(iname));
                }
                // System.out.println("Setting name " + iname);
                blue_frame.setTitle(iname);

                int frame_count = main_frame.getNumFrames();
                String zero = "";
                if(frame_count < 10)
                    zero = "0";

                blue_frame.setDocTitle(zero + frame_count + " " + "..\\" + iname);
                blue_frame.setFilename("..\\" + iname);
                main_frame.recreateMenuBar();
            }
            if(allRadio.isSelected())
            {
                // Create a new image for all channels
                Image red_image = tool_manager.isolate(image_frame.getJIPTImage().getImage(), original_width, original_height, 0);
                Image green_image = tool_manager.isolate(image_frame.getJIPTImage().getImage(), original_width, original_height, 1);
                Image blue_image = tool_manager.isolate(image_frame.getJIPTImage().getImage(), original_width, original_height, 2);

                /////////////////////////////////
                //// Create Red Image ///////////
                /////////////////////////////////
                ImageFrame red_frame = main_frame.createNewImage(red_image);
                iname = original_name + "Red";
                if(!main_frame.isUniqueName(iname))
                {
                    int count = 1;
                    do
                    {
                        iname = original_name + "Red" + count;
                        count++;
                    } while(!main_frame.isUniqueName(iname));
                }
                // System.out.println("Setting name " + iname);
                red_frame.setTitle(iname);

                int frame_count = main_frame.getNumFrames();
                String zero = "";
                if(frame_count < 10)
                    zero = "0";
                red_frame.setDocTitle(zero + frame_count + " " + "..\\" + iname);
                red_frame.setFilename("..\\" + iname);
                main_frame.recreateMenuBar();

                ////////////////////////////////////
                ////////// Create Green Image //////
                ////////////////////////////////////
                ImageFrame green_frame = main_frame.createNewImage(green_image);
                iname = original_name + "Green";
                if(!main_frame.isUniqueName(iname))
                {
                    int count = 1;
                    do
                    {
                        iname = original_name + "Green" + count;
                        count++;
                    } while(!main_frame.isUniqueName(iname));
                }
                // System.out.println("Setting name " + iname);
                green_frame.setTitle(iname);

                frame_count = main_frame.getNumFrames();
                zero = "";
                if(frame_count < 10)
                    zero = "0";
                green_frame.setDocTitle(zero + frame_count + " " + "..\\" + iname);
                green_frame.setFilename("..\\" + iname);
                main_frame.recreateMenuBar();

                ////////////////////////////////////
                ////////// Create Blue Image //////
                ////////////////////////////////////
                ImageFrame blue_frame = main_frame.createNewImage(blue_image);
                iname = original_name + "Blue";
                if(!main_frame.isUniqueName(iname))
                {
                    int count = 1;
                    do
                    {
                        iname = original_name + "Blue" + count;
                        count++;
                    } while(!main_frame.isUniqueName(iname));
                }
                // System.out.println("Setting name " + iname);
                blue_frame.setTitle(iname);

                frame_count = main_frame.getNumFrames();
                zero = "";
                if(frame_count < 10)
                    zero = "0";

                blue_frame.setDocTitle(zero + frame_count + " " + "..\\" + iname);
                blue_frame.setFilename("..\\" + iname);
                main_frame.recreateMenuBar();
            }
            thisWindowClosing(null);
	}



	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

    public void updateAlteredImage()
    {
            int color = 0;

            if(redRadio.isSelected())
                color = 0;
            else if(greenRadio.isSelected())
                color = 1;
            else if(blueRadio.isSelected())
                color = 2;
            else
                return; // User has selected ALL.  Don't do put an image in the preview
            Image new_image = tool_manager.isolate(original_image.getImage(), p_width, p_height, color);
            this.setAlteredImage(new_image);
    }

}
