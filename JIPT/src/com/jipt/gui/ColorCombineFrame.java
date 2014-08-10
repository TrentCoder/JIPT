package com.jipt.gui;
/*
    This class is used for combining the channels from various images into a new image.
    For example, the user can make a new image with the red channel from ImageX, the green
    channel from ImageY, and the blue channel from ImageZ.

    Trent Lucier
    12/6/2001

*/


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


public class ColorCombineFrame extends JFrame
{
    ColorManager color_manager = null;
    FrameManager frame_manager = null;
    MainFrame    main_frame    = null;
    ArrayList    frame_list    = null;

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel alteredImageLabel = new javax.swing.JLabel();
	javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
	javax.swing.JButton okButton = new javax.swing.JButton();
	javax.swing.JButton cancelButton = new javax.swing.JButton();
	javax.swing.JLabel redChannelLabel = new javax.swing.JLabel();
	javax.swing.JLabel greenChannelLabel = new javax.swing.JLabel();
	javax.swing.JLabel blueChannelLabel = new javax.swing.JLabel();
	javax.swing.JComboBox redComboBox = new javax.swing.JComboBox();
	javax.swing.JComboBox greenComboBox = new javax.swing.JComboBox();
	javax.swing.JComboBox blueComboBox = new javax.swing.JComboBox();
// END GENERATED CODE

    javax.swing.JRadioButton redRadio = new javax.swing.JRadioButton();
    javax.swing.JRadioButton greenRadio = new javax.swing.JRadioButton();
    javax.swing.JRadioButton blueRadio = new javax.swing.JRadioButton();

    javax.swing.JLabel scaleLabel = new javax.swing.JLabel();

	public ColorCombineFrame(ColorManager cm, FrameManager fm, MainFrame mf)
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
		alteredImageLabel.setSize(new java.awt.Dimension(190, 180));
		alteredImageLabel.setVisible(true);
		alteredImageLabel.setLocation(new java.awt.Point(90, 20));



		jLabel1.setSize(new java.awt.Dimension(40, 20));
		jLabel1.setVisible(true);
		jLabel1.setText("After");
		jLabel1.setLocation(new java.awt.Point(170, 220));

		okButton.setSize(new java.awt.Dimension(90, 35));
		okButton.setVisible(true);
		okButton.setText("OK");
		okButton.setLocation(new java.awt.Point(70, 410));

		cancelButton.setSize(new java.awt.Dimension(90, 35));
		cancelButton.setVisible(true);
		cancelButton.setText("Cancel");
		cancelButton.setLocation(new java.awt.Point(190, 410));

		redChannelLabel.setSize(new java.awt.Dimension(111, 20));
		redChannelLabel.setVisible(true);
		redChannelLabel.setText("Red Channel");
		redChannelLabel.setLocation(new java.awt.Point(30, 260));

		greenChannelLabel.setSize(new java.awt.Dimension(110, 20));
		greenChannelLabel.setVisible(true);
		greenChannelLabel.setText("Green Channel");
		greenChannelLabel.setLocation(new java.awt.Point(30, 310));

		blueChannelLabel.setSize(new java.awt.Dimension(110, 20));
		blueChannelLabel.setVisible(true);
		blueChannelLabel.setText("Blue Channel");
		blueChannelLabel.setLocation(new java.awt.Point(30, 360));

		redComboBox.setSize(new java.awt.Dimension(150, 30));
		redComboBox.setVisible(true);
		redComboBox.setLocation(new java.awt.Point(150, 260));

		greenComboBox.setSize(new java.awt.Dimension(150, 30));
		greenComboBox.setVisible(true);
		greenComboBox.setLocation(new java.awt.Point(150, 310));

		blueComboBox.setSize(new java.awt.Dimension(150, 30));
		blueComboBox.setVisible(true);
		blueComboBox.setLocation(new java.awt.Point(150, 360));

        redRadio.setSize(new java.awt.Dimension(40, 30));
        redRadio.setVisible(true);
        redRadio.setLocation(new java.awt.Point(340, 260));

        greenRadio.setSize(new java.awt.Dimension(40, 30));
        greenRadio.setVisible(true);
        greenRadio.setLocation(new java.awt.Point(340, 310));

