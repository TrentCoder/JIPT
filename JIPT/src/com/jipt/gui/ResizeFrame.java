package com.jipt.gui;
/*
	Trent Lucier
	10/14/2001
	ResizeFrame.java

	This frame provides the options for resizing an image.

*/

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.jipt.JIPTAlert;
import com.jipt.ToolManager;

public class ResizeFrame extends JFrame
{
        ToolManager tool_manager = null;
        ImageFrame im_frame = null;


	int original_width  = 1; // gets changed
	int original_height = 1; // gets changed

	public boolean changing_pixels = true;

	java.awt.event.TextListener pixelWidthListener = null;
	java.awt.event.TextListener percentageWidthListener = null;
		java.awt.event.TextListener pixelHeightListener = null;
	java.awt.event.TextListener percentageHeightListener = null;



// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JButton jButton1 = new javax.swing.JButton();
	javax.swing.JButton jButton2 = new javax.swing.JButton();
	javax.swing.JRadioButton radioButtonPixel = new javax.swing.JRadioButton();
	javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
	javax.swing.JLabel jLabel2 = new javax.swing.JLabel();
	javax.swing.JRadioButton radioButtonPercentage = new javax.swing.JRadioButton();
	javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
	javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
	javax.swing.JCheckBox aspectRatioCheckbox = new javax.swing.JCheckBox();
	javax.swing.JLabel aspectRatiolabel = new javax.swing.JLabel();
	java.awt.TextField ratioTextField = new java.awt.TextField();
	java.awt.TextField pixelWidthField = new java.awt.TextField();
	java.awt.TextField pixelHeightField = new java.awt.TextField();
	java.awt.TextField percentageWidthField = new java.awt.TextField();
	java.awt.TextField percentageHeightField = new java.awt.TextField();
// END GENERATED CODE

	public ResizeFrame()
	{
	}

 	public ResizeFrame(ToolManager tm, ImageFrame i)
	{
          setToolManager(tm);
          setImageFrame(i);
	}

        /////////////////////////////
        ///// Set the toolmanager ///
        /////////////////////////////
        public void setToolManager(ToolManager tm)
        {
          tool_manager = tm;
        }

        ///////////////////////////////
        ///// Set the image frame /////
        ///////////////////////////////
        public void setImageFrame(ImageFrame im)
        {
          im_frame = im;
          original_width  = im.getImageWidth();
          original_height = im.getImageHeight();
        }

