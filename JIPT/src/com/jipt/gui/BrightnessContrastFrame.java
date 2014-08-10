package com.jipt.gui;
/*
    BrightnessContrastFrame
    This class is used for presenting the GUI that lets the user alter
    the brightness or contrast.

    Trent Lucier
    12/1/2001
*/

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jipt.ColorManager;
import com.jipt.JIPTimage;

public class BrightnessContrastFrame extends ImagePreviewFrame
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
	javax.swing.JLabel brightnessLabel = new javax.swing.JLabel();
	javax.swing.JLabel contrastLabel = new javax.swing.JLabel();
	javax.swing.JSlider brightnessSlider = new javax.swing.JSlider();
	javax.swing.JSlider contrastSlider = new javax.swing.JSlider();
	javax.swing.JLabel brightnessValueLabel = new javax.swing.JLabel();
	javax.swing.JLabel contrastValueLabel = new javax.swing.JLabel();
// END GENERATED CODE




	public BrightnessContrastFrame(ColorManager cm, ImageFrame i_f)
	{
        image_frame = i_f;
        color_manager = cm;
	}

	public void initComponents() throws Exception
	{
        super.initComponents();
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		brightnessLabel.setSize(new java.awt.Dimension(90, 20));
		brightnessLabel.setVisible(true);
		brightnessLabel.setText("Brightness  %");
		brightnessLabel.setLocation(new java.awt.Point(40, 250));

		contrastLabel.setSize(new java.awt.Dimension(90, 20));
		contrastLabel.setVisible(true);
		contrastLabel.setText("Contrast  %");
		contrastLabel.setLocation(new java.awt.Point(40, 300));

		brightnessSlider.setSize(new java.awt.Dimension(220, 16));
		brightnessSlider.setVisible(true);
		brightnessSlider.setValue(0);
		brightnessSlider.setMinimum(-100);
		brightnessSlider.setMajorTickSpacing(10);
		brightnessSlider.setLocation(new java.awt.Point(150, 255));

		contrastSlider.setSize(new java.awt.Dimension(220, 16));
		contrastSlider.setVisible(true);
		contrastSlider.setValue(0);
		contrastSlider.setMinimum(-100);
		contrastSlider.setMajorTickSpacing(10);
		contrastSlider.setLocation(new java.awt.Point(150, 305));

		brightnessValueLabel.setSize(new java.awt.Dimension(50, 20));
		brightnessValueLabel.setVisible(true);
		brightnessValueLabel.setText("0");
		brightnessValueLabel.setLocation(new java.awt.Point(390, 250));

		contrastValueLabel.setSize(new java.awt.Dimension(50, 20));
		contrastValueLabel.setVisible(true);
		contrastValueLabel.setText("0");
		contrastValueLabel.setLocation(new java.awt.Point(390, 300));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("Brightness/Contrast");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(brightnessLabel);
		getContentPane().add(contrastLabel);
		getContentPane().add(brightnessSlider);
		getContentPane().add(contrastSlider);
		getContentPane().add(brightnessValueLabel);
		getContentPane().add(contrastValueLabel);


		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.brightnessContrastColorMenuItem.getIcon();
        this.setIconImage(ii.getImage());

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

        contrastSlider.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
            updateContrastSlider();
          }
        });

        brightnessSlider.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
            updateBrightnessSlider();
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
        int brightness = brightnessSlider.getValue();
        int contrast   = contrastSlider.getValue();

        Image new_image  = color_manager.brightness(image_frame.getJIPTImage().getImage(), original_width, original_height, brightness);
        Image new_image2 = color_manager.contrast(new_image, original_width, original_height, contrast);
        image_frame.setFrameImage(new_image2, true);
        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

    public void updateBrightnessSlider()
    {
        brightnessValueLabel.setText(brightnessSlider.getValue() + "");
        updateAlteredImage();
    }

    public void updateContrastSlider()
    {
        contrastValueLabel.setText(contrastSlider.getValue() + "");
        updateAlteredImage();
    }

    public void updateAlteredImage()
    {
        int brightness = brightnessSlider.getValue();
        int contrast   = contrastSlider.getValue();

        Image new_image  = color_manager.brightness(original_image.getImage(), p_width, p_height, brightness);
        Image new_image2 = color_manager.contrast(new_image, p_width, p_height, contrast);
        this.setAlteredImage(new_image2);
    }



}
