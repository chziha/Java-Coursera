
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;

public class CharactersInPlay {
    // Define private fields
    private ArrayList<String> myChars;
    private ArrayList<Integer> myFreqs;
    
    // Constructor
    public CharactersInPlay() {
        myChars = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    // Method to update the ArrayLists for a give string
    private void update(String person) {
        int idx = myChars.indexOf(person); 
        if (idx == -1) {
            myChars.add(person);
            myFreqs.add(1);
        } else {
            int freq = myFreqs.get(idx);
            myFreqs.set(idx, freq + 1);
        }
    }
    
    // Method to count all possible characters
    public void findAllCharacters() {
        myChars.clear();
        myFreqs.clear();
        FileResource fr = new FileResource();
        for (String s : fr.lines()) {
            int idx = s.indexOf(".");
            if (idx != -1) {
                update(s.substring(0, idx));
            }
        }
    }
    
    // Method to filter characters with a given range of appearances
    public void charactersWithNumParts(int num1, int num2) {
        for (int k = 0; k < myChars.size(); k++) {
            int freq = myFreqs.get(k);
            if (freq >= num1 && freq <= num2) {
                System.out.println(myChars.get(k) + " " + freq);
            }
        }
    }
    
    // Test method for findAllCharacters and charactersWithNumParts
    public void tester() {
        findAllCharacters();
        for (int k = 0; k < myChars.size(); k++) {
            int freq = myFreqs.get(k);
            if (freq > 1) {
                System.out.println(myChars.get(k) + " " + freq);
            }
        }
        System.out.println("");
        charactersWithNumParts(10, 15);
    }
}
