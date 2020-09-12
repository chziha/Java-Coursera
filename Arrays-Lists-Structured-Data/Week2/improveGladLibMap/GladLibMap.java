
/**
 * Write a description of GladLibMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedList;
    private ArrayList<String> catList;
	
    private Random myRandom;
	
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
	
    public GladLibMap(){
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }
	
    public GladLibMap(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }
	
    private void initializeFromSource(String source) {
        myMap = new HashMap<String, ArrayList<String>>();
        String[] labels = {"adjective", "noun", "color", "country", "name", 
            "animal", "timeframe", "verb", "fruit"};
        for (String s : labels) {
            ArrayList<String> wordList = readIt(source + "/"+ s + ".txt");
            myMap.put(s, wordList);
        }
        usedList = new ArrayList<String>();
        catList = new ArrayList<String>();
    }
	
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
	
    private String getSubstitute(String label) {
        if (label.equals("number")) {
            return "" + myRandom.nextInt(50);
        } else if (myMap.containsKey(label)) {
            return randomFrom(myMap.get(label));
        }
        return "**UNKNOWN**";
    }
	
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String label = w.substring(first+1,last);
        if (!catList.contains(label)) {
            catList.add(label);
        }
        String sub = getSubstitute(label);
        while (usedList.indexOf(sub) != -1) {
            sub = getSubstitute(w.substring(first+1,last));
        }
        usedList.add(sub);
        return prefix+sub+suffix;
    }
	
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
	
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
	
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
	
    private int totalWorsInMap() {
        int totalCount = 0;
        for (String label : myMap.keySet()) {
            totalCount += myMap.get(label).size();
        }
        return totalCount;
    }
    
    private int totalWordsConsidered() {
        int totalConsidered = 0;
        // System.out.println(catList);
        for (String label : catList) {
            if (!label.equals("number")) {
                totalConsidered += myMap.get(label).size();
            }
        }
        return totalConsidered;
    }
    
    public void makeStory(){
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("");
        System.out.println("The total number of words that were replaced: " + usedList.size());
        int totalCount = totalWorsInMap();
        System.out.println("The total number of words in all the ArrayLists in the HashMap: " + totalCount);
        int totalConsidered = totalWordsConsidered();
        System.out.println("The total number of words considered in the HashMap: " + totalConsidered);
    }
}

