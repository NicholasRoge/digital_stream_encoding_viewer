package roge.encodingviewer.gui.encodingpaneldrawers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Class intent is to provide an interface for the EncodingPanel to use to do its drawing.
 * 
 * @author Nicholas Rogé
 */
public interface EncodingDrawer{
    /**
     * Draws both the X and Y axes.
     * 
     * @param canvas The Graphics2D object to which any drawing should be done.
     * @param canvas_width The total width this object has to draw in.
     * @param canvas_height The total height this object has to draw in.
     * @param origin The top-left most point of the canvas.
     */
    public void drawAxes(Graphics2D canvas,int canvas_width,int canvas_height,Point origin);
    
    /**
     * Draws the bits.
     * 
     * @param canvas The Graphics2D object to which any drawing should be done.
     * @param canvas_width The total width this object has to draw in.
     * @param canvas_height The total height this object has to draw in.
     * @param origin The top-left most point of the canvas.
     * @param bit Boolean representation of the bits.  <code>True<code> if bit is set, <code>false</code> otherwise.
     */
    public void drawBits(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,ArrayList<Boolean> bits);
    
    /**
     * Draws the lines that separate the bits.
     * 
     * @param canvas The Graphics2D object to which any drawing should be done.
     * @param canvas_width The total width this object has to draw in.
     * @param canvas_height The total height this object has to draw in.
     * @param origin The top-left most point of the canvas.
     * @param bit_count The number of bits in the given frame.
     */
    public void drawBitSeperators(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,int bit_count);
    
    /**
     * Highlights any bits which are selected in the input.
     * 
     * @param canvas The Graphics2D object to which any drawing should be done.
     * @param canvas_width The total width this object has to draw in.
     * @param canvas_height The total height this object has to draw in.
     * @param origin The top-left most point of the canvas.
     * @param bitstream Stream of bits, ordered from the most significant bit to the least significan bit.
     * @param start_index Selection start index.
     * @param selection_length Total number of bits selected.
     */
    public void highlightSelectedBits(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,ArrayList<Boolean> bitstream,int start_index,int selection_length);
    
    /**
     * Gets the name of this encoder's type.
     * 
     * @return The name of this encoder's type.
     */
    public String getName();
}
