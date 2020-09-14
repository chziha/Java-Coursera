
/**
 * Write a description of test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class test {
    public void testCaesarCipherEcnrypt() {
        CaesarCipher cc = new CaesarCipher(5);
        FileResource fr = new FileResource();
        String origin = fr.asString();
        System.out.println(origin);
        System.out.println(cc.encrypt(origin));
    }
    
    public void testCaesarCrackerDecrypt() {
        CaesarCracker cc = new CaesarCracker('e');
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        System.out.println(encrypted);
        System.out.println(cc.decrypt(encrypted));
    }
    
    public void testVigenereCipherEncrypt() {
        int[] key = {17, 14, 12, 4};
        VigenereCipher vc = new VigenereCipher(key);
        FileResource fr = new FileResource();
        String origin = fr.asString();
        System.out.println(origin);
        System.out.println(vc.encrypt(origin));
    }
    
    public void testVigenereBreakerTryKeyLength() {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        VigenereBreaker vc = new VigenereBreaker();
        int[] key = vc.tryKeyLength(encrypted, 4, 'e');
        for (int i = 0; i < key.length; i++) {
            System.out.println(key[i]);
        }
    }
    
    public void quiz() {
        String s = "Hhdiu LVXNEW uxh WKWVCEW, krg k wbbsqa si Mmwcjiqm";
        int[] key = {3,20,10,4};;
        VigenereCipher vc = new VigenereCipher(key);
        System.out.println(vc.decrypt(s));
    }
}
