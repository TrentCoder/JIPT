package com.jipt.gui;
/*
    ApplyFilterFrame
    Trent Lucier

    This class is used for slecting a filter to apply to an image.
    It provides the user with a preview of the filtered image.
*/

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import com.jipt.JIPTAlert;
import com.jipt.JIPTimage;
import com.jipt.ToolManager;
import com.jipt.filter.Filter;
import com.jipt.filter.FilterManager;

public class ApplyFilterFrame extends ImagePreviewFrame
{
    ArrayList filters = null;

    FilterManager filter_manager = null;
    ToolManager   tool_manager   = null;
    ImageFrame    image_frame    = null;

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
	javax.swing.JComboBox filterComboBox = new javax.swing.JComboBox();
	javax.swing.JLabel filterLabel = new javax.swing.JLabel();
	javax.swing.JLabel channelLabel = new javax.swing.JLabel();
	javax.swing.JCheckBox redCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox greenCheckbox = new javax.swing.JCheckBox();
	javax.swing.JCheckBox blueCheckbox = new javax.swing.JCheckBox();
	javax.swing.JLabel manageLabel = new javax.swing.JLabel();
	javax.swing.JButton newFilterButton = new javax.swing.JButton();
	javax.swing.JButton editFilterButton = new javax.swing.JButton();
	javax.swing.JButton deleteFilterButton = new javax.swing.JButton();
// END GENERATED CODE

	public ApplyFilterFrame(FilterManager fm, ImageFrame i_frame)
	{
        image_frame = i_frame;
        filter_manager = fm;
        tool_manager = new ToolManager();
	}

