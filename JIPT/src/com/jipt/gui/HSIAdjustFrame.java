package com.jipt.gui;

/*
	ImagePreviewFrame.java

	Author:			Administrator
	Description:
*/

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jipt.ColorManager;
import com.jipt.JIPTimage;

public class HSIAdjustFrame extends ImagePreviewFrame
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
	javax.swing.JLabel hueLabel = new javax.swing.JLabel();
	javax.swing.JLabel saturationLabel = new javax.swing.JLabel();
	javax.swing.JLabel intensityLabel = new javax.swing.JLabel();
	javax.swing.JSlider hueSlider = new javax.swing.JSlider();
	javax.swing.JSlider saturationSlider = new javax.swing.JSlider();
	javax.swing.JSlider intensitySlider = new javax.swing.JSlider();
	javax.swing.JLabel hueValueLabel = new javax.swing.JLabel();
	javax.swing.JLabel saturationValueLabel = new javax.swing.JLabel();
	javax.swing.JLabel intensityValueLabel = new javax.swing.JLabel();
// END GENERATED CODE



	public HSIAdjustFrame(ColorManager cm, ImageFrame i_frame)
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
		hueLabel.setSize(new java.awt.Dimension(60, 20));
		hueLabel.setVisible(true);
		hueLabel.setText("Hue");
		hueLabel.setLocation(new java.awt.Point(60, 270));

		saturationLabel.setSize(new java.awt.Dimension(60, 20));
		saturationLabel.setVisible(true);
		saturationLabel.setText("Saturation");
		saturationLabel.setLocation(new java.awt.Point(60, 300));

		intensityLabel.setSize(new java.awt.Dimension(60, 20));
		intensityLabel.setVisible(true);
		intensityLabel.setText("Intensity");
		intensityLabel.setLocation(new java.awt.Point(60, 330));

		hueSlider.setSize(new java.awt.Dimension(200, 20));
		hueSlider.setVisible(true);
		hueSlider.setValue(0);
		hueSlider.setMinimum(-100);
		hueSlider.setMajorTickSpacing(10);
		hueSlider.setLocation(new java.awt.Point(160, 270));

		saturationSlider.setSize(new java.awt.Dimension(200, 20));
		saturationSlider.setVisible(true);
		saturationSlider.setValue(0);
		saturationSlider.setMinimum(-100);
		saturationSlider.setMajorTickSpacing(10);
		saturationSlider.setLocation(new java.awt.Point(160, 300));

		intensitySlider.setSize(new java.awt.Dimension(200, 20));
		intensitySlider.setVisible(true);
		intensitySlider.setValue(0);
		intensitySlider.setMinimum(-100);
		intensitySlider.setMajorTickSpacing(10);
		intensitySlider.setLocation(new java.awt.Point(160, 330));

		hueValueLabel.setSize(new java.awt.Dimension(40, 20));
		hueValueLabel.setVisible(true);
		hueValueLabel.setText("0");
		hueValueLabel.setLocation(new java.awt.Point(380, 270));

		saturationValueLabel.setSize(new java.awt.Dimension(40, 20));
		saturationValueLabel.setVisible(true);
		saturationValueLabel.setText("0");
		saturationValueLabel.setLocation(new java.awt.Point(380, 300));

		intensityValueLabel.setSize(new java.awt.Dimension(40, 20));
		intensityValueLabel.setVisible(true);
		intensityValueLabel.setText("0");
		intensityValueLabel.setLocation(new java.awt.Point(380, 330));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("Hue/Saturation/Contrast");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(hueLabel);
		getContentPane().add(saturationLabel);
		getContentPane().add(intensityLabel);
		getContentPane().add(hueSlider);
		getContentPane().add(saturationSlider);
		getContentPane().add(intensitySlider);
		getContentPane().add(hueValueLabel);
		getContentPane().add(saturationValueLabel);
		getContentPane().add(intensityValueLabel);


		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE
        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.hsiAdjustColorMenuItem.getIcon();
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

        hueSlider.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
            updateHueSlider();
          }
        });

        intensitySlider.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
            updateIntensitySlider();
          }
        });

        saturationSlider.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
            updateSaturationSlider();
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
        int hue        = hueSlider.getValue();
        int saturation = saturationSlider.getValue();
        int intensity  = intensitySlider.getValue();

        Image new_image = color_manager.hsi_adjust(image_frame.getJIPTImage().getImage(), original_width, original_height,
                                            hue, saturation, intensity);

        image_frame.setFrameImage(new_image, true);
        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

    public void updateIntensitySlider()
    {
        intensityValueLabel.setText(intensitySlider.getValue() + "");
        updateAlteredImage();
    }

    public void updateHueSlider()
    {
        hueValueLabel.setText(hueSlider.getValue() + "");
        updateAlteredImage();
    }

    public void updateSaturationSlider()
    {
        saturationValueLabel.setText(saturationSlider.getValue() + "");
        updateAlteredImage();
    }

    //////////////////////////////////////////
    //// Updates the altered image preview ///
    //////////////////////////////////////////
    public void updateAlteredImage()
    {

        int hue        = hueSlider.getValue();
        int saturation = saturationSlider.getValue();
        int intensity  = intensitySlider.getValue();

        //System.out.println("Sending HSI..." + hue + " " + saturation + " " + intensity);
        Image new_image = color_manager.hsi_adjust(original_image.getImage(), p_width, p_height,
                                            hue, saturation, intensity);
        this.setAlteredImage(new_image);
    }



}
