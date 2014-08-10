package com.jipt.gui;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;

public class ImagePreviewFrame extends JFrame
{
      int preview_image_width;
      int preview_image_height;

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel originalImageLabel = new javax.swing.JLabel();
	javax.swing.JLabel alteredImageLabel = new javax.swing.JLabel();
	javax.swing.JLabel originalLabel = new javax.swing.JLabel();
	javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
	javax.swing.JButton okButton = new javax.swing.JButton();
	javax.swing.JButton cancelButton = new javax.swing.JButton();
// END GENERATED CODE

	public ImagePreviewFrame()
	{
        }

	public void initComponents() throws Exception
	{
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		originalImageLabel.setSize(new java.awt.Dimension(190, 180));
		originalImageLabel.setVisible(true);
		originalImageLabel.setLocation(new java.awt.Point(40, 10));
        originalImageLabel.setVerticalAlignment( JLabel.CENTER );
        originalImageLabel.setHorizontalAlignment( JLabel.CENTER );

		alteredImageLabel.setSize(new java.awt.Dimension(190, 180));
		alteredImageLabel.setVisible(true);
		alteredImageLabel.setLocation(new java.awt.Point(270, 10));
        alteredImageLabel.setVerticalAlignment( JLabel.CENTER );
        alteredImageLabel.setHorizontalAlignment( JLabel.CENTER );

		originalLabel.setSize(new java.awt.Dimension(50, 20));
		originalLabel.setVisible(true);
		originalLabel.setText("Before");
		originalLabel.setLocation(new java.awt.Point(110, 210));

		jLabel1.setSize(new java.awt.Dimension(40, 20));
		jLabel1.setVisible(true);
		jLabel1.setText("After");
		jLabel1.setLocation(new java.awt.Point(350, 210));

		okButton.setSize(new java.awt.Dimension(90, 35));
		okButton.setVisible(true);
		okButton.setText("OK");
		okButton.setLocation(new java.awt.Point(130, 410));

		cancelButton.setSize(new java.awt.Dimension(90, 35));
		cancelButton.setVisible(true);
		cancelButton.setText("Cancel");
		cancelButton.setLocation(new java.awt.Point(250, 410));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("ImagePreviewFrame.ImagePreviewFrame");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(originalImageLabel);
		getContentPane().add(alteredImageLabel);
		getContentPane().add(originalLabel);
		getContentPane().add(jLabel1);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);


		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE

            preview_image_width  = alteredImageLabel.getWidth();
            preview_image_height = alteredImageLabel.getHeight();
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
		System.exit(0);
	}

        ////////////////////////////////
        //// Sets the original image ///
        ////////////////////////////////
        public void setOriginalImage(Image i)
        {
            originalImageLabel.setIcon(new ImageIcon(i));
        }

        ////////////////////////////////
        //// Sets the altered image ////
        ////////////////////////////////
        public void setAlteredImage(Image i)
        {
            alteredImageLabel.setIcon(new ImageIcon(i));
        }




}
