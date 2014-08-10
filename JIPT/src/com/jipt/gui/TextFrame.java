package com.jipt.gui;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import com.jipt.EditManager;
import com.jipt.JIPTColor;
import com.jipt.ToolManager;

public class TextFrame extends JFrame
{
	String sizes[] = {"6", "8", "10", "11", "12", "14", "16", "18", "20", "22", "24",  "26", "36", "48", "72"};
	JIPTColor colors[] = null; //{"Black", "White", "Blue", "Red", "Green", "Orange"};
	String [] fonts = null;
    String [] styles = {"Plain", "Bold", "Italic"};

    MainFrame main_frame = null;
    ImageFrame image_frame = null;

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel textBoxLabel = new javax.swing.JLabel();
	javax.swing.JLabel fontLabel = new javax.swing.JLabel();
	javax.swing.JComboBox fontComboBox = new javax.swing.JComboBox();
	javax.swing.JLabel sizeLabel = new javax.swing.JLabel();
	javax.swing.JComboBox sizeComboBox = new javax.swing.JComboBox();
	javax.swing.JLabel colorLabel = new javax.swing.JLabel();
	javax.swing.JComboBox colorComboBox = new javax.swing.JComboBox();
	javax.swing.JLabel previewLabel = new javax.swing.JLabel();
	java.awt.TextField contentTextField = new java.awt.TextField();
	javax.swing.JButton okButton = new javax.swing.JButton();
	javax.swing.JButton cancelButton = new javax.swing.JButton();
// END GENERATED CODE

    javax.swing.JLabel styleLabel = new javax.swing.JLabel("Style");
    javax.swing.JComboBox styleComboBox = new javax.swing.JComboBox();

	public TextFrame(MainFrame mf, ImageFrame i_frame)
	{
        main_frame = mf;
        image_frame = i_frame;
	}

