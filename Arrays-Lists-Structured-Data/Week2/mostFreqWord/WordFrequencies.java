
/**
 * Write a description of WordFrequencies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;

public class WordFrequencies {
    // Define the private fields
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    // Constructor
    public WordFrequencies() {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    // Method to count the unique words
    public void findUnique() {
        myWords.clear();
        myFreqs.clear();
        FileResource fr = new FileResource();
        for (String s : fr.words()) {
            s = s.toLowerCase();
            int idx = myWords.indexOf(s);
            if (idx == -1) {
                myWords.add(s);
                myFreqs.add(1);
            } else {
                int freq = myFreqs.get(idx);
                myFreqs.set(idx, freq + 1);
            }
        }
    }
    
    // Method to find the index of the word with the highest frequency
    public int findIndexOfMax() {
        int idx = 0;
        for (int k = 0; k < myFreqs.size(); k++) {
            if (myFreqs.get(k) > myFreqs.get(idx)) {
                idx = k;
            }
        }
        return idx;
    }
    
    // Test method for findUnique and findIndexOfMax
    public void tester() {
        findUnique();
        for (int k = 0; k < myWords.size(); k++) {
            System.out.println(myFreqs.get(k) + " " + myWords.get(k));
        }
        int maxIdx = findIndexOfMax();
        System.out.println("Number of unique words: " + myWords.size());
        System.out.println("The word that occurs most often and its count are: " + myWords.get(maxIdx) + " " + myFreqs.get(maxIdx));
    }
}
