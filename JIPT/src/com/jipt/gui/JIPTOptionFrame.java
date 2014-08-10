package com.jipt.gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jipt.JIPTAlert;
import com.jipt.JIPTColor;
import com.jipt.JIPTsettings;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class JIPTOptionFrame extends JFrame implements ListSelectionListener
{
    MainFrame main_frame = null;
    JIPTsettings js = null;

    private Color BGcolor = null;

    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel GeneralSettingsPanel = new JPanel();
    JPanel UndoPanel = new JPanel();
    JPanel ClearPixelPanel = new JPanel();
    JPanel BackgroundColorPanel = new JPanel();
    JScrollPane listScrollPane = new JScrollPane();
    JCheckBox AutoHistogramCheckBox = new JCheckBox();
    JCheckBox AutoFileInfoCheckBox = new JCheckBox();
    JCheckBox saveWindowSettingsCheckBox = new JCheckBox();
    JCheckBox saveRecent = new JCheckBox();
    JSlider numUndoSlider = new JSlider();
    JLabel numUndoLabel = new JLabel();
    //Create the label tables
    Hashtable undoLabelTable = new Hashtable();
    Hashtable clearPixelLabelTable = new Hashtable();
    JSlider clearPixelSlider = new JSlider();
    JPanel sampleBGColorPanel = new JPanel();
    JLabel sampleLabel = new JLabel();
    JList colorList = new JList();
    JLabel customColorLabel = new JLabel();
    JSlider numRecentSlider = new JSlider();
    JLabel numRecentLabel = new JLabel();
    JPanel jPanel2 = new JPanel();
    JButton OkButton = new JButton();
    JButton cancelButton = new JButton();
    JLabel sampleLabel1 = new JLabel();
    JPanel sampleClearPixelColorPanel = new JPanel();
    JButton newCustomColorButton = new JButton();
    JButton editCustomColorButton = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();
    JList customColorList = new JList();
    JLabel colorLabel = new JLabel();
    JButton deleteCustomColorButton = new JButton();
    JCheckBox splashScreenCheckBox = new JCheckBox();

    public JIPTOptionFrame(MainFrame mf)
    {
        main_frame = mf;
        js = mf.getJIPTsettings();

        //create undo slider labels
        undoLabelTable = new Hashtable();
        undoLabelTable.put( new Integer( 0 ), new JLabel("Disabled") );
        undoLabelTable.put( new Integer( 2 ), new JLabel("2") );
        undoLabelTable.put( new Integer( 4 ), new JLabel("4") );
        undoLabelTable.put( new Integer( 6 ), new JLabel("6") );
        undoLabelTable.put( new Integer( 8 ), new JLabel("8") );
        undoLabelTable.put( new Integer( 10 ), new JLabel("Unlimited") );

        //create clear pixel slider labels
        clearPixelLabelTable = new Hashtable();
        clearPixelLabelTable.put( new Integer( 0 ), new JLabel("Black") );
        clearPixelLabelTable.put( new Integer( 1 ), new JLabel("White") );
        clearPixelLabelTable.put( new Integer( 2 ), new JLabel("Match Background") );
        clearPixelLabelTable.put( new Integer( 3 ), new JLabel("Transparent") );

        try
        {
            addColors(true);
            jbInit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //get current background color
        BGcolor = js.getColorByName( js.getBGColor() );

        jTabbedPane1.addTab("General", new ImageIcon(JIPTsettings.GRAPHICS_PATH+"settings.gif"), GeneralSettingsPanel);
        jTabbedPane1.addTab("Undo", new ImageIcon(JIPTsettings.GRAPHICS_PATH+"undo.gif"), UndoPanel);
        jTabbedPane1.addTab("Clear Pixel", ClearPixelPanel);
        jTabbedPane1.addTab("Background Color", new ImageIcon(JIPTsettings.GRAPHICS_PATH+"color.gif"), BackgroundColorPanel);

        numRecentSlider.setSnapToTicks(true);
        numUndoSlider.setSnapToTicks(true);
        clearPixelSlider.setSnapToTicks(true);
        numUndoSlider.setPaintLabels(true);
        clearPixelSlider.setPaintLabels(true);

        this.setVisible(true);
        this.setEnabled(true);
        this.setSize(400,340);
        this.setIconImage( new ImageIcon(JIPTsettings.GRAPHICS_PATH+"settings.gif").getImage() );

        setInitialValues();
    }

    private void jbInit() throws Exception
    {
        jTabbedPane1.setDoubleBuffered(true);
        jTabbedPane1.setMaximumSize(new Dimension(400, 300));
        jTabbedPane1.setMinimumSize(new Dimension(400, 300));
        jTabbedPane1.setPreferredSize(new Dimension(400, 300));
        GeneralSettingsPanel.setLayout(null);
        UndoPanel.setLayout(null);
        ClearPixelPanel.setLayout(null);
        BackgroundColorPanel.setLayout(null);
        AutoHistogramCheckBox.setText("Automatically display Histogram when image opens");
        AutoHistogramCheckBox.setBounds(new Rectangle(11, 21, 319, 25));
        AutoFileInfoCheckBox.setText("Automatically display File Information when image opens");
        AutoFileInfoCheckBox.setBounds(new Rectangle(11, 46, 348, 27));
        saveWindowSettingsCheckBox.setText("Save window settings on exit");
        saveWindowSettingsCheckBox.setBounds(new Rectangle(11, 69, 352, 30));
        saveRecent.setText("Save list of recent images");
        saveRecent.setBounds(new Rectangle(11, 122, 351, 28));
        saveRecent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveRecent_actionPerformed(e);
            }
        });
        numUndoSlider.setLabelTable(undoLabelTable);
        numUndoSlider.setMajorTickSpacing(1);
        numUndoSlider.setMaximum(10);
        numUndoSlider.setDoubleBuffered(true);
        numUndoSlider.setActionMap(null);
        numUndoSlider.setPaintTicks(true);
        numUndoSlider.setBounds(new Rectangle(23, 84, 326, 77));
        numUndoSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                numUndoSlider_stateChanged(e);
            }
        });
        numUndoLabel.setText("Number of Undo actions");
        numUndoLabel.setBounds(new Rectangle(47, 29, 146, 36));
        clearPixelSlider.setOrientation(JSlider.VERTICAL);
        clearPixelSlider.setInverted(true);
        clearPixelSlider.setLabelTable(clearPixelLabelTable);
        clearPixelSlider.setPaintTicks(true);
        clearPixelSlider.setMajorTickSpacing(1);
        clearPixelSlider.setMaximum(3);
        clearPixelSlider.setBounds(new Rectangle(30, 25, 194, 162));
        clearPixelSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                clearPixelSlider_stateChanged(e);
            }
        });
        sampleBGColorPanel.setBorder(BorderFactory.createEtchedBorder());
        sampleBGColorPanel.setMinimumSize(new Dimension(32, 32));
        sampleBGColorPanel.setPreferredSize(new Dimension(32, 32));
        sampleBGColorPanel.setBounds(new Rectangle(261, 54, 57, 57));
        sampleLabel.setAlignmentX((float) 0.5);
        sampleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sampleLabel.setText("Sample");
        sampleLabel.setBounds(new Rectangle(262, 27, 57, 22));
        this.setResizable(false);
        this.setTitle("JIPT Settings");
        listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        listScrollPane.setBounds(new Rectangle(15, 27, 219, 115));
        customColorLabel.setText("Custom Colors");
        customColorLabel.setBounds(new Rectangle(76, 143, 87, 24));
        numRecentSlider.setMajorTickSpacing(1);
        numRecentSlider.setMaximum(9);
        numRecentSlider.setMinimum(1);
        numRecentSlider.setPaintLabels(true);
        numRecentSlider.setPaintTicks(true);
        numRecentSlider.setDoubleBuffered(true);
        numRecentSlider.setBounds(new Rectangle(32, 188, 322, 65));
        numRecentSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                numRecentSlider_stateChanged(e);
            }
        });
        numRecentLabel.setText("Number of recent images to store:");
        numRecentLabel.setBounds(new Rectangle(95, 158, 198, 26));
        OkButton.setAlignmentX((float) 1.0);
        OkButton.setText("Ok");
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OkButton_actionPerformed(e);
            }
        });
        cancelButton.setAlignmentX((float) 1.0);
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton_actionPerformed(e);
            }
        });
        jPanel2.setAlignmentX((float) 1.0);
        jPanel2.setBorder(BorderFactory.createEmptyBorder());
        jPanel2.setMaximumSize(new Dimension(400, 40));
        jPanel2.setMinimumSize(new Dimension(400, 40));
        jPanel2.setPreferredSize(new Dimension(400, 40));
        sampleLabel1.setBounds(new Rectangle(262, 27, 57, 22));
        sampleLabel1.setText("Sample");
        sampleLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        sampleLabel1.setAlignmentX((float) 0.5);
        sampleClearPixelColorPanel.setBounds(new Rectangle(261, 54, 57, 57));
        sampleClearPixelColorPanel.setPreferredSize(new Dimension(32, 32));
        sampleClearPixelColorPanel.setMinimumSize(new Dimension(32, 32));
        sampleClearPixelColorPanel.setBorder(BorderFactory.createEtchedBorder());
        colorList.setBorder(BorderFactory.createLoweredBevelBorder());
        colorList.setDoubleBuffered(true);
        colorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        colorList.addListSelectionListener(this);
        newCustomColorButton.setMargin(new Insets(2, 7, 2, 7));
        newCustomColorButton.setText("New Color...");
        newCustomColorButton.setBounds(new Rectangle(260, 146, 88, 27));
        newCustomColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newCustomColorButton_actionPerformed(e);
            }
        });
        editCustomColorButton.setBounds(new Rectangle(260, 179, 88, 27));
        editCustomColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editCustomColorButton_actionPerformed(e);
            }
        });
        editCustomColorButton.setText("Edit Color...");
        editCustomColorButton.setMargin(new Insets(2, 7, 2, 7));
        jScrollPane1.setBounds(new Rectangle(13, 166, 221, 68));
        colorLabel.setBounds(new Rectangle(80, 3, 87, 24));
        colorLabel.setText("Basic colors");
        deleteCustomColorButton.setMargin(new Insets(2, 1, 2, 1));
        deleteCustomColorButton.setText("Delete Color...");
        deleteCustomColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteCustomColorButton_actionPerformed(e);
            }
        });
        deleteCustomColorButton.setBounds(new Rectangle(260, 212, 88, 27));
        BackgroundColorPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                BackgroundColorPanel_componentShown(e);
            }
        });
        ClearPixelPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                ClearPixelPanel_componentShown(e);
            }
        });
        splashScreenCheckBox.setText("Display splash screen on startup");
        splashScreenCheckBox.setBounds(new Rectangle(11, 97, 345, 26));
        GeneralSettingsPanel.add(AutoHistogramCheckBox, null);
        GeneralSettingsPanel.add(saveRecent, null);
        GeneralSettingsPanel.add(numRecentSlider, null);
        GeneralSettingsPanel.add(numRecentLabel, null);
        GeneralSettingsPanel.add(AutoFileInfoCheckBox, null);
        GeneralSettingsPanel.add(saveWindowSettingsCheckBox, null);
        GeneralSettingsPanel.add(splashScreenCheckBox, null);
        UndoPanel.add(numUndoSlider, null);
        UndoPanel.add(numUndoLabel, null);
