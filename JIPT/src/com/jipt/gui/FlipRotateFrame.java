package com.jipt.gui;


import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.jipt.JIPTAlert;
import com.jipt.ToolManager;

public class FlipRotateFrame extends JFrame
{

      ToolManager tool_manager = null;
      ImageFrame  image_frame  = null;

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JRadioButton rotateRadio = new javax.swing.JRadioButton();
	javax.swing.JTextField rotateTextField = new javax.swing.JTextField();
	javax.swing.JRadioButton degreeRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton radianRadio = new javax.swing.JRadioButton();
	javax.swing.JRadioButton flipRadio = new javax.swing.JRadioButton();
	javax.swing.JCheckBox flipHorizontalCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox flipVerticalCheckbox = new javax.swing.JCheckBox();
	javax.swing.JButton okButton = new javax.swing.JButton();
	javax.swing.JButton cancelButton = new javax.swing.JButton();
// END GENERATED CODE

	public FlipRotateFrame()
	{
	}

 	public FlipRotateFrame(ToolManager tm, ImageFrame i)
	{
            image_frame = i;
            tool_manager = tm;
	}

	public void initComponents() throws Exception
	{
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		rotateRadio.setSize(new java.awt.Dimension(60, 21));
		rotateRadio.setVisible(true);
		rotateRadio.setText("Rotate");
		rotateRadio.setLocation(new java.awt.Point(20, 90));

		rotateTextField.setSize(new java.awt.Dimension(60, 20));
		rotateTextField.setVisible(true);
		rotateTextField.setLocation(new java.awt.Point(90, 90));

		degreeRadio.setSize(new java.awt.Dimension(77, 21));
		degreeRadio.setVisible(true);
		degreeRadio.setText("Degrees");
		degreeRadio.setLocation(new java.awt.Point(40, 120));

		radianRadio.setSize(new java.awt.Dimension(80, 21));
		radianRadio.setVisible(true);
		radianRadio.setText("Radians");
		radianRadio.setLocation(new java.awt.Point(150, 120));

		flipRadio.setSize(new java.awt.Dimension(44, 21));
		flipRadio.setVisible(true);
		flipRadio.setText("Flip");
		flipRadio.setLocation(new java.awt.Point(20, 10));

		flipHorizontalCheckbox.setSize(new java.awt.Dimension(87, 21));
		flipHorizontalCheckbox.setVisible(true);
		flipHorizontalCheckbox.setText("Horizontal");
		flipHorizontalCheckbox.setLocation(new java.awt.Point(40, 40));

		flipVerticalCheckbox.setSize(new java.awt.Dimension(77, 21));
		flipVerticalCheckbox.setVisible(true);
		flipVerticalCheckbox.setText("Vertical");
		flipVerticalCheckbox.setLocation(new java.awt.Point(150, 40));

		okButton.setSize(new java.awt.Dimension(80, 40));
		okButton.setVisible(true);
		okButton.setText("OK");
		okButton.setLocation(new java.awt.Point(50, 160));

		cancelButton.setSize(new java.awt.Dimension(80, 40));
		cancelButton.setVisible(true);
		cancelButton.setText("Cancel");
		cancelButton.setLocation(new java.awt.Point(150, 160));

		setSize(new java.awt.Dimension(252, 251));
		getContentPane().setLayout(null);
		setTitle("Flip/Rotate");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(rotateRadio);
		getContentPane().add(rotateTextField);
		getContentPane().add(degreeRadio);
		getContentPane().add(radianRadio);
		getContentPane().add(flipRadio);
		getContentPane().add(flipHorizontalCheckbox);
		getContentPane().add(flipVerticalCheckbox);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.flipRotateToolsMenuItem.getIcon();
        this.setIconImage(ii.getImage());

		rotateRadio.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				rotateRadioStateChanged(e);
			}
		});
		flipRadio.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				flipRadioStateChanged(e);
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
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE

		ButtonGroup bg = new ButtonGroup();
		bg.add(radianRadio);
		bg.add(degreeRadio);
		degreeRadio.setSelected(true);

		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(flipRadio);
		bg2.add(rotateRadio);

		flipRadio.setSelected(true);
		flipRadio.requestFocus();

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
	}

	public void okButtonActionPerformed(java.awt.event.ActionEvent e)
	{
             try
             {
                if(rotateRadio.isSelected())
                {
                  float degrees = Float.parseFloat(rotateTextField.getText());
                  if(radianRadio.isSelected())
                  {
                    degrees = (float) (degrees*180/(Math.PI));
                  }

                  tool_manager.rotateImage(image_frame, degrees);
                }
                else
                {
                  // Flip
                  if(flipHorizontalCheckbox.isSelected())
                  {
                    tool_manager.flipHorizontal(image_frame, flipVerticalCheckbox.isSelected() );
                  }

                  if(flipVerticalCheckbox.isSelected())
                  {
                    tool_manager.flipVertical(image_frame);
                  }
                }
             }
             catch(Exception e1)
             {
                ///////////////////////////////////
                //// User entered invalid input ///
                ///////////////////////////////////
                rotateTextField.requestFocus();
                rotateTextField.selectAll();
                JIPTAlert.error(this, "Value must be a real number", "Invalid Angle Value","OK");


                return;
             }
             thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
		thisWindowClosing(null);
	}

	public void flipRadioStateChanged(javax.swing.event.ChangeEvent e)
	{
		if(flipRadio.isSelected())
		{
			rotateRadio.setSelected(false);
			rotateTextField.setEnabled(false);
			degreeRadio.setEnabled(false);
			radianRadio.setEnabled(false);

			flipHorizontalCheckbox.setEnabled(true);
			flipVerticalCheckbox.setEnabled(true);
		}
	}

	public void rotateRadioStateChanged(javax.swing.event.ChangeEvent e)
	{
		if(rotateRadio.isSelected())
		{
			flipRadio.setSelected(false);
			flipHorizontalCheckbox.setEnabled(false);
			flipVerticalCheckbox.setEnabled(false);

			rotateTextField.setEnabled(true);
			degreeRadio.setEnabled(true);
			radianRadio.setEnabled(true);
		}

	}





}
