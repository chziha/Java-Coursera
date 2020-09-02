
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class Part1 {
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
    
    public void testFindStopCodon() {
        String dna = "ATGAAAAAAAATAAATAA";
        System.out.println("dna is " + dna);
        System.out.println(findStopCodon(dna, 3, "TAA"));
        dna = "ATGAAAAAATAA";
        System.out.println("dna is " + dna);
        System.out.println(findStopCodon(dna, 3, "TAA"));
        dna = "ATGAAAAAAAAA";
        System.out.println("dna is " + dna);
        System.out.println(findStopCodon(dna, 3, "TAA"));
        dna = "ATGTAAAAAAAAA";
        System.out.println("dna is " + dna);
        System.out.println(findStopCodon(dna, 3, "TAA"));
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
    
    public void testFindGene() {
        String dna = "ATAAGAAAAAAAATAAATAA";
        System.out.println("dna is " + dna);
        System.out.println(findGene(dna));
        dna = "ATGAAAAAATAA";
        System.out.println("dna is " + dna);
        System.out.println(findGene(dna));
        dna = "ATGAAAAAAAAA";
        System.out.println("dna is " + dna);
        System.out.println(findGene(dna));
        dna = "ATGAAATAGAAA";
        System.out.println("dna is " + dna);
        System.out.println(findGene(dna));
        dna = "ATGTAATAGAAA";
        System.out.println("dna is " + dna);
        System.out.println(findGene(dna));
    }
    
    public void pirntAllGenes(String dna) {
        int startIndex = 0;
        while (true) {
            String currGene = findGene(dna);
            if (currGene.isEmpty()) {
                break;
            }
            System.out.println(currGene);
            dna = dna.substring(dna.indexOf(currGene) + currGene.length());
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
    
    public void testPrintAllGenes() {
        String dna = "ATGAAAAAATAAAAAAAATGAAAATAAAATAAAAAAAAAATGAAATAAAAAAA";
        System.out.println("dna is " + dna);
        pirntAllGenes(dna);
    }
    
    public void testGetAllGenes() {
        StorageResource sr = getAllGenes("ATGAAAAAATAAAAAAAATGAAAATAAAATAAAAAAAAAATGAAATAAAAAAA");
        for (String s : sr.data()) {
            System.out.println(s);
        }
    }
}
