package com.jipt.gui;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jipt.JIPTimage;
import com.jipt.ToolManager;



public class GrayscaleFrame extends ImagePreviewFrame
{
    ToolManager tool_manager = null;
    ImageFrame image_frame   = null;
    JIPTimage  original_image     = null;
    JIPTimage  altered_image      = null;
    int original_height;
    int original_width;
    int p_height;       // Height & Width of preview images
    int p_width;

    javax.swing.JLabel redValueLabel = new javax.swing.JLabel();
    javax.swing.JLabel greenValueLabel = new javax.swing.JLabel();
    javax.swing.JLabel blueValueLabel = new javax.swing.JLabel();
    javax.swing.JLabel thresholdValueLabel = new javax.swing.JLabel();

    javax.swing.JCheckBox thresholdCheckbox = new javax.swing.JCheckBox();


// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JRadioButton grayScaleRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton monochromeRadio = new javax.swing.JRadioButton();
	javax.swing.JSlider redSlider = new javax.swing.JSlider();
	javax.swing.JLabel redLabel = new javax.swing.JLabel();
	javax.swing.JLabel greenLabel = new javax.swing.JLabel();
	javax.swing.JSlider greenSlider = new javax.swing.JSlider();
	javax.swing.JLabel blueLabel = new javax.swing.JLabel();
	javax.swing.JSlider blueSlider = new javax.swing.JSlider();
	javax.swing.JLabel thresholdLabel = new javax.swing.JLabel();
        javax.swing.JSlider thresholdSlider = new javax.swing.JSlider();
// END GENERATED CODE

	public GrayscaleFrame()
	{
	}

	public GrayscaleFrame(ToolManager tm, ImageFrame im)
	{
          tool_manager = tm;
          image_frame = im;
	}

