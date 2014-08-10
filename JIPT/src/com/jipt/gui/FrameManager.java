package com.jipt.gui;
/*
	Trent Lucier
	10/14/2001
	FrameManager.java

	This class manages the image frames.
*/
import java.util.ArrayList;

import javax.swing.JInternalFrame;

import com.jipt.FileManager;

public class FrameManager
{
    private static MainFrame main_frame = null;
	private static ArrayList frame_list;       //holds ImageFrame for graphic
    private static ArrayList histFrame_list;

    private static JIPTInfoFrame info_frame = null;

    private static int nextID = 0;

	// Constructor
	public FrameManager( MainFrame mf )
	{
        main_frame = mf;

		frame_list = new ArrayList();
        histFrame_list = new ArrayList();

//        info_frame = new JIPTInfoFrame(this);
//        info_frame.addInternalFrameListener(main_frame);
        info_frame = new JIPTInfoFrame(this);
//        main_frame.desktop_pane.add( info_frame );
	}

	// Add a frame
	public void addFrame(ImageFrame j)
	{
        int size = this.frame_list.size();
        j.setID( this.getNextID() );
        String title = null;
        if( size < 10 )
            title = ("0" + (size+1) + " " + j.getFilename() );
        else title = ( (size+1) + " " + j.getFilename() );
        j.setDocTitle( title );
        frame_list.add(j);
        HistogramFrame hf = new HistogramFrame( this, j );
        if( j.getFilename() != null )
            hf.setTitle( FileManager.parseFileName( j.getFilename() ) + " histogram" );
        else hf.setTitle( "untitled histogram" );
        histFrame_list.add( hf );
        main_frame.desktop_pane.add( hf );
        if( j.getShowHistogram() )
            hf.setVisible( true );
        else hf.setVisible( false );

        //enable AtLeastOneFrameOpen actions
        main_frame.menu_bar.enableAtLeastOneFrameOpenActions();
	}

	// Remove a frame is the JInternalFrame's title is "KILL THIS FRAME" set in MainFrame
	public void removeFrame( JInternalFrame jif  )
	{
       // if( jif.getClass().toString().equalsIgnoreCase( "class ImageFrame" )       //remove ImageFrame
       //     || jif.getClass().toString().equalsIgnoreCase( "class HistogramFrame" ) )  //remove histogramFrame
		
		
		if( jif instanceof ImageFrame    
		 || jif instanceof HistogramFrame )  
		{
            for(int loop=0; loop < frame_list.size(); loop++)
            {
                ImageFrame j = (ImageFrame) frame_list.get(loop);

                if(jif.getTitle().equalsIgnoreCase( j.getTitle() ) )
                {
                    //get matching histogramFrame
                    HistogramFrame hf = (HistogramFrame) histFrame_list.get(loop);
                    showInfoFrame( false );       //close info frame for this window (if open)
                    histFrame_list.remove( loop ); //remove HistogramFrame from arrayList
                    hf.dispose();
                    frame_list.remove( loop );  //remove ImageFrame from arrayList
                }
            }
            updateDocTitles();
        }
        else // JIPTInfoFrame
            info_frame.setEnabled( false ); //just hide Info Frame

        //disable AtLeastOneFrameOpen actions
        if( this.frame_list.isEmpty() )
            main_frame.menu_bar.disableAtLeastOneFrameOpenActions();
	}

    //Remove ALL frames
    public void removeAllFrames()
    {
        this.histFrame_list.clear();
        this.frame_list.clear();
        showInfoFrame( false );

        //disable AtLeastOneFrameOpen actions
        main_frame.menu_bar.disableAtLeastOneFrameOpenActions();
    }

	// Get the selected frame
	public ImageFrame getSelectedFrame()
	{
		int size = frame_list.size();
		for(int i = 0; i < size; i++)
		{
			ImageFrame j = (ImageFrame) frame_list.get(i);
            String s = j.getDocTitle();
			if(j.isSelected())
				return j;
		}

        //If an image frame is not currently activated, return the last activated image frame
		return getFrame( info_frame.getFilename() );
	}

