/**
 * 
 */
package roge.encodingviewer.gui.basedecoders;

/**
 * @author Nicholas Rogé
 */
public class HexadecimalDecoder implements BaseDecoder{
    @Override public String toBinary(String number_string,int padding_bits){
        final int unit_size=4;
        
        String binary_string="";
        String binary_unit=null;
        
        
        if(number_string==null||number_string.length()==0){
            return "";
        }
        
        for(int index=0;index<number_string.length();index++){
            binary_unit=Integer.toBinaryString(Integer.parseInt(""+number_string.charAt(index),16));
            while((binary_unit.length()%unit_size)>0){
                binary_unit='0'+binary_unit;
            }
            
            binary_string+=binary_unit;
        }
        
        return binary_string.substring(padding_bits);
    }
    
    @Override public ToBaseReturn toBase(String binary_string){
        final int unit_size=4;
        
        String binary_unit=null;
        String hex_string="";
        int padding_bits_added=0;
        
        
        if(binary_string==null||binary_string.length()==0){
            return new ToBaseReturn("",0);
        }
        
        for(int index=binary_string.length();index>0;index-=unit_size){
            if(index-unit_size<0){
                binary_unit=binary_string.substring(0,index);
                
                while((binary_unit.length()%unit_size)>0){
                    binary_unit='0'+binary_unit;
                    padding_bits_added++;
                }
            }else{
                binary_unit=binary_string.substring(index-unit_size,index);
            }
            
            hex_string=Integer.toHexString(Integer.parseInt(binary_unit,2))+hex_string;
        }
        
        return new ToBaseReturn(hex_string,padding_bits_added);
    }

    @Override public int getNumericBase(){
        return 16;
    }

    @Override public String getBaseString(){
        return "Hexadecimal";
    }

    @Override public boolean isValid(String number_string){
        for(int index=0;index<number_string.length();index++){
            if(!((number_string.charAt(index)>='0'&&number_string.charAt(index)<='9')||(number_string.toLowerCase().charAt(index)>='a'&&number_string.toLowerCase().charAt(index)<='f'))){
                return false;
            }
        }
        
        return true;
    }
}
