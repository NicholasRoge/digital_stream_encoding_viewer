package roge.encodingviewer.gui.basedecoders;


/**
 * Class intent is to provide an interface for the converting of a string of numbers from one base to another.
 * 
 * @author Nicholas Rogé
 */
public interface BaseDecoder{
    /**Container for the objects returned by the toBase method.*/
    public static class ToBaseReturn{
        /**String of characters that are readable in the given base.*/
        public String base_string;
        /**Number of bits that had to be added to make the string valid for the given base.*/
        public int padding_bits_added;
        
        
        /*Begin Constructors*/
        /**
         * Constructs the object with the default variable contents.
         */
        public ToBaseReturn(){
            this("",0);
        }
        
        /**
         * Constructs the object with the given variable contents.
         * 
         * @param base_string String of characters that are readable in the given base.
         * @param padding_bits_added Number of bits that had to be added to make the string valid for the given base.
         */
        public ToBaseReturn(String base_string,int padding_bits_added){
            this.base_string=base_string;
            this.padding_bits_added=padding_bits_added;
        }
        /*End Constructors*/
    }
    
    
    /**
     * Gets the numeric version of the base.
     * 
     * @return Returns the numeric version of the base.
     */
    public int getNumericBase();
    
    /**
     * Gets the String version of the base.  (binary, octal, decimal, hexadecimal)
     * 
     * @return Returns the String version of the base.  (binary, octal, decimal, hexadecimal)
     */
    public String getBaseString();
    
    /**
     * Converts the given string to binary.
     * 
     * @param number_string String containing a given set of characters that are valid for the encoder being used.
     * @param num_padding_bits Number of padding bits that had to be added to make the string valid for the given base.
     * 
     * @return The translated binary string.
     */
    public String toBinary(String number_string,int num_padding_bits);
    
    /**
     * Converts the binary string to a string that is readable in the given base.
     * 
     * @param binary_string String of binary numbers to be converted to the given base.
     * 
     * @return The translated string, and number of bits that had to be added to make it valid for the given base.
     */
    public ToBaseReturn toBase(String binary_string);
    
    /**
     * A check to test whether the given string is valid for the base.
     * 
     * @param number_string Number string to check the validity of base for.  TODO:  Word that better...
     * 
     * @return Returns <code>true</code> if the string is valid, and <code>false</code> otherwise.
     */
    public boolean isValid(String number_string);
}