	public void initComponents() throws Exception
	{
        super.initComponents();
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		filterComboBox.setSize(new java.awt.Dimension(130, 25));
		filterComboBox.setVisible(true);
		filterComboBox.setLocation(new java.awt.Point(200, 270));

		filterLabel.setSize(new java.awt.Dimension(60, 20));
		filterLabel.setVisible(true);
		filterLabel.setText("Filter");
		filterLabel.setLocation(new java.awt.Point(200, 240));

		channelLabel.setSize(new java.awt.Dimension(91, 20));
		channelLabel.setVisible(true);
		channelLabel.setText("Channels");
		channelLabel.setLocation(new java.awt.Point(370, 240));

		redCheckbox.setSize(new java.awt.Dimension(84, 20));
		redCheckbox.setVisible(true);
		redCheckbox.setText("Red");
		redCheckbox.setLocation(new java.awt.Point(370, 270));

		greenCheckbox.setSize(new java.awt.Dimension(85, 20));
		greenCheckbox.setVisible(true);
		greenCheckbox.setText("Green");
		greenCheckbox.setLocation(new java.awt.Point(370, 300));

		blueCheckbox.setSize(new java.awt.Dimension(75, 20));
		blueCheckbox.setVisible(true);
		blueCheckbox.setText("Blue");
		blueCheckbox.setLocation(new java.awt.Point(370, 330));

		manageLabel.setSize(new java.awt.Dimension(60, 20));
		manageLabel.setVisible(true);
		manageLabel.setText("Manage");
		manageLabel.setLocation(new java.awt.Point(20, 240));

		newFilterButton.setSize(new java.awt.Dimension(125, 30));
		newFilterButton.setVisible(true);
		newFilterButton.setText("New Filter");
		newFilterButton.setLocation(new java.awt.Point(20, 270));

		editFilterButton.setSize(new java.awt.Dimension(125, 30));
		editFilterButton.setVisible(true);
		editFilterButton.setText("Edit Filter");
		editFilterButton.setLocation(new java.awt.Point(20, 310));

		deleteFilterButton.setSize(new java.awt.Dimension(125, 30));
		deleteFilterButton.setVisible(true);
		deleteFilterButton.setText("Delete Filter");
		deleteFilterButton.setLocation(new java.awt.Point(20, 350));

		setSize(new java.awt.Dimension(500, 488));
		getContentPane().setLayout(null);
		setTitle("Apply Custom Filter");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(filterComboBox);
		getContentPane().add(filterLabel);
		getContentPane().add(channelLabel);
		getContentPane().add(redCheckbox);
		getContentPane().add(greenCheckbox);
		getContentPane().add(blueCheckbox);
		getContentPane().add(manageLabel);
		getContentPane().add(newFilterButton);
		getContentPane().add(editFilterButton);
		getContentPane().add(deleteFilterButton);


		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE
        redCheckbox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                updateAlteredImage();
            }
        });

        greenCheckbox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                updateAlteredImage();
            }
        });

        blueCheckbox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                updateAlteredImage();
            }
        });

        deleteFilterButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteFilter();
            }
        });

        editFilterButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                editFilter();
            }
        });

        newFilterButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                newFilter();
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

        filterComboBox.addActionListener(
        new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //windowSizeListValueChanged(e);
                updateAlteredImage();
            }

			    });

        /////////////////////
        /// Window icon /////
        /////////////////////
        ImageIcon ii = (ImageIcon) JIPTMenuBar.manageCustomFiltersMenuItem.getIcon();
        this.setIconImage(ii.getImage());

        //////////////////////
        /// Set the images ///
        //////////////////////
        Image i = image_frame.getJIPTImage().getImage();
        original_image = new JIPTimage(i);

        if( original_image.getImage().getWidth(this) > original_image.getImage().getHeight(this))
                original_image.setImage( original_image.getImage().getScaledInstance( originalImageLabel.getWidth(), -1, Image.SCALE_FAST ));
        else original_image.setImage( original_image.getImage().getScaledInstance( -1, originalImageLabel.getHeight(), Image.SCALE_FAST ));

        setOriginalImage(original_image.getImage());
        p_width  = original_image.getImage().getWidth(originalLabel);
        p_height = original_image.getImage().getHeight(originalLabel);


        original_height = image_frame.getJIPTImage().getImage().getHeight(this); // height of full size image
        original_width  = image_frame.getJIPTImage().getImage().getWidth(this);  // width  of full size image

        //////////////////////////////
        //// Load the filters      ///
        //////////////////////////////
        loadFilters();

        ///////////////////////
        //// Set the states ///
        ///////////////////////
        redCheckbox.setSelected(true);
        greenCheckbox.setSelected(true);
        blueCheckbox.setSelected(true);

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
        int index = filterComboBox.getSelectedIndex();
        if(index < 0)
            return;
        Filter filter = (Filter) filters.get(index);

        boolean r = redCheckbox.isSelected();
        boolean g = greenCheckbox.isSelected();
        boolean b = blueCheckbox.isSelected();

        Image new_image = image_frame.getJIPTImage().getImage();
        new_image = filter.filterImage(new_image, original_width, original_height, r, g, b);
        image_frame.setFrameImage(new_image, true);
        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

    //////////////////////////////////////////////
    ///// Put the filter names in the list ///////
    //////////////////////////////////////////////
    public void loadFilters()
    {
        // Empty combo box
        filterComboBox.removeAllItems();

        // Read in filters
        filters = filter_manager.getFilters();

        // Put filter names in list
        for(int i = 0; i < filters.size(); i++)
        {
            String fname = ((Filter) filters.get(i)).getName();
            filterComboBox.addItem(fname);
        }

        if(filters.size() == 0)
        {
            // disable edit & delete buttons
            this.editFilterButton.setEnabled(false);
            this.deleteFilterButton.setEnabled(false);
            okButton.setEnabled(false);
            this.filterComboBox.setEnabled(false);
        }
        else
        {
            // Enable edit and delete buttons
            this.editFilterButton.setEnabled(true);
            this.deleteFilterButton.setEnabled(true);
            okButton.setEnabled(true);
            this.filterComboBox.setEnabled(true);
        }
    }

    ////////////////////////////////
    /// Update the altered image ///
    ////////////////////////////////
    public void updateAlteredImage()
    {
        int index = filterComboBox.getSelectedIndex();
        if(index < 0)
        {
            this.setAlteredImage(original_image.getImage());
            return;
        }
        Filter filter = (Filter) filters.get(index);

        boolean r = redCheckbox.isSelected();
        boolean g = greenCheckbox.isSelected();
        boolean b = blueCheckbox.isSelected();

        Image new_image = filter.filterImage(original_image.getImage(), p_width, p_height, r, g, b);
        this.setAlteredImage(new_image);
    }

    ////////////////////////////////////
    /////////// Delete Filter //////////
    ////////////////////////////////////
    public void deleteFilter()
    {
        int index = filterComboBox.getSelectedIndex();
        if(index < 0)
            return;
        Filter filter = (Filter) filters.get(index);
        String name = filter.getName();
        boolean confirm = JIPTAlert.yesNo(this, "Delete filter '" + name + "'?", "Delete " + name,
                                "Yes", "No", JOptionPane.WARNING_MESSAGE);
        if(confirm)
        {
            filter.delete();
            filterComboBox.removeAllItems();
            loadFilters();
            updateAlteredImage();
        }

        return;


    }

    ////////////////////////////////////
    /////////// Edit Filter ////////////
    ////////////////////////////////////
    public void editFilter()
    {
        int index = filterComboBox.getSelectedIndex();

        if(index < 0)
            return;
        Filter filter = (Filter) filters.get(index);


        try
        {
            CreateFilterFrame cff = new CreateFilterFrame(filter_manager, filter, this);
            cff.setEditing(true);
            cff.initComponents();
            cff.initMatrix();
            cff.initDivision();
            cff.initName();
            cff.setVisible(true);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }

        return;
    }

    ////////////////////////////////////
    /////////// New Filter /////////////
    ////////////////////////////////////
    public void newFilter()
    {

        try
        {
            CreateFilterFrame cff = new CreateFilterFrame(filter_manager, new Filter(), this);
            cff.initComponents();
           // cff.initMatrix();
           // cff.initDivision();
           // cff.initName();
            cff.setVisible(true);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }

    }




}
