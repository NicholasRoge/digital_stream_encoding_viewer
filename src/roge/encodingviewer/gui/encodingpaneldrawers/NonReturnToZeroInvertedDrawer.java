/**
 * 
 */
package roge.encodingviewer.gui.encodingpaneldrawers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import roge.encodingviewer.gui.EncodingPanel;

/**
 * @author Nicholas Rogé
 */
public class NonReturnToZeroInvertedDrawer extends NonReturnToZeroDrawer{
    public static final int CANVAS_LEFT_PADDING=10;

    @Override public void drawBits(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,ArrayList<Boolean> bits){
        boolean inversion_toggle=false;
        
        
        canvas_width-=NonReturnToZeroDrawer.CANVAS_LEFT_PADDING;
        
        if(bits.size()>0){
            for(int bit_index=0,x1=0,x2=0,y=0;bit_index<bits.size();bit_index++){
                x1=(int)Math.ceil(((double)bit_index/(double)bits.size())*(canvas_width-10))+origin.x+NonReturnToZeroDrawer.CANVAS_LEFT_PADDING+10;
                x2=(int)Math.ceil(((double)(bit_index+1)/(double)bits.size())*(canvas_width-10))+origin.x+NonReturnToZeroDrawer.CANVAS_LEFT_PADDING+10;
                
                if(bits.get(bit_index)){
                    if(!inversion_toggle){
                        y=origin.y;
                    }else{
                        y=origin.y+canvas_height;
                    }
                    
                    inversion_toggle=!inversion_toggle;
                }else{
                    if(!inversion_toggle){
                        y=origin.y+canvas_height;
                    }else{
                        y=origin.y;
                    }
                }
                
                canvas.draw(new Line2D.Double(new Point(x1,y),new Point(x2,y)));
            }
        }
    }
    
    @Override public String getName(){
        return "Non-Return to Zero";
    }
}
