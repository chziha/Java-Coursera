import java.util.*;
import edu.duke.*;
import java.io.*;

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
        char maxCh = mostCommonCharIn(dictionary);
        String result = "";
        for (int i = 1; i < 101; i++) {
            int[] key = tryKeyLength(encrypted, i, maxCh);
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
    
    // Method to find the most common character
    public char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<Character, Integer> counts = new HashMap<Character, Integer>();
        for (String word : dictionary) {
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                if (!counts.containsKey(ch)) {
                    counts.put(ch, 1);
                } else {
                    counts.put(ch, counts.get(ch) + 1);
                }
            }
        }
        int maxCount = 0;
        char maxCh = 'a';
        for (char ch : counts.keySet()) {
            if (counts.get(ch) > maxCount) {
                maxCount = counts.get(ch);
                maxCh = ch;
            }
        }
        return maxCh;
    }
    
    // Method to break for all languages
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (String lang : languages.keySet()) {
            String decrypted = breakForLanguage(encrypted, languages.get(lang));
            int count = countWords(decrypted, languages.get(lang));
            counts.put(lang, count);
        }
        int maxCount = 0;
        String maxLang = "";
        for (String lang : counts.keySet()) {
            if (counts.get(lang) > maxCount) {
                maxCount = counts.get(lang);
                maxLang = lang;
            }
        }
        
        String result = breakForLanguage(encrypted, languages.get(maxLang));
        System.out.println("The language is " + maxLang);
        System.out.println("Decrypted: " + result);
    }
    
    // Method to decrypt
    public void breakVigenere () {
        HashMap<String, HashSet<String>> languages = new HashMap<>();
        DirectoryResource dr = new DirectoryResource();
        
        for(File fr: dr.selectedFiles()){
            languages.put(fr.getName(), readDictionary(new FileResource(fr)));
        }
        
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        
        breakForAllLangs(encrypted, languages);
    }
}
