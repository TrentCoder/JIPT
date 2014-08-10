package com.jipt.gui;
/*
	CreateFilterFrame.java
    Trent Lucier

    This class is used when the user wishes to create a filter.
*/

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import com.jipt.JIPTAlert;
import com.jipt.filter.Filter;
import com.jipt.filter.FilterManager;

public class CreateFilterFrame extends JFrame
{
    MainFrame     main_frame     = null;
    FilterManager filter_manager = null;
    Filter        filter         = null;


    ApplyFilterFrame apply_frame  = null;
    boolean source_is_apply_frame = false;    // whether or not this frame was launched from the
                                              // Apply Custom Filter frame or not.
    boolean editing = false;
	JTextField textArray[][] = new JTextField[7][7];
	String     windowSizes[] = {"3x3", "5x5", "7x7"};
	int ROWS = 7;
	int COLS = 7;

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel nameLabel = new javax.swing.JLabel();
	javax.swing.JTextField nameTextField = new javax.swing.JTextField();
	javax.swing.JButton okButton = new javax.swing.JButton();
	javax.swing.JButton cancelButton = new javax.swing.JButton();
	javax.swing.JLabel divisionLabel = new javax.swing.JLabel();
	javax.swing.JTextField divisionTextField = new javax.swing.JTextField();
	javax.swing.JButton set1Button = new javax.swing.JButton();
	javax.swing.JButton set0Button = new javax.swing.JButton();
	javax.swing.JLabel windowLabel = new javax.swing.JLabel();
	javax.swing.JComboBox jComboBox1 = new javax.swing.JComboBox(windowSizes);
// END GENERATED CODE

    javax.swing.JButton sumMatrixButton = new javax.swing.JButton();

	public CreateFilterFrame(FilterManager fm, MainFrame mf)
	{
        main_frame = mf;
        filter_manager = fm;
	}

    public CreateFilterFrame(FilterManager fm, Filter f, ApplyFilterFrame aff)
    {
        filter_manager = fm;
        filter         = f;
        apply_frame    = aff;
        source_is_apply_frame = true;
    }

    public void setEditing(boolean b)
    {
        editing = b;
    }

