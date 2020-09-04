
/**
 * Write a description of babyNames here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class babyNames {
    // Method to print the total number of births in terms of genders
    public void totalBirths(FileResource fr) {
        int total = 0;
        int totalB = 0;
        int totalG = 0;
        int totalBNames = 0;
        int totalGNames = 0;
        
        CSVParser parser = fr.getCSVParser(false);
        
        for (CSVRecord record : parser) {
            if (record.get(1).equals("F")) {
                totalG += Integer.parseInt(record.get(2));
                totalGNames += 1;
            } else {
                totalB += Integer.parseInt(record.get(2));
                totalBNames += 1;
            }
            total += Integer.parseInt(record.get(2));
        }
        System.out.println("Total number of births is " + total);
        System.out.println("Total number of girl births is " + totalG);
        System.out.println("Total number of boy births is " + totalB);
        System.out.println("Total number of girl names is " + totalGNames);
        System.out.println("Total number of boy names is " + totalBNames);
    }
    
    // Test method for totalBirths
    public void testTotalBirths() {
        FileResource fr = new FileResource();
        totalBirths(fr);
    }
    
    // Method to return the rank given name, year, and gender
    public int getRank(int year, String name, String gender) {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
        
        int rank = 0;
        
        for (CSVRecord record : parser) {
            if (record.get(1).equals(gender)) {
                rank += 1;
                if (record.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        return -1;
    }
    
    // Test method for getRank 
    public void testGetRank() {
        int rank = getRank(2012, "Mason", "M");
        System.out.println("Rank of Mason for boys in 2012 is " + rank);
    }
    
    // Method to get the name given the year, rank, and gender
    public String getName(int year, int rank, String gender) {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        CSVParser parser = fr.getCSVParser(false);
        
        int tempRank = 0;
        
        for (CSVRecord record : parser) {
            if (record.get(1).equals(gender)) {
                tempRank += 1;
                if (tempRank == rank) {
                    return record.get(0);
                }
            }
        }
        return "NO NAME";
    }
    
    // Test method for getName
    public void testGetName() {
        String name = getName(2012, 2, "M");
        System.out.println("Rank 2 name for boys in 2012 is " + name);
    }
    
    // Method to find your name if you were born in a different year
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender);
        if (rank == -1) {
            System.out.println("Rank not found for the given name.");
            return;
        }
        String newName = getName(newYear, rank, gender);
        if (newName.equals("NO NAME")) {
            System.out.println("No name for the corresponding rank found.");
            return;
        }
        if (gender.equals("F")) {
            System.out.println(name + " born in " + year + " would be " + newName + " if she was born in " + newYear);
        } else {
            System.out.println(name + " born in " + year + " would be " + newName + " if he was born in " + newYear);
        }
    }
    
    // Test method for whatIsNameInYear
    public void testWhatIsNameInYear() {
        whatIsNameInYear("Isabella", 2012, 2014, "F");
    }
    
    // Method to find the year for a specif name and gender
    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int rank = -1;
        int year = -1;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int tempYear = Integer.parseInt(f.getName().substring(3, 7));
            int tempRank = getRank(tempYear, name, gender);
            if (rank == -1) {
                if (tempRank != -1) {
                    rank = tempRank;
                    year = tempYear;
                }
            } else {
                if (tempRank != -1 && tempRank < rank) {
                    rank = tempRank;
                    year = tempYear;
                }
            }
        }
        return year;
    }
    
    // Test method for yearOfHighestRank
    public void testYearOfHighestRank() {
        int year = yearOfHighestRank("Mason", "M");
        System.out.println("The year for the highest ranking of Mason for boys is " + year);
        
    }
    
    // Method to calculate the average ranking
    public double getAverageRank(String name, String gender) {
        double totalRank = 0;
        int count = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int tempYear = Integer.parseInt(f.getName().substring(3, 7));
            int tempRank = getRank(tempYear, name, gender);
            if (tempRank != -1) {
                totalRank += tempRank;
                count += 1;
            } else {
                return -1.0;
            }
        }
        return (totalRank / count);
    }
    
    // Test method for getAverageRank
    public void testGetAverageRank() {
        double avg = getAverageRank("Jacob", "M");
        System.out.println("The average ranking for Mason for boys is " + avg);
    }
    
    /* Method to calculate the total number of births of thoses names,
    who have a higher rank than the given name in the same year 
    with same gender */
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        // FileResource fr = new FileResource("us_babynames/us_babynames_test/yob" + year + "short.csv");
        CSVParser parser = fr.getCSVParser(false);
        int count = 0;
        int rankGiven = getRank(year, name, gender);
        if (rankGiven == -1) {
            System.out.println("Name not found.");
            return -1;
        }
        
        for (CSVRecord record : parser) {
            if (record.get(1).equals(gender)) {
                if (record.get(0).equals(name)) {
                    return count;
                } else {
                    count += Integer.parseInt(record.get(2));
                }
            }
        }
        return count;
    }
    
    // Test method for getTotalBirthsRankedHigher
    public void testGetTotalBirthsRankedHigher() {
        int total = getTotalBirthsRankedHigher(2012, "Ethan", "M");
        System.out.println("Total count for higher ranked births is " + total);
    }
}