	public void initComponents() throws Exception
	{
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		jButton1.setSize(new java.awt.Dimension(130, 30));
		jButton1.setVisible(true);
		jButton1.setText("OK");
		jButton1.setLocation(new java.awt.Point(30, 210));

		jButton2.setSize(new java.awt.Dimension(130, 30));
		jButton2.setVisible(true);
		jButton2.setText("Cancel");
		jButton2.setLocation(new java.awt.Point(180, 210));

		radioButtonPixel.setSize(new java.awt.Dimension(101, 21));
		radioButtonPixel.setVisible(true);
		radioButtonPixel.setText("Pixel Size");
		radioButtonPixel.setLocation(new java.awt.Point(40, 10));

		jLabel1.setSize(new java.awt.Dimension(40, 20));
		jLabel1.setVisible(true);
		jLabel1.setText("Width");
		jLabel1.setLocation(new java.awt.Point(40, 40));

		jLabel2.setSize(new java.awt.Dimension(40, 20));
		jLabel2.setVisible(true);
		jLabel2.setText("Height");
		jLabel2.setLocation(new java.awt.Point(170, 40));

		radioButtonPercentage.setSize(new java.awt.Dimension(150, 21));
		radioButtonPercentage.setVisible(true);
		radioButtonPercentage.setText("Percentage of Original");
		radioButtonPercentage.setLocation(new java.awt.Point(40, 90));

		jLabel3.setSize(new java.awt.Dimension(40, 20));
		jLabel3.setVisible(true);
		jLabel3.setText("Width");
		jLabel3.setLocation(new java.awt.Point(40, 120));

		jLabel4.setSize(new java.awt.Dimension(40, 20));
		jLabel4.setVisible(true);
		jLabel4.setText("Height");
		jLabel4.setLocation(new java.awt.Point(170, 120));

		aspectRatioCheckbox.setSize(new java.awt.Dimension(165, 21));
		aspectRatioCheckbox.setVisible(true);
		aspectRatioCheckbox.setText("Maintain Aspect Ration of");
		aspectRatioCheckbox.setLocation(new java.awt.Point(35, 166));

		aspectRatiolabel.setSize(new java.awt.Dimension(50, 30));
		aspectRatiolabel.setVisible(true);
		aspectRatiolabel.setText("to 1");
		aspectRatiolabel.setForeground(new java.awt.Color(0, 0, 0));
		aspectRatiolabel.setLocation(new java.awt.Point(290, 160));

		ratioTextField.setSize(new java.awt.Dimension(70, 20));
		ratioTextField.setVisible(true);
		ratioTextField.setLocation(new java.awt.Point(210, 165));

		pixelWidthField.setSize(new java.awt.Dimension(60, 20));
		pixelWidthField.setVisible(true);
		pixelWidthField.setLocation(new java.awt.Point(90, 40));

		pixelHeightField.setSize(new java.awt.Dimension(60, 20));
		pixelHeightField.setVisible(true);
		pixelHeightField.setLocation(new java.awt.Point(230, 40));

		percentageWidthField.setSize(new java.awt.Dimension(60, 20));
		percentageWidthField.setVisible(true);
		percentageWidthField.setLocation(new java.awt.Point(90, 120));

		percentageHeightField.setSize(new java.awt.Dimension(60, 20));
		percentageHeightField.setVisible(true);
		percentageHeightField.setLocation(new java.awt.Point(230, 120));

		setSize(new java.awt.Dimension(349, 300));
		getContentPane().setLayout(null);
		setTitle("Resize Image");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(jButton1);
		getContentPane().add(jButton2);
		getContentPane().add(radioButtonPixel);
		getContentPane().add(jLabel1);
		getContentPane().add(jLabel2);
		getContentPane().add(radioButtonPercentage);
		getContentPane().add(jLabel3);
		getContentPane().add(jLabel4);
		getContentPane().add(aspectRatioCheckbox);
		getContentPane().add(aspectRatiolabel);
		getContentPane().add(ratioTextField);
		getContentPane().add(pixelWidthField);
		getContentPane().add(pixelHeightField);
		getContentPane().add(percentageWidthField);
		getContentPane().add(percentageHeightField);

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.resizeToolsMenuItem.getIcon();
        this.setIconImage(ii.getImage());


		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jButton1ActionPerformed(e);
			}
		});
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jButton2ActionPerformed(e);
			}
		});
		radioButtonPixel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				radioButtonPixelActionPerformed(e);
			}
		});
		radioButtonPercentage.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				radioButtonPercentageActionPerformed(e);
			}
		});
		aspectRatioCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				aspectRatioCheckboxStateChanged(e);
			}
		});
		ratioTextField.addTextListener(new java.awt.event.TextListener() {
			public void textValueChanged(java.awt.event.TextEvent e) {
				ratioTextFieldTextValueChanged(e);
			}
		});
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE


		////////////////////////////
		///// Text Listeners ///////
		////////////////////////////
		percentageWidthListener = new java.awt.event.TextListener() {
										public void textValueChanged(java.awt.event.TextEvent e) {
											percentageWidthFieldTextValueChanged(e);
										}
									};

        percentageHeightListener = new java.awt.event.TextListener() {
										public void textValueChanged(java.awt.event.TextEvent e) {
											percentageHeightFieldTextValueChanged(e);
										}
									};

		pixelWidthListener =	new java.awt.event.TextListener()
								{
									public void textValueChanged(java.awt.event.TextEvent e)
									{
										pixelWidthFieldTextValueChanged(e);
									}
								};
		pixelWidthField.addTextListener(pixelWidthListener);


		pixelHeightListener = new java.awt.event.TextListener()
								{
									public void textValueChanged(java.awt.event.TextEvent e)
									{
										pixelHeightFieldTextValueChanged(e);
									}
								};
		pixelHeightField.addTextListener(pixelHeightListener);






		pixelWidthField.setText("" + original_width);
		pixelHeightField.setText("" + original_height);

		percentageHeightField.setText("100");
		percentageWidthField.setText("100");

		radioButtonPercentage.setSelected(false);
		radioButtonPixel.setSelected(true);
		percentageWidthField.setEnabled(false);
		percentageHeightField.setEnabled(false);

        changing_pixels = true;

		ratioTextField.setText("" + original_width*1.0/original_height);
		ratioTextField.setEnabled(false);

	}

	// Close the window when the close box is clicked
	void thisWindowClosing(java.awt.event.WindowEvent e)
	{
		setVisible(false);
		dispose();
		//System.exit(0);
	}


	////////////////////
	///// OK clicked ///
	////////////////////
	public void jButton1ActionPerformed(java.awt.event.ActionEvent e)
	{
        int percent_width = 0;
        int percent_height = 0;

        try
        {
            percent_width = (int) Integer.parseInt(percentageWidthField.getText());
            if(percent_width <= 0)
                throw new Exception();
        }
        catch(Exception e1)
        {
            ///////////////////////////////////
            //// User entered invalid input ///
            ///////////////////////////////////
            percentageWidthField.requestFocus();
            percentageWidthField.selectAll();
            JIPTAlert.error(this, "Value must be a positive integer", "Invalid Size","OK");
            return;
        }

        try
        {
            percent_height = (int) Integer.parseInt(percentageHeightField.getText());
            if(percent_height <= 0)
                throw new Exception();
        }
        catch(Exception e2)
        {
            ///////////////////////////////////
            //// User entered invalid input ///
            ///////////////////////////////////
            percentageHeightField.requestFocus();
            percentageHeightField.selectAll();
            JIPTAlert.error(this, "Value must be a positive integer", "Invalid Size","OK");
            return;
        }

            tool_manager.resizeImage(im_frame,percent_width, percent_height);
            thisWindowClosing(null);
	}

	///////////////////////
	//// CANCEL clicked ///
	///////////////////////
	public void jButton2ActionPerformed(java.awt.event.ActionEvent e)
	{
		thisWindowClosing(null);
	}


	public void aspectRatioCheckboxStateChanged(javax.swing.event.ChangeEvent e)
	{
        if( aspectRatioCheckbox.isSelected() )
        {
			//pixelWidthField.removeTextListener(pixelWidthListener);
			//pixelHeightField.removeTextListener(pixelHeightListener);
            this.pixelHeightField.setEnabled( false );
            this.percentageHeightField.setEnabled( false );
        }
        else
        {
            if( this.radioButtonPixel.isSelected() )
            {
               this.pixelHeightField.setEnabled( true );
				pixelWidthField.addTextListener(pixelWidthListener);
            }
            else
            {
               this.percentageHeightField.setEnabled( true );
				pixelHeightField.addTextListener(pixelHeightListener);
            }
        }
	}



	public void radioButtonPixelActionPerformed(java.awt.event.ActionEvent e)
	{
        changing_pixels = true;
    		// Disable other radio button and text fields
    		radioButtonPercentage.setSelected(false);

    		percentageWidthField.setEnabled(false);
    		percentageHeightField.setEnabled(false);

    		// enable these text fields
    		pixelWidthField.setEnabled(true);
            if( this.aspectRatioCheckbox.isSelected() )
            {
    		    pixelHeightField.setEnabled(false);
               // pixelHeightField.removeTextListener(pixelHeightListener);
            }
           else
            {
        		pixelHeightField.addTextListener(pixelHeightListener);
                pixelHeightField.setEnabled(true);
            }

    		pixelWidthField.addTextListener(pixelWidthListener);
    	//	percentageWidthField.removeTextListener(percentageWidthListener);
    	//	percentageHeightField.removeTextListener(percentageHeightListener);

            changing_pixels = true;
	}

	public void radioButtonPercentageActionPerformed(java.awt.event.ActionEvent e)
	{
        changing_pixels = false;

		// Disable other radio button and text fields
		radioButtonPixel.setSelected(false);

		pixelWidthField.setEnabled(false);
		pixelHeightField.setEnabled(false);

		// enable these text fields
		percentageWidthField.setEnabled(true);
        if( this.aspectRatioCheckbox.isSelected() )
        {
		    percentageHeightField.setEnabled(false);
       		//percentageHeightField.removeTextListener(percentageHeightListener);
        }
        else
        {
    		percentageHeightField.addTextListener(percentageHeightListener);
            percentageHeightField.setEnabled(true);
        }

	//	pixelWidthField.removeTextListener(pixelWidthListener);
		//pixelHeightField.removeTextListener(pixelHeightListener);
		percentageWidthField.addTextListener(percentageWidthListener);
	}


	public void ratioTextFieldTextValueChanged(java.awt.event.TextEvent e)
	{
	}


	public void pixelHeightFieldTextValueChanged(java.awt.event.TextEvent e)
	{
    	try
		{
			int pixel_height = Integer.parseInt(pixelHeightField.getText());
            if(changing_pixels)
            {
			    percentageHeightField.setText((int) (pixel_height*100.0/original_height) + "");
              //  System.out.println("Chaning % height field");
            }
			if( aspectRatioCheckbox.isSelected()&& pixelHeightField.hasFocus())
			{
				double ratio = Double.parseDouble( ratioTextField.getText() );
				pixelWidthField.setText((int) (pixel_height*ratio) + "");
			}


		}
		catch(Exception e1)
		{
			// System.out.println("Exception in pixelHeightTextValueChange");
		}



	}


	//////////////////////////
	///// Pixel Width ////////
	//////////////////////////

	public void pixelWidthFieldTextValueChanged(java.awt.event.TextEvent e)
	{
    	try
		{
			int pixel_width = Integer.parseInt(pixelWidthField.getText());

			if(changing_pixels)
                percentageWidthField.setText((int) (pixel_width*100.0/original_width) + "");
			if(aspectRatioCheckbox.isSelected() && pixelWidthField.hasFocus())
			{
				double ratio = Double.parseDouble( ratioTextField.getText() );
				pixelHeightField.setText((int) (pixel_width/ratio) + "");
			}
		}
		catch(Exception e1)
		{
			// System.out.println("Exception in pixelWidthTextValueChange");
		}

	}

	public void percentageWidthFieldTextValueChanged(java.awt.event.TextEvent e)
	{
		try
		{
			int percentage_width = Integer.parseInt(percentageWidthField.getText());
            if(!changing_pixels)
			    pixelWidthField.setText((int) (percentage_width*original_width/100.0) + "");
			if( aspectRatioCheckbox.isSelected() && percentageWidthField.hasFocus())
			{
				//percentageHeightField.removeTextListener(percentageHeightListener);
				float ratio = Float.parseFloat(ratioTextField.getText());
				//percentageHeightField.setText((int) (percentage_width/ratio) + "");
                percentageHeightField.setText(percentageWidthField.getText());
				percentageHeightField.addTextListener(percentageHeightListener);
			}
			}
		catch(Exception e1)
		{
			// System.out.println("Exception in percentageWidthTextValueChange");
		}

	}

	public void percentageHeightFieldTextValueChanged(java.awt.event.TextEvent e)
	{
		try
		{
            //System.out.println("Changing percent height");
			int percentage_height = Integer.parseInt(percentageHeightField.getText());
            if(!changing_pixels)
			    pixelHeightField.setText((int) (percentage_height*original_height/100.0) + "");
		}
		catch(Exception e1)
		{
			// System.out.println("Exception in percentageHeightTextValueChange");
		}

	}




}
