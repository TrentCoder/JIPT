package com.jipt.gui;
/*
	ImagePreviewFrame.java

	Author:			Administrator
	Description:
*/



import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

import com.jipt.JIPTimage;
import com.jipt.filter.FilterManager;

public class MeanMedianFilterFrame extends ImagePreviewFrame
{
    FilterManager filter_manager = null;
    ImageFrame    image_frame    = null;

    JIPTimage  original_image     = null;
    JIPTimage  altered_image      = null;
    int original_height;
    int original_width;
    int p_height;       // Height & Width of preview images
    int p_width;

    private String windowSizes[] = {"3x3", "5x5", "7x7"};
    private String iterations[] =  {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel windowSizeLabel = new javax.swing.JLabel();

    //javax.swing.JList windowSizeList = new javax.swing.JList();
	//javax.swing.JList iterationList = new javax.swing.JList();
    javax.swing.JComboBox windowSizeList = new javax.swing.JComboBox(windowSizes);
	javax.swing.JComboBox iterationList  = new javax.swing.JComboBox(iterations);


    javax.swing.JLabel channelLabel = new javax.swing.JLabel();
	javax.swing.JCheckBox redCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox greenCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox blueCheckbox = new javax.swing.JCheckBox();
	javax.swing.JLabel iterationLabel = new javax.swing.JLabel();

	javax.swing.JRadioButton medianRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton meanRadio = new javax.swing.JRadioButton();
	javax.swing.JLabel typeLabel = new javax.swing.JLabel();
// END GENERATED CODE

	public MeanMedianFilterFrame(FilterManager fm, ImageFrame i_frame)
	{
        image_frame = i_frame;
        filter_manager = fm;
	}

	public void initComponents() throws Exception
	{
        super.initComponents();

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		windowSizeLabel.setSize(new java.awt.Dimension(80, 20));
		windowSizeLabel.setVisible(true);
		windowSizeLabel.setText("Window Size");
		windowSizeLabel.setLocation(new java.awt.Point(150, 250));


		windowSizeList.setSize(new java.awt.Dimension(70, 20));
		windowSizeList.setVisible(true);
		windowSizeList.setLocation(new java.awt.Point(150, 280));

		channelLabel.setSize(new java.awt.Dimension(60, 20));
		channelLabel.setVisible(true);
		channelLabel.setText("Channels");
		channelLabel.setLocation(new java.awt.Point(380, 250));

		redCheckbox.setSize(new java.awt.Dimension(60, 20));
		redCheckbox.setVisible(true);
		redCheckbox.setText("Red");
		redCheckbox.setLocation(new java.awt.Point(380, 280));

		greenCheckbox.setSize(new java.awt.Dimension(60, 20));
		greenCheckbox.setVisible(true);
		greenCheckbox.setText("Green");
		greenCheckbox.setLocation(new java.awt.Point(380, 305));

		blueCheckbox.setSize(new java.awt.Dimension(60, 20));
		blueCheckbox.setVisible(true);
		blueCheckbox.setText("Blue");
		blueCheckbox.setLocation(new java.awt.Point(380, 330));

		iterationLabel.setSize(new java.awt.Dimension(70, 20));
		iterationLabel.setVisible(true);
		iterationLabel.setText("Iterations");
		iterationLabel.setLocation(new java.awt.Point(280, 250));

		iterationList.setSize(new java.awt.Dimension(70, 20));
		iterationList.setVisible(true);
		iterationList.setLocation(new java.awt.Point(270, 280));

		medianRadio.setSize(new java.awt.Dimension(77, 21));
		medianRadio.setVisible(true);
		medianRadio.setText("Median");
		medianRadio.setLocation(new java.awt.Point(30, 280));

		meanRadio.setSize(new java.awt.Dimension(70, 20));
		meanRadio.setVisible(true);
		meanRadio.setText("Mean");
		meanRadio.setLocation(new java.awt.Point(30, 310));

		typeLabel.setSize(new java.awt.Dimension(40, 20));
		typeLabel.setVisible(true);
		typeLabel.setText("Filter");
		typeLabel.setLocation(new java.awt.Point(50, 250));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("Mean/Median");

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.meanMedianFiltersMenuItem.getIcon();
        this.setIconImage(ii.getImage());

		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(windowSizeLabel);
		getContentPane().add(windowSizeList);
		getContentPane().add(channelLabel);
		getContentPane().add(redCheckbox);
		getContentPane().add(greenCheckbox);
		getContentPane().add(blueCheckbox);
		getContentPane().add(iterationLabel);
		getContentPane().add(iterationList);
		getContentPane().add(medianRadio);
		getContentPane().add(meanRadio);
		getContentPane().add(typeLabel);


		windowSizeList.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        windowSizeListValueChanged(e);
                    }

			    });

       	iterationList.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        iterationListValueChanged(e);
                    }

			    });

		redCheckbox.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        redCheckboxStateChanged(e);
                    }

			    });
		greenCheckbox.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        greenCheckboxStateChanged(e);
                    }

			    });
		blueCheckbox.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        blueCheckboxStateChanged(e);
                    }

			    });

		medianRadio.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        medianRadioStateChanged(e);
                    }

			    });
		meanRadio.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        meanRadioStateChanged(e);
                    }

			    });
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE

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

        // Width and height of thhe full size image
        original_height = image_frame.getJIPTImage().getImage().getHeight(this);
        original_width  = image_frame.getJIPTImage().getImage().getWidth(this);
        //System.out.println("w,h = " + original_width + " " + original_height);

        //////////////////////////////////
        ////////////// Lists /////////////
        //////////////////////////////////

        //////////////////////
        ////// Buttons ///////
        //////////////////////
        ButtonGroup bg = new ButtonGroup();
        bg.add(meanRadio);
        bg.add(medianRadio);

        medianRadio.setSelected(true);


        redCheckbox.setSelected(true);
        greenCheckbox.setSelected(true);
        blueCheckbox.setSelected(true);
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
        boolean red_selected   = redCheckbox.isSelected();
        boolean green_selected = greenCheckbox.isSelected();
        boolean blue_selected  = blueCheckbox.isSelected();

        Image i = image_frame.getJIPTImage().getImage();

        ////////////////////////
        //// Get window size ///
        ////////////////////////
        String windowSizeString = windowSizes[windowSizeList.getSelectedIndex()];
        int window_size = Integer.parseInt(windowSizeString.substring(0,1));

        String iterationString   = iterations[iterationList.getSelectedIndex()];
        int number_of_iterations = Integer.parseInt(iterationString);


        ///////////////////////////
        /// Call mean or median ///
        ///////////////////////////
        if(meanRadio.isSelected())
        {
            // Mean
            Image new_image = filter_manager.mean(i,original_width, original_height, red_selected, green_selected, blue_selected,
                                    window_size, number_of_iterations);
           image_frame.setFrameImage(new_image, true);

        }
        else
        {
            // Median
            Image new_image = filter_manager.median(i,original_width, original_height, red_selected, green_selected, blue_selected,
                                    window_size, number_of_iterations);
           image_frame.setFrameImage(new_image, true);
        }

        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        //System.out.println("You clicked cancel!");
        thisWindowClosing(null);
	}

	/////////////////////////////////////////////////////////////
    /////////////////////////// Checkboxes //////////////////////
    /////////////////////////////////////////////////////////////
    public void blueCheckboxStateChanged(ActionEvent e)
	{
        updateAlteredImage();
	}

    public void greenCheckboxStateChanged(ActionEvent e)
	{
        updateAlteredImage();
	}

	public void redCheckboxStateChanged(ActionEvent e)
	{
        updateAlteredImage();
	}

 	/////////////////////////////////////////////////////////////
    /////////////////////////// Lists ///////////////////////////
    /////////////////////////////////////////////////////////////
	public void windowSizeListValueChanged(ActionEvent e)
	{
        // System.out.println(windowSizes[windowSizeList.getSelectedIndex()]);
        updateAlteredImage();
	}

	public void iterationListValueChanged(ActionEvent e)
	{
        // System.out.println(iterations[iterationList.getSelectedIndex()]);
        updateAlteredImage();
	}


	/////////////////////////////////////////////////////////////
    /////////////////////////// Radio Buttons ///////////////////
    /////////////////////////////////////////////////////////////
	public void medianRadioStateChanged(ActionEvent e)
	{
        if(medianRadio.isSelected())
            updateAlteredImage();
	}

	public void meanRadioStateChanged(ActionEvent e)
	{
        //System.out.println("Mean clicked");
        if(meanRadio.isSelected())
            updateAlteredImage();
	}

    /////////////////////////////////
    /// Change the altered image ////
    /////////////////////////////////
    public void updateAlteredImage()
    {
        //System.out.println("Change the altered image");

        boolean red_selected   = redCheckbox.isSelected();
        boolean green_selected = greenCheckbox.isSelected();
        boolean blue_selected  = blueCheckbox.isSelected();

        Image i = original_image.getImage();

        ////////////////////////
        //// Get window size ///
        ////////////////////////
        String windowSizeString = windowSizes[windowSizeList.getSelectedIndex()];
        int window_size = Integer.parseInt(windowSizeString.substring(0,1));

        String iterationString   = iterations[iterationList.getSelectedIndex()];
        int number_of_iterations = Integer.parseInt(iterationString);


        ///////////////////////////
        /// Call mean or median ///
        ///////////////////////////
        if(meanRadio.isSelected())
        {
            //System.out.println("Calling mean");
            // Mean
            Image new_image = filter_manager.mean(i,p_width, p_height, red_selected, green_selected, blue_selected,
                                    window_size, number_of_iterations);
            this.setAlteredImage(new_image);
        }
        else
        {
            // Median
            Image new_image = filter_manager.median(i,p_width, p_height, red_selected, green_selected, blue_selected,
                                    window_size, number_of_iterations);
            this.setAlteredImage(new_image);

        }
    }


}
