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
public class DifferentialManchesterDrawer extends ManchesterDrawer{
    public static final int CANVAS_LEFT_PADDING=10;


    @Override public void drawBits(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,ArrayList<Boolean> bits){
        canvas_width-=CANVAS_LEFT_PADDING;
        
        if(bits.size()>0){
            for(int bit_index=0,x1=0,x2=0,y1=origin.y+canvas_height,y2=origin.y;bit_index<bits.size();bit_index++){
                x1=(int)Math.ceil(((double)bit_index/(double)bits.size())*(canvas_width-10))+origin.x+CANVAS_LEFT_PADDING+10;
                x2=(int)Math.ceil(((double)(bit_index+1)/(double)bits.size())*(canvas_width-10))+origin.x+CANVAS_LEFT_PADDING+10;
                
                if(bits.get(bit_index).booleanValue()){
                    y1=y2;  //We want y1 to be whatever the last bit's last bit was.
                    if(y1==origin.y){
                        y2=origin.y+canvas_height;
                    }else{
                        y2=origin.y;
                    }
                }
                
                canvas.draw(new Line2D.Double(new Point(x1,y1),new Point(((x2-x1)/2)+x1,y1)));
                canvas.draw(new Line2D.Double(new Point(((x2-x1)/2)+x1,y2),new Point(x2,y2)));
                canvas.draw(new Line2D.Double(new Point(((x2-x1)/2)+x1,y1),new Point(((x2-x1)/2)+x1,y2)));
            }
        }
    }
    
    @Override public String getName(){
        return "Differential Manchester";
    }
}
