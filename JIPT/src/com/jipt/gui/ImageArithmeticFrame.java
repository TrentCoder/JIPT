package com.jipt.gui;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.jipt.ColorManager;
public class ImageArithmeticFrame extends JFrame
{
    ColorManager color_manager = null;
    FrameManager frame_manager = null;
    MainFrame    main_frame    = null;
    ArrayList    frame_list    = null;


// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel imageLabel = new javax.swing.JLabel();
	javax.swing.JLabel previewLabel = new javax.swing.JLabel();
	javax.swing.JLabel source1Label = new javax.swing.JLabel();
	javax.swing.JComboBox source1ComboBox = new javax.swing.JComboBox();
	javax.swing.JLabel source2Label = new javax.swing.JLabel();
	javax.swing.JComboBox source2ComboBox = new javax.swing.JComboBox();
	javax.swing.JLabel channelLabel = new javax.swing.JLabel();
	javax.swing.JCheckBox redCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox greenCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox blueCheckbox = new javax.swing.JCheckBox();
	javax.swing.JLabel clipLabel = new javax.swing.JLabel();
	javax.swing.JCheckBox clipColorCheckbox = new javax.swing.JCheckBox();
	javax.swing.JLabel resultSizeLabel = new javax.swing.JLabel();
	javax.swing.JRadioButton source1Radio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton source2Radio = new javax.swing.JRadioButton();
	javax.swing.JLabel operationLabel = new javax.swing.JLabel();
	javax.swing.JRadioButton andRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton orRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton xorRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton addRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton subtractRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton differenceRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton lightestRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton darkestRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton averageRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton multiplyRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton divideRadio = new javax.swing.JRadioButton();
	javax.swing.JButton okButton = new javax.swing.JButton();
	javax.swing.JButton cancelButton = new javax.swing.JButton();
// END GENERATED CODE


	public ImageArithmeticFrame(ColorManager cm, FrameManager fm, MainFrame mf)
	{
        color_manager = cm;
        frame_manager = fm;
        main_frame    = mf;
        frame_list = frame_manager.getFrameList();
	}

