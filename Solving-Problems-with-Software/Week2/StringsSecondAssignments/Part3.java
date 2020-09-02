
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part3 {
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
    
    public int countGenes(String dna) {
        int startIndex = 0;
        int count = 0;
        while (true) {
            String currGene = findGene(dna);
            if (currGene.isEmpty()) {
                break;
            }
            count = count + 1;
            dna = dna.substring(dna.indexOf(currGene) + currGene.length());
        }
        return count;
    }
    
    public void testCountGenes() {
        System.out.println("dna is ATGTAAGATGCCCTAGT");
        pirntAllGenes("ATGTAAGATGCCCTAGT");
        System.out.println(countGenes("ATGTAAGATGCCCTAGT"));
    }
}
