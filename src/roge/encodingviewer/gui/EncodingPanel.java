package roge.encodingviewer.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import roge.encodingviewer.gui.encodingpaneldrawers.*;
import roge.gui.ETextArea.TextUpdateListener;


/**
 * GUI based panel which displays the Encoding Graph.
 * 
 * @author Nicholas Rogé
 */
public class EncodingPanel extends JPanel implements TextUpdateListener,CaretListener{
    /*Begin Constants*/
    /**I have no idea what this is actually for, but meh.  Here's some BS documentation for it so the warning goes away.*/
    private static final long serialVersionUID = 4950181444531553226L;
    
    /**"Error" occurs when there are no bits in the bitstream to read.*/
    public static final int NO_INPUT=0x1;
    /**Error should be raised when a character has been entered into the stream which is not valid for the given encoding type*/
    public static final int INVALID_NUMBER=0x2;
    /*End Constants*/
    
    
    /*Begin Variables*/
    private ArrayList<Boolean> __bitstream=new ArrayList<Boolean>();
    private EncodingDrawer     __drawer=null;
    private int                __error_code=0;
    private int                __error_count=0;
    private int                __selection_start;
    private int                __selection_length;
    private boolean            __bit_stuffing_enabled=false;
    private boolean            __axes_enabled=true;
    private boolean            __bit_interval_markers_enabled=true;
    /*End Variables*/
    
    
    /*Begin Overridden Methods*/
    @Override protected void paintComponent(Graphics graphics){  
        Point graph_anchor=null;
        int graph_height=0;
        int graph_width=0;
        ArrayList<Boolean> bitstream=null;
        
        
        super.paintComponent(graphics);
        
        graph_anchor=new Point(20,20);
        graph_width=this.getWidth()-40;  //A padding of 20 to either side
        graph_height=this.getHeight()-40; //A padding of 20 on top and bottom

        if(this.__bitstream.size()==0){
            this.raiseError(EncodingPanel.NO_INPUT);
        }
        
        if(this.__error_count>0){            
            if(this.__error_code==EncodingPanel.NO_INPUT){  //We only want to display this error if it's the only error
                this._drawCenteredText((Graphics2D)graphics,"No Input",new Font("Arial",Font.BOLD,40));
            }
            if((this.__error_code&EncodingPanel.INVALID_NUMBER)>0){
                this._drawCenteredText((Graphics2D)graphics,"Invalid Character in Input",new Font("Arial",Font.BOLD,40));
            }
            
            this.__error_count=0;
            this.__error_code=0;
            
            return;
        }
        
        if(this.__axes_enabled){
            __drawer.drawAxes((Graphics2D)graphics,graph_width,graph_height,graph_anchor);
        }
        bitstream=this._applyOptions(this.__bitstream);
        
        if(this.__selection_length>0){
            ((Graphics2D)graphics).setPaint(Color.BLUE);
            __drawer.highlightSelectedBits((Graphics2D)graphics,graph_width,graph_height,graph_anchor,bitstream,this.__selection_start,this.__selection_length);
        }
        
        ((Graphics2D)graphics).setPaint(Color.RED);
        __drawer.drawBits((Graphics2D)graphics,graph_width,graph_height,graph_anchor,bitstream);
        if(this.__bit_interval_markers_enabled){
            __drawer.drawBitSeperators((Graphics2D)graphics,graph_width,graph_height,graph_anchor,bitstream.size());
        }
    }
    
    @Override public void recieveTextUpdate(String text){        
        this.__bitstream.clear();
        
        if(text!=null&&text.length()>0){
            for(int index=0;index<text.length();index++){
                if(text.charAt(index)=='0'){
                    this.__bitstream.add(false);
                }else if(text.charAt(index)=='1'){
                    this.__bitstream.add(true);
                }else{
                    this.raiseError(EncodingPanel.INVALID_NUMBER);
                }
            }
        }
        
        this.repaint();
    }
    
    @Override public void caretUpdate(CaretEvent caret){
        this.__selection_length=caret.getMark()-caret.getDot();
        
        if(this.__selection_length<0){
            this.__selection_length*=-1;
            this.__selection_start=caret.getDot()-this.__selection_length;
            
            this.repaint();
        }else{
            this.__selection_start=caret.getDot();
        }
    }
    /*End Overridden Methods*/
    
    /*Begin Other Essential Methods*/
    /**
     * Draws a string horizontally and vertically centered in the screen.
     * 
     * @param graphics Graphics2D object to draw on.
     * @param text Text to be drawn
     * @param font Font to draw the given text in.
     */
    protected void _drawCenteredText(Graphics2D graphics,String text,Font font){
        FontMetrics metrics=null;
        
        
        graphics.setFont(font);
        
        metrics=graphics.getFontMetrics();
        graphics.drawString(text,(this.getWidth()/2)-(metrics.stringWidth(text)/2),(this.getHeight()/2)-(metrics.getHeight()/2)+metrics.getHeight());
    }
    
    /**
     * Enables or disables bitstuffing on this object.  
     * 
     * @param enabled This should be set to <code>true</code> if bitstuffing should be enabled, and <code>false</code> otherwise.
     */
    public void setBitStuffingEnabled(boolean enabled){
        this.__bit_stuffing_enabled=enabled;
        
        this.repaint();
    }
    
    /**
     * Enables or disables the drawing of the axes on this object.  
     * 
     * @param enabled This should be set to <code>true</code> if the axes should be enabled, and <code>false</code> otherwise.
     */
    public void setAxesEnabled(boolean enabled){
        this.__axes_enabled=enabled;
        
        this.repaint();
    }
    
    /**
     * Enables or disables the drawing of the bit separators on this object.  
     * 
     * @param enabled This should be set to <code>true</code> if the bit separators should be enabled, and <code>false</code> otherwise.
     */
    public void setBitSeparatorsEnabled(boolean enabled){
        this.__bit_interval_markers_enabled=enabled;
        
        this.repaint();
    }
    
    protected ArrayList<Boolean> _applyOptions(final ArrayList<Boolean> bitstream){
        ArrayList<Boolean> modified_bitstream=null;
        
        
        if(!this.__bit_stuffing_enabled){
            return bitstream;
        }
        
        modified_bitstream=new ArrayList<Boolean>();
        for(boolean value:bitstream){
            modified_bitstream.add(value);
        }
        
        //Actually apply the options.
        if(this.__bit_stuffing_enabled){
            this._applyBitstuffing(modified_bitstream);
        }
        
        return modified_bitstream;
    }
    
    protected void _applyBitstuffing(ArrayList<Boolean> bitstream){
        for(int index=0,consecutive_ones=0;index<bitstream.size();index++){
            if(bitstream.get(index)){
                consecutive_ones++;
                
                if(consecutive_ones==5){
                    bitstream.add(index+1,false);
                    
                    index++;//Gotta skip the bit we just added.
                    consecutive_ones=0;
                }
            }else{
                consecutive_ones=0;
            }
        }
    }
    
    /**
     * Changes the registered EncodingDrawer object to the given drawer.
     * 
     * @param drawer EncodingDrawer to change to.
     */
    public void changeEncodingDrawer(EncodingDrawer drawer){
        this.__drawer=drawer;
        
        this.repaint();
    }
    
    /**
     * Raises a given error.  This should be one of the errors in the EncodingError class.
     * 
     * @param error_code Error to be raised.
     */
    public void raiseError(int error_code){
        if((this.__error_code&error_code)==0){  //If the error has already been raised, we don't want to attempt to raise it again.
            this.__error_code+=error_code;
            this.__error_count++;   
        }
    }
    /*End Other Essential Methods*/
}