        blueRadio.setSize(new java.awt.Dimension(40, 30));
        blueRadio.setVisible(true);
        blueRadio.setLocation(new java.awt.Point(340, 360));

        ButtonGroup bg = new ButtonGroup();
        bg.add(redRadio);
        bg.add(greenRadio);
        bg.add(blueRadio);
        redRadio.setSelected(true);

        scaleLabel.setSize(new java.awt.Dimension(110, 20));
		scaleLabel.setVisible(true);
		scaleLabel.setText("Result Size");
		scaleLabel.setLocation(new java.awt.Point(320, 230));

		setSize(new java.awt.Dimension(450, 490));
		getContentPane().setLayout(null);
		setTitle("Combine Channels");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(alteredImageLabel);
		getContentPane().add(jLabel1);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);
		getContentPane().add(redChannelLabel);
		getContentPane().add(greenChannelLabel);
		getContentPane().add(blueChannelLabel);
		getContentPane().add(redComboBox);
		getContentPane().add(greenComboBox);
		getContentPane().add(blueComboBox);
        getContentPane().add(scaleLabel);
        getContentPane().add(redRadio);
        getContentPane().add(greenRadio);
        getContentPane().add(blueRadio);

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.combineColorMenuItem.getIcon();
        this.setIconImage(ii.getImage());


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
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});

        redRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        greenRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        blueRadio.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });
