
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public int howMany(String stringa, String stringb) {
        int startIndex = 0;
        int count = 0;
        while (true) {
            int currIndex = stringb.indexOf(stringa, startIndex);
            if (currIndex == -1) {
                break;
            } else {
                startIndex = currIndex + stringa.length();
                count = count + 1;
            }
        }
        return count;
    }
    
    public void testHowMany() {
        System.out.println("a is GAA, b is ATGAACGAATTGAATC");
        System.out.println(howMany("GAA", "ATGAACGAATTGAATC"));
        
        System.out.println("a is AA, b is ATAAAA");
        System.out.println(howMany("AA", "ATAAAA"));
    }
}
