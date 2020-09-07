
/**
 * Write a description of TestCaesarCipherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class TestCaesarCipherTwo {
    // Method to get the string for every other letter given the staring point
    private String halfOfString(String message, int start) {
        StringBuilder sb = new StringBuilder();
        if (start < message.length()){
            for (int i = start; i < message.length(); i = i + 2) {
                sb.append(message.charAt(i));
            }
            return sb.toString();
        } else {
            System.out.println("The string is too short");
            return "";
        }
    }
    
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
    
    // Method to calculate the key based on the string
    private int getKey(String s) {
        int[] freqs = countLetters(s);
        int maxIdx = maxIndex(freqs);
        int key = maxIdx - 4;
        if (maxIdx < 4) {
            key = 22 + maxIdx;
        };
        return key;
    }
    
    // Method to decrypt based on frequencies of letters with two keys
    public String breakCaesarCipher(String input) {
        String firstHalf = halfOfString(input, 0);
        String secondHalf = halfOfString(input, 1);
        System.out.println("First half " + firstHalf);
        System.out.println("Second half  " + secondHalf);
        
        int key1 = getKey(firstHalf);
        int key2 = getKey(secondHalf);
        System.out.println("The first key is " + key1);
        System.out.println("The second key is " + key2);
        
        CaesarCipherTwo cct = new CaesarCipherTwo(key1, key2);
        String decrypted = cct.decrypt(input);
        return decrypted;
    }
    
    // Method to test the CaesarCipherTwo class
    public void simpleTests() {
        FileResource fr = new FileResource();
        String input = fr.asString();
        System.out.println("Original: " + input);
        
        CaesarCipherTwo cct = new CaesarCipherTwo(17, 3);
        String encrypted = cct.encrypt(input);
        System.out.println("Encrypted with keys = 17 & 3: " + encrypted);
        
        String decrypted1 = cct.decrypt(encrypted);
        System.out.println("Decrypted with keys = 9 & 23: " + decrypted1);
        
        String decrypted2 = breakCaesarCipher(encrypted);
        System.out.println("Decrypted based on frequencies: " + decrypted2);
    }
}
