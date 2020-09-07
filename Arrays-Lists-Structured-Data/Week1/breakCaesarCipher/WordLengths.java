
/**
 * Write a description of WordLengths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class WordLengths {
    // Method to count the length of a given word
    public int wordLength(String word) {
        int length = word.length();
        if (!Character.isLetter(word.charAt(0))) {
            length = length - 1;
        }
        if (word.length() > 1 && !Character.isLetter(word.charAt(word.length() - 1))) {
            length = length - 1;
        }
        return length;
    }
    
    // Method to count the occurrences of words with different lengths
    public void countWordLengths (FileResource resource, int[] counts) {
        for (String word : resource.words()) {
            int length = wordLength(word);
            if (length < counts.length) {
                counts[length] += 1;
            } else {
                counts[counts.length - 1] += 1;
            }
        }
        for (int i = 0; i < counts.length; i++) {
            System.out.println("The number of words of length " + i + " is " + counts[i]);
        }
    }
    
    // Method to get the index with the largest value in an int array
    public int indexOfMax(int[] values) {
        int idx = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[idx]) {
                idx = i;
            }
        }
        return idx;
    }
    
    // Test method for countWordLengths
    public void testCountWordLengths() {
        FileResource fr = new FileResource();
        int[] counts = new int[31];
        countWordLengths(fr, counts);
        System.out.println("The index with the largest value is " + indexOfMax(counts));
    }
}