	public void initComponents() throws Exception
	{
        super.initComponents();
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		grayScaleRadio.setSize(new java.awt.Dimension(112, 21));
		grayScaleRadio.setVisible(true);
		grayScaleRadio.setText("Grayscale");
		grayScaleRadio.setLocation(new java.awt.Point(40, 240));

		monochromeRadio.setSize(new java.awt.Dimension(117, 21));
		monochromeRadio.setVisible(true);
		monochromeRadio.setText("Monochromatic");
		monochromeRadio.setLocation(new java.awt.Point(40, 270));

		redSlider.setSize(new java.awt.Dimension(200, 16));
		redSlider.setVisible(true);
		redSlider.setMaximum(255);
		redSlider.setMajorTickSpacing(10);
		redSlider.setLocation(new java.awt.Point(140, 300));

		redLabel.setSize(new java.awt.Dimension(40, 20));
		redLabel.setVisible(true);
		redLabel.setText("Red");
		redLabel.setLocation(new java.awt.Point(70, 300));

                redValueLabel.setSize(new java.awt.Dimension(40,20));
		redValueLabel.setVisible(true);
                redValueLabel.setText("100");
		redValueLabel.setLocation(new java.awt.Point(360, 300));

                blueValueLabel.setSize(new java.awt.Dimension(40,20));
		blueValueLabel.setVisible(true);
                blueValueLabel.setText("100");
		blueValueLabel.setLocation(new java.awt.Point(360, 340));

                greenValueLabel.setSize(new java.awt.Dimension(40,20));
		greenValueLabel.setVisible(true);
                greenValueLabel.setText("100");
		greenValueLabel.setLocation(new java.awt.Point(360, 320));

                thresholdValueLabel.setSize(new java.awt.Dimension(40,20));
		thresholdValueLabel.setVisible(true);
                thresholdValueLabel.setText("100");
		thresholdValueLabel.setLocation(new java.awt.Point(360, 360));

		greenLabel.setSize(new java.awt.Dimension(40, 20));
		greenLabel.setVisible(true);
		greenLabel.setText("Green");
		greenLabel.setLocation(new java.awt.Point(70, 320));

		greenSlider.setSize(new java.awt.Dimension(200, 16));
		greenSlider.setVisible(true);
		greenSlider.setMaximum(255);
		greenSlider.setMajorTickSpacing(10);
		greenSlider.setLocation(new java.awt.Point(140, 320));

		blueLabel.setSize(new java.awt.Dimension(40, 20));
		blueLabel.setVisible(true);
		blueLabel.setText("Blue");
		blueLabel.setLocation(new java.awt.Point(70, 340));

		blueSlider.setSize(new java.awt.Dimension(200, 16));
		blueSlider.setVisible(true);
		blueSlider.setMaximum(255);
		blueSlider.setMajorTickSpacing(10);
		blueSlider.setLocation(new java.awt.Point(140, 340));

  		thresholdSlider.setSize(new java.awt.Dimension(200, 16));
		thresholdSlider.setVisible(true);
		thresholdSlider.setMaximum(255);
		thresholdSlider.setMajorTickSpacing(10);
		thresholdSlider.setLocation(new java.awt.Point(140, 360));

		thresholdLabel.setSize(new java.awt.Dimension(61, 20));
		thresholdLabel.setVisible(true);
		thresholdLabel.setText("Threshold");
		thresholdLabel.setLocation(new java.awt.Point(70, 360));

                thresholdCheckbox.setSize(new java.awt.Dimension(20, 40));
                thresholdCheckbox.setVisible(true);
                thresholdCheckbox.setLocation(new java.awt.Point(50,350));

		//setSize(new java.awt.Dimension(500, 488));
		//getContentPane().setLayout(null);
		setTitle("Grayscale/Monochrome");

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.grayScaleColorMenuItem.getIcon();
        this.setIconImage(ii.getImage());

		//setLocation(new java.awt.Point(0, 0));
		getContentPane().add(grayScaleRadio);
		getContentPane().add(monochromeRadio);
		getContentPane().add(redSlider);
		getContentPane().add(redLabel);
		getContentPane().add(greenLabel);
		getContentPane().add(greenSlider);
		getContentPane().add(blueLabel);
		getContentPane().add(blueSlider);
                getContentPane().add(thresholdSlider);
		getContentPane().add(thresholdLabel);

                getContentPane().add(redValueLabel);
                getContentPane().add(greenValueLabel);
                getContentPane().add(blueValueLabel);
                getContentPane().add(thresholdValueLabel);
                getContentPane().add(thresholdCheckbox);

		thresholdCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				thresholdCheckboxStateChanged(e);
			}
		});

		grayScaleRadio.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				grayScaleRadioStateChanged(e);
			}
		});

  		monochromeRadio.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				monochromeRadioStateChanged(e);
			}
		});
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE
                //////////////////////////////////////
                ////////// Add slider listeners //////
                //////////////////////////////////////
                redSlider.setValue(255);
                blueSlider.setValue(255);
                greenSlider.setValue(255);
                thresholdSlider.setValue(100);

                redSlider.addChangeListener(new ChangeListener()
                                                {
                                                  public void stateChanged(ChangeEvent e)
                                                  {
                                                    sliderValueChanged();
                                                  }
                                                });
                greenSlider.addChangeListener(new ChangeListener()
                                                {
                                                  public void stateChanged(ChangeEvent e)
                                                  {
                                                    sliderValueChanged();
                                                  }
                                                });
                blueSlider.addChangeListener(new ChangeListener()
                                                {
                                                  public void stateChanged(ChangeEvent e)
                                                  {
                                                    sliderValueChanged();
                                                  }
                                                });

                thresholdSlider.addChangeListener(new ChangeListener()
                                                {
                                                  public void stateChanged(ChangeEvent e)
                                                  {
                                                    sliderValueChanged();
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



          ////////////////////////
          //// Set the images ////
          ////////////////////////
//          Image i = ToolManager.cloneImage( image_frame.getJIPTImage() );
          Image i = image_frame.getJIPTImage().getImage();
          original_image = new JIPTimage(i);
/*      //THE BELOW IS NOT NECESSARY TO SCALE THE PREVIEW FRAME
          altered_image = new JIPTimage();

          original_height = image_frame.getImageHeight();
          original_width  = image_frame.getImageWidth();
          System.out.println("Width, Height = " + original_width + " " + original_height);

          int percent_width_change = (int)  (preview_image_width*100.0/original_width);
          int percent_height_change = (int) (preview_image_height*100.0/original_height);
          System.out.println("******" + percent_width_change + " " + percent_height_change);
          //// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
          //ImageFrame i_f = new ImageFrame("", null);
          //i_f.setImage(i);
          tool_manager.resizeImage(original_image, percent_width_change, percent_height_change,
                                        original_width, original_height);
*/
            //Scale by width or height
            if( original_image.getImage().getWidth(this) > original_image.getImage().getHeight(this))
                original_image.setImage( original_image.getImage().getScaledInstance( originalImageLabel.getWidth(), -1, Image.SCALE_FAST ));
            else original_image.setImage( original_image.getImage().getScaledInstance( -1, originalImageLabel.getHeight(), Image.SCALE_FAST ));

            setOriginalImage(original_image.getImage());
            p_width  = original_image.getImage().getWidth(originalLabel);
            p_height = original_image.getImage().getHeight(originalLabel);

          ///////////////////////////////
          ///// Set the button groups ///
          ///////////////////////////////
          ButtonGroup bg = new ButtonGroup();
          bg.add(grayScaleRadio);
          bg.add(monochromeRadio);

          grayScaleRadio.setSelected(true);
          redSlider.setEnabled(false);
          greenSlider.setEnabled(false);
          blueSlider.setEnabled(false);
          thresholdSlider.setEnabled(false);

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

        /////////////////////////////////////
        /////////// OK button clicked ///////
        /////////////////////////////////////
	public void okButtonActionPerformed(java.awt.event.ActionEvent e)
	{
            if(grayScaleRadio.isSelected())
            {
              tool_manager.grayscale_image(image_frame);
              thisWindowClosing(null);
            }
            else
            {
              int r = Integer.parseInt(redValueLabel.getText());
              int g = Integer.parseInt(greenValueLabel.getText());
              int b = Integer.parseInt(blueValueLabel.getText());
              int t = Integer.parseInt(thresholdValueLabel.getText());
            //altered_image.setImage(i);
            //System.out.println("Before toolmanager r g b t = " + r + " " + g + " " + b + " " + t);
              if(!thresholdCheckbox.isSelected())
              {
                t = -1;
              }
              tool_manager.threshold_image(image_frame, r, g, b, t);
            //this.setAlteredImage(altered_image.getImage());
              thisWindowClosing(null);
            }
	}

        //////////////////////////////////
        ///// Cancel clicked /////////////
        //////////////////////////////////
	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        this.image_frame.removePrematureUndoImagePush(this);
        thisWindowClosing(null);
	}

        //////////////////////////////////////
        ////// Grayscale radio selected //////
        //////////////////////////////////////
	public void grayScaleRadioStateChanged(javax.swing.event.ChangeEvent e)
	{
            if(grayScaleRadio.isSelected())
            {
              redSlider.setEnabled(false);
              greenSlider.setEnabled(false);
              blueSlider.setEnabled(false);
              thresholdSlider.setEnabled(false);
              thresholdCheckbox.setEnabled(false);
            }

            // Fire event to update Altered screen w/ grayscale
            changeAlteredFrame();
	}

        ////////////////////////////////////
        //// Monochrome radio selected /////
        ////////////////////////////////////
 	public void monochromeRadioStateChanged(javax.swing.event.ChangeEvent e)
	{
            if(monochromeRadio.isSelected())
            {
              redSlider.setEnabled(true);
              greenSlider.setEnabled(true);
              blueSlider.setEnabled(true);
              thresholdCheckbox.setEnabled(true);
              if(thresholdCheckbox.isSelected())
                thresholdSlider.setEnabled(true);
              sliderValueChanged();
            }

            // Fire event to update Altered screen w/ monochrome
            changeAlteredFrame();
	}

        ///////////////////////////////////
        //// Threshold checkbox changed ///
        ///////////////////////////////////
        public void thresholdCheckboxStateChanged(javax.swing.event.ChangeEvent e)
        {
          if(thresholdCheckbox.isSelected())
                thresholdSlider.setEnabled(true);
          else
                thresholdSlider.setEnabled(false);
          changeAlteredFrame();
        }

        ///////////////////////////////////////////////////////
        //// Change the altered frame based on the controls ///
        ///////////////////////////////////////////////////////
        public void changeAlteredFrame()
        {
            if(grayScaleRadio.isSelected())
            {
              //System.out.println("Changing to grayscale...(w,h = " + p_width + " " + p_height);

              Image i = original_image.getImage();
               if(altered_image == null)
               {
                altered_image = new JIPTimage();
               }
              altered_image.setImage( i );
              tool_manager.grayscale_image(altered_image, p_width, p_height);
              this.setAlteredImage(altered_image.getImage());
            }
            else
            {
              Image i = original_image.getImage();
               if(altered_image == null)
               {
                altered_image = new JIPTimage();
               }

              int r = Integer.parseInt(redValueLabel.getText());
              int g = Integer.parseInt(greenValueLabel.getText());
              int b = Integer.parseInt(blueValueLabel.getText());
              int t = Integer.parseInt(thresholdValueLabel.getText());
              if(!thresholdCheckbox.isSelected())
              {
                t = -1;
              }
              altered_image.setImage(i);
              // System.out.println("Before toolmanager r g b t = " + r + " " + g + " " + b + " " + t);
              tool_manager.threshold_image(altered_image, p_width, p_height, r, g, b, t);
              this.setAlteredImage(altered_image.getImage());
            }
        }

        /////////////////////////////////////////////////////
        /////////////// Slider value changed ////////////////
        /////////////////////////////////////////////////////
        public void sliderValueChanged()
        {
          // Set the labels to correspond to slider values
          redValueLabel.setText(redSlider.getValue() + "");
          greenValueLabel.setText(greenSlider.getValue() + "");
          blueValueLabel.setText(blueSlider.getValue() + "");
          thresholdValueLabel.setText(thresholdSlider.getValue() + "");
          // render the image in the preview frame
          changeAlteredFrame();
        }
}