    public ImageFrame getFrame(String frameName)
    {
        int size = frame_list.size();
        for( int loop=0; loop < size; loop++)
        {
            ImageFrame j = (ImageFrame) frame_list.get(loop);
            if( j.getFilename().equalsIgnoreCase( frameName ) );
            return j;
        }
        return null;
    }

    public ImageFrame getFrame(int index)
    {
        return (ImageFrame)this.frame_list.get( index );
    }

    public int getNumFrames()
    {
        return this.frame_list.size();
    }

    private int getNextID()
    {
        return this.nextID++;
    }

    public void updateDocTitles()
    {
        for( int loop=0; loop < this.frame_list.size(); loop++ )
        {
            ImageFrame f = (ImageFrame) frame_list.get( loop );
            if( loop < 10)
                f.setDocTitle( "0" + (loop+1) + " " + f.getFilename() );
            else f.setDocTitle( (loop+1) + " " + f.getFilename() );
        }
    }

    public int getFrameIndex( ImageFrame img_frame )
	{
		int size = frame_list.size();
		for(int i = 0; i < size; i++)
		{
			ImageFrame j = (ImageFrame) frame_list.get(i);
            if( img_frame.getID() == j.getID() )
//			if( j.isSelected() )
				return i;
		}
		return -1;
	}

    public void setInfoFrameFileName(String f )
    {
        info_frame.setFileName( f );
    }

    public String getInfoFrameFilename()
    {
        return info_frame.getFilename();
    }

    public void setInfoFrameFilePath( String f )
    {
        info_frame.setFilePath( f );
    }

    public void setInfoFrameColor( int bpp )
    {
        info_frame.setColor( bpp );
    }

    public void setInfoFrameSize( int x, int y)
    {
        info_frame.setImageSize(x,y);
    }

    public void setInfoFrameBpp( int bpp )
    {
        info_frame.setColor( bpp );
    }

    public void showInfoFrame(boolean b)
    {
        info_frame.setVisible( b );
    }

    public void updateInfoFrame( ImageFrame im_frame )
    {
        setInfoFrameFileName( FileManager.parseFileName(im_frame.getFilename() ) );
        setInfoFrameFilePath( FileManager.parsePath(im_frame.getFilename() ) );
        setInfoFrameColor( im_frame.getJIPTImage().getBpp() );
        setInfoFrameSize( im_frame.getImageWidth(), im_frame.getImageHeight() );
        showInfoFrame( im_frame.getShowFileInfo() );
    }

    public void updateHistogramFrame( ImageFrame im_frame )
    {
        int index = this.getFrameIndex( im_frame );
        boolean show = im_frame.getShowHistogram();
       HistogramFrame hist_frame = (HistogramFrame)this.histFrame_list.get( index );

        hist_frame.updateGraph();

        showHistogramFrame( show );
    }

    public HistogramFrame getHistogramFrame( int index )
    {
        return (HistogramFrame)this.histFrame_list.get( index );
    }

    public void showHistogramFrame( boolean b )
    {
        HistogramFrame hf = (HistogramFrame)this.histFrame_list.get( getFrameIndex( getSelectedFrame() ));
        if( hf != null )
            hf.setVisible( b );
    }

    public String getInfoFrameTitle()
    {
        return info_frame.getTitle();
    }

    public String getHistogramFrameTitle()
    {
        HistogramFrame hf = (HistogramFrame)histFrame_list.get( getFrameIndex( getSelectedFrame() ));
        return hf.getTitle();
    }

    public ArrayList getFrameList()
    {
        return frame_list;
    }

    public boolean isUniqueName(String name)
    {
        // Returns FALSE if a frame already has this name
        // TRUE if no frame has this title
        for(int i = 0; i < frame_list.size(); i++)
        {
            ImageFrame i_frame = (ImageFrame) frame_list.get(i);
            if(i_frame.getTitle().equalsIgnoreCase(name))
                return false;
        }

        return true;


    }

}