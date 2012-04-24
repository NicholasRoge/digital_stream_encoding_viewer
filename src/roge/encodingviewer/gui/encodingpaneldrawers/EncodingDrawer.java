package roge.encodingviewer.gui.encodingpaneldrawers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Nicholas Rogé
 *
 */
public interface EncodingDrawer{
    /**
     * Draws both the X and Y axes.
     */
    public void drawAxes(Graphics2D canvas,int canvas_width,int canvas_height,Point point);
    
    /**
     * Draws the bits.
     * 
     * @param bit Boolean representation of the bits.  <code>True<code> if bit is set, <code>false</code> otherwise.
     */
    public void drawBits(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,ArrayList<Boolean> bits);
    
    public void drawBitSeperators(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,int bit_count);
    
    public void highlightSelectedBits(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,ArrayList<Boolean> bitstream,int start_index,int selection_length);
    
    public String getName();
}
