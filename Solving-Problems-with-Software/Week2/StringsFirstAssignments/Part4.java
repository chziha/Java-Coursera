
/**
 * Write a description of Part4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class Part4 {
    public void findUrls(String url) {
        URLResource urlr = new URLResource(url);
        for (String word : urlr.lines()) {
            // System.out.println(word);
            if (word.toLowerCase().indexOf("youtube.com") != -1) {
                int keyIndex = word.toLowerCase().indexOf("youtube.com");
                int leftIndex = word.lastIndexOf("\"", keyIndex);
                int rightIndex = word.indexOf("\"", keyIndex);
                System.out.println(word.substring(leftIndex + 1, rightIndex));
            } else {
                System.out.println("Not Found At This Line.");
            }
        }
    }
    
    public void testFindUrls() {
        String url = "https://www.dukelearntoprogram.com//course2/data/manylinks.html";
        findUrls(url);
    }
}