	public void initComponents() throws Exception
	{
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		textBoxLabel.setSize(new java.awt.Dimension(40, 20));
		textBoxLabel.setVisible(true);
		textBoxLabel.setText("Text");
		textBoxLabel.setLocation(new java.awt.Point(10, 10));

		fontLabel.setSize(new java.awt.Dimension(40, 20));
		fontLabel.setVisible(true);
		fontLabel.setText("Font");
		fontLabel.setLocation(new java.awt.Point(80, 40));

		fontComboBox.setSize(new java.awt.Dimension(130, 26));
		fontComboBox.setVisible(true);
		fontComboBox.setLocation(new java.awt.Point(40, 70));

        styleLabel.setSize(new java.awt.Dimension(40, 20));
		styleLabel.setVisible(true);
		styleLabel.setText("Style");
		styleLabel.setLocation(new java.awt.Point(215, 40));

        styleComboBox.setSize(new java.awt.Dimension(80, 26));
		styleComboBox.setVisible(true);
		styleComboBox.setLocation(new java.awt.Point(190, 70));

		sizeLabel.setSize(new java.awt.Dimension(40, 20));
		sizeLabel.setVisible(true);
		sizeLabel.setText("Size");
		sizeLabel.setLocation(new java.awt.Point(310, 40));

		sizeComboBox.setSize(new java.awt.Dimension(60, 26));
		sizeComboBox.setVisible(true);
		sizeComboBox.setLocation(new java.awt.Point(295, 70));

		colorLabel.setSize(new java.awt.Dimension(40, 20));
		colorLabel.setVisible(true);
		colorLabel.setText("Color");
		colorLabel.setLocation(new java.awt.Point(430, 40));

		colorComboBox.setSize(new java.awt.Dimension(130, 26));
		colorComboBox.setVisible(true);
		colorComboBox.setLocation(new java.awt.Point(380, 70));

		previewLabel.setSize(new java.awt.Dimension(500, 190));
		previewLabel.setVisible(true);
		previewLabel.setLocation(new java.awt.Point(30, 120));

		contentTextField.setSize(new java.awt.Dimension(420, 20));
		contentTextField.setVisible(true);
		contentTextField.setLocation(new java.awt.Point(60, 10));

		okButton.setSize(new java.awt.Dimension(130, 30));
		okButton.setVisible(true);
		okButton.setText("OK");
		okButton.setLocation(new java.awt.Point(160, 340));

		cancelButton.setSize(new java.awt.Dimension(130, 30));
		cancelButton.setVisible(true);
		cancelButton.setText("Cancel");
		cancelButton.setLocation(new java.awt.Point(300, 340));

		setSize(new java.awt.Dimension(566, 410));
		setBackground(new java.awt.Color(200, 200, 200));
		getContentPane().setLayout(null);
		setTitle("Add Text");
		setLocation(new java.awt.Point(0, 0));
		getContentPane().add(textBoxLabel);
		getContentPane().add(fontLabel);
        getContentPane().add(styleLabel);
        getContentPane().add(styleComboBox);
		getContentPane().add(fontComboBox);
		getContentPane().add(sizeLabel);
		getContentPane().add(sizeComboBox);
		getContentPane().add(colorLabel);
		getContentPane().add(colorComboBox);
		getContentPane().add(previewLabel);
		getContentPane().add(contentTextField);
		getContentPane().add(okButton);
		getContentPane().add(cancelButton);


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

	///////////////////////////////////////////////
	//////////// Set the combo boxes //////////////
	///////////////////////////////////////////////
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    fonts = ge.getAvailableFontFamilyNames();
	//Toolkit tk = Toolkit.getDefaultToolkit();
	//fonts = tk.getFontList();
    colors = main_frame.getJIPTsettings().getJIPTColors();

	for(int i = 0; i < fonts.length; i++)
		fontComboBox.addItem(fonts[i]);

	for(int i = 0; i < sizes.length; i++)
		sizeComboBox.addItem(sizes[i]);

	for(int i = 0; i < colors.length; i++)
		colorComboBox.addItem(colors[i].getName());

    for(int i = 0; i < styles.length; i++)
		styleComboBox.addItem(styles[i]);



	colorComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       changeText();
                   }
        });

    sizeComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       changeText();
                   }
        });

    fontComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       changeText();
                   }
        });

    styleComboBox.addActionListener(new ActionListener(){
                   public void actionPerformed(ActionEvent e)
                   {
                       changeText();
                   }
        });

    contentTextField.addTextListener(new java.awt.event.TextListener() {
			public void textValueChanged(java.awt.event.TextEvent e) {
				changeText();
			}
		});
	/*contentTextField.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				changeText();
			}
		});*/


	contentTextField.setText("Test");
	sizeComboBox.setSelectedIndex(6);

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

	//////////////////////////////////////////////////
	////////////// Change the font label's text //////
	//////////////////////////////////////////////////
	public void changeText()
	{
		int size = Integer.parseInt(sizes[sizeComboBox.getSelectedIndex()]);
		String fontName = fonts[fontComboBox.getSelectedIndex()];
	    String text = contentTextField.getText();
        Color new_color = colors[colorComboBox.getSelectedIndex()].getColor();

        Font f = null;

        ////////////////////////////////
        /////////// Set style //////////
        ////////////////////////////////
        String type_string = styles[styleComboBox.getSelectedIndex()];
        if(type_string.equalsIgnoreCase("Plain"))
        {
            f = new Font(fontName, Font.PLAIN, size);
        }
        else if(type_string.equalsIgnoreCase("Bold"))
        {
            f = new Font(fontName, Font.BOLD, size);
        }
        else if(type_string.equalsIgnoreCase("Italic"))
        {
            f = new Font(fontName, Font.ITALIC, size);
        }
        else
        {
            f = new Font(fontName, Font.PLAIN, size);
        }



		//

        previewLabel.setFont(f);

        previewLabel.setForeground(new_color);
        previewLabel.setText(text);
        previewLabel.setOpaque(true);
        previewLabel.setBackground(Color.white);
        //previewLabel.repaint();

		// Create new image based on the text
		//FontMetrics fm = contentTextField.getFontMetrics(f);



	}

	public void okButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        ////////////////////////////////////
        ///////// Get font info ////////////
        ////////////////////////////////////
        int size = Integer.parseInt(sizes[sizeComboBox.getSelectedIndex()]);
		String fontName = fonts[fontComboBox.getSelectedIndex()];
		String text = contentTextField.getText();
        Color new_color = colors[colorComboBox.getSelectedIndex()].getColor();

		Font f = null;

        ////////////////////////////////
        /////////// Set style //////////
        ////////////////////////////////
        String type_string = styles[styleComboBox.getSelectedIndex()];
        if(type_string.equalsIgnoreCase("Plain"))
        {
            f = new Font(fontName, Font.PLAIN, size);
        }
        else if(type_string.equalsIgnoreCase("Bold"))
        {
            f = new Font(fontName, Font.BOLD, size);
        }
        else if(type_string.equalsIgnoreCase("Italic"))
        {
            f = new Font(fontName, Font.ITALIC, size);
            text += "   "; // Add this because the slanted letters at the end will overrun the area
        }
        else
        {
            f = new Font(fontName, Font.PLAIN, size);
        }


        // create a canvas in memory
        FontMetrics fm = contentTextField.getFontMetrics(f);
        int width = fm.stringWidth(text);
        int height = fm.getHeight();

        TextCanvas canvas = new TextCanvas(text, f, fm, new_color);
        canvas.setSize(width, height);
        canvas.setVisible(true);
        canvas.repaint();

        // get an Image object
        Image im = canvas.getImage();

        im = ToolManager.trimTextImage(im, im.getWidth(this), im.getHeight(this));
        // For now, create it as a new image
        //main_frame.createNewImage(im);

        //get edit manager
        EditManager edit_manager = main_frame.getEditManager();

        //post text image on dragboard
        edit_manager.setDragboardImage(im);

        //execute paste command
//        image_frame.doPaste(im);
        edit_manager.doPaste(false, true);

        //close this frame
        thisWindowClosing(null);
	}

	public void cancelButtonActionPerformed(java.awt.event.ActionEvent e)
	{
        thisWindowClosing(null);
	}

    ///////////////////////////////////////////////////////////////
    //////// Internal class for drawing text on a canvas //////////
    ///////////////////////////////////////////////////////////////
    class TextCanvas extends Canvas
    {
        Font   font = null;
        String text = null;
        FontMetrics font_metrics = null;
        Color color = null;

        public TextCanvas(String t, Font f, FontMetrics fm, Color c)
        {
            super();
            color = c;
            font_metrics = fm;
            text = t;
            font = f;
        }


        public void paint(Graphics g)
        {
            // Fill the whole rectangle with transparent background

            // Paint the text in the specified font
            this.setFont(font);
            //System.out.println("Drawing string " + text);
            //System.out.println("Font size = " + font.getSize());

            Color transparent_color = new Color(255, 255, 255, 0); // Transparent BG
            g.setColor(transparent_color);
            // System.out.println("Descent = " + font_metrics.getDescent());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(color);
            g.setFont(font);
            g.drawString(text, 0, this.getHeight() - font_metrics.getDescent());

        }

        public Image getImage()
        {
            Rectangle rect = getBounds();
            Image fileImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = fileImage.getGraphics();

            //write to the image
            this.paint(g);
            return fileImage;

        }
    }


}