	public void initComponents() throws Exception
	{
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		nameLabel.setSize(new java.awt.Dimension(80, 20));
		nameLabel.setVisible(true);
		nameLabel.setText("Filter Name");
		nameLabel.setLocation(new java.awt.Point(10, 290));

		nameTextField.setSize(new java.awt.Dimension(100, 20));
		nameTextField.setVisible(true);
		nameTextField.setLocation(new java.awt.Point(100, 290));

		okButton.setSize(new java.awt.Dimension(110, 30));
		okButton.setVisible(true);
		okButton.setText("OK");
		okButton.setLocation(new java.awt.Point(60, 370));

		cancelButton.setSize(new java.awt.Dimension(110, 30));
		cancelButton.setVisible(true);
		cancelButton.setText("Cancel");
		cancelButton.setLocation(new java.awt.Point(220, 370));

		divisionLabel.setSize(new java.awt.Dimension(83, 20));
		divisionLabel.setVisible(true);
		divisionLabel.setText("Division Factor");
		divisionLabel.setLocation(new java.awt.Point(10, 330));

		divisionTextField.setSize(new java.awt.Dimension(40, 20));
		divisionTextField.setVisible(true);
		divisionTextField.setText("1");
		divisionTextField.setLocation(new java.awt.Point(100, 330));

		set1Button.setSize(new java.awt.Dimension(130, 25));
		set1Button.setVisible(true);
		set1Button.setText("Set All to 1");
		set1Button.setLocation(new java.awt.Point(40, 10));

		set0Button.setSize(new java.awt.Dimension(135, 25));
		set0Button.setVisible(true);
		set0Button.setText("Set All to 0");
		set0Button.setLocation(new java.awt.Point(210, 10));

		windowLabel.setSize(new java.awt.Dimension(60, 23));
		windowLabel.setVisible(true);
		windowLabel.setText("Window");
		windowLabel.setLocation(new java.awt.Point(220, 290));

		jComboBox1.setSize(new java.awt.Dimension(65, 26));
		jComboBox1.setVisible(true);
		jComboBox1.setLocation(new java.awt.Point(290, 290));

        sumMatrixButton.setSize(new java.awt.Dimension(150, 25));
        sumMatrixButton.setVisible(true);
        sumMatrixButton.setText("Use Matrix Sum");
        sumMatrixButton.setLocation(new java.awt.Point(150, 330));

		setSize(new java.awt.Dimension(400, 450));
		getContentPane().setLayout(null);
		setTitle("Create Custom Filter");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(nameLabel);
		getContentPane().add(nameTextField);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);
		getContentPane().add(divisionLabel);
		getContentPane().add(divisionTextField);
		getContentPane().add(set1Button);
		getContentPane().add(set0Button);
		getContentPane().add(windowLabel);
		getContentPane().add(jComboBox1);
        getContentPane().add(sumMatrixButton);


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
		set1Button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				set1ButtonActionPerformed(e);
			}
		});
		set0Button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				set0ButtonActionPerformed(e);
			}
		});
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE

        sumMatrixButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				sumMatrix();
			}
		});

        ImageIcon ii = (ImageIcon) JIPTMenuBar.createCustomFiltersMenuItem.getIcon();
        this.setIconImage(ii.getImage());


		//////////////////////////////////
		/////// Window size //////////////
		//////////////////////////////////
		jComboBox1.addActionListener(
                new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        windowSizeListValueChanged(e);
                    }

			    });


		////////////////////////////////////
		////////// Matrix boxes ////////////
		///////////////////////////////////
		int spacing = 10;
		int startx = 60;
		int starty = 60;

		for(int r = 0; r < textArray.length; r++)
			for(int c = 0; c < textArray[0].length; c++)
			{
					JTextField new_field = new JTextField();
					new_field.setSize(new java.awt.Dimension(30, 20));
					new_field.setVisible(true);
					new_field.setText("0");
					new_field.setLocation(new java.awt.Point(startx + c*(spacing + new_field.getWidth()),
															 starty + r*(spacing + new_field.getHeight())));
					getContentPane().add(new_field);
					textArray[r][c] = new_field;
			}

        windowSizeListValueChanged(null);

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

    /////////////////////////////
    ///// Init matrix ///////////
    /////////////////////////////
    public void initMatrix()
    {
        set0ButtonActionPerformed(null);

        float m[][] = filter.getMatrix();
        int  size   = m[0].length;

        int start_position = 0;
        switch(size)
        {
            case 3 : start_position = 2; jComboBox1.setSelectedIndex(0); break;
            case 5 : start_position = 1; jComboBox1.setSelectedIndex(1); break;
            case 7 : start_position = 0; jComboBox1.setSelectedIndex(2); break;
        }
        windowSizeListValueChanged(null);


        for(int r = start_position; r < start_position + size; r++)
            for(int c = start_position; c < start_position + size; c++)
            {
                Float float_value = new Float(m[r - start_position][c - start_position]);

                String s = null;
                // Get rid of trailing zeroes
                if(float_value.floatValue() == float_value.intValue())
                    s = float_value.intValue() + "";
                else
                    s = float_value.floatValue() + "";



                textArray[r][c].setText(s);
            }
    }

    /////////////////////////////////
    ////// Init name ////////////////
    /////////////////////////////////
    public void initName()
    {
        nameTextField.setText(filter.getName());
    }

    //////////////////////////////////////
    /////////// Init division factor /////
    //////////////////////////////////////
    public void initDivision()
    {
        divisionTextField.setText(filter.getDivisionFactor() + "");
    }


	public void okButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        String filtername = nameTextField.getText();

        // Check for valid names (no incorrect characters)
        if(filtername.equalsIgnoreCase(""))
        {
            JIPTAlert.error(this, "Please enter a filter name.", "No Filter Name","OK");
            nameTextField.requestFocus();
            nameTextField.selectAll();
            return;
        }

        // Check for unique name CHANGE THIS SO THEY HAVE THE CHOICE TO OVERWRITE
        if(!editing)
        {
            if(!filter_manager.isUniqueName(filtername))
            {
                // Put up error message
                JIPTAlert.error(this, "The filter name '" + filtername + "' is already taken.", "Duplicate Filter Name","OK");
                nameTextField.requestFocus();
                nameTextField.selectAll();
                return;
            }
        }



        // Check for valid division factor (float value != 0)
        float division = 0f;
        try
        {
            division = Float.parseFloat(divisionTextField.getText());
        }
        catch(Exception e2)
        {
            divisionTextField.requestFocus();
            divisionTextField.selectAll();
            JIPTAlert.error(this, "Division factor must be any real number except 0", "Division Factor Error","OK");
            return;
        }

        if(division == 0f)
        {
            divisionTextField.requestFocus();
            divisionTextField.selectAll();
            JIPTAlert.error(this, "Division factor must be any real number except 0", "Division By Zero Error","OK");
            return;
        }

        // Check matrix for valid characters
        int size           = Integer.parseInt(windowSizes[jComboBox1.getSelectedIndex()].substring(0,1));
        float matrix[][]   = new float[size][size];
        int start_position = 0;
        switch(size)
        {
            case 3 : start_position = 2; break;
            case 5 : start_position = 1; break;
            case 7 : start_position = 0; break;
        }

        for(int r = start_position; r < start_position + size; r++)
            for(int c = start_position; c < start_position + size; c++)
            {
                try
                {

                    float value = Float.parseFloat(textArray[r][c].getText());
                    matrix[r - start_position][c - start_position] = value;
                }
                catch(Exception e3)
                {
                    textArray[r][c].requestFocus();
                    textArray[r][c].selectAll();
                    JIPTAlert.error(this, "Value must be a real number", "Invalid Matrix Value","OK");
                    return;
                }
            }

       if(source_is_apply_frame)
       {
        // Delete old filter.
        //filter.delete();

        // Save this filter.
         filter_manager.saveNewFilter(matrix, division, filtername);

        // Update ApplyfilterFrame's list
        apply_frame.loadFilters();

        // close this window
        thisWindowClosing(null);

        return;
       }
       filter_manager.saveNewFilter(matrix, division, filtername);
       thisWindowClosing(null);

	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

	public void set1ButtonActionPerformed(java.awt.event.ActionEvent e)
	{
		for(int r = 0; r < ROWS; r++)
			for(int c = 0; c < COLS; c++)
				textArray[r][c].setText("1");
	}

	public void set0ButtonActionPerformed(java.awt.event.ActionEvent e)
	{
		for(int r = 0; r < ROWS; r++)
			for(int c = 0; c < COLS; c++)
				textArray[r][c].setText("0");
	}

	///////////////////////////////////
	/////// Window size changed ///////
	///////////////////////////////////
	public void windowSizeListValueChanged(ActionEvent e)
	{
		int size = Integer.parseInt(windowSizes[jComboBox1.getSelectedIndex()].substring(0,1));
		for(int r = 0; r < ROWS; r++)
			for(int c = 0; c < COLS; c++)
			{
				if( (r >= ROWS/2 - size/2) && (r <= ROWS/2 + size/2) && (c >= COLS/2 - size/2) && (c <= COLS/2 + size/2))
					textArray[r][c].setVisible(true);
				else
					textArray[r][c].setVisible(false);

			}



	}

    ///////////////////////////
    //////// Sum Matrix ///////
    ///////////////////////////
    public void sumMatrix()
    {
        // Sums the matrix and places the result in the Division Factor text box
        float sum = 0;

        int size           = Integer.parseInt(windowSizes[jComboBox1.getSelectedIndex()].substring(0,1));
        int start_position = 0;
        switch(size)
        {
            case 3 : start_position = 2; break;
            case 5 : start_position = 1; break;
            case 7 : start_position = 0; break;
        }

        for(int r = start_position; r < start_position + size; r++)
            for(int c = start_position; c < start_position + size; c++)
            {
                try
                {

                    float value = Float.parseFloat(textArray[r][c].getText());
                    sum += value;
                }
                catch(Exception e3)
                {
                    textArray[r][c].requestFocus();
                    textArray[r][c].selectAll();
                    JIPTAlert.error(this, "Value must be a real number", "Invalid Matrix Value","OK");
                    return;
                }
            }

        String division_string = null;
        Float fl = new Float(sum);
        if(fl.intValue() == fl.floatValue())
            division_string = "" + fl.intValue();
        else
            division_string = "" + fl.floatValue();
        divisionTextField.setText(division_string);


    }



}
