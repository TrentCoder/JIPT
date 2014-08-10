package com.jipt.gui;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

import com.jipt.JIPTimage;
import com.jipt.ToolManager;
public class NegativeFrame extends ImagePreviewFrame
{
    ToolManager tool_manager = null;
    ImageFrame  image_frame  = null;

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
	//javax.swing.JButton okButton = new javax.swing.JButton();
	//javax.swing.JButton cancelButton = new javax.swing.JButton();
	javax.swing.JCheckBox redCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox greenCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox blueCheckbox = new javax.swing.JCheckBox();
// END GENERATED CODE

	public NegativeFrame()
	{
	}

    public NegativeFrame(ToolManager tm, ImageFrame i)
	{
            image_frame = i;
            tool_manager = tm;
	}

	public void initComponents() throws Exception
	{
        super.initComponents();
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		okButton.setSize(new java.awt.Dimension(90, 35));
		okButton.setVisible(true);
		okButton.setText("OK");
		okButton.setLocation(new java.awt.Point(130, 410));

		cancelButton.setSize(new java.awt.Dimension(90, 35));
		cancelButton.setVisible(true);
		cancelButton.setText("Cancel");
		cancelButton.setLocation(new java.awt.Point(250, 410));

		redCheckbox.setSize(new java.awt.Dimension(134, 21));
		redCheckbox.setVisible(true);
		redCheckbox.setText("Red");
		redCheckbox.setLocation(new java.awt.Point(80, 282));

		greenCheckbox.setSize(new java.awt.Dimension(97, 21));
		greenCheckbox.setVisible(true);
		greenCheckbox.setText("Green");
		greenCheckbox.setLocation(new java.awt.Point(80, 310));

		blueCheckbox.setSize(new java.awt.Dimension(108, 21));
		blueCheckbox.setVisible(true);
		blueCheckbox.setText("Blue");
		blueCheckbox.setLocation(new java.awt.Point(80, 340));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("Negative Image");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);
		getContentPane().add(redCheckbox);
		getContentPane().add(greenCheckbox);
		getContentPane().add(blueCheckbox);

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.negativeColorMenuItem.getIcon();
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

        redCheckbox.setSelected(true);
        greenCheckbox.setSelected(true);
        blueCheckbox.setSelected(true);

		redCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				redCheckboxStateChanged(e);
			}
		});
		greenCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				greenCheckboxStateChanged(e);
			}
		});
		blueCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				blueCheckboxStateChanged(e);
			}
		});
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE

          ////////////////////////
          //// Set the images ////
          ////////////////////////
          Image i = image_frame.getJIPTImage().getImage();
          original_image = new JIPTimage(i);

          // System.out.println("W, H = " + original_image.getImage().getWidth(this) + " " +   original_image.getImage().getHeight(this));
          if( original_image.getImage().getWidth(this) > original_image.getImage().getHeight(this))
                original_image.setImage( original_image.getImage().getScaledInstance( originalImageLabel.getWidth(), -1, Image.SCALE_FAST ));
            else original_image.setImage( original_image.getImage().getScaledInstance( -1, originalImageLabel.getHeight(), Image.SCALE_FAST ));

            setOriginalImage(original_image.getImage());
            p_width  = original_image.getImage().getWidth(originalLabel);
            p_height = original_image.getImage().getHeight(originalLabel);

            stateChanged();
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
        boolean r = redCheckbox.isSelected();
        boolean g = greenCheckbox.isSelected();
        boolean b = blueCheckbox.isSelected();
        Image j_im = tool_manager.negative_image(image_frame.getJIPTImage(), image_frame.getImageWidth(), image_frame.getImageHeight(), r, g, b);
        //JIPTimage jip = new JIPTimage(j_im);
        image_frame.setFrameImage(j_im, false);
        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        //remove undo push
        this.image_frame.removePrematureUndoImagePush(this);
        this.thisWindowClosing(null);
	}

	public void redCheckboxStateChanged(javax.swing.event.ChangeEvent e)
	{
        stateChanged();
	}

	public void greenCheckboxStateChanged(javax.swing.event.ChangeEvent e)
	{
        stateChanged();
	}

	public void blueCheckboxStateChanged(javax.swing.event.ChangeEvent e)
	{
        stateChanged();
	}

    ////////////////////////////////////////
    ///// Update the AFTER image frame /////
    ////////////////////////////////////////
    public void stateChanged()
    {
        boolean r = redCheckbox.isSelected();
        boolean g = greenCheckbox.isSelected();
        boolean b = blueCheckbox.isSelected();

       Image i = original_image.getImage();
       if(altered_image == null)
       {
        altered_image = new JIPTimage();
       }
       altered_image.setImage(i);
       tool_manager.negative_image(altered_image, p_width, p_height, r, g, b);
       this.setAlteredImage(altered_image.getImage());

       // Disable OK if nothing selected
       if(r || g || b)
            okButton.setEnabled(true);
       else
            okButton.setEnabled(false);
    }






}