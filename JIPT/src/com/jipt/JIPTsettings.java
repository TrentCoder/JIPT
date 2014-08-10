package com.jipt;
/*
	James LaTour
	10/16/2001
	JIPTsettings.java

	This class manages the settings under the Menu Bar, Options, and also screen size and position.
    If these settings change from the default settings, these settings are stored in a file jipt.ini

*/


/****************** JIPT.INI DEFINITION ****************************
 *
 *  The jipt.ini file can contain any of the following in any order
 *   where   # : x < MAX_NUM_RECENT_FILES
 *
 *  SPLASHSCREEN= 1 or 0
 *  AUTOHISTOGRAM= 1 or 0
 *  AUTOINFO= 1 or 0
 *  SAVEWINSETTINGS= 1 or 0
 *  WINSIZEX= <X window size>
 *  WINSIZEY= <Y window size>
 *  WINPOSX= <X window screen position>
 *  WINPOSY= <Y window screen position>
 *  CURRENTPATH= <current file load path>
 *  CLEARPIXEL= <int>
 *  NUMUNDO= <#>
 *  MAXRECENT= 0 .. 9
 *  RECENT <path and filename>
 *  BGCOLOR= <Color or CustomColor name>
 *  CUSTOMCOLOR= <red> <green> <blue> <color name>
 *
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class JIPTsettings
{
    private boolean saveWindowSettings = false;
    private boolean autoHistogram = false;
    private boolean autoFileInfo = false;
    private boolean showSplashScreen = true;

    public static final int CLEAR_PIXEL_0 = Color.black.getRGB();
    public static final int CLEAR_PIXEL_1 = Color.white.getRGB();
    public static final int CLEAR_PIXEL_2 = Color.lightGray.getRGB();
    public static final int CLEAR_PIXEL_3 = Color.TRANSLUCENT;
    public static final int UNLIMITED_UNDO = 10;
    public static final int MAX_NUM_RECENT_FILES = 9;

    private int DEFAULT_PARENT_WIDTH  = 640;    // Default width of parent window
    private int DEFAULT_PARENT_HEIGHT = 480;    // Default height of parent window
//    private int max_num_recent_files = 5;       // Number of recent files to save
    private int max_undo_size = 5;
    private int savedParentWidth = 640;
    private int savedParentHeight = 480;
    private int windowPositionX = 0;
    private int windowPositionY = 0;
    private String bg_color = "Gray (Light)";
    private int numRecentFiles = 0;
    private String[] recentFile;
    private int currentRecentIndex = 0; //temp (only for reading from ini)
    private int clearPixelColor = 0;
    private static int untitledFileNumber = 1;

    public static final String FILTERS_EXTENSION = ".fil";
    public static final String FILTERS_PATH = "./filters/";
    public static final String GRAPHICS_PATH = "./graphics/";
    public static final String HELP_PATH = "./help/";
    public static final String DEFAULT_PATH = ".."+File.separator;
    private final String JIPT_INI_FILENAME = "jipt.ini";
    public static final String JIPT_SPLASH_SCREEN_PATH = "./graphics/jipt_splash.gif"; // Trent, 12/7
    private final String JIPT_INI_PATH = "";
    private String current_path = "..";

    //Color variables
	private int numColors = 0;

	private JIPTColor color[] = null;

    private ArrayList customColor = new ArrayList();


    public JIPTsettings()
    {   //reads the values of jipt.ini
        int booleanValue = -1;
        String tokenValue = null;

        String line = null;
        StringTokenizer st = null;

        BufferedReader ini_file;

        //create recentFile array to store filenames
        this.recentFile = new String[ MAX_NUM_RECENT_FILES ];

		//initialize colors
		initColors();

		//read INI file
        try
        {
            //open file
            ini_file = new BufferedReader( new FileReader( JIPT_INI_PATH + JIPT_INI_FILENAME ) );

            line = ini_file.readLine();
            while( line != null )
            {
                this.parseSettings( line );
                line = ini_file.readLine();
            }

            //close file
            ini_file.close();

        }
        catch( FileNotFoundException e )
        {
//            e.printStackTrace();
        }
        catch( IOException e )
        {
//            e.printStackTrace();
        }

    }//end JIPTsettings constructor

    ///////////////////////
    /// PARSE SETTINGS ////
    ///////////////////////
    //
    //  The following procedure reads the JIPT_INI file and
    //   initializes JIPT's settings
    //
    /////////////////////////////////////////
    public boolean parseSettings(String line)
    {
        StringTokenizer st = new StringTokenizer( line );
        String command = st.nextToken();
        int commandLen = command.length();
        line = line.substring( commandLen + 1 );

        if( command.equalsIgnoreCase("SPLASHSCREEN=") )
        {
            //read autoHistogram data
            int value = Integer.parseInt( line );
            this.showSplashScreen= intToBoolean( value );
            return true;
        }

        if( command.equalsIgnoreCase("AUTOHISTOGRAM=") )
        {
            //read autoHistogram data
            int value = Integer.parseInt( line );
            this.autoHistogram = intToBoolean( value );
            return true;
        }

        if( command.equalsIgnoreCase("AUTOINFO=") )
        {
            //read autoFileInfo data
            int value = Integer.parseInt( line );
            this.autoFileInfo = intToBoolean( value );
            return true;
        }

        if( command.equalsIgnoreCase("SAVEWINSETTINGS=") )
        {
            //read saveWindowSettings data
            StringTokenizer st2 = new StringTokenizer( line );
            String token = st2.nextToken().toString();
            int value = Integer.parseInt( token );
            this.saveWindowSettings = intToBoolean( value );
			return true;
		}

        if( command.equalsIgnoreCase("WINSIZEX=") )
        {
            //get Saved Window Settings
	        StringTokenizer st2 = new StringTokenizer( line );
        	String token = st2.nextToken().toString();
            this.savedParentWidth = Integer.parseInt( token );
			return true;
		}

        if( command.equalsIgnoreCase("WINSIZEY=") )
        {
			StringTokenizer st2 = new StringTokenizer( line );
			String token = st2.nextToken().toString();
	        this.savedParentHeight = Integer.parseInt( token );
			return true;
		}

        if( command.equalsIgnoreCase("WINPOSX=") )
        {
            //get Saved Window Settings
	        StringTokenizer st2 = new StringTokenizer( line );
        	String token = st2.nextToken().toString();
            this.windowPositionX = Integer.parseInt( token );
			return true;
		}

        if( command.equalsIgnoreCase("WINPOSY=") )
        {
			StringTokenizer st2 = new StringTokenizer( line );
			String token = st2.nextToken().toString();
	        this.windowPositionY = Integer.parseInt( token );
			return true;
		}

        if( command.equalsIgnoreCase("NUMUNDO=") )
        {
			StringTokenizer st2 = new StringTokenizer( line );
			String token = st2.nextToken().toString();
	        this.max_undo_size = Integer.parseInt( token );
			return true;
		}

        if( command.equalsIgnoreCase("CLEARPIXEL=") )
        {
			StringTokenizer st2 = new StringTokenizer( line );
			String token = st2.nextToken().toString();
	        this.clearPixelColor = Integer.parseInt( token );
			return true;
		}

        if( command.equalsIgnoreCase("CURRENTPATH=") )
        {
            //read current path data
            this.current_path = line;
            return true;
        }

        if( command.equalsIgnoreCase("MAXRECENT=") )
        {
            //read save recent data
            int value = Integer.parseInt( line );
            this.numRecentFiles = value;
            return true;
        }

        if( command.equalsIgnoreCase("RECENTFILE=") )
        {
            //read recent file
            StringTokenizer st2 = new StringTokenizer( line );

            if( currentRecentIndex < this.MAX_NUM_RECENT_FILES )
            {
                this.recentFile[ currentRecentIndex ] = line;
                currentRecentIndex++;
                return true;
            }

            return false;
        }

        if( command.equalsIgnoreCase("BGCOLOR=") )
        {
            //read background color data
            this.bg_color = line;
            return true;
        }

        if( command.equalsIgnoreCase("CUSTOMCOLOR=") )
        {
            int len = 0;
            int red = 0;
            int green = 0;
            int blue = 0;

            //read Custom color
            StringTokenizer st2 = new StringTokenizer( line );

            //read RED
			if( st.hasMoreTokens() )
			{
				String token = st2.nextToken().toString();
				len += token.length();
				red = Integer.parseInt( token );
			}
			else return false;

            //read GREEN
			if( st.hasMoreTokens() )
			{
				String token = st2.nextToken().toString();
				len += token.length();
				green = Integer.parseInt( token );
			}
			else return false;

            //read BLUE
			if( st.hasMoreElements() )
			{
				String token = st2.nextToken().toString();
				len += token.length() + 3; // + 3 for spaces
				blue = Integer.parseInt( token );
			}
			else return false;

            line = line.substring( len );
            String name = line;
			if( name == null )
				name = "Unnamed color (" + red + ", " + green + ", " + blue + ")";

            if( (red!=0) && (green!=0) && (blue!=0) )
            addCustomColor( name, red, green, blue );
            return true;
        }

        return false;   //line not understood
    }

    /////////////////////////
    ///// Save Settings /////
    /////////////////////////
    /*  This procedure writes the current settings to the ini file
    /   Should be called just before System.exit(0) from MainFrame
	/	Returns false if error saving file, otherwise true
    */
    public boolean saveSettings()
    {
        String line = null;

        BufferedWriter ini_file;
        try
        {
            //open file
            ini_file = new BufferedWriter( new FileWriter( JIPT_INI_PATH + JIPT_INI_FILENAME ) );

            //write show Splash screen info
            ini_file.write( "SPLASHSCREEN= " + booleanToInt( this.showSplashScreen ) );
            ini_file.newLine();

            //write autoHistogram data
            ini_file.write( "AUTOHISTOGRAM= " + booleanToInt( this.autoHistogram ) );
            ini_file.newLine();

            //write autoFileInfo data
            ini_file.write( "AUTOINFO= " + booleanToInt( this.autoFileInfo ) );
            ini_file.newLine();

            //write saveWindowSettings data
            ini_file.write( "SAVEWINSETTINGS= " + booleanToInt( this.saveWindowSettings ) );
			ini_file.newLine();

            if( this.saveWindowSettings )
            {
                //write Window Settings Sizes
                ini_file.write( "WINSIZEX= " + this.savedParentWidth );
                ini_file.newLine();

                ini_file.write( "WINSIZEY= " + this.savedParentHeight );
                ini_file.newLine();

                //write Window Settings Location
                ini_file.write( "WINPOSX= " + this.windowPositionX );
                ini_file.newLine();

                ini_file.write( "WINPOSY= " + this.windowPositionY );
                ini_file.newLine();
            }

            //write Image Load Path
            ini_file.write( "CURRENTPATH= " + this.current_path );
            ini_file.newLine();

            //write clear pixel color
            ini_file.write( "CLEARPIXEL= " + this.clearPixelColor );
            ini_file.newLine();

            //write max_undo_size
            ini_file.write( "NUMUNDO= " + this.max_undo_size );
            ini_file.newLine();

            //write number of recent files to save
            ini_file.write( "MAXRECENT= " + this.numRecentFiles );
            ini_file.newLine();

            //write recentFile array to store filenames
            for( int loop=0; loop < this.numRecentFiles; loop++ )
            {
                String rf = this.recentFile[loop];
                if( (rf != null) && (!rf.equalsIgnoreCase("null")) )
                {
                    ini_file.write( "RECENTFILE= " + rf );
                    ini_file.newLine();
                }
            }

            //store background color
            ini_file.write( "BGCOLOR= " + this.bg_color );
            ini_file.newLine();

            //store custom colors
            for( int loop=0; loop < this.customColor.size(); loop++ )
            {
                JIPTColor c = (JIPTColor)this.customColor.get( loop );
                Color color = c.getColor();
                ini_file.write( "CUSTOMCOLOR= " + color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " " + c.getName() );
                ini_file.newLine();
            }

            //close file
            ini_file.close();

        }
        catch( FileNotFoundException e )
        {
            e.printStackTrace();
            return false;
        }
        catch( IOException e )
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }//end saveSettings

    public Dimension getParentWindowSize()
    {
        //returns the size of the stored window settings (from last exit of jipt)
        Dimension size = new Dimension();

        if( this.saveWindowSettings )
            size.setSize( this.savedParentWidth, this.savedParentHeight );
        else
            size.setSize( this.DEFAULT_PARENT_WIDTH, this.DEFAULT_PARENT_HEIGHT );

        return size;
    }//end getParentWindowSize()

    public Point getWindowPosition()
    {
        //returns the size of the stored window settings (from last exit of jipt)
        Point pos = new Point( this.windowPositionX, this.windowPositionY );
        return pos;
    }

    public String getINIFilename()
    {
        return this.JIPT_INI_PATH + this.JIPT_INI_FILENAME;
    }

    //Converts an int value to boolean
    // 1 = true
    // 0 = false
    public boolean intToBoolean( int i )
    {
        if( i == 0 )
            return false;
        else
            return true;
    }

    //Converts an boolean to int value
    // true = 1
    // false = 0
    public int booleanToInt( boolean b )
    {
        if( b )
            return 1;   //1 = true
        else
            return 0;   //0 = false
    }

    public boolean getAutoHistogram()
    {
        return this.autoHistogram;
    }

    public boolean getAutoFileInfo()
    {
        return this.autoFileInfo;
    }

    public boolean getSaveWindowSettings()
    {
        return this.saveWindowSettings;
    }

    public void setAutoHistogram( boolean b )
    {
        this.autoHistogram = b;
    }

    public void setAutoFileInfo( boolean b )
    {
        this.autoFileInfo = b;
    }

    public void setSaveWindowSettings( boolean b )
    {
        this.saveWindowSettings = b;
    }

    public boolean getShowSplashScreen()
    {
        return this.showSplashScreen;
    }

    public void setShowSplashScreen( boolean b )
    {
        this.showSplashScreen = b;
    }

    //returns the number of Recent Files
    public int getNumRecentFiles()
    {
        return this.numRecentFiles;
    }

    //returns the entire array of Recent Files
    public String[] getRecentFiles()
    {
        return this.recentFile;
    }

    //returns a single Recent File
    public String getRecentFile( int i )
    {
        return this.recentFile[ i ];
    }

    //adds a recent file to the list of recent files
    public void addRecentFile( String filename )
    {
        boolean notListed = true; //true if file is not on list, false if on list
        int index = 0;

        //determine if filename exists in list
        for(int i=0; i<numRecentFiles; i++)
        {
            String rf = getRecentFile( i );
            if( rf!=null && !rf.equalsIgnoreCase("null") )
                if( rf.equalsIgnoreCase( filename ) )
                {
                    notListed = false;
                    index = i;
                }
        }

        //increment #files for this file if not already on list and menu not full
        if( notListed && (this.numRecentFiles < this.MAX_NUM_RECENT_FILES) )
            numRecentFiles++;

        if( notListed )
        {
            //remove oldest file from list (push all files down the list)
            for( int i=numRecentFiles-1; i>0 ; i--)
                    recentFile[ i ] = getRecentFile( i-1 );
        }
        else //shuffle file order
        {
            String temp = recentFile[ index ];
            for( int i=index; i>0; i-- )
            {
                recentFile[ i ] = recentFile[ i-1 ];
            }
            recentFile[0] = temp;
        }

        //put newest at top
        recentFile[ 0 ] = filename;
    }

    //Parent window resized, update savedParentWidth/Height
    public void setSavedWindowSize(int x, int y, int w, int h)
    {
		if( this.saveWindowSettings )	//only update values if true
		{
            this.windowPositionX = x;
            this.windowPositionY = y;
			this.savedParentWidth = w;
			this.savedParentHeight = h;
		}
    }

    public void setCurrentPath(String s)
    {
        this.current_path = s;
    }

    public String getCurrentPath()
    {
        return this.current_path;
    }

    public void setBGColor( String c )
    {
        this.bg_color = c;
    }

    public String getBGColor()
    {
        return this.bg_color;
    }

    public void initColors()
    {
		this.numColors = 16;
        color = new JIPTColor[ numColors ];
        color[0] = new JIPTColor("Black", Color.black );
        color[1] = new JIPTColor("Blue", Color.blue );
        color[2] = new JIPTColor("Cornflower Blue", new Color(100, 149, 235) );
        color[3] = new JIPTColor("Cyan", Color.cyan );
        color[4] = new JIPTColor("Gray", Color.gray );
        color[5] = new JIPTColor("Gray (Dark)", Color.darkGray );
        color[6] = new JIPTColor("Gray (Light)", Color.lightGray );
        color[7] = new JIPTColor("Green", Color.green );
        color[8] = new JIPTColor("Magenta", Color.magenta );
        color[9] = new JIPTColor("Tool Tip Yellow", new Color(255, 255, 204) );
        color[10] = new JIPTColor("Orange", Color.orange );
        color[11] = new JIPTColor("Pink", Color.pink );
        color[12] = new JIPTColor("Purple", new Color(153, 153, 204) ); //128,0,128
        color[13] = new JIPTColor("Red", Color.red );
        color[14] = new JIPTColor("White", Color.white );
        color[15] = new JIPTColor("Yellow", Color.yellow );
    }

    public void addCustomColor( String n, Color c )
    {
        JIPTColor custom = new JIPTColor( n, c );
        this.customColor.add( custom );
    }

    public void addCustomColor( JIPTColor jc )
    {
        this.customColor.add( jc );
    }

    public JIPTColor getCustomColor(int i)
    {
        return (JIPTColor)this.customColor.get( i );
    }

    public void addCustomColor( String n, int red, int green, int blue )
    {
        Color c = new Color( red, green, blue );
        JIPTColor custom = new JIPTColor( n, c );
        customColor.add( custom );
    }

    public void removeCustomColor( String n, Color c )
    {
        for(int loop=0; loop < this.customColor.size(); loop++)
        {
            JIPTColor color = (JIPTColor)this.customColor.get( loop );
            if( color.getName().equalsIgnoreCase(n) || (color.getColor() == c) )
                customColor.remove( loop );
        }
    }

    public void removeCustomColor( Color c )
    {
        for(int loop=0; loop < this.customColor.size(); loop++)
        {
            JIPTColor color = (JIPTColor)this.customColor.get( loop );
            if( color.getColor() == c )
                customColor.remove( loop );
        }
    }

    public void removeCustomColor( String n  )
    {
        for(int loop=0; loop < this.customColor.size(); loop++)
        {
            JIPTColor color = (JIPTColor)this.customColor.get( loop );
            if( color.getName().equalsIgnoreCase(n) )
                customColor.remove( loop );
        }
    }

    public Color getColorByName( String name )
    {
        for(int loop=0; loop<numColors; loop++)
        {
            if( color[loop].getName().equalsIgnoreCase( name ) )
                return color[loop].getColor();
        }

        for(int loop=0; loop<this.customColor.size(); loop++)
        {
            JIPTColor c = (JIPTColor)this.customColor.get( loop );
            if( c.getName().equalsIgnoreCase( name ) )
                return c.getColor();
        }

        return null;
    }

	public int getNumColors()
	{
		return this.numColors;
	}

    ///////////////////////////////////////////
    ////// Returns the array of JIPT colors ///
    ///////////////////////////////////////////
    public JIPTColor[] getJIPTColors()
    {
        return color;
    }

    public String getColorName(Color c)
    {
        for(int loop=0; loop<numColors; loop++)
        {
            if( color[loop].getColor() == c )
                return color[loop].getName();
        }

        for(int loop=0; loop<this.customColor.size(); loop++)
        {
            JIPTColor jc = (JIPTColor)this.customColor.get( loop );
            if( jc.getColor() == c )
                return jc.getName();
        }

        return null;
    }

	public int getNumCustomColors()
	{
		return this.customColor.size();
	}

	public JIPTColor getColor( int i )
	{
		return this.color[i];
	}

    public int getMaxUndoSize()
    {
        return this.max_undo_size;
    }

    public void setMaxUndoSize( int i )
    {
        this.max_undo_size = i;
    }

    public int getClearPixelColor()
    {
        return this.clearPixelColor;
    }

    public void setClearPixelColor(int c)
    {
        switch( c )
        {
            case 0 : this.clearPixelColor = this.CLEAR_PIXEL_0;     break;
            case 1 : this.clearPixelColor = this.CLEAR_PIXEL_1;     break;
            case 2 : this.clearPixelColor = this.CLEAR_PIXEL_2;     break;
            case 3 :
            default: this.clearPixelColor = this.CLEAR_PIXEL_3;     break;
        }
    }

    public String getUntitledFileNumber()
    {
        String nextNum = null;
        if( untitledFileNumber < 10 )
            nextNum =  "00" + untitledFileNumber;
        else if( untitledFileNumber > 100 )
                nextNum =  ""+untitledFileNumber;
            else nextNum = "0" + untitledFileNumber;

        untitledFileNumber++;

        return nextNum;
    }

    public void setMaxNumRecentFiles(int num)
    {
        this.numRecentFiles = num;
    }

    public int getMaxNumRecentFiles()
    {
        return numRecentFiles;
    }

}//end class JIPTsettings






