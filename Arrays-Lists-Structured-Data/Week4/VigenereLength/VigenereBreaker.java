import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    // Method to slice a string with given staring point and interval
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sb = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            sb.append(message.charAt(i));
        }
        return sb.toString();
    }
    
    // Method to calculate the key
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        for (int i = 0; i < klength; i++) {
            String slice = sliceString(encrypted, i, klength);
            CaesarCracker cc = new CaesarCracker(mostCommon);
            key[i] = cc.getKey(slice);
        }
        return key;
    }
    
    // Method to read in a dictionary
    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dict = new HashSet<String>();
        for (String word : fr.lines()) {
            word = word.toLowerCase();
            if(!dict.contains(word)) {
                dict.add(word);
            }
        }
        return dict;
    }
    
    // Method to calculate # of real words
    public int countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        for (String word : message.toLowerCase().split("\\W+")) {
            if (dictionary.contains(word)) {
                count++;
            }
        }
        return count;
    }
    
    // Method to decrypt
    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int maxCount = 0;
        int length = 0;
        String result = "";
        for (int i = 1; i < 101; i++) {
            int[] key = tryKeyLength(encrypted, i, 'e');
            VigenereCipher vc = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            int count = countWords(decrypted, dictionary);
            if (count > maxCount) {
                maxCount = count;
                length = i;
                result = decrypted;
            }
        }
        System.out.println("Max count is " + maxCount);
        System.out.println("Key length is " + length);
        return result;
    }
    
    // Method to decrypt
    public void breakVigenere () {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        
        FileResource fr2 = new FileResource();
        HashSet<String> dict = readDictionary(fr2);
        
        String decrypted = breakForLanguage(encrypted, dict);
        // System.out.println("Encrypted: " + encrypted);
        // System.out.println("Decrypted: " + decrypted);
    }
    
    public void quiz3() {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        
        int[] key = tryKeyLength(encrypted, 57, 'e');
        
        FileResource fr2 = new FileResource();
        String twolines = fr2.asString();
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(twolines);
        System.out.println(decrypted);
    }
    
    public void quiz4() {
        FileResource fr = new FileResource();
        HashSet<String> dict = readDictionary(fr);
        
        FileResource fr2 = new FileResource();
        String encrypted = fr2.asString();
        
        int[] key = tryKeyLength(encrypted, 38, 'e');
        VigenereCipher vc = new VigenereCipher(key);
        String decrypted = vc.decrypt(encrypted);
        int count = countWords(decrypted, dict);
        System.out.println(count);
    }
}
