
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part3 {
    public boolean twoOccurrences(String stringa, String stringb) {
        int firstOccurrence = stringb.indexOf(stringa);
        if (firstOccurrence != -1) {
            if (stringb.indexOf(stringa, firstOccurrence + stringa.length()) != -1) {
                return true;
            }
        }
        return false;
    }
    
    public void testTwoOccurrences() {
        String stringa = "by";
        String stringb = "A story by Abby Long";
        System.out.println("a = " + stringa);
        System.out.println("b = " + stringb);
        System.out.println("twoOccurrences = " + twoOccurrences(stringa, stringb));
        
        stringa = "by";
        stringb = "A story by Abb Long";
        System.out.println("a = " + stringa);
        System.out.println("b = " + stringb);
        System.out.println("twoOccurrences = " + twoOccurrences(stringa, stringb));
    }
    
    public String lastPart(String stringa, String stringb) {
        String result = stringb;
        int startIndex = stringb.indexOf(stringa);
        if (startIndex != -1) {
            result = stringb.substring(startIndex + stringa.length());
            return result;
        }
        return result;
    }
    
    public void testLastPart() {
        String stringa = "an";
        String stringb = "banana";
        System.out.println(lastPart(stringa, stringb));
        
        stringa = "zoo";
        stringb = "forest";
        System.out.println(lastPart(stringa, stringb));
    }   
}
