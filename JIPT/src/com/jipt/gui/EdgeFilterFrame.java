package com.jipt.gui;
/*
    Trent Lucier
    November 18, 2001

    This class represents the interface for calling the edge detection filters.

*/

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jipt.JIPTimage;
import com.jipt.ToolManager;
import com.jipt.filter.FilterManager;

public class EdgeFilterFrame extends ImagePreviewFrame
{

    FilterManager filter_manager = null;
    ToolManager   tool_manager   = null;
    ImageFrame    image_frame    = null;

    JIPTimage  original_image     = null;
    JIPTimage  altered_image      = null;
    int original_height;
    int original_width;
    int p_height;       // Height & Width of preview images
    int p_width;

    private String windowSizes[] = {"3x3", "5x5", "7x7"};


// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel edgeLabel = new javax.swing.JLabel();
	javax.swing.JRadioButton sobelRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton laplaceRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton robertsRadio = new javax.swing.JRadioButton();
	javax.swing.JLabel prefilterRadio = new javax.swing.JLabel();
	javax.swing.JRadioButton nonePrefilterRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton gaussianPrefilterRadio = new
javax.swing.JRadioButton();
	javax.swing.JRadioButton meanPrefilterRadio = new javax.swing.JRadioButton();
	javax.swing.JLabel thresholdLabel = new javax.swing.JLabel();
	javax.swing.JRadioButton noneThresholdRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton manualThresholdRadio = new javax.swing.JRadioButton();
	javax.swing.JSlider thresholdSlider = new javax.swing.JSlider();
	javax.swing.JLabel manualValueLabel = new javax.swing.JLabel();
	javax.swing.JComboBox windowComboBox = new javax.swing.JComboBox(windowSizes);
// END GENERATED CODE

	public EdgeFilterFrame(FilterManager fm, ImageFrame i_frame)
	{
        image_frame = i_frame;
        filter_manager = fm;
        tool_manager = new ToolManager();
	}