	public void initComponents() throws Exception
	{
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		imageLabel.setSize(new java.awt.Dimension(190, 180));
		imageLabel.setVisible(true);
		imageLabel.setLocation(new java.awt.Point(335, 20));

		previewLabel.setSize(new java.awt.Dimension(63, 20));
		previewLabel.setVisible(true);
		previewLabel.setText("Preview");
		previewLabel.setLocation(new java.awt.Point(415, 210));

		source1Label.setSize(new java.awt.Dimension(72, 20));
		source1Label.setVisible(true);
		source1Label.setText("Source 1");
		source1Label.setLocation(new java.awt.Point(20, 20));

		source1ComboBox.setSize(new java.awt.Dimension(150, 25));
		source1ComboBox.setVisible(true);
		source1ComboBox.setLocation(new java.awt.Point(100, 20));

		source2Label.setSize(new java.awt.Dimension(72, 20));
		source2Label.setVisible(true);
		source2Label.setText("Source 2");
		source2Label.setLocation(new java.awt.Point(20, 60));

		source2ComboBox.setSize(new java.awt.Dimension(150, 25));
		source2ComboBox.setVisible(true);
		source2ComboBox.setLocation(new java.awt.Point(100, 60));

		channelLabel.setSize(new java.awt.Dimension(66, 20));
		channelLabel.setVisible(true);
		channelLabel.setText("Channels");
		channelLabel.setLocation(new java.awt.Point(20, 140));

		redCheckbox.setSize(new java.awt.Dimension(46, 20));
		redCheckbox.setVisible(true);
		redCheckbox.setText("Red");
		redCheckbox.setLocation(new java.awt.Point(90, 140));

		greenCheckbox.setSize(new java.awt.Dimension(60, 20));
		greenCheckbox.setVisible(true);
		greenCheckbox.setText("Green");
		greenCheckbox.setLocation(new java.awt.Point(160, 140));

		blueCheckbox.setSize(new java.awt.Dimension(50, 20));
		blueCheckbox.setVisible(true);
		blueCheckbox.setText("Blue");
		blueCheckbox.setLocation(new java.awt.Point(250, 140));

		clipLabel.setSize(new java.awt.Dimension(62, 20));
		clipLabel.setVisible(true);
		clipLabel.setText("Clip Color");
		clipLabel.setLocation(new java.awt.Point(20, 180));

		clipColorCheckbox.setSize(new java.awt.Dimension(43, 21));
		clipColorCheckbox.setVisible(true);
		clipColorCheckbox.setText("On");
		clipColorCheckbox.setLocation(new java.awt.Point(90, 180));

		resultSizeLabel.setSize(new java.awt.Dimension(140, 20));
		resultSizeLabel.setVisible(true);
		resultSizeLabel.setText("Scale result to size of");
		resultSizeLabel.setLocation(new java.awt.Point(20, 100));

		source1Radio.setSize(new java.awt.Dimension(80, 21));
		source1Radio.setVisible(true);
		source1Radio.setText("Source 1");
		source1Radio.setLocation(new java.awt.Point(160, 100));

		source2Radio.setSize(new java.awt.Dimension(80, 20));
		source2Radio.setVisible(true);
		source2Radio.setText("Source 2");
		source2Radio.setLocation(new java.awt.Point(250, 100));

		operationLabel.setSize(new java.awt.Dimension(80, 20));
		operationLabel.setVisible(true);
		operationLabel.setText("Operation");
		operationLabel.setLocation(new java.awt.Point(20, 220));

		andRadio.setSize(new java.awt.Dimension(69, 20));
		andRadio.setVisible(true);
		andRadio.setText("AND");
		andRadio.setLocation(new java.awt.Point(41, 248));

		orRadio.setSize(new java.awt.Dimension(70, 20));
		orRadio.setVisible(true);
		orRadio.setText("OR");
		orRadio.setLocation(new java.awt.Point(40, 280));

		xorRadio.setSize(new java.awt.Dimension(70, 20));
		xorRadio.setVisible(true);
		xorRadio.setText("XOR");
		xorRadio.setLocation(new java.awt.Point(40, 310));

		addRadio.setSize(new java.awt.Dimension(70, 20));
		addRadio.setVisible(true);
		addRadio.setText("Add");
		addRadio.setLocation(new java.awt.Point(140, 250));

		subtractRadio.setSize(new java.awt.Dimension(80, 20));
		subtractRadio.setVisible(true);
		subtractRadio.setText("Subtract");
		subtractRadio.setLocation(new java.awt.Point(140, 280));

		differenceRadio.setSize(new java.awt.Dimension(90, 20));
		differenceRadio.setVisible(true);
		differenceRadio.setText("Difference");
		differenceRadio.setLocation(new java.awt.Point(140, 310));

		lightestRadio.setSize(new java.awt.Dimension(70, 20));
		lightestRadio.setVisible(true);
		lightestRadio.setText("Lightest");
		lightestRadio.setLocation(new java.awt.Point(250, 250));

		darkestRadio.setSize(new java.awt.Dimension(70, 20));
		darkestRadio.setVisible(true);
		darkestRadio.setText("Darkest");
		darkestRadio.setLocation(new java.awt.Point(250, 280));

		averageRadio.setSize(new java.awt.Dimension(70, 20));
		averageRadio.setVisible(true);
		averageRadio.setText("Average");
		averageRadio.setLocation(new java.awt.Point(250, 310));

		multiplyRadio.setSize(new java.awt.Dimension(70, 20));
		multiplyRadio.setVisible(true);
		multiplyRadio.setText("Multiply");
		multiplyRadio.setLocation(new java.awt.Point(360, 250));

		divideRadio.setSize(new java.awt.Dimension(70, 20));
		divideRadio.setVisible(true);
		divideRadio.setText("Divide");
		divideRadio.setLocation(new java.awt.Point(360, 280));

		okButton.setSize(new java.awt.Dimension(82, 32));
		okButton.setVisible(true);
		okButton.setText("OK");
		okButton.setLocation(new java.awt.Point(160, 370));

		cancelButton.setSize(new java.awt.Dimension(80, 30));
		cancelButton.setVisible(true);
		cancelButton.setText("Cancel");
		cancelButton.setLocation(new java.awt.Point(270, 370));

		setSize(new java.awt.Dimension(575, 450));
		getContentPane().setLayout(null);
		setTitle("Image Arithmetic");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(imageLabel);
		getContentPane().add(previewLabel);
		getContentPane().add(source1Label);
		getContentPane().add(source1ComboBox);
		getContentPane().add(source2Label);
		getContentPane().add(source2ComboBox);
		getContentPane().add(channelLabel);
		getContentPane().add(redCheckbox);
		getContentPane().add(greenCheckbox);
		getContentPane().add(blueCheckbox);
		getContentPane().add(clipLabel);
		getContentPane().add(clipColorCheckbox);
		getContentPane().add(resultSizeLabel);
		getContentPane().add(source1Radio);
		getContentPane().add(source2Radio);
		getContentPane().add(operationLabel);
		getContentPane().add(andRadio);
		getContentPane().add(orRadio);
		getContentPane().add(xorRadio);
		getContentPane().add(addRadio);
		getContentPane().add(subtractRadio);
		getContentPane().add(differenceRadio);
		getContentPane().add(lightestRadio);
		getContentPane().add(darkestRadio);
		getContentPane().add(averageRadio);
		getContentPane().add(multiplyRadio);
		getContentPane().add(divideRadio);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.arithmaticScaleColorMenuItem.getIcon();
        this.setIconImage(ii.getImage());

		addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE

        ////////////////////////////////////////
        /////// Add file names to list boxes ///
        ////////////////////////////////////////
        source1ComboBox.removeAllItems();
        source2ComboBox.removeAllItems();
        for(int i = 0; i < frame_list.size(); i++)
        {
            ImageFrame i_frame = (ImageFrame) frame_list.get(i);
            String     name    = i_frame.getTitle();
            // System.out.println("Name = " + name);
            source1ComboBox.addItem(name);
            source2ComboBox.addItem(name);
        }

        /////////////////////////////////////////
        //////////// Button Groups //////////////
        /////////////////////////////////////////
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(source1Radio);
        sizeGroup.add(source2Radio);
        source1Radio.setSelected(true);

        ButtonGroup operationGroup = new ButtonGroup();
        operationGroup.add(andRadio);
		operationGroup.add(orRadio);
		operationGroup.add(xorRadio);
		operationGroup.add(addRadio);
		operationGroup.add(subtractRadio);
		operationGroup.add(differenceRadio);
		operationGroup.add(lightestRadio);
		operationGroup.add(darkestRadio);
		operationGroup.add(averageRadio);
		operationGroup.add(multiplyRadio);
		operationGroup.add(divideRadio);
        andRadio.setSelected(true);

        /////////////////////////////////////////
        //////// Add action listeners ///////////
        /////////////////////////////////////////

        // scale
        source1Radio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        source2Radio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });


        // Image lists
        source1ComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        source2ComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        // Color clip
        clipColorCheckbox.setSelected(true);

        clipColorCheckbox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        // Color channels
        redCheckbox.setSelected(true);
        greenCheckbox.setSelected(true);
        blueCheckbox.setSelected(true);

        redCheckbox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        blueCheckbox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        greenCheckbox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        // Operations
        andRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        orRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        xorRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        addRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        subtractRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        differenceRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        lightestRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        darkestRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        averageRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        multiplyRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
        divideRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        // OK and Cancel
        okButton.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       ok();
                   }
        });

        cancelButton.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       cancel();
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

    ///////////////////////////
    ///// OK clicked //////////
    ///////////////////////////
    public void ok()
    {
        // System.out.println("You clicked something");

        // Get source 1 image & dimensions
        int        source1_index        = source1ComboBox.getSelectedIndex();
        ImageFrame source1_image_frame  = ((ImageFrame) frame_list.get(source1_index));
        Image      source1_image        = source1_image_frame.getJIPTImage().getImage();
        int        source1_width        = source1_image_frame.getImageWidth();
        int        source1_height       = source1_image_frame.getImageHeight();

        // Get source 2 image & dimensions
        int        source2_index        = source2ComboBox.getSelectedIndex();
        ImageFrame source2_image_frame  = ((ImageFrame) frame_list.get(source2_index));
        Image      source2_image        = source2_image_frame.getJIPTImage().getImage();
        int        source2_width        = source2_image_frame.getImageWidth();
        int        source2_height       = source2_image_frame.getImageHeight();

        // Based on scale factor, determine which image should be scaled
        int width = 0;
        int height;

        if(source1Radio.isSelected())
        {
            // Scale source 2 to source 1's size
            source2_image = source2_image.getScaledInstance(source1_width, source1_height, Image.SCALE_FAST);
            width  = source1_width;
            height = source1_height;
        }
        else
        {
            // Scale source 1 to source 2's size
            source1_image = source1_image.getScaledInstance(source2_width, source2_height, Image.SCALE_FAST);
            width  = source2_width;
            height = source2_height;
        }

        while(
        source1_image.getWidth(this) == -1
        ||
        source1_image.getHeight(this) == -1
        ||
        source2_image.getWidth(this) == -1
        ||
        source2_image.getHeight(this) == -1
        )
        {
            //System.out.println("Something is still -1");
        }

        width  = source1_image.getWidth(this);
        height = source1_image.getHeight(this);

        // Get the options (operation, clipping, channel) and pass it to ColorManager for a new image
        boolean clip = clipColorCheckbox.isSelected();
        boolean r    = redCheckbox.isSelected();
        boolean g    = greenCheckbox.isSelected();
        boolean b    = blueCheckbox.isSelected();
        int     operation = 0;

        if(andRadio.isSelected())
            operation = ColorManager.AND;
        else if(orRadio.isSelected())
            operation = ColorManager.OR;
        else if(xorRadio.isSelected())
            operation = ColorManager.XOR;
        else if(addRadio.isSelected())
            operation = ColorManager.ADD;
        else if(subtractRadio.isSelected())
            operation = ColorManager.SUBTRACT;
        else if(differenceRadio.isSelected())
            operation = ColorManager.DIFFERENCE;
        else if(lightestRadio.isSelected())
            operation = ColorManager.LIGHTEST;
        else if(darkestRadio.isSelected())
            operation = ColorManager.DARKEST;
        else if(averageRadio.isSelected())
            operation = ColorManager.AVERAGE;
        else if(multiplyRadio.isSelected())
            operation = ColorManager.MULTIPLY;
        else if(divideRadio.isSelected())
            operation = ColorManager.DIVIDE;

        // Create a new frame and add it to the frame list
        Image new_image = color_manager.arithmetic(source1_image, source2_image,
                                                    width, height, operation,
                                                    r, g, b, clip);

       // System.out.println("Width, Height = " + width + " " + height);
        //imageLabel.setIcon(new ImageIcon(new_image));


        main_frame.createNewImage(new_image);
        //ImageFrame new_frame = new ImageFrame(main_frame, new_image);
        //new_frame.setName("untitled");
       // new_frame.setVisible(true);
       // frame_manager.addFrame(new_frame);
        thisWindowClosing(null);
    }

    /////////////////////////////
    /////// Cancel clicked //////
    /////////////////////////////
    public void cancel()
    {
        thisWindowClosing(null);
    }

    ////////////////////////////////////////////////
    /////////// Update the altered image ///////////
    ////////////////////////////////////////////////
    public void updateAlteredImage()
    {
       // System.out.println("You clicked something");

        // Get source 1 image & dimensions
        int        source1_index        = source1ComboBox.getSelectedIndex();
        ImageFrame source1_image_frame  = ((ImageFrame) frame_list.get(source1_index));
        Image      source1_image        = source1_image_frame.getJIPTImage().getImage();
        int        source1_width        = source1_image_frame.getImageWidth();
        int        source1_height       = source1_image_frame.getImageHeight();

        // Get source 2 image & dimensions
        int        source2_index        = source2ComboBox.getSelectedIndex();
        ImageFrame source2_image_frame  = ((ImageFrame) frame_list.get(source2_index));
        Image      source2_image        = source2_image_frame.getJIPTImage().getImage();
        int        source2_width        = source2_image_frame.getImageWidth();
        int        source2_height       = source2_image_frame.getImageHeight();



        // Based on scale factor, determine which image should be scaled
        int width = 0;
        int height;

        if(source1Radio.isSelected())
        {
            // Scale source 2 to source 1's size
            source2_image = source2_image.getScaledInstance(source1_width, source1_height, Image.SCALE_FAST);
            width  = source1_width;
            height = source1_height;
        }
        else
        {
            // Scale source 1 to source 2's size
            source1_image = source1_image.getScaledInstance(source2_width, source2_height, Image.SCALE_FAST);
            width  = source2_width;
            height = source2_height;
        }

        // Make images scaled for the preview frame
        if( width > height)
        {
            source1_image = source1_image.getScaledInstance(imageLabel.getWidth(), -1, Image.SCALE_FAST );
            source2_image = source2_image.getScaledInstance(imageLabel.getWidth(), -1, Image.SCALE_FAST );
        }
        else
        {
            source1_image = source1_image.getScaledInstance(-1, imageLabel.getHeight(), Image.SCALE_FAST );
            source2_image = source2_image.getScaledInstance(-1, imageLabel.getHeight(), Image.SCALE_FAST );
        }

        while(
        source1_image.getWidth(this) == -1
        ||
        source1_image.getHeight(this) == -1
        ||
        source2_image.getWidth(this) == -1
        ||
        source2_image.getHeight(this) == -1
        )
        {
            // System.out.println("Something is still -1");
        }

        //System.out.println("After scaling Source1 w, h = " + source1_image.getWidth(this) + " " + source1_image.getHeight(this));
       // System.out.println("After scaling Source2 w, h = " + source2_image.getWidth(this) + " " + source2_image.getHeight(this));

        width  = source1_image.getWidth(this);
        height = source1_image.getHeight(this);

        // Get the options (operation, clipping, channel) and pass it to ColorManager for a new image
        boolean clip = clipColorCheckbox.isSelected();
        boolean r    = redCheckbox.isSelected();
        boolean g    = greenCheckbox.isSelected();
        boolean b    = blueCheckbox.isSelected();
        int     operation = 0;

        if(andRadio.isSelected())
            operation = ColorManager.AND;
        else if(orRadio.isSelected())
            operation = ColorManager.OR;
        else if(xorRadio.isSelected())
            operation = ColorManager.XOR;
        else if(addRadio.isSelected())
            operation = ColorManager.ADD;
        else if(subtractRadio.isSelected())
            operation = ColorManager.SUBTRACT;
        else if(differenceRadio.isSelected())
            operation = ColorManager.DIFFERENCE;
        else if(lightestRadio.isSelected())
            operation = ColorManager.LIGHTEST;
        else if(darkestRadio.isSelected())
            operation = ColorManager.DARKEST;
        else if(averageRadio.isSelected())
            operation = ColorManager.AVERAGE;
        else if(multiplyRadio.isSelected())
            operation = ColorManager.MULTIPLY;
        else if(divideRadio.isSelected())
            operation = ColorManager.DIVIDE;

        // put the new image in the image preview frame
        Image new_image = color_manager.arithmetic(source1_image, source2_image,
                                                    width, height, operation,
                                                    r, g, b, clip);

       // System.out.println("Width, Height = " + width + " " + height);
        imageLabel.setIcon(new ImageIcon(new_image));


    }

}
