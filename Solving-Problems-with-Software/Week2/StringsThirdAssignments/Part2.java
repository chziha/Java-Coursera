
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
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
    
    public void testCgRatio() {
        System.out.println(cgRatio("ATGCCATAG"));
    }
    
    public int countCTG(String dna) {
        int count = 0;
        int currIndex = 0;
        while (dna.indexOf("CTG", currIndex) != -1) {
            count = count + 1;
            currIndex = dna.indexOf("CTG", currIndex) + 1;
        }
        return count;
    }
    
    public void testCountCTG() {
        System.out.println(countCTG("ATGCCATAGCTGCTG"));
    }
}
