package com.jipt;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jipt.gui.MainFrame;
public class JIPTAlert extends JOptionPane
{
    private static boolean cancelled = false;
    public static boolean yesNo(MainFrame frame, String message, String title, String yes, String no, int type)
    {
        //yes/no (not in those words)
            Object[] options = {yes, no};
            int n = JOptionPane.showOptionDialog(frame,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            type,
                            null,
                            options,
                            options[0]);
            if (n == JOptionPane.YES_OPTION)
                return true;
            else return false;
    }

    public static int yesNoCancel(MainFrame frame, String message, String title, String yes, String no, String cancel, int type)
    {
            // yes = option 1, returns 0
            // no  = option 2, returns 1
            // cancel = option 3, returns 2
            Object[] options = {yes, no, cancel};
            int n = JOptionPane.showOptionDialog(frame,
                            message,
                            title,
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            type,
                            null,
                            options,
                            options[0]);
            if (n == JOptionPane.YES_OPTION)
            {
                return 0;
            }

            if( n==JOptionPane.CANCEL_OPTION)
            {
                return 2;
            }
            //else JOptionPane.NO_OPTION

            return 1; // ?

    }

    public static boolean ok(MainFrame frame, String message, String title, String ok, int type)
    {
        //OK button dialog
            Object[] options = {ok};
            int n = JOptionPane.showOptionDialog(frame,
                            message,
                            title,
                            JOptionPane.OK_OPTION,
                            type,
                            null,
                            options,
                            options[0]);
            if (n == JOptionPane.YES_OPTION)
                return true;
            else return false;
    }

    public static void message(MainFrame frame, String message, String title, String button, int type)
    {
        //OK button dialog
        Object[] options = {button};
        int n = JOptionPane.showOptionDialog(frame,
                        message,
                        title,
                        JOptionPane.CANCEL_OPTION,
                        type,
                        null,
                        options,
                        options[0]);
        if (n == JOptionPane.CANCEL_OPTION)
            cancelled = true;
        else cancelled = false;
    }

    public static boolean error(JFrame frame, String message, String title, String ok)
    {
        int type = JOptionPane.ERROR_MESSAGE;
        //OK button dialog
            Object[] options = {ok};
            int n = JOptionPane.showOptionDialog(frame,
                            message,
                            title,
                            JOptionPane.OK_OPTION,
                            type,
                            null,
                            options,
                            options[0]);

            if (n == JOptionPane.YES_OPTION)
                return true;
            else return false;
    }

    public static boolean yesNo(JFrame frame, String message, String title, String yes, String no, int type)
    {
        //yes/no (not in those words)
            Object[] options = {yes, no};
            int n = JOptionPane.showOptionDialog(frame,
                            message,
                            title,
                            JOptionPane.YES_NO_OPTION,
                            type,
                            null,
                            options,
                            options[0]);
            if (n == JOptionPane.YES_OPTION)
                return true;
            else return false;
    }

    public boolean isCancelled()
    {
        return cancelled;
    }

    }
