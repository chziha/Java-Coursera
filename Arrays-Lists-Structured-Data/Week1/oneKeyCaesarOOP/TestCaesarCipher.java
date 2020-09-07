
/**
 * Write a description of TestCaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class TestCaesarCipher {
    // Method to count the number of occurrences of letters
    private int[] countLetters(String message) {
        int[] counts = new int[26];
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < message.length(); i++) {
            int idx = alphabet.indexOf(Character.toLowerCase(message.charAt(i)));
            if (idx != -1) {
                counts[idx] += 1;
            }
        }
        return counts;
    }
    
    // Method to get the index with the largest value in an int array
    private int maxIndex(int[] counts) {
        int idx = 0;
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > counts[idx]) {
                idx = i;
            }
        }
        return idx;
    } 
    
    // Method to decrypt based on frequencies of letters
    public String breakCaesarCipher(String input) {
        int[] freqs = countLetters(input);
        int maxIdx = maxIndex(freqs);
        
        int key = maxIdx - 4;
        if (maxIdx < 4) {
            key = 22 + maxIdx;
        }
        
        CaesarCipher cc = new CaesarCipher(key);
        String message = cc.decrypt(input);
        return message;
    }
    
    // Method to test the CaesarCipher class
    public void simpleTests() {
        FileResource fr = new FileResource();
        String input = fr.asString();
        System.out.println("Original: " + input);
        CaesarCipher cc = new CaesarCipher(18);
        String encrypted = cc.encrypt(input);
        System.out.println("Encrypted with key = 18: " + encrypted);
        String decrypted = cc.decrypt(encrypted);
        System.out.println("Decrypted with key = 8: " + decrypted);
        String decrypted2 = breakCaesarCipher(encrypted);
        System.out.println("Decrypted based on frequencies: " + decrypted2);
    }
}
