package roge.encodingviewer.main;

import java.awt.Dimension;
import roge.encodingviewer.gui.EncodingWindow;
import roge.encodingviewer.resources.Resources;

/**
 * Main class for the program...  Literally!<br />
 * ...<br />
 * Yes my jokes are lame...  Leave me alone.
 * 
 * @author Nicholas Rogé
 */
public class Main{
    private static EncodingWindow __window=null;

    
    /*Begin Main*/
    /**
     * Entry point for the application.
     * 
     * @param arguments Arguments passed to this program at runtime. 
     */
    public static void main(String[] arguments){             
        __window=new EncodingWindow(Resources.Strings.app_title);
        __window.setMinimumSize(new Dimension(800,600));
        __window.setVisible(true);
    }
    /*End Main*/
}
