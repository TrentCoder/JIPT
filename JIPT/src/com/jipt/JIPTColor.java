package com.jipt;
import java.awt.Color;

public class JIPTColor
{
    private String name = null;
    private Color color = null;

    public JIPTColor( String n, Color c )
    {
        name = n;
        color = c;
    }

    public String getName()
    {
        return name;
    }

    public Color getColor()
    {
        return color;
    }
} //end class JIPTColor