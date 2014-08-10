package com.jipt.gui;
/*
	ImagePreviewFrame.java

	Author:			Administrator
	Description:
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jipt.ColorManager;
import com.jipt.JIPTimage;

public class HueAdjustFrame extends ImagePreviewFrame
{
    ColorManager color_manager = null;
    ImageFrame   image_frame   = null;

	JLabel originalValues[] = new JLabel[10];
	JLabel originalColors[] = new JLabel[10];

	JSlider colorSliders[] = new JSlider[10];

	JLabel newValues[] = new JLabel[10];
	JLabel newColors[] = new JLabel[10];

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
	javax.swing.JButton resetButton = new javax.swing.JButton();
// END GENERATED CODE

	public HueAdjustFrame(ColorManager cm, ImageFrame i_frame)
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
		resetButton.setSize(new java.awt.Dimension(90, 35));
		resetButton.setVisible(true);
		resetButton.setText("Reset");
		resetButton.setLocation(new java.awt.Point(210, 570));

		setSize(new java.awt.Dimension(500, 650));
		getContentPane().setLayout(null);
		setTitle("Hue Adjust");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(resetButton);


		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE
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

        okButton.setLocation(new java.awt.Point(100, 570));
        cancelButton.setLocation(new java.awt.Point(320, 570));

		resetButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				resetButtonActionPerformed(e);
			}
		});

        JLabel topLabel = new JLabel("Original Hues");
        topLabel.setSize(new Dimension(125, 20));
        topLabel.setLocation(187,525);
        topLabel.setVisible(true);
        topLabel.setForeground(Color.black);
        this.getContentPane().add(topLabel);

        JLabel bottomLabel = new JLabel("Replacement Hues");
        bottomLabel.setSize(new Dimension(150, 20));
        bottomLabel.setLocation(200,225);
        bottomLabel.setVisible(true);
        bottomLabel.setForeground(Color.black);
        this.getContentPane().add(bottomLabel);

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.hueAdjustColorMenuItem.getIcon();
        this.setIconImage(ii.getImage());

		////////////////////////////
		//// Put down labels ///////
		////////////////////////////
		int initial_degree = 0;
		int initial_x = 50;
		int initial_y = 250;


		for(int i = 0; i < newColors.length; i++)
		{


			/////////////////
			/// Top Value ///
			/////////////////
			JLabel originalValue = new JLabel();
			originalValue.setSize(new java.awt.Dimension(30, 20));
			originalValue.setVisible(true);
			//originalValue.setBackground(new java.awt.Color(128, 128, 128));
			originalValue.setText(" " + initial_degree);
			originalValue.setForeground(new java.awt.Color(0, 0, 0));
			originalValue.setLocation(new java.awt.Point(initial_x + i*(originalValue.getWidth() + 10), initial_y + 250));
			originalValues[i] = originalValue;
			this.getContentPane().add(originalValue);


			/////////////////////
			/// Top Color Box ///
			/////////////////////
			JLabel originalColor = new JLabel();
			originalColor.setOpaque(true);
			originalColor.setSize(new java.awt.Dimension(30, 20));
			originalColor.setVisible(true);
			originalColor.setBackground(new java.awt.Color(255,0,128));
			originalColor.setLocation(new java.awt.Point(initial_x + i*(originalColor.getWidth() + 10), initial_y + 220));
            originalColors[i] = originalColor;
			this.getContentPane().add(originalColor);

            //newColor.setLocation(new java.awt.Point(initial_x + i*(originalValue.getWidth() + 10), initial_y + 220));
           //newValue.setLocation(new java.awt.Point(initial_x + i*(originalValue.getWidth() + 10), initial_y + 250));


			/////////////////////////
			///// Color Sliders /////
			/////////////////////////
			JSlider new_slider = new JSlider();
			new_slider.setSize(new java.awt.Dimension(20, 150));
			new_slider.setVisible(true);
			new_slider.setOrientation(1);

			new_slider.setMinimum(0);
			new_slider.setMaximum(359);
			new_slider.setValue(initial_degree);
			//new_slider.setMajorTickSpacing(10);
			new_slider.setLocation(new java.awt.Point(initial_x + i*(originalValue.getWidth() + 10) + 7, initial_y + 60));
			this.getContentPane().add(new_slider);
			colorSliders[i] = new_slider;
			//System.out.println("Initial degree + " + initial_degree);
			new_slider.addChangeListener(new ChangeListener(){
				public void stateChanged(ChangeEvent e)
				{
					setAlteredColors();
				}});


			//////////////////////////
			//// Bottom color box ////
			//////////////////////////
			JLabel newColor = new JLabel();
			newColor.setOpaque(true);
			newColor.setSize(new java.awt.Dimension(30, 20));
			newColor.setVisible(true);
			newColor.setBackground(new java.awt.Color(255,0,128));
			newColor.setLocation(new java.awt.Point(initial_x + i*(newColor.getWidth() + 10), initial_y + 30));
			newColors[i] = newColor;
			this.getContentPane().add(newColor);

			/////////////////////////////
			///// Bottom values /////////
			/////////////////////////////
			JLabel newValue = new JLabel();
			newValue.setSize(new java.awt.Dimension(30, 20));
			newValue.setVisible(true);
			//originalValue.setBackground(new java.awt.Color(128, 128, 128));
			newValue.setText(" " + initial_degree);
			newValue.setForeground(new java.awt.Color(0, 0, 0));
			newValue.setLocation(new java.awt.Point(initial_x + i*(newValue.getWidth() + 10), initial_y));
			newValues[i] = newValue;
			this.getContentPane().add(newValue);

            // originalColor.setLocation(new java.awt.Point(initial_x + i*(originalValue.getWidth() + 10), initial_y + 30));
			//originalValue.setLocation(new java.awt.Point(initial_x + i*(originalValue.getWidth() + 10), initial_y));



			initial_degree += 36;
		}

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

		setOriginalColors();
		setAlteredColors();


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

	public void resetButtonActionPerformed(java.awt.event.ActionEvent e)
	{
		for(int i = 0; i < originalValues.length; i++)
		{

			colorSliders[i].setValue(Integer.parseInt(originalValues[i].getText().trim()));
		}

		setAlteredColors();
	}

	public void okButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        int huesToReplace[] = new int[originalValues.length]; // Hues to replace in the image
        int newHues[]       = new int[originalValues.length]; // Hues to replace the corresponding original hue with.
                                           // ex: huesToReplace[0] will be replace with newHues[0]

        for(int i = 0; i < originalValues.length; i++)
        {
            huesToReplace[i] = Integer.parseInt(originalValues[i].getText().trim());
            newHues[i]       = Integer.parseInt(newValues[i].getText().trim());
        }

        Image new_image = color_manager.adjustHues(image_frame.getJIPTImage().getImage(), original_width, original_height,
                                                    huesToReplace, newHues);

        //this.setAlteredImage(new_image);
        image_frame.setFrameImage(new_image, true);
        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
		thisWindowClosing(null);
	}

	///////////////////////////
	/// Set original colors ///
	///////////////////////////
	public void setOriginalColors()
	{
		float saturation = 1;    // Saturation
		float intensity  = 1;  // Intensity

		for(int i = 0; i < originalColors.length; i++)
		{
			float hue = Float.parseFloat(originalValues[i].getText());

			Color new_color = Color.getHSBColor(hue/360.0f, saturation, intensity);
			originalColors[i].setBackground(new_color);

		}

	}

	///////////////////////
	/// Set new colors ////
	///////////////////////
	public void setAlteredColors()
	{
		float saturation = 1;    // Saturation
		float intensity  = 1;  // Intensity

		for(int i = 0; i < originalColors.length; i++)
		{
			float hue = colorSliders[i].getValue();
			newValues[i].setText(" " + (int) hue);

			Color new_color = Color.getHSBColor(hue/360.0f, saturation, intensity);
			newColors[i].setBackground(new_color);

		}

        updateAlteredImage();
    }

    public void updateAlteredImage()
    {
        int huesToReplace[] = new int[originalValues.length]; // Hues to replace in the image
        int newHues[]       = new int[originalValues.length]; // Hues to replace the corresponding original hue with.
                                           // ex: huesToReplace[0] will be replace with newHues[0]

        for(int i = 0; i < originalValues.length; i++)
        {
            huesToReplace[i] = Integer.parseInt(originalValues[i].getText().trim());
            newHues[i]       = Integer.parseInt(newValues[i].getText().trim());
        }


        Image new_image = color_manager.adjustHues(original_image.getImage(), p_width, p_height,
                                                    huesToReplace, newHues);

        this.setAlteredImage(new_image);
    }



}