//        jTabbedPane1.add(GeneralSettingsPanel,  "General");
        ClearPixelPanel.add(sampleLabel1, null);
        ClearPixelPanel.add(sampleClearPixelColorPanel, null);
        ClearPixelPanel.add(clearPixelSlider, null);
//        jTabbedPane1.add(UndoPanel, "Undo");
//        jTabbedPane1.add(ClearPixelPanel, "Clear Pixel");
//        jTabbedPane1.add(BackgroundColorPanel,   "Background Color");
        BackgroundColorPanel.add(listScrollPane, null);
        listScrollPane.getViewport().add(colorList, null);
        BackgroundColorPanel.add(sampleLabel, null);
        BackgroundColorPanel.add(sampleBGColorPanel, null);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(OkButton, null);
        jPanel2.add(cancelButton, null);
        this.getContentPane().add(jTabbedPane1, BorderLayout.NORTH);
        BackgroundColorPanel.add(jScrollPane1, null);
        BackgroundColorPanel.add(deleteCustomColorButton, null);
        BackgroundColorPanel.add(newCustomColorButton, null);
        BackgroundColorPanel.add(editCustomColorButton, null);
        BackgroundColorPanel.add(customColorLabel, null);
        BackgroundColorPanel.add(colorLabel, null);
        jScrollPane1.getViewport().add(customColorList, null);
    }

    private void setInitialValues()
    {
        //temp variables
        int value = 0;
        boolean bool = false;

        //set numRecentSlider value
        value = js.getNumRecentFiles();
        if( value == 0 )
        {
            //disable slider and uncheck perform undo checkbox
            this.saveRecent.setSelected(false);
            this.numRecentSlider.setValue( 5 );
            this.numRecentSlider.setEnabled(false);
        }
        else
        {
            this.saveRecent.setSelected(true);
            if( value>0 && value<=js.MAX_NUM_RECENT_FILES )
                this.numRecentSlider.setValue(value);
            else this.numRecentSlider.setValue( 5 );
        }

        //set numUndoSlider value
        value = js.getMaxUndoSize();
        if( value == js.UNLIMITED_UNDO )
            this.numUndoSlider.setValue( js.UNLIMITED_UNDO );
        else this.numUndoSlider.setValue( value );

        //set AUTO HISTOGRAM checkbox value
        bool = js.getAutoHistogram();
        this.AutoHistogramCheckBox.setSelected( bool );

        //set AUTO FILE INFO checkbox value
        bool = js.getAutoFileInfo();
        this.AutoFileInfoCheckBox.setSelected( bool );

        //set RETAIN WINDOW SETTINGS value
        bool = js.getSaveWindowSettings();
        this.saveWindowSettingsCheckBox.setSelected( bool );

        //set DISPLAY SPLASH SCREEN value
        bool = js.getShowSplashScreen();
        this.splashScreenCheckBox.setSelected( bool );

        //set CLEAR PIXEL color
        value = js.getClearPixelColor();
        //set clear pixel color sample square color
        this.clearPixelSlider.setValue( value );
    }

    //COLOR LIST listener
    public void valueChanged(ListSelectionEvent e)
    {
        if(e.getValueIsAdjusting())
            return;

        JList list = (JList)e.getSource();
        if( !list.isSelectionEmpty() )
        {
            int index = list.getSelectedIndex();

            if( list == colorList )
            {
                customColorList.clearSelection();
                BGcolor = js.getColor(index).getColor();
            }
            else //customColorList
            {
                colorList.clearSelection();
                BGcolor = js.getCustomColor(index).getColor();
            }

            setBGColorSample( BGcolor );
        }
    }

    public void addColors(boolean addBasicColors)
    {
        int numColors = js.getNumColors();
        int numCustomColors = js.getNumCustomColors();
        String currentBGColor = js.getBGColor();
        int index = -1;

        if( addBasicColors )
        {
            String[] color = new String[numColors];
            for( int loop=0; loop<numColors; loop++ )
            {
                color[loop] = js.getColor(loop).getName();
                if( color[loop].equalsIgnoreCase( currentBGColor ) )
                    index = loop;
            }
            colorList = new JList( color );
            if( index >=0 )
            {
                colorList.setSelectedIndex(index);
                customColorList.clearSelection();
            }
            else colorList.clearSelection();
            listScrollPane = new JScrollPane(colorList);
            listScrollPane.setVisible(true);
        }

        index = -1;
        String[] customColor = new String[numCustomColors];
        for( int loop=0; loop<numCustomColors; loop++ )
        {
            customColor[loop] = js.getCustomColor(loop).getName();
            if( customColor[loop].equalsIgnoreCase( currentBGColor ) )
                index = loop;
        }
        customColorList = new JList( customColor );
        if( index >=0 )
        {
            customColorList.setSelectedIndex(index);
            colorList.clearSelection();
        }
        else customColorList.clearSelection();

        customColorList.setBorder(BorderFactory.createLoweredBevelBorder());
        customColorList.setDoubleBuffered(true);
        customColorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customColorList.addListSelectionListener(this);
    }

    private void setBGColorSample( Color c )
    {
        Graphics g = sampleBGColorPanel.getGraphics();
        g.setColor( c );
        g.fillRect(0,0, sampleBGColorPanel.getWidth(), sampleBGColorPanel.getHeight());
    }

    private void setClearPixelColorSample( Color c )
    {
        Graphics g = sampleClearPixelColorPanel.getGraphics();
        g.setColor( c );
        g.fillRect(0,0, sampleClearPixelColorPanel.getWidth(), sampleClearPixelColorPanel.getHeight());
    }

    void OkButton_actionPerformed(ActionEvent e)
    {
        js.setAutoFileInfo( this.AutoFileInfoCheckBox.isSelected() );
        js.setAutoHistogram( this.AutoHistogramCheckBox.isSelected() );
        js.setMaxUndoSize( this.numUndoSlider.getValue() );
        js.setSaveWindowSettings( this.saveWindowSettingsCheckBox.isSelected() );
        js.setShowSplashScreen( this.splashScreenCheckBox.isSelected() );
        js.setBGColor( js.getColorName( BGcolor ) );
        js.setClearPixelColor( this.clearPixelSlider.getValue() );
        main_frame.desktop_pane.setBackground(BGcolor);

        //set number of RECENT FILES
        if( this.saveRecent.isSelected() )
        {
            int value = this.numRecentSlider.getValue();
            if( value >= numRecentSlider.getMinimum() && value <= numRecentSlider.getMaximum() )
                js.setMaxNumRecentFiles(value);
        }
        else js.setMaxNumRecentFiles(0);

        this.dispose();
    }

    void cancelButton_actionPerformed(ActionEvent e)
    {
        this.dispose();
    }

    void numRecentSlider_stateChanged(ChangeEvent e)
    {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting())
        {
	        int value = (int)source.getValue();
        }
    }

    void numUndoSlider_stateChanged(ChangeEvent e)
    {
        JSlider source = (JSlider)e.getSource();
        if( !source.getValueIsAdjusting())
        {
            int value = (int)source.getValue();
        }
    }

    void clearPixelSlider_stateChanged(ChangeEvent e)
    {
        JSlider source = (JSlider)e.getSource();
        if( !source.getValueIsAdjusting())
        {
            int value = (int)source.getValue();
            switch( value )
            {
                case 0: setClearPixelColorSample( new Color(js.CLEAR_PIXEL_0) ); break;
                case 1: setClearPixelColorSample( new Color(js.CLEAR_PIXEL_1) ); break;
                case 2: setClearPixelColorSample( new Color(js.CLEAR_PIXEL_2) ); break;
                case 3:
                default: setClearPixelColorSample( new Color(js.CLEAR_PIXEL_3) ); break;
            }
       }
    }

    void saveRecent_actionPerformed(ActionEvent e)
    {
        JCheckBox source = (JCheckBox)e.getSource();
        this.numRecentSlider.setEnabled( source.isSelected() );
    }

    public void updateColorList( boolean removeColor )
    {
        if( removeColor )
        {
            JIPTColor jc = js.getCustomColor( customColorList.getSelectedIndex() );
            js.removeCustomColor( jc.getName() );
        }
        this.addColors(false);   //update Color Lists
        jScrollPane1.getViewport().remove(customColorList);
        jScrollPane1.getViewport().add(customColorList, null);
    }

    void newCustomColorButton_actionPerformed(ActionEvent e)
    {
       try
        {
            JIPTCustomColorDialog jccd = new JIPTCustomColorDialog(this, main_frame);
            jccd.initComponents();
            jccd.setVisible(true);
        }
        catch(Exception ex1)
        {
            // System.out.println("Exception creating JIPT custom color dialog " + ex1.toString());
        }
    }

    void editCustomColorButton_actionPerformed(ActionEvent e)
    {
       try
        {
            JIPTColor jc = js.getCustomColor( customColorList.getSelectedIndex() );
            if( jc != null )
            {
                JIPTCustomColorDialog jccd = new JIPTCustomColorDialog(main_frame);
                jccd.initComponents();
                jccd.setValues(this, jc);
                jccd.setVisible(true);
            }
        }
        catch(Exception ex1)
        {
            // System.out.println("Exception creating JIPT custom color dialog " + ex1.toString());
        }
    }

    void deleteCustomColorButton_actionPerformed(ActionEvent e)
    {
            JIPTColor jc = js.getCustomColor( customColorList.getSelectedIndex() );
            if( jc != null )
            {
                if( JIPTAlert.yesNo(main_frame, "Delete custom color "+jc.getName()+"?", "Are you sure?", "Yes", "No", JOptionPane.QUESTION_MESSAGE ) )
                {
                    //remove custom color
                    this.updateColorList(true);
                    this.show();
                }
            }
    }

    void BackgroundColorPanel_componentShown(ComponentEvent e)
    {
        //set BACKGROUND color of sample square
        BGcolor = js.getColorByName( js.getBGColor() );
        //set background color sample square color
        setBGColorSample( BGcolor );
    }

    void ClearPixelPanel_componentShown(ComponentEvent e)
    {
        //set BACKGROUND color of sample square
        Color color = new Color( js.getClearPixelColor() );
        //set background color sample square color
        setClearPixelColorSample( color );
    }
}