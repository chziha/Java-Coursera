
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public String findSimpleGene(String dna, String startCodon, String stopCodon) {
        String result = "";
        
        if( Character.isUpperCase(dna.charAt(0)) ) {
            startCodon = startCodon.toUpperCase();
            stopCodon = stopCodon.toUpperCase();
        } else {
            startCodon = startCodon.toLowerCase();
            stopCodon = stopCodon.toLowerCase();
        }
        
        int startIndex = dna.indexOf(startCodon);
        if (startIndex == -1) {
            return "";
        }
        int stopIndex = dna.indexOf(stopCodon, startIndex + 3);
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
        String startCodon = "ATG";
        String stopCodon = "TAA";
        
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna, startCodon, stopCodon));
        
        dna = "ATGAAAAAAAAAA";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna, startCodon, stopCodon));
        
        dna = "AAAAAAAAAAAAAA";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna, startCodon, stopCodon));
        
        dna = "atgaaaaaaaaataa";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna, startCodon, stopCodon));
        
        dna = "ATGAAAAAAAATAA";
        System.out.println("The DNA string is " + dna);
        System.out.println("The gene is " + findSimpleGene(dna, startCodon, stopCodon));
    }
}
