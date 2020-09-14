
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
    
    public void testMostNumberVisitsByIP() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        System.out.println("Most number of visits by IP: " + la.mostNumberVisitsByIP(counts));    
    }
    
    public void testIPsMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        ArrayList<String> ips = la.iPsMostVisits(counts);
        System.out.println(ips);
    }
    
    public void testIPsForDays() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, ArrayList<String>> ipsDay = la.iPsForDays();
        System.out.println(ipsDay);
    }
    
    public void testDayWithMostIPVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, ArrayList<String>> ipsDay = la.iPsForDays();
        System.out.println(la.dayWithMostIPVisits(ipsDay));
    }
    
    public void testIPsWithMostVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, ArrayList<String>> ipsDay = la.iPsForDays();
        System.out.println(la.iPsWithMostVisitsOnDay(ipsDay, "Sep 30"));
    }
    
    public void quiz1() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        System.out.println("Most number of visits by IP: " + la.mostNumberVisitsByIP(counts));
    }
    
    public void quiz2() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        ArrayList<String> ips = la.iPsMostVisits(counts);
        System.out.println(ips);
    }
    
    public void quiz3() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        HashMap<String, ArrayList<String>> ipsDay = la.iPsForDays();
        System.out.println(la.dayWithMostIPVisits(ipsDay));
    }
    
    public void quiz4() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        HashMap<String, ArrayList<String>> ipsDay = la.iPsForDays();
        System.out.println(la.iPsWithMostVisitsOnDay(ipsDay, "Mar 17"));
    }
    
    public void review4() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("# of unique IPs: " + la.countUniqueIPs());
    }
    
    public void review5() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        ArrayList<String> al = la.uniqueIPVisitsOnDay("Sep 24");
        System.out.println(al.size());
    }
    
    public void review6() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        System.out.println("# of unique IPs: " + la.countUniqueIPsInRange(400, 499));
    }
    
    public void review7() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        System.out.println("Most number of visits by IP: " + la.mostNumberVisitsByIP(counts));
    }
    
    public void review8() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        ArrayList<String> ips = la.iPsMostVisits(counts);
        System.out.println(ips);
    }
    
    public void review9() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> ipsDay = la.iPsForDays();
        System.out.println(la.dayWithMostIPVisits(ipsDay));
    }
    
    public void review10() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2_log");
        HashMap<String, ArrayList<String>> ipsDay = la.iPsForDays();
        System.out.println(la.iPsWithMostVisitsOnDay(ipsDay, "Sep 29"));
    }
}
