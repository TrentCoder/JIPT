package com.jipt.gui;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.jipt.JIPTColor;
import com.jipt.JIPTsettings;

public class JIPTCustomColorDialog extends JFrame
{
	private static JIPTColor color = null;
    private static JIPTOptionFrame option_frame = null;
    MainFrame main_frame = null;
    boolean removeColor = false;    //signals if remove color from list during edit (as to not duplicate)

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
	javax.swing.JLabel colorLabel = new javax.swing.JLabel();
	javax.swing.JSlider redSlider = new javax.swing.JSlider();
	javax.swing.JLabel redLabel = new javax.swing.JLabel();
	javax.swing.JSlider greenSlider = new javax.swing.JSlider();
	javax.swing.JSlider blueSlider = new javax.swing.JSlider();
	javax.swing.JLabel greenLabel = new javax.swing.JLabel();
	javax.swing.JLabel blueLabel = new javax.swing.JLabel();
	javax.swing.JLabel redValueLabel = new javax.swing.JLabel();
	javax.swing.JLabel blueValueLabel = new javax.swing.JLabel();
	javax.swing.JLabel greenValueLabel = new javax.swing.JLabel();
	javax.swing.JButton okButton = new javax.swing.JButton();
	javax.swing.JButton cancelButton = new javax.swing.JButton();
	javax.swing.JTextField name = new javax.swing.JTextField();
	javax.swing.JLabel nameLabel = new javax.swing.JLabel();
// END GENERATED CODE

	public JIPTCustomColorDialog(MainFrame mf)
	{
        main_frame = mf;
	}

	public JIPTCustomColorDialog(JIPTOptionFrame jof, MainFrame mf)
	{
        option_frame = jof;
        main_frame = mf;
	}

	public void initComponents() throws Exception
	{
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		jLabel1.setSize(new java.awt.Dimension(44, 20));
		jLabel1.setVisible(true);
		jLabel1.setText("Color");
		jLabel1.setLocation(new java.awt.Point(150, 20));

		colorLabel.setSize(new java.awt.Dimension(150, 150));
		colorLabel.setVisible(true);
		colorLabel.setOpaque(true);
		colorLabel.setLocation(new java.awt.Point(90, 60));

		redSlider.setSize(new java.awt.Dimension(200, 16));
		redSlider.setVisible(true);
		redSlider.setValue(255);
		redSlider.setMaximum(255);
		redSlider.setMajorTickSpacing(10);
		redSlider.setLocation(new java.awt.Point(60, 230));

		redLabel.setSize(new java.awt.Dimension(40, 30));
		redLabel.setVisible(true);
		redLabel.setText("Red");
		redLabel.setLocation(new java.awt.Point(270, 220));

		greenSlider.setSize(new java.awt.Dimension(200, 20));
		greenSlider.setVisible(true);
		greenSlider.setValue(255);
		greenSlider.setMaximum(255);
		greenSlider.setMajorTickSpacing(10);
		greenSlider.setLocation(new java.awt.Point(60, 270));

		blueSlider.setSize(new java.awt.Dimension(200, 20));
		blueSlider.setVisible(true);
		blueSlider.setValue(255);
		blueSlider.setMaximum(255);
		blueSlider.setMajorTickSpacing(10);
		blueSlider.setLocation(new java.awt.Point(60, 310));

		greenLabel.setSize(new java.awt.Dimension(40, 30));
		greenLabel.setVisible(true);
		greenLabel.setText("Green");
		greenLabel.setLocation(new java.awt.Point(270, 260));

		blueLabel.setSize(new java.awt.Dimension(40, 30));
		blueLabel.setVisible(true);
		blueLabel.setText("Blue");
		blueLabel.setLocation(new java.awt.Point(270, 300));

		redValueLabel.setSize(new java.awt.Dimension(32, 20));
		redValueLabel.setVisible(true);
		redValueLabel.setLocation(new java.awt.Point(20, 220));

		blueValueLabel.setSize(new java.awt.Dimension(30, 20));
		blueValueLabel.setVisible(true);
		blueValueLabel.setLocation(new java.awt.Point(20, 300));

		greenValueLabel.setSize(new java.awt.Dimension(30, 20));
		greenValueLabel.setVisible(true);
		greenValueLabel.setLocation(new java.awt.Point(20, 260));

		okButton.setSize(new java.awt.Dimension(90, 40));
		okButton.setVisible(true);
		okButton.setText("OK");
		okButton.setLocation(new java.awt.Point(70, 390));

		cancelButton.setSize(new java.awt.Dimension(90, 40));
		cancelButton.setVisible(true);
		cancelButton.setText("Cancel");
		cancelButton.setLocation(new java.awt.Point(190, 390));

		name.setSize(new java.awt.Dimension(110, 20));
		name.setVisible(true);
		name.setLocation(new java.awt.Point(120, 350));

		nameLabel.setSize(new java.awt.Dimension(52, 20));
		nameLabel.setVisible(true);
		nameLabel.setText("Name");
		nameLabel.setLocation(new java.awt.Point(60, 350));

		setSize(new java.awt.Dimension(350, 474));
		getContentPane().setLayout(null);
		setTitle("Create Custom Color");
        this.setIconImage( new ImageIcon(JIPTsettings.GRAPHICS_PATH+"color.gif").getImage() );
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(jLabel1);
		getContentPane().add(colorLabel);
		getContentPane().add(redSlider);
		getContentPane().add(redLabel);
		getContentPane().add(greenSlider);
		getContentPane().add(blueSlider);
		getContentPane().add(greenLabel);
		getContentPane().add(blueLabel);
		getContentPane().add(redValueLabel);
		getContentPane().add(blueValueLabel);
		getContentPane().add(greenValueLabel);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);
		getContentPane().add(name);
		getContentPane().add(nameLabel);


		redSlider.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				redSliderStateChanged(e);
			}
		});
		greenSlider.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				greenSliderStateChanged(e);
			}
		});
		blueSlider.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent e) {
				blueSliderStateChanged(e);
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

		redSlider.setValue(255);
		greenSlider.setValue(255);
		blueSlider.setValue(255);
	}

    public void setValues(JIPTOptionFrame jof, JIPTColor jc)
    {
        removeColor = true; //remove old color and replace it with altered color
        option_frame = jof; //frame to control
        //set current color values and title
        this.redSlider.setValue( jc.getColor().getRed() );
        this.blueSlider.setValue( jc.getColor().getBlue() );
        this.greenSlider.setValue( jc.getColor().getGreen() );
        this.name.setText( jc.getName() );
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

	public void redSliderStateChanged(javax.swing.event.ChangeEvent e)
	{
		redValueLabel.setText(redSlider.getValue() + "");
		updateColor();
	}

	public void greenSliderStateChanged(javax.swing.event.ChangeEvent e)
	{
		greenValueLabel.setText(greenSlider.getValue() + "");
		updateColor();
	}

	public void blueSliderStateChanged(javax.swing.event.ChangeEvent e)
	{
		blueValueLabel.setText(blueSlider.getValue() + "");
		updateColor();
	}

	public void updateColor()
	{
		Color c = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), 255);
		colorLabel.setBackground(c);
	}

	public void okButtonActionPerformed(java.awt.event.ActionEvent e)
	{
		int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        this.color = new JIPTColor(name.getText(), new Color(red, green, blue));
        main_frame.getJIPTsettings().addCustomColor( color );
        main_frame.recreateMenuBar();
        if( option_frame != null )
            option_frame.updateColorList(removeColor);
        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
	    thisWindowClosing(null);
    }
}
