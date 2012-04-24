package roge.encodingviewer.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.StrokeBorder;
import roge.encodingviewer.gui.basedecoders.BaseDecoder;
import roge.encodingviewer.gui.basedecoders.BaseDecoder.ToBaseReturn;
import roge.gui.ETextArea;

/**
 * Input Panel designed to take in text and translate it to a given base.
 * 
 * @author Nicholas Rogé
 */
public class EncodingInput extends JPanel{
    /**I have no idea what this is actually for, but meh.  Here's some BS documentation for it so the warning goes away.*/
    private static final long serialVersionUID = 6715060991592690674L;
    
    private BaseDecoder   __current_decoder=null;
    private ETextArea     __input_region=null;
    private JLabel        __label_region=null;
    private boolean       __locked=false;
    private int           __padding_bits=0;
    private BaseDecoder   __previous_decoder=null;
    private JScrollPane   __scrollpane=null;
    
    
    /*Begin Initializer Methods*/
    private void initialize(){
        GridBagConstraints constraints=null;
        
        
        this.getInputRegion().setBackground(Color.WHITE);
        this.getInputRegion().setBorder(new EmptyBorder(2,2,2,2));
        this.__scrollpane=new JScrollPane(this.getInputRegion(),JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.__scrollpane.setBorder(new EmptyBorder(0,0,0,0));
        this.getLabelRegion().setOpaque(true);
        this.getLabelRegion().setBackground(Color.WHITE);
        this.getLabelRegion().setBorder(new EmptyBorder(2,2,2,2));
        
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        constraints=new GridBagConstraints();
        constraints.fill=GridBagConstraints.BOTH;
        
        constraints.gridx=0;
        constraints.weightx=1;
        constraints.weighty=1;
        this.add(this.__scrollpane,constraints);
        
        constraints.gridx=1;
        constraints.weightx=0;
        constraints.weighty=1;
        this.add(this.getLabelRegion(),constraints);
    }
    /*End Initializer Methods*/
    
    /*Begin Constructors*/
    /**
     * Constructs the object.
     */
    public EncodingInput(){
        super();
        
        this.initialize();
    }
    /*End Constructors*/
    
    
    /*Begin Other Essential Methods*/
    /**
     * Sets the decoder to use when translating to and from the given base.
     * 
     * @param base BaseDecoder to use.
     */
    public void setDecoder(BaseDecoder decoder){
        ToBaseReturn to_base=null;
        
        
        if(this.__current_decoder==null){
            this.__current_decoder=decoder;
        }else{
            this.__previous_decoder=this.__current_decoder;
            this.__current_decoder=decoder;
        }
        
        if(this.__previous_decoder!=null&&this.getInputRegion().getText().length()>0){
            to_base=this.__current_decoder.toBase(this.__previous_decoder.toBinary(this.getInputRegion().getText(),this.__padding_bits));
            
            this.__padding_bits=to_base.padding_bits_added;
            this.getInputRegion().setText(to_base.base_string.toUpperCase());
            
            if(this.__padding_bits>0){
                this.lock();
            }else{
                this.unlock();
            }
        }
        
        this.getLabelRegion().setText("Mode:  "+this.__current_decoder.getBaseString());
    }
    
    /**
     * Locks the input for any typing.
     */
    public void lock(){
        this.__locked=true;
        
        this.getInputRegion().setBorder(new StrokeBorder(new BasicStroke(2,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_MITER,1),Color.RED));
        this.getInputRegion().setToolTipText("This item is locked.  This usually occurs when you change your encoding base, and a few bits have to be added on as \"padding\" to allow for a complete string in the destination base.");
    }
    
    /**
     * Unlocks the input to allow typing.
     */
    public void unlock(){
        this.__locked=false;
        
        this.getInputRegion().setBorder(new EmptyBorder(2,2,2,2));
        this.getInputRegion().setToolTipText(null);
    }
    
    /**
     * Boolean check to find out if this object is locked or not.
     * 
     * @return If the object is locked, this method will return <code>true</code> and <code>false</code> otherwise.
     */
    public boolean isLocked(){
        return this.__locked;
    }
    
    /**
     * Gets the input region (The TextArea portion) of this object.
     * 
     * @return The input region (The TextArea portion) of this object.
     */
    public ETextArea getInputRegion(){
        if(this.__input_region==null){
            this.__input_region=new ETextArea(){
                /**I have no idea what this is actually for, but meh.  Here's some BS documentation for it so the warning goes away.*/
                private static final long serialVersionUID = 6715060991592690674L;
                
                
                /*Begin Overridden Methods*/
                @Override public void processKeyEvent(KeyEvent event){        
                    if(event.getKeyCode()==KeyEvent.VK_ENTER){
                        return;
                    }else if(EncodingInput.this.__locked&&!event.isActionKey()){  //We're only allowing the addition of characters into the first position in binary mode because of the padding characters used with the other bases.
                        return;
                    }
                    
                    super.processKeyEvent(event);
                }
                
                @Override protected void _broadcastTextToListeners(String text){
                    super._broadcastTextToListeners(EncodingInput.this.__current_decoder.toBinary(text,EncodingInput.this.__padding_bits));
                }
                /*End Overridden Methods*/
            };
        }
        
        return this.__input_region;
    }
    
    /**
     * Gets the label region (The Label portion) of this object.
     * 
     * @return The label region (The Label portion) of this object.
     */
    public JLabel getLabelRegion(){
        if(this.__label_region==null){
            this.__label_region=new JLabel();
        }
        
        return this.__label_region;
    }
    
    /**
     * Gets the active BaseDecoder.
     * 
     * @return The active BaseDecoder.
     */
    public BaseDecoder getCurrentDecoder(){
        return this.__current_decoder;
    }
    /*End Other Essential Methods*/
}
