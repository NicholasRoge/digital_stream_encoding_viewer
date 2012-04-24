/**
 * 
 */
package roge.encodingviewer.gui.basedecoders;

/**
 * @author Nicholas Rogé
 *
 */
public class OctalDecoder implements BaseDecoder{
    @Override public String toBinary(String number_string,int padding_bits){
        final int unit_size=3;
        
        String binary_string="";
        String binary_unit=null;
        
        
        if(number_string==null||number_string.length()==0){
            return "";
        }
        
        for(int index=0;index<number_string.length();index++){
            binary_unit=Integer.toBinaryString(Integer.parseInt(""+number_string.charAt(index),8));
            while((binary_unit.length()%unit_size)>0){
                binary_unit='0'+binary_unit;
            }
            
            binary_string+=binary_unit;
        }
        
        return binary_string.substring(padding_bits);
    }
    
    @Override public ToBaseReturn toBase(String binary_string){
        final int unit_size=3;
        
        String binary_unit=null;
        String octal_string="";
        int padding_bits_added=0;
        
        
        if(binary_string==null||binary_string.length()==0){
            return new ToBaseReturn("",0);
        }
        
        for(int index=binary_string.length();index>0;index-=unit_size){
            if(index-unit_size<0){
                binary_unit=binary_string.substring(0,index);
                
                while((binary_unit.length()%3)>0){
                    binary_unit='0'+binary_unit;
                    padding_bits_added++;
                }
            }else{
                binary_unit=binary_string.substring(index-unit_size,index);
            }
            
            octal_string=Integer.toOctalString(Integer.parseInt(binary_unit,2))+octal_string;
        }
        
        return new ToBaseReturn(octal_string,padding_bits_added);
    }

    @Override public int getNumericBase(){
        return 8;
    }

    @Override public String getBaseString(){
        return "Octal";
    }

    @Override public boolean isValid(String number_string){
        for(int index=0;index<number_string.length();index++){
            if(!(number_string.charAt(index)>='0'&&number_string.charAt(index)<='7')){
                return false;
            }
        }
        
        return true;
    }
}
