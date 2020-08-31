
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public String findSimpleGene(String dna) {
        String result = "";
        
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            return "";
        }
        int stopIndex = dna.indexOf("TAA", startIndex + 3);
        if (stopIndex == -1) {
            return "";
        }
        if ((stopIndex - startIndex) % 3 == 0) {
            result = dna.substring(startIndex, stopIndex + 3);
            return result;
        } else {
            return "";
        }
    }
    
    public void testSimpleGene() {
        String dna = "ATAAAAAAAAAATAA";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna));
        
        dna = "ATGAAAAAAAAAA";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna));
        
        dna = "AAAAAAAAAAAAAA";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna));
        
        dna = "ATGAAAAAAAAATAA";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna));
        
        dna = "ATGAAAAAAAATAA";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna));
        
        dna = "AAATGCCCTAACTAGATTAAGAAACC";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna));
    }
}

