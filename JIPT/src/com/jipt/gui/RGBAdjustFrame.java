package com.jipt.gui;
/*
	This class is used for adjusting the RGB channels in an image.

    12/5/2001
*/


import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jipt.ColorManager;
import com.jipt.JIPTimage;

public class RGBAdjustFrame extends ImagePreviewFrame
{
    ColorManager color_manager = null;
    ImageFrame   image_frame   = null;

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
	javax.swing.JLabel redLabel = new javax.swing.JLabel();
	javax.swing.JLabel greenLabel = new javax.swing.JLabel();
	javax.swing.JLabel blueLabel = new javax.swing.JLabel();
	javax.swing.JSlider redSlider = new javax.swing.JSlider();
	javax.swing.JSlider greenSlider = new javax.swing.JSlider();
	javax.swing.JSlider blueSlider = new javax.swing.JSlider();
	javax.swing.JLabel redValueLabel = new javax.swing.JLabel();
	javax.swing.JLabel greenValueLabel = new javax.swing.JLabel();
	javax.swing.JLabel blueValueLabel = new javax.swing.JLabel();
// END GENERATED CODE

	public RGBAdjustFrame(ColorManager cm, ImageFrame i_frame)
	{
        color_manager = cm;
        image_frame = i_frame;
	}

	public void initComponents() throws Exception
	{
     super.initComponents();

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		redLabel.setSize(new java.awt.Dimension(62, 20));
		redLabel.setVisible(true);
		redLabel.setText("Red");
		redLabel.setLocation(new java.awt.Point(50, 280));

		greenLabel.setSize(new java.awt.Dimension(60, 20));
		greenLabel.setVisible(true);
		greenLabel.setText("Green");
		greenLabel.setLocation(new java.awt.Point(50, 310));

		blueLabel.setSize(new java.awt.Dimension(60, 20));
		blueLabel.setVisible(true);
		blueLabel.setText("Blue");
		blueLabel.setLocation(new java.awt.Point(50, 340));

		redSlider.setSize(new java.awt.Dimension(200, 16));
		redSlider.setVisible(true);
		redSlider.setValue(0);
		redSlider.setMinimum(-100);
		redSlider.setMajorTickSpacing(10);
		redSlider.setLocation(new java.awt.Point(130, 280));

		greenSlider.setSize(new java.awt.Dimension(200, 20));
		greenSlider.setVisible(true);
		greenSlider.setValue(0);
		greenSlider.setMinimum(-100);
		greenSlider.setMajorTickSpacing(10);
		greenSlider.setLocation(new java.awt.Point(130, 310));

		blueSlider.setSize(new java.awt.Dimension(200, 20));
		blueSlider.setVisible(true);
		blueSlider.setValue(0);
		blueSlider.setMinimum(-100);
		blueSlider.setMajorTickSpacing(10);
		blueSlider.setLocation(new java.awt.Point(130, 340));

		redValueLabel.setSize(new java.awt.Dimension(42, 20));
		redValueLabel.setVisible(true);
		redValueLabel.setText("0");
		redValueLabel.setLocation(new java.awt.Point(350, 280));

		greenValueLabel.setSize(new java.awt.Dimension(40, 20));
		greenValueLabel.setVisible(true);
		greenValueLabel.setText("0");
		greenValueLabel.setLocation(new java.awt.Point(350, 310));

		blueValueLabel.setSize(new java.awt.Dimension(40, 20));
		blueValueLabel.setVisible(true);
		blueValueLabel.setText("0");
		blueValueLabel.setLocation(new java.awt.Point(350, 340));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("Red/Green/Blue");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(redLabel);
		getContentPane().add(greenLabel);
		getContentPane().add(blueLabel);
		getContentPane().add(redSlider);
		getContentPane().add(greenSlider);
		getContentPane().add(blueSlider);
		getContentPane().add(redValueLabel);
		getContentPane().add(greenValueLabel);
		getContentPane().add(blueValueLabel);

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.rgbAdjustColorMenuItem.getIcon();
        this.setIconImage(ii.getImage());


		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE

        redSlider.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent e)
                {
                    updateRedSlider();
                }
            });

        greenSlider.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent e)
                {
                    updateGreenSlider();
                }
            });

        blueSlider.addChangeListener(new ChangeListener()
            {
                public void stateChanged(ChangeEvent e)
                {
                    updateBlueSlider();
                }
            });

        ////////////////////////////////
        //// Add action listeners //////
        ////////////////////////////////
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

        // Width and height of the full size image
        original_height = image_frame.getJIPTImage().getImage().getHeight(this);
        original_width  = image_frame.getJIPTImage().getImage().getWidth(this);

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
        int red        = redSlider.getValue();
        int green      = greenSlider.getValue();
        int blue       = blueSlider.getValue();

        Image new_image = color_manager.rgb_adjust(image_frame.getJIPTImage().getImage(), original_width, original_height,
                                            red, green, blue);

        image_frame.setFrameImage(new_image, true);
        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

    public void updateRedSlider()
    {
        redValueLabel.setText(redSlider.getValue() + "");
        updateAlteredImage();
    }

    public void updateGreenSlider()
    {
        greenValueLabel.setText(greenSlider.getValue() + "");
        updateAlteredImage();
    }

    public void updateBlueSlider()
    {
        blueValueLabel.setText(blueSlider.getValue() + "");
        updateAlteredImage();
    }

    public void updateAlteredImage()
    {
        int red        = redSlider.getValue();
        int green      = greenSlider.getValue();
        int blue       = blueSlider.getValue();

        Image new_image = color_manager.rgb_adjust(original_image.getImage(), p_width, p_height,
                                            red, green, blue);

         this.setAlteredImage(new_image);
    }

}
