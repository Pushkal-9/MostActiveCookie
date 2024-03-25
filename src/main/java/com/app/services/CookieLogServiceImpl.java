package com.app.services;

import com.app.objects.ParsedArguments;
import com.app.utils.SearchUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CookieLogServiceImpl implements CookieLogService{
    private static final Logger logger = Logger.getLogger(CookieLogServiceImpl.class.getName());
    private final CommandLineParser commandLineParser;

    public CookieLogServiceImpl(CommandLineParser parser) {
        this.commandLineParser = parser;
    }


    /**
     * Processes command line arguments to identify and display the most active cookies for a given date.
     * It parses the command line arguments to extract the file path and date, then finds and prints the most active cookies.
     *
     * @param args Command line arguments containing the file path and date.
     */
    public void processCookies(String[] args) {
        try {
            ParsedArguments parsedArguments = commandLineParser.parse(args);
            List<String> mostActiveCookies = findMostActiveCookies(parsedArguments.getFilePath(), parsedArguments.getDate());
            mostActiveCookies.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            logger.severe(e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error processing the log file", e);
        }
    }

    /**
     * Finds the most active cookies for a given date from the log file specified by the file path.
     * It reads the log file, counts occurrences of cookies for the specified date, and identifies the most active ones.
     *
     * @param filePath The path to the log file containing cookie data.
     * @param date The date for which to find the most active cookies, in yyyy-MM-dd format.
     * @return A list of the most active cookies for the given date. Returns an empty list if an error occurs.
     */
    public List<String> findMostActiveCookies(String filePath, String date) {
        try {
            List<String> logRecords = readLogRecords(filePath);
            Map<String, Integer> cookieCount = countCookieOccurrences(logRecords, date);
            int maxCount = findMaxOccurrence(cookieCount);
            return collectMostActiveCookies(cookieCount, maxCount);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to find the most active cookies", e);
            return Collections.emptyList();
        }
    }

    /**
     * Counts occurrences of each cookie in the log lines for a specific date.
     *
     * @param logRecords A list of log lines from the log file.
     * @param date The date for which to count cookie occurrences, in yyyy-MM-dd format.
     * @return A map with cookies as keys and their occurrence count as values.
     */
    private Map<String, Integer> countCookieOccurrences(List<String> logRecords, String date) {
        Map<String, Integer> cookieCount = new HashMap<>();
        int startIndex = SearchUtil.findStartIndex(logRecords, date);
        int endIndex = SearchUtil.findEndIndex(logRecords, date);

        if (startIndex == -1 || endIndex == -1) return cookieCount;

        for (int i = startIndex; i <= endIndex; i++) {
            String line = logRecords.get(i);
            String[] parts = line.split(",");
            String cookie = parts[0];
            cookieCount.put(cookie, cookieCount.getOrDefault(cookie, 0) + 1);
        }

        return cookieCount;
    }

    /**
     * Finds the maximum occurrence count among all cookies.
     *
     * @param cookieCount A map with cookies as keys and their occurrence count as values.
     * @return The highest occurrence count found among the cookies.
     */
    private int findMaxOccurrence(Map<String, Integer> cookieCount) {
        int maxCount = 0;
        for (int count : cookieCount.values()) {
            if (count > maxCount) {
                maxCount = count;
            }
        }
        return maxCount;
    }

    /**
     * Collects cookies that have the highest occurrence count.
     *
     * @param cookieCount A map with cookies as keys and their occurrence count as values.
     * @param maxCount The highest occurrence count to filter the most active cookies.
     * @return A list of cookies that have the maximum occurrence count.
     */
    private List<String> collectMostActiveCookies(Map<String, Integer> cookieCount, int maxCount) {
        List<String> mostActiveCookies = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cookieCount.entrySet()) {
            if (entry.getValue() == maxCount) {
                mostActiveCookies.add(entry.getKey());
            }
        }
        return mostActiveCookies;
    }

    /**
     * Reads all lines from a file at the given file path.
     *
     * @param filePath The path to the file to be read.
     * @return A list of strings, each representing a line in the file.
     * @throws Exception If an error occurs during file reading.
     */
    private List<String> readLogRecords(String filePath) throws Exception {
        return Files.readAllLines(Paths.get(filePath));
    }
}
