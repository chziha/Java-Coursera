
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class Part3 {
    public double cgRatio(String dna) {
        double count = 0;
        double length = dna.length();
        int currIndex = 0;
        while (dna.indexOf("C", currIndex) != -1) {
            count = count + 1;
            currIndex = dna.indexOf("C", currIndex) + 1;
        }
        currIndex = 0;
        while (dna.indexOf("G", currIndex) != -1) {
            count = count + 1;
            currIndex = dna.indexOf("G", currIndex) + 1;
        }
        return count / length;
    }
    
    public void processGenes(StorageResource sr, int length) {
        int count1 = 0;
        int count2 = 0;
        String longest = "";
        for (String s : sr.data()) {
            if (s.length() > length) {
                System.out.println("Length > threshold: " + s);
                count1 = count1 + 1;
            }
            if (cgRatio(s) > 0.35) {
                System.out.println("CGratio > 0.35: " + s);
                count2 = count2 + 1;
            }
            if (s.length() > longest.length()) {
                longest = s;
            }
        }
        System.out.println("The number of Strings that are longer than the threshold : " + count1);
        System.out.println("The number of Strings with CG ratio > 0.35: " + count2);
        System.out.println("The length of the longest String is " + longest.length());
    }
    
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int currIndex = dna.indexOf(stopCodon, startIndex);
        
        while (currIndex != -1) {
            if ((currIndex - startIndex) % 3 == 0) {
                return currIndex;
            } else {
                currIndex = dna.indexOf(stopCodon, currIndex + 1);
            }
        }
        return -1;
    }
    
    public String findGene(String dna) {
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            return "";
        }
        
        int taaIndex = findStopCodon(dna, startIndex + 3, "TAA");
        int tagIndex = findStopCodon(dna, startIndex + 3, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex + 3, "TGA");
        
        int minIndex = 0;
        if (taaIndex == -1 || (tagIndex != -1 && tagIndex < taaIndex)) {
            minIndex = tagIndex;
        } else {
            minIndex = taaIndex;
        }
        if (minIndex == -1 || (tgaIndex != -1 && tgaIndex < minIndex)) {
            minIndex = tgaIndex;
        }
        
        if (minIndex == -1) {
            return "";
        } else {
            return dna.substring(startIndex, minIndex + 3);
        }
    }
    
    public StorageResource getAllGenes(String dna) {
        int startIndex = 0;
        StorageResource sr = new StorageResource();
        while (true) {
            String currGene = findGene(dna);
            if (currGene.isEmpty()) {
                break;
            }
            sr.add(currGene);
            dna = dna.substring(dna.indexOf(currGene) + currGene.length());
        }
        return sr;
    }
    
    public void testProcessGenes() {
        FileResource fr = new FileResource("brca1line.fa");
        String dna = fr.asString().toUpperCase();
        StorageResource sr = getAllGenes(dna);
        processGenes(sr, 60);
    }
}
