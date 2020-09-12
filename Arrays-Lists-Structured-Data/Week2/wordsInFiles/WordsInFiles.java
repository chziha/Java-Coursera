
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import java.io.*;

public class WordsInFiles {
    // Define the private field
    private HashMap<String, ArrayList<String>> wordMap;
    
    // Contructor
    public WordsInFiles() {
        wordMap = new HashMap<String, ArrayList<String>>();
    }
    
    // Method to adds all words in one file to the map
    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        String name = f.getName();
        for (String word : fr.words()) {
            if (!wordMap.containsKey(word)) {
                ArrayList<String> fileList = new ArrayList<String>();
                fileList.add(name);
                wordMap.put(word, fileList);
            } else {
                ArrayList<String> fileList = wordMap.get(word);
                if (!fileList.contains(name)) {
                    fileList.add(name);
                }
            }
        }
    }
    
    // Method to count the files 
    public void buildWordFileMap() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }
    
    // Method to calculate the maximum number of files any word appears in
    public int maxNumber() {
        int maxNumFiles = 0;
        for (String word: wordMap.keySet()) {
            ArrayList fileList = wordMap.get(word);
            if (fileList.size() > maxNumFiles) {
                maxNumFiles = fileList.size();
            }
        }
        return maxNumFiles;
    }
    
    // Method to return words that appear in exactly number files
    public ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> wordList = new ArrayList<String>();
        for (String word: wordMap.keySet()) {
            ArrayList fileList = wordMap.get(word);
            if (fileList.size() == number) {
                wordList.add(word);
            }
        }
        return wordList;
    }
    
    // Method to print all file names that one word appears in
    public void printFilesIn(String word) {
        if (wordMap.containsKey(word)) {
            System.out.print(word + ": ");
            for (String s : wordMap.get(word)) {
                System.out.print(s + " ");
            }
            System.out.println("Finished.");
        } else {
            System.out.println("Key not found.");
        }
    }
    
    // Test method
    public void tester() {
        buildWordFileMap();
        int maxNumFiles = maxNumber();
        ArrayList<String> wordList = wordsInNumFiles(maxNumFiles);
        for (String s : wordList) {
            printFilesIn(s);
        }
        System.out.println("The maximum number of files any word appears in: " + maxNumFiles);
        System.out.println("The number of files with maximum appearances: " + wordList.size());
        System.out.println("The number of files with 4 appearances: " + wordsInNumFiles(4).size());
        printFilesIn("tree");
        printFilesIn("laid");
    }
}
