package com.jipt.gui;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.jipt.JIPTAlert;

public class NewImageFrame extends JFrame
{
    MainFrame main_frame = null;

    JButton okButton = new JButton();
    JButton cancelButton = new JButton();
    JTextField pixelXSize = new JTextField();
    JLabel by = new JLabel();
    JTextField pixelYSize = new JTextField();
    JTextField inchXSize = new JTextField();
    JLabel jLabel3 = new JLabel();
    JTextField inchYSize = new JTextField();
    JTextField cmXSize = new JTextField();
    JLabel jLabel1 = new JLabel();
    JTextField cmYSize = new JTextField();
    ButtonGroup bg = new ButtonGroup();
    JRadioButton pixelRadioButton = new JRadioButton();
    JRadioButton inchRadioButton = new JRadioButton();
    JRadioButton cmRadioButton = new JRadioButton();

    public NewImageFrame(MainFrame mf)
    {
        main_frame = mf;
        try
        {
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        this.setVisible(true);
        this.setSize(300,225);

		addWindowListener(new java.awt.event.WindowAdapter()
        {
			public void windowClosing(java.awt.event.WindowEvent e)
            {
				thisWindowClosing(e);
			}
		});

    }

    private void jbInit() throws Exception
    {
        pixelRadioButton.setToolTipText("");
        pixelRadioButton.setSelected(true);
        pixelRadioButton.setText("pixels");
        pixelRadioButton.setBounds(new Rectangle(15, 20, 104, 25));
        pixelRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pixelRadioButton_actionPerformed(e);
            }
        });
        okButton.setActionCommand("okButton");
        okButton.setText("OK");
        okButton.setBounds(new Rectangle(101, 151, 81, 27));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        this.getContentPane().setLayout(null);
        cancelButton.setActionCommand("cancelButton");
        cancelButton.setText("Cancel");
        cancelButton.setBounds(new Rectangle(202, 150, 81, 27));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        pixelXSize.setBounds(new Rectangle(126, 22, 63, 21));
        by.setText("x");
        by.setBounds(new Rectangle(198, 24, 13, 17));
        pixelYSize.setBounds(new Rectangle(217, 21, 63, 21));
        inchXSize.setBounds(new Rectangle(127, 67, 63, 21));
        jLabel3.setText("x");
        jLabel3.setBounds(new Rectangle(199, 69, 15, 17));
        inchYSize.setBounds(new Rectangle(218, 67, 63, 21));
        cmXSize.setBounds(new Rectangle(128, 109, 63, 21));
        jLabel1.setText("x");
        jLabel1.setBounds(new Rectangle(198, 110, 16, 17));
        cmYSize.setBounds(new Rectangle(219, 108, 63, 21));
        this.getContentPane().setBackground(new Color(204, 204, 204));
        this.setResizable(false);
        this.setTitle("Create New Image...");
        inchRadioButton.setToolTipText("");
        inchRadioButton.setText("inches");
        inchRadioButton.setBounds(new Rectangle(15, 64, 104, 25));
        inchRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inchRadioButton_actionPerformed(e);
            }
        });
        cmRadioButton.setToolTipText("");
        cmRadioButton.setText("centimeters");
        cmRadioButton.setBounds(new Rectangle(16, 107, 104, 25));
        cmRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cmRadioButton_actionPerformed(e);
            }
        });
        this.getContentPane().add(cancelButton, null);
        this.getContentPane().add(cmRadioButton, null);
        this.getContentPane().add(inchRadioButton, null);
        this.getContentPane().add(pixelRadioButton, null);
        this.getContentPane().add(pixelXSize, null);
        this.getContentPane().add(inchXSize, null);
        this.getContentPane().add(cmXSize, null);
        this.getContentPane().add(cmYSize, null);
        this.getContentPane().add(pixelYSize, null);
        this.getContentPane().add(inchYSize, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(by, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(okButton, null);
        bg.add(pixelRadioButton);
        bg.add(inchRadioButton);
        bg.add(cmRadioButton);
    }

    void pixelRadioButton_actionPerformed(ActionEvent e)
    {
        this.pixelXSize.setEnabled(true);
        this.pixelYSize.setEnabled(true);
        this.inchXSize.setEnabled(false);
        this.inchYSize.setEnabled(false);
        this.cmXSize.setEnabled(false);
        this.cmYSize.setEnabled(false);
    }

    void inchRadioButton_actionPerformed(ActionEvent e)
    {
        this.pixelXSize.setEnabled(false);
        this.pixelYSize.setEnabled(false);
        this.inchXSize.setEnabled(true);
        this.inchYSize.setEnabled(true);
        this.cmXSize.setEnabled(false);
        this.cmYSize.setEnabled(false);
    }

    void cmRadioButton_actionPerformed(ActionEvent e)
    {
        this.pixelXSize.setEnabled(false);
        this.pixelYSize.setEnabled(false);
        this.inchXSize.setEnabled(false);
        this.inchYSize.setEnabled(false);
        this.cmXSize.setEnabled(true);
        this.cmYSize.setEnabled(true);
    }

    /////////////////////////
    /////// OK clicked //////
    /////////////////////////
    void okButton_actionPerformed(ActionEvent e)
    {
        double x;
        double y;

        if( this.cmRadioButton.isSelected() )
        {
            if( cmXSize.getText() != null)
                x = Double.parseDouble( cmXSize.getText() );
             else x = 0.0;
            if( !(x>0.0) )
                x=0.0;
            if( cmYSize.getText() != null)
                y = Double.parseDouble( cmYSize.getText() );
            else y = 0.0;
            if( !(y>0.0) )
                y=0.0;
            float p = (float)Toolkit.getDefaultToolkit().getScreenResolution();
            p /= 2.54;
            x *= p;
            y *= p;
        }
        else
        if( this.inchRadioButton.isSelected() )
        {
            if( inchXSize.getText() != null)
                x = Double.parseDouble( inchXSize.getText() );
            else x = 0.0;
            if( !(x>0.0) )
                x=0.0;
            else x = 0.0;
            if( inchYSize.getText() != null)
                y = Double.parseDouble( inchYSize.getText() );
            else y = 0.0;
            if( !(y>0.0) )
                y=0.0;
            int p = Toolkit.getDefaultToolkit().getScreenResolution();
            x *= p;
            y *= p;
        }
        else //pixels
        {
            if( pixelXSize.getText() != null )
                x = Double.parseDouble( pixelXSize.getText() );
            else x = 0.0;
            if( x <= 0 )
                x=0.0;
            if( pixelYSize.getText() != null )
                y = Double.parseDouble( pixelYSize.getText() );
            else y = 0.0;
            if( y <= 0 )
                y=0.0;
        }

        if( x > 0 && y > 0 )
        {
            main_frame.createNewImage((int)x, (int)y);
            setVisible(false);
        }
        else JIPTAlert.ok(main_frame, "Cannot enter 0 as a dimension", "ERROR", "OK", JOptionPane.ERROR_MESSAGE);
        thisWindowClosing(null);
    }

	// Close the window when the close box is clicked
	void thisWindowClosing(java.awt.event.WindowEvent e)
	{
		setVisible(false);
		dispose();
	}

    /////////////////////////////
    /////// Cancel clicked //////
    /////////////////////////////
    void cancelButton_actionPerformed(ActionEvent e)
    {
        thisWindowClosing(null);
    }

}