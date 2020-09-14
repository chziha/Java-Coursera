
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer {
    private ArrayList<LogEntry> records;
     
    public LogAnalyzer() {
        // complete constructor
        records = new ArrayList<LogEntry>();
    }
        
    public void readFile(String filename) {
        records.clear();
        // complete method
        FileResource fr = new FileResource(filename);
        for (String line : fr.lines()) {
            records.add(WebLogParser.parseEntry(line));
        }
    }
     
    // Method to count the number of unique IPs
    public int countUniqueIPs() {
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry le : records) {
            String ipAddr = le.getIpAddress();
            if (!uniqueIPs.contains(ipAddr)) {
                uniqueIPs.add(ipAddr);
            }
        }
        return uniqueIPs.size();
    }
    
    // Method to print entries with a status code greater than a given number
    public void printAllHigherThanNum(int num) {
        for (LogEntry le : records) {
            if (le.getStatusCode() > num) {
                System.out.println(le);
            }
        }
    }
    
    // Method to print unique IPs in a given day (MMM DD, i.e. SEP 30)
    public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry le : records) {
            String date = le.getAccessTime().toString();
            String ipAddr = le.getIpAddress();
            if (date.contains(someday) && !uniqueIPs.contains(ipAddr)) {
                uniqueIPs.add(ipAddr);
            }
        }
        return uniqueIPs;
    }
    
    // Method to count unique IPs in a given range of status code
    public int countUniqueIPsInRange(int low, int high) {
        ArrayList<String> uniqueIPs = new ArrayList<String>();
        for (LogEntry le : records) {
            int status = le.getStatusCode();
            String ipAddr = le.getIpAddress();
            if (status >= low && status <= high && !uniqueIPs.contains(ipAddr)) {
                uniqueIPs.add(ipAddr);
            }
        }
        return uniqueIPs.size();
    }
    
    // Method to count the number of visits per IP
    public HashMap<String, Integer> countVisitsPerIP() {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (LogEntry le : records) {
            String ipAddr = le.getIpAddress();
            if (!counts.containsKey(ipAddr)) {
                counts.put(ipAddr, 1);
            } else {
                counts.put(ipAddr, counts.get(ipAddr) + 1);
            }
        }
        return counts;
    }
    
    // Method to return the maximum number of visits of one IP
    public int mostNumberVisitsByIP(HashMap<String, Integer> map) {
        int maxVisits = 0;
        for (String IP : map.keySet()) {
            if (map.get(IP) > maxVisits) {
            maxVisits = map.get(IP);
            }
        }
        return maxVisits;
    }
    
    // Method to return all IP addresses with the maximum number of visits
    public ArrayList<String> iPsMostVisits(HashMap<String, Integer> map) {
        int maxVisits = mostNumberVisitsByIP(map);
        ArrayList<String> ipsMost = new ArrayList<String>();
        for (String IP : map.keySet()) {
            if (map.get(IP) == maxVisits && !ipsMost.contains(IP)) {
            ipsMost.add(IP);
            }
        }
        return ipsMost;
    }
    
    // Method to collect all IPs for each day
    public HashMap<String, ArrayList<String>> iPsForDays() {
        HashMap<String, ArrayList<String>> ipsDay = new HashMap<String, ArrayList<String>>();
        for (LogEntry le : records) {
            String date = le.getAccessTime().toString().substring(4, 10);
            String ip = le.getIpAddress();
            
            if (!ipsDay.containsKey(date)) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(ip);
                ipsDay.put(date, list);
            } else  {
                ArrayList<String> list = ipsDay.get(date);
                list.add(ip);
                ipsDay.put(date, list);
            }
        }
        return ipsDay;
    }
    
    // Method to find days with the maximum number of visits
    public ArrayList<String> dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
        int maxVisits = 0;
        for (String date : map.keySet()) {
            if (map.get(date).size() > maxVisits) {
            maxVisits = map.get(date).size();
            }
        }
        ArrayList<String> daysMost = new ArrayList<String>();
        for (String date : map.keySet()) {
            if (map.get(date).size() == maxVisits && !daysMost.contains(date)) {
            daysMost.add(date);
            }
        }
        return daysMost;
    }
    
    // Method to find the IP with the maximum visits for a given day
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> map, String date) {
        ArrayList<String> ips = new ArrayList<String>();
        if (!map.containsKey(date)) {
            System.out.println("Date not found.");
            return ips;
        }
        ArrayList<String> history = map.get(date);
        
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (String ip : history) {
            if (!counts.containsKey(ip)) {
                counts.put(ip, 1);
            } else {
                counts.put(ip, counts.get(ip) + 1);
            }
        }
        
        int maxVisits = 0;
        for (String IP : counts.keySet()) {
            if (counts.get(IP) > maxVisits) {
            maxVisits = counts.get(IP);
            }
        }
        
        for (String IP : counts.keySet()) {
            if (counts.get(IP) == maxVisits && !ips.contains(IP)) {
            ips.add(IP);
            }
        }
        return ips;
    }
    
    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }
}
