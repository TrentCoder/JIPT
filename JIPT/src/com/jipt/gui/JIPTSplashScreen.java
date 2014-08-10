package com.jipt.gui;
/*
    This class is used or JIPT's initial splash screen.  It has NO CONTROLS.
    MainFrame kills this window after several seconds.

*/


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import javax.swing.JWindow;

import com.jipt.JIPTsettings;


public class JIPTSplashScreen extends JWindow
{
	Image image          = null; // Image to load for splash screen

	int width = 0;      // Width of image
	int height = 0;     // Height of image

	int x = 0;
	int y = 0;

	public JIPTSplashScreen()
	{
		super();

		MediaTracker mt = new MediaTracker(this);

		image = Toolkit.getDefaultToolkit().getImage(JIPTsettings.JIPT_SPLASH_SCREEN_PATH);

		mt.addImage(image, 0);
		try
		{
			mt.waitForID(0);
		}
		catch(InterruptedException e2)
		{
			// System.out.println("Exception loading splash screen");
		}
		width = image.getWidth(this);
		height = image.getHeight(this);

        this.setSize(width, height);

		//////////////////////////////////
		//// Get the screen resolution ///
		//////////////////////////////////
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		int screen_width = (int) dim.getWidth();
		int screen_height = (int) dim.getHeight();
		//System.out.println("Screen width = " + screen_width);
		//System.out.println("Screen height = " + screen_height);

		// Center Image on Screen
		x = (screen_width - width)/2;
		y = (screen_height - height)/2;
		setLocation(x, y);
		this.setSize(width, height);

	}

    // Just draw the image
	public void paint(Graphics g)
    {
	    g.drawImage(image, 0, 0, Color.white, this);
    }


}