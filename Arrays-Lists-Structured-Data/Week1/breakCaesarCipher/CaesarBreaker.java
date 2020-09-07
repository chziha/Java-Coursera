
/**
 * Write a description of CaesarBreaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class CaesarBreaker {
    // Method to count the number of occurrences of letters
    public int[] countLetters(String message) {
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
    public int maxIndex(int[] counts) {
        int idx = 0;
        for (int i = 1; i < counts.length; i++) {
            if (counts[i] > counts[idx]) {
                idx = i;
            }
        }
        return idx;
    } 
    
    // Method to decrypt the message based on frequencies of letters
    public String decrypt(String encrypted) {
        CaesarCipher cc = new CaesarCipher();
        int[] freqs = countLetters(encrypted);
        int maxIdx = maxIndex(freqs);
        
        int key = maxIdx - 4;
        if (maxIdx < 4) {
            key = 22 + maxIdx;
        }
        String message = cc.encrypt(encrypted, 26 - key);
        return message;
    }
    
    // Test method for decrypt
    public void testDecrypt() {
        FileResource fr = new FileResource();
        String message = fr.asString();
        CaesarCipher cc = new CaesarCipher();
        // Encrypt first
        String encrypted = cc.encrypt(message, 3);
        // Then decrypt
        String decrypted = decrypt(encrypted);
        System.out.println("Original: " + message);
        System.out.println("Decrypted: " + decrypted);
    }
    
    // Method to get the string for every other letter given the staring point
    public String halfOfString(String message, int start) {
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
    
    // Test method for halfOfString
    public void testHalfOfString() {
        String half1 = halfOfString("Qbkm Zgis", 0);
        System.out.println("Original : Qbkm Zgis");
        System.out.println("Half1: " + half1);
        
        String half2 = halfOfString("Qbkm Zgis", 1);
        System.out.println("Original : Qbkm Zgis");
        System.out.println("Half2: " + half2);
    }
    
    // Method to calculate the key based on the string
    public int getKey(String s) {
        int[] freqs = countLetters(s);
        int maxIdx = maxIndex(freqs);
        int key = maxIdx - 4;
        if (maxIdx < 4) {
            key = 22 + maxIdx;
        };
        return key;
    }
    
    // Method to decrypt with two keys
    public String decryptTwoKeys(String encrypted) {
        String firstHalf = halfOfString(encrypted, 0);
        String secondHalf = halfOfString(encrypted, 1);
        System.out.println("First half " + firstHalf);
        System.out.println("Second half  " + secondHalf);
        
        int key1 = getKey(firstHalf);
        int key2 = getKey(secondHalf);
        System.out.println("The first key is " + key1);
        System.out.println("The second key is " + key2);
        
        CaesarCipher cc = new CaesarCipher();
        String decrypted = cc.encryptTwoKeys(encrypted, 26 - key1, 26 - key2);
        return decrypted;
    }
    
    // Test method for decryptTwoKeys
    public void testDecryptTwoKeys() {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        String decrypted = decryptTwoKeys(encrypted);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }

}