// END GENERATED CODE

        ////////////////////////////////////////
        /////// Add file names to list boxes ///
        ////////////////////////////////////////
        redComboBox.removeAllItems();
        greenComboBox.removeAllItems();
        blueComboBox.removeAllItems();
        for(int i = 0; i < frame_list.size(); i++)
        {
            ImageFrame i_frame = (ImageFrame) frame_list.get(i);
            String     name    = i_frame.getTitle();
            redComboBox.addItem(name);
            greenComboBox.addItem(name);
            blueComboBox.addItem(name);
        }


        ////////////////////////////
        //// Combo box listeners ///
        ////////////////////////////
        redComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        greenComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       updateAlteredImage();
                   }
        });

        blueComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
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
        Image redImage = ((ImageFrame)frame_list.get(redComboBox.getSelectedIndex())).getJIPTImage().getImage();
        Image greenImage = ((ImageFrame)frame_list.get(greenComboBox.getSelectedIndex())).getJIPTImage().getImage();
        Image blueImage = ((ImageFrame)frame_list.get(blueComboBox.getSelectedIndex())).getJIPTImage().getImage();

        int width  = 0;
        int height = 0;

        //////////////////////////////////////////
        ///// Determine which images to scale ////
        //////////////////////////////////////////
        if(redRadio.isSelected())
        {
            width  = redImage.getWidth(main_frame);
            height = redImage.getHeight(main_frame);

            greenImage = greenImage.getScaledInstance(width, height, Image.SCALE_FAST);
            blueImage  = blueImage.getScaledInstance(width, height, Image.SCALE_FAST);

            do
            {
                // do nothing while images finish loading
            }while( greenImage.getWidth(main_frame) == -1 ||
                    greenImage.getHeight(main_frame) == -1 ||
                    blueImage.getWidth(main_frame) == -1 ||
                    blueImage.getHeight(main_frame) == -1);
        }
        else if(greenRadio.isSelected())
        {
            width  = greenImage.getWidth(main_frame);
            height = greenImage.getHeight(main_frame);

            redImage   = redImage.getScaledInstance(width, height, Image.SCALE_FAST);
            blueImage  = blueImage.getScaledInstance(width, height, Image.SCALE_FAST);

            do
            {
                // do nothing while images finish loading
            }while( redImage.getWidth(main_frame) == -1 ||
                    redImage.getHeight(main_frame) == -1 ||
                    blueImage.getWidth(main_frame) == -1 ||
                    blueImage.getHeight(main_frame) == -1);
        }
        else if(blueRadio.isSelected())
        {
            width  = blueImage.getWidth(main_frame);
            height = blueImage.getHeight(main_frame);

            greenImage = greenImage.getScaledInstance(width, height, Image.SCALE_FAST);
            redImage   = redImage.getScaledInstance(width, height, Image.SCALE_FAST);

            do
            {
                // do nothing while images finish loading
            }while( greenImage.getWidth(main_frame) == -1 ||
                    greenImage.getHeight(main_frame) == -1 ||
                    redImage.getWidth(main_frame) == -1 ||
                    redImage.getHeight(main_frame) == -1);
        }

        /////////////////////////////////
        /// Build image and set name ////
        /////////////////////////////////
        Image new_image = color_manager.combine(redImage, greenImage, blueImage);
        ImageFrame new_image_frame = main_frame.createNewImage(new_image);

        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

    public void updateAlteredImage()
    {
        Image redImage = ((ImageFrame)frame_list.get(redComboBox.getSelectedIndex())).getJIPTImage().getImage();
        Image greenImage = ((ImageFrame)frame_list.get(greenComboBox.getSelectedIndex())).getJIPTImage().getImage();
        Image blueImage = ((ImageFrame)frame_list.get(blueComboBox.getSelectedIndex())).getJIPTImage().getImage();

        int width  = 0;
        int height = 0;

        //////////////////////////////////////////
        ///// Determine which images to scale ////
        //////////////////////////////////////////
        if(redRadio.isSelected())
        {
            width  = redImage.getWidth(main_frame);
            height = redImage.getHeight(main_frame);

            greenImage = greenImage.getScaledInstance(width, height, Image.SCALE_FAST);
            blueImage  = blueImage.getScaledInstance(width, height, Image.SCALE_FAST);

            do
            {
                // do nothing while images finish loading
            }while( greenImage.getWidth(main_frame) == -1 ||
                    greenImage.getHeight(main_frame) == -1 ||
                    blueImage.getWidth(main_frame) == -1 ||
                    blueImage.getHeight(main_frame) == -1);
        }
        else if(greenRadio.isSelected())
        {
            width  = greenImage.getWidth(main_frame);
            height = greenImage.getHeight(main_frame);

            redImage   = redImage.getScaledInstance(width, height, Image.SCALE_FAST);
            blueImage  = blueImage.getScaledInstance(width, height, Image.SCALE_FAST);

            do
            {
                // do nothing while images finish loading
            }while( redImage.getWidth(main_frame) == -1 ||
                    redImage.getHeight(main_frame) == -1 ||
                    blueImage.getWidth(main_frame) == -1 ||
                    blueImage.getHeight(main_frame) == -1);
        }
        else if(blueRadio.isSelected())
        {
            width  = blueImage.getWidth(main_frame);
            height = blueImage.getHeight(main_frame);

            greenImage = greenImage.getScaledInstance(width, height, Image.SCALE_FAST);
            redImage   = redImage.getScaledInstance(width, height, Image.SCALE_FAST);

            do
            {
                // do nothing while images finish loading
            }while( greenImage.getWidth(main_frame) == -1 ||
                    greenImage.getHeight(main_frame) == -1 ||
                    redImage.getWidth(main_frame) == -1 ||
                    redImage.getHeight(main_frame) == -1);
        }

        /////////////////////////////////////////////
        ///// Scale images to preview label size ////
        /////////////////////////////////////////////
        if( width > height)
        {
            redImage   = redImage.getScaledInstance(alteredImageLabel.getWidth(), -1, Image.SCALE_FAST );
            greenImage = greenImage.getScaledInstance(alteredImageLabel.getWidth(), -1, Image.SCALE_FAST );
            blueImage  = blueImage.getScaledInstance(alteredImageLabel.getWidth(), -1, Image.SCALE_FAST );
        }
        else
        {
            redImage   = redImage.getScaledInstance(-1, alteredImageLabel.getHeight(), Image.SCALE_FAST );
            greenImage = greenImage.getScaledInstance(-1, alteredImageLabel.getHeight(), Image.SCALE_FAST );
            blueImage = blueImage.getScaledInstance(-1, alteredImageLabel.getHeight(), Image.SCALE_FAST );

        }

        do
        {
            // do nothing while images finish loading
        }while( blueImage.getWidth(main_frame) == -1 ||
                blueImage.getHeight(main_frame) == -1 ||
                greenImage.getWidth(main_frame) == -1 ||
                greenImage.getHeight(main_frame) == -1 ||
                redImage.getWidth(main_frame) == -1 ||
                redImage.getHeight(main_frame) == -1);


        Image new_image = color_manager.combine(redImage, greenImage, blueImage);

        alteredImageLabel.setIcon(new ImageIcon(new_image));
    }
}


