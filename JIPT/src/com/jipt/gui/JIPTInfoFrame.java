package com.jipt.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.jipt.JIPTsettings;

/**
 * Title:        Java Image Processing Toolkit
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      cis480
 * @author Trent Lucier, James LaTour, Victor Rego
 * @version 1.0
 */

//public class JIPTInfoFrame extends JInternalFrame
public class JIPTInfoFrame extends JFrame
{
    private static FrameManager frame_manager = null;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel fileName = new JLabel();
    JLabel filePath = new JLabel();
    JLabel bitDepth = new JLabel();
    JLabel xSizeLabel = new JLabel();
    JButton okButton = new JButton();
    JLabel jLabel5 = new JLabel();
    JLabel ySizeLabel = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel fileType = new JLabel();

    public JIPTInfoFrame( FrameManager fm )
    {
        super();
        frame_manager = fm;
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        this.setIconImage( new ImageIcon(JIPTsettings.GRAPHICS_PATH + "info.gif").getImage() );
        this.setSize(275,185);
        this.setVisible(false);
        this.setResizable(false);
       // this.setClosable(true);
    }

    void okButton_actionPerformed(ActionEvent e)
    {
        ImageFrame im_frame = frame_manager.getFrame( this.fileName.getText() );
        im_frame.setShowFileInfo(false);
        this.setVisible( false );
    }

    public void setFileName( String fn )
    {
        this.fileName.setText( fn );    //set filename

        //determine file type
        int index = fn.indexOf(".");
        fn = fn.substring( index+1 );
        if( fn.equalsIgnoreCase( "bmp" ) )
            this.fileType.setText( "Bitmap (.bmp)" );
        else if( fn.equalsIgnoreCase( "gif" ) )
            this.fileType.setText( "Graphics Interchange Format (.gif)" );
        else if( fn.equalsIgnoreCase( "jpeg" ) || fn.equalsIgnoreCase( "jpg" ) )
            this.fileType.setText( "JPEG (." + fn + ")" );
        else  if( fn.equalsIgnoreCase( "pgm" ) )
            this.fileType.setText( "PGM (.pgm)" );
        else this.fileType.setText( "PPM (.ppm)" );

    }

    public String getFilename()
    {
        return this.fileName.getText();
    }

    public void setFilePath( String p )
    {
        this.filePath.setText( p );
    }

    public void setColor( int bpp )
    {
        ImageFrame img_frame = frame_manager.getSelectedFrame();
        if( img_frame.isColor() )
            this.bitDepth.setText( bpp + "-bit color" );
        else this.bitDepth.setText( bpp + "-bit greyscale" );
    }

    public void setImageSize( int w, int h)
    {
        this.xSizeLabel.setText(""+w);
        this.ySizeLabel.setText(""+h);
    }

    private void jbInit() throws Exception {
        jLabel1.setText("File name:");
        jLabel1.setBounds(new Rectangle(5, 5, 65, 17));
        this.setTitle("Image Information");
        this.getContentPane().setLayout(null);
        this.getContentPane().setBackground(Color.lightGray);
        this.setEnabled(true);

        jLabel2.setText("Path:");
        jLabel2.setBounds(new Rectangle(5, 45, 41, 17));
        jLabel3.setText("Color Depth:");
        jLabel3.setBounds(new Rectangle(5, 110, 82, 17));
        jLabel4.setText("Width:");
        jLabel4.setBounds(new Rectangle(100, 135, 41, 17));
        fileName.setFont(new java.awt.Font("Dialog", 0, 12));
        fileName.setText("filename");
        fileName.setBounds(new Rectangle(10, 23, 240, 17));
        filePath.setFont(new java.awt.Font("Dialog", 0, 12));
        filePath.setText("path");
        filePath.setBounds(new Rectangle(10, 59, 240, 17));
        bitDepth.setFont(new java.awt.Font("Dialog", 0, 12));
        bitDepth.setText("bpp");
        bitDepth.setBounds(new Rectangle(90, 110, 154, 17));
        xSizeLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        xSizeLabel.setText("x");
        //xSizeLabel.setBounds(new Rectangle(50, 135, 25, 17));
        xSizeLabel.setBounds(new Rectangle(50, 135, 40, 17)); // Trent, 12/8
        okButton.setText("Close");
        okButton.setBounds(new Rectangle(190, 125, 70, 25));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButton_actionPerformed(e);
            }
        });
        jLabel5.setText("Height:");
        jLabel5.setBounds(new Rectangle(5, 135, 41, 17));
        ySizeLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        ySizeLabel.setText("y");
        //ySizeLabel.setBounds(new Rectangle(145, 135, 35, 17));
        ySizeLabel.setBounds(new Rectangle(145, 135, 35, 17)); // Trent, 12/8
        jLabel6.setText("File Type:");
        jLabel6.setBounds(new Rectangle(5, 85, 58, 17));
        fileType.setFont(new java.awt.Font("Dialog", 0, 12));
        fileType.setText("type");
        fileType.setBounds(new Rectangle(75, 85, 174, 17));
        this.getContentPane().add(ySizeLabel, null);
        this.getContentPane().add(okButton, null);
        this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(jLabel5, null);
        this.getContentPane().add(filePath, null);
        this.getContentPane().add(fileName, null);
        this.getContentPane().add(bitDepth, null);
        this.getContentPane().add(jLabel3, null);
        this.getContentPane().add(fileType, null);
        this.getContentPane().add(jLabel2, null);
        this.getContentPane().add(jLabel6, null);
        this.getContentPane().add(xSizeLabel, null);
        this.getContentPane().add(jLabel4, null);
    }

}
