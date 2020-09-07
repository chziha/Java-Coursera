
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipher {
    // Declare the private fields
    private int mainKey;
    private String alphabet;
    private String shiftedAlphabet;
    
    // Constructor
    public CaesarCipher(int key) {
        mainKey = key;
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
    }
    
    // Method to encrypt
    public String encrypt(String input) {
        StringBuilder encrypted = new StringBuilder(input);
        for (int i = 0; i < encrypted.length(); i++) {
            char currChar = encrypted.charAt(i);
            int idx = alphabet.indexOf(Character.toUpperCase(currChar));
            if (idx != -1) {
                if (Character.isLowerCase(currChar)) {
                    char newChar = Character.toLowerCase(shiftedAlphabet.charAt(idx));
                    encrypted.setCharAt(i, newChar);
                } else {
                    char newChar = shiftedAlphabet.charAt(idx);
                    encrypted.setCharAt(i, newChar);
                }
            }
        }
        return encrypted.toString();
    }
    
    // Method to decrypt
    public String decrypt(String input) {
        CaesarCipher cc = new CaesarCipher(26 - mainKey);
        String decrypted = cc.encrypt(input);
        return decrypted;
    }

}
