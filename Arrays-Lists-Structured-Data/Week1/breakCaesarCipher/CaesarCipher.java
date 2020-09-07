
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;

public class CaesarCipher {
    // Method to encrypt using Caesar cipher
    public String encrypt(String input, int key) {
        StringBuilder encrypted = new StringBuilder(input);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
        
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
    
    // Test method for encrypt
    public void testEncrypt() {
        System.out.println("Original: 'FIRST LEGION ATTACK EAST FLANK!'");
        System.out.println("Encrypted: " + encrypt("FIRST LEGION ATTACK EAST FLANK!", 23));
        System.out.println("Original: 'First Legion'");
        System.out.println("Encrypted: " + encrypt("First Legion", 17));
    }
    
    // Test method for encrypt
    public void testCaesar() {
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = encrypt(message, 23);
        System.out.println("key is " + 23 + "\n" + encrypted);
    }
    
    // Method to encrypt using Caesar cipher using two keys
    public String encryptTwoKeys(String input, int key1, int key2) {
        StringBuilder encrypted = new StringBuilder(input);
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String shiftedAlphabet1 = alphabet.substring(key1) + alphabet.substring(0, key1);
        String shiftedAlphabet2 = alphabet.substring(key2) + alphabet.substring(0, key2);
        
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
    
    // Test method for encryptTwoKeys
    public void testEncryptTwoKeys() {
        System.out.println("Original: 'First Legion'");
        System.out.println("Encrypted with keys 23 and 17: " + encryptTwoKeys("First Legion", 23, 17));
    }
    
    // Quiz
    public void quiz() {
        System.out.println(encrypt("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 15));
        System.out.println(encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 8, 21));
    }
}
