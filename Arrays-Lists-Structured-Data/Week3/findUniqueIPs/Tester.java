
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        // complete method
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAll();
    }
    
    public void testUniqueIP() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAll();
        System.out.println("# of unique IPs: " + la.countUniqueIPs());
    }
    
    public void testPrintAllHigherThanNum() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAllHigherThanNum(201);
    }
    
    public void testUniqueIPVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog-short_log");
        System.out.println("Sep 14");
        for (String IP : la.uniqueIPVisitsOnDay("Sep 14")) {
            System.out.println(IP);
        }
        System.out.println("Sep 30");
        for (String IP : la.uniqueIPVisitsOnDay("Sep 30")) {
            System.out.println(IP);
        }
    }
    
    public void testCountUniqueIPsInRange() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        System.out.println("200-299");
        System.out.println("# of unique IPs: " + la.countUniqueIPsInRange(200, 299));
        System.out.println("300-399");
        System.out.println("# of unique IPs: " + la.countUniqueIPsInRange(300, 399));
    }
    
    public void quiz2() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        la.printAllHigherThanNum(400);
    }
    
    public void quiz3() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println("Mar 17");
        ArrayList<String> al = la.uniqueIPVisitsOnDay("Mar 17");
        System.out.println(al.size());
    }
    
    public void quiz4() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println("200-299");
        System.out.println("# of unique IPs: " + la.countUniqueIPsInRange(200, 299));
    }
}
