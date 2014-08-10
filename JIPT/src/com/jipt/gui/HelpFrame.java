package com.jipt.gui;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import com.jipt.JIPTsettings;

public class HelpFrame extends JFrame //implements HyperlinkListener
{

    private static JTextPane text_pane = new JTextPane();

    public HelpFrame()
    {
        String url = "http://www.marx7.org/jipthelp/doc.htm";
        try
        {
            text_pane.setPage( url );
        }
        catch(IOException e)
        {
            System.err.println("Attempted to read a bad URL: " + url);
        }

        this.setTitle("JIPT Help");
        this.setIconImage( new ImageIcon(JIPTsettings.GRAPHICS_PATH + "helpTopics.gif").getImage() );
        this.setSize( 640, 480 );

        JScrollPane scroll_pane = new JScrollPane();
        scroll_pane.createHorizontalScrollBar();
        scroll_pane.createVerticalScrollBar();
        scroll_pane.getViewport().add( text_pane );

        this.getContentPane().add( scroll_pane );
//        text_pane.addHyperlinkListener( this );
        this.setVisible( true );
        this.setEnabled( true );
    }

    public HelpFrame(String url)
    {
        text_pane.setEditable(false);
        try
        {
            text_pane.setPage( url );
        }
        catch (IOException e)
        {
            System.err.println("Attempted to read a bad URL: " + url);
        }
        JScrollPane scroll_pane = new JScrollPane();
        scroll_pane.createHorizontalScrollBar();
        scroll_pane.createVerticalScrollBar();
        scroll_pane.add( text_pane );
        this.getContentPane().add( scroll_pane );
        this.setVisible( true );
        this.setEnabled( true );

    }

/*    public void hyperlinkUpdate(HyperlinkEvent e)
    {
 	    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
        {
            JEditorPane pane = (JEditorPane) e.getSource();
            if (e instanceof HTMLFrameHyperlinkEvent)
            {
                HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                HTMLDocument doc = (HTMLDocument)pane.getDocument();
                doc.processHTMLFrameHyperlinkEvent(evt);
            }
            else
            {
                try
                {
                    pane.setPage(e.getURL());
                }
                catch (Throwable t)
                {
                    t.printStackTrace();
                }
            }
        }
    }
*/
}