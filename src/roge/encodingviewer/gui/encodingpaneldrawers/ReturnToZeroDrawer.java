package roge.encodingviewer.gui.encodingpaneldrawers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;


/**
 * @author Nicholas Rogé
 */
public class ReturnToZeroDrawer extends GeneralDrawer{
    /*Begin Overridden Methods*/
    @Override public void drawAxes(Graphics2D canvas,int canvas_width,int canvas_height,Point origin){
        canvas_width-=CANVAS_LEFT_PADDING;
        
        /*Add the graph lines*/
        canvas.setPaint(Color.BLACK);
        
        /*Y Axis*/
        canvas.draw(new Line2D.Double(new Point(origin.x+CANVAS_LEFT_PADDING,origin.y),new Point(origin.x+CANVAS_LEFT_PADDING+10,origin.y)));
        canvas.draw(new Line2D.Double(new Point(origin.x+CANVAS_LEFT_PADDING,origin.y+canvas_height),new Point(origin.x+CANVAS_LEFT_PADDING+10,origin.y+canvas_height)));
        canvas.draw(new Line2D.Double(new Point(origin.x+CANVAS_LEFT_PADDING+10,origin.y),new Point(origin.x+CANVAS_LEFT_PADDING+10,origin.y+canvas_height)));
        canvas.drawString("+V",origin.x+CANVAS_LEFT_PADDING-canvas.getFontMetrics().stringWidth("+V"),origin.y+canvas.getFontMetrics().getHeight());
        canvas.drawString("0V",origin.x+CANVAS_LEFT_PADDING-canvas.getFontMetrics().stringWidth("0V"),origin.y+(canvas_height/2)+(canvas.getFontMetrics().getHeight()/2));
        canvas.drawString("-V",origin.x+CANVAS_LEFT_PADDING-canvas.getFontMetrics().stringWidth("-V"),origin.y+canvas_height);
        
        /*X Axis*/
        canvas.draw(new Line2D.Double(new Point(origin.x+CANVAS_LEFT_PADDING+10,origin.y+(canvas_height/2)),new Point(origin.x+CANVAS_LEFT_PADDING+canvas_width,origin.y+(canvas_height/2))));
    }

    @Override public void drawBits(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,ArrayList<Boolean> bits){
        canvas_width-=CANVAS_LEFT_PADDING;
        
        if(bits.size()>0){
            for(int bit_index=0,x1=0,x2=0,y=0;bit_index<bits.size();bit_index++){
                x1=(int)Math.ceil(((double)bit_index/(double)bits.size())*(canvas_width-10))+origin.x+CANVAS_LEFT_PADDING+10;
                x2=(int)Math.ceil(((double)(bit_index+1)/(double)bits.size())*(canvas_width-10))+origin.x+CANVAS_LEFT_PADDING+10;
                
                if(bits.get(bit_index).booleanValue()){
                    y=origin.y;
                }else{
                    y=origin.y+canvas_height;
                }
                
                canvas.draw(new Line2D.Double(new Point(x1,y),new Point(((x2-x1)/2)+x1,y)));
                canvas.draw(new Line2D.Double(new Point(((x2-x1)/2)+x1,origin.y+(canvas_height/2)),new Point(x2,origin.y+(canvas_height/2))));
                canvas.draw(new Line2D.Double(new Point(((x2-x1)/2)+x1,y),new Point(((x2-x1)/2)+x1,origin.y+(canvas_height/2))));
            }
        }
    }
    
    @Override public void highlightSelectedBits(Graphics2D canvas,int canvas_width,int canvas_height,Point origin,ArrayList<Boolean> bitstream,int start_index,int selection_length){
        //Nothing here yet!
    }
    
    @Override public String getName(){
        return "Return to Zero";
    }
    /*End Overridden Methods*/
}
