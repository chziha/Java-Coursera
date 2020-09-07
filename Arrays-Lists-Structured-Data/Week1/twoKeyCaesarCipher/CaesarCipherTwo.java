
/**
 * Write a description of CaesarCipherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class CaesarCipherTwo {
    // Private fields
    private int mainKey1;
    private int mainKey2;
    private String alphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    
    // Constructor
    public CaesarCipherTwo(int key1, int key2) {
        mainKey1 = key1;
        mainKey2 = key2;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet1 = alphabet.substring(key1) + alphabet.substring(0, key1);
        shiftedAlphabet2 = alphabet.substring(key2) + alphabet.substring(0, key2);
    }
    
    // Method to encrypt using Caesar cipher using two keys
    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder(input);
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            char newChar = 'a';
            int idx = alphabet.indexOf(Character.toUpperCase(currChar));
            
            if (idx != -1) {
                if (i % 2 ==1) {
                    if (Character.isLowerCase(currChar)) {
                        newChar = Character.toLowerCase(shiftedAlphabet2.charAt(idx));
                    } else {
                        newChar = shiftedAlphabet2.charAt(idx);
                    }
                } else {
                    if (Character.isLowerCase(currChar)) {
                        newChar = Character.toLowerCase(shiftedAlphabet1.charAt(idx));
                    } else {
                        newChar = shiftedAlphabet1.charAt(idx);
                    }
                }
                encrypted.setCharAt(i, newChar);
            }
        }
        return encrypted.toString();
    }
    
    // Method to decrypt
    public String decrypt(String input) {
        CaesarCipherTwo cct = new CaesarCipherTwo(26 - mainKey1, 26 - mainKey2);
        String decrypt = cct.encrypt(input);
        return decrypt;
    }
}