	public void initComponents() throws Exception
	{
        super.initComponents();
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		edgeLabel.setSize(new java.awt.Dimension(90, 20));
		edgeLabel.setVisible(true);
		edgeLabel.setText("Edge Detector");
		edgeLabel.setLocation(new java.awt.Point(10, 240));

		sobelRadio.setSize(new java.awt.Dimension(70, 20));
		sobelRadio.setVisible(true);
		sobelRadio.setText("Sobel");
		sobelRadio.setLocation(new java.awt.Point(20, 270));

		laplaceRadio.setSize(new java.awt.Dimension(70, 20));
		laplaceRadio.setVisible(true);
		laplaceRadio.setText("Laplace");
		laplaceRadio.setLocation(new java.awt.Point(20, 300));

		robertsRadio.setSize(new java.awt.Dimension(70, 20));
		robertsRadio.setVisible(true);
		robertsRadio.setText("Roberts");
		robertsRadio.setLocation(new java.awt.Point(20, 330));

		prefilterRadio.setSize(new java.awt.Dimension(60, 20));
		prefilterRadio.setVisible(true);
		prefilterRadio.setText("Prefilter");
		prefilterRadio.setLocation(new java.awt.Point(125, 240));

		nonePrefilterRadio.setSize(new java.awt.Dimension(56, 20));
		nonePrefilterRadio.setVisible(true);
		nonePrefilterRadio.setText("None");
		nonePrefilterRadio.setLocation(new java.awt.Point(135, 270));

		gaussianPrefilterRadio.setSize(new java.awt.Dimension(81, 20));
		gaussianPrefilterRadio.setVisible(true);
		gaussianPrefilterRadio.setText("Gaussian");
		gaussianPrefilterRadio.setLocation(new java.awt.Point(135, 300));

		meanPrefilterRadio.setSize(new java.awt.Dimension(55, 20));
		meanPrefilterRadio.setVisible(true);
		meanPrefilterRadio.setText("Mean");
		meanPrefilterRadio.setLocation(new java.awt.Point(135, 330));

		thresholdLabel.setSize(new java.awt.Dimension(83, 20));
		thresholdLabel.setVisible(true);
		thresholdLabel.setText("Edge Strength");
		thresholdLabel.setLocation(new java.awt.Point(277, 240));

		noneThresholdRadio.setSize(new java.awt.Dimension(66, 20));
		noneThresholdRadio.setVisible(true);
		noneThresholdRadio.setText("None");
		noneThresholdRadio.setLocation(new java.awt.Point(290, 270));

		manualThresholdRadio.setSize(new java.awt.Dimension(75, 20));
		manualThresholdRadio.setVisible(true);
		manualThresholdRadio.setText("Manual");
		manualThresholdRadio.setLocation(new java.awt.Point(290, 300));

		thresholdSlider.setSize(new java.awt.Dimension(190, 20));
		thresholdSlider.setVisible(true);
		thresholdSlider.setMajorTickSpacing(10);
		thresholdSlider.setLocation(new java.awt.Point(290, 330));
  		thresholdSlider.setMaximum(255);
		thresholdSlider.setMajorTickSpacing(10);
        thresholdSlider.setValue(255);

		manualValueLabel.setSize(new java.awt.Dimension(50, 20));
		manualValueLabel.setVisible(true);
		manualValueLabel.setText(thresholdSlider.getValue() + "");
		manualValueLabel.setLocation(new java.awt.Point(410, 300));

		windowComboBox.setSize(new java.awt.Dimension(90, 26));
		windowComboBox.setVisible(true);
		windowComboBox.setLocation(new java.awt.Point(165, 360));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("Edge Detection");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(edgeLabel);
		getContentPane().add(sobelRadio);
		getContentPane().add(laplaceRadio);
		getContentPane().add(robertsRadio);
		getContentPane().add(prefilterRadio);
		getContentPane().add(nonePrefilterRadio);
		getContentPane().add(gaussianPrefilterRadio);
		getContentPane().add(meanPrefilterRadio);
		getContentPane().add(thresholdLabel);
		getContentPane().add(noneThresholdRadio);
		getContentPane().add(manualThresholdRadio);
		getContentPane().add(thresholdSlider);
		getContentPane().add(manualValueLabel);
		getContentPane().add(windowComboBox);
// END GENERATED CODE

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.edgeFiltersMenuItem.getIcon();
        this.setIconImage(ii.getImage());

        addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});

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



        //////////////////////////////
        //// Edge detection type /////
        //////////////////////////////
		sobelRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAlteredImage();
			}
		});
		laplaceRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAlteredImage();
			}
		});
		robertsRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAlteredImage();
			}
		});

        /////////////////////////////////////
        ///////// Prefilter /////////////////
        /////////////////////////////////////
        gaussianPrefilterRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gaussianPrefilterSelected();
			}
		});

		nonePrefilterRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nonePrefilterSelected();
			}
		});

		meanPrefilterRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				meanPrefilterSelected();
			}
		});

        windowComboBox.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        //windowSizeListValueChanged(e);
                        updateAlteredImage();
                    }

			    });

        ////////////////////////
        //// Threshold /////////
        ////////////////////////
        noneThresholdRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noneThresholdSelected();
			}
		});

        manualThresholdRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manualThresholdSelected();
			}
		});

        thresholdSlider.addChangeListener(new ChangeListener()
        {
          public void stateChanged(ChangeEvent e)
          {
            thresholdSliderValueChanged();
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


        original_height = image_frame.getJIPTImage().getImage().getHeight(this); // height of full size image
        original_width  = image_frame.getJIPTImage().getImage().getWidth(this);  // width  of full size image


        /////////////////////////////////////////////////
        ////// Set Button Groups ////////////////////////
        /////////////////////////////////////////////////
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(laplaceRadio);
        typeGroup.add(sobelRadio);
        typeGroup.add(robertsRadio);

        ButtonGroup prefilterGroup = new ButtonGroup();
        prefilterGroup.add(gaussianPrefilterRadio);
        prefilterGroup.add(nonePrefilterRadio);
        prefilterGroup.add(meanPrefilterRadio);

        ButtonGroup thresholdGroup = new ButtonGroup();
        thresholdGroup.add(noneThresholdRadio);
        thresholdGroup.add(manualThresholdRadio);

        ////////////////////////////////
        //// Set the initial states ////
        ////////////////////////////////
        sobelRadio.setSelected(true);
        nonePrefilterRadio.setSelected(true);
        noneThresholdRadio.setSelected(true);

        windowComboBox.setEnabled(false);
        thresholdSlider.setEnabled(false);


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
	//	System.exit(0);
	}

	public void okButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        Image i = image_frame.getJIPTImage().getImage();
        Image new_image = i;
        // Check for prefilter
        if(meanPrefilterRadio.isSelected())
        {
            //System.out.println("Mean prefilter selected");
            String windowSizeString = windowSizes[windowComboBox.getSelectedIndex()];
            int window_size = Integer.parseInt(windowSizeString.substring(0,1));
            new_image = filter_manager.mean(new_image,original_width, original_height, true, true, true, window_size, 1);
        }
        else if(gaussianPrefilterRadio.isSelected())
        {
            //System.out.println("Gaussian prefilter selected");
            new_image = filter_manager.gaussian(new_image,original_width, original_height);
        }
        //public Image mean(Image im, int im_width, int im_height,
        //          boolean filter_r, boolean filter_g, boolean filter_b,
        //                            int window_size, int iterations)

        // Execute edge detection
        if(laplaceRadio.isSelected())
        {
            // Laplace
            new_image = ToolManager.grayscale_image(new_image, original_width, original_height);
            new_image = filter_manager.laplace(new_image,original_width, original_height);

        }
        else if(sobelRadio.isSelected())
        {
            new_image = filter_manager.sobel(new_image,original_width, original_height);
        }
        else if(robertsRadio.isSelected())
        {
            new_image = filter_manager.roberts(new_image,original_width, original_height);
        }


        // Apply threshold
        if(noneThresholdRadio.isSelected())
        {
            // Do nothing
        }
        else
        {
            int threshold = thresholdSlider.getValue();
            new_image = tool_manager.threshold_image(new_image, original_width, original_height, 255,255,255,threshold);
        }

        image_frame.setFrameImage(new_image, true);
        thisWindowClosing(null);
        //this.setAlteredImage(new_image);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

    //////////////////
    /// Gaussian /////
    //////////////////
    public void gaussianPrefilterSelected()
    {
        windowComboBox.setEnabled(false);
        updateAlteredImage();
    }

    //////////////////
    /// Mean /////////
    //////////////////
    public void meanPrefilterSelected()
    {
       windowComboBox.setEnabled(true);
       updateAlteredImage();
    }

    ///////////////////////
    /// None Prefilter ////
    ///////////////////////
    public void nonePrefilterSelected()
    {
        windowComboBox.setEnabled(false);
        updateAlteredImage();
    }

    ////////////////////////////
    //// Manual Threshold //////
    ////////////////////////////
    public void manualThresholdSelected()
    {
        thresholdSlider.setEnabled(true);
        updateAlteredImage();
    }

    ////////////////////////////
    //// None   Threshold //////
    ////////////////////////////
    public void noneThresholdSelected()
    {
        thresholdSlider.setEnabled(false);
        updateAlteredImage();
    }

    ////////////////////////////////
    /// Update the altered image ///
    ////////////////////////////////
    public void updateAlteredImage()
    {
        Image i = original_image.getImage();
        Image new_image = i;
        // Check for prefilter
        if(meanPrefilterRadio.isSelected())
        {
            //System.out.println("Mean prefilter selected");
            String windowSizeString = windowSizes[windowComboBox.getSelectedIndex()];
            int window_size = Integer.parseInt(windowSizeString.substring(0,1));
            //System.out.println("Window size = " + window_size);
            new_image = filter_manager.mean(new_image,p_width, p_height, true, true, true, window_size, 1);

        }
        else if(gaussianPrefilterRadio.isSelected())
        {
            //System.out.println("Gaussian prefilter selected");
            new_image = filter_manager.gaussian(new_image,p_width, p_height);
        }

        // Execute edge detection
        if(laplaceRadio.isSelected())
        {
            // Laplace
            new_image = ToolManager.grayscale_image(new_image, p_width, p_height);
            new_image = filter_manager.laplace(new_image,p_width, p_height);

        }
        else if(sobelRadio.isSelected())
        {
            new_image = filter_manager.sobel(new_image,p_width, p_height);
        }
        else if(robertsRadio.isSelected())
        {
            new_image = filter_manager.roberts(new_image,p_width, p_height);
        }

        // Apply threshold
        if(noneThresholdRadio.isSelected())
        {
            // Do nothing
        }
        else
        {
            int threshold = thresholdSlider.getValue();
            //new_image = tool_manager.threshold_image(new_image, p_width, p_height, 255,255,255,threshold);
            new_image = ToolManager.threshold_image(new_image, p_width, p_height, 255,255,255,threshold);
        }

        this.setAlteredImage(new_image);
    }

    ////////////////////////////////////
    /// Threshold slider changed ///////
    ////////////////////////////////////
    public void thresholdSliderValueChanged()
    {
        manualValueLabel.setText(thresholdSlider.getValue() + "");
        updateAlteredImage();
    }

}
