/**
 * 
 */
package roge.encodingviewer.gui.basedecoders;

import java.util.ArrayList;

/**
 * @author Nicholas Rogé
 *
 */
public class AutoDetectDecoder implements BaseDecoder{
    private BaseDecoder __decoder;
    private ArrayList<BaseDecoder> __decoders;
    

    /*Begin Overridden Methods*/
    @Override public int getNumericBase(){
        return this.__decoder.getNumericBase();
    }

    @Override public String getBaseString(){
        return "Auto-Detect";
    }
    
    @Override public boolean isValid(String number_string){        
        return this.__decoder.isValid(number_string);
    }
    
    @Override public ToBaseReturn toBase(String binary_string){
        if(!this.isLastDecoderSet()){
            throw new RuntimeException("This method may not be called unless the last decoder has been set.");
        }
        
        return this.__decoder.toBase(binary_string);
    }
    
    @Override public String toBinary(String number_string,int num_padding_bits){
        this.setLastDecoder(this._detectBase(number_string));
        if(this.__decoder==null){
           System.out.print("There are no decoders added to decode that string.\n"); 
           
           return "";
        }
        
        return this.__decoder.toBinary(number_string,num_padding_bits);
    }
    /*End Overridden Methods*/
    
    /*Begin Other Essential Methods*/    
    /**
     * Registers a decoder to be checked against with this object.
     * 
     * @param decoder BaseDecoder to register with this object.
     */
    public void addDecoder(BaseDecoder decoder){       
        if(this.__decoders==null){
            this.__decoders=new ArrayList<BaseDecoder>();
        }
        
        if(this.__decoders.size()==0){
            this.__decoders.add(decoder);
        }else{
            for(int index=0;index<this.__decoders.size();index++){
                if(decoder.getNumericBase()<this.__decoders.get(index).getNumericBase()){
                    this.__decoders.add(index,decoder);
                    
                    return;
                }
            }
            
            this.__decoders.add(decoder);
        }
    }
    
    /**
     * Attempts to detect the base of the given string.
     * 
     * @param derp String of characters to attempt to match against a given base.
     *  
     * @return Returns the proper BaseDecoder for the given string, or <code>null</code> if a sufficient BaseDecoder could not be found.
     */
    protected BaseDecoder _detectBase(String derp){
        for(BaseDecoder base:this.__decoders){  //NOTE:  Remember that the decoders are organized from low base to high base, so it's going to match the lowest base possible.
            if(base.isValid(derp)){
                return base;
            }
        }
        
        return null;
    }
    
    /**
     * Check to see if the last decoder used has been set.
     * 
     * @return <code>true</code> if the last decoder has been set, and <code>false</code> otherwise.
     */
    public boolean isLastDecoderSet(){
        return this.__decoder!=null;
    }
    
    /**
     * Sets decoder that was in use before the AutoDetectDecoder.  This MUST be set if you intend to use the toBinary method of this object.
     * 
     * @param decoder Last decoder that was used.
     */
    public void setLastDecoder(BaseDecoder decoder){
        this.__decoder=decoder;
    }
    /*End Other Essential Methods*/
}
