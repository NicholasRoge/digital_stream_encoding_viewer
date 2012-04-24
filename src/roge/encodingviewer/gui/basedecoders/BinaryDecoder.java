/**
 * 
 */
package roge.encodingviewer.gui.basedecoders;

/**
 * @author Nicholas Rogé
 *
 */
public class BinaryDecoder implements BaseDecoder{
    @Override public String toBinary(String number_string,int padding_bits){
        return number_string;
    }
    
    @Override public ToBaseReturn toBase(String binary_string){
        return new ToBaseReturn(binary_string,0);
    }

    @Override public int getNumericBase(){
        return 2;
    }

    @Override public String getBaseString(){
        return "Binary";
    }

    @Override public boolean isValid(String number_string){
        for(int index=0;index<number_string.length();index++){
            if(!(number_string.charAt(index)=='0'||number_string.charAt(index)=='1')){
                return false;
            }
        }
        
        return true;
    }
}
