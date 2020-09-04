
/**
 * Write a description of CSVMin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMin {
    public CSVRecord getLowerOfTwo(CSVRecord currRow, CSVRecord coldestSoFar) {
        double currT = Double.parseDouble(currRow.get("TemperatureF"));
        if (coldestSoFar == null) {
            if (currT != -9999) {
                coldestSoFar = currRow;
            }
        } else {
            double coldestT = Double.parseDouble(coldestSoFar.get("TemperatureF"));
            if (currT < coldestT && currT != -9999) {
                coldestSoFar = currRow;
            }
        }
        return coldestSoFar;
    }
    
    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldestSoFar = null;
        for (CSVRecord currRow : parser) {
            coldestSoFar = getLowerOfTwo(currRow, coldestSoFar);
        }
        return coldestSoFar;
    }
    
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println(coldest.get("TemperatureF"));
    }
    
    public String fileWithColdestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord coldestSoFar = null;
        String fileName = null;
        File coldestF = null;
        
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currRow = coldestHourInFile(fr.getCSVParser());
            double currT = Double.parseDouble(currRow.get("TemperatureF"));
            if (coldestSoFar == null) {
                if (currT != -9999) {
                    coldestSoFar = currRow;
                    coldestF = f;
                    fileName = f.getName();
                }
            } else {
                double coldestT = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if (currT < coldestT && currT != -9999) {
                    coldestSoFar = currRow;
                    coldestF = f;
                    fileName = f.getName();
                }
            }
        }
        System.out.println("Coldest day was in file " + fileName);
        System.out.println("Coldest temperature on that day was " + coldestSoFar.get("TemperatureF"));
        System.out.println("All the Temperatures on the coldest day were:");
        FileResource coldestFr = new FileResource(coldestF);
        for (CSVRecord row : coldestFr.getCSVParser()) {
            System.out.println(row.get("DateUTC") + ": " + row.get("TemperatureF"));
        }
        return(fileName);
        
    }
    
    public void testFileWithColdestTemperature() {
        String fileName = fileWithColdestTemperature();
    }
    
    public CSVRecord getLowerHumOfTwo(CSVRecord currRow, CSVRecord lowestSoFar) {
        String currH = currRow.get("Humidity");
        if (lowestSoFar == null) {
            if (!(currH.contains("N/A"))) {
                lowestSoFar = currRow;
            }
        } else {
            double lowestH = Double.parseDouble(lowestSoFar.get("Humidity"));
            if (!(currH.contains("N/A")) && Double.parseDouble(currH) < lowestH) {
                lowestSoFar = currRow;
            }
        }
        return lowestSoFar;
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        for (CSVRecord currRow : parser) {
            lowestSoFar = getLowerHumOfTwo(currRow, lowestSoFar);
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestSoFar = null;
        
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord currRow = lowestHumidityInFile(fr.getCSVParser()); 
            lowestSoFar = getLowerHumOfTwo(currRow, lowestSoFar);
        }
        return(lowestSoFar);
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord lowestSoFar = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowestSoFar.get("Humidity") + " at " + lowestSoFar.get("DateUTC"));
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
        int count = 0;
        double total = 0;
        for (CSVRecord currRow : parser) {
            double currT = Double.parseDouble(currRow.get("TemperatureF"));
            if (currT != -9999) {
                count = count + 1;
                total = total + currT;
            }
        }
        return (total / count);
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        double avg = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average temperature in file is " + avg);
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        int count = 0;
        double total = 0;
        for (CSVRecord currRow : parser) {
            double currT = Double.parseDouble(currRow.get("TemperatureF"));
            String currH = currRow.get("Humidity");
            if (currT != -9999 && !(currH.contains("N/A")) && Double.parseDouble(currH) >= value) {
                count = count + 1;
                total = total + currT;
            }
        }
        return (total / count);
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        double avg = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), 80);
        System.out.println("Average Temp when high Humidity is " + avg);
    }
}
