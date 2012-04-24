/**
 * 
 */
package roge.encodingviewer.gui.encodingpaneldrawers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import roge.graphics.DashedLine;

/** 
 * @author Nicholas Rogé
 */
public abstract class GeneralDrawer implements EncodingDrawer{
    /**Amount of padding (in pixels) that should be added to the left of the canvas when drawing.*///TODO:  Find a better way of doing that.
    public static final int CANVAS_LEFT_PADDING=10;
    
    
    /*Begin Overridden Methods*/
    @Override public void drawBitSeperators(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,int bit_count){       
        canvas_width-=NonReturnToZeroDrawer.CANVAS_LEFT_PADDING;
        
        if(bit_count>0){//Don't want to divide by zero, do we?
            canvas.setPaint(Color.LIGHT_GRAY);
            for(int bit_tick_index=1,x=0;bit_tick_index<=bit_count;bit_tick_index++){
                x=(int)Math.ceil(((double)bit_tick_index/(double)bit_count)*(canvas_width-10))+origin.x+NonReturnToZeroDrawer.CANVAS_LEFT_PADDING+10;
                
                //EncodingPanel._drawTickLine(canvas,new Point(x,origin.y),new Point(x,origin.y+canvas_height),EncodingPanel.DashFrequency.HIGH);
                canvas.draw(new DashedLine(new Point(x,origin.y),new Point(x,origin.y+canvas_height),DashedLine.DashFrequency.HIGH));
            }
        }
    }
    /*End Overridden Methods*/
}
