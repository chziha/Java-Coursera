
/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WordPlay {
    // Method to test if the character is a vowel
    public boolean isVowel (char ch) {
        String vowelString = "aeiou";
        return (vowelString.indexOf(Character.toLowerCase(ch)) != -1);
    }

    // Test method for isVowel
    public void testIsVowel() {
        System.out.println("isVowel(F): " + isVowel('F'));
        System.out.println("isVowel(a): " + isVowel('a'));
        System.out.println("isVowel(A): " + isVowel('A'));
    }
    
    // Method to replace vowels with specified characters
    public String replaceVowels(String phrase, char ch) {
        StringBuilder output = new StringBuilder(phrase);
        for (int i = 0; i < phrase.length(); i++) {
            if (isVowel(phrase.charAt(i))) {
                output.setCharAt(i, ch);
            }
        }
        return output.toString();
    }
    
    // Test method for replaceVowels 
    public void testReplaceVowels() {
        System.out.println(replaceVowels("Hello World", '*'));
    }
    
    // Method to replace specified characters at different indices
    public String emphasize(String phrase, char ch) {
        StringBuilder output = new StringBuilder(phrase);
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == Character.toLowerCase(ch) || phrase.charAt(i) == Character.toUpperCase(ch)) {
                if (i % 2 == 1) {
                    output.setCharAt(i, '+');
                } else {
                    output.setCharAt(i, '*');
                }
            }
        }
        return output.toString();
    }
    
    // Test method for emphasize
    public void testEmphasize() {
        System.out.println("The original string is 'dna ctgaaactga'");
        System.out.println("After emphasizing: " + emphasize("dna ctgaaactga", 'a'));
        System.out.println("The original string is 'Mary Bella Abracadabra'");
        System.out.println("After emphasizing: " + emphasize("Mary Bella Abracadabra", 'a'));
    }
    
}
