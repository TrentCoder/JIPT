package com.jipt.filter;
/*
    Trent Lucier

    This class deals with filter operations.

*/

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jipt.JIPTAlert;
import com.jipt.JIPTsettings;
import com.jipt.gui.ApplyFilterFrame;
import com.jipt.gui.CreateFilterFrame;
import com.jipt.gui.EdgeFilterFrame;
import com.jipt.gui.ImageFrame;
import com.jipt.gui.MainFrame;
import com.jipt.gui.MeanMedianFilterFrame;

public class FilterManager
{
    MainFrame main_frame = null;

    public FilterManager(MainFrame mf)
    {
        main_frame = mf;
    }

    ////////////////////////////////////////////////////////////////////////
    //////////////////////// Load Filter Frame /////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    public void loadFilter(ImageFrame image_frame)
    {
            try
            {
                ApplyFilterFrame aff = new ApplyFilterFrame(this, image_frame);
                aff.initComponents();
                aff.setVisible(true);
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
    }

    ////////////////////////////////////////////////////////////////////////
    //////////////////////// Create Filter Frame ///////////////////////////
    ////////////////////////////////////////////////////////////////////////
    public void createFilter()
    {
            try
            {
                CreateFilterFrame cff = new CreateFilterFrame(this, main_frame);
                cff.initComponents();
                cff.setVisible(true);
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
    }

    ///////////////////////////////////////////
    //////// Create a new filter file /////////
    ///////////////////////////////////////////
    //filter_manager.saveNewFilter(matrix, division, filtername);
    public void saveNewFilter(float matrix[][], float division, String filtername)
    {
        Filter new_filter = new Filter(matrix, division, filtername);
        new_filter.save();
    }

    /////////////////////////////////////////
    ///// Check for unique name /////////////
    /////////////////////////////////////////
    public boolean isUniqueName(String name)
    {
    // System.out.println("In isUniqueName");
        name += JIPTsettings.FILTERS_EXTENSION;
        ArrayList names = getFilterNames();

        for(int i = 0; i < names.size(); i++)
        {
            String n = (String) names.get(i);
            if(n.equalsIgnoreCase(name))
            {
                return false;
            }
        }
        return true;
    }

    //////////////////////////////////////
    //// Gets list of Filter objects /////
    //////////////////////////////////////
    public ArrayList getFilters()
    {
        ArrayList filters = new ArrayList();
        File dir = new File(JIPTsettings.FILTERS_PATH);

        // System.out.println(JIPTsettings.FILTERS_PATH);
        ///////////////////////////////////////////
        /// Make directory if it does not exist ///
        ///////////////////////////////////////////
        if(!dir.exists())
        {
            // System.out.println("Making dir");
            dir.mkdir();
            return filters;
        }

        //////////////////////////////////////////////////
        ////// Traverse directory and read in filters ////
        //////////////////////////////////////////////////
        String arr[] = dir.list();
		for(int i = 0; i < arr.length; i++)
		{
			String filename = arr[i];
			// System.out.println(filename);
            if(filename.substring(filename.length() - 4, filename.length()).compareTo(JIPTsettings.FILTERS_EXTENSION) == 0)
	        {
                // It is a filter.  Read it in.
                try
                {
                    ObjectInputStream ois = new ObjectInputStream(
                                                new FileInputStream(
                                                    new File(JIPTsettings.FILTERS_PATH + filename)));
                    //oos.writeObject(this);
                    Filter f = (Filter) ois.readObject();
                    filters.add(f);
                }
                catch(Exception e2)
                {
                    // System.out.println("Error in reading filter " + filename + "Exception: " + e2.toString());
                    JIPTAlert.ok(main_frame, "Error saving filter.", "Filter Save Error","OK", JOptionPane.ERROR_MESSAGE);
                    //return;
                }
            }
		}

        return filters;
    }

    //////////////////////////////////////
    /////// Get list of filter names /////
    //////////////////////////////////////
    public ArrayList getFilterNames()
    {

        ArrayList filter_names =  new ArrayList();

		File dir = new File(JIPTsettings.FILTERS_PATH);

        // System.out.println(JIPTsettings.FILTERS_PATH);
        ///////////////////////////////////////////
        /// Make directory if it does not exist ///
        ///////////////////////////////////////////
        if(!dir.exists())
        {
            // System.out.println("Making dir");
            dir.mkdir();
            return filter_names;
        }

		String arr[] = dir.list();
		for(int i = 0; i < arr.length; i++)
		{
			String filename = arr[i];
			// System.out.println(filename);
            if(filename.substring(filename.length() - 4, filename.length()).compareTo(JIPTsettings.FILTERS_EXTENSION) == 0)
	            filter_names.add(filename);
		}

        return filter_names;
    }

    ////////////////////////////////////////////////////////////////////////
    ///////////////////////////// Edge Detection ///////////////////////////
    ////////////////////////////////////////////////////////////////////////
    public void edgeDetection(ImageFrame image_frame)
    {
            try
            {
                //image_frame.addToUndo(this);    // CHANGE THIS
                EdgeFilterFrame edge_frame = new EdgeFilterFrame(this, image_frame);
                edge_frame.initComponents();
                edge_frame.setVisible(true);
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
    }

    ////////////////////
    //// Laplace ///////
    ////////////////////
    public Image laplace(Image im, int width, int height)
    {
        LaplaceFilter laplaceFilter = new LaplaceFilter();
        return laplaceFilter.laplace(im, width, height);
    }

    /////////////////////
    /////// Sobel ///////
    /////////////////////
    public Image sobel(Image im, int width, int height)
    {
        SobelFilter sobelFilter = new SobelFilter();
        return sobelFilter.sobel(im, width, height);
    }

    /////////////////////
    ////// Roberts //////
    /////////////////////
    public Image roberts(Image im, int width, int height)
    {
        RobertsFilter robertsFilter = new RobertsFilter();
        return robertsFilter.roberts(im, width, height);
    }


    //////////////////////////////
    //// Gaussian (prefilter) ////
    //////////////////////////////
    public Image gaussian(Image im, int width, int height)
    {
        GaussianFilter gaussianFilter = new GaussianFilter();
        return gaussianFilter.gaussian(im, width, height);
    }




    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// Mean/Median //////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void meanMedian(ImageFrame image_frame)
    {
            try
            {
                //image_frame.addToUndo(this);    // CHANGE THIS
                MeanMedianFilterFrame mean_median_frame = new MeanMedianFilterFrame(this, image_frame);
                mean_median_frame.initComponents();
                mean_median_frame.setVisible(true);
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
    }

    // Median
    public Image median(Image im, int im_width, int im_height, boolean filter_r, boolean filter_g, boolean filter_b,
                                int window_size, int iterations)
    {
        MedianFilter median_filter = new MedianFilter();

        median_filter.setWindowSize(window_size);
        //System.out.println("Window size = " + window_size);

        // Add loop for iterations
        Image new_image = median_filter.filterImage(im, im_width, im_height, filter_r, filter_g, filter_b);;
        for(int i = 1; i < iterations; i++)
            new_image = median_filter.filterImage(new_image, im_width, im_height, filter_r, filter_g, filter_b);

        return new_image;
    }

    // Mean
    public Image mean(Image im, int im_width, int im_height, boolean filter_r, boolean filter_g, boolean filter_b,
                                int window_size, int iterations)
    {
        MeanFilter mean_filter = new MeanFilter();

        mean_filter.setWindowSize(window_size);
        // System.out.println("Window size = " + window_size);

        // Add loop for iterations
        Image new_image = mean_filter.filterImage(im, im_width, im_height, filter_r, filter_g, filter_b);;
        for(int i = 1; i < iterations; i++)
            new_image = mean_filter.filterImage(new_image, im_width, im_height, filter_r, filter_g, filter_b);

        return new_image;
    }
}