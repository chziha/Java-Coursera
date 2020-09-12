
/**
 * Write a description of CodonCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;

public class CodonCount {
    // Define the private field
    private HashMap<String, Integer> codonsCount;
    
    // Constructor
    public CodonCount() {
        codonsCount = new HashMap<String, Integer>();
    }
    
    // Method to count codons given a dna with a starting point
    public void buildCodonMap(int start, String dna) {
        codonsCount.clear();
        for (int k = start; k < (dna.length() - 2); k = k + 3) {
            String codon = dna.substring(k, k + 3);
            if (!codonsCount.containsKey(codon)) {
                codonsCount.put(codon, 1);
            } else {
                codonsCount.put(codon, codonsCount.get(codon) + 1);
            }
        }
    }
    
    // Method to get the most frequent codon
    public String getMostCommonCodon() {
        String mostFreqCodon = "";
        for (String codon : codonsCount.keySet()) {
            if (mostFreqCodon.isEmpty()) {
                mostFreqCodon = codon;
            } else {
                if (codonsCount.get(codon) > codonsCount.get(mostFreqCodon)) {
                    mostFreqCodon = codon;
                }
            }
        }
        return mostFreqCodon;
    }
    
    // Method to print all codons with corresponding counts given the range of counts
    public void printCodonCounts(int start, int end) {
        for (String codon : codonsCount.keySet()) {
            if (codonsCount.get(codon) >= start && codonsCount.get(codon) <= end) {
                System.out.println(codon + " " + codonsCount.get(codon));
            }
        }
    }
    
    // Test method
    public void test() {
        FileResource fr = new FileResource();
        String dna = fr.asString().toUpperCase();
        System.out.println(dna);
        System.out.println(dna.length());
        // Remove the blank line
        dna = dna.substring(0, dna.length() - 1);
        System.out.println(dna);
        System.out.println(dna.length());
        for (int k = 0; k < 3; k++) {
            System.out.println("reading frame of k = " + k);
            buildCodonMap(k, dna);
            System.out.println("Total number of unique codons: " + codonsCount.size());
            String mostFreqCodon = getMostCommonCodon();
            System.out.println("Most frequent codon: " + mostFreqCodon + " " + codonsCount.get(mostFreqCodon));
            printCodonCounts(1, codonsCount.get(mostFreqCodon));
        }
    }

}
